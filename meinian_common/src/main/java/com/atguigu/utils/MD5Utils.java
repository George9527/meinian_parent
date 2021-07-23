package com.atguigu.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * 使用md5的算法进行加密
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static void main(String[] args) {
		// 生成 MD5 的密码
//		System.out.println(md5("1234"));

		// 生成 BCrypt 的密码
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("admin");
		System.out.println(encode);

		// 密码校验
		boolean isFlag = bCryptPasswordEncoder.matches("admin",
				"$2a$10$iSvBaoBZ3zCwKJ4TEsNdUeacN8rPw/EMtoZVdHW.pNNZDS4Qk8ChK");
		if (isFlag) {
			System.out.println("密码正确");
		} else {
			System.out.println("密码错误");
		}

	}

}