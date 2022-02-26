package com.sportal.controller;

import com.sportal.exceptions.BadRequestException;
import com.sportal.model.dto.articleDTOs.ArticleResponseDTO;
import com.sportal.model.dto.commentDTOs.CommentAddReplyRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentAddRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentEditRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentResponseDTO;
import com.sportal.model.pojo.User;
import com.sportal.service.CommentService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private SessionService sessionService;
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> addComment(HttpSession session, @RequestBody CommentAddRequestDTO comment){
        User loggedUser = sessionService.getLoggedUser(session);
        return new ResponseEntity(commentService.addComment(loggedUser,comment), HttpStatus.CREATED);
    }

    @PostMapping("comments/reply")
    public ArticleResponseDTO replyToComment(HttpSession sessoin, @RequestBody CommentAddReplyRequestDTO reply){
        User loggedUser = sessionService.getLoggedUser(sessoin);
        return commentService.addCommentReply(loggedUser, reply);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id,HttpSession session){
        User loggedUser = sessionService.getLoggedUser(session);
        if(!commentService.userOwnsComment(loggedUser.getId(), id)){
            throw new BadRequestException("Only the owner of the comment can delete it");
        }
        commentService.deleteComment(id);
        return ResponseEntity.ok().body("\"message\": \"Comment deleted successfully.\"");
    }

    @PutMapping("/comments")
    public ArticleResponseDTO editComment(HttpSession session, @RequestBody CommentEditRequestDTO comment){
        User loggedUser = sessionService.getLoggedUser(session);
        if(commentService.userOwnsComment(loggedUser.getId(), comment.getId())){
            return commentService.editComment(comment);
        }
        else{
            throw new BadRequestException("Only the owner of the comment can edit it");
        }
    }

    @PostMapping("/comments/{id}/like")
    public int likePost(@PathVariable long id, HttpSession session){
        User loggedUser = sessionService.getLoggedUser(session);
        return commentService.likeComment(id,loggedUser.getId());
    }

    @PostMapping("/comments/{id}/dislike")
    public int unlikePost(@PathVariable long id, HttpSession session){
        User loggedUser = sessionService.getLoggedUser(session);
        return commentService.dislikeComment(id,loggedUser.getId());
    }
}
