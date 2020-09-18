package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 借据表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbLoanInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 借据创建时间 */
		@EntityParaAnno(zhName="借据创建时间")
	private String createDate;
	/** 业务系统 */
		@EntityParaAnno(zhName="业务系统")
	private String systemid;
	/** 借据编号 */
		@EntityParaAnno(zhName="借据编号")
	private String paperCode;
	/** 合同号 */
		@EntityParaAnno(zhName="合同号")
	private String contractCode;
	/** 授信编码 */
		@EntityParaAnno(zhName="授信编码")
	private String grantCode;
	/** 授信余额 */
		@EntityParaAnno(zhName="授信余额")
	private BigDecimal grantBalance;
	/** 贷款金额 */
		@EntityParaAnno(zhName="贷款金额")
	private BigDecimal amt;
	/** 贷款余额 */
		@EntityParaAnno(zhName="贷款余额")
	private BigDecimal balance;
	/** 币种 */
		@EntityParaAnno(zhName="币种")
	private String ccy;
	/** 原始到期日 */
		@EntityParaAnno(zhName="原始到期日")
	private String oriLimit;
	/** 放款时间 */
		@EntityParaAnno(zhName="放款时间")
	private String loanDate;
	/** 逾期天数 */
		@EntityParaAnno(zhName="逾期天数")
	private Integer overdueDays;
	/** 到期日 */
		@EntityParaAnno(zhName="到期日")
	private String limitDate;
	/** 交易机构 */
		@EntityParaAnno(zhName="交易机构")
	private String transOrgan;
	/** 产品代码 */
		@EntityParaAnno(zhName="产品代码")
	private String productCode;
	/** 产品名称 */
		@EntityParaAnno(zhName="产品名称")
	private String productName;
	/** 客户编号 */
		@EntityParaAnno(zhName="客户编号")
	private String custCode;
	/** 客户姓名 */
		@EntityParaAnno(zhName="客户姓名")
	private String custName;
	/** 证件类型 */
		@EntityParaAnno(zhName="证件类型")
	private String certType;
	/** 证件号码 */
		@EntityParaAnno(zhName="证件号码")
	private String certNum;
	/** 客户所属行业 */
		@EntityParaAnno(zhName="客户所属行业")
	private String custIndustry;
	/** 贷款用途 */
		@EntityParaAnno(zhName="贷款用途")
	private String loanUsage;
	/** 贷款投放行业 */
		@EntityParaAnno(zhName="贷款投放行业")
	private String loanIndustry;
	/** 利率类型 */
		@EntityParaAnno(zhName="利率类型")
	private String interestType;
	/** 浮动利率 */
		@EntityParaAnno(zhName="浮动利率")
	private Float floatingInterest;
	/** 基准利率 */
		@EntityParaAnno(zhName="基准利率")
	private Float baseInterest;
	/** 执行利率 */
		@EntityParaAnno(zhName="执行利率")
	private BigDecimal executeInterest;
	/** 客户经理号 */
		@EntityParaAnno(zhName="客户经理号")
	private String managerCode;
	/** 客户经理名 */
		@EntityParaAnno(zhName="客户经理名")
	private String managerName;
	/** 借据状态 */
		@EntityParaAnno(zhName="借据状态")
	private String paperStatus;
	/** 修改时间 */
		@EntityParaAnno(zhName="修改时间")
	private String lastModifyDate;
	/** 是否自动审批交易 */
		@EntityParaAnno(zhName="是否自动审批交易")
	private String isAuto;
	/** 会计日期 */
		@EntityParaAnno(zhName="会计日期")
	private String accDate;
	/** quotaUseDetail */
		@EntityParaAnno(zhName="quotaUseDetail")
	private String quotaUseDetail;
	/** planOrgan */
		@EntityParaAnno(zhName="planOrgan")
	private String planOrgan;
	/** remark */
		@EntityParaAnno(zhName="remark")
	private String remark;
	
	/** setter\getter方法 */
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getPaperCode() {
		return paperCode;
	}

	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public BigDecimal getGrantBalance() {
		return grantBalance;
	}

	public void setGrantBalance(BigDecimal grantBalance) {
		this.grantBalance = grantBalance;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getOriLimit() {
		return oriLimit;
	}

	public void setOriLimit(String oriLimit) {
		this.oriLimit = oriLimit;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public String getTransOrgan() {
		return transOrgan;
	}

	public void setTransOrgan(String transOrgan) {
		this.transOrgan = transOrgan;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCustIndustry() {
		return custIndustry;
	}

	public void setCustIndustry(String custIndustry) {
		this.custIndustry = custIndustry;
	}

	public String getLoanUsage() {
		return loanUsage;
	}

	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
	}

	public String getLoanIndustry() {
		return loanIndustry;
	}

	public void setLoanIndustry(String loanIndustry) {
		this.loanIndustry = loanIndustry;
	}

	public String getInterestType() {
		return interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public Float getFloatingInterest() {
		return floatingInterest;
	}

	public void setFloatingInterest(Float floatingInterest) {
		this.floatingInterest = floatingInterest;
	}

	public Float getBaseInterest() {
		return baseInterest;
	}

	public void setBaseInterest(Float baseInterest) {
		this.baseInterest = baseInterest;
	}

	public BigDecimal getExecuteInterest() {
		return executeInterest;
	}

	public void setExecuteInterest(BigDecimal executeInterest) {
		this.executeInterest = executeInterest;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(String paperStatus) {
		this.paperStatus = paperStatus;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	public String getAccDate() {
		return accDate;
	}

	public void setAccDate(String accDate) {
		this.accDate = accDate;
	}

	public String getQuotaUseDetail() {
		return quotaUseDetail;
	}

	public void setQuotaUseDetail(String quotaUseDetail) {
		this.quotaUseDetail = quotaUseDetail;
	}

	public String getPlanOrgan() {
		return planOrgan;
	}

	public void setPlanOrgan(String planOrgan) {
		this.planOrgan = planOrgan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}