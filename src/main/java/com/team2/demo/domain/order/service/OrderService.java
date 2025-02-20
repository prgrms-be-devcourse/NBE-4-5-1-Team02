package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<OrderDto> getOrdersByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createDate"); // 최근 주문이 가장 먼저 보이게.
        Page<Order> orders = orderRepository.findAllByEmail(email, pageable);

        return orders.map(OrderDto::new);
    }
}
