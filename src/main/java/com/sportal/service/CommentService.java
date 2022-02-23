package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.ArticleResponseDTO;
import com.sportal.model.dto.commentDTOs.CommentAddReplyRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentAddRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentEditRequestDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Comment;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.CommentRepository;
import com.sportal.model.repository.UserRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;


@Service
public class CommentService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    public ArticleResponseDTO addComment(User user, CommentAddRequestDTO addedComment){
        Article article = getArticleById(addedComment.getArticle_id());
        Comment comment = new Comment(addedComment.getComment_text(),article,user);
        commentRepository.save(comment);
        return new ArticleResponseDTO(article);
    }

    public boolean userOwnsComment(long userId, long commentId) {
        Comment comment = getCommentById(commentId);
        return comment.getUser().getId() == userId;
    }

    public ArticleResponseDTO addCommentReply(User loggedUser, CommentAddReplyRequestDTO reply) {
        Comment parent = commentRepository.findById(reply.getParent_comment_id()).orElseThrow(() -> new NotFoundException("Comment not found!"));
        Article article = parent.getArticle();
        Comment comment = new Comment(reply.getComment_text(), article, loggedUser, parent);
        commentRepository.save(comment);
        return new ArticleResponseDTO(article);
    }

    public void deleteComment(long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

    @Transactional
    public ArticleResponseDTO editComment(CommentEditRequestDTO editedComment) {
        Comment comment = getCommentById(editedComment.getId());
        String text = editedComment.getComment_text();
        if (text.isEmpty()){
            throw new BadRequestException("Text cannot be empty!");
        }
        comment.setCommentText(editedComment.getComment_text());
        comment.setUpdated_at(LocalDateTime.now());
        commentRepository.save(comment);
        long articleId =  comment.getArticle().getId();
        Article article = getArticleById(articleId);
        return new ArticleResponseDTO(article);
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
