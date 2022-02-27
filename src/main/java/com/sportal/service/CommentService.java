package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.commentDTOs.CommentAddReplyRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentAddRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentEditResponseDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Comment;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.CommentRepository;
import com.sportal.model.repository.UserRepository;
import com.sportal.util.CensoredWords;
import com.sportal.util.Validator;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CommentService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    public CommentEditResponseDTO addComment(User user, CommentAddRequestDTO addedComment){
        Article article = getArticleById(addedComment.getArticle_id());
        //TODO extract in method
        String commentText = addedComment.getComment_text();
        Validator.validateEmptyField(commentText,"Comment");
        String replacement = "******";
        String crealedText = commentText.replaceAll(CensoredWords.getRegexCensorship(),replacement);
//        if (crealedText.contains(replacement)){
//            banUser(user);
//        }
        Comment comment = new Comment(crealedText,article,user);
        article.getComments().add(comment);
        commentRepository.save(comment);
        CommentEditResponseDTO commentEditResponseDTO = new CommentEditResponseDTO(comment);
        commentEditResponseDTO.setMessage("Comment added successfully");
        return commentEditResponseDTO;
    }

    public boolean userOwnsComment(long userId, long commentId) {
        Comment comment = getCommentById(commentId);
        return comment.getUser().getId() == userId;
    }

    public CommentEditResponseDTO addCommentReply(User loggedUser, CommentAddReplyRequestDTO reply) {
        Comment parent = commentRepository.findById(reply.getParent_comment_id()).orElseThrow(() -> new NotFoundException("Comment not found!"));
        Article article = parent.getArticle();
        String commentText = reply.getComment_text();
        Validator.validateEmptyField(commentText,"Comment");
        String createdText = commentText.replaceAll(CensoredWords.getRegexCensorship(),"******");
        Comment comment = new Comment(createdText, article, loggedUser, parent);
        commentRepository.save(comment);

        CommentEditResponseDTO commentResponseDTO = new CommentEditResponseDTO();
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setComment_text(reply.getComment_text());
        commentResponseDTO.setPostDate(LocalDateTime.now());
        commentResponseDTO.setMessage("Comment added successfully!");
        return commentResponseDTO;
    }

    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    public CommentEditResponseDTO editComment(CommentEditResponseDTO editedComment) {
        Comment comment = getCommentById(editedComment.getId());
        String commentText = editedComment.getComment_text();
        Validator.validateEmptyField(commentText,"Comment");
        commentText = commentText.replaceAll(CensoredWords.getRegexCensorship(),"******");
        if (commentText.isEmpty()){
            throw new BadRequestException("Text cannot be empty!");
        }
        editedComment.setPostDate(LocalDateTime.now());
        editedComment.setMessage("Comment edited sucessfully!");
        commentRepository.save(comment);
        return editedComment;
    }

    @Synchronized
    public int likeComment(long commentId, long userId) {
        Comment comment = getCommentById(commentId);
        User user = getUserById(userId);
        if(user.getLikedComments().contains(comment)){
            throw new BadRequestException("You already liked this comment!");
        }
        comment.getLikers().add(user);
        if (comment.getDislikers().contains(user)){
            comment.getDislikers().remove(user);
        }
        commentRepository.save(comment);
        return comment.getLikers().size();
    }

    @Synchronized
    public int dislikeComment(long commentId, long userId) {
        Comment comment = getCommentById(commentId);
        User user = getUserById(userId);
        if(user.getDislikedComments().contains(comment)){
            throw new BadRequestException("You already disliked this comment!");
        }
        comment.getDislikers().add(user);
        if (comment.getLikers().contains(user)){
            comment.getLikers().remove(user);
        }
        commentRepository.save(comment);
        return comment.getDislikers().size();
    }

    private Comment getCommentById(long id){
        return commentRepository.findById(id).orElseThrow(()-> new NotFoundException("Comment not found!"));
    }

    private User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Article getArticleById(long id){
        return articleRepository.findById(id).orElseThrow( ()-> new NotFoundException("Article not found!"));
    }

}
