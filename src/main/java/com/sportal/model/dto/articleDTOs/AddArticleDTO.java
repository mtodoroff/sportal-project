package com.sportal.model.dto.articleDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class AddArticleDTO {


    private String title;

    private String content;

    private String category;

}
