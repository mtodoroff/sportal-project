package com.sportal.model.dto.commentDTOs;

import com.sportal.model.pojo.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentEditResponseDTO {
    private long id;
    private String comment_text;
    private LocalDateTime postDate;
    private String message;


    public CommentEditResponseDTO(Comment comment){
        this.id = comment.getId();
        this.comment_text = comment.getCommentText();
        this.postDate = comment.getCreated_at();

    }
}
