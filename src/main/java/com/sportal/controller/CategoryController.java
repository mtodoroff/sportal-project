package com.sportal.controller;
import com.sportal.model.pojo.Category;
import com.sportal.model.repository.UserRepository;
import com.sportal.service.CategoryService;
import com.sportal.service.SessionService;
import com.sportal.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private SessionService sessionService;

    @PutMapping("/add/category")
    public ResponseEntity<Category> addCategory(@RequestBody  Category category, HttpSession session, HttpServletRequest request) {
        sessionService.validateLogin(session,request);
        User u=userRepository.findUserById((Long) session.getAttribute(SessionService.USER_ID));
        sessionService.validateAdmin(u);
        Category c=categoryService.createCategory(category);
        return ResponseEntity.ok(c);
    }
}
