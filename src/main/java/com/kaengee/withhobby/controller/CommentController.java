package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.CommentForm;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.CommentService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    //댓글작성
    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComment(@RequestBody CommentForm commentForm,
                                                 @PathVariable Long postId,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple) {

        //유저 이름으로 id 찾기
        String username = userPrinciple.getUsername();
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            //댓글 저장
            commentService.CreateComment(commentForm, postId, user.get().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성 성공");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성 실패");
        }
    }
}
