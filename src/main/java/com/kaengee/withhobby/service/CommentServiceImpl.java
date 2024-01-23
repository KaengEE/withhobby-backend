package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Comment;
import com.kaengee.withhobby.model.CommentForm;
import com.kaengee.withhobby.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Override
    //댓글 작성
    public Comment CreateComment(CommentForm commentForm, Long postId, Long userId){

        Comment comment = new Comment();
        comment.setCommentText(commentForm.getText()); //댓글
        comment.setUser(userService.getUserById(userId));
        comment.setPost(postService.getPostById(postId));
        comment.setCreateAt(LocalDateTime.now()); //작성시간

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    //댓글수정
    public void updateComment(CommentForm commentForm, Long commentId) {
        commentRepository.updateComment(commentForm.getText(), commentId);
    }

    @Override
    @Transactional
    //댓글삭제
    public void deleteComment(Long commentId){
        commentRepository.deleteComment(commentId);
    }

    @Override
    @Transactional
    // 게시글 ID에 해당하는 댓글 삭제
    public void deleteCommentsByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }

    @Override
    //게시글별 댓글조회
    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Optional<Comment> findById(Long commentId){
        return  commentRepository.findById(commentId);
    }
}
