package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 한 개의 글에 여러 개 댓글
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    // 한 명이 여러 개 댓글 작성
    @ManyToOne(fetch = FetchType.EAGER)
    private User user; // 댓글 작성자

    @Column(name = "comment_text", nullable = false, length = 500)
    private String commentText; //댓글내용

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

}
