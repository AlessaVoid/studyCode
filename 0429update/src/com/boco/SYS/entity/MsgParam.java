package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 发送短信-参数表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class MsgParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 短信开关：1-打开 2-关闭 */
		@EntityParaAnno(zhName="短信开关：1-打开 2-关闭")
	private Integer openStatus;
	/** 每天最多发送短信的次数 */
		@EntityParaAnno(zhName="每天最多发送短信的次数")
	private Integer maxCount;
	/** 服务开启时发送短信的次数 */
		@EntityParaAnno(zhName="服务开启时发送短信的次数")
	private Integer openCount;
	/** 服务关闭时发送短信的次数 */
		@EntityParaAnno(zhName="服务关闭时发送短信的次数")
	private Integer closeCount;
	/** 今日已发送的次数 */
		@EntityParaAnno(zhName="今日已发送的次数")
	private Integer todayCount;
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	
	/** setter\getter方法 */
	/** 短信开关：1-打开 2-关闭 */
	public void setOpenStatus(Integer openStatus) {
		this.openStatus = openStatus;
	}
	public Integer getOpenStatus() {
		return this.openStatus;
	}
	/** 每天最多发送短信的次数 */
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Integer getMaxCount() {
		return this.maxCount;
	}
	/** 服务开启时发送短信的次数 */
	public void setOpenCount(Integer openCount) {
		this.openCount = openCount;
	}
	public Integer getOpenCount() {
		return this.openCount;
	}
	/** 服务关闭时发送短信的次数 */
	public void setCloseCount(Integer closeCount) {
		this.closeCount = closeCount;
	}
	public Integer getCloseCount() {
		return this.closeCount;
	}
	/** 今日已发送的次数 */
	public void setTodayCount(Integer todayCount) {
		this.todayCount = todayCount;
	}
	public Integer getTodayCount() {
		return this.todayCount;
	}
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
}