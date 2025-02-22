package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.service.ProductKeywordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("모든 상품 조회")
    void getAllProducts() throws Exception {
        ResultActions result = mockMvc.perform(get("/products"));

        result
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getProductList"))
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(jsonPath("$.message").value("Success."))
                .andExpect(jsonPath("$.data.data[0].productUuid").value("product-11111-22222-33331"))
                .andExpect(jsonPath("$.data.data[1].productUuid").value("product-11111-22222-33332"));
    }

    @Test
    @DisplayName("모든 상품 product name으로 검색")
    void getAllProductsByTitle() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/products?keyword-type=%s&keyword=%s"
                        .formatted("title", "product2")));

        result.andExpect(status().isOk())
                .andExpect(handler().methodName("getProductList"))
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(jsonPath("$.message").value("Success."))
                .andExpect(jsonPath("$.data.data[0].productUuid").value("product-11111-22222-33332"))
                .andDo(print())
        ;
    }
    @Test
    @DisplayName("모든 상품 product category로 검색")
    void getAllProductsByCategory() throws Exception {

        ResultActions result = mockMvc.perform(
                get("/products?keyword-type=%s&keyword=%s"
                        .formatted(ProductKeywordType.CATEGORY.getTypeName(), "coffeeBean")));

        result.andExpect(status().isOk())
                .andExpect(handler().methodName("getProductList"))
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(jsonPath("$.message").value("Success."))
                .andExpect(jsonPath("$.data.data").exists())
                .andExpect(jsonPath("$.data.data[0].productUuid").value("product-11111-22222-33331"))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("지원하지 않는 keyword type")
    void getAllProductsByUnSupportedKeywordType() throws Exception {

        ResultActions result = mockMvc.perform(
                get("/products?keyword-type=%s&keyword=%s"
                        .formatted("wrong-type", "coffeeBean")));

        result.andExpect(status().isBadRequest())
                .andExpect(handler().methodName("getProductList"))
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(jsonPath("$.message").value("지원하지 않는 검색 키워드 타입입니다."));
    }
}