package com.sportal.model.dto.imageDTOs;

import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
public class ImageUploadDTO {
    private long id;
    private ImageResponseDTO article;

    public ImageUploadDTO(Picture picture) {
        this.id = picture.getId();
        this.article.setPicUrl(picture.getPic_url());
    }
}
