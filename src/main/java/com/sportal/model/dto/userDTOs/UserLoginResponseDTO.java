package com.sportal.model.dto.userDTOs;

import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponseDTO {
    private long id;
    private String username;
    private String loginMsg ;

    public UserLoginResponseDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.loginMsg = "Welcome " + this.username + "! You successfully logged in!";
    }


}
