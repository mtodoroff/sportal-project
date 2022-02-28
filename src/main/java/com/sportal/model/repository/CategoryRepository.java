package com.sportal.model.repository;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryById(Long id);

    @Query(value = "FROM Category  WHERE category LIKE :category%")
    List<Category> findByCategoryUsingLike(@Param("category") String category);

    @Query(value = "FROM Article  WHERE category_id.category LIKE %:category_id%")
    List<Article> findByTitleUsingLikeCategory(@Param("category_id") String category_id, Pageable pageable);
}
