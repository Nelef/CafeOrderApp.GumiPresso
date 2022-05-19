package com.ssafy.through.model.repo;

import com.ssafy.through.model.dto.ImageFile;

public interface ImageRepo {
	int insert(ImageFile imageFile);
	ImageFile select(int id);
}
