package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * TbSystemCtrlStatus实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbSystemCtrlStatus extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 系统编号 */
		@EntityParaAnno(zhName="系统编号")
	private String systemid;
	/** 是否管控：0-管控 1-不管控 */
		@EntityParaAnno(zhName="是否管控：0-管控 1-不管控")
	private String systemStatus;
	
	/** setter\getter方法 */
	/** 系统编号 */
	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}
	public String getSystemid() {
		return this.systemid;
	}
	/** 是否管控：0-管控 1-不管控 */
	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus == null ? null : systemStatus.trim();
	}
	public String getSystemStatus() {
		return this.systemStatus;
	}
}