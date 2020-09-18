package com.boco.SYS.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.FdOper;
import com.boco.SYS.exception.SystemException;
/**
 * 
 * 
 *  Map������.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��2��    	    ����    �½�
 * </pre>
 */
public class MapUtil {
	/**
	 * 
	 *
	 * TODO JavaBeanת��ΪMap.
	 *
	 * @param obj
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public static HashMap<String,Object> beanToMap(Object obj){
		HashMap<String,Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = propertyDescriptor.getName();
				// ����class����  
				if(!"class".equals(key)){
					Method getter = propertyDescriptor.getReadMethod();
					try {
						Object value = getter.invoke(obj);
						map.put(key, value);
					} catch (IllegalAccessException e) {
						throw new SystemException("w117", e);
					} catch (IllegalArgumentException e) {
						throw new SystemException("w117", e);
					} catch (InvocationTargetException e) {
						throw new SystemException("w117", e);
					}
				}
			}
		} catch (IntrospectionException e) {
			throw new SystemException("w117", e);
		}
		return map;
	}
	
	/**
	 * 
	 *
	 * TODO Mapת��ΪJavaBean.
	 *
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��24��    	    ����    �½�
	 * </pre>
	 */
	public static <T> T mapToBean(Map<String,Object> map, Class<T> clazz){
		T bean ;
		try {
			bean = clazz.newInstance();
			BeanInfo beanInfo;
			try {
				beanInfo = Introspector.getBeanInfo(clazz);
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
		        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
		        	String key = propertyDescriptor.getName();
		        	if(map.containsKey(key)){
		        		Object value = map.get(key);  
		                Method setter = propertyDescriptor.getWriteMethod();  
		                try {
							setter.invoke(bean, value);
						} catch (IllegalArgumentException e) {
							throw new SystemException("w118", e);
						} catch (InvocationTargetException e) {
							throw new SystemException("w118", e);
						}  
		        	}
				}
			} catch (IntrospectionException e) {
				throw new SystemException("w118", e);
			}
			return bean;
		} catch (InstantiationException e) {
			throw new SystemException("w118", e);
		} catch (IllegalAccessException e) {
			throw new SystemException("w118", e);
		}
	}
	
	/**
	 * 
	 *
	 * TODO List<Map<String,Object>>����ת����List<Entity>����.
	 *
	 * @param list
	 * @param clazz
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	    ����    �½�
	 * </pre>
	 */
	public static <T> List<T> listMapToListBean(List<Map<String, Object>> list, Class<T> clazz){
		List<T> listBean = new ArrayList<T>();
		try {
			T bean = clazz.newInstance();
			for (Map<String, Object> map : list) {
				try {
					bean = mapToBean(map, clazz);
					listBean.add(bean);
				} catch (Exception e) {
					throw new SystemException("w119", e);
				}
			}
			return listBean;
		} catch (InstantiationException e) {
			throw new SystemException("w119", e);
		} catch (IllegalAccessException e) {
			throw new SystemException("w119", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opercode", "test");
		FdOper fdoper = mapToBean(map,FdOper.class);
		System.out.println(fdoper.getOpercode());
	}
}
