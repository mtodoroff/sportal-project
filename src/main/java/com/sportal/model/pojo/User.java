package com.sportal.model.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sportal.model.dto.userDTOs.UserRegisterRequestDTO;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="users")
@Component
public class User extends BasePojo{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name="username",unique = true)
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;

    @Column(name="is_admin")
    private boolean is_admin;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "user")
    private Set<Article> articles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "likers",cascade = CascadeType.ALL)
    private Set<Comment> likedComments;

    @ManyToMany(mappedBy = "dislikers",cascade = CascadeType.ALL)
    private Set<Comment> dislikedComments;

    @ManyToMany(mappedBy = "likedArticles")
    private Set<Article> likedArticles;

    @ManyToMany(mappedBy = "dislikedArticles")
    private Set<Article> dislikedArticles;

    public User(UserRegisterRequestDTO userDTO){
        this.firstName = userDTO.getFirst_name();
        this.lastName = userDTO.getLast_name();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.is_admin = userDTO.is_admin();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
}
