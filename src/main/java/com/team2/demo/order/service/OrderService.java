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


}
