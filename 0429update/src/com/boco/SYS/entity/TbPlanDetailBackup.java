package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 计划制定明细表备份表（第一次制定计划）实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbPlanDetailBackup extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** pdId */
		@EntityParaAnno(zhName="pdId")
	private String pdId;
	/** 批次号 */
		@EntityParaAnno(zhName="批次号")
	private String pdRefId;
	/** 机构号 */
		@EntityParaAnno(zhName="机构号")
	private String pdOrgan;
	/** 所属月份 */
		@EntityParaAnno(zhName="所属月份")
	private String pdMonth;
	/** 贷种组合类型 */
		@EntityParaAnno(zhName="贷种组合类型")
	private String pdLoanType;
	/** 金额 */
		@EntityParaAnno(zhName="金额")
	private BigDecimal pdAmount;
	/** 单位 */
		@EntityParaAnno(zhName="单位")
	private Integer pdUnit;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String pdCreateTime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String pdUpdateTime;
	
	/** setter\getter方法 */
	/** pdId */
	public void setPdId(String pdId) {
		this.pdId = pdId == null ? null : pdId.trim();
	}
	public String getPdId() {
		return this.pdId;
	}
	/** 批次号 */
	public void setPdRefId(String pdRefId) {
		this.pdRefId = pdRefId == null ? null : pdRefId.trim();
	}
	public String getPdRefId() {
		return this.pdRefId;
	}
	/** 机构号 */
	public void setPdOrgan(String pdOrgan) {
		this.pdOrgan = pdOrgan == null ? null : pdOrgan.trim();
	}
	public String getPdOrgan() {
		return this.pdOrgan;
	}
	/** 所属月份 */
	public void setPdMonth(String pdMonth) {
		this.pdMonth = pdMonth == null ? null : pdMonth.trim();
	}
	public String getPdMonth() {
		return this.pdMonth;
	}
	/** 贷种组合类型 */
	public void setPdLoanType(String pdLoanType) {
		this.pdLoanType = pdLoanType == null ? null : pdLoanType.trim();
	}
	public String getPdLoanType() {
		return this.pdLoanType;
	}
	/** 金额 */
	public BigDecimal getPdAmount() {
		return pdAmount;
	}

	public void setPdAmount(BigDecimal pdAmount) {
		this.pdAmount = pdAmount;
	}

	/** 单位 */
	public void setPdUnit(Integer pdUnit) {
		this.pdUnit = pdUnit;
	}
	public Integer getPdUnit() {
		return this.pdUnit;
	}
	/** 创建时间 */
	public void setPdCreateTime(String pdCreateTime) {
		this.pdCreateTime = pdCreateTime == null ? null : pdCreateTime.trim();
	}
	public String getPdCreateTime() {
		return this.pdCreateTime;
	}
	/** 更新时间 */
	public void setPdUpdateTime(String pdUpdateTime) {
		this.pdUpdateTime = pdUpdateTime == null ? null : pdUpdateTime.trim();
	}
	public String getPdUpdateTime() {
		return this.pdUpdateTime;
	}
}