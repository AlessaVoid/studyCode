package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 日终任务表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbBatchTask extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 任务代码 */
		@EntityParaAnno(zhName="任务代码")
	private String taskCode;
	/** 任务名称 */
		@EntityParaAnno(zhName="任务名称")
	private String taskName;
	/** 展示顺序 */
		@EntityParaAnno(zhName="展示顺序")
	private Integer disOrder;
	
	/** setter\getter方法 */
	/** 任务代码 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode == null ? null : taskCode.trim();
	}
	public String getTaskCode() {
		return this.taskCode;
	}
	/** 任务名称 */
	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? null : taskName.trim();
	}
	public String getTaskName() {
		return this.taskName;
	}
	/** 展示顺序 */
	public void setDisOrder(Integer disOrder) {
		this.disOrder = disOrder;
	}
	public Integer getDisOrder() {
		return this.disOrder;
	}
}