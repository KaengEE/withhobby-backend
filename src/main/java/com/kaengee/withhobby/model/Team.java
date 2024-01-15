package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="teamname", nullable = false, unique = true, length = 100)
    private String teamname; //동아리명

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "team_host", referencedColumnName = "username", insertable = false, updatable = false)
    private User teamHost; // 동아리 주인

    @Column(name="team_title", nullable=false)
    private String teamTitle; //동아리 설명

    @Column(name="team_img")
    private String teamImg; //동아리 사진

    //한개의 team은 한개의 category를 가짐
    @OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name="category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category; //카테고리

    @Column(name="create_at", nullable = false)
    private LocalDateTime createAt; //생성날짜

}
