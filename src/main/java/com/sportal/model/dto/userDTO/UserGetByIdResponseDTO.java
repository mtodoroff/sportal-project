package com.sportal.model.dto;

import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserGetByIdResponseDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    public UserGetByIdResponseDTO(User user) {
        this.firstName = user.getFirst_name();
        this.lastName = user.getLast_name();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
