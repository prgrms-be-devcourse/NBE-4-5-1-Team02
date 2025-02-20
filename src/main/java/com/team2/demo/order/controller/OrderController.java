package com.team2.demo.order.controller;

import com.team2.demo.order.Order;
import com.team2.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 수정
    @PutMapping("/{orderUuid}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable String orderUuid,
            @RequestBody Order order) {
        Order updateOrder = orderService.updateOrder(orderUuid, order);
        return ResponseEntity.ok(updateOrder);
    }

}
