package com.boco.SYS.global;
/**
 * 
 * 
 *  数据字典类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年12月1日    	    杨滔    新建
 * </pre>
 */
public class Dic {
	/**
	 * 
	 * 
	 *  功能类型：TRADE-交易，CHECK-复核.
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月8日    	    杨滔    新建
	 * </pre>
	 */
	public enum FunType {
		TRADE,CHECK
	}
	/**
	 * 
	 * 
	 *  AOP日志输出级别.
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月1日    	    杨滔    新建
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
	 *  访问数据库方式,Read方式只输出日志文件不记录日志表，Write方式输出日志文件且记录日志表.
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月1日    	    杨滔    新建
	 * </pre>
	 */
	public enum AccessType{
		READ,WRITE
	}
	
	/**
	 * 
	 * 
	 *  系统字典表：1-是；0-否.
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月1日    	    杨滔    新建
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
	 *  系统字典表：0-是；1-否.
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月1日    	    杨滔    新建
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
	 *  类型 是-01，否-00.
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月17日    	    杨滔    新建
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
