package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * TbOrganRateScoreImportData实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbOrganRateScoreImportData extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** 机构 */
		@EntityParaAnno(zhName="机构")
	private String organcode;
	/** 月份 */
		@EntityParaAnno(zhName="月份")
	private String month;
	/** 月末不良率 */
		@EntityParaAnno(zhName="月末不良率")
	private BigDecimal monthEndRatio;
	/** 年初不良率 */
		@EntityParaAnno(zhName="年初不良率")
	private BigDecimal yearBeginRatio;
	/** 个人自营存款年增量 */
		@EntityParaAnno(zhName="个人自营存款年增量")
	private BigDecimal personDepositYearIncrement;
	/** 公司存款年增量 */
		@EntityParaAnno(zhName="公司存款年增量")
	private BigDecimal companyDepositYearIncrement;
	/** 新发生贷款利率 */
		@EntityParaAnno(zhName="新发生贷款利率")
	private BigDecimal newLoanRatio;
	/** 全行平均贷款利率 */
		@EntityParaAnno(zhName="全行平均贷款利率")
	private BigDecimal bankAverageLoanRatio;
	
	/** setter\getter方法 */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** 机构 */
	public void setOrgancode(String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public String getOrgancode() {
		return this.organcode;
	}
	/** 月份 */
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