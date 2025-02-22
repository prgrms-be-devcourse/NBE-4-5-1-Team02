package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;

import com.team2.demo.domain.order.dto.OrderRequestDto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.user.dto.UserDto;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.service.UserService;
import com.team2.demo.global.response.OrderListResponse;
import com.team2.demo.global.response.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

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
    
    @Operation(summary = "주문 생성 ", description = "사용자가 주문을 생성한다.")
    @PostMapping
    public RsData<OrderRequestDto> payment(@RequestBody OrderRequestDto body) {

        System.out.println("이메일조회"+body.getBuyer().getEmail());

        Order response = orderService.payment(body);
        OrderRequestDto orderRequestDto = OrderRequestDto.of(response);
        return RsData.success("ok", orderRequestDto);

    }

    /*
        사용자 주문 수정
        PUT /orders/{orderId}?email=user@example.com
    */
    @Operation(summary = "주문 수정 ", description = "사용자가 자신이 생성한 주문을 수정한다.")
    @PutMapping("/{orderId}")
    public RsData<OrderDto> updateOrder(
            @PathVariable String orderId,
            @RequestParam String email,
            @Valid @RequestBody OrderRequestDto request) {
        OrderDto response = orderService.updateOrder(orderId, email, request);

        return RsData.success("ok", response);
    }

    /*
사용자 주문 취소
 DELETE /orders/{orderId}?email=user@example.com
*/
    @DeleteMapping("/{orderId}")
    public RsData<Void> cancelOrder(
            @PathVariable String orderId,
            @RequestParam String email) {
        RsData<Void> response = orderService.cancelOrder(orderId, email);
        return response;
    }
    
        /*
    사용자 주문 취소
     DELETE /orders/{orderId}?email=user@example.com
    */
    @Operation(summary = "주문 삭제 ", description = "사용자가 자신이 생성한 주문을 삭제한다.")
    @DeleteMapping("/{orderId}")
    public RsData<Void> cancelOrder(
            @PathVariable String orderId,
            @RequestParam String email) {
        RsData<Void> response = orderService.cancelOrder(orderId, email);
        return response;
    }
}


