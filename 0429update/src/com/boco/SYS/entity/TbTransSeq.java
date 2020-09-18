package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * ��ˮ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbTransSeq extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ϵͳ���ٺ�ESB */
		@EntityParaAnno(zhName="ϵͳ���ٺ�ESB")
	private String sysTrackNo;
	/** ���ѷ�ϵͳ���� */
		@EntityParaAnno(zhName="���ѷ�ϵͳ����")
	private String transInst;
	/** ����ϵͳ���� */
		@EntityParaAnno(zhName="����ϵͳ����")
	private String sendInst;
	/** �ṩ��ϵͳ���� */
		@EntityParaAnno(zhName="�ṩ��ϵͳ����")
	private String destInst;
	/** ��ˮ�� */
		@EntityParaAnno(zhName="��ˮ��")
	private String ctlSeqNo;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String transDate;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String transTime;
	/** ������� */
		@EntityParaAnno(zhName="�������")
	private String accDate;
	/** ҵ��ϵͳƽ̨�� */
		@EntityParaAnno(zhName="ҵ��ϵͳƽ̨��")
	private String systemid;
	/** �Ƿ��Զ��ſ�� */
		@EntityParaAnno(zhName="�Ƿ��Զ��ſ��")
	private String isAuto;
	/** ��ݱ��롢����id */
		@EntityParaAnno(zhName="��ݱ��롢����id")
	private String paperCode;
	/** ����������� */
		@EntityParaAnno(zhName="�����������")
	private BigDecimal amt;
	/** ���� */
		@EntityParaAnno(zhName="����")
	private String ccy;
	/** �ſ����� */
		@EntityParaAnno(zhName="�ſ�����")
	private String loanDate;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private String limitDate;
	/** ���׻��� */
		@EntityParaAnno(zhName="���׻���")
	private String transOrgan;
	/** ���ײ�Ʒ���� */
		@EntityParaAnno(zhName="���ײ�Ʒ����")
	private String productCode;
	/** �������� 1-������� 2-֪ͨ 3��ѯ */
		@EntityParaAnno(zhName="�������� 1-������� 2-֪ͨ 3��ѯ")
	private String tradeType;
	/** ֪ͨ���� 1-���� 2- ʧ�� 3-���� 4-չ�� */
		@EntityParaAnno(zhName="֪ͨ���� 1-���� 2- ʧ�� 3-���� 4-չ��")
	private String notifyType;
	/** ������ */
		@EntityParaAnno(zhName="������")
	private String respCode;
	/** ������Ϣ */
		@EntityParaAnno(zhName="������Ϣ")
	private String respInfo;
	/** ��ע */
		@EntityParaAnno(zhName="��ע")
	private String remark;
	/** ��Ⱦ���ۼ���� */
		@EntityParaAnno(zhName="��Ⱦ���ۼ����")
	private String quotaDetail;
	/** ����ʱ�� */
	@EntityParaAnno(zhName="����ʱ��")
	private Integer tradeCost;
	
	/** setter\getter���� */
	/** ϵͳ���ٺ�ESB */
	public void setSysTrackNo(String sysTrackNo) {
		this.sysTrackNo = sysTrackNo == null ? null : sysTrackNo.trim();
	}
	public String getSysTrackNo() {
		return this.sysTrackNo;
	}
	/** ���ѷ�ϵͳ���� */
	public void setTransInst(String transInst) {
		this.transInst = transInst == null ? null : transInst.trim();
	}
	public String getTransInst() {
		return this.transInst;
	}
	/** ����ϵͳ���� */
	public void setSendInst(String sendInst) {
		this.sendInst = sendInst == null ? null : sendInst.trim();
	}
	public String getSendInst() {
		return this.sendInst;
	}
	/** �ṩ��ϵͳ���� */
	public void setDestInst(String destInst) {
		this.destInst = destInst == null ? null : destInst.trim();
	}
	public String getDestInst() {
		return this.destInst;
	}
	/** ��ˮ�� */
	public void setCtlSeqNo(String ctlSeqNo) {
		this.ctlSeqNo = ctlSeqNo == null ? null : ctlSeqNo.trim();
	}
	public String getCtlSeqNo() {
		return this.ctlSeqNo;
	}
	/** �������� */
	public void setTransDate(String transDate) {
		this.transDate = transDate == null ? null : transDate.trim();
	}
	public String getTransDate() {
		return this.transDate;
	}
	/** ����ʱ�� */
	public void setTransTime(String transTime) {
		this.transTime = transTime == null ? null : transTime.trim();
	}
	public String getTransTime() {
		return this.transTime;
	}
	/** ������� */
	public void setAccDate(String accDate) {
		this.accDate = accDate == null ? null : accDate.trim();
	}
	public String getAccDate() {
		return this.accDate;
	}
	/** ҵ��ϵͳƽ̨�� */
	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}
	public String getSystemid() {
		return this.systemid;
	}
	/** �Ƿ��Զ��ſ�� */
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto == null ? null : isAuto.trim();
	}
	public String getIsAuto() {
		return this.isAuto;
	}
	/** ��ݱ��롢����id */
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode == null ? null : paperCode.trim();
	}
	public String getPaperCode() {
		return this.paperCode;
	}
	/** ����������� */
	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	/** ���� */
	public void setCcy(String ccy) {
		this.ccy = ccy == null ? null : ccy.trim();
	}
	public String getCcy() {
		return this.ccy;
	}
	/** �ſ����� */
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate == null ? null : loanDate.trim();
	}
	public String getLoanDate() {
		return this.loanDate;
	}
	/** �������� */
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate == null ? null : limitDate.trim();
	}
	public String getLimitDate() {
		return this.limitDate;
	}
	/** ���׻��� */
	public void setTransOrgan(String transOrgan) {
		this.transOrgan = transOrgan == null ? null : transOrgan.trim();
	}
	public String getTransOrgan() {
		return this.transOrgan;
	}
	/** ���ײ�Ʒ���� */
	public void setProductCode(String productCode) {
		this.productCode = productCode == null ? null : productCode.trim();
	}
	public String getProductCode() {
		return this.productCode;
	}
	/** �������� 1-������� 2-֪ͨ 3��ѯ */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType == null ? null : tradeType.trim();
	}
	public String getTradeType() {
		return this.tradeType;
	}
	/** ֪ͨ���� 1-���� 2- ʧ�� 3-���� 4-չ�� */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType == null ? null : notifyType.trim();
	}
	public String getNotifyType() {
		return this.notifyType;
	}
	/** ������ */
	public void setRespCode(String respCode) {
		this.respCode = respCode == null ? null : respCode.trim();
	}
	public String getRespCode() {
		return this.respCode;
	}
	/** ������Ϣ */
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo == null ? null : respInfo.trim();
	}
	public String getRespInfo() {
		return this.respInfo;
	}
	/** ��ע */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public String getRemark() {
		return this.remark;
	}
	/** ��Ⱦ���ۼ���� */
	public void setQuotaDetail(String quotaDetail) {
		this.quotaDetail = quotaDetail == null ? null : quotaDetail.trim();
	}
	public String getQuotaDetail() {
		return this.quotaDetail;
	}

	public Integer getTradeCost() {
		return tradeCost;
	}

	public void setTradeCost(Integer tradeCost) {
		this.tradeCost = tradeCost;
	}
}