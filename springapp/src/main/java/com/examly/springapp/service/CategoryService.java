package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Category;

public interface CategoryService {

    Category createCategory(String name);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);
}
