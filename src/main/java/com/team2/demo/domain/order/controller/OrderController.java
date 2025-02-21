package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;

import com.team2.demo.domain.order.dto.OrderRequestDto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.global.response.OrderListResponse;
import com.team2.demo.global.response.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /*
        [정상적인 이메일 형식]
        user@example.com
        user.name@example.com
        user+test@example.org
        user123@example.co.kr
        test-user@sub.domain.io
     */
    public record OrderForm(@Email
                            @Pattern(
                                    regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                                    message = "올바른 이메일 형식이어야 합니다."
                            ) String email) {
    }

    /*
        사용자 주문 목록 조회
        GET /orders?email=user@example.com&page=1&size=10
    */
    @GetMapping
    public RsData<OrderListResponse> getOrders(@ModelAttribute @Valid OrderForm orderForm,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {

        Page<OrderDto> orderPage = orderService.getOrdersByEmail(orderForm, page, size);

        OrderListResponse response = OrderListResponse.builder()
                .content(orderPage.getContent())
                .page(orderPage.getNumber())
                .size(orderPage.getSize())
                .totalPages(orderPage.getTotalPages())
                .build();

        return RsData.success("ok", response);
    }

    @PostMapping
    public RsData<OrderDto> payment(@RequestBody Order body) {
        try {
            Order order = orderService.payment(body);
            return null;
        } catch (Exception e) {
            return RsData.badRequest("결제 실패:" + e.getMessage(), 400);
        }
    }

    /*
        사용자 주문 수정
        PUT /orders/{orderId}?email=user@example.com
    */
    @PutMapping("/{orderId}")
    public ResponseEntity<RsData<OrderDto>> updateOrder(
            @PathVariable String orderId,
            @RequestParam String email,
            @RequestBody OrderRequestDto request) {
        RsData<OrderDto> response = orderService.updateOrder(orderId, email, request);
        return ResponseEntity.ok(response);
    }
}
