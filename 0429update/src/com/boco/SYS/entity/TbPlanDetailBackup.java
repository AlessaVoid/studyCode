package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * �ƻ��ƶ���ϸ���ݱ���һ���ƶ��ƻ���ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbPlanDetailBackup extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** pdId */
		@EntityParaAnno(zhName="pdId")
	private String pdId;
	/** ���κ� */
		@EntityParaAnno(zhName="���κ�")
	private String pdRefId;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String pdOrgan;
	/** �����·� */
		@EntityParaAnno(zhName="�����·�")
	private String pdMonth;
	/** ����������� */
		@EntityParaAnno(zhName="�����������")
	private String pdLoanType;
	/** ��� */
		@EntityParaAnno(zhName="���")
	private BigDecimal pdAmount;
	/** ��λ */
		@EntityParaAnno(zhName="��λ")
	private Integer pdUnit;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String pdCreateTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String pdUpdateTime;
	
	/** setter\getter���� */
	/** pdId */
	public void setPdId(String pdId) {
		this.pdId = pdId == null ? null : pdId.trim();
	}
	public String getPdId() {
		return this.pdId;
	}
	/** ���κ� */
	public void setPdRefId(String pdRefId) {
		this.pdRefId = pdRefId == null ? null : pdRefId.trim();
	}
	public String getPdRefId() {
		return this.pdRefId;
	}
	/** ������ */
	public void setPdOrgan(String pdOrgan) {
		this.pdOrgan = pdOrgan == null ? null : pdOrgan.trim();
	}
	public String getPdOrgan() {
		return this.pdOrgan;
	}
	/** �����·� */
	public void setPdMonth(String pdMonth) {
		this.pdMonth = pdMonth == null ? null : pdMonth.trim();
	}
	public String getPdMonth() {
		return this.pdMonth;
	}
	/** ����������� */
	public void setPdLoanType(String pdLoanType) {
		this.pdLoanType = pdLoanType == null ? null : pdLoanType.trim();
	}
	public String getPdLoanType() {
		return this.pdLoanType;
	}
	/** ��� */
	public BigDecimal getPdAmount() {
		return pdAmount;
	}

	public void setPdAmount(BigDecimal pdAmount) {
		this.pdAmount = pdAmount;
	}

	/** ��λ */
	public void setPdUnit(Integer pdUnit) {
		this.pdUnit = pdUnit;
	}
	public Integer getPdUnit() {
		return this.pdUnit;
	}
	/** ����ʱ�� */
	public void setPdCreateTime(String pdCreateTime) {
		this.pdCreateTime = pdCreateTime == null ? null : pdCreateTime.trim();
	}
	public String getPdCreateTime() {
		return this.pdCreateTime;
	}
	/** ����ʱ�� */
	public void setPdUpdateTime(String pdUpdateTime) {
		this.pdUpdateTime = pdUpdateTime == null ? null : pdUpdateTime.trim();
	}
	public String getPdUpdateTime() {
		return this.pdUpdateTime;
	}
}