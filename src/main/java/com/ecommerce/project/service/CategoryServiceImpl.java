package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    //private List<Category> categories=new ArrayList<>();
    //private Long nextId=1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){

        //return categories;
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category){
        //category.setCategoryId(nextId++);
        //categories.add(category);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
//        Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId))
//                .findFirst().orElse(null);
//        Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId))
//                .findFirst().
//                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        //1234
//        List<Category> categories=categoryRepository.findAll();
//        Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId))
//                .findFirst().
//                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        //1234
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        //categories.remove(category);
        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        //1234:Optimization
        //List<Category> categories=categoryRepository.findAll(); //Repository added
        //1234
        Category savedCategory=categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return savedCategory;

        //1234
//        Optional<Category> optionalCategory=categories.stream().filter(c->c.getCategoryId().equals(categoryId))
//                .findFirst();
//        if(optionalCategory.isPresent()){
//            Category existingCategory=optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            //repository added
//            return categoryRepository.save(existingCategory);
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
//        }

    }

}
