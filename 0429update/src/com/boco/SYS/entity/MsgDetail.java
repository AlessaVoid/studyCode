package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 短信详情表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class MsgDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** 发送的手机号 */
		@EntityParaAnno(zhName="发送的手机号")
	private String phoneNumber;
	/** 姓名 */
		@EntityParaAnno(zhName="姓名")
	private String name;
	/** 短信内容 */
		@EntityParaAnno(zhName="短信内容")
	private String sendMessage;
	/** 发送完成返回内容 */
		@EntityParaAnno(zhName="发送完成返回内容")
	private String returnMessage;
	/** 短信要发送的时间 */
		@EntityParaAnno(zhName="短信要发送的时间")
	private String sendDate;
	/** 短信发送状态1完成 2未发送 */
		@EntityParaAnno(zhName="短信发送状态1完成 2未发送")
	private String status;
	
	/** setter\getter方法 */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** 发送的手机号 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	/** 姓名 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getName() {
		return this.name;
	}
	/** 短信内容 */
	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage == null ? null : sendMessage.trim();
	}
	public String getSendMessage() {
		return this.sendMessage;
	}
	/** 发送完成返回内容 */
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage == null ? null : returnMessage.trim();
	}
	public String getReturnMessage() {
		return this.returnMessage;
	}
	/** 短信要发送的时间 */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate == null ? null : sendDate.trim();
	}
	public String getSendDate() {
		return this.sendDate;
	}
	/** 短信发送状态1完成 2未发送 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
	public String getStatus() {
		return this.status;
	}
}