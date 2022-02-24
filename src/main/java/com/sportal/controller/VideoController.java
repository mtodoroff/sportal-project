package com.sportal.controller;

import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.videoDTOs.DeleteVideoResponseDTO;
import com.sportal.model.pojo.User;
import com.sportal.service.SessionService;
import com.sportal.service.VideoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;


@RestController
public class VideoController {
    @Autowired
    VideoService videoService;
   @Autowired
    SessionService sessionService;

    @PostMapping("/articles/video")
    public String uploadArticleVideo(@RequestParam(name = "file") MultipartFile file,
                                     @RequestParam(value = "article_id") Long articleId, HttpServletRequest request) {
        User user  = sessionService.getLoggedUser(request.getSession());
        sessionService.validateAdmin(user);
        return videoService.uploadVideo(file,articleId,request);
    }
    @SneakyThrows
    @GetMapping("/files/{video}")
    public void downloadVideo(@PathVariable String video,HttpServletResponse response){

        File f=new File("uploads"+File.separator+video);
        if(!f.exists()){
            throw new NotFoundException("File doesn't exist");
        }
        Files.copy(f.toPath(),response.getOutputStream());
    }
    @DeleteMapping("/video/{videoId}")
    public ResponseEntity<DeleteVideoResponseDTO>deleteVideoById(@PathVariable(value = "videoId") Long videoId,HttpServletRequest request){
        User user  = sessionService.getLoggedUser(request.getSession());
        sessionService.validateAdmin(user);
        return videoService.deleteById(videoId);
    }
}
