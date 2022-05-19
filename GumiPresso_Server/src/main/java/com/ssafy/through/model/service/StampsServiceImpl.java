package com.ssafy.through.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.Stamps;
import com.ssafy.through.model.repo.StampsRepo;

@Service
public class StampsServiceImpl implements StampsService {

	@Autowired
	private StampsRepo repo;

	@Transactional
	@Override
	public int insert(Stamps stamps) {
		return repo.insert(stamps);
	}

}
