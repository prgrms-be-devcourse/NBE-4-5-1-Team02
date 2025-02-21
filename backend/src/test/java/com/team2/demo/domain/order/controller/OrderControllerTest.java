package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class OrderControllerTest {

    private void checkOrder(ResultActions resultActions, OrderDto orderDto) throws Exception {
        resultActions
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").value(orderDto.getOrderId()))
                .andExpect(jsonPath("$.data.orderDate").value(orderDto.getOrderDate().toString()))
                .andExpect(jsonPath("$.data.totalPrice").value(orderDto.getTotalPrice()))
                .andExpect(jsonPath("$.data.deliveryStatus").value(orderDto.getDeliveryStatus().toString()))
                .andExpect(jsonPath("$.data.buyerEmail").value(orderDto.getBuyerEmail()))
                .andExpect(jsonPath("$.data.createdDate").value(matchesPattern(orderDto.getCreatedDate().toString().replaceAll("0+$", "") + ".*")))
                .andExpect(jsonPath("$.data.modifiedDate").value(matchesPattern(orderDto.getModifiedDate().toString().replaceAll("0+$", "") + ".*")));
    }
}