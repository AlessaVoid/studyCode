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
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbBatchTask extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private String taskCode;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String taskName;
	/** չʾ˳�� */
		@EntityParaAnno(zhName="չʾ˳��")
	private Integer disOrder;
	
	/** setter\getter���� */
	/** ������� */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode == null ? null : taskCode.trim();
	}
	public String getTaskCode() {
		return this.taskCode;
	}
	/** �������� */
	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? null : taskName.trim();
	}
	public String getTaskName() {
		return this.taskName;
	}
	/** չʾ˳�� */
	public void setDisOrder(Integer disOrder) {
		this.disOrder = disOrder;
	}
	public Integer getDisOrder() {
		return this.disOrder;
	}
}