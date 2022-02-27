package com.sportal.model.dto.articleDTOs;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
public class ArticleSearchResponseDTO {
    private long id;
    private String title;
    private Picture picture;
    private LocalDateTime updated_at;
    private String category;


}
