package com.boco.SYS.global;
/**
 * 
 * 
 *  �����ֵ���.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��1��    	    ����    �½�
 * </pre>
 */
public class Dic {
	/**
	 * 
	 * 
	 *  �������ͣ�TRADE-���ף�CHECK-����.
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��8��    	    ����    �½�
	 * </pre>
	 */
	public enum FunType {
		TRADE,CHECK
	}
	/**
	 * 
	 * 
	 *  AOP��־�������.
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��1��    	    ����    �½�
	 * </pre>
	 */
	public enum Debug {
		DEBUG("debug",4),INFO("info",3),WARN("warn",2),ERROR("error",1);
		private final String name;
		private final int value;
		private Debug(String name, int value){
			this.name = name;
			this.value = value;
		}
		public String getName(){
			return this.name;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	/**
	 * 
	 * 
	 *  �������ݿⷽʽ,Read��ʽֻ�����־�ļ�����¼��־��Write��ʽ�����־�ļ��Ҽ�¼��־��.
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��1��    	    ����    �½�
	 * </pre>
	 */
	public enum AccessType{
		READ,WRITE
	}
	
	/**
	 * 
	 * 
	 *  ϵͳ�ֵ��1-�ǣ�0-��.
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��1��    	    ����    �½�
	 * </pre>
	 */
	public enum Yes1No0{
		YES("1"),NO("0");
		private final String value;
		private Yes1No0(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}
	}
	
	/**
	 * 
	 * 
	 *  ϵͳ�ֵ��0-�ǣ�1-��.
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��1��    	    ����    �½�
	 * </pre>
	 */
	public enum Yes0No1{
		YES("0"),NO("1");
		private final String value;
		private Yes0No1(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}
	}
	
	/**
	 * 
	 * 
	 *  ���� ��-01����-00.
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��17��    	    ����    �½�
	 * </pre>
	 */
	public enum Type{
		YES("1"),NO("0");
		private final String value;
		private Type(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}
	}
}
