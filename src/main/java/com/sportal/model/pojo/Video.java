package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "videos")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Video extends BasePojo{

    @Column
    private String video_url;
    @OneToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
