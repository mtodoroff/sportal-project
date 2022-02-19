package com.sportal.model.dto.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserLoginRequestDTO {
    @NotBlank(message = "Username field is mandatory")
    private String username;
    @NotBlank(message = "Password field is mandatory")
    private String password;
}
