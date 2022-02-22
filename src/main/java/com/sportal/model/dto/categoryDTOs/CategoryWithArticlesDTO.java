package com.sportal.model.dto.categoryDTOs;

import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
import com.sportal.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryWithArticlesDTO {
    private long id;
    private String category;
    private List<ArticleWithoutUserDTO> articleWithoutUserDTO;

    public CategoryWithArticlesDTO(Category category){
        this.id=category.getId();
        this.category=category.getCategory();
    }
}
