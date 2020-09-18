package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 
 * 
 * 贷种组合明细表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCombDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 贷种明细主键标识 */
		@EntityParaAnno(zhName="贷种明细主键标识")
	private Long combDetailId;
	/** 贷种编码 */
		@EntityParaAnno(zhName="贷种编码")
	private String combCode;
	/** 贷种产品编码 */
		@EntityParaAnno(zhName="贷种产品编码")
	private String prodCode;
	/** 贷种明细状态 -1-表示当前贷种已经被删除 1-表示当前贷种组合不可用贷种产品 2-表示当前贷种组合可用贷种产品 */
		@EntityParaAnno(zhName="贷种明细状态 -1-表示当前贷种已经被删除 1-表示当前贷种组合不可用贷种产品 2-表示当前贷种组合可用贷种产品")
	private Integer status;
	/** createTime */
		@EntityParaAnno(zhName="createTime")
	private LocalDateTime createTime;
	/** 产品所属系统id */
		@EntityParaAnno(zhName="产品所属系统id")
	private String prodSysId;
	
	/** setter\getter方法 */
	/** 贷种明细主键标识 */
	public void setCombDetailId(Long combDetailId) {
		this.combDetailId = combDetailId;
	}
	public Long getCombDetailId() {
		return this.combDetailId;
	}
	/** 贷种编码 */
	public void setCombCode(String combCode) {
		this.combCode = combCode == null ? null : combCode.trim();
	}
	public String getCombCode() {
		return this.combCode;
	}
	/** 贷种产品编码 */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode == null ? null : prodCode.trim();
	}
	public String getProdCode() {
		return this.prodCode;
	}
	/** 贷种明细状态 -1-表示当前贷种已经被删除 1-表示当前贷种组合不可用贷种产品 2-表示当前贷种组合可用贷种产品 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return this.status;
	}
//	/** createTime
//	 * @param createTime*/
//	public void setCreateTime(LocalDateTime createTime) {
//		this.createTime = createTime == null ? null : createTime.trim();
//	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getCreateTime() {
		return this.createTime;
	}
	/** 产品所属系统id */
	public void setProdSysId(String prodSysId) {
		this.prodSysId = prodSysId == null ? null : prodSysId.trim();
	}
	public String getProdSysId() {
		return this.prodSysId;
	}
}