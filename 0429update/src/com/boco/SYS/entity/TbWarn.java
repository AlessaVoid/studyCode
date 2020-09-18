package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * Ԥ���߱�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbWarn extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** Ԥ����id */
		@EntityParaAnno(zhName="Ԥ����id")
	private Integer warnId;
	/** Ԥ�������� */
		@EntityParaAnno(zhName="Ԥ��������")
	private String warnName;
	/** Ԥ������ */
		@EntityParaAnno(zhName="Ԥ������")
	private Integer warnOrgan;
	/** Ԥ������ */
		@EntityParaAnno(zhName="Ԥ������")
	private String warnComb;
	/** Ԥ�������� */
		@EntityParaAnno(zhName="Ԥ��������")
	private Integer warnType;
	/** һ��Ԥ���� */
		@EntityParaAnno(zhName="һ��Ԥ����")
	private Double warnOneLine;
	/** ����Ԥ���� */
		@EntityParaAnno(zhName="����Ԥ����")
	private Double warnTwoLine;
	/** ����Ԥ���� */
		@EntityParaAnno(zhName="����Ԥ����")
	private Double warnThreeLine;
	/** �ļ�Ԥ���� */
		@EntityParaAnno(zhName="�ļ�Ԥ����")
	private Double warnFourLine;
	/** �弶Ԥ���� */
		@EntityParaAnno(zhName="�弶Ԥ����")
	private Double warnFiveLine;
	/** Ԥ���ߴ�����Ա */
		@EntityParaAnno(zhName="Ԥ���ߴ�����Ա")
	private String warnCreateOper;
	/** Ԥ���߸�����Ա */
		@EntityParaAnno(zhName="Ԥ���߸�����Ա")
	private String warnUpdateOper;
	/** Ԥ���ߴ���ʱ�� */
		@EntityParaAnno(zhName="Ԥ���ߴ���ʱ��")
	private String warnCreateTime;
	/** Ԥ���߸���ʱ�� */
		@EntityParaAnno(zhName="Ԥ���߸���ʱ��")
	private String warnUpdateTime;
	
	/** setter\getter���� */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getWarnId() {
		return warnId;
	}

	public void setWarnId(Integer warnId) {
		this.warnId = warnId;
	}

	public String getWarnName() {
		return warnName;
	}

	public void setWarnName(String warnName) {
		this.warnName = warnName;
	}

	public Integer getWarnOrgan() {
		return warnOrgan;
	}

	public void setWarnOrgan(Integer warnOrgan) {
		this.warnOrgan = warnOrgan;
	}

	public String getWarnComb() {
		return warnComb;
	}

	public void setWarnComb(String warnComb) {
		this.warnComb = warnComb;
	}

	public Integer getWarnType() {
		return warnType;
	}

	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}

	public Double getWarnOneLine() {
		return warnOneLine;
	}

	public void setWarnOneLine(Double warnOneLine) {
		this.warnOneLine = warnOneLine;
	}

	public Double getWarnTwoLine() {
		return warnTwoLine;
	}

	public void setWarnTwoLine(Double warnTwoLine) {
		this.warnTwoLine = warnTwoLine;
	}

	public Double getWarnThreeLine() {
		return warnThreeLine;
	}

	public void setWarnThreeLine(Double warnThreeLine) {
		this.warnThreeLine = warnThreeLine;
	}

	public Double getWarnFourLine() {
		return warnFourLine;
	}

	public void setWarnFourLine(Double warnFourLine) {
		this.warnFourLine = warnFourLine;
	}

	public Double getWarnFiveLine() {
		return warnFiveLine;
	}

	public void setWarnFiveLine(Double warnFiveLine) {
		this.warnFiveLine = warnFiveLine;
	}

	public String getWarnCreateOper() {
		return warnCreateOper;
	}

	public void setWarnCreateOper(String warnCreateOper) {
		this.warnCreateOper = warnCreateOper;
	}

	public String getWarnUpdateOper() {
		return warnUpdateOper;
	}

	public void setWarnUpdateOper(String warnUpdateOper) {
		this.warnUpdateOper = warnUpdateOper;
	}

	public String getWarnCreateTime() {
		return warnCreateTime;
	}

	public void setWarnCreateTime(String warnCreateTime) {
		this.warnCreateTime = warnCreateTime;
	}

	public String getWarnUpdateTime() {
		return warnUpdateTime;
	}

	public void setWarnUpdateTime(String warnUpdateTime) {
		this.warnUpdateTime = warnUpdateTime;
	}
}