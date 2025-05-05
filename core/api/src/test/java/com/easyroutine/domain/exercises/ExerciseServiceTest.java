package com.easyroutine.domain.exercises;

import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.member.MemberService;
import com.easyroutine.repository.exercises.ExercisesRepository;
import com.easyroutine.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class ExerciseServiceTest extends IntegrationTestSupport {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExercisesRepository exerciseRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
}