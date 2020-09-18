package com.boco.SYS.exception;

/**
 * 
 * 
 *  ϵͳ�쳣�������׳��쳣ʱ������쳣�࣬�Ա��ڽ��쳣��ʾ��Ϣ����.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��2��3��    	    ����    �½�
 * </pre>
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	// ������
	private String code = "";
	//ռλ����
	private Object[] params;
	
	/**
	 * 
	 *
	 * TODO ͨ�������빹���쳣.
	 *
	 * @param code
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	    ����    �½�
	 * </pre>
	 */
	public SystemException(String code) {
		this.code = code;
	}
	
	/**
	 * 
	 *
	 * TODO ͨ�������룬ռλ�������鹹���쳣.
	 *
	 * @param code
	 * @param para
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	    ����    �½�
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
	 * TODO ͨ���������ԭʼ�쳣�����쳣.
	 *
	 * @param code
	 * @param cause
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	    ����    �½�
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
