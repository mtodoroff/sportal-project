package com.sportal.model.dto.articleDTOs;

import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTOs.UserWithoutArticlesDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class ArticleWithOwnerDTO {

    private long id;

    private String title;

    private String content;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private long views;

    private UserWithoutArticlesDTO owner;

    private CategoryWithoutArticleDTO category;

    public ArticleWithOwnerDTO(Article art,UserWithoutArticlesDTO owner, CategoryWithoutArticleDTO category) {
        this.id = art.getId();
        this.title = art.getTitle();
        this.content = art.getContent();
        this.created_at = art.getCreated_at();
        this.updated_at = art.getUpdated_at();
        this.views = art.getViews();
        this.owner = owner;
        this.category = category;
    }

//private CommentsWithOutUserAndWithoutArticlesDTO comments;
}
