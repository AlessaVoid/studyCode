package com.boco.SYS.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 
 * 
 *  ��ȷ���㹤����.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��9��    	    ����    �½�
 * </pre>
 */
public class MathUtil {
	// Ĭ�ϳ������㾫��
	private static final int DEF_DIV_SCALE = 10;
	
	/**
	 * 
	 *
	 * TODO ��������С��.
	 *
	 * @param b
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��9��    	    ����    �½�
	 * </pre>
	 */
	public static String formatDouble(Double b) {
		BigDecimal bg = new BigDecimal(b);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * ʹ���ʼ���
	 * 
	 * @return
	 */
	public static String fromUsage(long free, long total) {
		Double d = new BigDecimal(free * 100 / total).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}
	
	/**
	 * �Ƿ�Ϊ����
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric1(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * Stringת��double
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
	 *  ��ʽ��С�����ͣ���Դ����ݿ�����Ϊ��.023������
	 * @param dataStr ��ʽ���ַ���
	 * @param n ����С��λ��
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
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ĳ�
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * 
	 *
	 * TODO ���������������ַ�����ֵ
	 *
	 * @param v1
	 * @param v2
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��8��13��    	   ������  �½�
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
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������ĺ�
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ļ�
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	/**
	 * 
	 *
	 * TODO С����ת�ٷ���
	 *
	 * @param v1
	 * @param v2
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��7��5��    	   ������  �½�
	 * </pre>
	 */
	public static String mul_(String v1) {
		BigDecimal b1 = new BigDecimal(v1);
		return String.valueOf(b1.multiply(new BigDecimal(100)).doubleValue());
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ������������
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
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
	 * TODO  �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 *	 
	 * @param v1 ������
	 * @param v2 ����
	 * @param scale ����λ��
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��26��    	    ������   �½�
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
	  * TODO ��ÿ�������ּ��϶��Ŵ���
	  * <pre>
	  * �޸�����        �޸���    �޸�ԭ��
	  * 2016��9��9��    	 κ��  �½�
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
	 * TODO �ѿ�ѧ���㷨����ת��Ϊ��ͨ����
	 *
	 * @param
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��9��20��    	κ��    �½� 
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
	 * @Description:�Ե���doubleֵ���о��ȴ���
	 * @param:@param num ֵ
	 * @param:@param scale ����
	 * @param:@param roundingMode ����ģʽ
	 * @param:@return
	 * @return:double
	 * @throws
	 * @author:Hypo
	 * @date 2018��7��3��
	 */
	public static double round(double num,int scale,int roundingMode){
		BigDecimal bd = new BigDecimal(num);
		bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}
}
