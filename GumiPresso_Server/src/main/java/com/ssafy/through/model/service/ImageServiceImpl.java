package com.ssafy.through.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.through.model.dto.ImageFile;
import com.ssafy.through.model.repo.ImageRepo;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private ImageRepo repo;

	@Override
	public int insert(ImageFile imageFile) {
		return repo.insert(imageFile);
	}

	@Override
	public ImageFile select(int id) {
		return repo.select(id);
	}

	@Value("${app.upload.dir:${user.home}}")
	private String uploadDir;
	@Override
	public void fileUpload(MultipartFile multipartFile, String url){
        Path copyOfLocation = Paths.get(url);
        try {
            Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file : " + multipartFile.getOriginalFilename());
        }

    }
	
	
	
}
