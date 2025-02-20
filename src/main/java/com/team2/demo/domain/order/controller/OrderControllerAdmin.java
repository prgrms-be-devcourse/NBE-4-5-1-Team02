package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.service.OrderService;
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

    /*
        관리자 주문 목록 조회
        GET /admin/orders?page=1&size=10
    */

}
