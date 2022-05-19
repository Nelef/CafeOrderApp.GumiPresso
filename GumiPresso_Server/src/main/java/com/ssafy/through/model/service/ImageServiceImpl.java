package com.ssafy.through.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
