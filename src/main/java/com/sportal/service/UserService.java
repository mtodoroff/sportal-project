package com.sportal.service;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.exceptions.BadRequestException;
import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dao.CommentDAO;
import com.sportal.model.dto.articleDTOs.ArticleWithoutUserDTO;
import com.sportal.model.dto.categoryDTOs.CategoryWithoutArticleDTO;
import com.sportal.model.dto.commentDTOs.CommentResponseDTO;
import com.sportal.model.dto.userDTOs.*;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.Comment;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.CommentRepository;
import com.sportal.model.repository.UserRepository;
import com.sportal.util.PasswordBuilder;
import com.sportal.util.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JavaMailSender emailSender;


    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userDTO) {
        Validator.validateEmptyField(userDTO.getFirst_name(), "First name");
        Validator.validateEmptyField(userDTO.getLast_name(), "Last name");
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
        Validator.validatePhone(userDTO.getPhone());
        Validator.validateEmail(userDTO.getEmail());
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
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
    public void changePassword(UserChangePasswordRequestDTO userChangePasswordRequestDTO) {
        User user = userRepository.findById(userChangePasswordRequestDTO.getId()).orElseThrow(()-> new NotFoundException("User not found!"));

        if (!passwordEncoder.matches(userChangePasswordRequestDTO.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Please provide valid password");
        }
        if (!userChangePasswordRequestDTO.getNewPassword().equals(userChangePasswordRequestDTO.getConfirmNewPassword())) {
            throw new BadRequestException("Please provide valid conf password");
        }
        String oldPassword = user.getPassword();
        String newPassword = userChangePasswordRequestDTO.getNewPassword();
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

    public UserGetByIdResponseDTO getById(long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        UserGetByIdResponseDTO dto = modelMapper.map(user, UserGetByIdResponseDTO.class);
        return dto;
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User doesn't exists");
        }
        userRepository.deleteById(id);
    }

    public List<CommentResponseDTO> getUserComments(long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        CommentResponseDTO dto = new CommentResponseDTO();
        List<CommentResponseDTO> comments = commentDAO.commentsByUserId(id);
        return comments;
    }

    public void resetPassword(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Please provide valid email!"));
        String newPassword = PasswordBuilder.generatePassword(20);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        SimpleMailMessage message = new SimpleMailMessage();
        String msg = "Hello "+ user.getUsername() + "\n" + "Your new password is: " + newPassword;
        message.setTo(user.getEmail());
        message.setSubject("Sprotal Password change");
        message.setText(msg);
        emailSender.send(message);
    }
}
