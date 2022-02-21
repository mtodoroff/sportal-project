package com.sportal.model.dto.userDTOs;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Comment;
import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserGetAllResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<Comment> comments;

    public UserGetAllResponseDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.comments = user.getComments();
    }
}
