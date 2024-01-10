package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    // 카테고리 생성
    public Category createCategory(String categoryName) {
        // 이미 존재하는 카테고리인지 확인
        Optional<Category> existingCategory = categoryRepository.findByCategory(categoryName);
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category with name '" + categoryName + "' already exists.");
        }

        // 존재하지 않는다면 새로운 카테고리 생성
        Category newCategory = new Category();
        newCategory.setCategory(categoryName);

        return categoryRepository.save(newCategory);
    }

    //카테고리찾기(이름에 맞는 id)
    public Optional<Category> findByCategory(String category){
        return categoryRepository.findByCategory(category);
    }
}
