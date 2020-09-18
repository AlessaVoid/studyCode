package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * �������ּ��������ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbOrganRateScoreQuarterDetailBackup extends BaseEntity implements java.io.Serializable {
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
	/** ���ȵ÷� */
		@EntityParaAnno(zhName="���ȵ÷�")
	private BigDecimal rateScoreQuarter;
	/** �������ȵ÷�ȫ������ */
		@EntityParaAnno(zhName="�������ȵ÷�ȫ������")
	private BigDecimal rateRank;
	/** ������ռȫ�б��� */
		@EntityParaAnno(zhName="������ռȫ�б���")
	private BigDecimal rateRatio;
	/** ����������1-A��2-B��3-C��4-D�� */
		@EntityParaAnno(zhName="����������1-A��2-B��3-C��4-D��")
	private BigDecimal rateLevel;
	/** ����������ȵ÷� */
		@EntityParaAnno(zhName="����������ȵ÷�")
	private BigDecimal badConditionScoreQuarter;
	/** ���������һ���µ÷� */
		@EntityParaAnno(zhName="���������һ���µ÷�")
	private BigDecimal badConditionScoreOne;
	/** ��������ڶ����µ÷� */
		@EntityParaAnno(zhName="��������ڶ����µ÷�")
	private BigDecimal badConditionScoreTwo;
	/** ��������������µ÷� */
		@EntityParaAnno(zhName="��������������µ÷�")
	private BigDecimal badConditionScoreThree;
	/** ��Ӫ��������ȼ��ȵ÷� */
		@EntityParaAnno(zhName="��Ӫ��������ȼ��ȵ÷�")
	private BigDecimal depositLoanRatioScoreQuarter;
	/** ��Ӫ��������ȵ�һ���µ÷� */
		@EntityParaAnno(zhName="��Ӫ��������ȵ�һ���µ÷�")
	private BigDecimal depositLoanRatioScoreOne;
	/** ��Ӫ��������ȵڶ����µ÷� */
		@EntityParaAnno(zhName="��Ӫ��������ȵڶ����µ÷�")
	private BigDecimal depositLoanRatioScoreTwo;
	/** ��Ӫ��������ȵ������µ÷� */
		@EntityParaAnno(zhName="��Ӫ��������ȵ������µ÷�")
	private BigDecimal depositLoanRatioScoreThree;
	/** �·����������ʼ��ȵ÷� */
		@EntityParaAnno(zhName="�·����������ʼ��ȵ÷�")
	private BigDecimal newLoanRatioScoreQuarter;
	/** �·����������ʵ�һ���µ÷� */
		@EntityParaAnno(zhName="�·����������ʵ�һ���µ÷�")
	private BigDecimal newLoanRatioScoreOne;
	/** �·����������ʵڶ����µ÷� */
		@EntityParaAnno(zhName="�·����������ʵڶ����µ÷�")
	private BigDecimal newLoanRatioScoreTwo;
	/** �·����������ʵ������µ÷� */
		@EntityParaAnno(zhName="�·����������ʵ������µ÷�")
	private BigDecimal newLoanRatioScoreThree;
	/** ����ṹ���ȵ÷� */
		@EntityParaAnno(zhName="����ṹ���ȵ÷�")
	private BigDecimal loanStructScoreQuarter;
	/** ����ṹ��һ���µ÷� */
		@EntityParaAnno(zhName="����ṹ��һ���µ÷�")
	private BigDecimal loanStructScoreOne;
	/** ����ṹ�ڶ����µ÷� */
		@EntityParaAnno(zhName="����ṹ�ڶ����µ÷�")
	private BigDecimal loanStructScoreTwo;
	/** ����ṹ�������µ÷� */
		@EntityParaAnno(zhName="����ṹ�������µ÷�")
	private BigDecimal loanStructScoreThree;
	/** ����ģռ�÷Ѻ͹�ģ���÷Ѽ��ȵ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷Ѽ��ȵ÷�")
	private BigDecimal scaleAmountScoreQuarter;
	/** ����ģռ�÷Ѻ͹�ģ���÷ѵ�һ���µ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷ѵ�һ���µ÷�")
	private BigDecimal scaleAmountScoreOne;
	/** ����ģռ�÷Ѻ͹�ģ���÷ѵڶ����µ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷ѵڶ����µ÷�")
	private BigDecimal scaleAmountScoreTwo;
	/** ����ģռ�÷Ѻ͹�ģ���÷ѵ������µ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷ѵ������µ÷�")
	private BigDecimal scaleAmountScoreThree;
	/** �ƻ�����ƫ��ȼ��ȵ÷� */
		@EntityParaAnno(zhName="�ƻ�����ƫ��ȼ��ȵ÷�")
	private BigDecimal planSubmitDeviationScoreQuarter;
	/** �ƻ�����ƫ��ȵ�һ���µ÷� */
		@EntityParaAnno(zhName="�ƻ�����ƫ��ȵ�һ���µ÷�")
	private BigDecimal planSubmitDeviationScoreOne;
	/** �ƻ�����ƫ��ȵڶ����µ÷� */
		@EntityParaAnno(zhName="�ƻ�����ƫ��ȵڶ����µ÷�")
	private BigDecimal planSubmitDeviationScoreTwo;
	/** �ƻ�����ƫ��ȵ������µ÷� */
		@EntityParaAnno(zhName="�ƻ�����ƫ��ȵ������µ÷�")
	private BigDecimal planSubmitDeviationScoreThree;
	/** �ƻ�ִ��ƫ�����ĩ����ƫ��ȼ��ȵ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ����ƫ��ȼ��ȵ÷�")
	private BigDecimal planExecuteDeviationMonthMidScoreQuarter;
	/** �ƻ�ִ��ƫ�����ĩ����ƫ��ȵ�һ���µ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ����ƫ��ȵ�һ���µ÷�")
	private BigDecimal planExecuteDeviationMonthMidScoreOne;
	/** �ƻ�ִ��ƫ�����ĩ����ƫ��ȵڶ����µ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ����ƫ��ȵڶ����µ÷�")
	private BigDecimal planExecuteDeviationMonthMidScoreTwo;
	/** �ƻ�ִ��ƫ�����ĩ����ƫ��ȵ������µ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ����ƫ��ȵ������µ÷�")
	private BigDecimal planExecuteDeviationMonthMidScoreThree;
	/** �ƻ�ִ��ƫ�����ĩ�³�ƫ��ȼ��ȵ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ�³�ƫ��ȼ��ȵ÷�")
	private BigDecimal planExecuteDeviationMonthInitScoreQuarter;
	/** �ƻ�ִ��ƫ�����ĩ�³�ƫ��ȵ�һ���µ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ�³�ƫ��ȵ�һ���µ÷�")
	private BigDecimal planExecuteDeviationMonthInitScoreOne;
	/** �ƻ�ִ��ƫ�����ĩ�³�ƫ��ȵڶ����µ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ�³�ƫ��ȵڶ����µ÷�")
	private BigDecimal planExecuteDeviationMonthInitScoreTwo;
	/** �ƻ�ִ��ƫ�����ĩ�³�ƫ��ȵ������µ÷� */
		@EntityParaAnno(zhName="�ƻ�ִ��ƫ�����ĩ�³�ƫ��ȵ������µ÷�")
	private BigDecimal planExecuteDeviationMonthInitScoreThree;
	/** ����Ƶ�μ��ȵ÷� */
		@EntityParaAnno(zhName="����Ƶ�μ��ȵ÷�")
	private BigDecimal adjCountScoreQuarter;
	/** ����Ƶ�ε�һ���µ÷� */
		@EntityParaAnno(zhName="����Ƶ�ε�һ���µ÷�")
	private BigDecimal adjCountScoreOne;
	/** ����Ƶ�εڶ����µ÷� */
		@EntityParaAnno(zhName="����Ƶ�εڶ����µ÷�")
	private BigDecimal adjCountScoreTwo;
	/** ����Ƶ�ε������µ÷� */
		@EntityParaAnno(zhName="����Ƶ�ε������µ÷�")
	private BigDecimal adjCountScoreThree;
	/** δ��������ģռ�÷Ѽ��ȵ÷� */
		@EntityParaAnno(zhName="δ��������ģռ�÷Ѽ��ȵ÷�")
	private BigDecimal scaleAmountMonthCountScoreQuarter;
	/** δ��������ģռ�÷ѵ÷��·��� */
		@EntityParaAnno(zhName="δ��������ģռ�÷ѵ÷��·���")
	private BigDecimal scaleAmountMonthCount;
	/** ����Ƶ��δ�۷�ָ�����ȵ÷� */
		@EntityParaAnno(zhName="����Ƶ��δ�۷�ָ�����ȵ÷�")
	private BigDecimal adjCountMonthCountScoreQuarter;
	/** δ�۷ֵĵ���Ƶ���·��� */
		@EntityParaAnno(zhName="δ�۷ֵĵ���Ƶ���·���")
	private BigDecimal adjCountMonthCount;
	/** ��ȫ���Ŵ��ƻ��������������������鲢�����ɵĵ÷� */
		@EntityParaAnno(zhName="��ȫ���Ŵ��ƻ��������������������鲢�����ɵĵ÷�")
	private BigDecimal goodIdeaScore;
	/** �ڹؼ�ʱ����д����ʶ������������������Ŵ��ƻ����������������ӷ� */
		@EntityParaAnno(zhName="�ڹؼ�ʱ����д����ʶ������������������Ŵ��ƻ����������������ӷ�")
	private BigDecimal goodJobScore;
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
	/** ��ʷ�ӷ���÷� */
	@EntityParaAnno(zhName="��ʷ�ӷ���÷�")
	private BigDecimal addScoreQuarter;
	/** ��ʷ�ӷ����һ���µ÷� */
	@EntityParaAnno(zhName="��ʷ�ӷ����һ���µ÷�")
	private BigDecimal addScoreOne;
	/** ��ʷ�ӷ���ڶ����µ÷� */
	@EntityParaAnno(zhName="��ʷ�ӷ���ڶ����µ÷�")
	private BigDecimal addScoreTwo;
	/** ��ʷ�ӷ���������µ÷� */
	@EntityParaAnno(zhName="��ʷ�ӷ���������µ÷�")
	private BigDecimal addScoreThree;
	/** ��ʷ��� */
	@EntityParaAnno(zhName="��ʷ���")
	private BigDecimal historyNumber;



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

	public BigDecimal getRateScoreQuarter() {
		return rateScoreQuarter;
	}

	public void setRateScoreQuarter(BigDecimal rateScoreQuarter) {
		this.rateScoreQuarter = rateScoreQuarter;
	}

	public BigDecimal getRateRank() {
		return rateRank;
	}

	public void setRateRank(BigDecimal rateRank) {
		this.rateRank = rateRank;
	}

	public BigDecimal getRateRatio() {
		return rateRatio;
	}

	public void setRateRatio(BigDecimal rateRatio) {
		this.rateRatio = rateRatio;
	}

	public BigDecimal getRateLevel() {
		return rateLevel;
	}

	public void setRateLevel(BigDecimal rateLevel) {
		this.rateLevel = rateLevel;
	}

	public BigDecimal getBadConditionScoreQuarter() {
		return badConditionScoreQuarter;
	}

	public void setBadConditionScoreQuarter(BigDecimal badConditionScoreQuarter) {
		this.badConditionScoreQuarter = badConditionScoreQuarter;
	}

	public BigDecimal getBadConditionScoreOne() {
		return badConditionScoreOne;
	}

	public void setBadConditionScoreOne(BigDecimal badConditionScoreOne) {
		this.badConditionScoreOne = badConditionScoreOne;
	}

	public BigDecimal getBadConditionScoreTwo() {
		return badConditionScoreTwo;
	}

	public void setBadConditionScoreTwo(BigDecimal badConditionScoreTwo) {
		this.badConditionScoreTwo = badConditionScoreTwo;
	}

	public BigDecimal getBadConditionScoreThree() {
		return badConditionScoreThree;
	}

	public void setBadConditionScoreThree(BigDecimal badConditionScoreThree) {
		this.badConditionScoreThree = badConditionScoreThree;
	}

	public BigDecimal getDepositLoanRatioScoreQuarter() {
		return depositLoanRatioScoreQuarter;
	}

	public void setDepositLoanRatioScoreQuarter(BigDecimal depositLoanRatioScoreQuarter) {
		this.depositLoanRatioScoreQuarter = depositLoanRatioScoreQuarter;
	}

	public BigDecimal getDepositLoanRatioScoreOne() {
		return depositLoanRatioScoreOne;
	}

	public void setDepositLoanRatioScoreOne(BigDecimal depositLoanRatioScoreOne) {
		this.depositLoanRatioScoreOne = depositLoanRatioScoreOne;
	}

	public BigDecimal getDepositLoanRatioScoreTwo() {
		return depositLoanRatioScoreTwo;
	}

	public void setDepositLoanRatioScoreTwo(BigDecimal depositLoanRatioScoreTwo) {
		this.depositLoanRatioScoreTwo = depositLoanRatioScoreTwo;
	}

	public BigDecimal getDepositLoanRatioScoreThree() {
		return depositLoanRatioScoreThree;
	}

	public void setDepositLoanRatioScoreThree(BigDecimal depositLoanRatioScoreThree) {
		this.depositLoanRatioScoreThree = depositLoanRatioScoreThree;
	}

	public BigDecimal getNewLoanRatioScoreQuarter() {
		return newLoanRatioScoreQuarter;
	}

	public void setNewLoanRatioScoreQuarter(BigDecimal newLoanRatioScoreQuarter) {
		this.newLoanRatioScoreQuarter = newLoanRatioScoreQuarter;
	}

	public BigDecimal getNewLoanRatioScoreOne() {
		return newLoanRatioScoreOne;
	}

	public void setNewLoanRatioScoreOne(BigDecimal newLoanRatioScoreOne) {
		this.newLoanRatioScoreOne = newLoanRatioScoreOne;
	}

	public BigDecimal getNewLoanRatioScoreTwo() {
		return newLoanRatioScoreTwo;
	}

	public void setNewLoanRatioScoreTwo(BigDecimal newLoanRatioScoreTwo) {
		this.newLoanRatioScoreTwo = newLoanRatioScoreTwo;
	}

	public BigDecimal getNewLoanRatioScoreThree() {
		return newLoanRatioScoreThree;
	}

	public void setNewLoanRatioScoreThree(BigDecimal newLoanRatioScoreThree) {
		this.newLoanRatioScoreThree = newLoanRatioScoreThree;
	}

	public BigDecimal getLoanStructScoreQuarter() {
		return loanStructScoreQuarter;
	}

	public void setLoanStructScoreQuarter(BigDecimal loanStructScoreQuarter) {
		this.loanStructScoreQuarter = loanStructScoreQuarter;
	}

	public BigDecimal getLoanStructScoreOne() {
		return loanStructScoreOne;
	}

	public void setLoanStructScoreOne(BigDecimal loanStructScoreOne) {
		this.loanStructScoreOne = loanStructScoreOne;
	}

	public BigDecimal getLoanStructScoreTwo() {
		return loanStructScoreTwo;
	}

	public void setLoanStructScoreTwo(BigDecimal loanStructScoreTwo) {
		this.loanStructScoreTwo = loanStructScoreTwo;
	}

	public BigDecimal getLoanStructScoreThree() {
		return loanStructScoreThree;
	}

	public void setLoanStructScoreThree(BigDecimal loanStructScoreThree) {
		this.loanStructScoreThree = loanStructScoreThree;
	}

	public BigDecimal getScaleAmountScoreQuarter() {
		return scaleAmountScoreQuarter;
	}

	public void setScaleAmountScoreQuarter(BigDecimal scaleAmountScoreQuarter) {
		this.scaleAmountScoreQuarter = scaleAmountScoreQuarter;
	}

	public BigDecimal getScaleAmountScoreOne() {
		return scaleAmountScoreOne;
	}

	public void setScaleAmountScoreOne(BigDecimal scaleAmountScoreOne) {
		this.scaleAmountScoreOne = scaleAmountScoreOne;
	}

	public BigDecimal getScaleAmountScoreTwo() {
		return scaleAmountScoreTwo;
	}

	public void setScaleAmountScoreTwo(BigDecimal scaleAmountScoreTwo) {
		this.scaleAmountScoreTwo = scaleAmountScoreTwo;
	}

	public BigDecimal getScaleAmountScoreThree() {
		return scaleAmountScoreThree;
	}

	public void setScaleAmountScoreThree(BigDecimal scaleAmountScoreThree) {
		this.scaleAmountScoreThree = scaleAmountScoreThree;
	}

	public BigDecimal getPlanSubmitDeviationScoreQuarter() {
		return planSubmitDeviationScoreQuarter;
	}

	public void setPlanSubmitDeviationScoreQuarter(BigDecimal planSubmitDeviationScoreQuarter) {
		this.planSubmitDeviationScoreQuarter = planSubmitDeviationScoreQuarter;
	}

	public BigDecimal getPlanSubmitDeviationScoreOne() {
		return planSubmitDeviationScoreOne;
	}

	public void setPlanSubmitDeviationScoreOne(BigDecimal planSubmitDeviationScoreOne) {
		this.planSubmitDeviationScoreOne = planSubmitDeviationScoreOne;
	}

	public BigDecimal getPlanSubmitDeviationScoreTwo() {
		return planSubmitDeviationScoreTwo;
	}

	public void setPlanSubmitDeviationScoreTwo(BigDecimal planSubmitDeviationScoreTwo) {
		this.planSubmitDeviationScoreTwo = planSubmitDeviationScoreTwo;
	}

	public BigDecimal getPlanSubmitDeviationScoreThree() {
		return planSubmitDeviationScoreThree;
	}

	public void setPlanSubmitDeviationScoreThree(BigDecimal planSubmitDeviationScoreThree) {
		this.planSubmitDeviationScoreThree = planSubmitDeviationScoreThree;
	}

	public BigDecimal getPlanExecuteDeviationMonthMidScoreQuarter() {
		return planExecuteDeviationMonthMidScoreQuarter;
	}

	public void setPlanExecuteDeviationMonthMidScoreQuarter(BigDecimal planExecuteDeviationMonthMidScoreQuarter) {
		this.planExecuteDeviationMonthMidScoreQuarter = planExecuteDeviationMonthMidScoreQuarter;
	}

	public BigDecimal getPlanExecuteDeviationMonthMidScoreOne() {
		return planExecuteDeviationMonthMidScoreOne;
	}

	public void setPlanExecuteDeviationMonthMidScoreOne(BigDecimal planExecuteDeviationMonthMidScoreOne) {
		this.planExecuteDeviationMonthMidScoreOne = planExecuteDeviationMonthMidScoreOne;
	}

	public BigDecimal getPlanExecuteDeviationMonthMidScoreTwo() {
		return planExecuteDeviationMonthMidScoreTwo;
	}

	public void setPlanExecuteDeviationMonthMidScoreTwo(BigDecimal planExecuteDeviationMonthMidScoreTwo) {
		this.planExecuteDeviationMonthMidScoreTwo = planExecuteDeviationMonthMidScoreTwo;
	}

	public BigDecimal getPlanExecuteDeviationMonthMidScoreThree() {
		return planExecuteDeviationMonthMidScoreThree;
	}

	public void setPlanExecuteDeviationMonthMidScoreThree(BigDecimal planExecuteDeviationMonthMidScoreThree) {
		this.planExecuteDeviationMonthMidScoreThree = planExecuteDeviationMonthMidScoreThree;
	}

	public BigDecimal getPlanExecuteDeviationMonthInitScoreQuarter() {
		return planExecuteDeviationMonthInitScoreQuarter;
	}

	public void setPlanExecuteDeviationMonthInitScoreQuarter(BigDecimal planExecuteDeviationMonthInitScoreQuarter) {
		this.planExecuteDeviationMonthInitScoreQuarter = planExecuteDeviationMonthInitScoreQuarter;
	}

	public BigDecimal getPlanExecuteDeviationMonthInitScoreOne() {
		return planExecuteDeviationMonthInitScoreOne;
	}

	public void setPlanExecuteDeviationMonthInitScoreOne(BigDecimal planExecuteDeviationMonthInitScoreOne) {
		this.planExecuteDeviationMonthInitScoreOne = planExecuteDeviationMonthInitScoreOne;
	}

	public BigDecimal getPlanExecuteDeviationMonthInitScoreTwo() {
		return planExecuteDeviationMonthInitScoreTwo;
	}

	public void setPlanExecuteDeviationMonthInitScoreTwo(BigDecimal planExecuteDeviationMonthInitScoreTwo) {
		this.planExecuteDeviationMonthInitScoreTwo = planExecuteDeviationMonthInitScoreTwo;
	}

	public BigDecimal getPlanExecuteDeviationMonthInitScoreThree() {
		return planExecuteDeviationMonthInitScoreThree;
	}

	public void setPlanExecuteDeviationMonthInitScoreThree(BigDecimal planExecuteDeviationMonthInitScoreThree) {
		this.planExecuteDeviationMonthInitScoreThree = planExecuteDeviationMonthInitScoreThree;
	}

	public BigDecimal getAdjCountScoreQuarter() {
		return adjCountScoreQuarter;
	}

	public void setAdjCountScoreQuarter(BigDecimal adjCountScoreQuarter) {
		this.adjCountScoreQuarter = adjCountScoreQuarter;
	}

	public BigDecimal getAdjCountScoreOne() {
		return adjCountScoreOne;
	}

	public void setAdjCountScoreOne(BigDecimal adjCountScoreOne) {
		this.adjCountScoreOne = adjCountScoreOne;
	}

	public BigDecimal getAdjCountScoreTwo() {
		return adjCountScoreTwo;
	}

	public void setAdjCountScoreTwo(BigDecimal adjCountScoreTwo) {
		this.adjCountScoreTwo = adjCountScoreTwo;
	}

	public BigDecimal getAdjCountScoreThree() {
		return adjCountScoreThree;
	}

	public void setAdjCountScoreThree(BigDecimal adjCountScoreThree) {
		this.adjCountScoreThree = adjCountScoreThree;
	}

	public BigDecimal getScaleAmountMonthCountScoreQuarter() {
		return scaleAmountMonthCountScoreQuarter;
	}

	public void setScaleAmountMonthCountScoreQuarter(BigDecimal scaleAmountMonthCountScoreQuarter) {
		this.scaleAmountMonthCountScoreQuarter = scaleAmountMonthCountScoreQuarter;
	}

	public BigDecimal getScaleAmountMonthCount() {
		return scaleAmountMonthCount;
	}

	public void setScaleAmountMonthCount(BigDecimal scaleAmountMonthCount) {
		this.scaleAmountMonthCount = scaleAmountMonthCount;
	}

	public BigDecimal getAdjCountMonthCountScoreQuarter() {
		return adjCountMonthCountScoreQuarter;
	}

	public void setAdjCountMonthCountScoreQuarter(BigDecimal adjCountMonthCountScoreQuarter) {
		this.adjCountMonthCountScoreQuarter = adjCountMonthCountScoreQuarter;
	}

	public BigDecimal getAdjCountMonthCount() {
		return adjCountMonthCount;
	}

	public void setAdjCountMonthCount(BigDecimal adjCountMonthCount) {
		this.adjCountMonthCount = adjCountMonthCount;
	}

	public BigDecimal getGoodIdeaScore() {
		return goodIdeaScore;
	}

	public void setGoodIdeaScore(BigDecimal goodIdeaScore) {
		this.goodIdeaScore = goodIdeaScore;
	}

	public BigDecimal getGoodJobScore() {
		return goodJobScore;
	}

	public void setGoodJobScore(BigDecimal goodJobScore) {
		this.goodJobScore = goodJobScore;
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

	public BigDecimal getAddScoreQuarter() {
		return addScoreQuarter;
	}

	public void setAddScoreQuarter(BigDecimal addScoreQuarter) {
		this.addScoreQuarter = addScoreQuarter;
	}

	public BigDecimal getAddScoreOne() {
		return addScoreOne;
	}

	public void setAddScoreOne(BigDecimal addScoreOne) {
		this.addScoreOne = addScoreOne;
	}

	public BigDecimal getAddScoreTwo() {
		return addScoreTwo;
	}

	public void setAddScoreTwo(BigDecimal addScoreTwo) {
		this.addScoreTwo = addScoreTwo;
	}

	public BigDecimal getAddScoreThree() {
		return addScoreThree;
	}

	public void setAddScoreThree(BigDecimal addScoreThree) {
		this.addScoreThree = addScoreThree;
	}

	public BigDecimal getHistoryNumber() {
		return historyNumber;
	}

	public void setHistoryNumber(BigDecimal historyNumber) {
		this.historyNumber = historyNumber;
	}
}