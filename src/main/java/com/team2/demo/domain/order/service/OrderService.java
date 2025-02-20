package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order payment(Order order){
        System.out.println("결제 진행 서비스 시작");
        return orderRepository.save(order);
        //return null;
    }
}
