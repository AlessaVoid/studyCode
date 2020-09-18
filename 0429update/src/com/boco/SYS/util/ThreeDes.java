package com.boco.SYS.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 ????? DESede(3DES) ????
 */
public final class ThreeDes {
	public static final String LPAD_STR = "0";
	public static final int STR_LENGTH = 6;
	public static final int RANDOM_BASE_INT = 1000000;
	public static final String KEY = "111111111111111111111111111111111111111111111111";

	private ThreeDes() {

	}

	/**
	 * ???? ??????,???? DES,DESede,Blowfish
	 */
	private static final String ALGORITHM = "DESede";

	// keybyte???????????????24???
	// src??????????????????????
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// ???????
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);

			// ????
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			// e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			// e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			// e3.printStackTrace();
		}
		return null;
	}

	// keybyte???????????????24???
	// src????????????
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// ???????
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);

			// ????
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// ?????????????????
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer("");
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {

				hs.append("0");
				hs.append(stmp);
			} else {
				hs.append(stmp);
			}

		}
		return hs.toString().toUpperCase();
	}

	public static String genRandomSixPwd() {
		String value = Integer.valueOf((int) (Math.random() * RANDOM_BASE_INT)).toString();
		return lpadStr(value, null, STR_LENGTH);
	}

	public static String lpadStr(String str, String lpad, int length) {
		String templpad = null;
		if (null == str || str.trim().length() == 0) {
			return null;
		}
		if (null == lpad || lpad.trim().length() == 0) {
			templpad = LPAD_STR;
		} else {
			templpad = lpad;
		}
		StringBuffer sb = new StringBuffer();
		int strLength = str.length();
		if (strLength >= length) {
			return str;
		} else {
			for (int i = 0; i < length - strLength; i++) {
				sb.append(templpad);
			}
			return sb.append(str).toString();
		}
	}

	/*
	 * ??16????????????????????? @param hex @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toUpperCase().toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * ???????????
	 * 
	 * @param orgStr
	 * @param key
	 *            ???
	 * @return
	 * @throws Exception
	 */
	public static String decryptString(String orgStr, String key) {
		byte[] source = ThreeDes.hexStringToByte(orgStr);
		byte[] keyBt = ThreeDes.hexStringToByte(key);
		byte[] result = ThreeDes.decryptMode(keyBt, source);
		return new String(result);
	}

	/**
	 * ???????????
	 * 
	 * @param orgStr
	 * @param key
	 *            ???
	 * @return
	 * @throws Exception
	 */
	public static String encryptString(String ori, String keys) {
		byte[] source = ori.getBytes();
		byte[] keyBt = ThreeDes.hexStringToByte(keys);
		byte[] pwde = ThreeDes.encryptMode(keyBt, source);
		String d = ThreeDes.byte2hex(pwde);
		return d;
	}
	public static void main(String[] args) {
		//����
		System.out.println(ThreeDes.encryptString("jdbc:postgresql://20.200.29.76:5432/postgres", ThreeDes.KEY));
//		System.out.println(ThreeDes.encryptString("jdbc:postgresql://10.136.1.84:5432/trade_db", ThreeDes.KEY));
//
//		//????
//		System.out.println(ThreeDes.decryptString("C3611730B6AAFB3DA7C9F2C8C542BDD12ED413975A03D93889D63E5F2A59C2F8CE0D9FB167988CD1B95374BA8DDFF8C2", ThreeDes.KEY));
//
//		System.out.println(ThreeDes.decryptString("C3611730B6AAFB3DA7C9F2C8C542BDD12ED413975A03D9387B3E8A06E604CBCFBB33A00AA150718C597850E5B2FC1A70",ThreeDes.KEY));
//		System.out.println(ThreeDes.encryptString("jdbc:postgresql://10.136.1.84:5432/mdb_1",ThreeDes.KEY));

	}
}
