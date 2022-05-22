package com.ssafy.through.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.through.model.dto.Banner;
import com.ssafy.through.model.repo.BannerRepo;

@Service
public class BannerServiceImpl implements BannerService{

	@Autowired
	BannerRepo repo;
	@Override
	public int insertBanner(Banner banner) {		
		return repo.insertBanner(banner);
	}

	@Override
	public List<Banner> selectBanner() {
		return repo.selectBanner();
	}

	@Override
	public int updateBanner(Banner banner) {
		return repo.updateBanner(banner);
	}
	
}
