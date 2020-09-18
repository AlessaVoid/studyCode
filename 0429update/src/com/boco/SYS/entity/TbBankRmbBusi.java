package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 商业银行人民币业务统计表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbBankRmbBusi extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 主键 */
		@EntityParaAnno(zhName="主键")
	private String id;
	/** 银行名称 */
		@EntityParaAnno(zhName="银行名称")
	private String bankname;
	/** 统计日期 */
		@EntityParaAnno(zhName="统计日期")
	private String statisticsDay;
	/** 贷款类型 */
		@EntityParaAnno(zhName="贷款类型")
	private String loanType;
	/** 余额 */
		@EntityParaAnno(zhName="余额")
	private Double balance;
	/** 排名 */
		@EntityParaAnno(zhName="排名")
	private Integer rank;
	/** 当月新增 */
		@EntityParaAnno(zhName="当月新增")
	private Double monthlyAdd;
	/** 当月排名 */
		@EntityParaAnno(zhName="当月排名")
	private Integer monthlyRank;
	/** 当年新增 */
		@EntityParaAnno(zhName="当年新增")
	private Double yearAdd;
	/** 当年排名 */
		@EntityParaAnno(zhName="当年排名")
	private Integer yearRank;
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
	/** 银行名称 */
	public void setBankname(String bankname) {
		this.bankname = bankname == null ? null : bankname.trim();
	}
	public String getBankname() {
		return this.bankname;
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
	/** 排名 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getRank() {
		return this.rank;
	}
	/** 当月新增 */
	public void setMonthlyAdd(Double monthlyAdd) {
		this.monthlyAdd = monthlyAdd;
	}
	public Double getMonthlyAdd() {
		return this.monthlyAdd;
	}
	/** 当月排名 */
	public void setMonthlyRank(Integer monthlyRank) {
		this.monthlyRank = monthlyRank;
	}
	public Integer getMonthlyRank() {
		return this.monthlyRank;
	}
	/** 当年新增 */
	public void setYearAdd(Double yearAdd) {
		this.yearAdd = yearAdd;
	}
	public Double getYearAdd() {
		return this.yearAdd;
	}
	/** 当年排名 */
	public void setYearRank(Integer yearRank) {
		this.yearRank = yearRank;
	}
	public Integer getYearRank() {
		return this.yearRank;
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