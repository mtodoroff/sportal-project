package com.sportal.model.repository;

import com.sportal.model.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(value = "SELECT * FROM comments as c WHERE c.id = :id",nativeQuery = true)
    @Override
    Optional<Comment> findById(@Param("id") Long id);

    

}
