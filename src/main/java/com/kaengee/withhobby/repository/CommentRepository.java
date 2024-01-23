package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //수정
    @Modifying
    @Query("update Comment set commentText = :commentText where id = :id")
    void updateComment(@Param("commentText") String commentText,
                       @Param("id") Long id);

    //삭제
    @Modifying
    @Query("delete from Comment where id = :id")
    void deleteComment(@Param("id") Long id);

    //postid로 해당 댓글 삭제
    @Modifying
    void deleteByPostId(Long postId);

    //게시글별 댓글 조회
    List<Comment> findByPostId(Long postId);

    //id로 comment객체가져오기
    Optional<Comment> findById(Long commentId);
}
