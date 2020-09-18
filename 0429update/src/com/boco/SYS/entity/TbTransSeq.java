package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 流水表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbTransSeq extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 系统跟踪号ESB */
		@EntityParaAnno(zhName="系统跟踪号ESB")
	private String sysTrackNo;
	/** 消费方系统代码 */
		@EntityParaAnno(zhName="消费方系统代码")
	private String transInst;
	/** 请求方系统代码 */
		@EntityParaAnno(zhName="请求方系统代码")
	private String sendInst;
	/** 提供方系统代码 */
		@EntityParaAnno(zhName="提供方系统代码")
	private String destInst;
	/** 流水号 */
		@EntityParaAnno(zhName="流水号")
	private String ctlSeqNo;
	/** 交易日期 */
		@EntityParaAnno(zhName="交易日期")
	private String transDate;
	/** 交易时间 */
		@EntityParaAnno(zhName="交易时间")
	private String transTime;
	/** 会计日期 */
		@EntityParaAnno(zhName="会计日期")
	private String accDate;
	/** 业务系统平台号 */
		@EntityParaAnno(zhName="业务系统平台号")
	private String systemid;
	/** 是否自动放款交易 */
		@EntityParaAnno(zhName="是否自动放款交易")
	private String isAuto;
	/** 借据编码、交易id */
		@EntityParaAnno(zhName="借据编码、交易id")
	private String paperCode;
	/** 贷款金额、还款金额 */
		@EntityParaAnno(zhName="贷款金额、还款金额")
	private BigDecimal amt;
	/** 币种 */
		@EntityParaAnno(zhName="币种")
	private String ccy;
	/** 放款日期 */
		@EntityParaAnno(zhName="放款日期")
	private String loanDate;
	/** 到期日期 */
		@EntityParaAnno(zhName="到期日期")
	private String limitDate;
	/** 交易机构 */
		@EntityParaAnno(zhName="交易机构")
	private String transOrgan;
	/** 交易产品编码 */
		@EntityParaAnno(zhName="交易产品编码")
	private String productCode;
	/** 交易类型 1-额度申请 2-通知 3查询 */
		@EntityParaAnno(zhName="交易类型 1-额度申请 2-通知 3查询")
	private String tradeType;
	/** 通知类型 1-撤销 2- 失败 3-还款 4-展期 */
		@EntityParaAnno(zhName="通知类型 1-撤销 2- 失败 3-还款 4-展期")
	private String notifyType;
	/** 返回码 */
		@EntityParaAnno(zhName="返回码")
	private String respCode;
	/** 返回信息 */
		@EntityParaAnno(zhName="返回信息")
	private String respInfo;
	/** 备注 */
		@EntityParaAnno(zhName="备注")
	private String remark;
	/** 额度具体扣减情况 */
		@EntityParaAnno(zhName="额度具体扣减情况")
	private String quotaDetail;
	/** 交易时长 */
	@EntityParaAnno(zhName="交易时长")
	private Integer tradeCost;
	
	/** setter\getter方法 */
	/** 系统跟踪号ESB */
	public void setSysTrackNo(String sysTrackNo) {
		this.sysTrackNo = sysTrackNo == null ? null : sysTrackNo.trim();
	}
	public String getSysTrackNo() {
		return this.sysTrackNo;
	}
	/** 消费方系统代码 */
	public void setTransInst(String transInst) {
		this.transInst = transInst == null ? null : transInst.trim();
	}
	public String getTransInst() {
		return this.transInst;
	}
	/** 请求方系统代码 */
	public void setSendInst(String sendInst) {
		this.sendInst = sendInst == null ? null : sendInst.trim();
	}
	public String getSendInst() {
		return this.sendInst;
	}
	/** 提供方系统代码 */
	public void setDestInst(String destInst) {
		this.destInst = destInst == null ? null : destInst.trim();
	}
	public String getDestInst() {
		return this.destInst;
	}
	/** 流水号 */
	public void setCtlSeqNo(String ctlSeqNo) {
		this.ctlSeqNo = ctlSeqNo == null ? null : ctlSeqNo.trim();
	}
	public String getCtlSeqNo() {
		return this.ctlSeqNo;
	}
	/** 交易日期 */
	public void setTransDate(String transDate) {
		this.transDate = transDate == null ? null : transDate.trim();
	}
	public String getTransDate() {
		return this.transDate;
	}
	/** 交易时间 */
	public void setTransTime(String transTime) {
		this.transTime = transTime == null ? null : transTime.trim();
	}
	public String getTransTime() {
		return this.transTime;
	}
	/** 会计日期 */
	public void setAccDate(String accDate) {
		this.accDate = accDate == null ? null : accDate.trim();
	}
	public String getAccDate() {
		return this.accDate;
	}
	/** 业务系统平台号 */
	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}
	public String getSystemid() {
		return this.systemid;
	}
	/** 是否自动放款交易 */
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto == null ? null : isAuto.trim();
	}
	public String getIsAuto() {
		return this.isAuto;
	}
	/** 借据编码、交易id */
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode == null ? null : paperCode.trim();
	}
	public String getPaperCode() {
		return this.paperCode;
	}
	/** 贷款金额、还款金额 */
	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	/** 币种 */
	public void setCcy(String ccy) {
		this.ccy = ccy == null ? null : ccy.trim();
	}
	public String getCcy() {
		return this.ccy;
	}
	/** 放款日期 */
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate == null ? null : loanDate.trim();
	}
	public String getLoanDate() {
		return this.loanDate;
	}
	/** 到期日期 */
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate == null ? null : limitDate.trim();
	}
	public String getLimitDate() {
		return this.limitDate;
	}
	/** 交易机构 */
	public void setTransOrgan(String transOrgan) {
		this.transOrgan = transOrgan == null ? null : transOrgan.trim();
	}
	public String getTransOrgan() {
		return this.transOrgan;
	}
	/** 交易产品编码 */
	public void setProductCode(String productCode) {
		this.productCode = productCode == null ? null : productCode.trim();
	}
	public String getProductCode() {
		return this.productCode;
	}
	/** 交易类型 1-额度申请 2-通知 3查询 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType == null ? null : tradeType.trim();
	}
	public String getTradeType() {
		return this.tradeType;
	}
	/** 通知类型 1-撤销 2- 失败 3-还款 4-展期 */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType == null ? null : notifyType.trim();
	}
	public String getNotifyType() {
		return this.notifyType;
	}
	/** 返回码 */
	public void setRespCode(String respCode) {
		this.respCode = respCode == null ? null : respCode.trim();
	}
	public String getRespCode() {
		return this.respCode;
	}
	/** 返回信息 */
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo == null ? null : respInfo.trim();
	}
	public String getRespInfo() {
		return this.respInfo;
	}
	/** 备注 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	public String getRemark() {
		return this.remark;
	}
	/** 额度具体扣减情况 */
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