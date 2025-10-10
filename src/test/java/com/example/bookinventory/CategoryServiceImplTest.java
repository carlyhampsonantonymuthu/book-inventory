package com.example.bookinventory;

import com.example.bookinventory.entity.Category;
import com.example.bookinventory.repository.CategoryRepository;
import com.example.bookinventory.service.impl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryRepository categoryRepository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void testAddCategory() {
        Category category = new Category();
        category.setCatId(1);
        category.setCatDescription("Fiction");

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.addCategory(category);

        assertNotNull(result);
        assertEquals("Fiction", result.getCatDescription());
        verify(categoryRepository).save(category);
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setCatId(2);
        category.setCatDescription("Science");

        when(categoryRepository.findById(2)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(2);

        assertTrue(result.isPresent());
        assertEquals("Science", result.get().getCatDescription());
        verify(categoryRepository).findById(2);
    }

    @Test
    void testUpdateDescription() {
        Category category = new Category();
        category.setCatId(3);
        category.setCatDescription("Old Description");

        when(categoryRepository.findById(3)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category updated = categoryService.updateDescription(3, "New Description");

        assertEquals("New Description", updated.getCatDescription());
        verify(categoryRepository).save(category);
    }

    @Test
    void testUpdateDescriptionThrowsException() {
        when(categoryRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                categoryService.updateDescription(99, "Doesn't Matter"));

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, never()).save(any());
    }
}

