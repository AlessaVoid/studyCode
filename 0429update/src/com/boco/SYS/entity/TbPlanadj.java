package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 信贷计划调整批次表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbPlanadj extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id（批次号） */
		@EntityParaAnno(zhName="id（批次号）")
	private String planadjId;
	/** 所属月份 */
		@EntityParaAnno(zhName="所属月份")
	private String planadjMonth;
	/** 本月计划净增量 */
		@EntityParaAnno(zhName="本月计划净增量")
	private BigDecimal planadjNetIncrement;
	/** 调整金额 */
		@EntityParaAnno(zhName="调整金额")
	private BigDecimal planadjAdjAmount;
	/** 单位 */
		@EntityParaAnno(zhName="单位")
	private Integer planadjUnit;
	/** 审批状态 */
		@EntityParaAnno(zhName="审批状态")
	private Integer planadjStatus;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String planadjCreateTime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String planadjUpdateTime;
	/** 填报柜员号 */
		@EntityParaAnno(zhName="填报柜员号")
	private String planadjCreateOpercode;
	/** 计划制定机构 */
		@EntityParaAnno(zhName="计划制定机构")
	private String planadjOrgan;
	/** 本月调整净增量 */
	@EntityParaAnno(zhName="本月调整净增量")
	private BigDecimal planadjRealIncrement;

	/** 计划调整类型 1-计划 2-条线 */
	@EntityParaAnno(zhName="计划调整类型 1-计划 2-条线")
	private Integer planadjType;

	/** 是否统一规划调整（针对机构）  1-是 2-否 */
	@EntityParaAnno(zhName="是否统一规划调整（针对机构）  1-是 2-否")
	private Integer planadjUnifiedType;

	/** 机构计划贷种级别 */
	@EntityParaAnno(zhName="机构计划贷种级别")
	private Integer combLevel;
	
	/** setter\getter方法 */
	/** id（批次号） */
	public void setPlanadjId(String planadjId) {
		this.planadjId = planadjId == null ? null : planadjId.trim();
	}
	public String getPlanadjId() {
		return this.planadjId;
	}
	/** 所属月份 */
	public void setPlanadjMonth(String planadjMonth) {
		this.planadjMonth = planadjMonth == null ? null : planadjMonth.trim();
	}
	public String getPlanadjMonth() {
		return this.planadjMonth;
	}

	public BigDecimal getPlanadjNetIncrement() {
		return planadjNetIncrement;
	}

	public void setPlanadjNetIncrement(BigDecimal planadjNetIncrement) {
		this.planadjNetIncrement = planadjNetIncrement;
	}

	public BigDecimal getPlanadjAdjAmount() {
		return planadjAdjAmount;
	}

	public void setPlanadjAdjAmount(BigDecimal planadjAdjAmount) {
		this.planadjAdjAmount = planadjAdjAmount;
	}

	/** 单位 */
	public void setPlanadjUnit(Integer planadjUnit) {
		this.planadjUnit = planadjUnit;
	}
	public Integer getPlanadjUnit() {
		return this.planadjUnit;
	}
	/** 审批状态 */
	public void setPlanadjStatus(Integer planadjStatus) {
		this.planadjStatus = planadjStatus;
	}
	public Integer getPlanadjStatus() {
		return this.planadjStatus;
	}
	/** 创建时间 */
	public void setPlanadjCreateTime(String planadjCreateTime) {
		this.planadjCreateTime = planadjCreateTime == null ? null : planadjCreateTime.trim();
	}
	public String getPlanadjCreateTime() {
		return this.planadjCreateTime;
	}
	/** 更新时间 */
	public void setPlanadjUpdateTime(String planadjUpdateTime) {
		this.planadjUpdateTime = planadjUpdateTime == null ? null : planadjUpdateTime.trim();
	}
	public String getPlanadjUpdateTime() {
		return this.planadjUpdateTime;
	}
	/** 填报机构id */
	public void setPlanadjCreateOpercode(String planadjCreateOpercode) {
		this.planadjCreateOpercode = planadjCreateOpercode == null ? null : planadjCreateOpercode.trim();
	}
	public String getPlanadjCreateOpercode() {
		return this.planadjCreateOpercode;
	}
	/** planadjOrgan */
	public void setPlanadjOrgan(String planadjOrgan) {
		this.planadjOrgan = planadjOrgan == null ? null : planadjOrgan.trim();
	}
	public String getPlanadjOrgan() {
		return this.planadjOrgan;
	}

	public BigDecimal getPlanadjRealIncrement() {
		return planadjRealIncrement;
	}

	public void setPlanadjRealIncrement(BigDecimal planadjRealIncrement) {
		this.planadjRealIncrement = planadjRealIncrement;
	}

	public Integer getPlanadjType() {
		return planadjType;
	}

	public void setPlanadjType(Integer planadjType) {
		this.planadjType = planadjType;
	}

	public Integer getPlanadjUnifiedType() {
		return planadjUnifiedType;
	}

	public void setPlanadjUnifiedType(Integer planadjUnifiedType) {
		this.planadjUnifiedType = planadjUnifiedType;
	}

	public Integer getCombLevel() {
		return combLevel;
	}

	public void setCombLevel(Integer combLevel) {
		this.combLevel = combLevel;
	}
}