package com.sportal.model.dto.articleDTOs;

import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleForPictureDTO {

    private long id;

    private String title;

    private String content;

    private String picUrl;

    public ArticleForPictureDTO(Picture picture) {
        this.id = picture.getId();
        this.content = picture.getArticle_id().getContent();
        this.title = picture.getArticle_id().getTitle();
        this.picUrl = picture.getPic_url();
    }
}
