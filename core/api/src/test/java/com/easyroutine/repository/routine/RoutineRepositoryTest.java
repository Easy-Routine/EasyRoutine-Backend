package com.easyroutine.repository.routine;


import com.easyroutine.IntegrationTestSupport;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.domain.member.MemberStatus;
import com.easyroutine.domain.routine.Routine;
import com.easyroutine.domain.routine.dto.RoutineDto;
import com.easyroutine.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional
public class RoutineRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Routine 을 동록한다.")
    @Test
    void saveRoutine(){
        //given
        Member member = getMember("google", "1234", "tester");
        Member savedMember = memberRepository.save(member);
        Routine routine = getMockRoutine(savedMember);

        // when
        Routine savedRoutine = routineRepository.save(routine);


        //then
        assertThat(savedRoutine.getId()).isNotNull();
        assertThat(savedRoutine.getName()).isEqualTo("testName");
        assertThat(savedRoutine.getColor()).isEqualTo("blue");
        assertThat(savedRoutine.getMember().getNickname()).isEqualTo("tester");

    }


    private static Routine getMockRoutine(Member member){
        Routine routine = Routine.builder()
                .member(member)
                .name("testName")
                .color("blue")
                .build();

        return routine;
    }

    private static Member getMember(String provider, String providerId, String nickname) {
        return Member.builder()
                .provider(provider)
                .providerId(providerId)
                .nickname(nickname)
                .email("" + nickname + "@example.com")
                .masking_email("" + nickname + "@example.com")
                .role(MemberRole.MEMBER)
                .status(MemberStatus.ACTIVE)
                .build();
    }



}
