package com.sportal.model.repository;

import com.sportal.model.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

 @Query(value = "FROM Article  WHERE title LIKE %:title%")
 List<Article> findByTitleUsingLike(@Param("title") String title);
 @Query(value = "SELECT * FROM articles ORDER BY views DESC LIMIT 5",nativeQuery = true)
 List<Article> findTopByViews();
 @Query(value = "SELECT * FROM articles ORDER BY created_at DESC LIMIT 5;",nativeQuery = true)
 List<Article> latestFiveArticles();
 @Query(value = "FROM Article  WHERE category_id.category LIKE %:category_id%")
 List<Article> findByTitleUsingLikeCategory(@Param("category_id") String category_id,Pageable pageable);
 @Query(value = "SELECT *FROM articles  JOIN \n" +
         "(SELECT comments.article_id, COUNT(*) AS comment FROM comments GROUP BY article_id LIMIT 10) \n" +
         "comments ON articles.id=comments.article_id",nativeQuery = true)
 List<Article>findByMostComment();
}
