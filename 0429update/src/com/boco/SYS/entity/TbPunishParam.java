package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 *
 *
 * 罚息参数表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbPunishParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** id */
	@EntityParaAnno(zhName="id")
	private java.lang.Integer ppId;
	/** 罚息名称 */
	@EntityParaAnno(zhName="罚息名称")
	private java.lang.String ppName;
	/** 机构号 */
	@EntityParaAnno(zhName="机构号")
	private java.lang.String ppOrgan;
	/** 罚息类型：1 季末月中,2季末月末,4非季末月中,8非季末月末 */
	@EntityParaAnno(zhName="罚息类型：1 季末月中,2季末月末,4非季末月中,8非季末月末")
	private java.lang.Integer type;
	/** 区间最小值 */
	@EntityParaAnno(zhName="区间最小值")
	BigDecimal minnum;
	/** 区间最大值 */
	@EntityParaAnno(zhName="区间最大值")
	BigDecimal maxnum;
	/** 区间对应利息 */
	@EntityParaAnno(zhName="区间对应利息")
	BigDecimal interest;
	/** 状态 */
	@EntityParaAnno(zhName="状态")
	private java.lang.Integer state;
	/** 创建时间 */
	@EntityParaAnno(zhName="创建时间")
	private java.lang.String createtime;
	/** 更新时间 */
	@EntityParaAnno(zhName="更新时间")
	private java.lang.String updatetime;
	/** 创建用户id */
	@EntityParaAnno(zhName="创建用户id")
	private java.lang.String createuserid;
	/** 更新用户id */
	@EntityParaAnno(zhName="更新用户id")
	private java.lang.String updateuserid;
	/** 收取类型 */
	@EntityParaAnno(zhName="收取类型")
	private java.lang.Integer collecttype;
	/** 罚息类型 */
	@EntityParaAnno(zhName="罚息类型")
	private java.lang.Integer ppType;

	/** setter\getter方法 */
	/** id */
	public void setPpId(java.lang.Integer ppId) {
		this.ppId = ppId;
	}
	public java.lang.Integer getPpId() {
		return this.ppId;
	}
	/** 罚息名称 */
	public void setPpName(java.lang.String ppName) {
		this.ppName = ppName == null ? null : ppName.trim();
	}
	public java.lang.String getPpName() {
		return this.ppName;
	}
	/** 机构号 */
	public void setPpOrgan(java.lang.String ppOrgan) {
		this.ppOrgan = ppOrgan == null ? null : ppOrgan.trim();
	}
	public java.lang.String getPpOrgan() {
		return this.ppOrgan;
	}
	/** 罚息类型：1 季末月中,2季末月末,4非季末月中,8非季末月末 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	public java.lang.Integer getType() {
		return this.type;
	}
	/** 区间最小值 */
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

	/** 状态 */
	public void setState(java.lang.Integer state) {
		this.state = state;
	}
	public java.lang.Integer getState() {
		return this.state;
	}
	/** 创建时间 */
	public void setCreatetime(java.lang.String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}
	public java.lang.String getCreatetime() {
		return this.createtime;
	}
	/** 更新时间 */
	public void setUpdatetime(java.lang.String updatetime) {
		this.updatetime = updatetime == null ? null : updatetime.trim();
	}
	public java.lang.String getUpdatetime() {
		return this.updatetime;
	}
	/** 创建用户id */
	public void setCreateuserid(java.lang.String createuserid) {
		this.createuserid = createuserid == null ? null : createuserid.trim();
	}
	public java.lang.String getCreateuserid() {
		return this.createuserid;
	}
	/** 更新用户id */
	public void setUpdateuserid(java.lang.String updateuserid) {
		this.updateuserid = updateuserid == null ? null : updateuserid.trim();
	}
	public java.lang.String getUpdateuserid() {
		return this.updateuserid;
	}
	/** 收取类型 */
	public void setCollecttype(java.lang.Integer collecttype) {
		this.collecttype = collecttype;
	}
	public java.lang.Integer getCollecttype() {
		return this.collecttype;
	}
	/** 罚息类型 */
	public void setPpType(java.lang.Integer ppType) {
		this.ppType = ppType;
	}
	public java.lang.Integer getPpType() {
		return this.ppType;
	}
}