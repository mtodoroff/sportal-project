package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sportal.model.dto.userDTOs.UserRegisterRequestDTO;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="users")
@Component
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
//    @ManyToOne
//    @JoinColumn(name="user_type")
//    private Role role;
    @Column(name = "created_at")
    private Instant created_at;
    @Column(name = "updated_at")
    private Instant updated_at;

    @OneToMany(mappedBy = "user")
    private Set<Article>articles;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comment> comments;

    //TODO add Role enums
    public User(UserRegisterRequestDTO userDTO){
        this.firstName = userDTO.getFirst_name();
        this.lastName = userDTO.getLast_name();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
    }
}
