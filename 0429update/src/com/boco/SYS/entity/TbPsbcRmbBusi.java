package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ����ҵ�������Ҫҵ��ֵ���ͳ�Ʊ�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbPsbcRmbBusi extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String id;
	/** ������ʡ�� */
		@EntityParaAnno(zhName="������ʡ��")
	private String area;
	/** ͳ������ */
		@EntityParaAnno(zhName="ͳ������")
	private String statisticsDay;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String loanType;
	/** ��� */
		@EntityParaAnno(zhName="���")
	private Double balance;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private Integer rank;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private Double monthlyAdd;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private Double yearAdd;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String createdBy;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String createdTime;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String updatedBy;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String updatedTime;
	
	/** setter\getter���� */
	/** ���� */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** ������ʡ�� */
	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}
	public String getArea() {
		return this.area;
	}
	/** ͳ������ */
	public void setStatisticsDay(String statisticsDay) {
		this.statisticsDay = statisticsDay == null ? null : statisticsDay.trim();
	}
	public String getStatisticsDay() {
		return this.statisticsDay;
	}
	/** �������� */
	public void setLoanType(String loanType) {
		this.loanType = loanType == null ? null : loanType.trim();
	}
	public String getLoanType() {
		return this.loanType;
	}
	/** ��� */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getBalance() {
		return this.balance;
	}
	/** ���� */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getRank() {
		return this.rank;
	}
	/** �������� */
	public void setMonthlyAdd(Double monthlyAdd) {
		this.monthlyAdd = monthlyAdd;
	}
	public Double getMonthlyAdd() {
		return this.monthlyAdd;
	}
	/** �������� */
	public void setYearAdd(Double yearAdd) {
		this.yearAdd = yearAdd;
	}
	public Double getYearAdd() {
		return this.yearAdd;
	}
	/** ������ */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? null : createdBy.trim();
	}
	public String getCreatedBy() {
		return this.createdBy;
	}
	/** ����ʱ�� */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime == null ? null : createdTime.trim();
	}
	public String getCreatedTime() {
		return this.createdTime;
	}
	/** ������ */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}
	public String getUpdatedBy() {
		return this.updatedBy;
	}
	/** ����ʱ�� */
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime == null ? null : updatedTime.trim();
	}
	public String getUpdatedTime() {
		return this.updatedTime;
	}
}