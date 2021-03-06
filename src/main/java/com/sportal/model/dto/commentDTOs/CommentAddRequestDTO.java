package com.sportal.model.dto.commentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Component
public class CommentAddRequestDTO {
    private long article_id;
    private String comment_text;
}
