package com.sportal.controller;

import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.UnauthorizedException;
import com.sportal.model.pojo.Category;
import com.sportal.service.CategoryService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.sportal.controller.UserController.LOGGED_FROM;
import static com.sportal.controller.UserController.LOGGED_IN;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @PutMapping("/addcategori")
    public ResponseEntity<Category> addCategory(@RequestBody User u, String category, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        validateAdmin(u);
        Category c=categoryService.createCategory(category);
        return ResponseEntity.ok(c);
    }
    private void validateLogin(HttpSession session, HttpServletRequest request) {
        if (session.isNew() || !(Boolean) session.getAttribute(LOGGED_IN)
                || (!request.getRemoteHost().equals(session.getAttribute(LOGGED_FROM)))) {
            throw new BadRequestException("You are not registered");
        }
    }
    private void validateAdmin(User u){
        if(!u.getRoles().getType().toUpperCase().equals("Admin")){
            throw new UnauthorizedException("You are not admin");
        }
    }

}
