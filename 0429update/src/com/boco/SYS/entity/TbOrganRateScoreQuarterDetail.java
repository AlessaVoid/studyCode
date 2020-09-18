package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 机构评分季度详情表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbOrganRateScoreQuarterDetail extends BaseEntity implements java.io.Serializable {
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
	/** 季度得分 */
		@EntityParaAnno(zhName="季度得分")
	private BigDecimal rateScoreQuarter;
	/** 机构季度得分全行排名 */
		@EntityParaAnno(zhName="机构季度得分全行排名")
	private BigDecimal rateRank;
	/** 机构所占全行比例 */
		@EntityParaAnno(zhName="机构所占全行比例")
	private BigDecimal rateRatio;
	/** 季度评级（1-A，2-B，3-C，4-D） */
		@EntityParaAnno(zhName="季度评级（1-A，2-B，3-C，4-D）")
	private BigDecimal rateLevel;
	/** 不良情况季度得分 */
		@EntityParaAnno(zhName="不良情况季度得分")
	private BigDecimal badConditionScoreQuarter;
	/** 不良情况第一个月得分 */
		@EntityParaAnno(zhName="不良情况第一个月得分")
	private BigDecimal badConditionScoreOne;
	/** 不良情况第二个月得分 */
		@EntityParaAnno(zhName="不良情况第二个月得分")
	private BigDecimal badConditionScoreTwo;
	/** 不良情况第三个月得分 */
		@EntityParaAnno(zhName="不良情况第三个月得分")
	private BigDecimal badConditionScoreThree;
	/** 自营新增存贷比季度得分 */
		@EntityParaAnno(zhName="自营新增存贷比季度得分")
	private BigDecimal depositLoanRatioScoreQuarter;
	/** 自营新增存贷比第一个月得分 */
		@EntityParaAnno(zhName="自营新增存贷比第一个月得分")
	private BigDecimal depositLoanRatioScoreOne;
	/** 自营新增存贷比第二个月得分 */
		@EntityParaAnno(zhName="自营新增存贷比第二个月得分")
	private BigDecimal depositLoanRatioScoreTwo;
	/** 自营新增存贷比第三个月得分 */
		@EntityParaAnno(zhName="自营新增存贷比第三个月得分")
	private BigDecimal depositLoanRatioScoreThree;
	/** 新发生贷款利率季度得分 */
		@EntityParaAnno(zhName="新发生贷款利率季度得分")
	private BigDecimal newLoanRatioScoreQuarter;
	/** 新发生贷款利率第一个月得分 */
		@EntityParaAnno(zhName="新发生贷款利率第一个月得分")
	private BigDecimal newLoanRatioScoreOne;
	/** 新发生贷款利率第二个月得分 */
		@EntityParaAnno(zhName="新发生贷款利率第二个月得分")
	private BigDecimal newLoanRatioScoreTwo;
	/** 新发生贷款利率第三个月得分 */
		@EntityParaAnno(zhName="新发生贷款利率第三个月得分")
	private BigDecimal newLoanRatioScoreThree;
	/** 贷款结构季度得分 */
		@EntityParaAnno(zhName="贷款结构季度得分")
	private BigDecimal loanStructScoreQuarter;
	/** 贷款结构第一个月得分 */
		@EntityParaAnno(zhName="贷款结构第一个月得分")
	private BigDecimal loanStructScoreOne;
	/** 贷款结构第二个月得分 */
		@EntityParaAnno(zhName="贷款结构第二个月得分")
	private BigDecimal loanStructScoreTwo;
	/** 贷款结构第三个月得分 */
		@EntityParaAnno(zhName="贷款结构第三个月得分")
	private BigDecimal loanStructScoreThree;
	/** 超规模占用费和规模闲置费季度得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费季度得分")
	private BigDecimal scaleAmountScoreQuarter;
	/** 超规模占用费和规模闲置费第一个月得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费第一个月得分")
	private BigDecimal scaleAmountScoreOne;
	/** 超规模占用费和规模闲置费第二个月得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费第二个月得分")
	private BigDecimal scaleAmountScoreTwo;
	/** 超规模占用费和规模闲置费第三个月得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费第三个月得分")
	private BigDecimal scaleAmountScoreThree;
	/** 计划报送偏离度季度得分 */
		@EntityParaAnno(zhName="计划报送偏离度季度得分")
	private BigDecimal planSubmitDeviationScoreQuarter;
	/** 计划报送偏离度第一个月得分 */
		@EntityParaAnno(zhName="计划报送偏离度第一个月得分")
	private BigDecimal planSubmitDeviationScoreOne;
	/** 计划报送偏离度第二个月得分 */
		@EntityParaAnno(zhName="计划报送偏离度第二个月得分")
	private BigDecimal planSubmitDeviationScoreTwo;
	/** 计划报送偏离度第三个月得分 */
		@EntityParaAnno(zhName="计划报送偏离度第三个月得分")
	private BigDecimal planSubmitDeviationScoreThree;
	/** 计划执行偏离度月末月中偏离度季度得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月中偏离度季度得分")
	private BigDecimal planExecuteDeviationMonthMidScoreQuarter;
	/** 计划执行偏离度月末月中偏离度第一个月得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月中偏离度第一个月得分")
	private BigDecimal planExecuteDeviationMonthMidScoreOne;
	/** 计划执行偏离度月末月中偏离度第二个月得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月中偏离度第二个月得分")
	private BigDecimal planExecuteDeviationMonthMidScoreTwo;
	/** 计划执行偏离度月末月中偏离度第三个月得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月中偏离度第三个月得分")
	private BigDecimal planExecuteDeviationMonthMidScoreThree;
	/** 计划执行偏离度月末月初偏离度季度得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月初偏离度季度得分")
	private BigDecimal planExecuteDeviationMonthInitScoreQuarter;
	/** 计划执行偏离度月末月初偏离度第一个月得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月初偏离度第一个月得分")
	private BigDecimal planExecuteDeviationMonthInitScoreOne;
	/** 计划执行偏离度月末月初偏离度第二个月得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月初偏离度第二个月得分")
	private BigDecimal planExecuteDeviationMonthInitScoreTwo;
	/** 计划执行偏离度月末月初偏离度第三个月得分 */
		@EntityParaAnno(zhName="计划执行偏离度月末月初偏离度第三个月得分")
	private BigDecimal planExecuteDeviationMonthInitScoreThree;
	/** 调整频次季度得分 */
		@EntityParaAnno(zhName="调整频次季度得分")
	private BigDecimal adjCountScoreQuarter;
	/** 调整频次第一个月得分 */
		@EntityParaAnno(zhName="调整频次第一个月得分")
	private BigDecimal adjCountScoreOne;
	/** 调整频次第二个月得分 */
		@EntityParaAnno(zhName="调整频次第二个月得分")
	private BigDecimal adjCountScoreTwo;
	/** 调整频次第三个月得分 */
		@EntityParaAnno(zhName="调整频次第三个月得分")
	private BigDecimal adjCountScoreThree;
	/** 未产生超规模占用费季度得分 */
		@EntityParaAnno(zhName="未产生超规模占用费季度得分")
	private BigDecimal scaleAmountMonthCountScoreQuarter;
	/** 未产生超规模占用费得分月份数 */
		@EntityParaAnno(zhName="未产生超规模占用费得分月份数")
	private BigDecimal scaleAmountMonthCount;
	/** 调整频次未扣分指数季度得分 */
		@EntityParaAnno(zhName="调整频次未扣分指数季度得分")
	private BigDecimal adjCountMonthCountScoreQuarter;
	/** 未扣分的调整频次月份数 */
		@EntityParaAnno(zhName="未扣分的调整频次月份数")
	private BigDecimal adjCountMonthCount;
	/** 对全行信贷计划管理提出建设性意见或建议并被采纳的得分 */
		@EntityParaAnno(zhName="对全行信贷计划管理提出建设性意见或建议并被采纳的得分")
	private BigDecimal goodIdeaScore;
	/** 在关键时点具有大局意识，积极配合总行做好信贷计划管理工作，酌情给予加分 */
		@EntityParaAnno(zhName="在关键时点具有大局意识，积极配合总行做好信贷计划管理工作，酌情给予加分")
	private BigDecimal goodJobScore;
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
	/** 历史加分项得分 */
	@EntityParaAnno(zhName="历史加分项得分")
	private BigDecimal addScoreQuarter;
	/** 历史加分项第一个月得分 */
	@EntityParaAnno(zhName="历史加分项第一个月得分")
	private BigDecimal addScoreOne;
	/** 历史加分项第二个月得分 */
	@EntityParaAnno(zhName="历史加分项第二个月得分")
	private BigDecimal addScoreTwo;
	/** 历史加分项第三个月得分 */
	@EntityParaAnno(zhName="历史加分项第三个月得分")
	private BigDecimal addScoreThree;



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
}