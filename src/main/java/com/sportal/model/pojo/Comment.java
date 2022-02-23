package com.sportal.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
@Component
public class Comment extends BasePojo{

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parent_comment_id;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent_comment_id")
    private List<Comment> replies;

    @ManyToMany
    @JoinTable(
            name = "users_like_comments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likers;

    @ManyToMany
    @JoinTable(
            name = "users_dislike_comments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> dislikers;

    public Comment(String commentText, Article article, User user) {
        this.commentText = commentText;
        this.article = article;
        this.user = user;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public Comment(String commentText, Article article, User user, Comment parentComment) {
        this.commentText = commentText;
        this.article = article;
        this.user = user;
        this.parent_comment_id = parentComment;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
}
