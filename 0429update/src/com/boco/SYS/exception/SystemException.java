package com.boco.SYS.exception;

/**
 * 
 * 
 *  系统异常，主动抛出异常时抛这个异常类，以便于将异常提示信息返回.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年2月3日    	    杨滔    新建
 * </pre>
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	// 错误码
	private String code = "";
	//占位参数
	private Object[] params;
	
	/**
	 * 
	 *
	 * TODO 通过错误码构造异常.
	 *
	 * @param code
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	    杨滔    新建
	 * </pre>
	 */
	public SystemException(String code) {
		this.code = code;
	}
	
	/**
	 * 
	 *
	 * TODO 通过错误码，占位参数数组构造异常.
	 *
	 * @param code
	 * @param para
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	    杨滔    新建
	 * </pre>
	 */
	public SystemException(String code, Object... para) {
		params = new Object[para.length];
		for (int i = 0; i<para.length; i++) {
			this.params[i] = para[i];
		}
		this.code = code;
	}

	/**
	 * 
	 *
	 * TODO 通过错误码和原始异常构造异常.
	 *
	 * @param code
	 * @param cause
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	    杨滔    新建
	 * </pre>
	 */
	public SystemException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
