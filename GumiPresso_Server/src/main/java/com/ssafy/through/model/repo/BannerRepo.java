package com.ssafy.through.model.repo;

import java.util.List;

import com.ssafy.through.model.dto.Banner;

public interface BannerRepo {
	int insertBanner(Banner banner);
	List<Banner> selectBanner();
	int updateBanner(Banner banner);
}
