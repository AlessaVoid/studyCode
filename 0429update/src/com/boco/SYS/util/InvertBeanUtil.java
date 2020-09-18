package com.boco.SYS.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InvertBeanUtil {

	/**
	 * 解析Json list 为bean list.json必须是list
	 *
	 * @param <T>
	 * @param clazz
	 * @param jsonList
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2014-11-6    	    杨滔    新建
	 * </pre>
	 */
	public static <T> List<T> initBeanList(Class<T> clazz,String jsonList){
		List<T> list = new ArrayList<T>();
		if(StringUtils.isBlank(jsonList))
			return new ArrayList<T>();
		try {
			JSONArray ja = new JSONArray(jsonList);
			Field[] fields = clazz.getDeclaredFields();
			T t = null;
			for(int i=0;i<ja.length();i++){
				t = clazz.newInstance();
				JSONObject jo = (JSONObject) ja.get(i);
				for(int j=0;j<fields.length;j++){
					String field="";
					String methodStr = "";
					Method method;
					if(jo.has(fields[j].getName())){
						field = fields[j].getName();
						methodStr = "set".concat(field.substring(0,1).toUpperCase().concat(field.substring(1)));
						method = clazz.getMethod(methodStr,String.class);
						method.invoke(t, new Object[]{jo.get(field)+""});
					}
				}
				list.add(t);
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		String s = "[{\"irecordcount\":\"序号8\",\"checkno\":\"支票号\",\"tradedate\":\"生成日期\",\"businesskindcode\":\"产品代码\",\"tradecode\":\"交易码6\",\"bankcode\":\"银行代码4\",\"acctno\":\"银行账号\",\"acctname\":\"账户名称\",\"amt\":\"个人金额16\",\"affirmdate\":\"确认日期8\",\"amtflag\":\"结算方式1\",\"procflag\":\"标志1\",\"bankacctno\":\"银行行号\",\"jgamt\":\"机构金额16\",\"allamt\":\"16\",\"sxf\":\"手续费16\",\"resultnote\":\"处理结果\",\"batchserialbj\":\"批次号\",\"__status\":\"nochanged\",\"rowPosition\":0},{\"irecordcount\":\"序号8\",\"checkno\":\"支票号\",\"tradedate\":\"生成日期\",\"businesskindcode\":\"产品代码\",\"tradecode\":\"交易码6\",\"bankcode\":\"银行代码4\",\"acctno\":\"银行账号\",\"acctname\":\"账户名称\",\"amt\":\"个人金额16\",\"affirmdate\":\"确认日期8\",\"amtflag\":\"结算方式1\",\"procflag\":\"标志1\",\"bankacctno\":\"银行行号\",\"jgamt\":\"机构金额16\",\"allamt\":\"16\",\"sxf\":\"手续费16\",\"resultnote\":\"处理结果\",\"batchserialbj\":\"批次号\",\"__status\":\"nochanged\",\"rowPosition\":35}]";
//		JsonUtils.fromJson(s, FdHkNotifyDTO.class);
		//List<FdHkNotifyDTO> l = initBeanList(FdHkNotifyDTO.class, s);
		System.out.println(1);
	}
}
