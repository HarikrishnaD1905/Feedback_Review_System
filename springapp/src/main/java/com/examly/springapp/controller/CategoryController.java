package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CategoryRequest;
import com.examly.springapp.model.Category;
import com.examly.springapp.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * FR6.1: Create a new feedback category.
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.createCategory(request.getName());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * FR6.3: List all categories (used for filtering and assignment).
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
