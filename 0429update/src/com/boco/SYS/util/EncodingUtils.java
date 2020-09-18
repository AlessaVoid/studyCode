package com.boco.SYS.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * 
 * 编码工具类
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     杨滔    新建
 * </pre>
 */
public class EncodingUtils {
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static String to10dao16(int Num) {
		int i = Num / 16;
		int j = Num % 16;
		String sw = "0";
		String gw = "0";
		if (i < 10) {
			sw = i + "";
		} else if (i == 10) {
			sw = "A";
		} else if (i == 11) {
			sw = "B";
		} else if (i == 12) {
			sw = "C";
		} else if (i == 13) {
			sw = "D";
		} else if (i == 14) {
			sw = "E";
		} else if (i == 15) {
			sw = "F";
		}

		if (j < 10) {
			gw = j + "";
		} else if (j == 10) {
			gw = "A";
		} else if (j == 11) {
			gw = "B";
		} else if (j == 12) {
			gw = "C";
		} else if (j == 13) {
			gw = "D";
		} else if (j == 14) {
			gw = "E";
		} else if (j == 15) {
			gw = "F";
		}
		return sw + gw;

	}

	public static String GetBMZiFu(String index) {
		try {
			return new String(hexStringToByte(index), "utf-16be");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
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

	public static String ToBMZiFu(String index) {
		try {
			byte[] bt = index.getBytes("utf-16be");
			String bts = bytesToHexString(bt);
			int len = bts.length();
			String lens = to10dao16(len / 2);
			return bts;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";

		}
	}
}
