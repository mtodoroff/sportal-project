package com.sportal.controller;

import com.sportal.model.dto.article.AddArticleDTO;
import com.sportal.model.dto.article.ArticleWithOwnerDTO;
import com.sportal.model.pojo.Article;
import com.sportal.service.ArticleService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    SessionService sessionService;

    @PostMapping("/articles")
        public ArticleWithOwnerDTO add(@RequestBody AddArticleDTO article, HttpSession session) {
          return articleService.addArticle(article,(Long)session.getAttribute(SessionService.USER_ID));

    }
    @GetMapping("article/{id}")
    public ArticleWithOwnerDTO getByid(@PathVariable long id){
        return articleService.getByid(id);
    }
}
