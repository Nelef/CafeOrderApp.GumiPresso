package com.ssafy.through.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import org.springframework.stereotype.Component;

@Component
public class RSAUtil {

	private static KeyPair rsaKeyPair;
    /**
     * 1024비트 RSA 키쌍을 생성
     */
    public static KeyPair getRSAKeyPair() throws NoSuchAlgorithmException {
        if(rsaKeyPair == null) {
        	KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        	gen.initialize(2048, new SecureRandom());
        	rsaKeyPair = gen.genKeyPair();
        	System.out.println("KEY PAIR GENERATED####");
        }
        return rsaKeyPair;
    }
    
    public static String getStringPublicKey() throws Exception {    	
    	return Base64.getEncoder().encodeToString(getRSAKeyPair().getPublic().getEncoded());
    }

    /**
     * Public Key로 RSA 암호화를 수행
     * @throws InvalidKeySpecException 
     * @throws InvalidAlgorithmParameterException 
     */
    public static String encryptRSA(String plainText, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");			
		RSAPublicKeySpec pkspec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);  
		String pubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("###PubKey:"+pubKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(bytePlain);
    }

    /**
     * Private Key로 RSA 복호화를 수행
     * @throws InvalidKeySpecException 
     * @throws InvalidAlgorithmParameterException 
     */
    public static String decryptRSA(String encrypted, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
        System.out.println(byteEncrypted.length);
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");			
		PKCS8EncodedKeySpec pkspec = (PKCS8EncodedKeySpec) keyFactory.getKeySpec(privateKey, PKCS8EncodedKeySpec.class);  
		String privateKeyModulus = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		System.out.println("###PrivateKey: "+ privateKeyModulus);
		System.out.println("###EncryptedPW: "+encrypted);
        

        cipher.init(Cipher.DECRYPT_MODE, privateKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        System.out.println("###DecryptedPW: "+new String(bytePlain));
        return new String(bytePlain, "utf-8");
    }

    public static PublicKey getPublicKeyFromBase64Encrypted(String base64PublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PubKey = Base64.getDecoder().decode(base64PublicKey);

        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decodedBase64PubKey));
    }

    public static PrivateKey getPrivateKeyFromBase64Encrypted(String base64PrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PrivateKey = Base64.getDecoder().decode(base64PrivateKey);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedBase64PrivateKey));
    }

}