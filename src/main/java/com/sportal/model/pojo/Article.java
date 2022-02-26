package com.sportal.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sportal.model.dto.articleDTOs.AddArticleDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Table(name="articles")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article extends BasePojo{
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
    @ManyToOne
    @JoinColumn(name="author_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category_id;

    @OneToMany(mappedBy = "article",fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article_id")
    @JsonManagedReference
    private Set<Picture> articleImages;


    @OneToOne(mappedBy = "article")
    @JsonManagedReference
    private Video video;

    @ManyToMany
    @JoinTable(
            name = "users_like_articles",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonBackReference
    private Set<User> likedArticles;

    @ManyToMany
    @JoinTable(
            name = "users_dislike_articles",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonBackReference
    private Set<User> dislikedArticles;

    public Article(AddArticleDTO article, User user){

        this.title= article.getTitle();
        this.content= article.getContent();
        this.created_at=LocalDateTime.now();
        this.updated_at=LocalDateTime.now();
        this.views=0;
        this.user=user;
    }
}
