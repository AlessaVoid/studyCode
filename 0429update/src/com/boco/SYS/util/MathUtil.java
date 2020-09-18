package com.boco.SYS.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 
 * 
 *  精确计算工具类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月9日    	    杨滔    新建
 * </pre>
 */
public class MathUtil {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	/**
	 * 
	 *
	 * TODO 保留两个小数.
	 *
	 * @param b
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月9日    	    杨滔    新建
	 * </pre>
	 */
	public static String formatDouble(Double b) {
		BigDecimal bg = new BigDecimal(b);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 使用率计算
	 * 
	 * @return
	 */
	public static String fromUsage(long free, long total) {
		Double d = new BigDecimal(free * 100 / total).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}
	
	/**
	 * 是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric1(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * String转换double
	 * 
	 * @param string
	 * @return double
	 */
	public static double convertSourData(String dataStr) throws Exception {
		if (dataStr != null && "".equals(dataStr)) {
			return Double.valueOf(dataStr);
		}
		throw new NumberFormatException("convert error!");
	}
	/**
	 *  格式化小数类型，针对从数据库查出来为“.023”问题
	 * @param dataStr 格式化字符串
	 * @param n 保留小数位数
	 * @return
	 * @throws Exception
	 */
	public static String formatDecimal(String dataStr,Integer n) throws Exception{
		String pattern="#0";
		if (n>0) {
			pattern+=".";
		}
		for (int i = 0; i < n; i++) {
			pattern+="0";
		}
		DecimalFormat df=new DecimalFormat(pattern);
		dataStr=dataStr.trim();
		if (dataStr != null && "".equals(dataStr)) {
			 BigDecimal bd = new BigDecimal(dataStr);
			return  df.format(bd);
		}
		throw new NumberFormatException("convert error!");
	}
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * 
	 *
	 * TODO 计算两个数字型字符串差值
	 *
	 * @param v1
	 * @param v2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年8月13日    	   谷立羊  新建
	 * </pre>
	 */
	public static double subStr(String v1, String v2) {
		if (StringUtil.isBlack(v1)) {
			v1="0";
		}
		if (StringUtil.isBlack(v2)) {
			v2="0";
		}
 		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	/**
	 * 
	 *
	 * TODO 小数制转百分制
	 *
	 * @param v1
	 * @param v2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年7月5日    	   谷立羊  新建
	 * </pre>
	 */
	public static String mul_(String v1) {
		BigDecimal b1 = new BigDecimal(v1);
		return String.valueOf(b1.multiply(new BigDecimal(100)).doubleValue());
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 *
	 * TODO  提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 *	 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 保留位数
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月26日    	    谷立羊   新建
	 * </pre>
	 */
	public static String  div_(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	 /**
	  * 
	  *
	  * TODO 将每三个数字加上逗号处理
	  * <pre>
	  * 修改日期        修改人    修改原因
	  * 2016年9月9日    	 魏玉航  新建
	  * </pre>
	  */
	 public static String addComma(String text)throws Exception{
		 DecimalFormat df = null;
		 if(text.indexOf(".")>0){
			 if(text.length()-text.indexOf(".")-1 == 0){
				 df = new DecimalFormat("###,##0.");
			 }else if(text.length()-text.indexOf(".")-1 == 1){
				 df = new DecimalFormat("###,##0.0");
			 }else{
				 df = new DecimalFormat("###,##0.00");
			 }
		 }else{
			 df = new DecimalFormat("###,##0");
		 }
		 double number = 0.0;
		 try{
		 number = Double.parseDouble(text);
		 }catch(Exception e){
			 number = 0.0;
		 }
		 return df.format(number) ;
	 }
 	/**
	 * 
	 *
	 * TODO 把科学计算法数字转换为普通数字
	 *
	 * @param
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月20日    	魏玉航    新建 
	 * </pre>
	 */
	public static String sciNotationToNumber(String str ) throws Exception{
			BigDecimal a1 = new BigDecimal(str);
			String sum = a1.toPlainString();
			return sum;
		}
	/**
	 * 
	 * @Title:round
	 * @Description:对单个double值进行精度处理
	 * @param:@param num 值
	 * @param:@param scale 精度
	 * @param:@param roundingMode 舍入模式
	 * @param:@return
	 * @return:double
	 * @throws
	 * @author:Hypo
	 * @date 2018年7月3日
	 */
	public static double round(double num,int scale,int roundingMode){
		BigDecimal bd = new BigDecimal(num);
		bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}
}
