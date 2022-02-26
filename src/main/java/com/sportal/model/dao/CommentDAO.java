package com.sportal.model.dao;

import com.sportal.model.dto.commentDTOs.CommentResponseDTO;
import com.sportal.model.pojo.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Getter
@Setter
public class CommentDAO {
    @Autowired
    private JdbcTemplate jdbc;
    private final String FIND_USER_COMMENTS_BY_ID = "SELECT id,comment_text, FROM comments as c where c.user_id = ?";


    public List<CommentResponseDTO> commentsByUserId(long id){
        List<CommentResponseDTO> userComments = new ArrayList<>();
        String sql = "SELECT id,comment_text,created_at FROM comments as c where c.user_id = " + id;
        try(Connection connection = jdbc.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment= new Comment();
                comment.setId(rs.getInt(1));
                comment.setCommentText(rs.getString(2));
                comment.setCreated_at(rs.getTimestamp(3).toLocalDateTime());
                userComments.add(new CommentResponseDTO(comment));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userComments;
    }

}
