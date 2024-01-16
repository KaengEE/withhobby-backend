package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Comment;
import com.kaengee.withhobby.model.CommentForm;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CommentService {
    //댓글 작성
    Comment CreateComment(CommentForm commentForm, Long postId, Long userId);

    @Transactional
    //댓글수정
    void updateComment(CommentForm commentForm, Long commentId);

    @Transactional
    //댓글삭제
    void deleteComment(Long commentId);

    //게시글별 댓글조회
    List<Comment> findCommentsByPostId(Long postId);

    //특정게시글의 댓글 모두 삭제
}
