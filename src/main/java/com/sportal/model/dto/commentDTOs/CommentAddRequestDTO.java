package com.sportal.model.dto.commentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class CommentAddRequestDTO {
    private long articleId;
    private String comment_text;
}
