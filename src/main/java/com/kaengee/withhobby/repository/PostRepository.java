package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Post;
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


    // 게시글 삭제
    @Modifying
    @Query("delete from Post where id = :id")
    void deleteById(@Param("id") Long id);


}
