package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ����ר��_������ϱ�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReportComb extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����������к� */
		@EntityParaAnno(zhName="����������к�")
	private String combId;
	/** ������ϱ��� */
		@EntityParaAnno(zhName="������ϱ���")
	private String combCode;
	/** ����������� */
		@EntityParaAnno(zhName="�����������")
	private String combName;
	/** ������ϼ��� */
		@EntityParaAnno(zhName="������ϼ���")
	private int combLevel;
	/** �������״̬ -1-��ǰ������ɾ��,  0-�ɱ��ϼ��������,  1-��ʾ�ѱ������ϼ�����ռ�� */
		@EntityParaAnno(zhName="�������״̬ -1-��ǰ������ɾ��,  0-�ɱ��ϼ��������,  1-��ʾ�ѱ������ϼ�����ռ��")
	private int combStatus;
	/** ���ִ����� */
		@EntityParaAnno(zhName="���ִ�����")
	private String combCreateOper;
	/** ���ִ���ʱ�� */
		@EntityParaAnno(zhName="���ִ���ʱ��")
	private String combCreateTime;
	/** ���ָ����� */
		@EntityParaAnno(zhName="���ָ�����")
	private String combUpdateOper;
	/** ���ָ���ʱ�� */
		@EntityParaAnno(zhName="���ָ���ʱ��")
	private String combUpdateTime;
	/** ���������ֶ� */
		@EntityParaAnno(zhName="���������ֶ�")
	private Integer combOrder;
	
	/** setter\getter���� */
	/** ����������к� */
	public void setCombId(String combId) {
		this.combId = combId == null ? null : combId.trim();
	}
	public String getCombId() {
		return this.combId;
	}
	/** ������ϱ��� */
	public void setCombCode(String combCode) {
		this.combCode = combCode == null ? null : combCode.trim();
	}
	public String getCombCode() {
		return this.combCode;
	}
	/** ����������� */
	public void setCombName(String combName) {
		this.combName = combName == null ? null : combName.trim();
	}
	public String getCombName() {
		return this.combName;
	}
	/** ������ϼ��� */
	public void setCombLevel(Integer combLevel) {
		this.combLevel = combLevel;
	}
	public Integer getCombLevel() {
		return this.combLevel;
	}
	/** �������״̬ -1-��ǰ������ɾ��,  0-�ɱ��ϼ��������,  1-��ʾ�ѱ������ϼ�����ռ�� */
	public void setCombStatus(Integer combStatus) {
		this.combStatus = combStatus;
	}
	public Integer getCombStatus() {
		return this.combStatus;
	}
	/** ���ִ����� */
	public void setCombCreateOper(String combCreateOper) {
		this.combCreateOper = combCreateOper == null ? null : combCreateOper.trim();
	}
	public String getCombCreateOper() {
		return this.combCreateOper;
	}
	/** ���ִ���ʱ�� */
	public void setCombCreateTime(String combCreateTime) {
		this.combCreateTime = combCreateTime == null ? null : combCreateTime.trim();
	}
	public String getCombCreateTime() {
		return this.combCreateTime;
	}
	/** ���ָ����� */
	public void setCombUpdateOper(String combUpdateOper) {
		this.combUpdateOper = combUpdateOper == null ? null : combUpdateOper.trim();
	}
	public String getCombUpdateOper() {
		return this.combUpdateOper;
	}
	/** ���ָ���ʱ�� */
	public void setCombUpdateTime(String combUpdateTime) {
		this.combUpdateTime = combUpdateTime == null ? null : combUpdateTime.trim();
	}
	public String getCombUpdateTime() {
		return this.combUpdateTime;
	}
	/** ���������ֶ� */
	public void setCombOrder(Integer combOrder) {
		this.combOrder = combOrder;
	}
	public Integer getCombOrder() {
		return this.combOrder;
	}
}