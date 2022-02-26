package com.sportal.controller;

import com.sportal.model.dto.MessageResponseDTO;
import com.sportal.model.dto.categoryDTOs.CategorySearchResponseDTO;
import com.sportal.model.dto.categoryDTOs.CategoryRequestEditDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


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
    public ResponseEntity<CategoryWithoutArticleDTO> addCategory(@RequestBody Category category, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        Category c = categoryService.createCategory(category);
        CategoryWithoutArticleDTO dto=new CategoryWithoutArticleDTO();
        dto.setCategory(category.getCategory());
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public List<CategoryWithArticlesDTO> getAllCategory() {
        return categoryService.findAllArticles();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<MessageResponseDTO> deleteById(@PathVariable long id, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        categoryRepository.deleteById(id);
        return new ResponseEntity(new MessageResponseDTO("You successfully deleted category"),HttpStatus.OK);
    }


    @PostMapping("/categories/edit")
    public ResponseEntity<CategoryWithoutArticleDTO> edit(@RequestBody CategoryRequestEditDTO category, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return new ResponseEntity( categoryService.editCategory(category),HttpStatus.OK);

    }

    @GetMapping("/categories/search")
    public ResponseEntity<List<CategorySearchResponseDTO>> searchByCategoryName(@RequestParam(value = "category") String category){
       return new ResponseEntity( categoryService.searchByCategory(category),HttpStatus.OK);
    }

}
