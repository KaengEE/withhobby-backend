package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.repository.CategoryRepository;
import com.kaengee.withhobby.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final TeamRepository teamRepository;

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

    @Override
    //카테고리찾기(이름에 맞는 id)
    public Optional<Category> findByCategory(String category) {
        return categoryRepository.findByCategory(category);
    }

    @Transactional
    @Override
    //카테고리 수정
    public void updateCategory(String category, Long id) {
        categoryRepository.updateCategory(category, id);
    }

    @Transactional
    @Override
    // 카테고리 생성 또는 이미 존재하는 카테고리 반환
    public Category createOrGetExistingCategory(String categoryName) {
        // 이미 존재하는 카테고리인지 확인
        Optional<Category> existingCategory = categoryRepository.findByCategory(categoryName);
        if (existingCategory.isPresent()) {
            return existingCategory.get(); // 이미 존재하는 경우 해당 카테고리 반환
        } else {
            // 존재하지 않는다면 새로운 카테고리 생성
            Category newCategory = new Category();
            newCategory.setCategory(categoryName);

            return categoryRepository.save(newCategory);
        }
    }

    @Transactional
    @Override
    //카테고리 삭제
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategoryToDelete = categoryRepository.findById(id);

        if (optionalCategoryToDelete.isPresent()) {
            Category categoryToDelete = optionalCategoryToDelete.get();

            // 기타 카테고리 가져오거나 생성
            Category etcCategory = createOrGetExistingCategory("기타");

            // 팀들의 카테고리를 기타 카테고리로 변경
            List<Team> teamsToUpdate = teamRepository.findByCategory(categoryToDelete);
            for (Team team : teamsToUpdate) {
                team.setCategory(etcCategory);
            }

            categoryRepository.deleteCategory(id);
        } else {
            // 해당 ID에 해당하는 카테고리가 없을 경우 예외 처리 또는 메시지 출력
            throw new EntityNotFoundException("카테고리를 찾을 수 없습니다. " + id);
        }
    }
}
