package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.dto.OrderInfoWithoutItemDto;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.service.AdminOrderService;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.OrderListResponse;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class OrderControllerAdmin {

    private final OrderService orderService;
    private final AdminOrderService adminOrderService;
    private final ProductService productService;


    /*
        관리자 주문 목록 조회

        GET /admin/orders?page=1&size=10
    */
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

    @PutMapping("/{orderUuid}")
    public RsData<OrderDto> updateOrder(
            @PathVariable String orderUuid,
            @RequestBody Order order) {
        OrderDto updateOrder = adminOrderService.updateOrder(orderUuid, order);
        return RsData.success("success.", updateOrder);
    }

    @GetMapping("{orderId}")
    public RsData<OrderInfoWithoutItemDto> getOrderInfo(@PathVariable String orderId){
        OrderInfoWithoutItemDto data= orderService.getOrderAdmin(orderId);

        return RsData.success("Success.", data);
    }

    //주문 삭제
    @DeleteMapping("/{orderUuid}")
    public RsData<Void> deleteOrder(@PathVariable String orderUuid){
        adminOrderService.deleteOrder(orderUuid);
        return RsData.success("주문이 성공적으로 삭제되었습니다.",null);
    }


}
