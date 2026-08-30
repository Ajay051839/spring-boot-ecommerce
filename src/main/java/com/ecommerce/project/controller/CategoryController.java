package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("api")
public class CategoryController {
    //private List<Category> categories=new ArrayList<>();
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // IMP-PGN; Implementing Pagination
    //@GetMapping("api/public/categories") -- below @RequestMapping is equivalent to this
    //@RequestMapping(value="api/public/categories", method=RequestMethod.GET) //2-@RequestMapping at class
    @RequestMapping(value="/public/categories", method=RequestMethod.GET) //2-@RequestMapping at class
    public ResponseEntity<CategoryResponse>  getAllCategories(
            @RequestParam(name="pageNumber")Integer pageNumber,
            @RequestParam(name="pageSize")Integer pageSize
    ){
    //public ResponseEntity<List<Category>> getAllCategories(){
        //return categoryService.getAllCategories();
        // IMP-PGN; Implementing Pagination
        //CategoryResponse categories=categoryService.getAllCategories();
        CategoryResponse categories=categoryService.getAllCategories(pageNumber,pageSize);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    //@PostMapping("api/public/categories")
    //@RequestMapping(value="api/public/categories", method=RequestMethod.POST) //2-@RequestMapping at class
    @RequestMapping(value="/public/categories", method=RequestMethod.POST) //2-@RequestMapping at class
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.createCategory(categoryDTO);
        //return "Category added successfully.";
        return new ResponseEntity<>("Category added successfully.",HttpStatus.CREATED);
    }


    //@DeleteMapping("api/admin/categories/{categoryId}")
    @RequestMapping(value="/admin/categories/{categoryId}", method=RequestMethod.DELETE)
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status,HttpStatus.OK);
    }

    //@PutMapping("api/public/categories/{categoryId}")
    @RequestMapping(value="/public/categories/{categoryId}", method=RequestMethod.PUT)
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId){
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK);


    }

    //just mock echo API for @RequestParam
    @GetMapping("/echo")
    public ResponseEntity<String> getEchoedMessage(@RequestParam(name="message")String message){
        String messageEchoed="Echoed message: "+message;
        return new ResponseEntity<>(messageEchoed,HttpStatus.OK);
    }


}
