package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
//    List<Category> getAllCategories=new ArrayList<>();
    //After DTO implementation; Below line commented
    //List<Category> getAllCategories();
    CategoryResponse getAllCategories();
    //DTO Impl
    //void createCategory(Category category);
    CategoryDTO createCategory(CategoryDTO category);
    String deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
