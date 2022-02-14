package com.sportal.controller;

import com.sportal.model.dto.UserGetAllResponseDTO;
import com.sportal.model.dto.UserGetByIdResponseDTO;
import com.sportal.model.dto.UserRegisterRequestDTO;
import com.sportal.model.dto.UserRegisterResponseDTO;
import com.sportal.model.pojo.User;
import com.sportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserGetAllResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public UserGetByIdResponseDTO getById(@PathVariable int id){
        return userService.getById(id);
    }


    @PostMapping(value = "/users/register")
    public UserRegisterResponseDTO register(@RequestBody UserRegisterRequestDTO userDTO) {
        //TODO add session
        return userService.registerUser(userDTO);
    }


}
