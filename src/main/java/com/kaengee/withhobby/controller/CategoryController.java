package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 추가
    @PostMapping("create")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category.getCategory());
        return ResponseEntity.ok(true);
    }

    //카테고리 수정(이름 변경)

    //카테고리 삭제

    //카테고리 이동(TEAM 이동)

}
