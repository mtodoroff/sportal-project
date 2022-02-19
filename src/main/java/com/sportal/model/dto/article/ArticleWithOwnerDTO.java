package com.sportal.model.dto.article;

import com.sportal.model.dto.categoryDTO.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTO.UserWithoutArticlesDTO;
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

   // private Category category_id;

    private UserWithoutArticlesDTO owner;

    private CategoryWithoutArticleDTO category;
}
