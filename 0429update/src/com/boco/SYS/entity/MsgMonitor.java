package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 发送短信-服务监控状态实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class MsgMonitor extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 程序所在ip地址 */
		@EntityParaAnno(zhName="程序所在ip地址")
	private String moIp;
	/** 服务节点编号 */
		@EntityParaAnno(zhName="服务节点编号")
	private String moSvrId;
	/** 服务节点类型 ap、ctrl、end、web */
		@EntityParaAnno(zhName="服务节点类型 ap、ctrl、end、web")
	private String moSvrType;
	/** 程序运行状态,0运行中1未运行2不适用 */
		@EntityParaAnno(zhName="程序运行状态,0运行中1未运行2不适用")
	private String moSvrRunStatus;
	/** 短信发送次数 */
		@EntityParaAnno(zhName="短信发送次数")
	private Integer sendCount;
	
	/** setter\getter方法 */
	/** 程序所在ip地址 */
	public void setMoIp(String moIp) {
		this.moIp = moIp == null ? null : moIp.trim();
	}
	public String getMoIp() {
		return this.moIp;
	}
	/** 服务节点编号 */
	public void setMoSvrId(String moSvrId) {
		this.moSvrId = moSvrId == null ? null : moSvrId.trim();
	}
	public String getMoSvrId() {
		return this.moSvrId;
	}
	/** 服务节点类型 ap、ctrl、end、web */
	public void setMoSvrType(String moSvrType) {
		this.moSvrType = moSvrType == null ? null : moSvrType.trim();
	}
	public String getMoSvrType() {
		return this.moSvrType;
	}
	/** 程序运行状态,0运行中1未运行2不适用 */
	public void setMoSvrRunStatus(String moSvrRunStatus) {
		this.moSvrRunStatus = moSvrRunStatus == null ? null : moSvrRunStatus.trim();
	}
	public String getMoSvrRunStatus() {
		return this.moSvrRunStatus;
	}
	/** 短信发送次数 */
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getSendCount() {
		return this.sendCount;
	}
}