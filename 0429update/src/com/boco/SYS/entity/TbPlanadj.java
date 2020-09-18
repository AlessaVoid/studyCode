package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * �Ŵ��ƻ��������α�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbPlanadj extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id�����κţ� */
		@EntityParaAnno(zhName="id�����κţ�")
	private String planadjId;
	/** �����·� */
		@EntityParaAnno(zhName="�����·�")
	private String planadjMonth;
	/** ���¼ƻ������� */
		@EntityParaAnno(zhName="���¼ƻ�������")
	private BigDecimal planadjNetIncrement;
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private BigDecimal planadjAdjAmount;
	/** ��λ */
		@EntityParaAnno(zhName="��λ")
	private Integer planadjUnit;
	/** ����״̬ */
		@EntityParaAnno(zhName="����״̬")
	private Integer planadjStatus;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String planadjCreateTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String planadjUpdateTime;
	/** ���Ա�� */
		@EntityParaAnno(zhName="���Ա��")
	private String planadjCreateOpercode;
	/** �ƻ��ƶ����� */
		@EntityParaAnno(zhName="�ƻ��ƶ�����")
	private String planadjOrgan;
	/** ���µ��������� */
	@EntityParaAnno(zhName="���µ���������")
	private BigDecimal planadjRealIncrement;

	/** �ƻ��������� 1-�ƻ� 2-���� */
	@EntityParaAnno(zhName="�ƻ��������� 1-�ƻ� 2-����")
	private Integer planadjType;

	/** �Ƿ�ͳһ�滮��������Ի�����  1-�� 2-�� */
	@EntityParaAnno(zhName="�Ƿ�ͳһ�滮��������Ի�����  1-�� 2-��")
	private Integer planadjUnifiedType;

	/** �����ƻ����ּ��� */
	@EntityParaAnno(zhName="�����ƻ����ּ���")
	private Integer combLevel;
	
	/** setter\getter���� */
	/** id�����κţ� */
	public void setPlanadjId(String planadjId) {
		this.planadjId = planadjId == null ? null : planadjId.trim();
	}
	public String getPlanadjId() {
		return this.planadjId;
	}
	/** �����·� */
	public void setPlanadjMonth(String planadjMonth) {
		this.planadjMonth = planadjMonth == null ? null : planadjMonth.trim();
	}
	public String getPlanadjMonth() {
		return this.planadjMonth;
	}

	public BigDecimal getPlanadjNetIncrement() {
		return planadjNetIncrement;
	}

	public void setPlanadjNetIncrement(BigDecimal planadjNetIncrement) {
		this.planadjNetIncrement = planadjNetIncrement;
	}

	public BigDecimal getPlanadjAdjAmount() {
		return planadjAdjAmount;
	}

	public void setPlanadjAdjAmount(BigDecimal planadjAdjAmount) {
		this.planadjAdjAmount = planadjAdjAmount;
	}

	/** ��λ */
	public void setPlanadjUnit(Integer planadjUnit) {
		this.planadjUnit = planadjUnit;
	}
	public Integer getPlanadjUnit() {
		return this.planadjUnit;
	}
	/** ����״̬ */
	public void setPlanadjStatus(Integer planadjStatus) {
		this.planadjStatus = planadjStatus;
	}
	public Integer getPlanadjStatus() {
		return this.planadjStatus;
	}
	/** ����ʱ�� */
	public void setPlanadjCreateTime(String planadjCreateTime) {
		this.planadjCreateTime = planadjCreateTime == null ? null : planadjCreateTime.trim();
	}
	public String getPlanadjCreateTime() {
		return this.planadjCreateTime;
	}
	/** ����ʱ�� */
	public void setPlanadjUpdateTime(String planadjUpdateTime) {
		this.planadjUpdateTime = planadjUpdateTime == null ? null : planadjUpdateTime.trim();
	}
	public String getPlanadjUpdateTime() {
		return this.planadjUpdateTime;
	}
	/** �����id */
	public void setPlanadjCreateOpercode(String planadjCreateOpercode) {
		this.planadjCreateOpercode = planadjCreateOpercode == null ? null : planadjCreateOpercode.trim();
	}
	public String getPlanadjCreateOpercode() {
		return this.planadjCreateOpercode;
	}
	/** planadjOrgan */
	public void setPlanadjOrgan(String planadjOrgan) {
		this.planadjOrgan = planadjOrgan == null ? null : planadjOrgan.trim();
	}
	public String getPlanadjOrgan() {
		return this.planadjOrgan;
	}

	public BigDecimal getPlanadjRealIncrement() {
		return planadjRealIncrement;
	}

	public void setPlanadjRealIncrement(BigDecimal planadjRealIncrement) {
		this.planadjRealIncrement = planadjRealIncrement;
	}

	public Integer getPlanadjType() {
		return planadjType;
	}

	public void setPlanadjType(Integer planadjType) {
		this.planadjType = planadjType;
	}

	public Integer getPlanadjUnifiedType() {
		return planadjUnifiedType;
	}

	public void setPlanadjUnifiedType(Integer planadjUnifiedType) {
		this.planadjUnifiedType = planadjUnifiedType;
	}

	public Integer getCombLevel() {
		return combLevel;
	}

	public void setCombLevel(Integer combLevel) {
		this.combLevel = combLevel;
	}
}