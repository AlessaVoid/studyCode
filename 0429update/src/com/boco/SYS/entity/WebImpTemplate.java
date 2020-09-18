package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebImpTemplate实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebImpTemplate extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 模板编号 */
	private java.lang.String templateCode;
	/** 模板名称 */
	private java.lang.String templateName;
	/** 存放路径 */
	private java.lang.String templateUrl;
	/** 备注 */
	private java.lang.String remark;
	
	/** setter\getter方法 */
	/** 模板编号 */
	public void setTemplateCode(java.lang.String templateCode) {
		this.templateCode = templateCode == null ? null : templateCode.trim();
	}
	public java.lang.String getTemplateCode() {
		return this.templateCode;
	}
	/** 模板名称 */
	public void setTemplateName(java.lang.String templateName) {
		this.templateName = templateName == null ? null : templateName.trim();
	}
	public java.lang.String getTemplateName() {
		return this.templateName;
	}
	/** 存放路径 */
	public void setTemplateUrl(java.lang.String templateUrl) {
		this.templateUrl = templateUrl == null ? null : templateUrl.trim();
	}
	public java.lang.String getTemplateUrl() {
		return this.templateUrl;
	}
	/** 备注 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
}