package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * FdOperʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class FdOper extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ��������  */
	private java.lang.String organcode;
	/** ����Ա���� */
	private java.lang.String opercode;
	/** ����Ա����     */
	private java.lang.String opername;
	/** ����Ա����    */
	private java.lang.String operpassword;
	/** ����Ա״̬    0-ǩ�� 1-ǩ�� 2-ע��  */
	private java.lang.String operstate;
	/** ����Ա��ݣ�����1-��Ա,2-�ۺϹ�Ա,3-�����Σ�֧�ֳ���,4-��������,5-����,6-���Ա,7-ƾ֤����Ա,8-ҵ������   */
	private java.lang.String operdegree;
	/** ע���ն˺�       */
	private java.lang.String ttyno;
	/** �������Ա��־  0-�������� 1-�绰���� 2-ATM  3-POS  4-��������  */
	private java.lang.String virtualflag;
	/** ƾ֤�׺�(��Ʒ�¼��ˮ��һ����ˮ��) */
	private java.lang.Long voucherserial;
	/** ��ǰ��ˮ��  */
	private java.lang.Long acctserial;
	/** ��־��ˮ��   */
	private java.lang.Long logserial;
	/** �޸�ʱ��(�Ըñ�������޸�ʱ�����ۼ�)  */
	private java.lang.String modifydate;
	/** �޸Ļ���(�Ըñ�������޸�ʱ�����ۼ�) */
	private java.lang.String modifyorgan;
	/** �޸Ĺ�Ա(�Ըñ�������޸�ʱ�����ۼ�)  */
	private java.lang.String modifyoper;
	/** */
	private java.lang.String certno;
	/** ��ɫ�������ں����ѯʹ�� */          
	private java.lang.String operdegrees;
	
	/** setter\getter���� */
	/** ��������   */
	public void setOrgancode(java.lang.String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public java.lang.String getOrgancode() {
		return this.organcode;
	}
	/** ����Ա����                                                                                                  */
	public void setOpercode(java.lang.String opercode) {
		this.opercode = opercode == null ? null : opercode.trim();
	}
	public java.lang.String getOpercode() {
		return this.opercode;
	}
	/** ����Ա����                                                                                                  */
	public void setOpername(java.lang.String opername) {
		this.opername = opername == null ? null : opername.trim();
	}
	public java.lang.String getOpername() {
		return this.opername;
	}
	/** ����Ա����                                                                                                  */
	public void setOperpassword(java.lang.String operpassword) {
		this.operpassword = operpassword == null ? null : operpassword.trim();
	}
	public java.lang.String getOperpassword() {
		return this.operpassword;
	}
	/** ����Ա״̬    0-ǩ�� 1-ǩ�� 2-ע��                                                                          */
	public void setOperstate(java.lang.String operstate) {
		this.operstate = operstate == null ? null : operstate.trim();
	}
	public java.lang.String getOperstate() {
		return this.operstate;
	}
	/** ����Ա��ݣ�����1-��Ա,2-�ۺϹ�Ա,3-�����Σ�֧�ֳ���,4-��������,5-����,6-���Ա,7-ƾ֤����Ա,8-ҵ������   */
	public void setOperdegree(java.lang.String operdegree) {
		this.operdegree = operdegree == null ? null : operdegree.trim();
	}
	public java.lang.String getOperdegree() {
		return this.operdegree;
	}
	/** ע���ն˺�                                                                                                  */
	public void setTtyno(java.lang.String ttyno) {
		this.ttyno = ttyno == null ? null : ttyno.trim();
	}
	public java.lang.String getTtyno() {
		return this.ttyno;
	}
	/** �������Ա��־  0-�������� 1-�绰���� 2-ATM  3-POS  4-��������                                              */
	public void setVirtualflag(java.lang.String virtualflag) {
		this.virtualflag = virtualflag == null ? null : virtualflag.trim();
	}
	public java.lang.String getVirtualflag() {
		return this.virtualflag;
	}
	/** ƾ֤�׺�(��Ʒ�¼��ˮ��һ����ˮ��)                                                                          */
	public void setVoucherserial(java.lang.Long voucherserial) {
		this.voucherserial = voucherserial;
	}
	public java.lang.Long getVoucherserial() {
		return this.voucherserial;
	}
	/** ��ǰ��ˮ��                                                                                                  */
	public void setAcctserial(java.lang.Long acctserial) {
		this.acctserial = acctserial;
	}
	public java.lang.Long getAcctserial() {
		return this.acctserial;
	}
	/** ��־��ˮ��                                                                                                  */
	public void setLogserial(java.lang.Long logserial) {
		this.logserial = logserial;
	}
	public java.lang.Long getLogserial() {
		return this.logserial;
	}
	/** �޸�ʱ��(�Ըñ�������޸�ʱ�����ۼ�)                                                                        */
	public void setModifydate(java.lang.String modifydate) {
		this.modifydate = modifydate == null ? null : modifydate.trim();
	}
	public java.lang.String getModifydate() {
		return this.modifydate;
	}
	/** �޸Ļ���(�Ըñ�������޸�ʱ�����ۼ�)                                                                        */
	public void setModifyorgan(java.lang.String modifyorgan) {
		this.modifyorgan = modifyorgan == null ? null : modifyorgan.trim();
	}
	public java.lang.String getModifyorgan() {
		return this.modifyorgan;
	}
	/** �޸Ĺ�Ա(�Ըñ�������޸�ʱ�����ۼ�)                                                                        */
	public void setModifyoper(java.lang.String modifyoper) {
		this.modifyoper = modifyoper == null ? null : modifyoper.trim();
	}
	public java.lang.String getModifyoper() {
		return this.modifyoper;
	}
	/**                                                                                                              */
	public void setCertno(java.lang.String certno) {
		this.certno = certno == null ? null : certno.trim();
	}
	public java.lang.String getCertno() {
		return this.certno;
	}
	/** ��ɫ�������ں����ѯʹ�� */   
	public java.lang.String getOperdegrees() {
		return operdegrees;
	}
	/** ��ɫ�������ں����ѯʹ�� */   
	public void setOperdegrees(java.lang.String operdegrees) {
		this.operdegrees = operdegrees;
	}
	
}