package com.team2.demo.domain.order.controller;

import com.team2.demo.domain.product.controller.ProductController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerAdminTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("주문 내 모든 상품 조회 성공")
    void getOrderInfoTest() throws Exception {

        ResultActions perform = mockMvc.perform(get("/admin/orders/%s/products".formatted("order-11111-22222-33331")));

        perform
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("getProductsInOrder"))
                .andExpect(jsonPath("$.message").value("Success."))
                .andExpect(jsonPath("$.data.data").exists())
                .andExpect(jsonPath("$.data.data[0].productUuid").value("product-11111-22222-33331"));
    }
    @Test
    @DisplayName("존재하지 않는 주문 선택시 에러")
    void getOrderInfoTest2() throws Exception {

        ResultActions perform = mockMvc.perform(get("/admin/orders/%s/products".formatted("order-notExist")));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("getProductsInOrder"))
                .andExpect(jsonPath("$.message")
                        .value("id가 %s인 Order는 없습니다.".formatted("order-notExist")));
    }
}