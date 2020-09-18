package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * esb注册控制实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbEurekaStatus extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 服务名 */
		@EntityParaAnno(zhName="服务名")
	private String serverName;
	/** 服务状态0注册到esb，1不注册到esb，或者从esb注销 */
		@EntityParaAnno(zhName="服务状态0注册到esb，1不注册到esb，或者从esb注销")
	private String serverStatus;
	/** 当前注册状态0已注册1未注册 */
		@EntityParaAnno(zhName="当前注册状态0已注册1未注册")
	private String serverRegStatus;
	/** 服务注册ip地址,逗号[,]分隔 */
		@EntityParaAnno(zhName="服务注册ip地址,逗号[,]分隔")
	private String serverRegIp;
	/** 最后更新时间 */
	@EntityParaAnno(zhName="最后更新时间")
	private String updateTime;
	
	/** setter\getter方法 */
	/** 服务名 */
	public void setServerName(String serverName) {
		this.serverName = serverName == null ? null : serverName.trim();
	}
	public String getServerName() {
		return this.serverName;
	}
	/** 服务状态0注册到esb，1不注册到esb，或者从esb注销 */
	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus == null ? null : serverStatus.trim();
	}
	public String getServerStatus() {
		return this.serverStatus;
	}
	/** 当前注册状态0已注册1未注册 */
	public void setServerRegStatus(String serverRegStatus) {
		this.serverRegStatus = serverRegStatus == null ? null : serverRegStatus.trim();
	}
	public String getServerRegStatus() {
		return this.serverRegStatus;
	}
	/** 服务注册ip地址,逗号[,]分隔 */
	public void setServerRegIp(String serverRegIp) {
		this.serverRegIp = serverRegIp == null ? null : serverRegIp.trim();
	}
	public String getServerRegIp() {
		return this.serverRegIp;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}