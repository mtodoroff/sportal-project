package com.sportal.model.dto.imageDTOs;

import com.sportal.model.pojo.Picture;

public class ImageUploadDTO {
    private long id;

    public ImageUploadDTO(Picture picture) {
        this.id = picture.getId();
    }
}
