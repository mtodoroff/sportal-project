package com.sportal.model.dto.userDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutArticlesDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
