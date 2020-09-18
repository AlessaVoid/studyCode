package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * WebReviewMain实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebReviewMain extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 复核代码 */
	private java.lang.String appNo;
	/** 交易码 */
	private java.lang.String tradeCode;
	/** 交易描述 */
	private java.lang.String operDescribe;
	/** 交易操作类型: 0- 新增 1- 修改2- 删 */
	private java.lang.String appType;
	/** 交易对象编码 */
	private java.lang.String operNo;
	/** 交易对象名称 */
	private java.lang.String operName;
	/** 复核详细页面 */
	private java.lang.String appUrl;
	/** 重新编辑地址 */
	private java.lang.String reeditUrl;
	/** 申请操作员代码 */
	private java.lang.String appUser;
	/** 申请日期 */
	private java.lang.String appDate;
	/** 申请时间 */
	private java.lang.String appTime;
	/** 申请操作员姓名 */
	private java.lang.String appOperName;
	/** 申请操作员角色名称 */
	private java.lang.String appRoleName;
	/** 申请操作员所属机构代码 */
	private java.lang.String appOrganCode;
	/** 申请操作员所属机构名称 */
	private java.lang.String appOrganName;
	/** 申请备注 */
	private java.lang.String appRemark;
	/** 复核操作员代码 */
	private java.lang.String repUserCode;
	/** 复核操作员姓名 */
	private java.lang.String repUserName;
	/** 复核操作员角色名称 */
	private java.lang.String repRoleName;
	/** 复核操作员所属机构代码 */
	private java.lang.String repUserOrganCode;
	/** 复核操作员所属机构名称 */
	private java.lang.String repUserOrganName;
	/** 复核日期 */
	private java.lang.String repDate;
	/** 复核时间 */
	private java.lang.String repTime;
	/** 复核备注 */
	private java.lang.String repRemark;
	/** 复核状态: 1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请  */
	private java.lang.String repStatus;
	/** 撤销标识，用于查询，1-撤销,其他-正常，待复核列表只取非撤销状态的申请  */
	private java.lang.String revocationFlag;
	/** 查询标识，该属性不为空时，默认查询某个用户申请和复核的列表，其他情况只查询该用户申请或复核的列表  */
	private java.lang.String defaultList;
	/** 修改申请时，表示修改前的数据 */
	private java.lang.String oldData;
	/** 申请开始时间 */
	private java.lang.String appBeginDate;
	/** 申请结束时间 */
	private java.lang.String appEndDate;
	
	/** setter\getter方法 */
	/** 复核代码 */
	public void setAppNo(java.lang.String appNo) {
		this.appNo = appNo == null ? null : appNo.trim();
	}
	public java.lang.String getAppNo() {
		return this.appNo;
	}
	/** 交易码 */
	public void setTradeCode(java.lang.String tradeCode) {
		this.tradeCode = tradeCode == null ? null : tradeCode.trim();
	}
	public java.lang.String getTradeCode() {
		return this.tradeCode;
	}
	/** 交易描述 */
	public void setOperDescribe(java.lang.String operDescribe) {
		this.operDescribe = operDescribe == null ? null : operDescribe.trim();
	}
	public java.lang.String getOperDescribe() {
		return this.operDescribe;
	}
	/** 交易操作类型: 0- 新增 1- 修改2- 删 */
	public void setAppType(java.lang.String appType) {
		this.appType = appType == null ? null : appType.trim();
	}
	public java.lang.String getAppType() {
		return this.appType;
	}
	/** 交易对象编码 */
	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo == null ? null : operNo.trim();
	}
	public java.lang.String getOperNo() {
		return this.operNo;
	}
	/** 交易对象名称 */
	public void setOperName(java.lang.String operName) {
		this.operName = operName == null ? null : operName.trim();
	}
	public java.lang.String getOperName() {
		return this.operName;
	}
	/** 复核详细页面 */
	public void setAppUrl(java.lang.String appUrl) {
		this.appUrl = appUrl == null ? null : appUrl.trim();
	}
	public java.lang.String getAppUrl() {
		return this.appUrl;
	}
	/** 重新编辑地址 */
	public void setReeditUrl(java.lang.String reeditUrl) {
		this.reeditUrl = reeditUrl == null ? null : reeditUrl.trim();
	}
	public java.lang.String getReeditUrl() {
		return this.reeditUrl;
	}
	/** 申请操作员代码 */
	public void setAppUser(java.lang.String appUser) {
		this.appUser = appUser == null ? null : appUser.trim();
	}
	public java.lang.String getAppUser() {
		return this.appUser;
	}
	/** 申请日期 */
	public void setAppDate(java.lang.String appDate) {
		this.appDate = appDate == null ? null : appDate.trim();
	}
	public java.lang.String getAppDate() {
		return this.appDate;
	}
	/** 申请时间 */
	public void setAppTime(java.lang.String appTime) {
		this.appTime = appTime == null ? null : appTime.trim();
	}
	public java.lang.String getAppTime() {
		return this.appTime;
	}
	/** 申请操作员姓名 */
	public void setAppOperName(java.lang.String appOperName) {
		this.appOperName = appOperName == null ? null : appOperName.trim();
	}
	public java.lang.String getAppOperName() {
		return this.appOperName;
	}
	/** 申请操作员角色名称 */
	public void setAppRoleName(java.lang.String appRoleName) {
		this.appRoleName = appRoleName == null ? null : appRoleName.trim();
	}
	public java.lang.String getAppRoleName() {
		return this.appRoleName;
	}
	/** 申请操作员所属机构代码 */
	public void setAppOrganCode(java.lang.String appOrganCode) {
		this.appOrganCode = appOrganCode == null ? null : appOrganCode.trim();
	}
	public java.lang.String getAppOrganCode() {
		return this.appOrganCode;
	}
	/** 申请操作员所属机构名称 */
	public void setAppOrganName(java.lang.String appOrganName) {
		this.appOrganName = appOrganName == null ? null : appOrganName.trim();
	}
	public java.lang.String getAppOrganName() {
		return this.appOrganName;
	}
	/** 申请备注 */
	public void setAppRemark(java.lang.String appRemark) {
		this.appRemark = appRemark == null ? null : appRemark.trim();
	}
	public java.lang.String getAppRemark() {
		return this.appRemark;
	}
	/** 复核操作员代码 */
	public void setRepUserCode(java.lang.String repUserCode) {
		this.repUserCode = repUserCode == null ? null : repUserCode.trim();
	}
	public java.lang.String getRepUserCode() {
		return this.repUserCode;
	}
	/** 复核操作员姓名 */
	public void setRepUserName(java.lang.String repUserName) {
		this.repUserName = repUserName == null ? null : repUserName.trim();
	}
	public java.lang.String getRepUserName() {
		return this.repUserName;
	}
	/** 复核操作员角色名称 */
	public void setRepRoleName(java.lang.String repRoleName) {
		this.repRoleName = repRoleName == null ? null : repRoleName.trim();
	}
	public java.lang.String getRepRoleName() {
		return this.repRoleName;
	}
	/** 复核操作员所属机构代码 */
	public void setRepUserOrganCode(java.lang.String repUserOrganCode) {
		this.repUserOrganCode = repUserOrganCode == null ? null : repUserOrganCode.trim();
	}
	public java.lang.String getRepUserOrganCode() {
		return this.repUserOrganCode;
	}
	/** 复核操作员所属机构名称 */
	public void setRepUserOrganName(java.lang.String repUserOrganName) {
		this.repUserOrganName = repUserOrganName == null ? null : repUserOrganName.trim();
	}
	public java.lang.String getRepUserOrganName() {
		return this.repUserOrganName;
	}
	/** 复核日期 */
	public void setRepDate(java.lang.String repDate) {
		this.repDate = repDate == null ? null : repDate.trim();
	}
	public java.lang.String getRepDate() {
		return this.repDate;
	}
	/** 复核时间 */
	public void setRepTime(java.lang.String repTime) {
		this.repTime = repTime == null ? null : repTime.trim();
	}
	public java.lang.String getRepTime() {
		return this.repTime;
	}
	/** 复核备注 */
	public void setRepRemark(java.lang.String repRemark) {
		this.repRemark = repRemark == null ? null : repRemark.trim();
	}
	public java.lang.String getRepRemark() {
		return this.repRemark;
	}
	/** 复核状态: 1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请  */
	public void setRepStatus(java.lang.String repStatus) {
		this.repStatus = repStatus == null ? null : repStatus.trim();
	}
	public java.lang.String getRepStatus() {
		return this.repStatus;
	}
	/** 撤销标识，用于查询，1-撤销,其他-正常，待复核列表只取非撤销状态的申请  */
	public void setRevocationFlag(java.lang.String revocationFlag) {
		this.revocationFlag = revocationFlag;
	}
	public java.lang.String getRevocationFlag() {
		return revocationFlag;
	}
	/**查询标识，该属性不为空时，默认查询某个用户申请和复核的列表，其他情况只查询该用户申请或复核的列表*/
	public void setDefaultList(java.lang.String defaultList) {
		this.defaultList = defaultList;
	}
	public java.lang.String getDefaultList() {
		return defaultList;
	}
	/** 修改申请时，表示修改前的数据 */
	public void setOldData(java.lang.String value) {
		this.oldData = value;
	}
	/** 修改申请时，表示修改前的数据 */
	public java.lang.String getOldData() {
		return this.oldData;
	}
	/**申请开始时间*/
	public java.lang.String getAppBeginDate() {
		return appBeginDate;
	}
	/**申请开始时间*/
	public void setAppBeginDate(java.lang.String appBeginDate) {
		this.appBeginDate = appBeginDate;
	}
	/**申请结束时间*/
	public java.lang.String getAppEndDate() {
		return appEndDate;
	}
	/**申请结束时间*/
	public void setAppEndDate(java.lang.String appEndDate) {
		this.appEndDate = appEndDate;
	}
}