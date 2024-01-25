package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {


    //게시글 수정
    @Modifying
    @Query("update Post set postTitle = :postTitle, postText = :postText " +
            "where id = :id")
    void updatePost(@Param("postTitle") String postTitle,
                    @Param("postText") String postText,
                    @Param("id") Long id);

    //작성자 id 찾기
    @Query("SELECT p.user.id FROM Post p WHERE p.id = :postId")
    Long findUserIdByPostId(@Param("postId") Long postId);

    // 게시글 삭제
    @Modifying
    @Query("delete from Post where id = :id")
    void deleteById(@Param("id") Long id);

    //전체 게시글 조회
    List<Post> findAll();

    //id로 게시글 조회
    Optional<Post> findById(Long postId);

    //userId로 게시글 리스트 조회
    List<Post> findPostsByUserId(Long userId);

}
