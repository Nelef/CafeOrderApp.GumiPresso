package com.ssafy.through.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ssafy.through.model.dto.DateDTO;
import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.Product;
import com.ssafy.through.model.dto.RecentOrder;
import com.ssafy.through.model.dto.Sales;
import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.service.AdminService;
import com.ssafy.through.model.service.ImageService;
import com.ssafy.through.utils.FCMUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/admin")
@Api(value = "Admin")
public class AdminRestContoller {
	@Autowired
	private AdminService aService;
	@Autowired
	private ImageService iService;
	
	@ApiOperation(value = "로그인 버튼 클릭시 -> 로그인 할 유저 정보 return: User", response = User.class)
	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody User user, HttpServletResponse response) throws UnsupportedEncodingException {
		User selectedUser = aService.loginAdmin(user);		
		if (selectedUser != null && selectedUser.getId().equals(user.getId())
				&& selectedUser.getPass().equals(user.getPass())) {
			Cookie cookie = new Cookie("loginId", URLEncoder.encode(selectedUser.getId(), "utf-8"));
			cookie.setPath("/");
			cookie.setMaxAge(10 * 60); // 초단위,. 600초
			response.addCookie(cookie);
			return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}
	
	@ApiOperation(value = "로그인 중인 관리자 정보 return: User", response = User.class)
	@GetMapping("/me")
	public ResponseEntity<?> getAdminUser(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	
		Cookie[] cookies = request.getCookies();		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					String userId = cookie.getValue(); 					
					User selectedUser = aService.getAdminUser(userId);
					response.addCookie(cookie);
					return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
				}
			}
		}	
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "로그아웃 버튼 클릭시 -> ", response = User.class)
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Cookie[] cookies = request.getCookies();
		System.out.println("#####################"+cookies);
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					Cookie removeCookie = new Cookie("loginId", "");
					removeCookie.setPath("/");
					removeCookie.setMaxAge(0);
					response.addCookie(removeCookie);
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}
	
	@ApiOperation(value = "가입시 아이디 중복검사 return: Boolean", response = Boolean.class)
	@GetMapping("/join/{id}")
	public ResponseEntity<?> checkId(@PathVariable String id){
		int countId = aService.checkId(id);
		if(countId == 1) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);			
		}
		else {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "회원가입 버튼 클릭시 -> insert user", response = User.class)
	@PostMapping("/join")
	public ResponseEntity<?> insertAdmin(@RequestBody User user) {
		try {
			System.out.println(user);
			int result = aService.insertAdmin(user);
		}catch(Exception e) {
			
		}finally {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
				
	}
	
	
	@GetMapping("/order/completed")
	public ResponseEntity<?> selectRecentOrderByCompleted(){
		List<Order> orders = aService.selectOrderByCompleted();
		List<RecentOrder> list = aService.convertOrdersToRecentOrder(orders);
		
		return new ResponseEntity<List<RecentOrder>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/order/completed/{id}")
	public ResponseEntity<?> completeOrder(@PathVariable int id){
		aService.orderComplete(id);
		
		List<Order> orders = aService.selectOrderByCompleted();
		List<RecentOrder> list = aService.convertOrdersToRecentOrder(orders);
		
		return new ResponseEntity<List<RecentOrder>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/order/{format}")
	public ResponseEntity<?> getSales(@PathVariable String format, @RequestBody DateDTO date){
		List<Sales> list = new ArrayList<Sales>();
		System.out.println(date);
		if(format.equals("year")) {
			list = aService.selectYear(date);
		}
		else if(format.equals("yeartype")) {
			list = aService.selectYearType(date);
		}
		else if(format.equals("month")) {
			list = aService.selectMonth(date);
		}
		else if(format.equals("monthtype")) {
			list = aService.selectMonthType(date);
		}
		else if(format.equals("day")) {
			list = aService.selectDay(date);
			System.out.println(list);
		}
		else if(format.equals("daytype")) {
			list = aService.selectDayType(date);
		}
		System.out.println(list);
		System.out.println(format);
		return new ResponseEntity<List<Sales>>(list,HttpStatus.OK);		
	}
	
	@PostMapping("/fcm/insert")
	public ResponseEntity<?> insertFCMTokenUser(@RequestBody Map<String, String> map){
		int result = aService.insertFCMTokenUser(map);
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK); 
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/fcm/update")
	public ResponseEntity<?> updateFCMTokenUser(@RequestBody Map<String, String> map){
		int result = aService.updateFCMTokenUser(map);
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK); 
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/fcm")
	public ResponseEntity<?> sendMessageAll(@RequestBody Map<String, String> map){
		List<String> tokenList = aService.selectAllToken();
		FCMUtil fcmUtil = new FCMUtil();
		fcmUtil.send_FCM_All(tokenList, map);
		if(tokenList != null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/product")
	public ResponseEntity<?> insertProduct(@RequestParam("uploaded_file") MultipartFile imageFile, @RequestParam("product") String json){
		Gson gson = new Gson();
		Product product = gson.fromJson(json, Product.class);
		iService.fileUpload(imageFile);
		int result = aService.insertProduct(product);
		if(result > 0) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/product/image")
	public ResponseEntity<?> updateProductImage(@RequestParam("uploaded_file") MultipartFile imageFile, @RequestParam("product") String json){
		Gson gson = new Gson();
		Product product = gson.fromJson(json, Product.class);
		System.out.println(product);
		iService.deleteFile(product.getImg());
		product.setImg(imageFile.getOriginalFilename());		
		iService.fileUpload(imageFile);
		int result = aService.updateProduct(product);
		if(result > 0) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/product")
	public ResponseEntity<?> updateProduct(@RequestBody Product product){	
		int result = aService.updateProduct(product);
		if(result > 0) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id){		
		int result = aService.deleteProduct(id);
		if(result > 0) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}










