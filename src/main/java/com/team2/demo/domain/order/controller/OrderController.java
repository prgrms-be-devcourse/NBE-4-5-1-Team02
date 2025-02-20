package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/{orderId}")
    public ResponseEntity<RsData<OrderDto>> updateOrder(
            @PathVariable String orderId,
            @RequestBody OrderDto request) {
        RsData<OrderDto> response = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(response);
    }
}
