package com.sportal.model.dao;

import com.sportal.model.dto.commentDTOs.CommentEditResponseDTO;
import com.sportal.model.dto.commentDTOs.CommentResponseDTO;
import com.sportal.model.pojo.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "SELECT id,comment_text,created_at,\n" +
                "count(ulc.comment_id) as likes,\n" +
                "count(udc.comment_id) as dislikes\n" +
                "FROM comments as c\n" +
                "left Join users_like_comments ulc on (c.id = ulc.comment_id)\n" +
                "left join users_dislike_comments udc on (c.id = udc.comment_id)\n" +
                "where c.user_id = \n" + id + "\n" +
                "group by ulc.comment_id,c.id ";
        try(Connection connection = jdbc.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
                commentResponseDTO.setId(rs.getInt(1));
                commentResponseDTO.setComment_text(rs.getString(2));
                commentResponseDTO.setPostDate(rs.getTimestamp(3).toLocalDateTime());
                commentResponseDTO.setLikes(rs.getInt(4));
                commentResponseDTO.setDislikes(rs.getInt(5));
                userComments.add(commentResponseDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userComments;
    }

}
