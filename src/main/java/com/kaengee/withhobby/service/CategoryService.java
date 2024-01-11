package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Category;

import java.util.Optional;

public interface CategoryService {

    // 카테고리 생성
    Category createCategory(String categoryName);

    //카테고리찾기(이름에 맞는 id)
    Optional<Category> findByCategory(String category);
}
