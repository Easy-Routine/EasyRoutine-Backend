package com.easyroutine.api.controller.v1.routine;

import com.easyroutine.ControllerTestSupport;
import com.easyroutine.domain.routine.dto.RoutineListDto;
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
				  "name": "루틴테스트2",
				  "color": "#000000",
				  "order": 4,
				  "routineExercises": [
				    {
				      "order": 1,
				      "exercise": {
				        "id": 1
				      },
				      "sets": [
				        {
				          "order": 1,
				          "weight": "10",
				          "rep": "10",
				          "exerciseSec": "60",
				          "restSec": "60"
				        }
				      ]
				    }
				  ]
				}
				""";

		given(routineService.createRoutine(any())).willReturn(1L);

		mockMvc.perform(post("/api/v1/routines").with(csrf()).contentType("application/json").content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.result").value(1L));
	}

	@Description("루틴을 등록을 실패한다. ")
	@WithMockUser(username = "tester", roles = "MEMBER")
//	@Test
	void createRoutineInputError() throws Exception {
		CustomOAuth2User customUser = mock(CustomOAuth2User.class);

		when(customUser.getMemberId()).thenReturn("test-id");

		TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(customUser, null, "MEMBER");
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		String requestJson = """
				{
				  "name": "",
				  "color": "#000000",
				  "order": 4,
				  "routineExercises": [
				    {
				      "order": 1,
				      "exercise": {
				        "id": 1
				      },
				      "sets": [
				        {
				          "order": 1,
				          "weight": "10",
				          "rep": "10",
				          "exerciseSec": "10:30",
				          "restSec": "01:30"
				        }
				      ]
				    }
				  ]
				}
				""";

//		given(routineService.createRoutine(any())).willReturn(1L);

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
			.refreshSec(60)
			.build();

		RoutineExerciseDto routineExerciseDto = RoutineExerciseDto.builder()
			.id(1L)
			.routineId(1L)
			.exerciseId(1L)
			.order(1)
			.routineExerciseSetsDtoList(Arrays.asList(routineExerciseSetsDto))
			.build();

		RoutineListDto routineDto = RoutineListDto.builder()
			.id(1L)
			.name("testRoutineName")
			.color("blue")
			.build();

		given(routineService.findAllRoutine(any())).willReturn(Arrays.asList(routineDto));

		mockMvc.perform(get("/api/v1/routines").with(csrf()).contentType("application/json"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true));
	}

}
