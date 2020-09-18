package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 菜单表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebMenuInfo extends BaseEntity implements java.io.Serializable {
	/** 属性 */
	/** 主键 */
	private java.lang.String id;
	/** 菜单编号 */
	private java.lang.String menuNo;
	/** 上级菜单编号 */
	private java.lang.String upMenuNo;
	/** 菜单排序编号 */
	private java.lang.String orderNo;
	/** 是否为叶节点 0-否；1-是 */
	private java.lang.String isParent;
	/** 菜单URL */
	private java.lang.String menuUrl;
	/** 菜单名称 */
	private java.lang.String menuName;
	/** 菜单图标 */
	private java.lang.String menuIcon;
	/** 菜单状态 1-启用,2-禁用 */
	private java.lang.String menuStatus;
	/** 菜单类型 1-菜单,2-按钮,3-功能 */
	private java.lang.String menuType;
	/** 最后修改日期 */
	private java.lang.String latestModifyDate;
	/** 最后修改时间 */
	private java.lang.String latestModifyTime;
	/** 最后操作员 */
	private java.lang.String latestOperCode;
	/** 上级菜单名称 */
	private java.lang.String upMenuName;
	
	/** setter\getter方法 */
	/** 主键 */
	public void setId(java.lang.String id) {
		this.id = id == null ? null : id.trim();
	}
	public java.lang.String getId() {
		return this.id;
	}
	/** 菜单编号 */
	public void setMenuNo(java.lang.String menuNo) {
		this.menuNo = menuNo == null ? null : menuNo.trim();
	}
	public java.lang.String getMenuNo() {
		return this.menuNo;
	}
	/** 上级菜单编号 */
	public void setUpMenuNo(java.lang.String upMenuNo) {
		this.upMenuNo = upMenuNo == null ? null : upMenuNo.trim();
	}
	public java.lang.String getUpMenuNo() {
		return this.upMenuNo;
	}
	/** 菜单排序编号 */
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	/** 是否为叶节点 0-否；1-是 */
	public void setIsParent(java.lang.String isParent) {
		this.isParent = isParent == null ? null : isParent.trim();
	}
	public java.lang.String getIsParent() {
		return this.isParent;
	}
	/** 菜单URL */
	public void setMenuUrl(java.lang.String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}
	public java.lang.String getMenuUrl() {
		return this.menuUrl;
	}
	/** 菜单名称 */
	public void setMenuName(java.lang.String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}
	public java.lang.String getMenuName() {
		return this.menuName;
	}
	/** 菜单图标 */
	public void setMenuIcon(java.lang.String menuIcon) {
		this.menuIcon = menuIcon == null ? null : menuIcon.trim();
	}
	public java.lang.String getMenuIcon() {
		return this.menuIcon;
	}
	/** 菜单状态 1-启用,2-禁用 */
	public void setMenuStatus(java.lang.String menuStatus) {
		this.menuStatus = menuStatus == null ? null : menuStatus.trim();
	}
	public java.lang.String getMenuStatus() {
		return this.menuStatus;
	}
	/** 菜单类型 1-菜单,2-按钮,3-功能 */
	public void setMenuType(java.lang.String menuType) {
		this.menuType = menuType == null ? null : menuType.trim();
	}
	public java.lang.String getMenuType() {
		return this.menuType;
	}
	/** 最后修改日期 */
	public void setLatestModifyDate(java.lang.String latestModifyDate) {
		this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
	}
	public java.lang.String getLatestModifyDate() {
		return this.latestModifyDate;
	}
	/** 最后修改时间 */
	public void setLatestModifyTime(java.lang.String latestModifyTime) {
		this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
	}
	public java.lang.String getLatestModifyTime() {
		return this.latestModifyTime;
	}
	/** 最后操作员 */
	public void setLatestOperCode(java.lang.String latestOperCode) {
		this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
	}
	public java.lang.String getLatestOperCode() {
		return this.latestOperCode;
	}
	/**上级菜单名称*/
	public java.lang.String getUpMenuName() {
		return upMenuName;
	}
	/**上级菜单名称*/
	public void setUpMenuName(java.lang.String upMenuName) {
		this.upMenuName = upMenuName;
	}
}