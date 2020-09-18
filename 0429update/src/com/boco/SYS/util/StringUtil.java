package com.boco.SYS.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static String getUUID(){
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		if(uuid.length()>32) return uuid.substring(0,32);
		else return uuid;
	}
	/**
	 * 去掉字符串中的空格，含全角半角空�?
	 * @return
	 */
	public static String lmrtrim(String s){
		if(s == null)
			return null;
		s = s.replaceAll("�? ", "");
		return s;
	}
	/**
	 * 格式化路径信�? 将路径统�?��Unix风格, 同时去掉�?���?
	 * @param path
	 * @return
	 */
	public static String formatPath(String path){
		if (path == null || "".equals(path))
			return "";

		if (path.lastIndexOf('/') == path.length() - 1)
			path = path.substring(0, path.length() - 1);
		path = path.replace('\\', '/');
		path = path.replaceAll("//", "/");
		return path;
	}
	
	public static String getStandardDate(String str){
		String restr="";
		if(str!=null && !"".equals(str)){
			restr=str;
			if(str.length()<14){
				int k=14-str.length();
				while(k>0){
					restr=restr+"0";
					k--;
				}
			}
		}
		return restr;
	}
	/**
	 * 根据标准长度截取字符串，并在后面加上 ...
	 * @param str 要截取的字符�?
	 * @param length	要截取的长度�?
	 * @return
	 */
	public static String getSubstring(String str,String isDate,int length){
		if(str == null){
			return null;
		}
		if(str.length() > length){
			if("y".equals(isDate)){//2010-05-22wyl:日期截取
				return str.substring(0,length);
			}
			return str.substring(0, length)+"...";
		}else{
			return str;
		}
	}
	/**
	 * 将java.sql.Timestamp 类型转换�?java.util.Date 类型�?
	 * @param _date
	 * @return
	 */
	public static  java.util.Date getUtilDateType(java.sql.Timestamp  _date){
		return (java.util.Date)_date;
	}
	
	
	/**
	 * 处理字符串方法，对于是null的String对象，或是�?null’字符串的，均返回空字符串�?
	 */
	public static String doEmpty(String src){
		if(src==null||"null".equalsIgnoreCase(src)) return "";
		else return src;
	}
	

	/**
	 * 处理字符串方法，对于是null的String对象，或是�?null’字符串的，均返回空字符串�?
	 */
	public static String doEmpty(Object src){
		if(src==null||"null".equalsIgnoreCase(String.valueOf(src))) return "";
		else return String.valueOf(src);
	}
	
	/**
	 * 处理字符串方法，对于是null的String对象，或是�?null’字符串的，均以参数replace中指定的内容返回，否则原字符串返回�?
	 */
	public static String doEmpty(String src,String replace){
		if(src==null||"null".equalsIgnoreCase(src)||src.length()<1 || "".equals(src)) return replace;
		else return src;
	}
	/**
	 * 处理字符串方法，对于是null的Object对象，或是�?null’字符串，或是空字符串的，均以参数replace中指定的内容返回，否则原字符串返回�?
	 */
	public static String doEmpty(Object src,String replace){
		if(src==null||"null".equalsIgnoreCase(String.valueOf(src))||String.valueOf(src).length()<1) return replace;
		else return String.valueOf(src);
	}
	
	/**
	 * 处理字符串方法，对于是null的String对象，或是�?null’字符串的，均返回空字符串，否则原字符串去除头尾空格后返回�?
	 */
	public static String doEmptyAndTrim(String src){
		if(src==null||"null".equalsIgnoreCase(src)) return "";
		else return src.trim();
	}
	
	/**
	 * 处理字符串方法，对于是null的String对象，或是�?null’字符串，或是空字符串的，均以参数replace中指定的内容返回，否则原字符串去除头尾空格后返回�?
	 */
	public static String doEmptyAndTrim(String src,String replace){
		if(src==null||"null".equalsIgnoreCase(src)||src.length()<1) return replace;
		else return src.trim();
	}
	
	/**
	 * 字符串转换成数字
	 * @param intStr
	 * @return
	 */
	public static int toInt(String intStr){
		intStr = doEmpty(intStr,"0");
		int i = 0;
		try{
			i = Integer.parseInt(intStr);
		}catch(Exception e){
			System.err.println("StringUtil.toInt(String intStr) failed. "+intStr+" can't parse to int.");
			i = 0;
		}
		return i;
	}
	
	public static int toInt(String intStr, String replace){
		intStr = doEmpty(intStr,replace);
		int i = 0;
		try{
			i = Integer.parseInt(intStr);
		}catch(Exception e){
			System.err.println("StringUtil.toInt(String intStr) failed. "+intStr+" can't parse to int.");
			i = 0;
		}
		return i;
	}
	
	/**
	 * 字符串转换成数字
	 * @param intStr
	 * @return
	 */
	public static int toInt(Object intStr){
		int i = 0;
		if(intStr==null) return i;
		try{
			i = Integer.parseInt(doEmpty(intStr,"0"));
		}catch(Exception e){
			System.err.println("StringUtil.toInt(Object intStr) failed. "+intStr+" can't parse to int.");
			i = 0;
		}
		return i;
	}
	
	public static int toInt(Object intStr, String replace){
		int i = 0;
		if(intStr==null) return i;
		try{
			i = Integer.parseInt(doEmpty(intStr,replace));
		}catch(Exception e){
			System.err.println("StringUtil.toInt(Object intStr) failed. "+intStr+" can't parse to int.");
			i = 0;
		}
		return i;
	}
	
	//返回Textarea中录入文本对应的HTML格式
	public static String getTextareaHTML(String src){
		src = StringUtil.doEmpty(src);
		src = src.replaceAll("\\&", "&amp;");
		src = src.replaceAll("\\<", "&lt;");
		src = src.replaceAll("\\>", "&gt;");
		src = src.replaceAll("\r\n", "<br/>");
		src = src.replaceAll("\n", "<br/>");
		src = src.replaceAll(" ", "&nbsp;");
		return src;
	}
	/**
	 * 用正则表达式分隔文件名称
	 * @param fileName
	 * @param prefix
	 * @param index
	 * @return
	 */
	public static String getWorkflowCode(String fileName,String prefix,int index) {
//		Pattern pat = Pattern.compile("(_?\\d+_?|[_a-zA-Z]+)");
		String result = "";
		String regex = "^(\\d{11})_(.+)_(\\d{4})_(\\d{8})_([ZAI])_(\\d{4})_(\\d{4}).(.+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fileName);
		if (matcher.matches()) {
			result = matcher.group(index);
			if(StringUtil.doEmpty(prefix).length()>0){
				result = prefix + "_" + result;
			}
		} else {
			result = fileName;
			System.out.println("no matches!!!");
		}
		return result;
	}
	/**
	 * 
	 *
	 * TODO 求两个字符数组的并集
	 *
	 * @param arr1
	 * @param arr2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月22日    	   谷立羊  新建
	 * </pre>
	 */
	public static String[] union(String[] arr1,String[] arr2){
		Set<String> set=new HashSet<String>();
		for (String str : arr1) {
			set.add(str);
		}
		for (String str : arr2) {
			set.add(str);
		}
		String[] result={};
		return set.toArray(result);
	}
	/**
	 * 
	 *
	 * TODO求两个字符数组的交集
	 *
	 * @param arr1
	 * @param arr2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月22日    	   谷立羊  新建
	 * </pre>
	 */
	public static String[] intersect(String[] arr1,String[] arr2){
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		LinkedList<String> list=new LinkedList<String>();
		for (String str : arr1) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for (String str : arr2) {
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}
		for (Entry<String, Boolean> e : map.entrySet()) {
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}
		String[] result={};
		return list.toArray(result);
	}
	/**
	 * 
	 *
	 * TODO 求两个字符数组的差集
	 *
	 * @param arr1
	 * @param arr2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月22日    	   谷立羊  新建
	 * </pre>
	 */
	public static String[] minus(String[] arr1,String[] arr2){
		LinkedList<String> list=new LinkedList<String>();
		LinkedList<String> his=new LinkedList<String>();
		String[] longerArr=arr1;
		String[] shortArr=arr2;
		if (arr1.length>arr2.length) {
			longerArr=arr2;
			shortArr=arr1;
		}
		for (String str : longerArr) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : shortArr) {
			if (list.contains(str)) {
				his.add(str);
				list.remove(str);
			}else{
				if (!his.contains(str)) {
					list.add(str);
				}
			}
		}
		String[] result={};
		return list.toArray(result);
	}
	/**
	 * 
	 *
	 * TODO 判断字符串是否为空
	 *
	 * @param str
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月11日    	   谷立羊  新建
	 * </pre>
	 */
	public static boolean isBlack(String str){
		if (null==str||"null".equalsIgnoreCase(str)||"".equals(str)) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 
	 *
	 * TODO 判断是否为空
	 *
	 * @param obj
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
	 * </pre>
	 */
	public static boolean isNull(Object obj){
		if (null==obj) {
			return true;
		}else if (obj  instanceof String) {
			String str = (String) obj;
			if (isBlack(str)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	/**
	 * 
	 *
	 * TODO 判断是否是数字
	 *
	 * @param oldValue
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月13日    	   谷立羊  新建
	 * </pre>
	 */
	public static boolean isNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit((str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 比较两个字符串是否相等
	 *
	 * @param str1
	 * @param str2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月25日    	   谷立羊  新建
	 * </pre>
	 */
	public static boolean isEquals(String str1,String str2) {
	 if (isBlack(str1)) {
		 if (isBlack(str2)) {
			 return true;
			}else {
				return false;	
			}
		}else if (isBlack(str2)) {
			return false;
		}else if (str1.equals(str2)) {
			return true;
		}else{
			return false;	
		}
	}

	/**
	 * 把拓峰式命名转换为下划线式的命名
	 * @param str
	 * @return
	 */
	public static String toUnderLine(String str) {
		str = StringUtils.uncapitalize(str);
		char[] letters = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char letter : letters) {
			if (Character.isUpperCase(letter)) {
				sb.append("_" + letter + "");
			} else {
				sb.append(letter + "");
			}
		}
		return StringUtils.lowerCase(sb.toString());
	}
}

