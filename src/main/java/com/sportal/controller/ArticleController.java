package com.sportal.controller;

import com.sportal.model.dto.articleDTOs.*;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.User;
import com.sportal.service.ArticleService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    SessionService sessionService;


    @PostMapping("/articles/add")
        public ResponseEntity<ArticleResponseDTO> add(@RequestBody AddArticleDTO article, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return ResponseEntity.ok(articleService.addArticle(article,(Long)session.getAttribute(SessionService.USER_ID)));
    }

    @PutMapping("/articles/edit")
    public ResponseEntity<ArticleResponseDTO>editArticle(@RequestBody ArticleEditDTO articleDTO, HttpSession session){
        User user =sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return ResponseEntity.ok(articleService.editArticle(articleDTO,(Long)session.getAttribute(SessionService.USER_ID)));
    }

    @GetMapping("/articles/search")
    public List<ArticleWithUserDTO> searchByTitle(@RequestParam(value = "pageNumber") int pageNumber,@RequestParam(value = "pageSize") int pageSize){
        return articleService.searchByTitle(pageNumber,pageSize);
    }

    @GetMapping("/articles/top5")
    public List<ArticleWithoutUserDTO> topFiveMostViewedArticles(){
        return articleService.getTopFiveMostViewed();
    }

    @GetMapping("/")
    public List<ArticleWithoutUserDTO> mainPage(){
        return articleService.getTopFiveMostViewed();
    }

    @GetMapping("/articles/latest")
    public List<ArticleWithoutUserDTO> latestArticles(){
        return articleService.latestArticles();
    }


    @PostMapping("/articles/{id}/like")
    public int likePost(@PathVariable long id, HttpSession session){
        User loggedUser = sessionService.getLoggedUser(session);
        return articleService.likeArticle(id,loggedUser.getId());
    }

    @PostMapping("/articles/{id}/dislike")
    public int unlikePost(@PathVariable long id, HttpSession session){
        User loggedUser = sessionService.getLoggedUser(session);
        return articleService.dislikeArticle(id,loggedUser.getId());
    }

    @GetMapping("/articles/{articleId}")
    public ArticleWithoutUserDTO getById(@PathVariable long articleId){
        return articleService.getById(articleId);
    }

    @DeleteMapping("/articles/{articleId}")
    public ArticleWithoutUserDTO deleteById(@PathVariable long articleId, HttpSession session){
        User user =sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return articleService.deleteById(articleId);
    }

}
