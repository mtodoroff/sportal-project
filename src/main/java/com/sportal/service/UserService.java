package com.sportal.service;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
import com.sportal.model.dto.userDTOs.*;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userDTO) {
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            throw new BadRequestException("The passwords do not match!");
        }
        userDTO.set_admin(false);
        User user = new User(userDTO);
        user = userRepository.save(user);
        return modelMapper.map(user,UserRegisterResponseDTO.class);
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO userDTO) {
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
    public UserEditDTO editUser(UserEditDTO userEditDTO) {
        Optional<User> u = userRepository.findById(userEditDTO.getId());
        if (u.isPresent()) {
            User user = u.get();
            user.setFirstName(userEditDTO.getFirstName());
            user.setLastName(userEditDTO.getLastName());
            user.setPhone(userEditDTO.getPhone());
            user.setUsername(userEditDTO.getUsername());
            //TODO all method works but return status 500, should return
            if (userRepository.findUserByUsername(userEditDTO.getUsername()).isEmpty()) {
                throw new BadRequestException("Username already exists");
            }
            user.setUpdated_at(LocalDateTime.now());
            userRepository.save(user);
            return modelMapper.map(user,UserEditDTO.class);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Transactional
    public void changePassword(UserChangePasswordRequest userChangePasswordRequest) {
        Optional<User> u = userRepository.findById(userChangePasswordRequest.getId());
        User user = u.get();
        String oldPassword = user.getPassword();
        String newPassword = userChangePasswordRequest.getNewPassword();
        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new BadRequestException("New password must be different from the old.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdated_at(LocalDateTime.now());
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

    public UserWithArticleDTO getById(long id) {
        User user = userRepository.getById(id);
        UserWithArticleDTO dto = modelMapper.map(user, UserWithArticleDTO.class);
        Set<Article> article = user.getArticles();
        Set<ArticleWithoutUserDTO> currentDTO = new HashSet<>();

        for (Article a : article) {
            currentDTO.add(modelMapper.map(a, ArticleWithoutUserDTO.class));
        }
        dto.setArticle(currentDTO);
        return dto;
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)){
            throw new BadRequestException("User doesn't exists");
        }
        userRepository.deleteById(id);
    }
}
