package com.sportal.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
@Component
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "created_at")
    private Instant created_at;
    @Column(name = "updated_at")
    private Instant updated_at;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String commentText, Article article, User user) {
        this.commentText = commentText;
        this.article = article;
        this.user = user;
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
    }
}
