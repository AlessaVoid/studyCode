package com.boco.SYS.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.boco.util.JsonUtils;

/**
 * action service ��������
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2014-12-11    	    Ԭ����    �½�
 * service ��ִ��ʧ��ʱ, ��Ҫthrow �� Json </br>
 * ���� : throw Json.getInstance().setMsg("ҵ�����ڻ�ȡʧ��");
 * �� action ��try..catch ����.
 * </pre>
 */
public class Json extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private String msg;
	private String success;
	private List<?> rows;
	private int pageNo;
	private int totalRows;

	// Ч��
	private String field;
	private String classa;

	// ����ʵ��
	private Object bean;

	public void init() {
		this.msg = "";
		this.success = "false";
		this.rows = null;
		this.field = "";
		this.classa = "";
		this.bean = null;
	}

	public static Json getInstance() {
		return new Json().setSuccess("false");
	}
	

	@Override
	public String toString() {
		return this.toJson();
	}

	/**
	 * ��̬��������Json Map
	 * 
	 * @return
	 * 
	 *         <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2014-12-12    	    Ԭ����    �½�
	 * </pre>
	 */
	private Map<String, Object> createJsonBean() {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Field[] fields = this.getClass().getDeclaredFields();
		Method[] methods = this.getClass().getDeclaredMethods();
		Method method;
		String methodStr;
		String fieldStr;
		for (int i = 0; i < fields.length; i++) {
			fieldStr = fields[i].getName();
			methodStr = "get" + fieldStr.substring(0, 1).toUpperCase() + fieldStr.substring(1);
			for (int j = 0; j < methods.length; j++) {
				method = methods[j];
				if (StringUtils.equals(methodStr, method.getName())) {
					try {
						Object obj = method.invoke(this, null);
						if (null != obj) {
							resultMap.put(fieldStr, obj);
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		return resultMap;
	}

	public String toJson() {
		// return JsonUtils.toJson(this).replaceAll("pageNo",
		// "pager.pageNo").replaceAll("totalRows", "pager.totalRows");
		Map<String, Object> map = createJsonBean();
		String s = JsonUtils.toJson(map).replaceAll("pageNo", "pager.pageNo").replaceAll("totalRows", "pager.totalRows");
		return s;
	}

	public Json returnMsg(String success, String msg) {
		this.success = success;
		this.msg = msg;
		return this;
	}

	public Json returnList(int pageNo, int count, List<?> dateList) {
		this.pageNo = pageNo;
		this.totalRows = count;
		this.rows = dateList;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Json setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public String getSuccess() {
		return success;
	}

	public Json setSuccess(String success) {
		this.success = success;
		return this;
	}

	public List<?> getRows() {
		return rows;
	}

	public Json setRows(List<?> rows) {
		this.rows = rows;
		return this;
	}

	public int getPageNo() {
		return pageNo;
	}

	public Json setPageNo(int pageNo) {
		this.pageNo = pageNo;
		return this;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public Json setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		return this;
	}

	public String getField() {
		return field;
	}

	public Json setField(String field) {
		this.field = field;
		return this;
	}

	public String getClassa() {
		return classa;
	}

	public Json setClassa(String classa) {
		this.classa = classa;
		return this;
	}

	public Object getBean() {
		return bean;
	}

	public Json setBean(Object bean) {
		this.bean = bean;
		return this;
	}

}
