package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderService orderService;

    public Page<OrderDto> getOrdersByEmail(String email, int page, int size) {

        return null;
    }
}
