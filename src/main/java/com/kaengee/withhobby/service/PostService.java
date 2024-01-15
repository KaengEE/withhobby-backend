package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Post;
import jakarta.transaction.Transactional;

public interface PostService {

    //게시글 작성
    Post createPost(Post post, Long userId);

    @Transactional
        //게시글 수정
    void updatePost(Long postId, Post post);

    //게시글 삭제
    @Transactional
    void deletePost(Long postId);
}
