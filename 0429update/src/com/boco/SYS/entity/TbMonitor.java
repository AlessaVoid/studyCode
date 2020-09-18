package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 服务监控状态实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbMonitor extends BaseEntity implements java.io.Serializable {
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
	/** 程序所在路径 */
		@EntityParaAnno(zhName="程序所在路径")
	private String moSvrPath;
	/** 程序名 */
		@EntityParaAnno(zhName="程序名")
	private String moSvrName;
	/** 程序启用的端口，没有启用端口时为0,多个端口时,用逗号分开 */
		@EntityParaAnno(zhName="程序启用的端口，没有启用端口时为0,多个端口时,用逗号分开")
	private String moSvrPort;
	/** 程序运行状态,0运行中1未运行2不适用 */
		@EntityParaAnno(zhName="程序运行状态,0运行中1未运行2不适用")
	private String moSvrRunStatus;
	/** 端口状态0端口启用中1端口未启用2不适用 */
		@EntityParaAnno(zhName="端口状态0端口启用中1端口未启用2不适用")
	private String moSvrPortStatus;
	/** 数据采集时间 */
		@EntityParaAnno(zhName="数据采集时间")
	private String moCollectTime;
	
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
	/** 程序所在路径 */
	public void setMoSvrPath(String moSvrPath) {
		this.moSvrPath = moSvrPath == null ? null : moSvrPath.trim();
	}
	public String getMoSvrPath() {
		return this.moSvrPath;
	}
	/** 程序名 */
	public void setMoSvrName(String moSvrName) {
		this.moSvrName = moSvrName == null ? null : moSvrName.trim();
	}
	public String getMoSvrName() {
		return this.moSvrName;
	}
	/** 程序启用的端口，没有启用端口时为0,多个端口时,用逗号分开 */
	public void setMoSvrPort(String moSvrPort) {
		this.moSvrPort = moSvrPort == null ? null : moSvrPort.trim();
	}
	public String getMoSvrPort() {
		return this.moSvrPort;
	}
	/** 程序运行状态,0运行中1未运行2不适用 */
	public void setMoSvrRunStatus(String moSvrRunStatus) {
		this.moSvrRunStatus = moSvrRunStatus == null ? null : moSvrRunStatus.trim();
	}
	public String getMoSvrRunStatus() {
		return this.moSvrRunStatus;
	}
	/** 端口状态0端口启用中1端口未启用2不适用 */
	public void setMoSvrPortStatus(String moSvrPortStatus) {
		this.moSvrPortStatus = moSvrPortStatus == null ? null : moSvrPortStatus.trim();
	}
	public String getMoSvrPortStatus() {
		return this.moSvrPortStatus;
	}
	/** 数据采集时间 */
	public void setMoCollectTime(String moCollectTime) {
		this.moCollectTime = moCollectTime == null ? null : moCollectTime.trim();
	}
	public String getMoCollectTime() {
		return this.moCollectTime;
	}
}