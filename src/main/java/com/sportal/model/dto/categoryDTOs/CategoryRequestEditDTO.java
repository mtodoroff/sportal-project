package com.sportal.model.dto.categoryDTOs;

import jdk.jfr.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestEditDTO {
    private long id;
    private String category;
}
