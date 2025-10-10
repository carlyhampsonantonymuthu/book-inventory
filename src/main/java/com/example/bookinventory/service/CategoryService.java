package com.example.bookinventory.service;

import com.example.bookinventory.entity.Category;

import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category);
    Optional<Category> getCategoryById(Integer catId);
    Category updateDescription(Integer catId, String description);
}