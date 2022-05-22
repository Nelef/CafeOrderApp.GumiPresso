package com.ssafy.through.controller;

import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.through.model.dto.ImageFile;
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
	public ResponseEntity<?> upload(@RequestParam("uploaded_file") MultipartFile imageFile) {
		System.out.println("####");
		String fileName = imageFile.getOriginalFilename();
		String url = "C:/SSAFY/final_gumi0607_10/GumiPresso_Server/src/main/resources/image/"+fileName;
		//iService.fileUpload(imageFile);
		iService.insert(new ImageFile(fileName, url));

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
