package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team; //모임 동아리 id

    @Column(name="meeting_at", nullable = false)
    private String meetingAt; //모임 날짜

    @Enumerated(EnumType.STRING)
    @Column(name="meeting_status",nullable = false)
    private MeetingStatus meetingStatus; //모임상태(모집중, 마감)

    @Column(name="create_at", nullable = false)
    private LocalDateTime createAt;
}
