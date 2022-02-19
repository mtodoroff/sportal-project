package com.sportal.model.dto.commentDTOs;

import com.sportal.model.pojo.Comment;

import java.time.Instant;

public class CommentResponseDTO {
    private long id;
    private String comment_text;
    private Instant postDate;

    public CommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.comment_text = comment.getCommentText();
        this.postDate = comment.getCreated_at();
    }
}
