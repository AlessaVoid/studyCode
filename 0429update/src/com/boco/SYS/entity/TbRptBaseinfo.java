package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 报表基础数据-每日生成-包含贷款余额，发生量，到期量等实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbRptBaseinfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 报表日期 */
		@EntityParaAnno(zhName="报表日期")
	private String rptDate;
	/** 机构号 */
		@EntityParaAnno(zhName="机构号")
	private String organ;
	/** 所属月份 */
		@EntityParaAnno(zhName="所属月份")
	private String rptMonth;
	/** 贷款分类 */
		@EntityParaAnno(zhName="贷款分类")
	private String loanKind;
	/** 当前余额 */
		@EntityParaAnno(zhName="当前余额")
	private double balance;
	/** 本月发生 */
		@EntityParaAnno(zhName="本月发生")
	private double monthOcc;
	/** 本月到期 */
		@EntityParaAnno(zhName="本月到期")
	private double monthLimit;
	/** 当日发生 */
		@EntityParaAnno(zhName="当日发生")
	private double dayOcc;
	/** 当日到期 */
		@EntityParaAnno(zhName="当日到期")
	private double dayLimit;
	/** 当月剩余到期量 */
		@EntityParaAnno(zhName="当月剩余到期量")
	private double monthLimitLeft;
	
	/** setter\getter方法 */
	/** 报表日期 */
	public void setRptDate(String rptDate) {
		this.rptDate = rptDate == null ? null : rptDate.trim();
	}
	public String getRptDate() {
		return this.rptDate;
	}
	/** 机构号 */
	public void setOrgan(String organ) {
		this.organ = organ == null ? null : organ.trim();
	}
	public String getOrgan() {
		return this.organ;
	}
	/** 所属月份 */
	public void setRptMonth(String rptMonth) {
		this.rptMonth = rptMonth == null ? null : rptMonth.trim();
	}
	public String getRptMonth() {
		return this.rptMonth;
	}
	/** 贷款分类 */
	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind == null ? null : loanKind.trim();
	}
	public String getLoanKind() {
		return this.loanKind;
	}
	/** 当前余额 */
	public void setBalance(double balance) {
		this.balance = balance ;
	}
	public double getBalance() {
		return this.balance;
	}
	/** 本月发生 */
	public void setMonthOcc(double monthOcc) {
		this.monthOcc = monthOcc ;
	}
	public double getMonthOcc() {
		return this.monthOcc;
	}
	/** 本月到期 */
	public void setMonthLimit(double monthLimit) {
		this.monthLimit = monthLimit ;
	}
	public double getMonthLimit() {
		return this.monthLimit;
	}
	/** 当日发生 */
	public void setDayOcc(double dayOcc) {
		this.dayOcc = dayOcc ;
	}
	public double getDayOcc() {
		return this.dayOcc;
	}
	/** 当日到期 */
	public void setDayLimit(double dayLimit) {
		this.dayLimit = dayLimit ;
	}
	public double getDayLimit() {
		return this.dayLimit;
	}
	/** 当月剩余到期量 */
	public void setMonthLimitLeft(double monthLimitLeft) {
		this.monthLimitLeft = monthLimitLeft ;
	}
	public double getMonthLimitLeft() {
		return this.monthLimitLeft;
	}
}