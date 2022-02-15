package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sportal.model.dto.userDTO.UserRegisterRequestDTO;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="users")
@Component
public class User extends BasePOJO {
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private Instant created_at;
    private Instant updated_at;

    //TODO add Role enums
    public User(UserRegisterRequestDTO userDTO){
        first_name = userDTO.getFirst_name();
        last_name = userDTO.getLast_name();
        username = userDTO.getUsername();
        password = userDTO.getPassword();
        phone = userDTO.getPhone();
        email = userDTO.getEmail();
        created_at = Instant.now();
        updated_at = Instant.now();
    }

}
