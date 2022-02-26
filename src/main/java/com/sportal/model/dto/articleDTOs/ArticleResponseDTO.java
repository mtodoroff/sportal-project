package com.sportal.model.dto.articleDTOs;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@Component
public class ArticleResponseDTO {
    private long id;
    private String title;
    private String content;
    private long views;
    private List<Comment> comments;

    public ArticleResponseDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.views = article.getViews();
        this.comments = article.getComments();
    }
}
