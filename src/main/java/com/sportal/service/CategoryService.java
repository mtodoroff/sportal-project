package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundCategory;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithArticlesDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import com.sportal.model.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;

    public Category createCategory(Category category) {
        Category cat = new Category();
        cat.setCategory(category.getCategory());
        List<Category> categorySet = categoryRepository.findAll();
        if (categorySet.contains(cat)) {
            throw new BadRequestException("This category is already added!");
        }
        categoryRepository.save(cat);
        return cat;
    }

    public List<ArticleWithoutUserDTO> searchByCategory(String category) {
        List<Category> categoryList = categoryRepository.findByCategoryUsingLike(category);
        if (category.trim().isEmpty() || categoryList == null) {
            throw new NotFoundCategory("Category not found");
        }

        List<ArticleWithoutUserDTO> articleWithoutUserDTO = new ArrayList<>();

        for (Category categoryElement : categoryList) {
            for (Article a : categoryElement.getArticles()) {
                ArticleWithoutUserDTO articleWithoutUserDTOCurrent = mapper.map(a, ArticleWithoutUserDTO.class);
                articleWithoutUserDTO.add(articleWithoutUserDTOCurrent);
            }
        }
        return articleWithoutUserDTO;
    }

    public List<CategoryWithArticlesDTO> findAllArticles() {
        List<CategoryWithArticlesDTO> categoryWithArticlesDTOList=new ArrayList<>();
        List<Category>category=categoryRepository.findAll();
        if(category.isEmpty()){
            throw new NotFoundCategory("No found any categorues");
        }
        for(Category c:category){
           CategoryWithArticlesDTO categoryWithArticlesDTO=new CategoryWithArticlesDTO(c);
           List<ArticleWithoutUserDTO>setWithArticlesWithoutUser=new ArrayList<>();

           for(Article a :c.getArticles()){
            setWithArticlesWithoutUser.add( new ArticleWithoutUserDTO(a));
           }
           categoryWithArticlesDTO.setArticleWithoutUserDTO(setWithArticlesWithoutUser);
           categoryWithArticlesDTOList.add(categoryWithArticlesDTO);
        }
        return categoryWithArticlesDTOList;
    }
}
