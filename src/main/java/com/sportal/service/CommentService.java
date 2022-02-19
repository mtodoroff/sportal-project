package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.ArticleResponseDTO;
import com.sportal.model.dto.commentDTOs.CommentAddRequestDTO;
import com.sportal.model.dto.commentDTOs.CommentEditRequestDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Comment;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.CommentRepository;
import com.sportal.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class CommentService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    public ArticleResponseDTO addComment(User user, CommentAddRequestDTO addedComment){
        Article article = articleRepository.findById(addedComment.getArticleId()).orElseThrow(()-> new NotFoundException("Article not found!"));
        Comment comment = new Comment(addedComment.getComment_text(),article,user);
        commentRepository.save(comment);
        return new ArticleResponseDTO(article);
    }

    public boolean userOwnsComment(long userId, long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NotFoundException("Comment not found!"));
        return comment.getUser().getId() == userId;
    }
    //Todo extract line 42,37,50 in method
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NotFoundException("Comment not found!"));
        commentRepository.delete(comment);
    }

    public ArticleResponseDTO editComment(CommentEditRequestDTO editedComment) {
        Comment comment = commentRepository.findById(editedComment.getId()).orElseThrow(()-> new NotFoundException("Comment not found!"));
        String text = editedComment.getComment_text();
        if (text.isEmpty()){
            throw new BadRequestException("Text cannot be empty!");
        }
        comment.setCommentText(editedComment.getComment_text());
        comment.setUpdated_at(Instant.now());
        commentRepository.save(comment);
        long articleId =  comment.getArticle().getId();
        Article article = articleRepository.findById(articleId).orElseThrow( ()-> new NotFoundException("Article not found!"));
        return new ArticleResponseDTO(article);
    }

}
