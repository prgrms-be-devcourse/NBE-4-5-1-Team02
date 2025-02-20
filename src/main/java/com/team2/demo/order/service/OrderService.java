package com.team2.demo.order.service;

import com.team2.demo.order.Order;
import com.team2.demo.order.dto.OrderDto;
import com.team2.demo.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    //주문 찾기
    @Transactional
    public Order updateOrder(String orderId, Order request) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));

        //주문 정보 업데이트
        order.updateOrder(
                request.getTotalAmount(),
                request.getDeliveryAddress(),
                request.getDeliveryStatus(),
                request.getZipCode()
        );
        return order;
    }
}


