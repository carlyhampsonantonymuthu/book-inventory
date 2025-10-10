package com.example.bookinventory.service.impl;


import com.example.bookinventory.entity.Category;
import com.example.bookinventory.repository.CategoryRepository;
import com.example.bookinventory.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public java.util.Optional<Category> getCategoryById(Integer catId) {
        return categoryRepository.findById(catId);
    }

    @Override
    public Category updateDescription(Integer catId, String description) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setCatDescription(description);
        return categoryRepository.save(category);
    }
}
