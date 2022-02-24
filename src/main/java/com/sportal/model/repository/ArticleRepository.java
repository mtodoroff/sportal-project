package com.sportal.model.repository;

import com.sportal.model.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

 @Query(value = "FROM Article  WHERE title LIKE %:title%")
 List<Article> findByTitleUsingLike(@Param("title") String title) ;
 @Query(value = "SELECT * FROM articles ORDER BY views DESC LIMIT 5",nativeQuery = true)
 List<Article> findTopByViews();
 @Query(value = "SELECT * FROM articles ORDER BY created_at DESC LIMIT 5;",nativeQuery = true)
 List<Article> latestFiveArticles();
}
