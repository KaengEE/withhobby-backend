package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Comment;
import com.kaengee.withhobby.model.CommentForm;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    //댓글 작성
    Comment CreateComment(CommentForm commentForm, Long postId, Long userId);

    @Transactional
    //댓글수정
    void updateComment(CommentForm commentForm, Long commentId);

    @Transactional
    //댓글삭제
    void deleteComment(Long commentId);

    @Transactional
    // 게시글 ID에 해당하는 댓글 삭제
    void deleteCommentsByPostId(Long postId);

    //게시글별 댓글조회
    List<Comment> findCommentsByPostId(Long postId);

    Optional<Comment> findById(Long commentId);

    //특정게시글의 댓글 모두 삭제
}
