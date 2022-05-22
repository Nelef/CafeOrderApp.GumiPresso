package com.ssafy.through.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.through.model.dto.ImageFile;

public interface ImageService {
	int insert(ImageFile imageFile);
	ImageFile select(int id);	
	void deleteFile(String imageFileName);
	void fileUpload(MultipartFile multipartFile, String flag);
}
