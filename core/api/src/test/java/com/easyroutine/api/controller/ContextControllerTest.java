package com.easyroutine.api.controller;

import com.easyroutine.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ContextControllerTest extends ControllerTestSupport {

    @Test
    @WithMockUser(username ="tester", roles = "MEMBER")
    @DisplayName("GET /context should return a context status message")
    void contextShouldReturnExpectedMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/context"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.result").value("Context is working!"));
    }
}