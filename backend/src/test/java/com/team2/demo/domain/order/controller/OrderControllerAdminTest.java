package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.repository.OrderRepository;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    OrderRepository orderRepository;
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

    @Test // 성공
    @DisplayName("관리자 주문 목록 조회")
    void getAllOrders() throws Exception {

        // 초기 데이터
        int page = 1;
        int size = 10;

        ResultActions resultActions = mvc
                .perform(get("/admin/orders")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("max-products", "2")  // 최대 2개의 상품만 조회
                        .contentType("application/json"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data.content.length()").value(5))
                .andExpect(jsonPath("$.data.page").value(1))
                .andExpect(jsonPath("$.data.size").value(10))
                .andExpect(jsonPath("$.data.totalPages").value(1));

        // 각 주문 항목에 대한 검증
        resultActions
                .andExpect(jsonPath("$.data.content[0].orderId").value("order-11111-22222-33335"))
//                .andExpect(jsonPath("$.data.content[0].orderDate").value("2025-02-21T21:56:50.719344"))
                .andExpect(jsonPath("$.data.content[0].totalPrice").value(30000))
                .andExpect(jsonPath("$.data.content[0].deliveryStatus").value("DELIVERED"))
                .andExpect(jsonPath("$.data.content[0].buyerEmail").value("email3@email.com"))
                .andExpect(jsonPath("$.data.content[0].items.length()").value(0));

        resultActions
                .andExpect(jsonPath("$.data.content[1].orderId").value("order-11111-22222-33334"))
//                .andExpect(jsonPath("$.data.content[1].orderDate").value("2025-02-21T21:56:50.719344"))
                .andExpect(jsonPath("$.data.content[1].totalPrice").value(20000))
                .andExpect(jsonPath("$.data.content[1].deliveryStatus").value("SHIPPED"))
                .andExpect(jsonPath("$.data.content[1].buyerEmail").value("email2@email.com"))
                .andExpect(jsonPath("$.data.content[1].items.length()").value(0));

        resultActions
                .andExpect(jsonPath("$.data.content[2].orderId").value("order-11111-22222-33333"))
//                .andExpect(jsonPath("$.data.content[2].orderDate").value("2025-02-21T21:56:50.719344"))
                .andExpect(jsonPath("$.data.content[2].totalPrice").value(10000))
                .andExpect(jsonPath("$.data.content[2].deliveryStatus").value("SHIPPED"))
                .andExpect(jsonPath("$.data.content[2].buyerEmail").value("email2@email.com"))
                .andExpect(jsonPath("$.data.content[2].items.length()").value(0));

        resultActions
                .andExpect(jsonPath("$.data.content[3].orderId").value("order-11111-22222-33332"))
//                .andExpect(jsonPath("$.data.content[3].orderDate").value("2025-02-21T21:56:50.718345"))
                .andExpect(jsonPath("$.data.content[3].totalPrice").value(20000))
                .andExpect(jsonPath("$.data.content[3].deliveryStatus").value("PENDING"))
                .andExpect(jsonPath("$.data.content[3].buyerEmail").value("email1@email.com"))
                .andExpect(jsonPath("$.data.content[3].items.length()").value(0));

        resultActions
                .andExpect(jsonPath("$.data.content[4].orderId").value("order-11111-22222-33331"))
//                .andExpect(jsonPath("$.data.content[4].orderDate").value("2025-02-21T21:56:50.718345"))
                .andExpect(jsonPath("$.data.content[4].totalPrice").value(10000))
                .andExpect(jsonPath("$.data.content[4].deliveryStatus").value("PENDING"))
                .andExpect(jsonPath("$.data.content[4].buyerEmail").value("email1@email.com"))
                .andExpect(jsonPath("$.data.content[4].items.length()").value(2))
                .andExpect(jsonPath("$.data.content[4].items[0].name").value("product2"))
                .andExpect(jsonPath("$.data.content[4].items[0].quantity").value(1))
                .andExpect(jsonPath("$.data.content[4].items[1].name").value("product1"))
                .andExpect(jsonPath("$.data.content[4].items[1].quantity").value(5));
    }

    @Test // 실패
    @DisplayName("관리자 주문 목록 조회 - 주문 없는 경우")
    @Transactional
    void getAllOrders_fail1() throws Exception {
        // 주문이 없을 경우를 테스트
        int page = 1;
        int size = 10;

        // 초기화를 수행
        orderRepository.deleteAll();

        ResultActions resultActions = mvc
                .perform(get("/admin/orders")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("max-products", "2")
                        .contentType("application/json"))
                .andDo(print());

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("주문이 없습니다."));
//                .andExpect(jsonPath("$.data.content.length()").value(0)); // 주문이 없으면 0이어야 하지만, 주문이 있을 경우 실패
    }


}