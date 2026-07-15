package com.examly.springapp.dto;

import jakarta.validation.constraints.NotNull;

public class CategoryAssignRequest {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
