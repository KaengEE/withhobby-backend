package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username; //유저id (고유값)

    @Column(name = "name", nullable = false, length = 100)
    private String name; //유저이름

    @Column(name = "password", nullable = false)
    private String password; //비밀번호

    @Column(name = "user_profile", nullable = true)
    private String userProfile; //유저프로필

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; //유저 권한

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private Status userStatus; //유저상태

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createTime; //가입날짜

    @Transient
    private String token; //DB에 저장되지 않음
    
}
