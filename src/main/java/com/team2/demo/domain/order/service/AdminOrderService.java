package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order updateOrder(String orderId, Order request) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));

        //주문 정보 업데이트
        order.updateOrder(
                request.getTotalAmount(),
                request.getDeliveryAddress(),
                request.getZipCode(),
                request.getDeliveryStatus()
        );
        return order;
    }
}
