package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 公共提示表||公共提示表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class WebPublicPromptTable extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id||id */
		@EntityParaAnno(zhName="id")
	private java.lang.String id;
	/** 内容||内容 */
		@EntityParaAnno(zhName="内容")
	private java.lang.String content;
	/** 操作人||操作人 */
		@EntityParaAnno(zhName="操作人")
	private java.lang.String operCode;
	/** 操作时间||操作时间 */
		@EntityParaAnno(zhName="操作时间")
	private java.lang.String operTime;
	/** 操作日期||操作日期 */
		@EntityParaAnno(zhName="操作日期")
	private java.lang.String operDate;
	/** 状态||状态 1-启用，1-停用 */
		@EntityParaAnno(zhName="状态")
	private java.lang.String useStatus;
	
	/** setter\getter方法 */
	/** id||id */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** 内容||内容 */
	public void setContent(java.lang.String content) {
		this.content = content == null ? null : content.trim();
	}
	public java.lang.String getContent() {
		return this.content;
	}
	/** 操作人||操作人 */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
	/** 操作时间||操作时间 */
	public void setOperTime(java.lang.String operTime) {
		this.operTime = operTime == null ? null : operTime.trim();
	}
	public java.lang.String getOperTime() {
		return this.operTime;
	}
	/** 操作日期||操作日期 */
	public void setOperDate(java.lang.String operDate) {
		this.operDate = operDate == null ? null : operDate.trim();
	}
	public java.lang.String getOperDate() {
		return this.operDate;
	}
	/** 状态||状态 1-启用，1-停用 */
	public void setUseStatus(java.lang.String useStatus) {
		this.useStatus = useStatus == null ? null : useStatus.trim();
	}
	public java.lang.String getUseStatus() {
		return this.useStatus;
	}
}