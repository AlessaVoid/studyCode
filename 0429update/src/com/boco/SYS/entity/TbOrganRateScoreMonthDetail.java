package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 机构评分月度详情表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbOrganRateScoreMonthDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/**  机构评分批次表-tb_organ_rate_score   id */
		@EntityParaAnno(zhName=" 机构评分批次表-tb_organ_rate_score   id")
	private String refId;
	/** 月份 */
		@EntityParaAnno(zhName="月份")
	private String rateMonth;
	/** 机构 */
		@EntityParaAnno(zhName="机构")
	private String rateOrgan;
	/** 月度总分 */
		@EntityParaAnno(zhName="月度总分")
	private BigDecimal rateScoreMonth;
	/** 不良情况得分 */
		@EntityParaAnno(zhName="不良情况得分")
	private BigDecimal badConditionScore;
	/** 月末不良率 */
		@EntityParaAnno(zhName="月末不良率")
	private BigDecimal badConditionRatioMonthEnd;
	/** 年初不良率 */
		@EntityParaAnno(zhName="年初不良率")
	private BigDecimal badConditionRatioYearBegin;
	/** 不良率变化情况 */
		@EntityParaAnno(zhName="不良率变化情况")
	private BigDecimal badConditionRatioChange;
	/** 自营新增存贷比得分 */
		@EntityParaAnno(zhName="自营新增存贷比得分")
	private BigDecimal depositLoanRatioScore;
	/** 各项贷款年增量 */
		@EntityParaAnno(zhName="各项贷款年增量")
	private BigDecimal loanYearAdd;
	/** 个人自营存款年增量 */
		@EntityParaAnno(zhName="个人自营存款年增量")
	private BigDecimal personalDepositYearIncrement;
	/** 公司存款年增量 */
		@EntityParaAnno(zhName="公司存款年增量")
	private BigDecimal companyDepositYearIncrement;
	/** 自营新增存贷比 */
		@EntityParaAnno(zhName="自营新增存贷比")
	private BigDecimal depositLoanRatioChange;
	/** 新发生贷款利率得分 */
		@EntityParaAnno(zhName="新发生贷款利率得分")
	private BigDecimal newLoanRatioScore;
	/** 新发生贷款利率 */
		@EntityParaAnno(zhName="新发生贷款利率")
	private BigDecimal newLoanRatio;
	/** 全行平均贷款利率 */
		@EntityParaAnno(zhName="全行平均贷款利率")
	private BigDecimal bankAverageLoanRatio;
	/** 贷款利率变化情况 */
		@EntityParaAnno(zhName="贷款利率变化情况")
	private BigDecimal newLoanRatioChange;
	/** 贷款结构得分 */
		@EntityParaAnno(zhName="贷款结构得分")
	private BigDecimal loanStructScore;
	/** 总贷款 */
		@EntityParaAnno(zhName="总贷款")
	private BigDecimal loanCount;
	/** 月初实体贷款余额 */
		@EntityParaAnno(zhName="月初实体贷款余额")
	private BigDecimal monthBeginEntityLoan;
	/** 月末实体贷款余额 */
		@EntityParaAnno(zhName="月末实体贷款余额")
	private BigDecimal monthEndEntityLoan;
	/** 月初实体贷款余额占比 */
		@EntityParaAnno(zhName="月初实体贷款余额占比")
	private BigDecimal monthBeginEntityLoanChange;
	/** 月末实体贷款余额占比 */
		@EntityParaAnno(zhName="月末实体贷款余额占比")
	private BigDecimal monthEndEntityLoanChange;
	/** 月末实体贷款余额占比变化 */
		@EntityParaAnno(zhName="月末实体贷款余额占比变化")
	private BigDecimal monthEndBeginEntityLoanChange;
	/** 超规模占用费和规模闲置费得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费得分")
	private BigDecimal scaleAmountScore;
	/** 分行罚息金额 */
		@EntityParaAnno(zhName="分行罚息金额")
	private BigDecimal organScaleAmount;
	/** 全行罚息金额 */
		@EntityParaAnno(zhName="全行罚息金额")
	private BigDecimal bankScaleAmount;
	/** 罚息金额占比 */
		@EntityParaAnno(zhName="罚息金额占比")
	private BigDecimal scaleAmountChange;
	/** 计划报送偏离度得分 */
		@EntityParaAnno(zhName="计划报送偏离度得分")
	private BigDecimal planSubmitDeviationScore;
	/** 月末实际月增量 */
		@EntityParaAnno(zhName="月末实际月增量")
	private BigDecimal monthEndIncrement;
	/** 月初报送需求 */
		@EntityParaAnno(zhName="月初报送需求")
	private BigDecimal monthBeginReport;
	/** 偏离幅度 */
		@EntityParaAnno(zhName="偏离幅度")
	private BigDecimal planSubmitDeviationChange;
	/** 计划执行偏离度-月末月中偏离度得分 */
		@EntityParaAnno(zhName="计划执行偏离度-月末月中偏离度得分")
	private BigDecimal planExecuteDeviationMonthMidScore;
	/** 月中统一动态调整后计划 */
		@EntityParaAnno(zhName="月中统一动态调整后计划")
	private BigDecimal monthMidPlan;
	/** 月末统一动态调整后计划 */
		@EntityParaAnno(zhName="月末统一动态调整后计划")
	private BigDecimal monthEndPlan;
	/** 月末月中偏离幅度 */
		@EntityParaAnno(zhName="月末月中偏离幅度")
	private BigDecimal planExecuteDeviationMonthMidChange;
	/** 计划执行偏离度-月末月初偏离度得分 */
		@EntityParaAnno(zhName="计划执行偏离度-月末月初偏离度得分")
	private BigDecimal planExecuteDeviationMonthInitScore;
	/** 月初总行下达计划 */
		@EntityParaAnno(zhName="月初总行下达计划")
	private BigDecimal monthBeginPlan;
	/** 月末月初偏离幅度 */
		@EntityParaAnno(zhName="月末月初偏离幅度")
	private BigDecimal planExecuteDeviationMonthInitChange;
	/** 调整频次得分 */
		@EntityParaAnno(zhName="调整频次得分")
	private BigDecimal adjCountScore;
	/** 调整频次次数 */
		@EntityParaAnno(zhName="调整频次次数")
	private BigDecimal adjCount;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String createTime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String updateTime;
	/** 修改人员 */
		@EntityParaAnno(zhName="修改人员")
	private String updateOper;
	/** 加分项 */
	@EntityParaAnno(zhName="加分项")
	private BigDecimal addScore;
	/** 备注 */
	@EntityParaAnno(zhName="备注")
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