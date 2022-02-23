package com.sportal.controller;

import com.sportal.exceptions.NotFoundException;
import com.sportal.model.pojo.User;
import com.sportal.service.SessionService;
import com.sportal.service.VideoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new NotFoundException("File does not exist");
        }
        Files.copy(f.toPath(),response.getOutputStream());
    }
}
