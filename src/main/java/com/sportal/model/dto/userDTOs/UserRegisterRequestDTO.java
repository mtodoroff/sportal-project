package com.sportal.model.dto.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@Component
public class UserRegisterRequestDTO {
    @NotBlank(message = "First name can not be empty")
    private String first_name;
    @NotBlank(message = "Last name can not be empty")
    private String last_name;
    @NotBlank(message = "Username cannot be empty")
    @Length(min = 2,message = "Username is too short")
    private String username;
    @NotBlank(message = "Password can not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @Length(min=10,message = "Password is not in valid format. " +
            "Make sure your password contains at least 8 symbols,1 letter and 1 number")
    private String password;
    @NotBlank(message = "Confirm password can not be empty")
    private String confirmPassword;
    @NotBlank( message = "Phone can not be empty")
    @Pattern(regexp = "\\d+")
    @Length(min=6,message = "Phone must be at least 6 symbols")
    private String phone;
    private boolean is_admin;
    @NotBlank(message = "Email can not be empty")
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String email;
}
