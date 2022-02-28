package com.sportal.model.dto.articleDTOs;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
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
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private List<Comment> comments;

    public ArticleResponseDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.views = article.getViews();
        this.comments = article.getComments();
        this.created_at = article.getCreated_at();
        this.updated_at = article.getUpdated_at();
    }
}
