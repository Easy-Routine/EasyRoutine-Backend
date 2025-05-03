package com.easyroutine.domain.exercises;


import com.easyroutine.domain.BaseEntity;
import com.easyroutine.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "exercises")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "register_id", nullable = false)
    private Member member;

    @Column
    private String image;

    @Column(name = "origin_image")
    private String originImage;

    @Column(length = 50)
    private String category;


    @Column(name = "is_editable")
    private int isEditable;

    @Column(name = "share_level")
    private int shareLevel;

    public static Exercise of(Long id){
        return Exercise.builder()
                .id(id)
                .build();
    }

    public static Exercise of(Member member){
        return Exercise.builder()
                .member(member)
                .build();
    }


}
