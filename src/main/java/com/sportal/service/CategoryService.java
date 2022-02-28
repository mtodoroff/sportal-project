package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundCategory;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.ArticleSearchResponseDTO;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;

import com.sportal.model.dto.categoryDTOs.*;
import com.sportal.model.dto.categoryDTOs.CategoryRequestDTO;
import com.sportal.model.dto.categoryDTOs.CategoryRequestEditDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithArticlesDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import com.sportal.model.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;


    public List<ArticleSearchResponseDTO> searchByCategory(String category,int pageSize,int pageNumber) {
        if (pageNumber < 0 || pageSize < 0) {
            throw new NotFoundException("Not valid parameter");
        }

        Pageable pageable = PageRequest.of(pageSize, pageNumber);
        List<Article> articleList = categoryRepository.findByTitleUsingLikeCategory(category,pageable);
        if (category.trim().isEmpty() || articleList == null) {
            throw new NotFoundCategory("Category not found");
        }

        List<ArticleSearchResponseDTO> articleSearchResponseDTOS = new ArrayList<>();

        for (Article article : articleList) {
                ArticleSearchResponseDTO articleSearchResponseDTO = mapper.map(article, ArticleSearchResponseDTO.class);
                articleSearchResponseDTOS.add(articleSearchResponseDTO);
        }
        return articleSearchResponseDTOS;
    }


    public List<CategoryWithArticlesDTO> findAllCategoriesWithArticles() {
        List<CategoryWithArticlesDTO> list = new ArrayList<>();
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()) {
            throw new NotFoundCategory("No found any categories");

        }
        for (Category cat : category) {
            CategoryWithArticlesDTO categoryWithArticlesDTO = new CategoryWithArticlesDTO(cat);
                list.add(categoryWithArticlesDTO);
        }
        return list;
    }

    public CategoryWithoutArticleDTO editCategory(CategoryRequestEditDTO dto) {
        CategoryWithoutArticleDTO currentDto = new CategoryWithoutArticleDTO();
        Category category = categoryRepository.findCategoryById(dto.getId());
        currentDto.setCategory(dto.getCategory());
        if (dto.getCategory() == null || dto.getCategory().trim().isEmpty()) {
            throw new BadRequestException("New category cannot be null or empty");
        }
        List<Category> categorySet = categoryRepository.findAll();
        for (Category cat : categorySet) {
            if (cat.getCategory().equals(dto.getCategory())) {
                throw new BadRequestException("This category name is already added!");
            }
        }
        category.setCategory(dto.getCategory());
        categoryRepository.save(category);
        return currentDto;
    }


    public CategoryWithoutArticleDTO addCategory(CategoryWithoutArticleDTO dto) {
        Category category = new Category();
        if (dto.getCategory() == null || dto.getCategory().trim().isEmpty()) {
            throw new BadRequestException("New category cannot be null or empty");
        }
        category.setCategory(dto.getCategory());
        List<Category> categorySet = categoryRepository.findAll();
        if (categorySet.contains(category)) {
            throw new BadRequestException("This category is already added!");
        }
        categoryRepository.save(category);
        CategoryWithoutArticleDTO currentDTO = new CategoryWithoutArticleDTO(category);

        return currentDTO;


    }
}
