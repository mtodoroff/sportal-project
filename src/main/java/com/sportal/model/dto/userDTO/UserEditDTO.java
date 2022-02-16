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
public class UserEditDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;

    public UserEditDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.password = user.getPassword();
    }
}
