package com.sportal.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    private String email;


}
