package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Category;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    //카테고리 전체조회
    List<Category> categoryList();

    // 카테고리 생성
    Category createCategory(String categoryName);

    //카테고리찾기(이름에 맞는 id)
    Optional<Category> findByCategory(String category);

    @Transactional
        //카테고리 수정
    void updateCategory(String category, Long id);

    // 카테고리 생성 또는 이미 존재하는 카테고리 반환
    Category createOrGetExistingCategory(String categoryName);

    //카테고리 찾기
    Category getCategoryById(Long id);

    @Transactional
        // 카테고리 삭제
    void deleteCategory(Long categoryId);
}
