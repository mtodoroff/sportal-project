package com.sportal.model.dto.categoryDTOs;

import com.sportal.model.dto.articleDTOs.ArticleSearchResponseDTO;
import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CategorySearchResponseDTO {
   private long id;
   private String title;
   private LocalDateTime updated_at;
   private CategoryGetAllResponse category;

}
