package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * TbOrganRateScoreImportDataʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbOrganRateScoreImportData extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String organcode;
	/** �·� */
		@EntityParaAnno(zhName="�·�")
	private String month;
	/** ��ĩ������ */
		@EntityParaAnno(zhName="��ĩ������")
	private BigDecimal monthEndRatio;
	/** ��������� */
		@EntityParaAnno(zhName="���������")
	private BigDecimal yearBeginRatio;
	/** ������Ӫ��������� */
		@EntityParaAnno(zhName="������Ӫ���������")
	private BigDecimal personDepositYearIncrement;
	/** ��˾��������� */
		@EntityParaAnno(zhName="��˾���������")
	private BigDecimal companyDepositYearIncrement;
	/** �·����������� */
		@EntityParaAnno(zhName="�·�����������")
	private BigDecimal newLoanRatio;
	/** ȫ��ƽ���������� */
		@EntityParaAnno(zhName="ȫ��ƽ����������")
	private BigDecimal bankAverageLoanRatio;
	
	/** setter\getter���� */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** ���� */
	public void setOrgancode(String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public String getOrgancode() {
		return this.organcode;
	}
	/** �·� */
	public void setMonth(String month) {
		this.month = month == null ? null : month.trim();
	}
	public String getMonth() {
		return this.month;
	}

	public BigDecimal getMonthEndRatio() {
		return monthEndRatio;
	}

	public void setMonthEndRatio(BigDecimal monthEndRatio) {
		this.monthEndRatio = monthEndRatio;
	}

	public BigDecimal getYearBeginRatio() {
		return yearBeginRatio;
	}

	public void setYearBeginRatio(BigDecimal yearBeginRatio) {
		this.yearBeginRatio = yearBeginRatio;
	}

	public BigDecimal getPersonDepositYearIncrement() {
		return personDepositYearIncrement;
	}

	public void setPersonDepositYearIncrement(BigDecimal personDepositYearIncrement) {
		this.personDepositYearIncrement = personDepositYearIncrement;
	}

	public BigDecimal getCompanyDepositYearIncrement() {
		return companyDepositYearIncrement;
	}

	public void setCompanyDepositYearIncrement(BigDecimal companyDepositYearIncrement) {
		this.companyDepositYearIncrement = companyDepositYearIncrement;
	}

	public BigDecimal getNewLoanRatio() {
		return newLoanRatio;
	}

	public void setNewLoanRatio(BigDecimal newLoanRatio) {
		this.newLoanRatio = newLoanRatio;
	}

	public BigDecimal getBankAverageLoanRatio() {
		return bankAverageLoanRatio;
	}

	public void setBankAverageLoanRatio(BigDecimal bankAverageLoanRatio) {
		this.bankAverageLoanRatio = bankAverageLoanRatio;
	}
}