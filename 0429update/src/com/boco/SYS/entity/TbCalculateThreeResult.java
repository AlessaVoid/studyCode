package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbCalculateThreeResult实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCalculateThreeResult extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** organcode */
		@EntityParaAnno(zhName="organcode")
	private String organcode;
	/** month */
		@EntityParaAnno(zhName="month")
	private String month;
	/** reqListId */
		@EntityParaAnno(zhName="reqListId")
	private String reqListId;
	/** reqListName */
		@EntityParaAnno(zhName="reqListName")
	private String reqListName;
	/** code */
		@EntityParaAnno(zhName="code")
	private String code;
	/** oldNum */
		@EntityParaAnno(zhName="oldNum")
	private String oldNum;
	/** newNum */
		@EntityParaAnno(zhName="newNum")
	private String newNum;
	
	/** setter\getter方法 */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** organcode */
	public void setOrgancode(String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public String getOrgancode() {
		return this.organcode;
	}
	/** month */
	public void setMonth(String month) {
		this.month = month == null ? null : month.trim();
	}
	public String getMonth() {
		return this.month;
	}
	/** reqListId */
	public void setReqListId(String reqListId) {
		this.reqListId = reqListId == null ? null : reqListId.trim();
	}
	public String getReqListId() {
		return this.reqListId;
	}
	/** reqListName */
	public void setReqListName(String reqListName) {
		this.reqListName = reqListName == null ? null : reqListName.trim();
	}
	public String getReqListName() {
		return this.reqListName;
	}
	/** code */
	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}
	public String getCode() {
		return this.code;
	}
	/** oldNum */
	public void setOldNum(String oldNum) {
		this.oldNum = oldNum == null ? null : oldNum.trim();
	}
	public String getOldNum() {
		return this.oldNum;
	}
	/** newNum */
	public void setNewNum(String newNum) {
		this.newNum = newNum == null ? null : newNum.trim();
	}
	public String getNewNum() {
		return this.newNum;
	}
}