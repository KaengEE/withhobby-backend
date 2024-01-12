package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory(String categoryName);

    //카테고리 수정
    @Modifying
    @Query("update Category set category = :category where id = :id")
    void updateCategory(@Param("category") String category,
                        @Param("id") Long id);
    //카테고리 삭제
    @Modifying
    @Query("delete from Category where id = :categoryId")
    void deleteCategory(@Param("categoryId") Long categoryId);



}
