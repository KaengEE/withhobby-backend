package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Comment;
import com.kaengee.withhobby.model.CommentForm;
import jakarta.transaction.Transactional;

public interface CommentService {
    //댓글 작성
    Comment CreateComment(CommentForm commentForm, Long postId, Long userId);

    @Transactional
    //댓글수정
    void updateComment(CommentForm commentForm, Long commentId);
}
