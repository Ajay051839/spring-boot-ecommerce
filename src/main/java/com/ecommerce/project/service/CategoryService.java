package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories=new ArrayList<>();

    //After DTO implementation; Below line commented
    //List<Category> getAllCategories();
    CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}
