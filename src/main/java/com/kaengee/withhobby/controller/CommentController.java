package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Comment;
import com.kaengee.withhobby.model.CommentForm;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

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
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성 성공");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 작성 실패");
        }
    }


    //댓글 수정
    @PutMapping("/update/{commentId}")
    public ResponseEntity<Object> updateComment(@RequestBody CommentForm commentForm,
                                                @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserPrinciple userPrinciple){

        //유저 이름으로 id 찾기
        String username = userPrinciple.getUsername();
        Optional<User> user = userService.findByUsername(username);

        //commentForm의 comment id로 userId 찾기
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isPresent()) {
            //작성자 userId
            Long writerId = comment.get().getUser().getId();

            // 작성자 유저와 로그인 유저가 같아야 수정 가능
            if(user.isPresent() && writerId.equals(user.get().getId())) {

                commentForm.setText(commentForm.getText());
                commentService.updateComment(commentForm, commentId);
                return ResponseEntity.status(HttpStatus.CREATED).body("댓글 수정 성공");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 수정 실패");
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("유저를 찾을 수 없음");
        }
    }
}
