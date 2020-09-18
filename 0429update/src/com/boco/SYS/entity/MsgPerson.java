package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ���Ͷ���-��Ա��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class MsgPerson extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String name;
	/** �ֻ��� */
		@EntityParaAnno(zhName="�ֻ���")
	private String phoneNumber;
	/** ״̬ 1-���� 2-ͣ�� */
		@EntityParaAnno(zhName="״̬ 1-���� 2-ͣ��")
	private String status;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String groupEmp;
	
	/** setter\getter���� */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** ���� */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getName() {
		return this.name;
	}
	/** �ֻ��� */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	/** ״̬ 1-���� 2-ͣ�� */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
	public String getStatus() {
		return this.status;
	}
	/** ���� */
	public void setGroupEmp(String groupEmp) {
		this.groupEmp = groupEmp == null ? null : groupEmp.trim();
	}
	public String getGroupEmp() {
		return this.groupEmp;
	}
}