package com.sportal.model.repository;

import com.sportal.model.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

 Article findByContent(String content);
 Article findByTitle(String title);
}
