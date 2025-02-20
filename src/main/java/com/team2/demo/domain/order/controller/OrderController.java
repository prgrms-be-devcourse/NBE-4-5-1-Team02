package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public RsData<OrderDto> payment(@RequestBody Order body){

        try{
            Order order = orderService.payment(body);
            OrderDto orderDto = OrderDto.of(order);
            return RsData.success(orderDto);
        }catch (Exception e){
            return RsData.badRequest("결제 실패:" +e.getMessage());
        }
    }
}
