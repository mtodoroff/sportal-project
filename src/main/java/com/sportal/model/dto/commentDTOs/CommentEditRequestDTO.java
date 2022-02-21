package com.sportal.model.dto.commentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CommentEditRequestDTO {
    private long id;
    @NotBlank(message = "Comment field is cannot be empty")
    private String comment_text;
}
