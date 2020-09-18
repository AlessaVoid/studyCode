package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * TbPunishDetail实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbPunishDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 主键 */
		@EntityParaAnno(zhName="主键")
	private Integer id;
	/** 日期 */
		@EntityParaAnno(zhName="日期")
	private String day;
	/** 所属月份 */
		@EntityParaAnno(zhName="所属月份")
	private String month;
	/** 机构号 */
		@EntityParaAnno(zhName="机构号")
	private String organ;
	/** 罚息类型 0-无 1-超计划 2-闲置 */
		@EntityParaAnno(zhName="罚息类型 0-无 1-超计划 2-闲置")
	private BigDecimal type;
	/** 贷种组合 */
		@EntityParaAnno(zhName="贷种组合")
	private String comb;
	/** 当月计划 */
		@EntityParaAnno(zhName="当月计划")
	private BigDecimal plan;
	/** 净增量 */
		@EntityParaAnno(zhName="净增量")
	private BigDecimal diff;
	/** 偏离量 */
		@EntityParaAnno(zhName="偏离量")
	private BigDecimal departure;
	/** 罚息金额 */
		@EntityParaAnno(zhName="罚息金额")
	private BigDecimal punishMoney;
	/** 总罚息天数 */
		@EntityParaAnno(zhName="总罚息天数")
	private BigDecimal punishDay;
	/** 倒数第几天 */
		@EntityParaAnno(zhName="倒数第几天")
	private BigDecimal leftDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public BigDecimal getType() {
		return type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}

	public String getComb() {
		return comb;
	}

	public void setComb(String comb) {
		this.comb = comb;
	}

	public BigDecimal getPlan() {
		return plan;
	}

	public void setPlan(BigDecimal plan) {
		this.plan = plan;
	}

	public BigDecimal getDiff() {
		return diff;
	}

	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}

	public BigDecimal getDeparture() {
		return departure;
	}

	public void setDeparture(BigDecimal departure) {
		this.departure = departure;
	}

	public BigDecimal getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(BigDecimal punishMoney) {
		this.punishMoney = punishMoney;
	}

	public BigDecimal getPunishDay() {
		return punishDay;
	}

	public void setPunishDay(BigDecimal punishDay) {
		this.punishDay = punishDay;
	}

	public BigDecimal getLeftDay() {
		return leftDay;
	}

	public void setLeftDay(BigDecimal leftDay) {
		this.leftDay = leftDay;
	}
}