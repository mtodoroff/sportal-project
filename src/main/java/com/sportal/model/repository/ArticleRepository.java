package com.sportal.model.repository;

import com.sportal.model.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

 Article findByContent(String content);
 Article findByTitle(String title);
 @Query(value = "SELECT * FROM articles ORDER BY views DESC LIMIT 5",nativeQuery = true)
 List<Article> findTopByViews();
 @Query(value = "SELECT * FROM articles ORDER BY created_at DESC LIMIT 5;",nativeQuery = true)
 List<Article> latestFiveArticles();

}
