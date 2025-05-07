package com.easyroutine.api.controller.v1.member;

import com.easyroutine.ControllerTestSupport;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    @Description("회원을 삭제한다.")
    @WithMockUser(username ="tester", roles = "MEMBER")
    @Test
    void deleteMember() throws Exception {

        CustomOAuth2User customUser = mock(CustomOAuth2User.class);
        when(customUser.getMemberId()).thenReturn("test-id");

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(customUser, null, "MEMBER");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        given(memberService.deleteMember(anyString()))
                .willReturn("test-id");

        mockMvc.perform(delete("/api/v1/members").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.result").value("test-id"));

    }
}