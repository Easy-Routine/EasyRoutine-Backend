package com.easyroutine;

import com.easyroutine.api.controller.ContextController;
import com.easyroutine.api.controller.v1.member.MemberController;
import com.easyroutine.api.controller.v1.routine.RoutineController;
import com.easyroutine.api.security.jwt.JsonWebTokenUtil;
import com.easyroutine.domain.member.MemberService;
import com.easyroutine.domain.routine.RoutineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ContextController.class, MemberController.class, RoutineController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected RoutineService routineService;

    @MockBean
    private JsonWebTokenUtil jsonWebTokenUtil;
}

