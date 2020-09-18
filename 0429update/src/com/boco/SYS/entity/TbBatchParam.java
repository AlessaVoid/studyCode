package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbBatchParam实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbBatchParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 系统日期 */
		@EntityParaAnno(zhName="系统日期")
	private String systemDate;
	/** 系统月份 */
		@EntityParaAnno(zhName="系统月份")
	private String systemMonth;
	/** id */
		@EntityParaAnno(zhName="id")
	private String paramId;
	
	/** setter\getter方法 */
	/** 系统日期 */
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate == null ? null : systemDate.trim();
	}
	public String getSystemDate() {
		return this.systemDate;
	}
	/** 系统月份 */
	public void setSystemMonth(String systemMonth) {
		this.systemMonth = systemMonth == null ? null : systemMonth.trim();
	}
	public String getSystemMonth() {
		return this.systemMonth;
	}
	/** id */
	public void setParamId(String paramId) {
		this.paramId = paramId == null ? null : paramId.trim();
	}
	public String getParamId() {
		return this.paramId;
	}
}