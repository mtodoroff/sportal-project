package com.sportal.controller;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.UnauthorizedException;
import com.sportal.model.dto.userDTO.*;
import com.sportal.model.pojo.Category;
import com.sportal.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class UserController {
    public static final String LOGGED_IN = "LOGGED_IN";
    public static final String LOGGED_FROM = "LOGGED_FROM";
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserGetAllResponseDTO> getAllUsers() {
        //TODO Add right only for admin users
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public UserGetByIdResponseDTO getById(@PathVariable int id) {
        return userService.getById(id);
    }


    @PostMapping("/users/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO userDTO) {
        return new ResponseEntity(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userDTO, HttpSession session, HttpServletRequest request) {
        //TODO check if session exists

        UserLoginResponseDTO user = userService.login(userDTO);
        session.setAttribute(LOGGED_IN, user.getId());
        session.setMaxInactiveInterval(60 * 60 * 3);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping("/users/edit")
    public ResponseEntity<UserEditDTO> editUser(@RequestBody UserEditDTO userDTO, HttpSession session) {
        //TODO check if user is logged in
        return new ResponseEntity(userService.editUser(userDTO), HttpStatus.OK);
    }

    @PatchMapping("/users/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        //TODO check if user is logged in
        userService.changePassword(userChangePasswordRequest);
        return ResponseEntity.ok().body("Password was changed.");
    }
}
