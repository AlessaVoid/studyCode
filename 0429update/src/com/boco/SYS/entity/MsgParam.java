package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ���Ͷ���-������ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class MsgParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���ſ��أ�1-�� 2-�ر� */
		@EntityParaAnno(zhName="���ſ��أ�1-�� 2-�ر�")
	private Integer openStatus;
	/** ÿ����෢�Ͷ��ŵĴ��� */
		@EntityParaAnno(zhName="ÿ����෢�Ͷ��ŵĴ���")
	private Integer maxCount;
	/** ������ʱ���Ͷ��ŵĴ��� */
		@EntityParaAnno(zhName="������ʱ���Ͷ��ŵĴ���")
	private Integer openCount;
	/** ����ر�ʱ���Ͷ��ŵĴ��� */
		@EntityParaAnno(zhName="����ر�ʱ���Ͷ��ŵĴ���")
	private Integer closeCount;
	/** �����ѷ��͵Ĵ��� */
		@EntityParaAnno(zhName="�����ѷ��͵Ĵ���")
	private Integer todayCount;
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	
	/** setter\getter���� */
	/** ���ſ��أ�1-�� 2-�ر� */
	public void setOpenStatus(Integer openStatus) {
		this.openStatus = openStatus;
	}
	public Integer getOpenStatus() {
		return this.openStatus;
	}
	/** ÿ����෢�Ͷ��ŵĴ��� */
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public Integer getMaxCount() {
		return this.maxCount;
	}
	/** ������ʱ���Ͷ��ŵĴ��� */
	public void setOpenCount(Integer openCount) {
		this.openCount = openCount;
	}
	public Integer getOpenCount() {
		return this.openCount;
	}
	/** ����ر�ʱ���Ͷ��ŵĴ��� */
	public void setCloseCount(Integer closeCount) {
		this.closeCount = closeCount;
	}
	public Integer getCloseCount() {
		return this.closeCount;
	}
	/** �����ѷ��͵Ĵ��� */
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