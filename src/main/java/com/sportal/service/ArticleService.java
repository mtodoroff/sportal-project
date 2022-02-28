package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.InvalidArticle;
import com.sportal.exceptions.NotFoundCategory;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.*;
import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTOs.UserWithoutArticlesDTO;
import com.sportal.model.pojo.Article;

import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.Picture;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.CategoryRepository;
import com.sportal.model.repository.UserRepository;
import lombok.Synchronized;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final static Object object = new Object();


    public ArticleResponseDTO addArticle(AddArticleDTO articleDTO, Long userId) {
        validateArticle(articleDTO);
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Owner not found"));
        Article article = new Article(articleDTO, u);
        article.setCreated_at(LocalDateTime.now());
        article.setUpdated_at(LocalDateTime.now());
        Category category = categoryRepository.findCategoryById(articleDTO.getCategory_id());
        if (category == null) {
            throw new NotFoundCategory("Not found category");
        }
        article.setCategory_id(category);
        ArticleResponseDTO dto = map.map(article, ArticleResponseDTO.class);
        articleRepository.save(article);
        dto.setId(article.getId());
        dto.setComments(new ArrayList<>());
        return dto;
    }

    private void validateArticle(AddArticleDTO article) {
        if (article.getContent() == null || article.getTitle() == null || article.getContent().trim().isEmpty() || article.getTitle().trim().isEmpty() ) {
            throw new InvalidArticle("All fields are mandatory");
        }
    }


    public List<ArticleSearchResponseDTO> searchByTitle(int pageNumber, int pageSize, String title) {
        if (pageNumber < 0 || pageSize < 0) {
            throw new NotFoundException("Not valid parameter");
        }
        Pageable pageable = PageRequest.of(pageSize, pageNumber);
        List<Article> art = articleRepository.findByTitleUsingLike(title, pageable);

        List<ArticleSearchResponseDTO> searchResponseDTOS = new ArrayList<>();
        verifyArticleId(art == null, "Article not found");
        Picture defaultPicture = new Picture();
        defaultPicture.setPic_url("article_images/sportal.png");
        defaultPicture.setId(1);
        for (Article a : art) {
            ArticleSearchResponseDTO current = new ArticleSearchResponseDTO();
            current.setId(a.getId());
            current.setUpdated_at(a.getUpdated_at());
            current.setTitle(a.getTitle());
            current.setPicture(a.getArticleImages().stream().findFirst().orElse(defaultPicture));
            current.setCategory(a.getCategory_id().getCategory());
            searchResponseDTOS.add(current);
        }
        return searchResponseDTOS;
    }

    @Synchronized
    public int likeArticle(long articleId, long userId) {
        verifyArticleId(articleId <= 0, "Not found Article");
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if (user.getLikedArticles().contains(article)) {
            throw new BadRequestException("You already liked this article!");
        }
        article.getLikedArticles().add(user);
        if (article.getDislikedArticles().contains(user)) {
            article.getDislikedArticles().remove(user);
        }
        articleRepository.save(article);
        return article.getLikedArticles().size();
    }

    @Synchronized
    public int dislikeArticle(long articleId, long userId) {
        verifyArticleId(articleId <= 0, "Not found Article");
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if (user.getDislikedArticles().contains(article)) {
            throw new BadRequestException("You already disliked this article!");
        }
        article.getDislikedArticles().add(user);
        if (article.getLikedArticles().contains(user)) {
            article.getLikedArticles().remove(user);
        }
        articleRepository.save(article);
        return article.getDislikedArticles().size();
    }

    public List<ArticleWithoutUserDTO> getTopFiveMostViewed() {
        List<Article> articles = articleRepository.findTopByViews();
        List<ArticleWithoutUserDTO> articleWithoutUserDTOS = new ArrayList<>();
        if (articles.size() > 0) {
            for (Article a : articles) {
                Optional<Article> articleById = articleRepository.findById(a.getId());
                articleById.ifPresent(article -> articleWithoutUserDTOS.add(new ArticleWithoutUserDTO(article)));
            }
            return articleWithoutUserDTOS;
        }
        return articleWithoutUserDTOS;
    }

    public List<ArticleWithoutUserDTO> latestArticles() {
        List<Article> articlesByDate = articleRepository.latestFiveArticles()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
        List<ArticleWithoutUserDTO> latestArticles = new ArrayList<>();
        for (Article a : articlesByDate) {
            latestArticles.add(new ArticleWithoutUserDTO(a));
        }
        return latestArticles;
    }

    private User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Article getArticleById(long id) {
        return articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article not found!"));
    }

    @Synchronized
    public ArticleWithoutUserDTO getById(long articleId) {
        verifyArticleId(articleId <= 0, "Not found Article");
        Optional<Article> opt = articleRepository.findById(articleId);
        verifyArticleId(!opt.isPresent(), "The article not found");

        Article article = opt.get();
        article.setViews(article.getViews() + 1);
        articleRepository.save(article);
        CategoryWithoutArticleDTO categoryWithoutArticleDTO = new CategoryWithoutArticleDTO(article.getCategory_id());
        ArticleWithoutUserDTO articleWithoutUserDTO = new ArticleWithoutUserDTO(article);
        articleWithoutUserDTO.setCategory(categoryWithoutArticleDTO);
        return articleWithoutUserDTO;
    }

    public ArticleWithoutUserDTO deleteById(long articleId) {
        verifyArticleId(articleId <= 0, "Article not found");
        Optional<Article> opt = articleRepository.findById(articleId);
        Article article = opt.get();
        CategoryWithoutArticleDTO categoryWithoutArticleDTO = new CategoryWithoutArticleDTO(article.getCategory_id());
        ArticleWithoutUserDTO articleWithoutUserDTO = new ArticleWithoutUserDTO(article);
        articleWithoutUserDTO.setCategory(categoryWithoutArticleDTO);
        articleRepository.delete(article);
        return articleWithoutUserDTO;
    }

    private void verifyArticleId(boolean statement, String title) {
        if (statement) {
            throw new NotFoundException(title);
        }
    }

    public ArticleResponseDTO editArticle(ArticleEditDTO articleDTO, Long attribute) {
        Optional<Article> opt = articleRepository.findById(articleDTO.getId());
        verifyArticleId(!opt.isPresent(), "Not found article to edit");
        Article article = opt.get();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setUpdated_at(LocalDateTime.now());
        articleRepository.save(article);
        return new ArticleResponseDTO(article);
    }

    public List<ArticleWithoutUserDTO> getMostComment() {
        List<Article> art = articleRepository.findByMostComment();
        List<ArticleWithoutUserDTO> article = new ArrayList<>();
        for (Article a : art) {
            ArticleWithoutUserDTO dto = new ArticleWithoutUserDTO(a);
            article.add(dto);
        }
        return article;
    }

    public List<ArticleWithoutUserDTO> getLeadNews() {
        List<Article> art = articleRepository.findLeadNews();
        List<ArticleWithoutUserDTO> article = new ArrayList<>();
        for (Article a : art) {
            ArticleWithoutUserDTO dto = new ArticleWithoutUserDTO(a);
            article.add(dto);
        }
        return article;
    }
}
