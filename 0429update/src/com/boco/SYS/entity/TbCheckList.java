package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbCheckListʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbCheckList extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String transDate;
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
	private String grantBalance;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String amt;
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private String balance;
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
	private String executeInterest;
	/** �ͻ������ */
		@EntityParaAnno(zhName="�ͻ������")
	private String managerCode;
	/** �ͻ������� */
		@EntityParaAnno(zhName="�ͻ�������")
	private String managerName;
	/** ���״̬0����1����2���� */
		@EntityParaAnno(zhName="���״̬0����1����2����")
	private String paperStatus;
	/** ����״̬0δ���� 1�Ѷ���2������Ϣ��Ч����������ʧ��ʱ��ȫ���ζ�����Ϊ��Ч�� */
		@EntityParaAnno(zhName="����״̬0δ���� 1�Ѷ���2������Ϣ��Ч����������ʧ��ʱ��ȫ���ζ�����Ϊ��Ч��")
	private String checkStatus;
	/** ��ע��Ϣ */
		@EntityParaAnno(zhName="��ע��Ϣ")
	private String remark;
	
	/** setter\getter���� */
	/** �������� */
	public void setTransDate(String transDate) {
		this.transDate = transDate == null ? null : transDate.trim();
	}
	public String getTransDate() {
		return this.transDate;
	}
	/** ҵ��ϵͳ */
	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}
	public String getSystemid() {
		return this.systemid;
	}
	/** ��ݱ�� */
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode == null ? null : paperCode.trim();
	}
	public String getPaperCode() {
		return this.paperCode;
	}
	/** ��ͬ�� */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode == null ? null : contractCode.trim();
	}
	public String getContractCode() {
		return this.contractCode;
	}
	/** ���ű��� */
	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode == null ? null : grantCode.trim();
	}
	public String getGrantCode() {
		return this.grantCode;
	}
	/** ������� */
	public void setGrantBalance(String grantBalance) {
		this.grantBalance = grantBalance == null ? null : grantBalance.trim();
	}
	public String getGrantBalance() {
		return this.grantBalance;
	}
	/** ������ */
	public void setAmt(String amt) {
		this.amt = amt == null ? null : amt.trim();
	}
	public String getAmt() {
		return this.amt;
	}
	/** ������� */
	public void setBalance(String balance) {
		this.balance = balance == null ? null : balance.trim();
	}
	public String getBalance() {
		return this.balance;
	}
	/** ���� */
	public void setCcy(String ccy) {
		this.ccy = ccy == null ? null : ccy.trim();
	}
	public String getCcy() {
		return this.ccy;
	}
	/** ԭʼ������ */
	public void setOriLimit(String oriLimit) {
		this.oriLimit = oriLimit == null ? null : oriLimit.trim();
	}
	public String getOriLimit() {
		return this.oriLimit;
	}
	/** �ſ�ʱ�� */
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate == null ? null : loanDate.trim();
	}
	public String getLoanDate() {
		return this.loanDate;
	}
	/** �������� */
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	public Integer getOverdueDays() {
		return this.overdueDays;
	}
	/** ������ */
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate == null ? null : limitDate.trim();
	}
	public String getLimitDate() {
		return this.limitDate;
	}
	/** ���׻��� */
	public void setTransOrgan(String transOrgan) {
		this.transOrgan = transOrgan == null ? null : transOrgan.trim();
	}
	public String getTransOrgan() {
		return this.transOrgan;
	}
	/** ��Ʒ���� */
	public void setProductCode(String productCode) {
		this.productCode = productCode == null ? null : productCode.trim();
	}
	public String getProductCode() {
		return this.productCode;
	}
	/** �ͻ���� */
	public void setCustCode(String custCode) {
		this.custCode = custCode == null ? null : custCode.trim();
	}
	public String getCustCode() {
		return this.custCode;
	}
	/** �ͻ����� */
	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}
	public String getCustName() {
		return this.custName;
	}
	/** ֤������ */
	public void setCertType(String certType) {
		this.certType = certType == null ? null : certType.trim();
	}
	public String getCertType() {
		return this.certType;
	}
	/** ֤������ */
	public void setCertNum(String certNum) {
		this.certNum = certNum == null ? null : certNum.trim();
	}
	public String getCertNum() {
		return this.certNum;
	}
	/** �ͻ�������ҵ */
	public void setCustIndustry(String custIndustry) {
		this.custIndustry = custIndustry == null ? null : custIndustry.trim();
	}
	public String getCustIndustry() {
		return this.custIndustry;
	}
	/** ������; */
	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage == null ? null : loanUsage.trim();
	}
	public String getLoanUsage() {
		return this.loanUsage;
	}
	/** ����Ͷ����ҵ */
	public void setLoanIndustry(String loanIndustry) {
		this.loanIndustry = loanIndustry == null ? null : loanIndustry.trim();
	}
	public String getLoanIndustry() {
		return this.loanIndustry;
	}
	/** �������� */
	public void setInterestType(String interestType) {
		this.interestType = interestType == null ? null : interestType.trim();
	}
	public String getInterestType() {
		return this.interestType;
	}
	/** �������� */
	public void setFloatingInterest(Float floatingInterest) {
		this.floatingInterest = floatingInterest;
	}
	public Float getFloatingInterest() {
		return this.floatingInterest;
	}
	/** ��׼���� */
	public void setBaseInterest(Float baseInterest) {
		this.baseInterest = baseInterest;
	}
	public Float getBaseInterest() {
		return this.baseInterest;
	}
	/** ִ������ */
	public void setExecuteInterest(String executeInterest) {
		this.executeInterest = executeInterest == null ? null : executeInterest.trim();
	}
	public String getExecuteInterest() {
		return this.executeInterest;
	}
	/** �ͻ������ */
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode == null ? null : managerCode.trim();
	}
	public String getManagerCode() {
		return this.managerCode;
	}
	/** �ͻ������� */
	public void setManagerName(String managerName) {
		this.managerName = managerName == null ? null : managerName.trim();
	}
	public String getManagerName() {
		return this.managerName;
	}
	/** ���״̬0����1����2���� */
	public void setPaperStatus(String paperStatus) {
		this.paperStatus = paperStatus == null ? null : paperStatus.trim();
	}
	public String getPaperStatus() {
		return this.paperStatus;
	}
	/** ����״̬0δ���� 1�Ѷ���2������Ϣ��Ч����������ʧ��ʱ��ȫ���ζ�����Ϊ��Ч�� */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus == null ? null : checkStatus.trim();
	}
	public String getCheckStatus() {
		return this.checkStatus;
	}
	/** ��ע��Ϣ */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public String getRemark() {
		return this.remark;
	}
}