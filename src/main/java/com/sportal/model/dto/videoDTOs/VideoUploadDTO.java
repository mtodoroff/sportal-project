package com.sportal.model.dto.videoDTOs;

import com.sportal.model.pojo.Video;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoUploadDTO {
    private long id;
    private VideoResponseDTO article;

    public VideoUploadDTO(Video video) {
        this.id = video.getId();
        this.article.setVideoUrl(article.getVideoUrl());
    }
}
