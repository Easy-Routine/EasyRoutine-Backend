package com.easyroutine.domain.routine;

import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.routine.dto.RoutineDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "routines")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column
    private String color;


    public static Routine of(Long id){
        return Routine.builder()
                .id(id)
                .build();
    }
}
