package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.dto.OrderInfoWithoutItemDto;
import com.team2.demo.domain.order.dto.OrderRequestDto;
import com.team2.demo.domain.order.service.AdminOrderService;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.global.response.OrderListResponse;
import com.team2.demo.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Tag(name = "AdminOrders", description = "관리자 주문 API")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/admin/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderControllerAdmin {

    private final OrderService orderService;
    private final AdminOrderService adminOrderService;

    /*
        관리자 주문 목록 조회

        GET /admin/orders?page=1&size=10
    */
    @Operation(summary = "주문 목록 조회", description = "모든 유저의 주문을 조회한다.")
    @GetMapping
    public RsData<OrderListResponse> getAllOrders(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(name = "max-products" ,defaultValue = "2") int maxProducts) {

        Page<OrderDto> orderPage = orderService.getAllOrders(page, size, maxProducts);

        OrderListResponse response = OrderListResponse.builder()
                .content(orderPage.getContent())
                .page(orderPage.getNumber() + 1)
                .size(orderPage.getSize())
                .totalPages(orderPage.getTotalPages())
                .build();

        return RsData.success("ok", response);
    }

    @Operation(summary = "주문 수정", description = "특정 유저의 주문을 수정한다.")
    @PutMapping("/{orderUuid}")
    public RsData<OrderDto> updateOrder(
            @PathVariable String orderUuid,
            @RequestBody OrderRequestDto order) {
        OrderDto updateOrder = adminOrderService.updateOrder(orderUuid, order);
        return RsData.success("success.", updateOrder);
    }

    @Operation(summary = "주문 상세 조회", description = "특정 유저의 주문 상세를 조회한다.")
    @GetMapping("{orderId}")
    public RsData<OrderInfoWithoutItemDto> getOrderInfo(@PathVariable String orderId){
        OrderInfoWithoutItemDto data= orderService.getOrderAdmin(orderId);

        return RsData.success("Success.", data);
    }

    //주문 삭제

    @Operation(summary = "주문 삭제", description = "특정 유저의 주문을 삭제한다.")
    @DeleteMapping("/{orderUuid}")
    public RsData<Void> deleteOrder(@PathVariable String orderUuid){
        adminOrderService.deleteOrder(orderUuid);
        return RsData.success("주문이 성공적으로 삭제되었습니다.", null);
    }
}
