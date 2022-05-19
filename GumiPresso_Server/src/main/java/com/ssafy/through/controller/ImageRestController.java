package com.ssafy.through.controller;

import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.through.model.service.ImageService;

import io.swagger.annotations.Api;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/image")
@Api(value = "Image")
public class ImageRestController {
	@Autowired
	ImageService iService;
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		try {
			FileOutputStream fos = new FileOutputStream("c:/ssafy/");
			
		}catch(Exception e) {
			throw new RuntimeException("file save error");
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
