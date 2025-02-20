package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.controller.OrderController;
import com.team2.demo.domain.order.dto.OrderDto;
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

    public Page<OrderDto> getOrdersByEmail(OrderController.OrderForm orderForm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createDate"); // 최근 주문이 가장 먼저 보이게
        Page<Order> orders = orderRepository.findAllByUser_Email(orderForm.email(), pageable);
        return orders.map(OrderDto::new);
    }

    @Transactional
    public RsData<OrderDto> updateOrder(String orderId, String email, OrderDto request) {
        Order order = orderRepository.findByOrderUuid(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));


        if (order.getDeliveryStatus() == Order.DeliveryStatus.SHIPPED ||
                order.getDeliveryStatus() == Order.DeliveryStatus.DELIVERED) {
            return RsData.fail("배송 중이거나 배송 완료된 주문은 수정할 수 없습니다.");
        }


        List<Product> updatedProducts = request.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId)))
                .collect(Collectors.toList());

        order.getProducts().clear();
        order.getProducts().addAll(updatedProducts);

        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setZipCode(request.getZipCode());

        order.setModifiedDate(LocalDateTime.now());
        orderRepository.save(order);

        if (updatedProducts.isEmpty()) {
            order.setDeliveryStatus(Order.DeliveryStatus.CANCELLED);
            orderRepository.delete(order);
            return RsData.success(null, "주문이 취소되었습니다.");
        }

        return RsData.success(new OrderDto(order), "주문이 수정되었습니다.");
    }
}
