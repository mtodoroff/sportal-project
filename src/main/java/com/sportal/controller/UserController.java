package com.sportal.controller;

import com.sportal.exceptions.BadRequestException;
import com.sportal.model.dto.MessageResponseDTO;
import com.sportal.model.dto.commentDTOs.CommentResponseDTO;
import com.sportal.model.dto.userDTOs.*;
import com.sportal.model.pojo.Comment;
import com.sportal.model.pojo.User;
import com.sportal.service.SessionService;

import com.sportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;


    @GetMapping("/users")
    public List<UserGetAllResponseDTO> getAllUsers(HttpSession session) {
        User user = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetByIdResponseDTO> getById(@PathVariable int id,HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/users/{id}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getUserComments(@PathVariable long id, HttpSession session) {
        User user  = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(user);
        return ResponseEntity.ok(userService.getUserComments(id));
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userDTO, HttpSession session,HttpServletRequest request) {
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
    public ResponseEntity<MessageResponseDTO> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity(new MessageResponseDTO("You successfully deleted this user"), HttpStatus.OK);
    }

    @PutMapping("/users/edit")
    public ResponseEntity<UserEditDTO> editUser(@RequestBody UserEditDTO userDTO, HttpSession session) {
        if (!sessionService.userAlreadyLogged(session)) {
            throw new BadRequestException("You must be logged in!");
        }
        return new ResponseEntity<>(userService.editUser(userDTO), HttpStatus.OK);
    }

    @PatchMapping("/users/change-password")
    public ResponseEntity<MessageResponseDTO> changePassword(@RequestBody UserChangePasswordRequestDTO userChangePasswordRequestDTO, HttpSession session, HttpServletRequest request) {
        if (!sessionService.userAlreadyLogged(session)) {
            throw new BadRequestException("You must be logged in!");
        }
        User loggedUser = sessionService.getLoggedUser(session);
        if (loggedUser.getId() != userChangePasswordRequestDTO.getId()){
            throw new BadRequestException("You have permission to change only your password");
        }
        userService.changePassword(userChangePasswordRequestDTO);
        return new ResponseEntity(new MessageResponseDTO("You successfully changed your password!"), HttpStatus.OK);
    }
    //TODO check if email is in DB
    @PutMapping("/users/reset-password/{id}")
    public void resetPassword(@PathVariable long id){
        userService.resetPassword(id);
    }
}
