package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * esbע�����ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbEurekaStatus extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String serverName;
	/** ����״̬0ע�ᵽesb��1��ע�ᵽesb�����ߴ�esbע�� */
		@EntityParaAnno(zhName="����״̬0ע�ᵽesb��1��ע�ᵽesb�����ߴ�esbע��")
	private String serverStatus;
	/** ��ǰע��״̬0��ע��1δע�� */
		@EntityParaAnno(zhName="��ǰע��״̬0��ע��1δע��")
	private String serverRegStatus;
	/** ����ע��ip��ַ,����[,]�ָ� */
		@EntityParaAnno(zhName="����ע��ip��ַ,����[,]�ָ�")
	private String serverRegIp;
	/** ������ʱ�� */
	@EntityParaAnno(zhName="������ʱ��")
	private String updateTime;
	
	/** setter\getter���� */
	/** ������ */
	public void setServerName(String serverName) {
		this.serverName = serverName == null ? null : serverName.trim();
	}
	public String getServerName() {
		return this.serverName;
	}
	/** ����״̬0ע�ᵽesb��1��ע�ᵽesb�����ߴ�esbע�� */
	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus == null ? null : serverStatus.trim();
	}
	public String getServerStatus() {
		return this.serverStatus;
	}
	/** ��ǰע��״̬0��ע��1δע�� */
	public void setServerRegStatus(String serverRegStatus) {
		this.serverRegStatus = serverRegStatus == null ? null : serverRegStatus.trim();
	}
	public String getServerRegStatus() {
		return this.serverRegStatus;
	}
	/** ����ע��ip��ַ,����[,]�ָ� */
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