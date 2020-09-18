package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebReviewSublist实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebReviewSublist extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 复核代码:日期+审批序列 */
	private java.lang.String appNo;
	/** 复核数据序号 */
	private java.lang.String orderNo;
	/** 复核数据 */
	private java.lang.String appData;
	
	/** setter\getter方法 */
	/** 复核代码:日期+审批序列 */
	public void setAppNo(java.lang.String appNo) {
		this.appNo = appNo == null ? null : appNo.trim();
	}
	public java.lang.String getAppNo() {
		return this.appNo;
	}
	/** 复核数据序号 */
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	/** 复核数据 */
	public void setAppData(java.lang.String appData) {
		this.appData = appData == null ? null : appData.trim();
	}
	public java.lang.String getAppData() {
		return this.appData;
	}
}