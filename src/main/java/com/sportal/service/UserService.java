package com.sportal.service;

import com.sportal.exceptions.BadRequestException;
import com.sportal.model.dto.UserRegisterRequestDTO;
import com.sportal.model.dto.UserRegisterResponseDTO;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.sportal.util.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userDTO){
        if(userRepository.findUserByEmail(userDTO.getEmail()) != null){
            throw new BadRequestException("Email already exists");
        }
        Validator.validateUsername(userDTO.getUsername());
        if(userRepository.findUserByUsername(userDTO.getUsername()).isPresent()){
            throw new BadRequestException("Username already exists");
        }
        Validator.validatePassword(userDTO.getPassword());
        Validator.validatePassword(userDTO.getConfirmPassword());
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            throw new BadRequestException("The passwords do not match!");
        }
        User user = new User(userDTO);
        user = userRepository.save(user);
        UserRegisterResponseDTO responseUserDTO = new UserRegisterResponseDTO(user);
        return responseUserDTO;
    }

//    public List<User> getAllUsers(){
//
//    }
}
