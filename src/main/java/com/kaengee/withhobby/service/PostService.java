package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Post;

public interface PostService {

    //게시글 작성
    Post createPost(Post post, Long userId);
}
