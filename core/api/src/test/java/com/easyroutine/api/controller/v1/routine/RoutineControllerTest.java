package com.easyroutine.api.controller.v1.routine;

import com.easyroutine.ControllerTestSupport;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;



import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoutineControllerTest extends ControllerTestSupport {

    @Description("루틴을 등록한다. ")
    @WithMockUser(username = "tester", roles = "MEMBER")
    @Test
    void createRoutine() throws Exception{
        CustomOAuth2User customUser = mock(CustomOAuth2User.class);

        when(customUser.getMemberId()).thenReturn("test-id");

        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(customUser, null, "MEMBER");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String requestJson = """
        {
          "routineDto": {
            "name": "상체 루틴",
            "color": "blue",
            "routineExerciseDtoList": [
              {
                "exerciseId": 1,
                "order": 1,
                "setsDtoList": [
                  {
                    "order": 1,
                    "weight": 20.0,
                    "rep": 10,
                    "refreshTime": "01:00"
                  }
                ]
              }
            ]
          }
        }
        """;

        given(routineService.createRoutine(any())).willReturn(1L);

        mockMvc.perform(post("/routines")
                .with(csrf())
                .contentType("application/json")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value(1));
    }


    @Description("루틴을 등록을 실패한다. ")
    @WithMockUser(username = "tester", roles = "MEMBER")
    @Test
    void createRoutineInputError() throws Exception{
        CustomOAuth2User customUser = mock(CustomOAuth2User.class);

        when(customUser.getMemberId()).thenReturn("test-id");

        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(customUser, null, "MEMBER");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String requestJson = """
        {
          "routineDto": {
            "name": "",
            "routineExerciseDtoList": [
              {
                "exerciseId": 1,
                "order": 1,
                "setsDtoList": [
                  {
                    "order": 1,
                    "weight": 20.0,
                    "rep": 10,
                    "refreshTime": "01:00"
                  }
                ]
              }
            ]
          }
        }
        """;

        given(routineService.createRoutine(any())).willReturn(1L);

        mockMvc.perform(post("/routines")
                .with(csrf())
                .contentType("application/json")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("ERR-C001"));
    }



}
