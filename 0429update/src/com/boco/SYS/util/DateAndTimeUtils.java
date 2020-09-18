package com.boco.SYS.util;

public class DateAndTimeUtils {
	public static String formatTime(String formatType,String dataStr){
		if("HH:MM".equals(formatType)){
			String[] strs = dataStr.split(":");
			String prefix = strs[0];
			String suffix = strs[1];
			if(prefix.length()!=2){
				if(Integer.valueOf(prefix)<=9 && Integer.valueOf(prefix)>=0){
					prefix = "0"+prefix;
				}
			}
			dataStr = prefix+suffix+"00";
		}
		return dataStr;
	}
	public static String parseTime(String formatType,String dataStr){
		if("HH:MM".equals(formatType)){
			String prefix = dataStr.substring(0, 2);
			String suffix = dataStr.substring(2, 4);
			dataStr = prefix+":"+suffix;
		}
		return dataStr;
	}
	public static void main(String[] args) {
		String str = formatTime("HH:MM", "23:21");
		System.out.println(str);
		str = parseTime("HH:MM", str);
		System.out.println(str);
	}
}
