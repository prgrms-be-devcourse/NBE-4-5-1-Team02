package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.controller.OrderController;
import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.dto.OrderItemGrouper;
import com.team2.demo.domain.order.dto.OrderInfoWithoutItemDto;
import com.team2.demo.domain.order.dto.OrderRequestDto;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.global.response.RsData;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 사용자: 주문 리스트 조회
    public Page<OrderDto> getOrdersByEmail(OrderController.OrderForm orderForm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createDate"); // 최근 주문이 가장 먼저 보이게
        Page<Order> orders = orderRepository.findAllByUser_Email(orderForm.email(), pageable);
        return orders.map(order -> new OrderDto(order)); // 상품 미포함
    }


    // 사용자: 주문 수정
    @Transactional
    public RsData<OrderDto> updateOrder(String orderId, String email, OrderRequestDto request) {
        Order order = orderRepository.findByOrderUuid(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));


        if (order.getDeliveryStatus() == Order.DeliveryStatus.SHIPPED ||
                order.getDeliveryStatus() == Order.DeliveryStatus.DELIVERED) {
            return RsData.badRequest("배송 중이거나 배송 완료된 주문은 수정할 수 없습니다.", 400);
        }


        List<Product> updatedProducts = request.getProductIds().stream()
                .map(productUuid -> productRepository.findByProductUuid(productUuid)
                        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productUuid)))
                .collect(Collectors.toList());

        order.updateProducts(updatedProducts); // clear() + addAll()

        order.updateDeliveryInfo(request.getDeliveryAddress(), request.getZipCode());

        order.updateModifiedDate();

        orderRepository.save(order);

        if (updatedProducts.isEmpty()) {
            order.updateDeliveryStatus(Order.DeliveryStatus.CANCELLED);
            orderRepository.delete(order);
            return RsData.success("주문이 취소되었습니다.", null);
        }

        return RsData.success("주문이 수정되었습니다.", new OrderDto(order));
    }

    // 관리자: 주문 리스트 조회
    @Transactional
    public Page<OrderDto> getAllOrders(int page, int size, int maxItems) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createDate");
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
                    order.getDeliveryStatus(), order.getUser().getEmail(), limitedItems);
        });

//        return orders.map(order -> new OrderDto(order, true)); // 상품 포함
    }
  
    public Order payment(Order order){
        System.out.println("결제 진행 서비스 시작");
        return orderRepository.save(order);
    }

    public OrderInfoWithoutItemDto getOrderAdmin(@NotEmpty String orderId) {
        return new OrderInfoWithoutItemDto(orderRepository.findByOrderUuid(orderId)
                .orElseThrow(() -> new EntityNotFoundException("orderId가 " + orderId + "인 order를 찾을 수 없습니다."))
        );
    }

    // 사용자: 주문 취소
    @Transactional
    public RsData<Void> cancelOrder(String orderId, String email) {
        Optional<Order> optionalOrder = orderRepository.findByOrderUuid(orderId);

        if (optionalOrder.isEmpty()) {
            return RsData.badRequest("해당 주문을 찾을 수 없습니다.", 404);
        }

        Order order = optionalOrder.get();
        if (order.getDeliveryStatus() == Order.DeliveryStatus.SHIPPED ||
                order.getDeliveryStatus() == Order.DeliveryStatus.DELIVERED) {
            return RsData.badRequest("배송 중이거나 배송 완료된 주문은 취소할 수 없습니다.", 400);
        }

        order.updateDeliveryStatus(Order.DeliveryStatus.CANCELLED);
        orderRepository.save(order);

        return RsData.success("주문이 취소되었습니다.", null);
    }
}
