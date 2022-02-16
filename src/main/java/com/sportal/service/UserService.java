package com.sportal.service;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.userDTO.*;
import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.sportal.util.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userDTO) {
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        Validator.validateUsername(userDTO.getUsername());
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
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

    public UserLoginResponseDTO login(UserLoginRequestDTO userDTO) {
        Validator.validateUsername(userDTO.getUsername());
        Optional<User> userOpt = userRepository.findUserByUsername(userDTO.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                return new UserLoginResponseDTO(user);
            } else {
                throw new AuthenticationException("Username or password is invalid");
            }
        } else {
            throw new AuthenticationException("Username or password is invalid");
        }
    }

    @Transactional
    public UserEditDTO editUser(UserEditDTO userDTO) {
        Optional<User> u = userRepository.findById(userDTO.getId());
        if (u.isPresent()) {
            User user = u.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPhone(userDTO.getPhone());
            user.setUpdated_at(Instant.now());
            userRepository.save(user);
            return new UserEditDTO(user);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Transactional
    public void changePassword(UserChangePasswordRequest userChangePasswordRequest) {
        if (!userChangePasswordRequest.getNewPassword().equals(userChangePasswordRequest.getConfirmNewPassword())) {
            throw new BadRequestException("Passwords do not match.");
        }
        Optional<User> u = userRepository.findById(userChangePasswordRequest.getId());
        User user = u.get();
        String oldPassword = user.getPassword();
        String newPassword = userChangePasswordRequest.getNewPassword();
        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new BadRequestException("New password must be different from the old.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdated_at(Instant.now());
        userRepository.save(user);
    }

    public List<UserGetAllResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserGetAllResponseDTO> userGetAllResponseDTOS = new ArrayList<>();
        for (User user : users) {
            userGetAllResponseDTOS.add(new UserGetAllResponseDTO(user));
        }
        return userGetAllResponseDTOS;
    }

    public UserGetByIdResponseDTO getById(long id) {
        User user = userRepository.getById(id);
        UserGetByIdResponseDTO userGetByIdResponseDTO = new UserGetByIdResponseDTO(user);
        return userGetByIdResponseDTO;
    }
}
