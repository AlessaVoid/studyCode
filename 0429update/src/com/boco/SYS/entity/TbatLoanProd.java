package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * TbatLoanProd实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbatLoanProd extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** dataDate */
		@EntityParaAnno(zhName="dataDate")
	private String dataDate;
	/** prodtId */
		@EntityParaAnno(zhName="prodtId")
	private String prodtId;
	/** instNo */
		@EntityParaAnno(zhName="instNo")
	private String instNo;
	/** totalBal */
		@EntityParaAnno(zhName="totalBal")
	private BigDecimal totalBal;
	
	/** setter\getter方法 */
	/** dataDate */
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate == null ? null : dataDate.trim();
	}
	public String getDataDate() {
		return this.dataDate;
	}
	/** prodtId */
	public void setProdtId(String prodtId) {
		this.prodtId = prodtId == null ? null : prodtId.trim();
	}
	public String getProdtId() {
		return this.prodtId;
	}
	/** instNo */
	public void setInstNo(String instNo) {
		this.instNo = instNo == null ? null : instNo.trim();
	}
	public String getInstNo() {
		return this.instNo;
	}
	/** totalBal */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}