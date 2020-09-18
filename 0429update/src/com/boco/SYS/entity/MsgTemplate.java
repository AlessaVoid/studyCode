package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 短信模板实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class MsgTemplate extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 模板id */
		@EntityParaAnno(zhName="模板id")
	private String id;
	/** 模板名称 */
		@EntityParaAnno(zhName="模板名称")
	private String name;
	/** 模板内容 */
		@EntityParaAnno(zhName="模板内容")
	private String template;
	
	/** setter\getter方法 */
	/** 模板id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** 模板名称 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getName() {
		return this.name;
	}
	/** 模板内容 */
	public void setTemplate(String template) {
		this.template = template == null ? null : template.trim();
	}
	public String getTemplate() {
		return this.template;
	}
}