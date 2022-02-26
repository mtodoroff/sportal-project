package com.sportal.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "videos")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Video{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String video_url;
    @OneToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference
    private Article article;
}
