package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.InvalidArticle;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.AddArticleDTO;
import com.sportal.model.dto.articleDTOs.ArticleResponseDTO;
import com.sportal.model.dto.articleDTOs.ArticleWithOwnerDTO;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
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
import org.springframework.stereotype.Service;

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

    public ArticleResponseDTO addArticle(AddArticleDTO article, Long id) {
        validateArticle(article);
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Owner not found"));
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
            throw new InvalidArticle("All fields are mandatory");
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

    public int likeArticle(long articleId, long userId) {
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if(user.getLikedComments().contains(article)){
            throw new BadRequestException("User already liked this article!");
        }
        article.getLikedArticles().add(user);
        if (article.getDislikedArticles().contains(user)){
            article.getDislikedArticles().remove(user);
        }
        articleRepository.save(article);
        return article.getLikedArticles().size();
    }

    public int dislikeArticle(long articleId, long userId) {
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if(user.getDislikedArticles().contains(article)){
            throw new BadRequestException("User already disliked this article!");
        }
        article.getDislikedArticles().add(user);
        if (article.getLikedArticles().contains(user)){
            article.getLikedArticles().remove(user);
        }
        articleRepository.save(article);
        return article.getDislikedArticles().size();
    }

    public List<ArticleWithoutUserDTO> getTopFiveMostViewed() {
        List<Article> articles = articleRepository.findTopByViews();
        List<ArticleWithoutUserDTO> articleWithoutUserDTOS = new ArrayList<>();
        if(articles.size() > 0) {
            for (Article a : articles) {
                Optional<Article> articleById = articleRepository.findById(a.getId());
                articleById.ifPresent(article -> articleWithoutUserDTOS.add(new ArticleWithoutUserDTO(article)));
            }
            return articleWithoutUserDTOS;
        }
        return articleWithoutUserDTOS;
    }

    public List<ArticleWithoutUserDTO> latestArticles(){
        List<Article> articlesByDate = articleRepository.latestFiveArticles()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
        List<ArticleWithoutUserDTO> latestArticles = new ArrayList<>();
        for(Article a : articlesByDate){
            latestArticles.add(new ArticleWithoutUserDTO(a));
        }
        return latestArticles;
    }

    //TODO Add User Article and Category to global Service
    private User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Article getArticleById(long id){
        return articleRepository.findById(id).orElseThrow( ()-> new NotFoundException("Article not found!"));
    }


}
