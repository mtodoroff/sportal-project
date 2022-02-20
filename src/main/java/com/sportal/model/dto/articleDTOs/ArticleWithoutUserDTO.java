package com.sportal.model.dto.articleDTOs;

import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
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

    private CategoryWithoutArticleDTO category;
}
