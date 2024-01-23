package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Post;
import com.kaengee.withhobby.repository.PostRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.CommentService;
import com.kaengee.withhobby.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentService commentService;

    //게시글 작성
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestBody Post post,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple){

        Long userId = userPrinciple.getId();
        //System.out.println(userId);
        postService.createPost(post, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성 성공");
    }

    //게시글 상세내용
    @GetMapping("/{postId}")
    public Optional<Post> getPostById(@PathVariable Long postId,
                                      @AuthenticationPrincipal UserPrinciple userPrinciple) {
        return postService.findById(postId);
    }

    //게시글 수정
    @PutMapping("/update/{postId}")
    public ResponseEntity<Object> updatePost(@RequestBody Post updatedPost,
                                             @PathVariable Long postId,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple){

        // 현재 로그인한 유저 id
        Long userId = userPrinciple.getId();

        // 게시글의 작성자 id 가져오기
        Long postUserId = postRepository.findUserIdByPostId(postId);

//        System.out.println(userId);
//        System.out.println(postUserId);

        // 게시글의 작성자 id와 로그인한 유저 id가 동일하면 수정
        if (postUserId != null && postUserId.equals(userId)) {

            // 수정된 내용으로 게시글 업데이트
            postService.updatePost(postId, updatedPost);

            return ResponseEntity.status(HttpStatus.OK).body("게시글 수정 성공");
        }

        // 게시글이 존재하지 않거나 권한이 없는 경우
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 수정 권한이 없습니다.");
    }

    //게시글삭제
    //수정과 동일하게 id가 동일하면 삭제
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple){
        // 현재 로그인한 유저 id
        Long userId = userPrinciple.getId();

        // 게시글의 작성자 id 가져오기
        Long postUserId = postRepository.findUserIdByPostId(postId);

        // 게시글의 작성자 id와 로그인한 유저 id가 동일하면 삭제
        if (postUserId != null && postUserId.equals(userId)) {

            //댓글 삭제하기
            commentService.deleteCommentsByPostId(postId);
            //게시글 삭제하기
            postService.deletePost(postId);

            return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
        }

        // 게시글이 존재하지 않거나 권한이 없는 경우
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 수정 권한이 없습니다.");
    }

    //전체 게시글 조회
    @GetMapping("/list")
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

}
