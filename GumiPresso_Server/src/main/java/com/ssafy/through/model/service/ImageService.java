package com.ssafy.through.model.service;

import com.ssafy.through.model.dto.ImageFile;

public interface ImageService {
	int insert(ImageFile imageFile);
	ImageFile select(int id);
}
