package com.team2.demo.order.service;

import com.team2.demo.order.Order;
import com.team2.demo.order.dto.OrderDto;
import com.team2.demo.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

//    public Order updateOrder(String orderUuid, OrderDto requset) {
//        Order order = orderRepository.findByOrderUuid(orderUuid)
//                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderUuid));
//
//    }


}
