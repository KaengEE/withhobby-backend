package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Post;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.PostRepository;
import com.kaengee.withhobby.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    //게시글 작성
    public Post createPost(Post post, Long userId){
        // userId를 사용하여 User 엔터티 조회
        User user = userRepository.findById(userId).orElse(null);

        post.setUser(user);
        post.setPostTitle(post.getPostTitle());
        post.setPostText(post.getPostText());
        post.setCreateAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    //게시글 수정

    //게시글 삭제

    //게시글 조회
}
