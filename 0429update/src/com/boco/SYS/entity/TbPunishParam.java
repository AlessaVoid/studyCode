package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 *
 *
 * ��Ϣ������ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbPunishParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** id */
	@EntityParaAnno(zhName="id")
	private java.lang.Integer ppId;
	/** ��Ϣ���� */
	@EntityParaAnno(zhName="��Ϣ����")
	private java.lang.String ppName;
	/** ������ */
	@EntityParaAnno(zhName="������")
	private java.lang.String ppOrgan;
	/** ��Ϣ���ͣ�1 ��ĩ����,2��ĩ��ĩ,4�Ǽ�ĩ����,8�Ǽ�ĩ��ĩ */
	@EntityParaAnno(zhName="��Ϣ���ͣ�1 ��ĩ����,2��ĩ��ĩ,4�Ǽ�ĩ����,8�Ǽ�ĩ��ĩ")
	private java.lang.Integer type;
	/** ������Сֵ */
	@EntityParaAnno(zhName="������Сֵ")
	BigDecimal minnum;
	/** �������ֵ */
	@EntityParaAnno(zhName="�������ֵ")
	BigDecimal maxnum;
	/** �����Ӧ��Ϣ */
	@EntityParaAnno(zhName="�����Ӧ��Ϣ")
	BigDecimal interest;
	/** ״̬ */
	@EntityParaAnno(zhName="״̬")
	private java.lang.Integer state;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String createtime;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String updatetime;
	/** �����û�id */
	@EntityParaAnno(zhName="�����û�id")
	private java.lang.String createuserid;
	/** �����û�id */
	@EntityParaAnno(zhName="�����û�id")
	private java.lang.String updateuserid;
	/** ��ȡ���� */
	@EntityParaAnno(zhName="��ȡ����")
	private java.lang.Integer collecttype;
	/** ��Ϣ���� */
	@EntityParaAnno(zhName="��Ϣ����")
	private java.lang.Integer ppType;

	/** setter\getter���� */
	/** id */
	public void setPpId(java.lang.Integer ppId) {
		this.ppId = ppId;
	}
	public java.lang.Integer getPpId() {
		return this.ppId;
	}
	/** ��Ϣ���� */
	public void setPpName(java.lang.String ppName) {
		this.ppName = ppName == null ? null : ppName.trim();
	}
	public java.lang.String getPpName() {
		return this.ppName;
	}
	/** ������ */
	public void setPpOrgan(java.lang.String ppOrgan) {
		this.ppOrgan = ppOrgan == null ? null : ppOrgan.trim();
	}
	public java.lang.String getPpOrgan() {
		return this.ppOrgan;
	}
	/** ��Ϣ���ͣ�1 ��ĩ����,2��ĩ��ĩ,4�Ǽ�ĩ����,8�Ǽ�ĩ��ĩ */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	public java.lang.Integer getType() {
		return this.type;
	}
	/** ������Сֵ */
	public BigDecimal getMinnum() {
		return minnum;
	}

	public void setMinnum(BigDecimal minnum) {
		this.minnum = minnum;
	}

	public BigDecimal getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(BigDecimal maxnum) {
		this.maxnum = maxnum;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	/** ״̬ */
	public void setState(java.lang.Integer state) {
		this.state = state;
	}
	public java.lang.Integer getState() {
		return this.state;
	}
	/** ����ʱ�� */
	public void setCreatetime(java.lang.String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}
	public java.lang.String getCreatetime() {
		return this.createtime;
	}
	/** ����ʱ�� */
	public void setUpdatetime(java.lang.String updatetime) {
		this.updatetime = updatetime == null ? null : updatetime.trim();
	}
	public java.lang.String getUpdatetime() {
		return this.updatetime;
	}
	/** �����û�id */
	public void setCreateuserid(java.lang.String createuserid) {
		this.createuserid = createuserid == null ? null : createuserid.trim();
	}
	public java.lang.String getCreateuserid() {
		return this.createuserid;
	}
	/** �����û�id */
	public void setUpdateuserid(java.lang.String updateuserid) {
		this.updateuserid = updateuserid == null ? null : updateuserid.trim();
	}
	public java.lang.String getUpdateuserid() {
		return this.updateuserid;
	}
	/** ��ȡ���� */
	public void setCollecttype(java.lang.Integer collecttype) {
		this.collecttype = collecttype;
	}
	public java.lang.Integer getCollecttype() {
		return this.collecttype;
	}
	/** ��Ϣ���� */
	public void setPpType(java.lang.Integer ppType) {
		this.ppType = ppType;
	}
	public java.lang.Integer getPpType() {
		return this.ppType;
	}
}