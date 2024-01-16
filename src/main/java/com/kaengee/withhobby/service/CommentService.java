package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Comment;
import com.kaengee.withhobby.model.CommentForm;

public interface CommentService {
    //댓글 작성
    Comment CreateComment(CommentForm commentForm, Long postId, Long userId);
}
