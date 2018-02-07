package com.wardensky.mongofileserver.controller;

import java.security.MessageDigest;

/**
 * @author zch
 *
 */
public class Test {
	public static void main(String[] args) {
		String plain = "1234Abcd";
		String s = plain;
		String md5 = "1BCC153DD87779D959615751C44982DC".toLowerCase();
		// 1BCC153DD87779D959615751C44982DC
		// a80349f984f2c2e432f953f6f6eca1f8
		// b2e018b977dbcb7201f1ece0e37b6864
		// b497fc303f0ee6d8bdab612e3fd3541b
		// 5a949b15771efe8c2b994c440b42d220
		System.out.println(md5);
		System.out.println();

		System.out.println("--------");
		int size = 10;
		String abc = "";
		for (int i = 0; i < size; i++) {
			abc = string2MD5(s);
			System.out.println(abc);
			if (abc.equals(md5)) {

			}
			s = abc;
		}

		for (int i = 0; i < size; i++) {
			abc = string2MD5(s);
			if (abc.equals(md5)) {
				System.out.println(abc);
			}
			s = abc;
		}
		System.out.println("--------");
		abc = "";
		s = plain;
		for (int i = 0; i < size; i++) {
			abc = string2MD5(abc + s);
			System.out.println(abc);

		}

		System.out.println("--------");
		abc = "";
		s = plain;
		for (int i = 0; i < size; i++) {
			abc = string2MD5(s + abc);
			System.out.println(abc);
		}
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public static String encode(String str) {
		String algorithm = "MD5";
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
}
