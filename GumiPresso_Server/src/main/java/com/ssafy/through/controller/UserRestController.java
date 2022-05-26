package com.ssafy.through.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.service.KakaoLoginService;
import com.ssafy.through.model.service.NaverLoginService;
import com.ssafy.through.model.service.UserService;
import com.ssafy.through.utils.AESUtil;
import com.ssafy.through.utils.FCMUtil;
import com.ssafy.through.utils.RSAUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/user")
@Api(value = "User")
public class UserRestController {
	@Autowired
	private UserService uService;
	@Autowired
	private KakaoLoginService kService;
	@Autowired
	private NaverLoginService nService;
	@Autowired
	private PasswordEncoder pwEncoder;

	@ApiOperation(value = "가입시 아이디 중복검사 return: Boolean", response = Boolean.class)
	@GetMapping("/join/{id}")
	public ResponseEntity<?> isUsedId(@PathVariable String id) {
		int countId = uService.selectId(id);
		if (countId == 1) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "로그인 버튼 클릭시 -> 로그인 할 유저 정보 return: User", response = User.class)
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) throws Exception {
		User selectedUser = uService.select(user.getId());
		if (selectedUser != null && selectedUser.getId().equals(user.getId())
				&& pwEncoder.matches(selectedUser.getPass(), user.getPass())) {

			Cookie cookie = new Cookie("loginId", selectedUser.getId());
			cookie.setPath("/");
			cookie.setMaxAge(10 * 60); // 초단위,. 600초
			response.addCookie(cookie);

			return new ResponseEntity<User>(selectedUser, HttpStatus.OK);

		}
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUser(@PathVariable String user_id) {
		User user = uService.select(user_id);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
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

	@ApiOperation(value = "로그인 중인 유저 정보 return: User", response = User.class)
	@GetMapping("/me")
	public ResponseEntity<?> me(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					String userId = cookie.getValue();
					User selectedUser = uService.select(userId);
					response.addCookie(cookie);
					return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원가입 버튼 클릭시 -> insert user", response = User.class)
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody User user) {
		try {
			user.setPass(pwEncoder.encode(user.getPass()));
			int result = uService.insert(user);
		} catch (Exception e) {

		} finally {
			user = uService.select(user.getId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}
	
	@PostMapping("/google")
	public ResponseEntity<?> googleLogin(@RequestBody User user, HttpServletResponse response) throws Exception {
		User selectedUser = uService.select(user.getId());
		if (selectedUser != null) {
			Cookie cookie = new Cookie("loginId", selectedUser.getId());
			cookie.setPath("/");
			cookie.setMaxAge(10 * 60); // 초단위,. 600초
			response.addCookie(cookie);
			return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
		}
		else {
			uService.insert(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}		
	}

	@PostMapping("/kakao")
	public ResponseEntity<?> getKakaoToken(@RequestBody String code, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Map<String, Object> userInfo = kService.getUserInfo(code);
		String email = (String) userInfo.get("email");
		String nickname = (String) userInfo.get("nickname");
		User user = new User(email, nickname, pwEncoder.encode("naver"));
		int countId = uService.selectId(email);
		Cookie cookie = new Cookie("loginId", user.getId());
		cookie.setPath("/");
		cookie.setMaxAge(10 * 60); // 초단위,. 600초
		response.addCookie(cookie);
		if (countId == 1) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			int result = uService.insertKakaoUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@PostMapping("/naver")
	public ResponseEntity<?> getNaverToken(@RequestBody String code, HttpServletResponse response) {
		Map userInfo = nService.getUserInfo(code);

		String email = userInfo.get("email").toString();
		String name = userInfo.get("name").toString();

		User user = new User(email, name, pwEncoder.encode("naver"));
		int countId = uService.selectId(email);
		Cookie cookie = new Cookie("loginId", user.getId());
		cookie.setPath("/");
		cookie.setMaxAge(10 * 60); // 초단위,. 600초
		response.addCookie(cookie);

		if (countId == 1) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			int result = uService.insertKakaoUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@PostMapping("/fcm")
	public String fcmTest(@RequestBody Map<String, String> map) {
		FCMUtil fcmUtil = new FCMUtil();
		fcmUtil.send_FCM(map.get("token"), map.get("title"), map.get("content"));
		return "d";
	}

	@PutMapping("/money")
	public ResponseEntity<?> updateMoney(@RequestBody User user) throws Exception {
		KeyPair keyPair = RSAUtil.getRSAKeyPair();
		user.setMoney(Integer.parseInt(RSAUtil.decryptRSA(user.getName(), keyPair.getPrivate())));
		int result = uService.updateMoney(user);
		User selectedUser = uService.select(user.getId());
		if (result > 0) {
			return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
	}

	@GetMapping("/aos")
	public ResponseEntity<?> setPrivateKey(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		Cookie[] cookies = request.getCookies();
		System.out.println(RSAUtil.getStringPublicKey());
		response.addHeader("publicKey", RSAUtil.getStringPublicKey());
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					String userId = cookie.getValue();
					User selectedUser = uService.select(userId);
					response.addCookie(cookie);
					System.out.println("##");
					return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
				}
			}
		}	
		return new ResponseEntity<String>("da", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/aos")
	public ResponseEntity<?> rsaLogin(@RequestBody User user, HttpServletResponse response) throws Exception{
		User selectedUser = uService.select(user.getId());
		KeyPair keyPair = RSAUtil.getRSAKeyPair();

		String parsedPW = RSAUtil.decryptRSA(user.getPass(), keyPair.getPrivate());		
		if(selectedUser != null && selectedUser.getId().equals(user.getId())&& pwEncoder.matches(parsedPW, selectedUser.getPass())) {
			Cookie cookie = new Cookie("loginId", selectedUser.getId());
			cookie.setPath("/");
			cookie.setMaxAge(10 * 60); // 초단위,. 600초
			response.addCookie(cookie);
			return new ResponseEntity<User>(selectedUser, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		
	}
}













