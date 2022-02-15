package com.sportal.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sportal.model.pojo.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@Component
public class UserRegisterResponseDTO {
    private Integer id;
    private String username;
    private String email;

    public UserRegisterResponseDTO(User user){
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
