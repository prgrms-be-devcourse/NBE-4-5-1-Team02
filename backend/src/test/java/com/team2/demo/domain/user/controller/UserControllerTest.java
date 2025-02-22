package com.team2.demo.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.demo.domain.user.repository.UserRepository;
import com.team2.demo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("유저 생성")
    void addUserTest() throws Exception{
        //초기 데이터
        String jsonRequest = """
            {
              "buyer": {
                "email": "user@example.com"
              }
            }
            """;
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("유저 생성 - 올바르지 않은 이메일 형식")
    void addUserTest2() throws Exception{
        //초기 데이터
        String jsonRequest = """
            {
              "buyer": {
                "email": "user@example...com"
              }
            }
            """;
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}