package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    // IMP-PGN; Implementing Pagination
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize){
        // IMP-PGN; Implementing Pagination
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize);
        //return categories;
        //return categoryRepository.findAll();ß
        //Code added to throw error when no category is present..
        // IMP-PGN; Implementing Pagination
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);
        List<Category> savedCategories=categoryRepository.findAll(pageDetails).getContent();
        if(savedCategories.isEmpty()){
            throw new APIException("No Category exists.");
        }
        //After DTO implementation
        List<CategoryDTO> categoryDTOs=savedCategories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        //return savedCategories;
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        //category.setCategoryId(nextId++);
        //categories.add(category);

        //After DTO Impl
        Category category=modelMapper.map(categoryDTO,Category.class);

        //JPA will create SQL automatically & implementation for findByCategoryName method just need to follow certain naming convention
        Category categoryFromDB=categoryRepository.findByCategoryName(category.getCategoryName());
        //API Exception- Custom Exception
        if(categoryFromDB!=null){
            throw new APIException("Category with name " + category.getCategoryName() + " already exists !!!");
        }
        Category newSavedCategory=categoryRepository.save(category);
        return modelMapper.map(newSavedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId){
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
         CategoryDTO deletedCategory=modelMapper.map(category,CategoryDTO.class);
        //categories.remove(category);
        categoryRepository.delete(category);
        //return "Category with categoryId: " + categoryId + " deleted successfully !!";
        return deletedCategory;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        //1234:Optimization
        //List<Category> categories=categoryRepository.findAll(); //Repository added
        //1234

        //######## Commented out Below lines to use custom "ResourceNotFoundException"
        //Category savedCategory=categoryRepository.findById(categoryId).
        //orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        Category savedCategory=categoryRepository.findById(categoryId).
                 orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));
        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);

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
