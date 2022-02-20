package com.sportal.service;

import com.sportal.exceptions.NotFoundException;
import com.sportal.model.pojo.Picture;
import com.sportal.model.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;
    //TODO implement
    public Picture uploadImage(String filePath, MultipartFile file) {
        File pFile = new File(filePath + File.separator + "_" + System.nanoTime() + ".png");
        try (OutputStream os = new FileOutputStream(pFile)) {
            os.write(file.getBytes());
            Picture picture = new Picture();
            //TODO get url and save to picture repo
            return picture;
        } catch (FileNotFoundException e) {
            throw new NotFoundException("No files to upload" + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
