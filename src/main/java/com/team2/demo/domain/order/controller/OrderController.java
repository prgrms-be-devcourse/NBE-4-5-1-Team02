package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /*
        사용자 주문 목록 조회
        GET /orders?email=user@example.com&page=1&size=10
     */
    @GetMapping
    public void getOrders(@RequestParam String email,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size) {

        Page<OrderDto> orderPage = orderService.getOrdersByEmail(email, page, size);

    }
}
