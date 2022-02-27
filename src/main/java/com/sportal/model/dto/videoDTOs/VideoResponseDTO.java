package com.sportal.model.dto.videoDTOs;

import com.sportal.model.pojo.Picture;
import com.sportal.model.pojo.Video;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoResponseDTO {
    private String videoUrl;

    public VideoResponseDTO(Video video) {
        this.videoUrl = video.getVideo_url();
    }
}
