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



@RestController
public class CategoryController {
    private static final String LOGGED_FROM = "logged_from";
    private static final String LOGGED_IN = "logged_in";
    private CategoryService categoryService;

    @PutMapping("/addcategory")
    public ResponseEntity<Category> addCategory(@RequestBody User u, String category, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
//        validateAdmin(u);
        Category c=categoryService.createCategory(category);
        return ResponseEntity.ok(c);
    }
    private void validateLogin(HttpSession session, HttpServletRequest request) {
        if (session.isNew() || !(Boolean) session.getAttribute(LOGGED_IN)
                || (!request.getRemoteHost().equals(session.getAttribute(LOGGED_FROM)))) {
            throw new BadRequestException("You are not registered");
        }
    }
    //TODO validate admin
//    private void validateAdmin(User u){
//        if(!u.getRoles().getType().toUpperCase().equals("Admin")){
//            throw new UnauthorizedException("You are not admin");
//        }
//    }

}
