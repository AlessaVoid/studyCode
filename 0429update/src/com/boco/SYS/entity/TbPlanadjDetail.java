package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 *
 *
 * �Ŵ��ƻ��������������ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbPlanadjDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
	@EntityParaAnno(zhName="id")
	private java.lang.String planadjdId;
	/** ���κ� */
	@EntityParaAnno(zhName="���κ�")
	private java.lang.String planadjdRefId;
	/** ������ */
	@EntityParaAnno(zhName="������")
	private java.lang.String planadjdOrgan;
	/** �������� */
	@EntityParaAnno(zhName="��������")
	private java.lang.String planadjdLoanType;
	/** ��ʼ��� */
	@EntityParaAnno(zhName="��ʼ���")
	private BigDecimal planadjdInitAmount;
	/** ������� */
	@EntityParaAnno(zhName="�������")
	private BigDecimal planadjdAdjAmount;
	/** �������� */
	@EntityParaAnno(zhName="��������")
	private BigDecimal planadjdAdjedAmount;
	/** ��λ */
	@EntityParaAnno(zhName="��λ")
	private java.lang.Integer planadjdUnit;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String planadjdCreateTime;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String planadjdUpdateTime;
	/** �����·� */
	@EntityParaAnno(zhName="�����·�")
	private java.lang.String planadjdMonth;
	/** ������ */
	@EntityParaAnno(zhName="������")
	private BigDecimal planadjdReqAmount;

	private String __status;

	/** setter\getter���� */
	/** id */
	public void setPlanadjdId(java.lang.String planadjdId) {
		this.planadjdId = planadjdId == null ? null : planadjdId.trim();
	}
	public java.lang.String getPlanadjdId() {
		return this.planadjdId;
	}
	/** ���κ� */
	public void setPlanadjdRefId(java.lang.String planadjdRefId) {
		this.planadjdRefId = planadjdRefId == null ? null : planadjdRefId.trim();
	}
	public java.lang.String getPlanadjdRefId() {
		return this.planadjdRefId;
	}
	/** ������ */
	public void setPlanadjdOrgan(java.lang.String planadjdOrgan) {
		this.planadjdOrgan = planadjdOrgan == null ? null : planadjdOrgan.trim();
	}
	public java.lang.String getPlanadjdOrgan() {
		return this.planadjdOrgan;
	}
	/** �������� */
	public void setPlanadjdLoanType(java.lang.String planadjdLoanType) {
		this.planadjdLoanType = planadjdLoanType == null ? null : planadjdLoanType.trim();
	}
	public java.lang.String getPlanadjdLoanType() {
		return this.planadjdLoanType;
	}

	/** ��λ */
	public void setPlanadjdUnit(java.lang.Integer planadjdUnit) {
		this.planadjdUnit = planadjdUnit;
	}
	public java.lang.Integer getPlanadjdUnit() {
		return this.planadjdUnit;
	}
	/** ����ʱ�� */
	public void setPlanadjdCreateTime(java.lang.String planadjdCreateTime) {
		this.planadjdCreateTime = planadjdCreateTime == null ? null : planadjdCreateTime.trim();
	}
	public java.lang.String getPlanadjdCreateTime() {
		return this.planadjdCreateTime;
	}
	/** ����ʱ�� */
	public void setPlanadjdUpdateTime(java.lang.String planadjdUpdateTime) {
		this.planadjdUpdateTime = planadjdUpdateTime == null ? null : planadjdUpdateTime.trim();
	}
	public java.lang.String getPlanadjdUpdateTime() {
		return this.planadjdUpdateTime;
	}
	/** �����·� */
	public void setPlanadjdMonth(java.lang.String planadjdMonth) {
		this.planadjdMonth = planadjdMonth == null ? null : planadjdMonth.trim();
	}
	public java.lang.String getPlanadjdMonth() {
		return this.planadjdMonth;
	}

	public BigDecimal getPlanadjdInitAmount() {
		return planadjdInitAmount;
	}

	public void setPlanadjdInitAmount(BigDecimal planadjdInitAmount) {
		this.planadjdInitAmount = planadjdInitAmount;
	}

	public BigDecimal getPlanadjdAdjAmount() {
		return planadjdAdjAmount;
	}

	public void setPlanadjdAdjAmount(BigDecimal planadjdAdjAmount) {
		this.planadjdAdjAmount = planadjdAdjAmount;
	}

	public BigDecimal getPlanadjdAdjedAmount() {
		return planadjdAdjedAmount;
	}

	public void setPlanadjdAdjedAmount(BigDecimal planadjdAdjedAmount) {
		this.planadjdAdjedAmount = planadjdAdjedAmount;
	}

	public BigDecimal getPlanadjdReqAmount() {
		return planadjdReqAmount;
	}

	public void setPlanadjdReqAmount(BigDecimal planadjdReqAmount) {
		this.planadjdReqAmount = planadjdReqAmount;
	}

	public String get__status() {
		return __status;
	}

	public void set__status(String __status) {
		this.__status = __status;
	}
}