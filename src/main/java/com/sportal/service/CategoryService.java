package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundCategory;
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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;


    public List<CategorySearchResponseDTO> searchByCategory(String category) {
        List<Category> categoryList = categoryRepository.findByCategoryUsingLike(category);
        if (category.trim().isEmpty() || categoryList == null) {
            throw new NotFoundCategory("Category not found");
        }

        List<CategorySearchResponseDTO> categorySearchResponseDTOList = new ArrayList<>();

        for (Category categoryElement : categoryList) {
            for (Article a : categoryElement.getArticles()) {
                CategorySearchResponseDTO categorySearchResponseDTO = mapper.map(a, CategorySearchResponseDTO.class);
                categorySearchResponseDTOList.add(categorySearchResponseDTO);
            }
        }
        return categorySearchResponseDTOList;
    }


    public List<CategoryWithArticlesDTO> findAllCategoriesWithArticles() {
        List<CategoryWithArticlesDTO> list = new ArrayList<>();
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()) {
            throw new NotFoundCategory("No found any categories");

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
