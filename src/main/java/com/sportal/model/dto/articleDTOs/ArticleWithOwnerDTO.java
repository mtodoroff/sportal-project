package com.sportal.model.dto.articleDTOs;

import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTOs.UserWithoutArticlesDTO;
import com.sportal.model.pojo.Article;
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

    private String picUrl;

    private String videoUrl;

    public ArticleWithOwnerDTO(Article article, UserWithoutArticlesDTO owner, CategoryWithoutArticleDTO category) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.created_at = article.getCreated_at();
        this.updated_at = article.getUpdated_at();
        this.views = article.getViews();
        this.owner = owner;
        this.category = category;
        if(article.getArticleImages()!=null){
            this.picUrl= article.getArticleImages().getPic_url();
        }
        if(article.getVideo().getVideo_url()!=null){
            this.videoUrl= article.getVideo().getVideo_url();
        }
    }
}
