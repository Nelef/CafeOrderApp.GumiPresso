package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.Banner;

public interface BannerService {
	int insertBanner(Banner banner);
	List<Banner> selectBanner();
	int updateBanner(Banner banner);
	int deleteBanner(Banner banner);
}
