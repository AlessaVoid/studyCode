package com.boco.RE.excel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
	
	public static final String COMMON_DATE_FORMAT_STRING = "yyyy-MM-dd";
	public static final String LONG_DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	
	public static String getCommonDateString(long time){
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_FORMAT_STRING);
		sdf.setLenient(false);
		String dateStr = null;
		Date date = new Date(time);
		dateStr = sdf.format(date);
		
		return dateStr;
	}
	
	public static String getCommonDateString(Long time){
		if(time == null){
			return "";
		}

		return getCommonDateString(time.longValue());
	}

	public static String getCommonDateStringExcept1970(long time){
		if(time == 0){
			return "";
		}
		
		return getCommonDateString(time);
	}
	
	public static String getCommonDateStringExcept1970(Long time){
		if(time == null || time.doubleValue() == 0){
			return "";
		}

		return getCommonDateString(time.longValue());
	}
	
	public static String getFullDateTimeString(long time){
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_TIME_FORMAT_STRING);
		sdf.setLenient(false);
		String dateStr = null;
		Date date = new Date(time);
		dateStr = sdf.format(date);
		
		return dateStr;
	}
	
	public static String getFullDateTimeString(Long time){
		if(time == null){
			return "";
		}

		return getFullDateTimeString(time.longValue());
	}
	
	public static String getFullDateTimeStringExcept1970(long time){
		if(time == 0){
			return "";
		}
		
		return getFullDateTimeString(time);
	}
	
	public static String getFullDateTimeStringExcept1970(Long time){
		if(time == null || time.doubleValue() == 0){
			return "";
		}

		return getFullDateTimeString(time.longValue());
	}
	
	public static long getTimeFromDateString(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_FORMAT_STRING);
		sdf.setLenient(false);
		Date date = null;
		date = sdf.parse(dateStr);
		return date.getTime();
	}
	
	public static Date getJavaUtilDateFromDateString(String dateStr){
		if(dateStr == null || "".equals(dateStr.trim())){
			return null;
		}
		
		Date date = null;
		try {
			long time = getTimeFromDateString(dateStr);
			date = new Date(time);
		} catch (ParseException e) {
		}
		
		return date;
	}
	
	public static Long getLongTimeFromDateString(String dateStr){
		Long longTime = null;
		try {
			//longTime = Long.valueOf(getTimeFromDateString(dateStr));
			longTime = new Long(getTimeFromDateString(dateStr));
		} catch (ParseException e) {
			longTime = null;
		}
		
		return longTime;
	}
	
	public static String getStringExceptNull(Object object){
		if(object == null){
			return "";
		}
		else{
			String result = object.toString();
			if(result == null){
				return "";
				
				//
				/*
				if(object instanceof Number){
					return "0";
				}
				else{
					return "";
				}
				*/
			}
			else{
				return result;
			}
		}
	}
	
	public static String getTrimedStringExceptNull(Object object){
		if(object == null){
			return "";
		}
		else{
			String result = object.toString();
			if(result == null){
				return "";
			}
			else{
				return result.trim();
			}
		}
	}
	
	public static Double getDoubleObjectFromString(String str){
		if(str == null || "".equals(str.trim())){
			return null;
		}
		
		Double doubleObject = null;
		try {
			doubleObject = Double.valueOf(str);
		} catch (Exception e) {
		}
		
		return doubleObject;
	}
	
	public static Integer getIntegerObjectFromString(String str){
		if(str == null || "".equals(str.trim())){
			return null;
		}
		
		if(str.endsWith(".0")){
			str = str.substring(0, str.indexOf(".0"));
		}
		
		Integer integerObject = null;
		try {
			integerObject = Integer.valueOf(str);
		} catch (Exception e) {
		}
		
		return integerObject;
	}
	
	public static Long getLongObjectFromString(String str){
		if(str == null || "".equals(str.trim())){
			return null;
		}
		
		if(str.endsWith(".0")){
			str = str.substring(0, str.indexOf(".0"));
		}
		
		Long longObject = null;
		try {
			longObject = Long.valueOf(str);
		} catch (Exception e) {
		}
		
		return longObject;
	}
	
	public static Short getShortObjectFromString(String str){
		if(str == null || "".equals(str.trim())){
			return null;
		}
		
		if(str.endsWith(".0")){
			str = str.substring(0, str.indexOf(".0"));
		}
		
		Short shortObject = null;
		try {
			shortObject = Short.valueOf(str);
		} catch (Exception e) {
		}
		
		return shortObject;
	}
	
	public static String getPropertiesKey(String propString){
		if(propString == null || "".equals(propString.trim()) || propString.indexOf("=") == -1){
			return null;
		}
		
		String key = propString.substring(0, propString.indexOf("="));
		if(key == null || "".equals(key.trim())){
			return null;
		}
		else{
			return key.trim();
		}
	}
	
	public static String getPropertiesValue(String propString){
		if(propString == null || "".equals(propString.trim()) || propString.indexOf("=") == -1){
			return null;
		}
		
		String value = propString.substring(propString.indexOf("=") + 1);
		if(value == null || "".equals(value.trim())){
			return null;
		}
		else{
			return value.trim();
		}
	}
	
	/**
	 */
	public static String deleteEnterSymbolInString(String str){
		if(str != null && ! "".equals(str)){
			str = str.replace('\n', ' ');
		}
		
		return str;
	}
	
}
