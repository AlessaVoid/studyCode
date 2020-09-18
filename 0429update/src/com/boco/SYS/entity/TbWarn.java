package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 预警线表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbWarn extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 预警线id */
		@EntityParaAnno(zhName="预警线id")
	private Integer warnId;
	/** 预警线名称 */
		@EntityParaAnno(zhName="预警线名称")
	private String warnName;
	/** 预警机构 */
		@EntityParaAnno(zhName="预警机构")
	private Integer warnOrgan;
	/** 预警贷种 */
		@EntityParaAnno(zhName="预警贷种")
	private String warnComb;
	/** 预警线类型 */
		@EntityParaAnno(zhName="预警线类型")
	private Integer warnType;
	/** 一级预警线 */
		@EntityParaAnno(zhName="一级预警线")
	private Double warnOneLine;
	/** 二级预警线 */
		@EntityParaAnno(zhName="二级预警线")
	private Double warnTwoLine;
	/** 三级预警线 */
		@EntityParaAnno(zhName="三级预警线")
	private Double warnThreeLine;
	/** 四级预警线 */
		@EntityParaAnno(zhName="四级预警线")
	private Double warnFourLine;
	/** 五级预警线 */
		@EntityParaAnno(zhName="五级预警线")
	private Double warnFiveLine;
	/** 预警线创建人员 */
		@EntityParaAnno(zhName="预警线创建人员")
	private String warnCreateOper;
	/** 预警线更新人员 */
		@EntityParaAnno(zhName="预警线更新人员")
	private String warnUpdateOper;
	/** 预警线创建时间 */
		@EntityParaAnno(zhName="预警线创建时间")
	private String warnCreateTime;
	/** 预警线更新时间 */
		@EntityParaAnno(zhName="预警线更新时间")
	private String warnUpdateTime;
	
	/** setter\getter方法 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getWarnId() {
		return warnId;
	}

	public void setWarnId(Integer warnId) {
		this.warnId = warnId;
	}

	public String getWarnName() {
		return warnName;
	}

	public void setWarnName(String warnName) {
		this.warnName = warnName;
	}

	public Integer getWarnOrgan() {
		return warnOrgan;
	}

	public void setWarnOrgan(Integer warnOrgan) {
		this.warnOrgan = warnOrgan;
	}

	public String getWarnComb() {
		return warnComb;
	}

	public void setWarnComb(String warnComb) {
		this.warnComb = warnComb;
	}

	public Integer getWarnType() {
		return warnType;
	}

	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}

	public Double getWarnOneLine() {
		return warnOneLine;
	}

	public void setWarnOneLine(Double warnOneLine) {
		this.warnOneLine = warnOneLine;
	}

	public Double getWarnTwoLine() {
		return warnTwoLine;
	}

	public void setWarnTwoLine(Double warnTwoLine) {
		this.warnTwoLine = warnTwoLine;
	}

	public Double getWarnThreeLine() {
		return warnThreeLine;
	}

	public void setWarnThreeLine(Double warnThreeLine) {
		this.warnThreeLine = warnThreeLine;
	}

	public Double getWarnFourLine() {
		return warnFourLine;
	}

	public void setWarnFourLine(Double warnFourLine) {
		this.warnFourLine = warnFourLine;
	}

	public Double getWarnFiveLine() {
		return warnFiveLine;
	}

	public void setWarnFiveLine(Double warnFiveLine) {
		this.warnFiveLine = warnFiveLine;
	}

	public String getWarnCreateOper() {
		return warnCreateOper;
	}

	public void setWarnCreateOper(String warnCreateOper) {
		this.warnCreateOper = warnCreateOper;
	}

	public String getWarnUpdateOper() {
		return warnUpdateOper;
	}

	public void setWarnUpdateOper(String warnUpdateOper) {
		this.warnUpdateOper = warnUpdateOper;
	}

	public String getWarnCreateTime() {
		return warnCreateTime;
	}

	public void setWarnCreateTime(String warnCreateTime) {
		this.warnCreateTime = warnCreateTime;
	}

	public String getWarnUpdateTime() {
		return warnUpdateTime;
	}

	public void setWarnUpdateTime(String warnUpdateTime) {
		this.warnUpdateTime = warnUpdateTime;
	}
}