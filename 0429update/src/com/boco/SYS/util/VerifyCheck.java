package com.boco.SYS.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyCheck {
	
	
	public static boolean checkCode(String code){
		if(code == null || "".equals(code)){
			return true;
		}
		Pattern password_Pattern = Pattern.compile("^[0-9A-Z]{10}"); 
		Matcher matcher = password_Pattern.matcher(code);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean checkDate(String date){
		if(date == null || "".equals(date)){
			return true;
		}
		Pattern password_Pattern = Pattern.compile("^[0-9]{8}"); 
		Matcher matcher = password_Pattern.matcher(date);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean checkBrandCode(String brandCode){
		if(brandCode == null || "".equals(brandCode)){
			return true;
		}
		Pattern password_Pattern = Pattern.compile("^[A-Z]{2}"); 
		Matcher matcher = password_Pattern.matcher(brandCode);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (checkCode("0912AA1234")) {
			System.out.println("正确");
		}else {
			System.out.println("错误");
		}
		if (!VerifyCheck.checkCode(null)) {
			System.out.println("不符合");
		}
		if (!VerifyCheck.checkBrandCode("AA")) {
			System.out.println("不符合1");
		}
		if (!VerifyCheck.checkDate("20190101")) {
			System.out.println("不符合2");
		}
	}
}
