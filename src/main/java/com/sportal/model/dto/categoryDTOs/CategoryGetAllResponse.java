package com.sportal.model.dto.categoryDTOs;

import com.sportal.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryGetAllResponse {
    private long id;
    private String category;

    public CategoryGetAllResponse(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
