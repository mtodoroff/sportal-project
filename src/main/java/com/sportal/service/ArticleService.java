package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.InvalidArticle;
import com.sportal.exceptions.NotFoundCategory;
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
import lombok.Synchronized;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
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

    public ArticleResponseDTO addArticle(AddArticleDTO articleDTO, Long userId) {
        validateArticle(articleDTO);
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Owner not found"));
        Article article = new Article(articleDTO, u);
        Category category = categoryRepository.findCategoryById(articleDTO.getCategoryId());
        if (category == null) {
            throw new NotFoundCategory("Not found category");
        }
        article.setCategory_id(category);
        ArticleResponseDTO dto = map.map(article, ArticleResponseDTO.class);
        articleRepository.save(article);
        dto.setId(category.getId());
        return dto;
    }

    private void validateArticle(AddArticleDTO article) {
        if (article.getContent() == null || article.getTitle() == null) {
            throw new InvalidArticle("All fields are mandatory");
        }
    }

    public List<ArticleWithOwnerDTO> getByTitle(String title) {
        if (title.trim().isEmpty()) {
            throw new NotFoundException("Not found Article with name" + title);
        }
        List<Article> art = articleRepository.findByTitleUsingLike(title);
        if (art == null) {
            throw new NotFoundException("Article not found");
        }
        List<ArticleWithOwnerDTO> articleWithOwnerDTOS = new ArrayList<>();
        for (Article a : art) {
            CategoryWithoutArticleDTO categoryDTO = new CategoryWithoutArticleDTO(a.getCategory_id());
            ArticleWithOwnerDTO current = new ArticleWithOwnerDTO(a, map.map(a.getUser(), UserWithoutArticlesDTO.class), categoryDTO);
            articleWithOwnerDTOS.add(current);
        }
        return articleWithOwnerDTOS;
    }
    @Synchronized
    public int likeArticle(long articleId, long userId) {
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if (user.getLikedComments().contains(article)) {
            throw new BadRequestException("User already liked this article!");
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
        Article article = getArticleById(articleId);
        User user = getUserById(userId);
        if (user.getDislikedArticles().contains(article)) {
            throw new BadRequestException("User already disliked this article!");
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

    //TODO Add User Article and Category to global Service
    private User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Article getArticleById(long id) {
        return articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article not found!"));
    }

    public ArticleWithoutUserDTO getById(long articleId) {
        Optional<Article> opt=articleRepository.findById(articleId);
        if(!opt.isPresent()){
            throw new NotFoundException("The article not found");
        }
        Article article=opt.get();
        Object object=new Object();
        synchronized (object){
            article.setViews(article.getViews()+1);
        }
        articleRepository.save(article);
        CategoryWithoutArticleDTO categoryWithoutArticleDTO=new CategoryWithoutArticleDTO(article.getCategory_id());
        ArticleWithoutUserDTO articleWithoutUserDTO =new ArticleWithoutUserDTO(article);
        articleWithoutUserDTO.setCategory(categoryWithoutArticleDTO);
        return articleWithoutUserDTO;
    }
    public ArticleWithoutUserDTO deleteById(long articleId) {
        Optional<Article>opt=articleRepository.findById(articleId);
        if(!opt.isPresent()){
            throw new NotFoundException("The article not found");
        }
        Article article=opt.get();
        CategoryWithoutArticleDTO categoryWithoutArticleDTO=new CategoryWithoutArticleDTO(article.getCategory_id());
        ArticleWithoutUserDTO articleWithoutUserDTO=new ArticleWithoutUserDTO(article);
        articleWithoutUserDTO.setCategory(categoryWithoutArticleDTO);
        articleRepository.delete(article);
        return articleWithoutUserDTO;
    }
}
