package com.sportal.model.repository;

import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryById(Long id);

    Category findByCategory(String category);

    @Query(value = "FROM Category  WHERE category LIKE %:category%")
    Category findByCategoryUsingLike(@Param("category") String category);
}
