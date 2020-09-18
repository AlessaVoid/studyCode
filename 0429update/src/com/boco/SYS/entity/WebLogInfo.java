package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 业务日志表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebLogInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 主键 */
	private java.lang.String id;
	/** 线程号 */
	private java.lang.String threadCode;
	/** 交易日期 */
	private java.lang.String tradeDate;
	/** 交易时间 */
	private java.lang.String tradeTime;
	/** 机构编码 */
	private java.lang.String organCode;
	/** 机构名称 */
	private java.lang.String organName;
	/** 操作员代码 */
	private java.lang.String operCode;
	/** 操作员姓名 */
	private java.lang.String operName;
	/** 功能名称 */
	private java.lang.String moduleName;
	/** 功能编码 */
	private java.lang.String moduleCode;
	/** 交易耗时(ms) */
	private java.lang.String tradeConsumingTime;
	/** 执行方法 */
	private java.lang.String runningMethod;
	/** 请求IP */
	private java.lang.String requestIp;
	/** 执行结果 */
	private java.lang.String runningResult;
	/** 机器号 */
	private java.lang.String serviceCode;
	//方法参数，仅在log4J中输出
	private java.lang.String argu;
	private Throwable ex;
	
	/** setter\getter方法 */
	/** 主键 */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** 线程号 */
	public void setThreadCode(java.lang.String threadCode) {
		this.threadCode = threadCode == null ? null : threadCode.trim();
	}
	public java.lang.String getThreadCode() {
		return this.threadCode;
	}
	/** 交易日期 */
	public void setTradeDate(java.lang.String tradeDate) {
		this.tradeDate = tradeDate == null ? null : tradeDate.trim();
	}
	public java.lang.String getTradeDate() {
		return this.tradeDate;
	}
	/** 交易时间 */
	public void setTradeTime(java.lang.String tradeTime) {
		this.tradeTime = tradeTime == null ? null : tradeTime.trim();
	}
	public java.lang.String getTradeTime() {
		return this.tradeTime;
	}
	/** 机构编码 */
	public void setOrganCode(java.lang.String organCode) {
		this.organCode = organCode == null ? null : organCode.trim();
	}
	public java.lang.String getOrganCode() {
		return this.organCode;
	}
	/** 机构名称 */
	public void setOrganName(java.lang.String organName) {
		this.organName = organName == null ? null : organName.trim();
	}
	public java.lang.String getOrganName() {
		return this.organName;
	}
	/** 操作员代码 */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
	/** 操作员姓名 */
	public void setOperName(java.lang.String operName) {
		this.operName = operName == null ? null : operName.trim();
	}
	public java.lang.String getOperName() {
		return this.operName;
	}
	/** 功能名称 */
	public void setModuleName(java.lang.String moduleName) {
		this.moduleName = moduleName == null ? null : moduleName.trim();
	}
	public java.lang.String getModuleName() {
		return this.moduleName;
	}
	/** 功能编码 */
	public void setModuleCode(java.lang.String moduleCode) {
		this.moduleCode = moduleCode == null ? null : moduleCode.trim();
	}
	public java.lang.String getModuleCode() {
		return this.moduleCode;
	}
	/** 交易耗时(ms) */
	public void setTradeConsumingTime(java.lang.String tradeConsumingTime) {
		this.tradeConsumingTime = tradeConsumingTime == null ? null : tradeConsumingTime.trim();
	}
	public java.lang.String getTradeConsumingTime() {
		return this.tradeConsumingTime;
	}
	/** 执行方法 */
	public void setRunningMethod(java.lang.String runningMethod) {
		this.runningMethod = runningMethod == null ? null : runningMethod.trim();
	}
	public java.lang.String getRunningMethod() {
		return this.runningMethod;
	}
	/** 请求IP */
	public void setRequestIp(java.lang.String requestIp) {
		this.requestIp = requestIp == null ? null : requestIp.trim();
	}
	public java.lang.String getRequestIp() {
		return this.requestIp;
	}
	/** 执行结果 */
	public void setRunningResult(java.lang.String runningResult) {
		this.runningResult = runningResult == null ? null : runningResult.trim();
	}
	public java.lang.String getRunningResult() {
		return this.runningResult;
	}
	/** 机器号 */
	public void setServiceCode(java.lang.String serviceCode) {
		this.serviceCode = serviceCode == null ? null : serviceCode.trim();
	}
	public java.lang.String getServiceCode() {
		return this.serviceCode;
	}
	public java.lang.String getArgu() {
		return argu;
	}
	public void setArgu(java.lang.String argu) {
		this.argu = argu;
	}
	/** 操作员部门名称 */
	public Throwable getEx() {
		return ex;
	}
	public void setEx(Throwable ex) {
		this.ex = ex;
	}
	
}