package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * GfDict实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class GfDict extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 字典标识 */
	private java.lang.String dictNo;
	/** 字典名称 */
	private java.lang.String dictName;
	/** 字典主键-内部 */
	private java.lang.String dictKeyIn;
	/** 字典值-内部 */
	private java.lang.String dictValueIn;
	/** 字典主键-外部 */
	private java.lang.String dictKeyOut;
	/** 字典值-外部 */
	private java.lang.String dictValueOut;
	/** 标识序号 */
	private java.lang.Integer dictNoOrder;
	/** 字典描述 */
	private java.lang.String dictDesc;
	/** 状态 */
	private java.lang.String status;
	/** 更新时间 */
	private java.lang.String updateTime;
	/** 更新日期 */
	private java.lang.String updateDate;
	public java.lang.String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.lang.String updateDate) {
		this.updateDate = updateDate;
	}
	/** 创建人员 */
	private java.lang.String createOper;
	
	/** setter\getter方法 */
	/** 字典标识 */
	public void setDictNo(java.lang.String dictNo) {
		this.dictNo = dictNo == null ? null : dictNo.trim();
	}
	public java.lang.String getDictNo() {
		return this.dictNo;
	}
	/** 字典名称 */
	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName == null ? null : dictName.trim();
	}
	public java.lang.String getDictName() {
		return this.dictName;
	}
	/** 字典主键-内部 */
	public void setDictKeyIn(java.lang.String dictKeyIn) {
		this.dictKeyIn = dictKeyIn == null ? null : dictKeyIn.trim();
	}
	public java.lang.String getDictKeyIn() {
		return this.dictKeyIn;
	}
	/** 字典值-内部 */
	public void setDictValueIn(java.lang.String dictValueIn) {
		this.dictValueIn = dictValueIn == null ? null : dictValueIn.trim();
	}
	public java.lang.String getDictValueIn() {
		return this.dictValueIn;
	}
	/** 字典主键-外部 */
	public void setDictKeyOut(java.lang.String dictKeyOut) {
		this.dictKeyOut = dictKeyOut == null ? null : dictKeyOut.trim();
	}
	public java.lang.String getDictKeyOut() {
		return this.dictKeyOut;
	}
	/** 字典值-外部 */
	public void setDictValueOut(java.lang.String dictValueOut) {
		this.dictValueOut = dictValueOut == null ? null : dictValueOut.trim();
	}
	public java.lang.String getDictValueOut() {
		return this.dictValueOut;
	}
	/** 标识序号 */
	public void setDictNoOrder(java.lang.Integer dictNoOrder) {
		this.dictNoOrder = dictNoOrder;
	}
	public java.lang.Integer getDictNoOrder() {
		return this.dictNoOrder;
	}
	/** 字典描述 */
	public void setDictDesc(java.lang.String dictDesc) {
		this.dictDesc = dictDesc == null ? null : dictDesc.trim();
	}
	public java.lang.String getDictDesc() {
		return this.dictDesc;
	}
	/** 状态  1-启用 2-禁用*/
	public void setStatus(java.lang.String status) {
		this.status = status == null ? null : status.trim();
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	/** 更新时间 */
	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}
	public java.lang.String getUpdateTime() {
		return this.updateTime;
	}
	/** 创建人员 */
	public void setCreateOper(java.lang.String createOper) {
		this.createOper = createOper == null ? null : createOper.trim();
	}
	public java.lang.String getCreateOper() {
		return this.createOper;
	}
}