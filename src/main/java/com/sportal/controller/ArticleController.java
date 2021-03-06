package com.sportal.controller;

import com.sportal.model.dto.MessageResponseDTO;
import com.sportal.model.dto.articleDTOs.*;
import com.sportal.model.pojo.User;
import com.sportal.service.ArticleService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    SessionService sessionService;


    @PostMapping("/articles")
    public ResponseEntity<ArticleResponseDTO> add(@RequestBody AddArticleDTO article, HttpSession session) {
        User user = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return ResponseEntity.ok(articleService.addArticle(article, (Long) session.getAttribute(SessionService.USER_ID)));
    }

    @PutMapping("/articles/edit")
    public ResponseEntity<ArticleResponseDTO> editArticle(@RequestBody ArticleEditDTO articleDTO, HttpSession session) {
        User user = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return ResponseEntity.ok(articleService.editArticle(articleDTO, (Long) session.getAttribute(SessionService.USER_ID)));
    }

    @GetMapping("/articles/search")
    public List<ArticleSearchResponseDTO> searchByTitle(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "pageNumber") int pageNumber
    ) {
        return articleService.searchByTitle(pageNumber, pageSize, title);
    }


    @GetMapping("/articles/top5")
    public List<ArticleWithoutUserDTO> topFiveMostViewedArticles() {
        return articleService.getTopFiveMostViewed();
    }

    @GetMapping("/")
    public List<List<ArticleWithoutUserDTO>>  mainPage() {
        return articleService.getFirstScrollArticles();
    }

    @GetMapping("/most_comment")
    public List<ArticleWithoutUserDTO> mostComment() {
        return articleService.getMostComment();
    }

    @GetMapping("/lead_news")
    public List<ArticleWithoutUserDTO> leadNews() {
        return articleService.getLeadNews();
    }

    @GetMapping("/articles/latest")
    public List<ArticleWithoutUserDTO> latestArticles() {
        return articleService.latestArticles();
    }


    @PostMapping("/articles/{id}/like")
    public int likePost(@PathVariable long id, HttpSession session) {
        User loggedUser = sessionService.getLoggedUser(session);
        return articleService.likeArticle(id, loggedUser.getId());
    }

    @PostMapping("/articles/{id}/dislike")
    public int unlikePost(@PathVariable long id, HttpSession session) {
        User loggedUser = sessionService.getLoggedUser(session);
        return articleService.dislikeArticle(id, loggedUser.getId());
    }

    @GetMapping("/articles/{articleId}")
    public ArticleWithoutUserDTO getById(@PathVariable long articleId) {
        return articleService.getById(articleId);
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<MessageResponseDTO> deleteById(@PathVariable long articleId, HttpSession session) {
        User user = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        articleService.deleteById(articleId);
        return new ResponseEntity<>(new MessageResponseDTO("You successfully deleted this article"), HttpStatus.OK);
    }
}
