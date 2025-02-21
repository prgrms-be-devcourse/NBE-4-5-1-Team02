package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.controller.OrderController;
import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.dto.OrderRequestDto;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

        order.getProducts().clear();
        order.getProducts().addAll(updatedProducts);

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
    public Page<OrderDto> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createDate");
        Page<Order> orders = orderRepository.findAll(pageable);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("주문 내역이 없습니다.");
        }

        return orders.map(order -> new OrderDto(order, true)); // 상품 포함
    }
  
  
    public Order payment(Order order){
        System.out.println("결제 진행 서비스 시작");
        return orderRepository.save(order);
    }
}
