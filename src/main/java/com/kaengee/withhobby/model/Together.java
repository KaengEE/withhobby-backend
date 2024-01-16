package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Together {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team; //팀 id

    @Column(name = "title", nullable = false)
    private String title; //모임 제목

    @Column(name = "location", nullable = false)
    private String location; //모임 장소

    @Column(name = "together_dep")
    private String togetherDep; //모임설명

    @Column(name = "date", nullable = false)
    private LocalDate date; //모임날짜
}
