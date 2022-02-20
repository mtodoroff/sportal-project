package com.sportal.model.dto.articleDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class AddArticleDTO {

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String category;

}
