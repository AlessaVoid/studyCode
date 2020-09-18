package com.boco.SYS.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.log4j.Logger;

public class DataProcess {

	Logger logger = Logger.getLogger(DataProcess.class);
	
	public String getDate(String pattern) {
		return this.getFormatDate(pattern);
	}
	
	public String getSEQNO(){
		return this.getFormatDate("yyyyMMddHHmmss") + getLastSixNum();
	}
	
	public String getSeqno(){
		String str = BocoUtils.getForamt("HHmmssS").format(new Date());
		str = str.substring(0, str.length() - 1);
		return str;
	}
	
	public String getPROCESS_ID(){
		return System.getProperty("pid");
	}
	
	public String getLastSixNum() {
		int count = 0;
		char str[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < 6) {
			int i = Math.abs(r.nextInt(10));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	
	public static String getFormatDate(String pattern) {
		try {
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String result = formater.format(new java.util.Date());
		return result;
		}catch(Exception e){
			return "";
		}
	}
	
	public static String getFormatDate(Date date) {
		try {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String result = formater.format(date);
		return result;
		}catch(Exception e){
			return "";
		}
	}
	public static String formatDate(Date date,String pattern) {
		try {
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String result = formater.format(date);
		return result;
		}catch(Exception e){
			return "";
		}
	}
	public static void main(String[] args) {
		System.out.println(new DataProcess().getSEQNO());
	}
	 public static String StringFormatStringDate(String date, String pattern1,String pattern2) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);
		try {
			date=sdf2.format(sdf1.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
