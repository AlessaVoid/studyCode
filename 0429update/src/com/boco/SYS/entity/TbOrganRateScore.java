package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * �����������α�ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbOrganRateScore extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
		@EntityParaAnno(zhName="id")
	private String id;
	/** �·� */
		@EntityParaAnno(zhName="�·�")
	private String rateMonth;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String rateOrgan;
	/** �������ͣ�1-�¶ȣ�2-���ȣ� */
		@EntityParaAnno(zhName="�������ͣ�1-�¶ȣ�2-���ȣ�")
	private Integer rateType;
	/** ����״̬ */
		@EntityParaAnno(zhName="����״̬")
	private Integer rateStatus;
	/** �·�״̬ */
		@EntityParaAnno(zhName="�·�״̬")
	private Integer reportStatus;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String createTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String updateTime;
	/** ������Ա */
		@EntityParaAnno(zhName="������Ա")
	private String updateOper;
	
	/** setter\getter���� */
	/** id */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getId() {
		return this.id;
	}
	/** �·� */
	public void setRateMonth(String rateMonth) {
		this.rateMonth = rateMonth == null ? null : rateMonth.trim();
	}
	public String getRateMonth() {
		return this.rateMonth;
	}
	/** ���� */
	public void setRateOrgan(String rateOrgan) {
		this.rateOrgan = rateOrgan == null ? null : rateOrgan.trim();
	}
	public String getRateOrgan() {
		return this.rateOrgan;
	}
	/** �������ͣ�1-�¶ȣ�2-���ȣ� */
	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}
	public Integer getRateType() {
		return this.rateType;
	}
	/** ����״̬ */
	public void setRateStatus(Integer rateStatus) {
		this.rateStatus = rateStatus;
	}
	public Integer getRateStatus() {
		return this.rateStatus;
	}
	/** �·�״̬ */
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public Integer getReportStatus() {
		return this.reportStatus;
	}
	/** ����ʱ�� */
	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
	public String getCreateTime() {
		return this.createTime;
	}
	/** ����ʱ�� */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}
	public String getUpdateTime() {
		return this.updateTime;
	}
	/** ������Ա */
	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper == null ? null : updateOper.trim();
	}
	public String getUpdateOper() {
		return this.updateOper;
	}
}