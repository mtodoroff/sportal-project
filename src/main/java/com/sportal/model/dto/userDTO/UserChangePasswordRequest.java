package com.sportal.model.dto.userDTO;

import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserChangePasswordRequest {
    private long id;
    @NotBlank(message = "Password can not be empty")
    private String oldPassword;
    @NotBlank(message = "Password can not be empty")
    private String newPassword;
    @NotBlank(message = "Password can not be empty")
    private String confirmNewPassword;

}
