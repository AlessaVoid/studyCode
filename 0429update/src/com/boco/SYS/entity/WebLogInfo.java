package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ҵ����־��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class WebLogInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���� */
	private java.lang.String id;
	/** �̺߳� */
	private java.lang.String threadCode;
	/** �������� */
	private java.lang.String tradeDate;
	/** ����ʱ�� */
	private java.lang.String tradeTime;
	/** �������� */
	private java.lang.String organCode;
	/** �������� */
	private java.lang.String organName;
	/** ����Ա���� */
	private java.lang.String operCode;
	/** ����Ա���� */
	private java.lang.String operName;
	/** �������� */
	private java.lang.String moduleName;
	/** ���ܱ��� */
	private java.lang.String moduleCode;
	/** ���׺�ʱ(ms) */
	private java.lang.String tradeConsumingTime;
	/** ִ�з��� */
	private java.lang.String runningMethod;
	/** ����IP */
	private java.lang.String requestIp;
	/** ִ�н�� */
	private java.lang.String runningResult;
	/** ������ */
	private java.lang.String serviceCode;
	//��������������log4J�����
	private java.lang.String argu;
	private Throwable ex;
	
	/** setter\getter���� */
	/** ���� */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** �̺߳� */
	public void setThreadCode(java.lang.String threadCode) {
		this.threadCode = threadCode == null ? null : threadCode.trim();
	}
	public java.lang.String getThreadCode() {
		return this.threadCode;
	}
	/** �������� */
	public void setTradeDate(java.lang.String tradeDate) {
		this.tradeDate = tradeDate == null ? null : tradeDate.trim();
	}
	public java.lang.String getTradeDate() {
		return this.tradeDate;
	}
	/** ����ʱ�� */
	public void setTradeTime(java.lang.String tradeTime) {
		this.tradeTime = tradeTime == null ? null : tradeTime.trim();
	}
	public java.lang.String getTradeTime() {
		return this.tradeTime;
	}
	/** �������� */
	public void setOrganCode(java.lang.String organCode) {
		this.organCode = organCode == null ? null : organCode.trim();
	}
	public java.lang.String getOrganCode() {
		return this.organCode;
	}
	/** �������� */
	public void setOrganName(java.lang.String organName) {
		this.organName = organName == null ? null : organName.trim();
	}
	public java.lang.String getOrganName() {
		return this.organName;
	}
	/** ����Ա���� */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
	/** ����Ա���� */
	public void setOperName(java.lang.String operName) {
		this.operName = operName == null ? null : operName.trim();
	}
	public java.lang.String getOperName() {
		return this.operName;
	}
	/** �������� */
	public void setModuleName(java.lang.String moduleName) {
		this.moduleName = moduleName == null ? null : moduleName.trim();
	}
	public java.lang.String getModuleName() {
		return this.moduleName;
	}
	/** ���ܱ��� */
	public void setModuleCode(java.lang.String moduleCode) {
		this.moduleCode = moduleCode == null ? null : moduleCode.trim();
	}
	public java.lang.String getModuleCode() {
		return this.moduleCode;
	}
	/** ���׺�ʱ(ms) */
	public void setTradeConsumingTime(java.lang.String tradeConsumingTime) {
		this.tradeConsumingTime = tradeConsumingTime == null ? null : tradeConsumingTime.trim();
	}
	public java.lang.String getTradeConsumingTime() {
		return this.tradeConsumingTime;
	}
	/** ִ�з��� */
	public void setRunningMethod(java.lang.String runningMethod) {
		this.runningMethod = runningMethod == null ? null : runningMethod.trim();
	}
	public java.lang.String getRunningMethod() {
		return this.runningMethod;
	}
	/** ����IP */
	public void setRequestIp(java.lang.String requestIp) {
		this.requestIp = requestIp == null ? null : requestIp.trim();
	}
	public java.lang.String getRequestIp() {
		return this.requestIp;
	}
	/** ִ�н�� */
	public void setRunningResult(java.lang.String runningResult) {
		this.runningResult = runningResult == null ? null : runningResult.trim();
	}
	public java.lang.String getRunningResult() {
		return this.runningResult;
	}
	/** ������ */
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
	/** ����Ա�������� */
	public Throwable getEx() {
		return ex;
	}
	public void setEx(Throwable ex) {
		this.ex = ex;
	}
	
}