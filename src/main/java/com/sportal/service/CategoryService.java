package com.sportal.service;

import com.sportal.model.pojo.Category;
import com.sportal.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        Category cat=new Category();
        cat.setCategory(category.getCategory());
        categoryRepository.save(cat);
        return cat;
    }

}