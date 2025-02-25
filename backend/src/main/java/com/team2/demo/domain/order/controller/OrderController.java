package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.*;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.user.service.UserService;
import com.team2.demo.global.response.OrderListResponse;
import com.team2.demo.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Orders", description = "주문 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
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
                                    regexp = "^[a-z0-9]+@[a-z]+\\.com$",
                                    message = "올바른 이메일 형식이어야 합니다."
                            ) String email) {
    }

    /*
        사용자 주문 목록 조회
        GET /orders?email=user@example.com&page=1&size=10
    */

    @Operation(summary = "주문 리스트 조회 ", description = "사용자의 모든 주문 목록을 조회한다.")
    @GetMapping
    public RsData<OrderListResponse> getOrders(
            @RequestParam
            @Email
            @Pattern(regexp = "^[a-z0-9]+@[a-z]+\\.com$",
                    message = "올바른 이메일 형식이어야 합니다.")
            String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<OrderDto> orderPage = orderService.getOrdersByEmail(email, page, size);

        OrderListResponse response = OrderListResponse.builder()
                .content(orderPage.getContent())
                .page(orderPage.getNumber())
                .size(orderPage.getSize())
                .totalPages(orderPage.getTotalPages())
                .build();

        return RsData.success("ok", response);
    }

    @Operation(summary = "주문 상세 조회 ",
            description = """
                    사용자의 주문 하나의 상세 정보를 조회한다.<br/>
                    주문에 포함된 모든 상품을 페이지네이션된 결과로 보여주는 API를 추가로 호출해야 완전한 주문 상세 결과를 얻을 수 있다.
                    """)
    @GetMapping(value = "/{orderId}")
    public RsData<OrderInfoWithoutItemDto> getOrderInfo(@NotEmpty @PathVariable String orderId,
                                                        @Valid @NotNull @Email @RequestParam(name = "email") String email) {
        OrderInfoWithoutItemDto order = orderService.findOrder(orderId, email);

        return RsData.success("주문 상세 조회 성공", order);
    }


    @Operation(summary = "주문 생성 ", description = "사용자가 주문을 생성한다.")
    @PostMapping
    public RsData<OrderInfoDto> payment(@Valid @RequestBody OrderRequestDto body) {

        System.out.println("이메일조회"+body.getBuyer().getEmail());

        Order response = orderService.payment(body);
        OrderInfoDto orderRequestDto = OrderInfoDto.of(response);
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
        if(response == null){
            return RsData.success("주문이 취소되었습니다." ,null);
        }
        return RsData.success("ok", response);
    }

    /*
    사용자 주문 취소
     DELETE /orders/{orderId}?email=user@example.com
    */
    @Operation(summary = "주문 삭제 ", description = "사용자가 자신이 생성한 주문을 삭제한다.")
    @DeleteMapping("/{orderId}")
    public RsData<OrderResponseCancelDto> cancelOrder(
            @PathVariable String orderId,
            @RequestParam String email) {
        return orderService.cancelOrder(orderId, email);
    }
}

