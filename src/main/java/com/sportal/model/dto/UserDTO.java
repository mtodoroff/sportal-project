package com.sportal.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@NoArgsConstructor
@Setter
@Getter
@Component
public class UserDTO {
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String phone;
    private Instant created_at;
    private Instant updated_at;
}
