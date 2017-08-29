package com.vicky.android.baselib.utils;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;

public class RSA {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

	/**
	 * 解密
	 * 
	 * @param content
	 *            密文
	 * @param prikey
	 *            私钥
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, PrivateKey prikey) throws Exception {
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		byte[] b1 = Base64.decode(content, Base64.DEFAULT);
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}

	/**
	 * 加密
	 *
	 * @param key
	 *            随机密码
	 * @param publicKey
	 *            公钥
	 * @return 加密后的字符串
	 */
	public static String encrypt(String key, PublicKey publicKey) throws Exception {
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b = key.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return Base64.encodeToString(b1, Base64.DEFAULT);
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] encodedKey = Base64.decode(key, Base64.DEFAULT);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到公钥
	 * 
	 * @Title: getPublicKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param pubkey
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String pubkey) throws Exception {
		byte[] encodedKey = Base64.decode(pubkey, Base64.DEFAULT);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		return publicKey;
	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 *
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}
}
