package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 消息列表||消息列表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class WebMsg extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 消息代码||消息代码 */
		@EntityParaAnno(zhName="消息代码")
	private java.lang.String msgNo;
	/** 交易代码||交易代码 */
		@EntityParaAnno(zhName="交易代码")
	private java.lang.String webTradeCode;
	/** 交易描述||交易描述 */
		@EntityParaAnno(zhName="交易描述")
	private java.lang.String operDescribe;
	/** 交易操作类型||交易操作类型 00-  简单复核 01-预研审批 02-预研驳回 03-设计审批 04-设计驳回 05-档期审批 06-档期驳回 07-资金划拨审批 08-资金划拨驳回 */
		@EntityParaAnno(zhName="交易操作类型")
	private java.lang.String msgType;
	/** 交易对象编码||交易对象编码 */
		@EntityParaAnno(zhName="交易对象编码")
	private java.lang.String operNo;
	/** 操作员姓名||操作员姓名 */
		@EntityParaAnno(zhName="操作员姓名")
	private java.lang.String operName;
	/** 处理详细页面||处理详细页面 */
		@EntityParaAnno(zhName="处理详细页面")
	private java.lang.String msgUrl;
	/** 申请操作员代码||申请操作员代码 */
		@EntityParaAnno(zhName="申请操作员代码")
	private java.lang.String appUser;
	/** 申请日期||申请日期 */
		@EntityParaAnno(zhName="申请日期")
	private java.lang.String appDate;
	/** 申请时间||申请时间 */
		@EntityParaAnno(zhName="申请时间")
	private java.lang.String appTime;
	/** 申请操作员姓名||申请操作员姓名 */
		@EntityParaAnno(zhName="申请操作员姓名")
	private java.lang.String appOperName;
	/** 申请操作员角色名称||申请操作员角色名称 */
		@EntityParaAnno(zhName="申请操作员角色名称")
	private java.lang.String appRoleName;
	/** 申请操作员所属机构代码||申请操作员所属机构代码 */
		@EntityParaAnno(zhName="申请操作员所属机构代码")
	private java.lang.String appOrganCode;
	/** 申请操作员所属机构名称||申请操作员所属机构名称 */
		@EntityParaAnno(zhName="申请操作员所属机构名称")
	private java.lang.String appOrganName;
	/** 申请备注||申请备注 */
		@EntityParaAnno(zhName="申请备注")
	private java.lang.String appRemark;
	/** 复核操作员代码||复核操作员代码 */
		@EntityParaAnno(zhName="复核操作员代码")
	private java.lang.String repUserCode;
	/** 复核操作员姓名||复核操作员姓名 */
		@EntityParaAnno(zhName="复核操作员姓名")
	private java.lang.String repUserName;
	/** 复核操作员角色名称||复核操作员角色名称 */
		@EntityParaAnno(zhName="复核操作员角色名称")
	private java.lang.String repRoleName;
	/** 复核操作员所属机构代码||复核操作员所属机构代码 */
		@EntityParaAnno(zhName="复核操作员所属机构代码")
	private java.lang.String repUserOrganCode;
	/** 复核操作员所属机构名称||复核操作员所属机构名称 */
		@EntityParaAnno(zhName="复核操作员所属机构名称")
	private java.lang.String repUserOrganName;
	/** 审核日期||审核日期 */
		@EntityParaAnno(zhName="审核日期")
	private java.lang.String repDate;
	/** 复核时间||复核时间 */
		@EntityParaAnno(zhName="复核时间")
	private java.lang.String repTime;
	/** 复核备注||复核备注 */
		@EntityParaAnno(zhName="复核备注")
	private java.lang.String repRemark;
	/** 复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请 */
		@EntityParaAnno(zhName="复核状态")
	private java.lang.String webMsgStatus;
	
	/** setter\getter方法 */
	/** 消息代码||消息代码 */
	public void setMsgNo(java.lang.String msgNo) {
		this.msgNo = msgNo == null ? null : msgNo.trim();
	}
	public java.lang.String getMsgNo() {
		return this.msgNo;
	}
	/** 交易代码||交易代码 */
	public void setWebTradeCode(java.lang.String webTradeCode) {
		this.webTradeCode = webTradeCode == null ? null : webTradeCode.trim();
	}
	public java.lang.String getWebTradeCode() {
		return this.webTradeCode;
	}
	/** 交易描述||交易描述 */
	public void setOperDescribe(java.lang.String operDescribe) {
		this.operDescribe = operDescribe == null ? null : operDescribe.trim();
	}
	public java.lang.String getOperDescribe() {
		return this.operDescribe;
	}
	/** 交易操作类型||交易操作类型 00-  简单复核 01-预研审批 02-预研驳回 03-设计审批 04-设计驳回 05-档期审批 06-档期驳回 07-资金划拨审批 08-资金划拨驳回 */
	public void setMsgType(java.lang.String msgType) {
		this.msgType = msgType == null ? null : msgType.trim();
	}
	public java.lang.String getMsgType() {
		return this.msgType;
	}
	/** 交易对象编码||交易对象编码 */
	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo == null ? null : operNo.trim();
	}
	public java.lang.String getOperNo() {
		return this.operNo;
	}
	/** 操作员姓名||操作员姓名 */
	public void setOperName(java.lang.String operName) {
		this.operName = operName == null ? null : operName.trim();
	}
	public java.lang.String getOperName() {
		return this.operName;
	}
	/** 处理详细页面||处理详细页面 */
	public void setMsgUrl(java.lang.String msgUrl) {
		this.msgUrl = msgUrl == null ? null : msgUrl.trim();
	}
	public java.lang.String getMsgUrl() {
		return this.msgUrl;
	}
	/** 申请操作员代码||申请操作员代码 */
	public void setAppUser(java.lang.String appUser) {
		this.appUser = appUser == null ? null : appUser.trim();
	}
	public java.lang.String getAppUser() {
		return this.appUser;
	}
	/** 申请日期||申请日期 */
	public void setAppDate(java.lang.String appDate) {
		this.appDate = appDate == null ? null : appDate.trim();
	}
	public java.lang.String getAppDate() {
		return this.appDate;
	}
	/** 申请时间||申请时间 */
	public void setAppTime(java.lang.String appTime) {
		this.appTime = appTime == null ? null : appTime.trim();
	}
	public java.lang.String getAppTime() {
		return this.appTime;
	}
	/** 申请操作员姓名||申请操作员姓名 */
	public void setAppOperName(java.lang.String appOperName) {
		this.appOperName = appOperName == null ? null : appOperName.trim();
	}
	public java.lang.String getAppOperName() {
		return this.appOperName;
	}
	/** 申请操作员角色名称||申请操作员角色名称 */
	public void setAppRoleName(java.lang.String appRoleName) {
		this.appRoleName = appRoleName == null ? null : appRoleName.trim();
	}
	public java.lang.String getAppRoleName() {
		return this.appRoleName;
	}
	/** 申请操作员所属机构代码||申请操作员所属机构代码 */
	public void setAppOrganCode(java.lang.String appOrganCode) {
		this.appOrganCode = appOrganCode == null ? null : appOrganCode.trim();
	}
	public java.lang.String getAppOrganCode() {
		return this.appOrganCode;
	}
	/** 申请操作员所属机构名称||申请操作员所属机构名称 */
	public void setAppOrganName(java.lang.String appOrganName) {
		this.appOrganName = appOrganName == null ? null : appOrganName.trim();
	}
	public java.lang.String getAppOrganName() {
		return this.appOrganName;
	}
	/** 申请备注||申请备注 */
	public void setAppRemark(java.lang.String appRemark) {
		this.appRemark = appRemark == null ? null : appRemark.trim();
	}
	public java.lang.String getAppRemark() {
		return this.appRemark;
	}
	/** 复核操作员代码||复核操作员代码 */
	public void setRepUserCode(java.lang.String repUserCode) {
		this.repUserCode = repUserCode == null ? null : repUserCode.trim();
	}
	public java.lang.String getRepUserCode() {
		return this.repUserCode;
	}
	/** 复核操作员姓名||复核操作员姓名 */
	public void setRepUserName(java.lang.String repUserName) {
		this.repUserName = repUserName == null ? null : repUserName.trim();
	}
	public java.lang.String getRepUserName() {
		return this.repUserName;
	}
	/** 复核操作员角色名称||复核操作员角色名称 */
	public void setRepRoleName(java.lang.String repRoleName) {
		this.repRoleName = repRoleName == null ? null : repRoleName.trim();
	}
	public java.lang.String getRepRoleName() {
		return this.repRoleName;
	}
	/** 复核操作员所属机构代码||复核操作员所属机构代码 */
	public void setRepUserOrganCode(java.lang.String repUserOrganCode) {
		this.repUserOrganCode = repUserOrganCode == null ? null : repUserOrganCode.trim();
	}
	public java.lang.String getRepUserOrganCode() {
		return this.repUserOrganCode;
	}
	/** 复核操作员所属机构名称||复核操作员所属机构名称 */
	public void setRepUserOrganName(java.lang.String repUserOrganName) {
		this.repUserOrganName = repUserOrganName == null ? null : repUserOrganName.trim();
	}
	public java.lang.String getRepUserOrganName() {
		return this.repUserOrganName;
	}
	/** 审核日期||审核日期 */
	public void setRepDate(java.lang.String repDate) {
		this.repDate = repDate == null ? null : repDate.trim();
	}
	public java.lang.String getRepDate() {
		return this.repDate;
	}
	/** 复核时间||复核时间 */
	public void setRepTime(java.lang.String repTime) {
		this.repTime = repTime == null ? null : repTime.trim();
	}
	public java.lang.String getRepTime() {
		return this.repTime;
	}
	/** 复核备注||复核备注 */
	public void setRepRemark(java.lang.String repRemark) {
		this.repRemark = repRemark == null ? null : repRemark.trim();
	}
	public java.lang.String getRepRemark() {
		return this.repRemark;
	}
	/** 复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请 */
	public void setWebMsgStatus(java.lang.String webMsgStatus) {
		this.webMsgStatus = webMsgStatus == null ? null : webMsgStatus.trim();
	}
	public java.lang.String getWebMsgStatus() {
		return this.webMsgStatus;
	}
}