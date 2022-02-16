package com.sportal.model.dto.userDTO;

import com.sportal.model.pojo.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class UserRegisterRequestDTO {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private Role role;
    private String email;


}
