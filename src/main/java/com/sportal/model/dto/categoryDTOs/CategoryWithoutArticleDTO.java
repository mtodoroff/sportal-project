package com.sportal.model.dto.categoryDTOs;

import com.sportal.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CategoryWithoutArticleDTO {


    private String category;

    public CategoryWithoutArticleDTO(Category category) {

        this.category = category.getCategory();
    }
}
