package com.sportal.service;

import com.sportal.exceptions.NotFoundException;
import com.sportal.model.dto.imageDTOs.ImageResponseDTO;
import com.sportal.model.dto.imageDTOs.ImageUploadDTO;
import com.sportal.model.pojo.Article;
import com.sportal.model.pojo.Picture;
import com.sportal.model.repository.ArticleRepository;
import com.sportal.model.repository.PictureRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;
@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    ArticleRepository articleRepository;

    @SneakyThrows
    public ImageResponseDTO uploadImage(String filePath, MultipartFile file, ImageUploadDTO imageUploadDTO) {
        File picFile = new File(filePath + File.separator + "_" + System.nanoTime() + ".png");
        OutputStream os = new FileOutputStream(picFile);
            os.write(file.getBytes());
            Picture picture = new Picture();
            Optional<Article> opt = articleRepository.findById(imageUploadDTO.getId());
            if(!opt.isPresent()){
                throw new NotFoundException("Article not found");
            }
            Article article =opt.get();
            picture.setPic_url(picFile.getAbsolutePath());
            picture.setArticle_id(article);
            picture = pictureRepository.save(picture);
            ImageResponseDTO dto=new ImageResponseDTO(picture);
            pictureRepository.findById(picture.getId());
            return dto;
    }

    public long deleteImage(long imageId) {
        Optional<Picture> picture = pictureRepository.findById(imageId);
        if (picture.isEmpty()){
            throw  new NotFoundException("Image not found");
        } else {
            Picture pic = picture.get();
            pictureRepository.delete(pic);
            File f = new File(pic.getPic_url());
            f.delete();
            return pic.getId();
        }
    }
}
