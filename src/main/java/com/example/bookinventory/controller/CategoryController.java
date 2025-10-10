package com.example.bookinventory.controller;

import com.example.bookinventory.entity.Category;
import com.example.bookinventory.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Endpoints:
 * POST /api/category/post
 * GET  /api/category/update/description/{catId}
 * PUT  /api/category/{catId}
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Add new category
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addCategory(@RequestBody Category category) {
        try {
            Category saved = categoryService.addCategory(category);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Category added successfully",
                    "catId", String.valueOf(saved.getCatId())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (RuntimeException ex) {
            Map<String, String> body = Map.of(
                    "code", "ADDFAILS",
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }

    // Get category by ID
    @GetMapping("/update/description/{catId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer catId) {
        return categoryService.getCategoryById(catId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "Category not found")));
    }

    // Update category description
    @PutMapping("/{catId}")
    public ResponseEntity<?> updateDescription(@PathVariable Integer catId, @RequestBody Map<String, String> body) {
        String description = body.get("description");
        if (description == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "description is required"));

        try {
            Category updated = categoryService.updateDescription(catId, description);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
