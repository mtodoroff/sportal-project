package com.sportal.service;

import com.sportal.exceptions.InvalidArticle;
import com.sportal.exceptions.NotFoundException;
import com.sportal.exceptions.UnauthorizedException;
import com.sportal.model.dto.articleDTOs.AddArticleDTO;
import com.sportal.model.dto.articleDTOs.ArticleResponseDTO;
import com.sportal.model.dto.articleDTOs.ArticleWithOwnerDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTOs.UserWithoutArticlesDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.CategoryRepository;
import com.sportal.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    ModelMapper map;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    SessionService sessionService;

    public ArticleResponseDTO addArticle(AddArticleDTO article, Long id) {
        validateArticle(article);
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Owner not found"));
        sessionService.validateAdmin(u);
        Article art = new Article(article, u);
        Category cat = categoryRepository.findByCategory(article.getCategory());
        if (cat == null) {
            cat = new Category(article.getCategory());
            categoryRepository.save(cat);
        }
        //TODO ADD String validation
        art.setCategory_id(cat);
        ArticleResponseDTO dto = map.map(art, ArticleResponseDTO.class);
        articleRepository.save(art);
        dto.setId(cat.getId());
        return dto;
    }

    private void validateArticle(AddArticleDTO article) {
        if (article.getContent() == null || article.getTitle() == null) {
            throw new InvalidArticle("Invalid Article");
        }
    }

    public ArticleWithOwnerDTO getByTitle(String title) {

        Article art = articleRepository.findByTitle(title);
        if (art == null) {
            throw new NotFoundException("Article not found");
        }
        CategoryWithoutArticleDTO categoryDTO = new CategoryWithoutArticleDTO(art.getCategory_id());

        return new ArticleWithOwnerDTO(art, map.map(art.getUser(), UserWithoutArticlesDTO.class), categoryDTO);
    }
}
