package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * @Description:FdBusinessDate实体类
 * @param:
 * @throws
 * @author:Hypo
 * @date 2018年9月21日 下午3:20:46
 */
public class FdBusinessDate extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** busiTypeCode */
		@EntityParaAnno(zhName="busiTypeCode")
	private String busiTypeCode;
	/** curDate */
		@EntityParaAnno(zhName="curDate")
	private String curDate;
	/** lastDate */
		@EntityParaAnno(zhName="lastDate")
	private String lastDate;
	/** nextWorkDate */
		@EntityParaAnno(zhName="nextWorkDate")
	private String nextWorkDate;
	/** busiFlag */
		@EntityParaAnno(zhName="busiFlag")
	private String busiFlag;
	/** monthFlag */
		@EntityParaAnno(zhName="monthFlag")
	private String monthFlag;
	/** lastModOprTellerNo */
		@EntityParaAnno(zhName="lastModOprTellerNo")
	private String lastModOprTellerNo;
	/** lastModBusiDate */
		@EntityParaAnno(zhName="lastModBusiDate")
	private String lastModBusiDate;
	/** lastModLocalDate */
		@EntityParaAnno(zhName="lastModLocalDate")
	private String lastModLocalDate;
	/** lastModLocalTime */
		@EntityParaAnno(zhName="lastModLocalTime")
	private String lastModLocalTime;
	/** subBusiType */
		@EntityParaAnno(zhName="subBusiType")
	private String subBusiType;
	
	/** setter\getter方法 */
	/** busiTypeCode */
	public void setBusiTypeCode(String busiTypeCode) {
		this.busiTypeCode = busiTypeCode == null ? null : busiTypeCode.trim();
	}
	public String getBusiTypeCode() {
		return this.busiTypeCode;
	}
	/** curDate */
	public void setCurDate(String curDate) {
		this.curDate = curDate == null ? null : curDate.trim();
	}
	public String getCurDate() {
		return this.curDate;
	}
	/** lastDate */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate == null ? null : lastDate.trim();
	}
	public String getLastDate() {
		return this.lastDate;
	}
	/** nextWorkDate */
	public void setNextWorkDate(String nextWorkDate) {
		this.nextWorkDate = nextWorkDate == null ? null : nextWorkDate.trim();
	}
	public String getNextWorkDate() {
		return this.nextWorkDate;
	}
	/** busiFlag */
	public void setBusiFlag(String busiFlag) {
		this.busiFlag = busiFlag == null ? null : busiFlag.trim();
	}
	public String getBusiFlag() {
		return this.busiFlag;
	}
	/** monthFlag */
	public void setMonthFlag(String monthFlag) {
		this.monthFlag = monthFlag == null ? null : monthFlag.trim();
	}
	public String getMonthFlag() {
		return this.monthFlag;
	}
	/** lastModOprTellerNo */
	public void setLastModOprTellerNo(String lastModOprTellerNo) {
		this.lastModOprTellerNo = lastModOprTellerNo == null ? null : lastModOprTellerNo.trim();
	}
	public String getLastModOprTellerNo() {
		return this.lastModOprTellerNo;
	}
	/** lastModBusiDate */
	public void setLastModBusiDate(String lastModBusiDate) {
		this.lastModBusiDate = lastModBusiDate == null ? null : lastModBusiDate.trim();
	}
	public String getLastModBusiDate() {
		return this.lastModBusiDate;
	}
	/** lastModLocalDate */
	public void setLastModLocalDate(String lastModLocalDate) {
		this.lastModLocalDate = lastModLocalDate == null ? null : lastModLocalDate.trim();
	}
	public String getLastModLocalDate() {
		return this.lastModLocalDate;
	}
	/** lastModLocalTime */
	public void setLastModLocalTime(String lastModLocalTime) {
		this.lastModLocalTime = lastModLocalTime == null ? null : lastModLocalTime.trim();
	}
	public String getLastModLocalTime() {
		return this.lastModLocalTime;
	}
	/** subBusiType */
	public void setSubBusiType(String subBusiType) {
		this.subBusiType = subBusiType == null ? null : subBusiType.trim();
	}
	public String getSubBusiType() {
		return this.subBusiType;
	}
}