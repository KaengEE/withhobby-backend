package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody Category category) {
        categoryService.updateCategory(category.getCategory(), id);
    }

    //카테고리 삭제 및 "기타"로 이동
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    //카테고리 조회(출력)
    @GetMapping("/list")
    public List<Category> categoryList(){
        return categoryService.categoryList();
    }

    //카테고리 이름 조회
    @GetMapping("/name/{id}")
    public String findCategoryById(@PathVariable Long id){
        return categoryService.findCategoryById(id);
    }

    //카테고리 id 찾기
    @GetMapping("/{name}")
    public Long findIdByName(@PathVariable String name){
        Optional<Category> category = categoryService.findByCategory(name);
        if(category.isPresent()){
        Category selectCategory = category.get();

        return selectCategory.getId();
        }else {
            return null;
        }
    }

}
