package com.sportal.model.dto.articleDTOs;

import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class ArticleWithoutUserDTO {

    private long id;

    private String title;

    private String content;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private long views;

    private String pic_url;

    private CategoryWithoutArticleDTO category;

    public ArticleWithoutUserDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.created_at = article.getCreated_at();
        this.updated_at = article.getUpdated_at();
        this.views = article.getViews();
        this.pic_url = article.getArticleImages().getPic_url();
    }
}
