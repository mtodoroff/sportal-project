package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;
@Table(name="articles")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
//    @OneToOne
//    @JoinColumn(name="author_id")
//    private User author_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category_id;
}
