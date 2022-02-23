package com.sportal.model.dto.userDTOs;

import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserWithArticleDTO {
    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private Set<ArticleWithoutUserDTO> article;
}
