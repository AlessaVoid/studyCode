package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * FdUnichangeinfo实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class FdUnichangeinfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** uniauthoritylevel */
	private java.lang.String uniauthoritylevel;
	/** unioperiden */
	private java.lang.String unioperiden;
	/** operdegree */
	private java.lang.String operdegree;
	/** remark */
	private java.lang.String remark;
	
	/** setter\getter方法 */
	/** uniauthoritylevel */
	public void setUniauthoritylevel(java.lang.String uniauthoritylevel) {
		this.uniauthoritylevel = uniauthoritylevel == null ? null : uniauthoritylevel.trim();
	}
	public java.lang.String getUniauthoritylevel() {
		return this.uniauthoritylevel;
	}
	/** unioperiden */
	public void setUnioperiden(java.lang.String unioperiden) {
		this.unioperiden = unioperiden == null ? null : unioperiden.trim();
	}
	public java.lang.String getUnioperiden() {
		return this.unioperiden;
	}
	/** operdegree */
	public void setOperdegree(java.lang.String operdegree) {
		this.operdegree = operdegree == null ? null : operdegree.trim();
	}
	public java.lang.String getOperdegree() {
		return this.operdegree;
	}
	/** remark */
	public void setRemark(java.lang.String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
}