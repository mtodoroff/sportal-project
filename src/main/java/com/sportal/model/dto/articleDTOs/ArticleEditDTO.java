package com.sportal.model.dto.articleDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditDTO {

    private long id;

    private String title;

    private String content;
}
