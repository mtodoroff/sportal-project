package com.sportal.service;

import com.sportal.exceptions.InvalidArticle;
import com.sportal.exceptions.NotFoundException;
import com.sportal.exceptions.UnauthorizedException;
import com.sportal.model.dto.article.AddArticleDTO;
import com.sportal.model.dto.article.ArticleWithOwnerDTO;
import com.sportal.model.dto.categoryDTO.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTO.UserWithoutArticlesDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.UserRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    ModelMapper map;
    @Autowired
    private UserRepository userRepository;

    public ArticleWithOwnerDTO addArticle(AddArticleDTO article, Long id) {
        if (article.getContent() == null || article.getTitle() == null) {
            throw new InvalidArticle("Invalid Article");
        }
        if (id == null) {
            throw new UnauthorizedException("Please login");
        }
        ArticleWithOwnerDTO art = new ArticleWithOwnerDTO();
        art.setContent(article.getContent());
        art.setTitle(article.getTitle());
        art.setCreated_at(LocalDateTime.now());
        art.setUpdated_at(LocalDateTime.now());
        User u=userRepository.findById(id).orElseThrow(() -> new NotFoundException("Owner not found"));
        UserWithoutArticlesDTO dto=map.map(u,UserWithoutArticlesDTO.class);
        art.setOwner(dto);
        Article articleForDataBase=new Article();
        articleForDataBase.setUser(u);

        articleForDataBase.setUpdated_at(art.getUpdated_at());
        articleForDataBase.setCreated_at(art.getCreated_at());
        articleForDataBase.setViews(art.getViews());
        articleForDataBase.setTitle(art.getTitle());
        articleForDataBase.setContent(art.getContent());
        articleRepository.save(articleForDataBase);
        art.setId(articleForDataBase.getId());
       // articleForDataBase.setCategory_id(art.getCategory_id());
        return art;
    }

    public ArticleWithOwnerDTO getByid(long id) {

        Article art = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article not found"));
        ArticleWithOwnerDTO artWithOwner = new ArticleWithOwnerDTO();
        CategoryWithoutArticleDTO dto=new CategoryWithoutArticleDTO();
        dto.setId(art.getCategory_id().getId());
        dto.setCategory(art.getCategory_id().getCategory());
        artWithOwner.setCategory(dto);

        artWithOwner.setOwner(map.map(art.getUser(), UserWithoutArticlesDTO.class));
        artWithOwner.setContent(art.getContent());
        artWithOwner.setTitle(art.getTitle());
        artWithOwner.setCreated_at(art.getCreated_at());
        artWithOwner.setUpdated_at(art.getUpdated_at());
        artWithOwner.setId(art.getId());
        artWithOwner.setViews(art.getViews());
        return artWithOwner;
    }
}
