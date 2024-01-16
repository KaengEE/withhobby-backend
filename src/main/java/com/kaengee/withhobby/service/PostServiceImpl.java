package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Post;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.PostRepository;
import com.kaengee.withhobby.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    @Override
    //게시글 수정
    public void updatePost(Long postId, Post post) {
        // 게시글 수정
        post.setPostTitle(post.getPostTitle());
        post.setPostText(post.getPostText());

        // 수정된 게시글 저장
        postRepository.updatePost(post.getPostTitle(), post.getPostText(), postId);
    }

    @Transactional
    @Override
    //게시글 삭제
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

    @Override
    //게시글 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }


    //게시글id로 게시글 찾기
    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글ID를 찾을 수 없음: " + postId));
    }
}
