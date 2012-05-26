package com.eincs.athens.olympus.config;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class TokenSecret {
	private static TokenSecret INSTANCE = new TokenSecret();

	static {
		INSTANCE = new TokenSecret();
		TokenSecretConf secretConf = OlympusConf.getInstance()
				.getTokenSecret();
		INSTANCE.aesSecret = new SecretKeySpec(secretConf.getSymmetric(), "AES");
		INSTANCE.hmacSecret = new SecretKeySpec(secretConf.getHmac(), "HmacMD5");
	}

	private SecretKeySpec hmacSecret;
	private SecretKeySpec aesSecret;

	private ThreadLocal<Mac> macLocal = new ThreadLocal<Mac>();
	private ThreadLocal<Cipher> encryptorLocal = new ThreadLocal<Cipher>();
	private ThreadLocal<Cipher> decryptorLocal = new ThreadLocal<Cipher>();

	public static TokenSecret getInstance() {
		return INSTANCE;
	}

	public Cipher getDecryptor() {
		Cipher decryptor = decryptorLocal.get();
		if (decryptor == null) {
			try {
				decryptor = Cipher.getInstance("AES/ECB/PKCS5Padding");
				decryptor.init(Cipher.DECRYPT_MODE, aesSecret);
				decryptorLocal.set(decryptor);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return decryptor;
	}

	public Cipher getEncryptor() {
		Cipher encryptor = decryptorLocal.get();
		if (encryptor == null) {
			try {
				encryptor = Cipher.getInstance("AES/ECB/PKCS5Padding");
				 encryptor.init(Cipher.ENCRYPT_MODE, aesSecret);
				encryptorLocal.set(encryptor);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return encryptor;
	}

	public Mac getMac() {
		Mac mac = macLocal.get();
		if (mac == null) {
			try {
				mac = Mac.getInstance("HmacMD5");
				// mac.init(hmacSecret);
				mac.init(hmacSecret);
				macLocal.set(mac);
			} catch (NoSuchAlgorithmException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mac;
	}

}
