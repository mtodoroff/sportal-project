package com.sportal.service;

import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.videoDTOs.DeleteVideoResponseDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Video;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.VideoRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Service

public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @SneakyThrows
    public String uploadVideo(MultipartFile file, Long articleId, HttpServletRequest request) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = (System.nanoTime() + "." + extension);
        Files.copy(file.getInputStream(), new File("uploads" + File.separator + name).toPath());
        Optional<Article> opt = articleRepository.findById(articleId);
        if (!opt.isPresent()) {
            throw new NotFoundException("Not found Article with this id:" + articleId);
        }
        Video video = new Video();
        video.setVideo_url(name);
        Article article =opt.get();
        video.setArticle(article);
        videoRepository.save(video);
        article.setVideo(video);
        return name;
    }

    public ResponseEntity<DeleteVideoResponseDTO> deleteById(Long videoId) {
        if(videoId==null||videoId<=0){
            throw new NotFoundException("Not found vidoe");
        }
        Video video=videoRepository.getById(videoId);
        DeleteVideoResponseDTO dto=new DeleteVideoResponseDTO();
        dto.setId(video.getId());
        videoRepository.delete(video);
        return ResponseEntity.ok(dto);
    }
}