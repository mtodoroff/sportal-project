package com.sportal.model.dto.userDTOs;

import com.sportal.model.pojo.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
public class UserWithoutArticlesDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private Role role;

    private Instant created_at;

    private Instant updated_at;
}
