package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    //private List<Category> categories=new ArrayList<>();
    //private Long nextId=1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    //After DTO implementation return type changes
    //public List<Category> getAllCategories(){
    public CategoryResponse getAllCategories(){
        //return categories;
        //return categoryRepository.findAll();
        //Code added to throw error when no category is present..
        List<Category> savedCategories=categoryRepository.findAll();
        if(savedCategories.isEmpty()){
            throw new APIException("No Category exists.");
        }
        //After DTO implementation
        List<CategoryDTO> categoryDTOs=savedCategories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        //return savedCategories;
        CategoryResponse categories=new CategoryResponse();
        categories.setContent(categoryDTOs);
        return categories;
    }

    @Override
    public void createCategory(Category category){
        //category.setCategoryId(nextId++);
        //categories.add(category);

        //JPA will create SQL automatically & implementation for findByCategoryName method just need to follow certain naming convention
        Category savedCategory=categoryRepository.findByCategoryName(category.getCategoryName());
        //API Exception- Custom Exception
        if(savedCategory!=null){
            throw new APIException("Category with name " + category.getCategoryName() + " already exists !!!");
        }
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

        //######## Commented out Below lines to use custom "ResourceNotFoundException"
        //Category savedCategory=categoryRepository.findById(categoryId).
        //orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        Category savedCategory=categoryRepository.findById(categoryId).
                 orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));

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
