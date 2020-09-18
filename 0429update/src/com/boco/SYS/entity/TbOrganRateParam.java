package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 机构评分参数表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbOrganRateParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 参数ID */
		@EntityParaAnno(zhName="参数ID")
	private String id;
	/** 得分项类型 bad_condition  不良情况 deposit_loan_ratio  自营新增存贷比 new_deposit_ratio 新发生贷款利率 deposit_struct 贷款结构 scale 超规模占用费和规模闲置费 plan_submit_deviation  计划报送偏离度 plan_execute_deviation_month_mid 计划执行偏离度-月末月中偏离度 plan_execute_deviation_month_init 计划执行偏离度-月末月初偏离度 adj_count 调整频次 scale_and_adj_count 未产生超规模占用费和规模闲置费和调整频次 */
		@EntityParaAnno(zhName="得分项类型 bad_condition  不良情况 deposit_loan_ratio  自营新增存贷比 new_deposit_ratio 新发生贷款利率 deposit_struct 贷款结构 scale 超规模占用费和规模闲置费 plan_submit_deviation  计划报送偏离度 plan_execute_deviation_month_mid 计划执行偏离度-月末月中偏离度 plan_execute_deviation_month_init 计划执行偏离度-月末月初偏离度 adj_count 调整频次 scale_and_adj_count 未产生超规模占用费和规模闲置费和调整频次")
	private String elementType;
	/** 目标分值 */
		@EntityParaAnno(zhName="目标分值")
	private BigDecimal targetScore;
	/** 最小得分值 */
		@EntityParaAnno(zhName="最小得分值")
	private BigDecimal minTargetScore;
	/** 最大得分值 */
		@EntityParaAnno(zhName="最大得分值")
	private BigDecimal maxTargetScore;
	/** 区间下限 */
		@EntityParaAnno(zhName="区间下限")
	private BigDecimal low;
	/** 区间上限 */
		@EntityParaAnno(zhName="区间上限")
	private BigDecimal high;
	/** 根据区间直接得分 */
		@EntityParaAnno(zhName="根据区间直接得分")
	private BigDecimal directScore;
	/** 调整频次次数 */
		@EntityParaAnno(zhName="调整频次次数")
	private BigDecimal adjCount;
	/** 每low_high_var加varscore分 */
		@EntityParaAnno(zhName="每low_high_var加varscore分")
	private BigDecimal lowHighVar;
	/** 每low_high_var加varscore分 */
		@EntityParaAnno(zhName="每low_high_var加varscore分")
	private BigDecimal varScore;
	/** 调整频次3次未扣分得分 */
		@EntityParaAnno(zhName="调整频次3次未扣分得分")
	private BigDecimal adjCountThree;
	/** 调整频次2次未扣分得分 */
		@EntityParaAnno(zhName="调整频次2次未扣分得分")
	private BigDecimal adjCountTwo;
	/** 超规模占用费和规模闲置费3次未扣分得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费3次未扣分得分")
	private BigDecimal scaleThree;
	/** 超规模占用费和规模闲置费2次未扣分得分 */
		@EntityParaAnno(zhName="超规模占用费和规模闲置费2次未扣分得分")
	private BigDecimal scaleTwo;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String createTime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String updateTime;
	/** 更新人员 */
		@EntityParaAnno(zhName="更新人员")
	private String updateOper;
	/** 得分类型名称 */
	@EntityParaAnno(zhName="得分类型名称")
	private String elementTypeName;
	/** 排序名称 */
	@EntityParaAnno(zhName="排序名称")
	private Integer orderRate;
	/** 季度评分第一个月权重 */
	@EntityParaAnno(zhName="季度评分第一个月权重")
	private BigDecimal scoreOne;
	/** 季度评分第二个月权重 */
	@EntityParaAnno(zhName="季度评分第二个月权重")
	private BigDecimal scoreTwo;
	/** 季度评分第三个月权重 */
	@EntityParaAnno(zhName="季度评分第三个月权重")
	private BigDecimal scoreThree;
	
	/** setter\getter方法 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public BigDecimal getTargetScore() {
		return targetScore;
	}

	public void setTargetScore(BigDecimal targetScore) {
		this.targetScore = targetScore;
	}

	public BigDecimal getMinTargetScore() {
		return minTargetScore;
	}

	public void setMinTargetScore(BigDecimal minTargetScore) {
		this.minTargetScore = minTargetScore;
	}

	public BigDecimal getMaxTargetScore() {
		return maxTargetScore;
	}

	public void setMaxTargetScore(BigDecimal maxTargetScore) {
		this.maxTargetScore = maxTargetScore;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getDirectScore() {
		return directScore;
	}

	public void setDirectScore(BigDecimal directScore) {
		this.directScore = directScore;
	}

	public BigDecimal getAdjCount() {
		return adjCount;
	}

	public void setAdjCount(BigDecimal adjCount) {
		this.adjCount = adjCount;
	}

	public BigDecimal getLowHighVar() {
		return lowHighVar;
	}

	public void setLowHighVar(BigDecimal lowHighVar) {
		this.lowHighVar = lowHighVar;
	}

	public BigDecimal getVarScore() {
		return varScore;
	}

	public void setVarScore(BigDecimal varScore) {
		this.varScore = varScore;
	}

	public BigDecimal getAdjCountThree() {
		return adjCountThree;
	}

	public void setAdjCountThree(BigDecimal adjCountThree) {
		this.adjCountThree = adjCountThree;
	}

	public BigDecimal getAdjCountTwo() {
		return adjCountTwo;
	}

	public void setAdjCountTwo(BigDecimal adjCountTwo) {
		this.adjCountTwo = adjCountTwo;
	}

	public BigDecimal getScaleThree() {
		return scaleThree;
	}

	public void setScaleThree(BigDecimal scaleThree) {
		this.scaleThree = scaleThree;
	}

	public BigDecimal getScaleTwo() {
		return scaleTwo;
	}

	public void setScaleTwo(BigDecimal scaleTwo) {
		this.scaleTwo = scaleTwo;
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

	public String getElementTypeName() {
		return elementTypeName;
	}

	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}

	public Integer getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(Integer orderRate) {
		this.orderRate = orderRate;
	}

	public BigDecimal getScoreOne() {
		return scoreOne;
	}

	public void setScoreOne(BigDecimal scoreOne) {
		this.scoreOne = scoreOne;
	}

	public BigDecimal getScoreTwo() {
		return scoreTwo;
	}

	public void setScoreTwo(BigDecimal scoreTwo) {
		this.scoreTwo = scoreTwo;
	}

	public BigDecimal getScoreThree() {
		return scoreThree;
	}

	public void setScoreThree(BigDecimal scoreThree) {
		this.scoreThree = scoreThree;
	}
}