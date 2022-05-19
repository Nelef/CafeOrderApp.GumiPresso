package com.ssafy.through.model.service;

import java.io.FileInputStream;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FCMUtil {

	public void send_FCM(String tokenId, String title, String content) {
		try {
			FileInputStream refreshToken = new FileInputStream("C:\\SSAFY\\final_gumi0607_10\\GumiPresso_Server\\src\\main\\resources\\firebase\\firebase_service_key.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken)).setDatabaseUrl("firebase-adminsdk-xa105@gumipresso.iam.gserviceaccount.com")
					.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			String registrationToken = tokenId;
			Message msg = Message.builder()
					.setAndroidConfig(
							AndroidConfig.builder()
							.setTtl(3600 * 1000)
							.setPriority(AndroidConfig.Priority.NORMAL)
							.setNotification(AndroidNotification.builder()
									.setTitle(title)
									.setBody(content)
									.setIcon("stock_ticker_update")
									.setColor("#f45342").build())
							.build())
					.setToken(registrationToken)
					.build();
			String response = FirebaseMessaging.getInstance().send(msg);
			System.out.println("Successfully sent message: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
