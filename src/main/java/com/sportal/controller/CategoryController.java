package com.sportal.controller;

import com.sportal.exceptions.NotFoundCategory;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.ArticleWithOwnerDTO;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithArticlesDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.CategoryRepository;
import com.sportal.model.repository.UserRepository;
import com.sportal.service.CategoryService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        Category c = categoryService.createCategory(category);
        return new ResponseEntity(c, HttpStatus.CREATED);
    }
    //TODO Remove circular JSON
    @GetMapping("/categories")
    public List<CategoryWithArticlesDTO> getAllCategory() {
        return categoryService.findAllArticles();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("\"message\": \"Category removed successfully.\"");
    }

    @PutMapping("/categories")
    @Validated
    public ResponseEntity<Category> edit(@Valid @RequestBody Category category, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        Optional<Category> opt = categoryRepository.findById(category.getId());
        if (!opt.isPresent()) {
            throw new NotFoundException("Category not found");
        }
        Category currentCategory = opt.get();
        currentCategory.setCategory(category.getCategory());
        categoryRepository.save(category);
        return ResponseEntity.ok(currentCategory);
    }

    @GetMapping("/categories/search")
    public List<ArticleWithoutUserDTO>getByCategory(@RequestParam(value = "category") String category){
       return categoryService.getByCategory(category);
    }

}
