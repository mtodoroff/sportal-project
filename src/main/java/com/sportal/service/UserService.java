package com.sportal.service;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.dto.userDTOs.*;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.UserRepository;
import com.sportal.util.Validator;
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
        Validator.validateEmail(userDTO.getEmail());
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        Validator.validateEmptyField(userDTO.getUsername(), "Username");
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
        Validator.validateEmptyField(userDTO.getFirst_name(), "First name");
        Validator.validateEmptyField(userDTO.getLast_name(), "Last name");
        Validator.validatePhone(userDTO.getPhone());
        userDTO.set_admin(false);
        User user = new User(userDTO);
        user = userRepository.save(user);
        return modelMapper.map(user, UserRegisterResponseDTO.class);
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO userDTO) {
        Validator.validateEmptyField(userDTO.getUsername(), "Username");
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
            Validator.validateEmptyField(userEditDTO.getFirstName(), "First name");
            user.setFirstName(userEditDTO.getFirstName());
            Validator.validateEmptyField(userEditDTO.getLastName(), "Last name");
            user.setLastName(userEditDTO.getLastName());
            Validator.validatePhone(userEditDTO.getPhone());
            user.setPhone(userEditDTO.getPhone());
            user.setUpdated_at(LocalDateTime.now());
            userRepository.save(user);
            return modelMapper.map(user, UserEditDTO.class);
        } else {
            throw new NotFoundException("User not found");
        }
    }


    @Transactional
    public void changePassword(UserChangePasswordRequest userChangePasswordRequest) {
        User user = userRepository.findById(userChangePasswordRequest.getId()).orElseThrow(()-> new NotFoundException("User not found!"));

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Please provide valid password");
        }
        if (!userChangePasswordRequest.getNewPassword().equals(userChangePasswordRequest.getConfirmNewPassword())) {
            throw new BadRequestException("Please provide valid conf password");
        }
        String oldPassword = user.getPassword();
        String newPassword = userChangePasswordRequest.getNewPassword();
        Validator.validatePassword(newPassword);
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
            ArticleWithoutUserDTO art = (modelMapper.map(a, ArticleWithoutUserDTO.class));
            Category cat = a.getCategory_id();
            CategoryWithoutArticleDTO categoryWithoutArticleDTO = new CategoryWithoutArticleDTO(cat);
            art.setCategory(categoryWithoutArticleDTO);
            currentDTO.add(art);
        }
        dto.setArticle(currentDTO);
        return dto;
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User doesn't exists");
        }
        userRepository.deleteById(id);
    }
}
