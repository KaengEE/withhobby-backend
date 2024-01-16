package com.kaengee.withhobby.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user; //작성자id

    @Column(name="post_title", nullable = false, length = 1000)
    private String postTitle; //글 제목

    @Column(name="post_text", nullable = false, length = 10000)
    private String postText; //글 내용

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt; //작성시간

}
