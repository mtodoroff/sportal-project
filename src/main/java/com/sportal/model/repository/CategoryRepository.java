package com.sportal.model.repository;

import com.sportal.model.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryById(Long id);

    Category findByCategory(String category);

}
