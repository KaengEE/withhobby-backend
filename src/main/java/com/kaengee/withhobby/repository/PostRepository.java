package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Post;
import com.kaengee.withhobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 게시글의 작성자 객체를 가져오는 메서드
    @Query("SELECT p.user FROM Post p WHERE p.id = :postId")
    User findUserByPostId(@Param("postId") Long postId);


    // 게시글 삭제
    @Modifying
    @Query("delete from Post where id = :id")
    void deleteById(@Param("id") Long id);


}
