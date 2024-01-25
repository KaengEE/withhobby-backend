package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Post;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostService {

    //게시글 작성
    Post createPost(Post post, Long userId);

    @Transactional
        //게시글 수정
    void updatePost(Long postId, Post post);

    //게시글 삭제
    @Transactional
    void deletePost(Long postId);

    //게시글 조회
    List<Post> findAllPosts();


    //id로 게시글 조회
    Optional<Post> findById(Long postId);

    Post getPostById(Long postId);

    //유저id로 게시글 찾기
    List<Post> getPostsByUserId(Long userId);
}
