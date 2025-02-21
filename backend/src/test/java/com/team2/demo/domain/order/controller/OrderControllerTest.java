package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"classpath:/schema.sql", "classpath:/data.sql"})
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderService orderService;

    /*@Autowired
    private UserService userService;*/

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

    @Test // 실패
    @DisplayName("주문 목록 조회 - 잘못된 이메일 형식")
    void getOrders_noUser() throws Exception {

        String email = "user@example.com..com";
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

        resultActions
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("올바른 이메일 형식이어야 합니다."));
    }

    @Test
    @DisplayName("주문 목록 조회 - 잘못된 페이지 번호")
    void getOrders_invalidPage() throws Exception {

        // 잘못된 페이지 번호
        String email = "user@example.com";
        int page = -1;  // 유효하지 않은 페이지 번호
        int size = 10;

        // Mock 요청
        ResultActions resultActions = mvc
                .perform(get("/orders")
                        .param("email", email)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // 예상 응답: 400 Bad Request, 잘못된 페이지 번호 오류
        resultActions
                .andExpect(status().isBadRequest())  // BadRequest 400
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid page number"));
    }

    @Test
    @DisplayName("주문 목록 조회 - 존재하지 않는 주문")
    void getOrders_noOrders() throws Exception {

        // 존재하지 않는 주문
        String email = "user@example.com";  // 실제 존재하는 이메일을 사용하지만, 주문이 없는 경우
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

        // 예상 응답: 빈 주문 리스트 반환 (content.length == 0)
        resultActions
                .andExpect(jsonPath("$.data.content.length()").value(0))  // 빈 주문 목록
                .andExpect(jsonPath("$.code").value(200))  // 응답 코드 200
                .andExpect(jsonPath("$.message").value("ok"));
    }


    /*@Test
    @DisplayName("주문 목록 조회 - 검색")
    void getOrdersSearch() throws Exception {
    }*/
}