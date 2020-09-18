package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 *
 *
 * 信贷计划调整批次详情表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbPlanadjDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
	@EntityParaAnno(zhName="id")
	private java.lang.String planadjdId;
	/** 批次号 */
	@EntityParaAnno(zhName="批次号")
	private java.lang.String planadjdRefId;
	/** 机构号 */
	@EntityParaAnno(zhName="机构号")
	private java.lang.String planadjdOrgan;
	/** 贷种类型 */
	@EntityParaAnno(zhName="贷种类型")
	private java.lang.String planadjdLoanType;
	/** 初始金额 */
	@EntityParaAnno(zhName="初始金额")
	private BigDecimal planadjdInitAmount;
	/** 调整金额 */
	@EntityParaAnno(zhName="调整金额")
	private BigDecimal planadjdAdjAmount;
	/** 调整后金额 */
	@EntityParaAnno(zhName="调整后金额")
	private BigDecimal planadjdAdjedAmount;
	/** 单位 */
	@EntityParaAnno(zhName="单位")
	private java.lang.Integer planadjdUnit;
	/** 创建时间 */
	@EntityParaAnno(zhName="创建时间")
	private java.lang.String planadjdCreateTime;
	/** 更新时间 */
	@EntityParaAnno(zhName="更新时间")
	private java.lang.String planadjdUpdateTime;
	/** 所属月份 */
	@EntityParaAnno(zhName="所属月份")
	private java.lang.String planadjdMonth;
	/** 需求金额 */
	@EntityParaAnno(zhName="需求金额")
	private BigDecimal planadjdReqAmount;

	private String __status;

	/** setter\getter方法 */
	/** id */
	public void setPlanadjdId(java.lang.String planadjdId) {
		this.planadjdId = planadjdId == null ? null : planadjdId.trim();
	}
	public java.lang.String getPlanadjdId() {
		return this.planadjdId;
	}
	/** 批次号 */
	public void setPlanadjdRefId(java.lang.String planadjdRefId) {
		this.planadjdRefId = planadjdRefId == null ? null : planadjdRefId.trim();
	}
	public java.lang.String getPlanadjdRefId() {
		return this.planadjdRefId;
	}
	/** 机构号 */
	public void setPlanadjdOrgan(java.lang.String planadjdOrgan) {
		this.planadjdOrgan = planadjdOrgan == null ? null : planadjdOrgan.trim();
	}
	public java.lang.String getPlanadjdOrgan() {
		return this.planadjdOrgan;
	}
	/** 贷种类型 */
	public void setPlanadjdLoanType(java.lang.String planadjdLoanType) {
		this.planadjdLoanType = planadjdLoanType == null ? null : planadjdLoanType.trim();
	}
	public java.lang.String getPlanadjdLoanType() {
		return this.planadjdLoanType;
	}

	/** 单位 */
	public void setPlanadjdUnit(java.lang.Integer planadjdUnit) {
		this.planadjdUnit = planadjdUnit;
	}
	public java.lang.Integer getPlanadjdUnit() {
		return this.planadjdUnit;
	}
	/** 创建时间 */
	public void setPlanadjdCreateTime(java.lang.String planadjdCreateTime) {
		this.planadjdCreateTime = planadjdCreateTime == null ? null : planadjdCreateTime.trim();
	}
	public java.lang.String getPlanadjdCreateTime() {
		return this.planadjdCreateTime;
	}
	/** 更新时间 */
	public void setPlanadjdUpdateTime(java.lang.String planadjdUpdateTime) {
		this.planadjdUpdateTime = planadjdUpdateTime == null ? null : planadjdUpdateTime.trim();
	}
	public java.lang.String getPlanadjdUpdateTime() {
		return this.planadjdUpdateTime;
	}
	/** 所属月份 */
	public void setPlanadjdMonth(java.lang.String planadjdMonth) {
		this.planadjdMonth = planadjdMonth == null ? null : planadjdMonth.trim();
	}
	public java.lang.String getPlanadjdMonth() {
		return this.planadjdMonth;
	}

	public BigDecimal getPlanadjdInitAmount() {
		return planadjdInitAmount;
	}

	public void setPlanadjdInitAmount(BigDecimal planadjdInitAmount) {
		this.planadjdInitAmount = planadjdInitAmount;
	}

	public BigDecimal getPlanadjdAdjAmount() {
		return planadjdAdjAmount;
	}

	public void setPlanadjdAdjAmount(BigDecimal planadjdAdjAmount) {
		this.planadjdAdjAmount = planadjdAdjAmount;
	}

	public BigDecimal getPlanadjdAdjedAmount() {
		return planadjdAdjedAmount;
	}

	public void setPlanadjdAdjedAmount(BigDecimal planadjdAdjedAmount) {
		this.planadjdAdjedAmount = planadjdAdjedAmount;
	}

	public BigDecimal getPlanadjdReqAmount() {
		return planadjdReqAmount;
	}

	public void setPlanadjdReqAmount(BigDecimal planadjdReqAmount) {
		this.planadjdReqAmount = planadjdReqAmount;
	}

	public String get__status() {
		return __status;
	}

	public void set__status(String __status) {
		this.__status = __status;
	}
}