package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbCheckList实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCheckList extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 数据日期 */
		@EntityParaAnno(zhName="数据日期")
	private String transDate;
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
	private String grantBalance;
	/** 贷款金额 */
		@EntityParaAnno(zhName="贷款金额")
	private String amt;
	/** 贷款余额 */
		@EntityParaAnno(zhName="贷款余额")
	private String balance;
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
	private String executeInterest;
	/** 客户经理号 */
		@EntityParaAnno(zhName="客户经理号")
	private String managerCode;
	/** 客户经理名 */
		@EntityParaAnno(zhName="客户经理名")
	private String managerName;
	/** 借据状态0正常1结清2核销 */
		@EntityParaAnno(zhName="借据状态0正常1结清2核销")
	private String paperStatus;
	/** 对账状态0未对账 1已对账2对账信息无效（发生对账失败时，全批次都更改为无效） */
		@EntityParaAnno(zhName="对账状态0未对账 1已对账2对账信息无效（发生对账失败时，全批次都更改为无效）")
	private String checkStatus;
	/** 备注信息 */
		@EntityParaAnno(zhName="备注信息")
	private String remark;
	
	/** setter\getter方法 */
	/** 数据日期 */
	public void setTransDate(String transDate) {
		this.transDate = transDate == null ? null : transDate.trim();
	}
	public String getTransDate() {
		return this.transDate;
	}
	/** 业务系统 */
	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}
	public String getSystemid() {
		return this.systemid;
	}
	/** 借据编号 */
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode == null ? null : paperCode.trim();
	}
	public String getPaperCode() {
		return this.paperCode;
	}
	/** 合同号 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode == null ? null : contractCode.trim();
	}
	public String getContractCode() {
		return this.contractCode;
	}
	/** 授信编码 */
	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode == null ? null : grantCode.trim();
	}
	public String getGrantCode() {
		return this.grantCode;
	}
	/** 授信余额 */
	public void setGrantBalance(String grantBalance) {
		this.grantBalance = grantBalance == null ? null : grantBalance.trim();
	}
	public String getGrantBalance() {
		return this.grantBalance;
	}
	/** 贷款金额 */
	public void setAmt(String amt) {
		this.amt = amt == null ? null : amt.trim();
	}
	public String getAmt() {
		return this.amt;
	}
	/** 贷款余额 */
	public void setBalance(String balance) {
		this.balance = balance == null ? null : balance.trim();
	}
	public String getBalance() {
		return this.balance;
	}
	/** 币种 */
	public void setCcy(String ccy) {
		this.ccy = ccy == null ? null : ccy.trim();
	}
	public String getCcy() {
		return this.ccy;
	}
	/** 原始到期日 */
	public void setOriLimit(String oriLimit) {
		this.oriLimit = oriLimit == null ? null : oriLimit.trim();
	}
	public String getOriLimit() {
		return this.oriLimit;
	}
	/** 放款时间 */
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate == null ? null : loanDate.trim();
	}
	public String getLoanDate() {
		return this.loanDate;
	}
	/** 逾期天数 */
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	public Integer getOverdueDays() {
		return this.overdueDays;
	}
	/** 到期日 */
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate == null ? null : limitDate.trim();
	}
	public String getLimitDate() {
		return this.limitDate;
	}
	/** 交易机构 */
	public void setTransOrgan(String transOrgan) {
		this.transOrgan = transOrgan == null ? null : transOrgan.trim();
	}
	public String getTransOrgan() {
		return this.transOrgan;
	}
	/** 产品代码 */
	public void setProductCode(String productCode) {
		this.productCode = productCode == null ? null : productCode.trim();
	}
	public String getProductCode() {
		return this.productCode;
	}
	/** 客户编号 */
	public void setCustCode(String custCode) {
		this.custCode = custCode == null ? null : custCode.trim();
	}
	public String getCustCode() {
		return this.custCode;
	}
	/** 客户姓名 */
	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}
	public String getCustName() {
		return this.custName;
	}
	/** 证件类型 */
	public void setCertType(String certType) {
		this.certType = certType == null ? null : certType.trim();
	}
	public String getCertType() {
		return this.certType;
	}
	/** 证件号码 */
	public void setCertNum(String certNum) {
		this.certNum = certNum == null ? null : certNum.trim();
	}
	public String getCertNum() {
		return this.certNum;
	}
	/** 客户所属行业 */
	public void setCustIndustry(String custIndustry) {
		this.custIndustry = custIndustry == null ? null : custIndustry.trim();
	}
	public String getCustIndustry() {
		return this.custIndustry;
	}
	/** 贷款用途 */
	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage == null ? null : loanUsage.trim();
	}
	public String getLoanUsage() {
		return this.loanUsage;
	}
	/** 贷款投放行业 */
	public void setLoanIndustry(String loanIndustry) {
		this.loanIndustry = loanIndustry == null ? null : loanIndustry.trim();
	}
	public String getLoanIndustry() {
		return this.loanIndustry;
	}
	/** 利率类型 */
	public void setInterestType(String interestType) {
		this.interestType = interestType == null ? null : interestType.trim();
	}
	public String getInterestType() {
		return this.interestType;
	}
	/** 浮动利率 */
	public void setFloatingInterest(Float floatingInterest) {
		this.floatingInterest = floatingInterest;
	}
	public Float getFloatingInterest() {
		return this.floatingInterest;
	}
	/** 基准利率 */
	public void setBaseInterest(Float baseInterest) {
		this.baseInterest = baseInterest;
	}
	public Float getBaseInterest() {
		return this.baseInterest;
	}
	/** 执行利率 */
	public void setExecuteInterest(String executeInterest) {
		this.executeInterest = executeInterest == null ? null : executeInterest.trim();
	}
	public String getExecuteInterest() {
		return this.executeInterest;
	}
	/** 客户经理号 */
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode == null ? null : managerCode.trim();
	}
	public String getManagerCode() {
		return this.managerCode;
	}
	/** 客户经理名 */
	public void setManagerName(String managerName) {
		this.managerName = managerName == null ? null : managerName.trim();
	}
	public String getManagerName() {
		return this.managerName;
	}
	/** 借据状态0正常1结清2核销 */
	public void setPaperStatus(String paperStatus) {
		this.paperStatus = paperStatus == null ? null : paperStatus.trim();
	}
	public String getPaperStatus() {
		return this.paperStatus;
	}
	/** 对账状态0未对账 1已对账2对账信息无效（发生对账失败时，全批次都更改为无效） */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus == null ? null : checkStatus.trim();
	}
	public String getCheckStatus() {
		return this.checkStatus;
	}
	/** 备注信息 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public String getRemark() {
		return this.remark;
	}
}