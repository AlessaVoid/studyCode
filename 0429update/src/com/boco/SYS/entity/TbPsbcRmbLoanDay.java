package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * ����Ҵ����ձ���ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbPsbcRmbLoanDay extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String id;
	/** ͳ������ */
		@EntityParaAnno(zhName="ͳ������")
	private String statisticsDay;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String loanType;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String organcode;
	/** ��� */
		@EntityParaAnno(zhName="���")
	private String grepcode;
	/** ��� */
		@EntityParaAnno(zhName="���")
	private Double balance;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private Double dayAdd;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private Double monthlyAdd;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private Double quarterAdd;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private Double yearAdd;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private Integer yearRank;
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
	/** ���� */
	public void setOrgancode(String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public String getOrgancode() {
		return this.organcode;
	}
	/** ��� */
	public void setGrepcode(String grepcode) {
		this.grepcode = grepcode == null ? null : grepcode.trim();
	}
	public String getGrepcode() {
		return this.grepcode;
	}
	/** ��� */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getBalance() {
		return this.balance;
	}
	/** ���� */
	public void setDayAdd(Double dayAdd) {
		this.dayAdd = dayAdd;
	}
	public Double getDayAdd() {
		return this.dayAdd;
	}
	/** ���� */
	public void setMonthlyAdd(Double monthlyAdd) {
		this.monthlyAdd = monthlyAdd;
	}
	public Double getMonthlyAdd() {
		return this.monthlyAdd;
	}
	/** ���� */
	public void setQuarterAdd(Double quarterAdd) {
		this.quarterAdd = quarterAdd;
	}
	public Double getQuarterAdd() {
		return this.quarterAdd;
	}
	/** ���� */
	public void setYearAdd(Double yearAdd) {
		this.yearAdd = yearAdd;
	}
	public Double getYearAdd() {
		return this.yearAdd;
	}
	/** �������� */
	public void setYearRank(Integer yearRank) {
		this.yearRank = yearRank;
	}
	public Integer getYearRank() {
		return this.yearRank;
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