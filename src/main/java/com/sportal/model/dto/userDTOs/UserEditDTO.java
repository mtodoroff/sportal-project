package com.sportal.model.dto.userDTOs;

import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserEditDTO {

    private long id;
    @NotBlank(message = "First name can not be empty")
    private String firstName;
    @NotBlank(message = "Last name can not be empty")
    private String lastName;
    @NotBlank( message = "Phone can not be empty")
    @Pattern(regexp = "\\d+")
    private String phone;

    public UserEditDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
    }
}
