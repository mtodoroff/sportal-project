package com.sportal.model.dto.commentDTOs;

import com.sportal.model.pojo.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDTO {
    private long id;
    private String comment_text;
    private LocalDateTime postDate;
    private long likes;
    private long dislikes;


    public CommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.comment_text = comment.getCommentText();
        this.postDate = comment.getCreated_at();
        this.likes = comment.getLikers().size();
        this.dislikes = comment.getDislikers().size();
    }
}
