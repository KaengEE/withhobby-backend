package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Post;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestBody Post post,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple){

        Long userId = userPrinciple.getId();
        //System.out.println(userId);
        postService.createPost(post, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성 성공");
    }
}
