package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql({"classpath:/schema.sql", "classpath:/data.sql"})
class OrderControllerAdminTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    OrderService orderService;

    private void checkOrder(ResultActions resultActions, OrderDto orderDto) throws Exception {
        resultActions
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").value(orderDto.getOrderId()))
                .andExpect(jsonPath("$.data.orderDate").value(orderDto.getOrderDate()))
                .andExpect(jsonPath("$.data.totalPrice").value(orderDto.getTotalPrice()))
                .andExpect(jsonPath("$.data.deliveryStatus").value(orderDto.getDeliveryStatus()))
                .andExpect(jsonPath("$.data.buyerEmail").value(orderDto.getBuyerEmail()))
                .andExpect(jsonPath("$.data.items").value(orderDto.getItems()));
    }

    @Test
    @DisplayName("관리자 주문 목록 조회")
    void getAllOrders() throws Exception {

        // 초기 데이터
        int page = 1;
        int size = 10;
        int maxItems = 2;

        ResultActions resultActions = mvc
                .perform(get("/admin/orders")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("max-products", "2")  // 최대 2개의 상품만 조회
                        .contentType("application/json"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))  // 응답 코드가 200인지 확인
                .andExpect(jsonPath("$.message").value("ok"))  // message 필드가 "ok"인지 확인
                .andExpect(jsonPath("$.data.content.length()").value(5))  // content 배열에 5개의 주문이 있는지 확인
                .andExpect(jsonPath("$.data.page").value(1))  // 페이지 번호가 1인지 확인
                .andExpect(jsonPath("$.data.size").value(10))  // 한 페이지에 포함된 항목 수가 10인지 확인
                .andExpect(jsonPath("$.data.totalPages").value(1));  // 전체 페이지 수가 1인지 확인

        // 각 주문 항목에 대한 검증
        resultActions
                .andExpect(jsonPath("$.data.content[0].orderId").value("order-11111-22222-33335"))
//                .andExpect(jsonPath("$.data.content[0].orderDate").value("2025-02-21T21:56:50.719344"))
                .andExpect(jsonPath("$.data.content[0].totalPrice").value(30000))
                .andExpect(jsonPath("$.data.content[0].deliveryStatus").value("DELIVERED"))
                .andExpect(jsonPath("$.data.content[0].buyerEmail").value("email3@email.com"))
                .andExpect(jsonPath("$.data.content[0].items.length()").value(0));  // 첫 번째 주문의 items 배열 길이 검증

        resultActions
                .andExpect(jsonPath("$.data.content[1].orderId").value("order-11111-22222-33334"))
//                .andExpect(jsonPath("$.data.content[1].orderDate").value("2025-02-21T21:56:50.719344"))
                .andExpect(jsonPath("$.data.content[1].totalPrice").value(20000))
                .andExpect(jsonPath("$.data.content[1].deliveryStatus").value("SHIPPED"))
                .andExpect(jsonPath("$.data.content[1].buyerEmail").value("email2@email.com"))
                .andExpect(jsonPath("$.data.content[1].items.length()").value(0));  // 두 번째 주문의 items 배열 길이 검증

        resultActions
                .andExpect(jsonPath("$.data.content[2].orderId").value("order-11111-22222-33333"))
//                .andExpect(jsonPath("$.data.content[2].orderDate").value("2025-02-21T21:56:50.719344"))
                .andExpect(jsonPath("$.data.content[2].totalPrice").value(10000))
                .andExpect(jsonPath("$.data.content[2].deliveryStatus").value("SHIPPED"))
                .andExpect(jsonPath("$.data.content[2].buyerEmail").value("email2@email.com"))
                .andExpect(jsonPath("$.data.content[2].items.length()").value(0));  // 세 번째 주문의 items 배열 길이 검증

        resultActions
                .andExpect(jsonPath("$.data.content[3].orderId").value("order-11111-22222-33332"))
//                .andExpect(jsonPath("$.data.content[3].orderDate").value("2025-02-21T21:56:50.718345"))
                .andExpect(jsonPath("$.data.content[3].totalPrice").value(20000))
                .andExpect(jsonPath("$.data.content[3].deliveryStatus").value("PENDING"))
                .andExpect(jsonPath("$.data.content[3].buyerEmail").value("email1@email.com"))
                .andExpect(jsonPath("$.data.content[3].items.length()").value(0));  // 네 번째 주문의 items 배열 길이 검증

        resultActions
                .andExpect(jsonPath("$.data.content[4].orderId").value("order-11111-22222-33331"))
//                .andExpect(jsonPath("$.data.content[4].orderDate").value("2025-02-21T21:56:50.718345"))
                .andExpect(jsonPath("$.data.content[4].totalPrice").value(10000))
                .andExpect(jsonPath("$.data.content[4].deliveryStatus").value("PENDING"))
                .andExpect(jsonPath("$.data.content[4].buyerEmail").value("email1@email.com"))
                .andExpect(jsonPath("$.data.content[4].items.length()").value(2))  // 다섯 번째 주문의 items 배열 길이 검증
                .andExpect(jsonPath("$.data.content[4].items[0].name").value("product2"))
                .andExpect(jsonPath("$.data.content[4].items[0].quantity").value(1))  // 첫 번째 상품 검증
                .andExpect(jsonPath("$.data.content[4].items[1].name").value("product1"))
                .andExpect(jsonPath("$.data.content[4].items[1].quantity").value(5));  // 두 번째 상품 검증
    }



}