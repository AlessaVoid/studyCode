package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * ��ݱ�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbLoanInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��ݴ���ʱ�� */
		@EntityParaAnno(zhName="��ݴ���ʱ��")
	private String createDate;
	/** ҵ��ϵͳ */
		@EntityParaAnno(zhName="ҵ��ϵͳ")
	private String systemid;
	/** ��ݱ�� */
		@EntityParaAnno(zhName="��ݱ��")
	private String paperCode;
	/** ��ͬ�� */
		@EntityParaAnno(zhName="��ͬ��")
	private String contractCode;
	/** ���ű��� */
		@EntityParaAnno(zhName="���ű���")
	private String grantCode;
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private BigDecimal grantBalance;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private BigDecimal amt;
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private BigDecimal balance;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String ccy;
	/** ԭʼ������ */
		@EntityParaAnno(zhName="ԭʼ������")
	private String oriLimit;
	/** �ſ�ʱ�� */
		@EntityParaAnno(zhName="�ſ�ʱ��")
	private String loanDate;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private Integer overdueDays;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String limitDate;
	/** ���׻��� */
		@EntityParaAnno(zhName="���׻���")
	private String transOrgan;
	/** ��Ʒ���� */
		@EntityParaAnno(zhName="��Ʒ����")
	private String productCode;
	/** ��Ʒ���� */
		@EntityParaAnno(zhName="��Ʒ����")
	private String productName;
	/** �ͻ���� */
		@EntityParaAnno(zhName="�ͻ����")
	private String custCode;
	/** �ͻ����� */
		@EntityParaAnno(zhName="�ͻ�����")
	private String custName;
	/** ֤������ */
		@EntityParaAnno(zhName="֤������")
	private String certType;
	/** ֤������ */
		@EntityParaAnno(zhName="֤������")
	private String certNum;
	/** �ͻ�������ҵ */
		@EntityParaAnno(zhName="�ͻ�������ҵ")
	private String custIndustry;
	/** ������; */
		@EntityParaAnno(zhName="������;")
	private String loanUsage;
	/** ����Ͷ����ҵ */
		@EntityParaAnno(zhName="����Ͷ����ҵ")
	private String loanIndustry;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String interestType;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private Float floatingInterest;
	/** ��׼���� */
		@EntityParaAnno(zhName="��׼����")
	private Float baseInterest;
	/** ִ������ */
		@EntityParaAnno(zhName="ִ������")
	private BigDecimal executeInterest;
	/** �ͻ������ */
		@EntityParaAnno(zhName="�ͻ������")
	private String managerCode;
	/** �ͻ������� */
		@EntityParaAnno(zhName="�ͻ�������")
	private String managerName;
	/** ���״̬ */
		@EntityParaAnno(zhName="���״̬")
	private String paperStatus;
	/** �޸�ʱ�� */
		@EntityParaAnno(zhName="�޸�ʱ��")
	private String lastModifyDate;
	/** �Ƿ��Զ��������� */
		@EntityParaAnno(zhName="�Ƿ��Զ���������")
	private String isAuto;
	/** ������� */
		@EntityParaAnno(zhName="�������")
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
	
	/** setter\getter���� */
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