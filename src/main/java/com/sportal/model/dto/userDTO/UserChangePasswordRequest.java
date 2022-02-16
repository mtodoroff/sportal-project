package com.sportal.model.dto.userDTO;

import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserChangePasswordRequest {
    private long id;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
