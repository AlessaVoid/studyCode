package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * TbPunishDetailʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbPunishDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ���� */
		@EntityParaAnno(zhName="����")
	private Integer id;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String day;
	/** �����·� */
		@EntityParaAnno(zhName="�����·�")
	private String month;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String organ;
	/** ��Ϣ���� 0-�� 1-���ƻ� 2-���� */
		@EntityParaAnno(zhName="��Ϣ���� 0-�� 1-���ƻ� 2-����")
	private BigDecimal type;
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private String comb;
	/** ���¼ƻ� */
		@EntityParaAnno(zhName="���¼ƻ�")
	private BigDecimal plan;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private BigDecimal diff;
	/** ƫ���� */
		@EntityParaAnno(zhName="ƫ����")
	private BigDecimal departure;
	/** ��Ϣ��� */
		@EntityParaAnno(zhName="��Ϣ���")
	private BigDecimal punishMoney;
	/** �ܷ�Ϣ���� */
		@EntityParaAnno(zhName="�ܷ�Ϣ����")
	private BigDecimal punishDay;
	/** �����ڼ��� */
		@EntityParaAnno(zhName="�����ڼ���")
	private BigDecimal leftDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public BigDecimal getType() {
		return type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}

	public String getComb() {
		return comb;
	}

	public void setComb(String comb) {
		this.comb = comb;
	}

	public BigDecimal getPlan() {
		return plan;
	}

	public void setPlan(BigDecimal plan) {
		this.plan = plan;
	}

	public BigDecimal getDiff() {
		return diff;
	}

	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}

	public BigDecimal getDeparture() {
		return departure;
	}

	public void setDeparture(BigDecimal departure) {
		this.departure = departure;
	}

	public BigDecimal getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(BigDecimal punishMoney) {
		this.punishMoney = punishMoney;
	}

	public BigDecimal getPunishDay() {
		return punishDay;
	}

	public void setPunishDay(BigDecimal punishDay) {
		this.punishDay = punishDay;
	}

	public BigDecimal getLeftDay() {
		return leftDay;
	}

	public void setLeftDay(BigDecimal leftDay) {
		this.leftDay = leftDay;
	}
}