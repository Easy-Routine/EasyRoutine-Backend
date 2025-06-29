package com.easyroutine.api.controller.v1.routine;

import com.easyroutine.ControllerTestSupport;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.domain.routine_exercise.dto.RoutineExerciseDto;
import com.easyroutine.domain.routine_exercise_sets.dto.RoutineExerciseSetsDto;
import com.easyroutine.infrastructure.oauth.CustomOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoutineControllerTest extends ControllerTestSupport {

	@Description("루틴을 등록한다. ")
	@WithMockUser(username = "tester", roles = "MEMBER")
	@Test
	void createRoutine() throws Exception {
		CustomOAuth2User customUser = mock(CustomOAuth2User.class);

		when(customUser.getMemberId()).thenReturn("test-id");

		TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(customUser, null, "MEMBER");
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		String requestJson = """
				{
				  "routineDto": {
				    "name": "상체 루틴",
				    "color": "blue",
				    "order": 1,
				    "routineExerciseDtoList": [
				      {
				        "exerciseId": 1,
				        "order": 1,
				        "setsDtoList": [
				          {
				            "order": 1,
				            "weight": 20.0,
				            "rep": 10,
				            "restSec": "01:00"
				          }
				        ]
				      }
				    ]
				  }
				}
				""";

		given(routineService.createRoutine(any())).willReturn(1L);

		mockMvc.perform(post("/api/v1/routines").with(csrf()).contentType("application/json").content(requestJson))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.result").value(1));
	}

	@Description("루틴을 등록을 실패한다. ")
	@WithMockUser(username = "tester", roles = "MEMBER")
	@Test
	void createRoutineInputError() throws Exception {
		CustomOAuth2User customUser = mock(CustomOAuth2User.class);

		when(customUser.getMemberId()).thenReturn("test-id");

		TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(customUser, null, "MEMBER");
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		String requestJson = """
				{
				  "routineDto": {
				    "name": "",
				    "order": 1,
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

		mockMvc.perform(post("/api/v1/routines").with(csrf()).contentType("application/json").content(requestJson))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.code").value("ERR-C001"));
	}

	@Description("루틴을 등록한다. ")
	@WithMockUser(username = "tester", roles = "MEMBER")
	@Test
	void findAllRoutine() throws Exception {
		CustomOAuth2User customUser = mock(CustomOAuth2User.class);

		when(customUser.getMemberId()).thenReturn("test-id");
		TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(customUser, null, "MEMBER");
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		RoutineExerciseSetsDto routineExerciseSetsDto = RoutineExerciseSetsDto.builder()
			.id(1L)
			.routineExerciesId(1L)
			.order(1)
			.weight(100.0)
			.rep(10)
			.restSec("1:30")
			.build();

		RoutineExerciseDto routineExerciseDto = RoutineExerciseDto.builder()
			.id(1L)
			.routineId(1L)
			.exerciseId(1L)
			.order(1)
			.setsDtoList(Arrays.asList(routineExerciseSetsDto))
			.build();

		RoutineDto routineDto = RoutineDto.builder()
			.id(1L)
			.memberId("test-id")
			.name("testRoutineName")
			.color("blue")
			.routineExerciseDtoList(Arrays.asList(routineExerciseDto))
			.build();

		given(routineService.findAllRoutine(any())).willReturn(Arrays.asList(routineDto));

		mockMvc.perform(get("/api/v1/routines").with(csrf()).contentType("application/json"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

}
