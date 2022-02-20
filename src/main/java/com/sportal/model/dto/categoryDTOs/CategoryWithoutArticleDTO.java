package com.sportal.model.dto.categoryDTOs;

import com.sportal.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryWithoutArticleDTO {

    private long id;

    private String category;

     public CategoryWithoutArticleDTO(Category category){
      this.id=category.getId();
      this.category=category.getCategory();
     }
}
