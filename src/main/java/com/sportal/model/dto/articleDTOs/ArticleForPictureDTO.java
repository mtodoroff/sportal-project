package com.sportal.model.dto.articleDTOs;

import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleForPictureDTO {


    private String picUrl;

    public ArticleForPictureDTO(Picture picture) {

        this.picUrl = picture.getPic_url();
    }
}
