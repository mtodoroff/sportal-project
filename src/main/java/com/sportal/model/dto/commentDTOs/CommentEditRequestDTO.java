package com.sportal.model.dto.commentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CommentEditRequestDTO {
    private long id;
    private String comment_text;
}
