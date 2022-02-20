package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name="articles")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article extends BasePojo{

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime updated_at;
    @Column
    private long views;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="author_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category_id;
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<Comment> comments;

}
