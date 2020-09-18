package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 报表专用_贷种组合表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReportComb extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 贷种组合序列号 */
		@EntityParaAnno(zhName="贷种组合序列号")
	private String combId;
	/** 贷种组合编码 */
		@EntityParaAnno(zhName="贷种组合编码")
	private String combCode;
	/** 贷种组合名称 */
		@EntityParaAnno(zhName="贷种组合名称")
	private String combName;
	/** 贷种组合级别 */
		@EntityParaAnno(zhName="贷种组合级别")
	private int combLevel;
	/** 贷种组合状态 -1-当前贷种已删除,  0-可被上级贷种组合,  1-表示已被其他上级贷种占用 */
		@EntityParaAnno(zhName="贷种组合状态 -1-当前贷种已删除,  0-可被上级贷种组合,  1-表示已被其他上级贷种占用")
	private int combStatus;
	/** 贷种创建者 */
		@EntityParaAnno(zhName="贷种创建者")
	private String combCreateOper;
	/** 贷种创建时间 */
		@EntityParaAnno(zhName="贷种创建时间")
	private String combCreateTime;
	/** 贷种更新者 */
		@EntityParaAnno(zhName="贷种更新者")
	private String combUpdateOper;
	/** 贷种更新时间 */
		@EntityParaAnno(zhName="贷种更新时间")
	private String combUpdateTime;
	/** 贷种排序字段 */
		@EntityParaAnno(zhName="贷种排序字段")
	private Integer combOrder;
	
	/** setter\getter方法 */
	/** 贷种组合序列号 */
	public void setCombId(String combId) {
		this.combId = combId == null ? null : combId.trim();
	}
	public String getCombId() {
		return this.combId;
	}
	/** 贷种组合编码 */
	public void setCombCode(String combCode) {
		this.combCode = combCode == null ? null : combCode.trim();
	}
	public String getCombCode() {
		return this.combCode;
	}
	/** 贷种组合名称 */
	public void setCombName(String combName) {
		this.combName = combName == null ? null : combName.trim();
	}
	public String getCombName() {
		return this.combName;
	}
	/** 贷种组合级别 */
	public void setCombLevel(Integer combLevel) {
		this.combLevel = combLevel;
	}
	public Integer getCombLevel() {
		return this.combLevel;
	}
	/** 贷种组合状态 -1-当前贷种已删除,  0-可被上级贷种组合,  1-表示已被其他上级贷种占用 */
	public void setCombStatus(Integer combStatus) {
		this.combStatus = combStatus;
	}
	public Integer getCombStatus() {
		return this.combStatus;
	}
	/** 贷种创建者 */
	public void setCombCreateOper(String combCreateOper) {
		this.combCreateOper = combCreateOper == null ? null : combCreateOper.trim();
	}
	public String getCombCreateOper() {
		return this.combCreateOper;
	}
	/** 贷种创建时间 */
	public void setCombCreateTime(String combCreateTime) {
		this.combCreateTime = combCreateTime == null ? null : combCreateTime.trim();
	}
	public String getCombCreateTime() {
		return this.combCreateTime;
	}
	/** 贷种更新者 */
	public void setCombUpdateOper(String combUpdateOper) {
		this.combUpdateOper = combUpdateOper == null ? null : combUpdateOper.trim();
	}
	public String getCombUpdateOper() {
		return this.combUpdateOper;
	}
	/** 贷种更新时间 */
	public void setCombUpdateTime(String combUpdateTime) {
		this.combUpdateTime = combUpdateTime == null ? null : combUpdateTime.trim();
	}
	public String getCombUpdateTime() {
		return this.combUpdateTime;
	}
	/** 贷种排序字段 */
	public void setCombOrder(Integer combOrder) {
		this.combOrder = combOrder;
	}
	public Integer getCombOrder() {
		return this.combOrder;
	}
}