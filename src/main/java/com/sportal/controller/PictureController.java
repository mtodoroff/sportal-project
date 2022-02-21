package com.sportal.controller;

import com.sportal.model.dto.imageDTOs.DeleteImageRequestDTO;
import com.sportal.model.dto.imageDTOs.DeleteImageResponseDTO;
import com.sportal.model.dto.imageDTOs.ImageUploadDTO;
import com.sportal.model.pojo.User;
import com.sportal.service.PictureService;
import com.sportal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
@RestController
public class PictureController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private PictureService pictureService;

    @Value("${file.path}")
    private String filePath;


    @PostMapping("/images")
    public ResponseEntity<ImageUploadDTO> addImageToArticle(@RequestParam(name="file") MultipartFile file, HttpSession session) {
        User loggedUser = sessionService.getLoggedUser(session);
        sessionService.validateAdmin(loggedUser);
        return new ResponseEntity(pictureService.uploadImage(filePath, file), HttpStatus.CREATED);
    }

    @DeleteMapping("/images")
    public ResponseEntity<DeleteImageResponseDTO> deleteImage(@RequestBody DeleteImageRequestDTO deleteRq, HttpSession ses){
        User loggedUser = sessionService.getLoggedUser(ses);
        sessionService.validateAdmin(loggedUser);
        return new ResponseEntity(pictureService.deleteImage(deleteRq.getId()),HttpStatus.OK);

    }
}
