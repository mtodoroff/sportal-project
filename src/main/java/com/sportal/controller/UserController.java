package com.sportal.controller;

import com.sportal.model.dto.UserRegisterRequestDTO;
import com.sportal.model.dto.UserRegisterResponseDTO;
import com.sportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {
    @Autowired
    private UserService userService;

//    @GetMapping("/users")
//    public List<User> getAllUsers(){
//
//    }


    @PostMapping(value = "/users/register")
    public UserRegisterResponseDTO register(@RequestBody UserRegisterRequestDTO userDTO) {
        //TODO add session
        return userService.registerUser(userDTO);
    }


}
