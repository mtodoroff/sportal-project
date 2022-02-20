package com.sportal.controller;
import com.sportal.exceptions.NotFoundCategory;
import com.sportal.model.pojo.Category;
import com.sportal.model.repository.CategoryRepository;
import com.sportal.model.repository.UserRepository;
import com.sportal.service.CategoryService;
import com.sportal.service.SessionService;
import com.sportal.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
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

    @PutMapping("/add/category")
    public ResponseEntity<Category> addCategory(@RequestBody  Category category, HttpSession session, HttpServletRequest request) {
        validateLoginAndAdmin(session, request);
        Category c=categoryService.createCategory(category);
        return ResponseEntity.ok(c);
    }
    @GetMapping("get/all/category")
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<Category> deleteById(@PathVariable long id,HttpSession session,HttpServletRequest request){
        validateLoginAndAdmin(session, request);
        Optional<Category>opt=categoryRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundCategory("No found Category");
        }
        Category category =opt.get();
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(category);
    }
    @PutMapping("/edit/category")
    public ResponseEntity<Category> edit(@RequestBody Category category,HttpSession session,HttpServletRequest request){
        validateLoginAndAdmin(session, request);
        Optional<Category>opt= categoryRepository.findById(category.getId());
        if(!opt.isPresent()){
            throw new NotFoundCategory("No found Category");
        }
        Category currentCategory =opt.get();
        currentCategory.setCategory(category.getCategory());
        categoryRepository.save(category);
        return ResponseEntity.ok(currentCategory);
    }

    @GetMapping("/get/by/{name}")
    public ResponseEntity<Category> findByName(@PathVariable String name){
        Optional<Category>opt= Optional.ofNullable(categoryRepository.findByCategory(name));
        if(!opt.isPresent()){
            throw new NotFoundCategory("No found category");
        }
        Category category=opt.get();
        return ResponseEntity.ok(category);
    }

    private void validateLoginAndAdmin( HttpSession session, HttpServletRequest request) {
        sessionService.validateLogin(session, request);
        User u = userRepository.findUserById((Long) session.getAttribute(SessionService.USER_ID));
        sessionService.validateAdmin(u);
    }

}
