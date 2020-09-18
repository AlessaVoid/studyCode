package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 日终任务执行表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbBatchTaskStatus extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 批量日期 */
		@EntityParaAnno(zhName="批量日期")
	private String batchDay;
	/** 日终任务编号 */
		@EntityParaAnno(zhName="日终任务编号")
	private String taskCode;
	/** 任务状态0成功1失败 */
		@EntityParaAnno(zhName="任务状态0成功1失败")
	private Integer taskStatus;
	/** 开始时间 */
		@EntityParaAnno(zhName="开始时间")
	private String startTime;
	/** 结束时间 */
		@EntityParaAnno(zhName="结束时间")
	private String endTime;
	
	/** setter\getter方法 */
	/** 批量日期 */
	public void setBatchDay(String batchDay) {
		this.batchDay = batchDay == null ? null : batchDay.trim();
	}
	public String getBatchDay() {
		return this.batchDay;
	}
	/** 日终任务编号 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode == null ? null : taskCode.trim();
	}
	public String getTaskCode() {
		return this.taskCode;
	}
	/** 任务状态0成功1失败 */
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Integer getTaskStatus() {
		return this.taskStatus;
	}
	/** 开始时间 */
	public void setStartTime(String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}
	public String getStartTime() {
		return this.startTime;
	}
	/** 结束时间 */
	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}
	public String getEndTime() {
		return this.endTime;
	}
}