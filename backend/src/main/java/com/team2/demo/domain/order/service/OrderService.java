package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.dto.*;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.product.dto.ProductListDto;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.domain.user.dto.UserDto;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.repository.UserRepository;
import com.team2.demo.domain.user.service.UserService;
import com.team2.demo.global.exception.order.NoProductsInOrderException;
import com.team2.demo.global.exception.order.NoSuchOrderException;
import com.team2.demo.global.exception.product.NoSuchProductException;
import com.team2.demo.global.exception.user.AccessDeniedException;
import com.team2.demo.global.response.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // 사용자: 주문 리스트 조회
    public Page<OrderDto> getOrdersByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate"); // 최근 주문이 가장 먼저 보이게
        Page<Order> orders = orderRepository.findAllByBuyer_Email(email, pageable);
        return orders.map(order -> new OrderDto(order)); // 상품 미포함
    }


    // 사용자: 주문 수정
    @Transactional
    public OrderDto updateOrder(String orderId, String email, @Valid OrderRequestDto request) {
        Order order = orderRepository.findByOrderUuid(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        User loggedInUser = userRepository.findByEmail(email);

        if(!loggedInUser.isMine(order))
            throw new AccessDeniedException("자신이 주문하지 않은 주문을 수정할 수 없습니다.");

        if (order.getDeliveryStatus() == Order.DeliveryStatus.SHIPPED ||
                order.getDeliveryStatus() == Order.DeliveryStatus.DELIVERED) {
            throw new IllegalStateException("배송 중이거나 배송 완료된 주문은 수정할 수 없습니다.");
        }

        List<ProductWithAmount> updatedProducts = request.getItems().stream()
                .map(item ->
                                new ProductWithAmount(
                                        productRepository.findByProductUuid(item.getProductId())
                                                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + item.getProductId()))
                                        , item.getQuantity()))
                        .collect(Collectors.toList());

        if (updatedProducts.isEmpty()) {
            cancelOrder(orderId, email);
        }

        order.updateOrder(updatedProducts,  request.getAddress(), request.getZipcode(), order.getDeliveryStatus());

        return new OrderDto(order);
    }

    // 관리자: 주문 리스트 조회
    @Transactional
    public Page<OrderDto> getAllOrders(int page, int size, int maxItems) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "orderUuid");
        Page<Order> orders = orderRepository.findAll(pageable);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("주문 내역이 없습니다.");
        }

        return orders.map(order -> {

            Map<String, Integer> productCountMap = OrderItemGrouper.countProducts(order.getProducts());

            List<OrderDto.ProductItem> limitedItems = productCountMap.entrySet().stream()
                    .map(entry -> new OrderDto.ProductItem(entry.getKey(), entry.getValue()))
                    .limit(maxItems) // 보여줄 상품 개수 제한
                    .collect(Collectors.toList());

            return new OrderDto(order.getOrderUuid(), order.getCreateDate(), order.getTotalAmount(),
                    order.getDeliveryStatus(), order.getBuyer().getEmail(), order.getDeliveryAddress(), limitedItems);
        });

//        return orders.map(order -> new OrderDto(order, true)); // 상품 포함
    }

    //사용자: 주문 생성
    public Order payment(OrderRequestDto body){
        System.out.println("결제 진행 서비스 시작");

        User user = userService.findByEmail(body.getBuyer().getEmail());
        if(user == null){
            user=userService.addUser(new UserDto(body.getBuyer()));
        }

        int totalAmount = 0;

        List<ProductListDto> items = body.getItems();

        // 주문에 아이템 추가하는 로직. 머지할때 조심할것
        List<Pair<Product, Integer>> productsInOrder = new ArrayList<>();
        for (ProductListDto item: items){
            Product product = productRepository.findByProductUuid(item.getProductId())
                    .orElseThrow(() -> new NoSuchProductException("id가 %s인 product는 없습니다.".formatted(item.getProductId())));

            // 주문에 아이템 추가하는 로직. 머지할때 조심할것
            productsInOrder.add(Pair.of(product, item.getQuantity()));
            totalAmount += product.getProductPrice() * item.getQuantity();
        }

        Order order = Order.builder()
                .buyer(user)
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .deliveryStatus(Order.DeliveryStatus.PENDING)
                .zipCode(body.getZipcode())
                .deliveryAddress(body.getAddress())
                .build();

        // 주문에 아이템 추가하는 로직. 머지할때 조심할것
        order.addItems(productsInOrder);

        return orderRepository.save(order);
    }

    public OrderInfoWithoutItemDto getOrderAdmin(@NotEmpty String orderId) {
        return new OrderInfoWithoutItemDto(orderRepository.findByOrderUuid(orderId)
                .orElseThrow(() -> new NoSuchOrderException("orderId가 " + orderId + "인 order를 찾을 수 없습니다."))
        );
    }

    // 사용자: 주문 취소
    @Transactional
    public RsData<OrderResponseCancelDto> cancelOrder(String orderId, String email) {
        Optional<Order> optionalOrder = orderRepository.findByOrderUuid(orderId);

        if (optionalOrder.isEmpty()) {
            return RsData.badRequest("해당하는 주문을 찾을 수 없어 삭제에 실패했습니다.", 404);
        }

        Order order = optionalOrder.get();

        User user = userService.findByEmail(email);
        if (!user.isMine(order)) {
            return RsData.badRequest("주문 취소 권한이 없습니다.", 403);
        }

        if (order.getDeliveryStatus() == Order.DeliveryStatus.SHIPPED ||
                order.getDeliveryStatus() == Order.DeliveryStatus.DELIVERED) {
            return RsData.badRequest("배송 중이거나 배송 완료된 주문은 취소할 수 없습니다.", 400);
        }

        order.updateDeliveryStatus(Order.DeliveryStatus.CANCELLED);

        OrderResponseCancelDto responseDto = OrderResponseCancelDto.builder()
                .orderId(order.getOrderUuid())
                .message("주문이 성공적으로 취소되었습니다.")
                .build();

        return RsData.success("주문이 성공적으로 취소되었습니다.", responseDto);
    }

    public OrderInfoWithoutItemDto findOrder(String orderId, String email) {
        Order order =  orderRepository.findByOrderUuid(orderId).
                orElseThrow(() -> new NoSuchOrderException("id가 %s인 order를 찾을 수 없습니다.".formatted(orderId)));

        User loggedInUser = userService.findByEmail(email);

        if(loggedInUser == null)
            throw new AccessDeniedException("email이 %s인 유저는 없습니다.".formatted(email));

        if(!loggedInUser.isMine(order))
            throw new AccessDeniedException("다른 사람의 주문을 조회할 수 없습니다.");

        return new OrderInfoWithoutItemDto(order);
    }

    public void orderIsExist(@NotEmpty String orderId) {
        if(orderRepository.countByOrderUuid(orderId)==0){
            throw new NoSuchOrderException("id가 %s인 Order는 없습니다.".formatted(orderId));
        }
    }
}
