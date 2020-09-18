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
	 * ����Json list Ϊbean list.json������list
	 *
	 * @param <T>
	 * @param clazz
	 * @param jsonList
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2014-11-6    	    ����    �½�
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
		String s = "[{\"irecordcount\":\"���8\",\"checkno\":\"֧Ʊ��\",\"tradedate\":\"��������\",\"businesskindcode\":\"��Ʒ����\",\"tradecode\":\"������6\",\"bankcode\":\"���д���4\",\"acctno\":\"�����˺�\",\"acctname\":\"�˻�����\",\"amt\":\"���˽��16\",\"affirmdate\":\"ȷ������8\",\"amtflag\":\"���㷽ʽ1\",\"procflag\":\"��־1\",\"bankacctno\":\"�����к�\",\"jgamt\":\"�������16\",\"allamt\":\"16\",\"sxf\":\"������16\",\"resultnote\":\"������\",\"batchserialbj\":\"���κ�\",\"__status\":\"nochanged\",\"rowPosition\":0},{\"irecordcount\":\"���8\",\"checkno\":\"֧Ʊ��\",\"tradedate\":\"��������\",\"businesskindcode\":\"��Ʒ����\",\"tradecode\":\"������6\",\"bankcode\":\"���д���4\",\"acctno\":\"�����˺�\",\"acctname\":\"�˻�����\",\"amt\":\"���˽��16\",\"affirmdate\":\"ȷ������8\",\"amtflag\":\"���㷽ʽ1\",\"procflag\":\"��־1\",\"bankacctno\":\"�����к�\",\"jgamt\":\"�������16\",\"allamt\":\"16\",\"sxf\":\"������16\",\"resultnote\":\"������\",\"batchserialbj\":\"���κ�\",\"__status\":\"nochanged\",\"rowPosition\":35}]";
//		JsonUtils.fromJson(s, FdHkNotifyDTO.class);
		//List<FdHkNotifyDTO> l = initBeanList(FdHkNotifyDTO.class, s);
		System.out.println(1);
	}
}
