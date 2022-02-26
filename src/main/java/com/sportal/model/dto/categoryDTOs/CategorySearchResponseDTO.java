package com.sportal.model.dto.categoryDTOs;

import com.sportal.model.dto.articleDTOs.ArticleSearchResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategorySearchResponseDTO {
   private ArticleSearchResponseDTO articleSearchResponseDTO;

}
