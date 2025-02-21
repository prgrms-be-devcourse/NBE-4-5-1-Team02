package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@ActiveProfiles("test")
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    private void checkOrder(ResultActions resultActions, OrderDto orderDto) throws Exception {
        resultActions
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").value(orderDto.getOrderId()))
                .andExpect(jsonPath("$.data.orderDate").value(orderDto.getOrderDate().toString()))
                .andExpect(jsonPath("$.data.totalPrice").value(orderDto.getTotalPrice()))
                .andExpect(jsonPath("$.data.deliveryStatus").value(orderDto.getDeliveryStatus().toString()))
                .andExpect(jsonPath("$.data.buyerEmail").value(orderDto.getBuyerEmail()));
//                .andExpect(jsonPath("$.data.createdDate").value(matchesPattern(orderDto.getCreatedDate().toString().replaceAll("0+$", "") + ".*")))
//                .andExpect(jsonPath("$.data.modifiedDate").value(matchesPattern(orderDto.getModifiedDate().toString().replaceAll("0+$", "") + ".*")));
    }

    @Test
    @DisplayName("주문 목록 조회")
    void getOrders() throws Exception {

        // 초기 데이터
        String email = "user@example.com";
        int page = 0;
        int size = 10;

        // Mock 요청
        ResultActions resultActions = mvc
                .perform(get("/orders")
                        .param("email", email)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        Page<OrderDto> orderPage = orderService.getOrdersByEmail(new OrderController.OrderForm(email), page, size);
        List<OrderDto> orderList = orderPage.getContent();

        for (int i = 0; i < orderList.size(); i++) {
            checkOrder(resultActions, orderList.get(i)); // 각 주문에 대한 검증 진행
        }
    }

    /*@Test
    @DisplayName("주문 목록 조회 - 검색")
    void getOrdersSearch() throws Exception {
    }*/
}