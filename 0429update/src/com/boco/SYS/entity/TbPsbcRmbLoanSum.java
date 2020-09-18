package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 人民币存贷款日报汇总表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbPsbcRmbLoanSum extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 主键 */
		@EntityParaAnno(zhName="主键")
	private String id;
	/** 统计日期 */
		@EntityParaAnno(zhName="统计日期")
	private String statisticsDay;
	/** 贷款类型 */
		@EntityParaAnno(zhName="贷款类型")
	private String loanType;
	/** 余额 */
		@EntityParaAnno(zhName="余额")
	private Double balance;
	/** 日增 */
		@EntityParaAnno(zhName="日增")
	private Double dayAdd;
	/** 月增 */
		@EntityParaAnno(zhName="月增")
	private Double monthlyAdd;
	/** 季增 */
		@EntityParaAnno(zhName="季增")
	private Double quarterAdd;
	/** 年增 */
		@EntityParaAnno(zhName="年增")
	private Double yearAdd;
	/** 年增幅 */
		@EntityParaAnno(zhName="年增幅")
	private Double annualGrowthRate;
	/** 创建人 */
		@EntityParaAnno(zhName="创建人")
	private String createdBy;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String createdTime;
	/** 更新人 */
		@EntityParaAnno(zhName="更新人")
	private String updatedBy;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String updatedTime;
	
	/** setter\getter方法 */
	/** 主键 */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** 统计日期 */
	public void setStatisticsDay(String statisticsDay) {
		this.statisticsDay = statisticsDay == null ? null : statisticsDay.trim();
	}
	public String getStatisticsDay() {
		return this.statisticsDay;
	}
	/** 贷款类型 */
	public void setLoanType(String loanType) {
		this.loanType = loanType == null ? null : loanType.trim();
	}
	public String getLoanType() {
		return this.loanType;
	}
	/** 余额 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getBalance() {
		return this.balance;
	}
	/** 日增 */
	public void setDayAdd(Double dayAdd) {
		this.dayAdd = dayAdd;
	}
	public Double getDayAdd() {
		return this.dayAdd;
	}
	/** 月增 */
	public void setMonthlyAdd(Double monthlyAdd) {
		this.monthlyAdd = monthlyAdd;
	}
	public Double getMonthlyAdd() {
		return this.monthlyAdd;
	}
	/** 季增 */
	public void setQuarterAdd(Double quarterAdd) {
		this.quarterAdd = quarterAdd;
	}
	public Double getQuarterAdd() {
		return this.quarterAdd;
	}
	/** 年增 */
	public void setYearAdd(Double yearAdd) {
		this.yearAdd = yearAdd;
	}
	public Double getYearAdd() {
		return this.yearAdd;
	}
	/** 年增幅 */
	public void setAnnualGrowthRate(Double annualGrowthRate) {
		this.annualGrowthRate = annualGrowthRate;
	}
	public Double getAnnualGrowthRate() {
		return this.annualGrowthRate;
	}
	/** 创建人 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? null : createdBy.trim();
	}
	public String getCreatedBy() {
		return this.createdBy;
	}
	/** 创建时间 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime == null ? null : createdTime.trim();
	}
	public String getCreatedTime() {
		return this.createdTime;
	}
	/** 更新人 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}
	public String getUpdatedBy() {
		return this.updatedBy;
	}
	/** 更新时间 */
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime == null ? null : updatedTime.trim();
	}
	public String getUpdatedTime() {
		return this.updatedTime;
	}
}