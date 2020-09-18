package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 发送短信-人员表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class MsgPerson extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** 姓名 */
		@EntityParaAnno(zhName="姓名")
	private String name;
	/** 手机号 */
		@EntityParaAnno(zhName="手机号")
	private String phoneNumber;
	/** 状态 1-启用 2-停用 */
		@EntityParaAnno(zhName="状态 1-启用 2-停用")
	private String status;
	/** 分组 */
		@EntityParaAnno(zhName="分组")
	private String groupEmp;
	
	/** setter\getter方法 */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** 姓名 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getName() {
		return this.name;
	}
	/** 手机号 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	/** 状态 1-启用 2-停用 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
	public String getStatus() {
		return this.status;
	}
	/** 分组 */
	public void setGroupEmp(String groupEmp) {
		this.groupEmp = groupEmp == null ? null : groupEmp.trim();
	}
	public String getGroupEmp() {
		return this.groupEmp;
	}
}