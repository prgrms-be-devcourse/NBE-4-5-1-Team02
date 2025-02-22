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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private MockMvc mockMvc;

    /*@Autowired
    private UserService userService;*/

    private void checkOrder(ResultActions resultActions, OrderDto orderDto) throws Exception {
        resultActions
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderId").value(orderDto.getOrderId()))
                .andExpect(jsonPath("$.data.orderDate").value(orderDto.getOrderDate().toString()))
                .andExpect(jsonPath("$.data.totalPrice").value(orderDto.getTotalPrice()))
                .andExpect(jsonPath("$.data.deliveryStatus").value(orderDto.getDeliveryStatus().toString()))
                .andExpect(jsonPath("$.data.buyerEmail").value(orderDto.getBuyerEmail()))
                .andExpect(jsonPath("$.data.deliveryAddress").value(orderDto.getAddress()));
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

        Page<OrderDto> orderPage = orderService.getOrdersByEmail(email, page, size);
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

        ResultActions resultActions = mvc
                .perform(get("/orders")
                        .param("email", email)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())  // HTTP 400 검증 추가
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("getOrders.email: must be a well-formed email address, getOrders.email: 올바른 이메일 형식이어야 합니다."));
        //  getOrders.email: must be a well-formed email address, getOrders.email: 올바른 이메일 형식이어야 합니다.
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
                .andExpect(jsonPath("$.message").value("Page index must not be less than zero"));
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

    @Test
    @DisplayName("주문 생성")
    void paymentTest() throws Exception{
        //초기 데이터
        String jsonRequest = """
                {
                      "address": "addr1",
                      "zipcode": 1073,
                      "items": [
                        {
                          "productId": "product-11111-22222-33331",
                          "quantity": 3
                        }
                      ],
                      "buyer": {
                        "email": "email1@email.com"
                      }
                    }
            """;
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("주문 생성 - 제품 id 찾지 못함.")
    void paymentTest2() throws Exception{
        //초기 데이터
        String jsonRequest = """
                {
                      "address": "addr1",
                      "zipcode": 1073,
                      "items": [
                        {
                          "productId": "product-11111-22222",
                          "quantity": 3
                        }
                      ],
                      "buyer": {
                        "email": "email1@email.com"
                      }
                    }
            """;
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("주문 생성 - 제품 id 찾지 못함.")
    void paymentTest3() throws Exception{
        //초기 데이터
        String jsonRequest = """
                {
                      "address": "addr1",
                      "zipcode": 1073,
                      "items": [
                        {
                          "productId": "product-11111-22222-33331",
                          "quantity": 3
                        }
                      ],
                      "buyer": {
                        "email": "email1@email..com"
                      }
                    }
            """;
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("주문 수정")
    public void updateOrder() throws Exception {
        String orderId = "order-11111-22222-33331";
        String email = "email1@email.com";
        String requestJson = String.format("""
            {
                "address": "updated addr",
                "zipcode": 456456,
                "items": [
                    {"productId": "product-11111-22222-33331", "quantity": 3},
                    {"productId": "product-11111-22222-33332", "quantity": 2}
                ],
                "buyer": {
                    "email": "%s"
                }
            }
            """, email);

        mvc.perform(put("/orders/" + orderId)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data.orderId").value(orderId))
                .andDo(print());
    }

    @Test
    @DisplayName("주문 수정 - 배송 중, 배송 완료 수정 불가")
    public void updateOrder2() throws Exception {
        String orderId = "order-11111-22222-33333";
        String email = "email2@email.com";
        String requestJson = String.format("""
            {
                "address": "updated addr for shipped order",
                "zipcode": 789789,
                "items": [
                    {"productId": "product-11111-22222-33331", "quantity": 1}
                ],
                "buyer": {
                    "email": "%s"
                }
            }
            """, email);

        mvc.perform(put("/orders/" + orderId)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("배송 중이거나 배송 완료한 주문은 수정할 수 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("주문 수정 - 주문 상품 모두 삭제로 주문 취소")
    public void updateOrder3() throws Exception {
        String orderId = "order-11111-22222-33332";
        String email = "email1@email.com";
        String requestJson = String.format("""
            {
                "address": "updated addr for empty items",
                "zipcode": 999999,
                "items": [],
                "buyer": {
                    "email": "%s"
                }
            }
            """, email);

        mvc.perform(put("/orders/" + orderId)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("주문에 상품이 하나도 없어 주문이 취소되었습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("주문 취소")
    public void cancelOrder() throws Exception {
        String orderId = "order-11111-22222-33331";
        String email = "email1@email.com";

        mvc.perform(delete("/orders/" + orderId)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("주문이 성공적으로 취소되었습니다."))
                .andExpect(jsonPath("$.data.orderId").value(orderId))
                .andDo(print());
    }

    @Test
    @DisplayName("주문 취소 - 다른 사람의 주문 취소 불가")
    public void cancelOrder2() throws Exception {
        String orderId = "order-11111-22222-33331";
        String email = "email2@email.com";

        mvc.perform(delete("/orders/" + orderId)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("주문 취소 권한이 없습니다."))
                .andDo(print());
    }
}