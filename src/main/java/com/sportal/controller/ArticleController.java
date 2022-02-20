package com.sportal.controller;

import com.sportal.model.dto.articleDTOs.AddArticleDTO;
import com.sportal.model.dto.articleDTOs.ArticleResponseDTO;
import com.sportal.model.dto.articleDTOs.ArticleWithOwnerDTO;
import com.sportal.model.pojo.User;
import com.sportal.service.ArticleService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    SessionService sessionService;

    @PostMapping("/articles")
        public ResponseEntity<ArticleResponseDTO> add(@RequestBody AddArticleDTO article, HttpSession session) {
          return ResponseEntity.ok(articleService.addArticle(article,(Long)session.getAttribute(SessionService.USER_ID)));
    }
    @GetMapping("articles/{title}")
    public ArticleWithOwnerDTO getByTitle(@PathVariable String title){
        return articleService.getByTitle(title);
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
}
