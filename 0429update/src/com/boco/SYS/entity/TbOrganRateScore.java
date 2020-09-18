package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 机构评分批次表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbOrganRateScore extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** 月份 */
		@EntityParaAnno(zhName="月份")
	private String rateMonth;
	/** 机构 */
		@EntityParaAnno(zhName="机构")
	private String rateOrgan;
	/** 评分类型（1-月度，2-季度） */
		@EntityParaAnno(zhName="评分类型（1-月度，2-季度）")
	private Integer rateType;
	/** 审批状态 */
		@EntityParaAnno(zhName="审批状态")
	private Integer rateStatus;
	/** 下发状态 */
		@EntityParaAnno(zhName="下发状态")
	private Integer reportStatus;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String createTime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String updateTime;
	/** 更新人员 */
		@EntityParaAnno(zhName="更新人员")
	private String updateOper;
	
	/** setter\getter方法 */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** 月份 */
	public void setRateMonth(String rateMonth) {
		this.rateMonth = rateMonth == null ? null : rateMonth.trim();
	}
	public String getRateMonth() {
		return this.rateMonth;
	}
	/** 机构 */
	public void setRateOrgan(String rateOrgan) {
		this.rateOrgan = rateOrgan == null ? null : rateOrgan.trim();
	}
	public String getRateOrgan() {
		return this.rateOrgan;
	}
	/** 评分类型（1-月度，2-季度） */
	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}
	public Integer getRateType() {
		return this.rateType;
	}
	/** 审批状态 */
	public void setRateStatus(Integer rateStatus) {
		this.rateStatus = rateStatus;
	}
	public Integer getRateStatus() {
		return this.rateStatus;
	}
	/** 下发状态 */
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public Integer getReportStatus() {
		return this.reportStatus;
	}
	/** 创建时间 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
	public String getCreateTime() {
		return this.createTime;
	}
	/** 更新时间 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}
	public String getUpdateTime() {
		return this.updateTime;
	}
	/** 更新人员 */
	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper == null ? null : updateOper.trim();
	}
	public String getUpdateOper() {
		return this.updateOper;
	}
}