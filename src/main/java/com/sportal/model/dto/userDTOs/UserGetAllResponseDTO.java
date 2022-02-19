package com.sportal.model.dto.userDTOs;

import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
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

    public UserGetAllResponseDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
