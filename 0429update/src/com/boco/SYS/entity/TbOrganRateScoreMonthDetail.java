package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * ���������¶������ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbOrganRateScoreMonthDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/**  �����������α�-tb_organ_rate_score   id */
		@EntityParaAnno(zhName=" �����������α�-tb_organ_rate_score   id")
	private String refId;
	/** �·� */
		@EntityParaAnno(zhName="�·�")
	private String rateMonth;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String rateOrgan;
	/** �¶��ܷ� */
		@EntityParaAnno(zhName="�¶��ܷ�")
	private BigDecimal rateScoreMonth;
	/** ��������÷� */
		@EntityParaAnno(zhName="��������÷�")
	private BigDecimal badConditionScore;
	/** ��ĩ������ */
		@EntityParaAnno(zhName="��ĩ������")
	private BigDecimal badConditionRatioMonthEnd;
	/** ��������� */
		@EntityParaAnno(zhName="���������")
	private BigDecimal badConditionRatioYearBegin;
	/** �����ʱ仯��� */
		@EntityParaAnno(zhName="�����ʱ仯���")
	private BigDecimal badConditionRatioChange;
	/** ��Ӫ��������ȵ÷� */
		@EntityParaAnno(zhName="��Ӫ��������ȵ÷�")
	private BigDecimal depositLoanRatioScore;
	/** ������������� */
		@EntityParaAnno(zhName="�������������")
	private BigDecimal loanYearAdd;
	/** ������Ӫ��������� */
		@EntityParaAnno(zhName="������Ӫ���������")
	private BigDecimal personalDepositYearIncrement;
	/** ��˾��������� */
		@EntityParaAnno(zhName="��˾���������")
	private BigDecimal companyDepositYearIncrement;
	/** ��Ӫ��������� */
		@EntityParaAnno(zhName="��Ӫ���������")
	private BigDecimal depositLoanRatioChange;
	/** �·����������ʵ÷� */
		@EntityParaAnno(zhName="�·����������ʵ÷�")
	private BigDecimal newLoanRatioScore;
	/** �·����������� */
		@EntityParaAnno(zhName="�·�����������")
	private BigDecimal newLoanRatio;
	/** ȫ��ƽ���������� */
		@EntityParaAnno(zhName="ȫ��ƽ����������")
	private BigDecimal bankAverageLoanRatio;
	/** �������ʱ仯��� */
		@EntityParaAnno(zhName="�������ʱ仯���")
	private BigDecimal newLoanRatioChange;
	/** ����ṹ�÷� */
		@EntityParaAnno(zhName="����ṹ�÷�")
	private BigDecimal loanStructScore;
	/** �ܴ��� */
		@EntityParaAnno(zhName="�ܴ���")
	private BigDecimal loanCount;
	/** �³�ʵ�������� */
		@EntityParaAnno(zhName="�³�ʵ��������")
	private BigDecimal monthBeginEntityLoan;
	/** ��ĩʵ�������� */
		@EntityParaAnno(zhName="��ĩʵ��������")
	private BigDecimal monthEndEntityLoan;
	/** �³�ʵ��������ռ�� */
		@EntityParaAnno(zhName="�³�ʵ��������ռ��")
	private BigDecimal monthBeginEntityLoanChange;
	/** ��ĩʵ��������ռ�� */
		@EntityParaAnno(zhName="��ĩʵ��������ռ��")
	private BigDecimal monthEndEntityLoanChange;
	/** ��ĩʵ��������ռ�ȱ仯 */
		@EntityParaAnno(zhName="��ĩʵ��������ռ�ȱ仯")
	private BigDecimal monthEndBeginEntityLoanChange;
	/** ����ģռ�÷Ѻ͹�ģ���÷ѵ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷ѵ÷�")
	private BigDecimal scaleAmountScore;
	/** ���з�Ϣ��� */
		@EntityParaAnno(zhName="���з�Ϣ���")
	private BigDecimal organScaleAmount;
	/** ȫ�з�Ϣ��� */
		@EntityParaAnno(zhName="ȫ�з�Ϣ���")
	private BigDecimal bankScaleAmount;
	/** ��Ϣ���ռ�� */
		@EntityParaAnno(zhName="��Ϣ���ռ��")
	private BigDecimal scaleAmountChange;
	/** �ƻ�����ƫ��ȵ÷� */
		@EntityParaAnno(zhName="�ƻ�����ƫ��ȵ÷�")
	private BigDecimal planSubmitDeviationScore;
	/** ��ĩʵ�������� */
		@EntityParaAnno(zhName="��ĩʵ��������")
	private BigDecimal monthEndIncrement;
	/** �³��������� */
		@EntityParaAnno(zhName="�³���������")
	private BigDecimal monthBeginReport;
	/** ƫ����� */
		@EntityParaAnno(zhName="ƫ�����")
	private BigDecimal planSubmitDeviationChange;
	/** �ƻ�ִ��ƫ���-��ĩ����ƫ��ȵ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ���-��ĩ����ƫ��ȵ÷�")
	private BigDecimal planExecuteDeviationMonthMidScore;
	/** ����ͳһ��̬������ƻ� */
		@EntityParaAnno(zhName="����ͳһ��̬������ƻ�")
	private BigDecimal monthMidPlan;
	/** ��ĩͳһ��̬������ƻ� */
		@EntityParaAnno(zhName="��ĩͳһ��̬������ƻ�")
	private BigDecimal monthEndPlan;
	/** ��ĩ����ƫ����� */
		@EntityParaAnno(zhName="��ĩ����ƫ�����")
	private BigDecimal planExecuteDeviationMonthMidChange;
	/** �ƻ�ִ��ƫ���-��ĩ�³�ƫ��ȵ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ���-��ĩ�³�ƫ��ȵ÷�")
	private BigDecimal planExecuteDeviationMonthInitScore;
	/** �³������´�ƻ� */
		@EntityParaAnno(zhName="�³������´�ƻ�")
	private BigDecimal monthBeginPlan;
	/** ��ĩ�³�ƫ����� */
		@EntityParaAnno(zhName="��ĩ�³�ƫ�����")
	private BigDecimal planExecuteDeviationMonthInitChange;
	/** ����Ƶ�ε÷� */
		@EntityParaAnno(zhName="����Ƶ�ε÷�")
	private BigDecimal adjCountScore;
	/** ����Ƶ�δ��� */
		@EntityParaAnno(zhName="����Ƶ�δ���")
	private BigDecimal adjCount;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String createTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String updateTime;
	/** �޸���Ա */
		@EntityParaAnno(zhName="�޸���Ա")
	private String updateOper;
	/** �ӷ��� */
	@EntityParaAnno(zhName="�ӷ���")
	private BigDecimal addScore;
	/** ��ע */
	@EntityParaAnno(zhName="��ע")
	private String remark;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRateMonth() {
		return rateMonth;
	}

	public void setRateMonth(String rateMonth) {
		this.rateMonth = rateMonth;
	}

	public String getRateOrgan() {
		return rateOrgan;
	}

	public void setRateOrgan(String rateOrgan) {
		this.rateOrgan = rateOrgan;
	}

	public BigDecimal getRateScoreMonth() {
		return rateScoreMonth;
	}

	public void setRateScoreMonth(BigDecimal rateScoreMonth) {
		this.rateScoreMonth = rateScoreMonth;
	}

	public BigDecimal getBadConditionScore() {
		return badConditionScore;
	}

	public void setBadConditionScore(BigDecimal badConditionScore) {
		this.badConditionScore = badConditionScore;
	}

	public BigDecimal getBadConditionRatioMonthEnd() {
		return badConditionRatioMonthEnd;
	}

	public void setBadConditionRatioMonthEnd(BigDecimal badConditionRatioMonthEnd) {
		this.badConditionRatioMonthEnd = badConditionRatioMonthEnd;
	}

	public BigDecimal getBadConditionRatioYearBegin() {
		return badConditionRatioYearBegin;
	}

	public void setBadConditionRatioYearBegin(BigDecimal badConditionRatioYearBegin) {
		this.badConditionRatioYearBegin = badConditionRatioYearBegin;
	}

	public BigDecimal getBadConditionRatioChange() {
		return badConditionRatioChange;
	}

	public void setBadConditionRatioChange(BigDecimal badConditionRatioChange) {
		this.badConditionRatioChange = badConditionRatioChange;
	}

	public BigDecimal getDepositLoanRatioScore() {
		return depositLoanRatioScore;
	}

	public void setDepositLoanRatioScore(BigDecimal depositLoanRatioScore) {
		this.depositLoanRatioScore = depositLoanRatioScore;
	}

	public BigDecimal getLoanYearAdd() {
		return loanYearAdd;
	}

	public void setLoanYearAdd(BigDecimal loanYearAdd) {
		this.loanYearAdd = loanYearAdd;
	}

	public BigDecimal getPersonalDepositYearIncrement() {
		return personalDepositYearIncrement;
	}

	public void setPersonalDepositYearIncrement(BigDecimal personalDepositYearIncrement) {
		this.personalDepositYearIncrement = personalDepositYearIncrement;
	}

	public BigDecimal getCompanyDepositYearIncrement() {
		return companyDepositYearIncrement;
	}

	public void setCompanyDepositYearIncrement(BigDecimal companyDepositYearIncrement) {
		this.companyDepositYearIncrement = companyDepositYearIncrement;
	}

	public BigDecimal getDepositLoanRatioChange() {
		return depositLoanRatioChange;
	}

	public void setDepositLoanRatioChange(BigDecimal depositLoanRatioChange) {
		this.depositLoanRatioChange = depositLoanRatioChange;
	}

	public BigDecimal getNewLoanRatioScore() {
		return newLoanRatioScore;
	}

	public void setNewLoanRatioScore(BigDecimal newLoanRatioScore) {
		this.newLoanRatioScore = newLoanRatioScore;
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

	public BigDecimal getNewLoanRatioChange() {
		return newLoanRatioChange;
	}

	public void setNewLoanRatioChange(BigDecimal newLoanRatioChange) {
		this.newLoanRatioChange = newLoanRatioChange;
	}

	public BigDecimal getLoanStructScore() {
		return loanStructScore;
	}

	public void setLoanStructScore(BigDecimal loanStructScore) {
		this.loanStructScore = loanStructScore;
	}

	public BigDecimal getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(BigDecimal loanCount) {
		this.loanCount = loanCount;
	}

	public BigDecimal getMonthBeginEntityLoan() {
		return monthBeginEntityLoan;
	}

	public void setMonthBeginEntityLoan(BigDecimal monthBeginEntityLoan) {
		this.monthBeginEntityLoan = monthBeginEntityLoan;
	}

	public BigDecimal getMonthEndEntityLoan() {
		return monthEndEntityLoan;
	}

	public void setMonthEndEntityLoan(BigDecimal monthEndEntityLoan) {
		this.monthEndEntityLoan = monthEndEntityLoan;
	}

	public BigDecimal getMonthBeginEntityLoanChange() {
		return monthBeginEntityLoanChange;
	}

	public void setMonthBeginEntityLoanChange(BigDecimal monthBeginEntityLoanChange) {
		this.monthBeginEntityLoanChange = monthBeginEntityLoanChange;
	}

	public BigDecimal getMonthEndEntityLoanChange() {
		return monthEndEntityLoanChange;
	}

	public void setMonthEndEntityLoanChange(BigDecimal monthEndEntityLoanChange) {
		this.monthEndEntityLoanChange = monthEndEntityLoanChange;
	}

	public BigDecimal getMonthEndBeginEntityLoanChange() {
		return monthEndBeginEntityLoanChange;
	}

	public void setMonthEndBeginEntityLoanChange(BigDecimal monthEndBeginEntityLoanChange) {
		this.monthEndBeginEntityLoanChange = monthEndBeginEntityLoanChange;
	}

	public BigDecimal getScaleAmountScore() {
		return scaleAmountScore;
	}

	public void setScaleAmountScore(BigDecimal scaleAmountScore) {
		this.scaleAmountScore = scaleAmountScore;
	}

	public BigDecimal getOrganScaleAmount() {
		return organScaleAmount;
	}

	public void setOrganScaleAmount(BigDecimal organScaleAmount) {
		this.organScaleAmount = organScaleAmount;
	}

	public BigDecimal getBankScaleAmount() {
		return bankScaleAmount;
	}

	public void setBankScaleAmount(BigDecimal bankScaleAmount) {
		this.bankScaleAmount = bankScaleAmount;
	}

	public BigDecimal getScaleAmountChange() {
		return scaleAmountChange;
	}

	public void setScaleAmountChange(BigDecimal scaleAmountChange) {
		this.scaleAmountChange = scaleAmountChange;
	}

	public BigDecimal getPlanSubmitDeviationScore() {
		return planSubmitDeviationScore;
	}

	public void setPlanSubmitDeviationScore(BigDecimal planSubmitDeviationScore) {
		this.planSubmitDeviationScore = planSubmitDeviationScore;
	}

	public BigDecimal getMonthEndIncrement() {
		return monthEndIncrement;
	}

	public void setMonthEndIncrement(BigDecimal monthEndIncrement) {
		this.monthEndIncrement = monthEndIncrement;
	}

	public BigDecimal getMonthBeginReport() {
		return monthBeginReport;
	}

	public void setMonthBeginReport(BigDecimal monthBeginReport) {
		this.monthBeginReport = monthBeginReport;
	}

	public BigDecimal getPlanSubmitDeviationChange() {
		return planSubmitDeviationChange;
	}

	public void setPlanSubmitDeviationChange(BigDecimal planSubmitDeviationChange) {
		this.planSubmitDeviationChange = planSubmitDeviationChange;
	}

	public BigDecimal getPlanExecuteDeviationMonthMidScore() {
		return planExecuteDeviationMonthMidScore;
	}

	public void setPlanExecuteDeviationMonthMidScore(BigDecimal planExecuteDeviationMonthMidScore) {
		this.planExecuteDeviationMonthMidScore = planExecuteDeviationMonthMidScore;
	}

	public BigDecimal getMonthMidPlan() {
		return monthMidPlan;
	}

	public void setMonthMidPlan(BigDecimal monthMidPlan) {
		this.monthMidPlan = monthMidPlan;
	}

	public BigDecimal getMonthEndPlan() {
		return monthEndPlan;
	}

	public void setMonthEndPlan(BigDecimal monthEndPlan) {
		this.monthEndPlan = monthEndPlan;
	}

	public BigDecimal getPlanExecuteDeviationMonthMidChange() {
		return planExecuteDeviationMonthMidChange;
	}

	public void setPlanExecuteDeviationMonthMidChange(BigDecimal planExecuteDeviationMonthMidChange) {
		this.planExecuteDeviationMonthMidChange = planExecuteDeviationMonthMidChange;
	}

	public BigDecimal getPlanExecuteDeviationMonthInitScore() {
		return planExecuteDeviationMonthInitScore;
	}

	public void setPlanExecuteDeviationMonthInitScore(BigDecimal planExecuteDeviationMonthInitScore) {
		this.planExecuteDeviationMonthInitScore = planExecuteDeviationMonthInitScore;
	}

	public BigDecimal getMonthBeginPlan() {
		return monthBeginPlan;
	}

	public void setMonthBeginPlan(BigDecimal monthBeginPlan) {
		this.monthBeginPlan = monthBeginPlan;
	}

	public BigDecimal getPlanExecuteDeviationMonthInitChange() {
		return planExecuteDeviationMonthInitChange;
	}

	public void setPlanExecuteDeviationMonthInitChange(BigDecimal planExecuteDeviationMonthInitChange) {
		this.planExecuteDeviationMonthInitChange = planExecuteDeviationMonthInitChange;
	}

	public BigDecimal getAdjCountScore() {
		return adjCountScore;
	}

	public void setAdjCountScore(BigDecimal adjCountScore) {
		this.adjCountScore = adjCountScore;
	}

	public BigDecimal getAdjCount() {
		return adjCount;
	}

	public void setAdjCount(BigDecimal adjCount) {
		this.adjCount = adjCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateOper() {
		return updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	public BigDecimal getAddScore() {
		return addScore;
	}

	public void setAddScore(BigDecimal addScore) {
		this.addScore = addScore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}