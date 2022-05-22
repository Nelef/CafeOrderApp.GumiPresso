package com.ssafy.through.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ssafy.through.model.dto.Banner;
import com.ssafy.through.model.dto.ImageFile;
import com.ssafy.through.model.dto.Product;
import com.ssafy.through.model.service.BannerService;
import com.ssafy.through.model.service.ImageService;

import io.swagger.annotations.Api;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/banner")
@Api(value = "Banner")
public class BannerRestController {
	@Autowired
	BannerService bService;
	@Autowired
	ImageService iService;

	
	@GetMapping("/")
	public ResponseEntity<?> selectBanner() {		
		List<Banner> list = bService.selectBanner();
		if(list.size() > 0) {			
			return new ResponseEntity<List<Banner>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> insertBanner(@RequestParam("uploaded_file") MultipartFile imageFile, @RequestParam("banner") String json) {		
		Gson gson = new Gson();
		Banner banner = gson.fromJson(json, Banner.class);
		banner.setImg(imageFile.getOriginalFilename());
		iService.fileUpload(imageFile);
		int result = bService.insertBanner(banner);		
		if(result > 0) {			
			List<Banner> list = bService.selectBanner();
			return new ResponseEntity<List<Banner>>(list, HttpStatus.OK);			
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateBanner(@RequestBody Banner banner) {		
		int result = bService.updateBanner(banner);
		if(result > 0) {
			List<Banner> list = bService.selectBanner();
			return new ResponseEntity<List<Banner>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/image")
	public ResponseEntity<?> updateBannerImage(@RequestParam("uploaded_file") MultipartFile imageFile, @RequestParam("banner") String json) {		
		Gson gson = new Gson();
		Banner banner = gson.fromJson(json, Banner.class);
		iService.deleteFile(banner.getImg());
		banner.setImg(imageFile.getOriginalFilename());
		iService.fileUpload(imageFile);
		int result = bService.updateBanner(banner);		
		if(result > 0) {			
			List<Banner> list = bService.selectBanner();
			return new ResponseEntity<List<Banner>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteBanner(@RequestBody Banner banner) {	
		iService.deleteFile(banner.getImg());
		int result = bService.deleteBanner(banner);
		if(result > 0) {			
			List<Banner> list = bService.selectBanner();
			return new ResponseEntity<List<Banner>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
