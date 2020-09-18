package com.boco.SYS.entity;

import java.text.SimpleDateFormat;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * GfErrInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class GfErrInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 系统代码-内 */
	private java.lang.String gfSysCode;
	/** 响应代码-内 */
	private java.lang.String gfRetCode;
	/** 响应信息-内 */
	private java.lang.String gfRetInfo;
	/** 系统代码-外 */
	private java.lang.String otherSysCode;
	/** 响应代码-外 */
	private java.lang.String otherRetCode;
	/** 响应信息-外 */
	private java.lang.String otherRetInfo;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	
	/** setter\getter方法 */
	/** 系统代码-内 */
	public void setGfSysCode(java.lang.String gfSysCode) {
		this.gfSysCode = gfSysCode == null ? null : gfSysCode.trim();
	}
	public java.lang.String getGfSysCode() {
		return this.gfSysCode;
	}
	/** 响应代码-内 */
	public void setGfRetCode(java.lang.String gfRetCode) {
		this.gfRetCode = gfRetCode == null ? null : gfRetCode.trim();
	}
	public java.lang.String getGfRetCode() {
		return this.gfRetCode;
	}
	/** 响应信息-内 */
	public void setGfRetInfo(java.lang.String gfRetInfo) {
		this.gfRetInfo = gfRetInfo == null ? null : gfRetInfo.trim();
	}
	public java.lang.String getGfRetInfo() {
		return this.gfRetInfo;
	}
	/** 系统代码-外 */
	public void setOtherSysCode(java.lang.String otherSysCode) {
		this.otherSysCode = otherSysCode == null ? null : otherSysCode.trim();
	}
	public java.lang.String getOtherSysCode() {
		return this.otherSysCode;
	}
	/** 响应代码-外 */
	public void setOtherRetCode(java.lang.String otherRetCode) {
		this.otherRetCode = otherRetCode == null ? null : otherRetCode.trim();
	}
	public java.lang.String getOtherRetCode() {
		return this.otherRetCode;
	}
	/** 响应信息-外 */
	public void setOtherRetInfo(java.lang.String otherRetInfo) {
		this.otherRetInfo = otherRetInfo == null ? null : otherRetInfo.trim();
	}
	public java.lang.String getOtherRetInfo() {
		return this.otherRetInfo;
	}
	/** 最后修改日期 */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** 最后修改时间 */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** 最后操作员 */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
}