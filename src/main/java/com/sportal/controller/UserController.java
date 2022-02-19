package com.sportal.controller;

import com.sportal.exceptions.BadRequestException;
import com.sportal.model.dto.userDTOs.*;
import com.sportal.service.SessionService;

import com.sportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;


    @GetMapping("/users")
    public List<UserGetAllResponseDTO> getAllUsers() {
        //TODO Add right only for admin users
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserWithArticleDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }


    @PostMapping("/users/register")
    @Validated
    public ResponseEntity<UserRegisterResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    @Validated
    public ResponseEntity<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO userDTO, HttpSession session,HttpServletRequest request) {
        if (sessionService.userAlreadyLogged(session)) {
            throw new BadRequestException("You are already logged in!");
        }
        UserLoginResponseDTO userLoginResponseDTO = userService.login(userDTO);
        session.setMaxInactiveInterval(60 * 60 * 3);
        sessionService.loginUser(session, userLoginResponseDTO.getId(),request);
        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<UserLogoutDTO> logout(HttpSession session, HttpServletRequest request) {
        session.invalidate();
        return new ResponseEntity<>(new UserLogoutDTO("You have been loged out."), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("\"message\": \"Profile deleted successfully.\"");
    }

    @PutMapping("/users/edit")
    @Validated
    public ResponseEntity<UserEditDTO> editUser(@Valid @RequestBody UserEditDTO userDTO, HttpSession session) {
        if (!sessionService.userAlreadyLogged(session)) {
            throw new BadRequestException("You must be logged in!");
        }
        return new ResponseEntity<>(userService.editUser(userDTO), HttpStatus.OK);
    }

    @PatchMapping("/users/change-password")
    @Validated
    public ResponseEntity<String> changePassword(@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest, HttpSession session, HttpServletRequest request) {
        if (!sessionService.userAlreadyLogged(session)) {
            throw new BadRequestException("You must be logged in!");
        }

        userService.changePassword(userChangePasswordRequest);
        return ResponseEntity.ok().body("Password was changed successfully.");
    }
}
