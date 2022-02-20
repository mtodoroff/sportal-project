package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.model.pojo.Category;
import com.sportal.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        Category cat = new Category();
        cat.setCategory(category.getCategory());
        List<Category> categorySet = categoryRepository.findAll();
        if (categorySet.contains(cat)){
            throw new BadRequestException("This category is already added!");
        }
        categoryRepository.save(cat);
        return cat;
    }

}
