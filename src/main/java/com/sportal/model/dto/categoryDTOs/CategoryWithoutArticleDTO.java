package com.sportal.model.dto.categoryDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CategoryWithoutArticleDTO {

    private long id;

    private String category;

}
