package com.sportal.model.dto.imageDTOs;

import com.sportal.model.pojo.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageResponseDTO {
    private long id;
    private String picUrl;

    public ImageResponseDTO(Picture picture) {
        this.id = picture.getId();
        this.picUrl = picture.getPic_url();
    }
}
