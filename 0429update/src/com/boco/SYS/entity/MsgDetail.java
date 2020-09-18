package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ���������ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class MsgDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** ���͵��ֻ��� */
		@EntityParaAnno(zhName="���͵��ֻ���")
	private String phoneNumber;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String name;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String sendMessage;
	/** ������ɷ������� */
		@EntityParaAnno(zhName="������ɷ�������")
	private String returnMessage;
	/** ����Ҫ���͵�ʱ�� */
		@EntityParaAnno(zhName="����Ҫ���͵�ʱ��")
	private String sendDate;
	/** ���ŷ���״̬1��� 2δ���� */
		@EntityParaAnno(zhName="���ŷ���״̬1��� 2δ����")
	private String status;
	
	/** setter\getter���� */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** ���͵��ֻ��� */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	/** ���� */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getName() {
		return this.name;
	}
	/** �������� */
	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage == null ? null : sendMessage.trim();
	}
	public String getSendMessage() {
		return this.sendMessage;
	}
	/** ������ɷ������� */
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage == null ? null : returnMessage.trim();
	}
	public String getReturnMessage() {
		return this.returnMessage;
	}
	/** ����Ҫ���͵�ʱ�� */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate == null ? null : sendDate.trim();
	}
	public String getSendDate() {
		return this.sendDate;
	}
	/** ���ŷ���״̬1��� 2δ���� */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
	public String getStatus() {
		return this.status;
	}
}