package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebAreaOrganInfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebAreaOrganInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 地区管理对应字典表dictNo */
	private java.lang.String dictNo;
	/** 地区管理对应字典表dictName */
	private java.lang.String dictName;
	/** 地区代码 */
	private java.lang.String areaCode;
	/** 地区名称 */
	private java.lang.String areaName;
	/** 省份代码 */
	private java.lang.String provCode;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	
	/** setter\getter方法 */
	/** 地区代码 */
	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}
	public java.lang.String getAreaCode() {
		return this.areaCode;
	}
	/** 省份代码 */
	public void setProvCode(java.lang.String provCode) {
		this.provCode = provCode == null ? null : provCode.trim();
	}
	public java.lang.String getProvCode() {
		return this.provCode;
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
	/** 地区名称 */
	public java.lang.String getAreaName() {
		return areaName;
	}
	/** 地区名称 */
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	/** 地区对应字典表名称 */
	public java.lang.String getDictNo() {
		return dictNo;
	}
	/** 地区对应字典表名称 */
	public void setDictNo(java.lang.String dictNo) {
		this.dictNo = dictNo;
	}
	/** 地区管理对应字典表dictName */
	public java.lang.String getDictName() {
		return dictName;
	}
	/** 地区管理对应字典表dictName */
	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName;
	}
}