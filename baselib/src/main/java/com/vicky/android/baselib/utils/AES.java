package com.vicky.android.baselib.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	public static final String CHAR_ENCODING = "UTF-8";

	public static final String AES_ECB_PKCS5_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final String AES_CBC_PKCS5_ALGORITHM = "AES/CBC/PKCS5Padding";

	// algorithm "算法/模式/补码方式"
	////使用CBC模式，需要一个向量iv，可增加加密算法的强度
	public static byte[] encrypt(byte[] data, byte[] key,String algorithm,String ivStr) {
		if (key.length != 16) {
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(algorithm);
			if (ivStr == null)
				cipher.init(Cipher.ENCRYPT_MODE, seckey);
			else {
				IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes(CHAR_ENCODING));
				cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
			}
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static byte[] decrypt(byte[] data, byte[] key,String algorithm,String ivStr) {
		if (key.length != 16) {
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(algorithm);
			if (ivStr == null)
				cipher.init(Cipher.DECRYPT_MODE, seckey);
			else {
				IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes(CHAR_ENCODING));
				cipher.init(Cipher.DECRYPT_MODE, seckey,iv);
			}
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static String encryptToBase64(String data, String key,String algorithm,String ivStr) {
		try {
			byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING),algorithm,ivStr);
			return new String(Base64.encode(valueByte, Base64.DEFAULT));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}

	}

	public static String decryptFromBase64(String data, String key,String algorithm,String ivStr) {
		try {
			byte[] originalData = Base64.decode(data, Base64.DEFAULT);
			byte[] valueByte = decrypt(originalData, key.getBytes(CHAR_ENCODING),algorithm,ivStr);
			return new String(valueByte, CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}

	public static String encryptWithKeyBase64(String data, String key,String algorithm,String ivStr) {
		try {
			byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), Base64.decode(key, Base64.DEFAULT),algorithm,ivStr);
			return new String(Base64.encode(valueByte, Base64.DEFAULT));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	public static String decryptWithKeyBase64(String data, String key,String algorithm,String ivStr) {
		try {
			byte[] originalData = Base64.decode(data, Base64.DEFAULT);
			byte[] valueByte = decrypt(originalData, Base64.decode(key, Base64.DEFAULT),algorithm,ivStr);
			return new String(valueByte, CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decrypt fail!", e);
		}
	}
}
