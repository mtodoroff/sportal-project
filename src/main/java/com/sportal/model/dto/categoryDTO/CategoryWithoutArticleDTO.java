package com.sportal.model.dto.categoryDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
@NoArgsConstructor
public class CategoryWithoutArticleDTO {

    private long id;

    private String category;

}
