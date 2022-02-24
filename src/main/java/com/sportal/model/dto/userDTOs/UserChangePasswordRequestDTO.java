package com.sportal.model.dto.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@Component
public class UserChangePasswordRequestDTO {
    private long id;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
