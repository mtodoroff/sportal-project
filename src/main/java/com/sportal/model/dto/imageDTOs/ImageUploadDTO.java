package com.sportal.model.dto.imageDTOs;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ImageUploadDTO {
    private long id;
    private Article article_id;

    public ImageUploadDTO(Picture picture) {
        this.id = picture.getId();
        this.article_id = picture.getArticle_id();
    }
}
