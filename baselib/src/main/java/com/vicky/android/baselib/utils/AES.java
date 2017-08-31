package com.vicky.android.baselib.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

	public static byte[] encrypt(byte[] data, byte[] key) {
		if (key.length != 16) {
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, seckey);
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static byte[] decrypt(byte[] data, byte[] key) {
		if (key.length != 16) {
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, seckey);
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static String encryptToBase64(String data, String key) {
		try {
			byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING));
			return new String(Base64.encode(valueByte,Base64.DEFAULT));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}

	}

	public static String decryptFromBase64(String data, String key) {
		try {
			byte[] originalData = Base64.decode(data,Base64.DEFAULT);
			byte[] valueByte = decrypt(originalData, key.getBytes(CHAR_ENCODING));
			return new String(valueByte, CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static String encryptWithKeyBase64(String data, String key) {
		try {
			byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), Base64.decode(key, Base64.DEFAULT));
			return new String(Base64.encode(valueByte,Base64.DEFAULT));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static String decryptWithKeyBase64(String data, String key) {
		try {
			byte[] originalData = Base64.decode(data,Base64.DEFAULT);
			byte[] valueByte = decrypt(originalData, Base64.decode(key, Base64.DEFAULT));
			return new String(valueByte, CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static void main(String[] args) {
		System.out.println(decryptFromBase64("MaMGaAeuVYCUNaZ5HGddtqKUZyFj+3fIoQVQ76kjXFs=", "ed4d57MMCB6K96DA"));
		String data = encryptToBase64("123456", "12WEREWR1586TREW");
		System.out.println(data);
		System.out.println(decryptFromBase64(data, "12WEREWR1586TREW"));
	}
}
