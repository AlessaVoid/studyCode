package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 额度分配表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbQuotaAllocate extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 主键
     */
    @EntityParaAnno(zhName = "主键")
    private String paId;
    /**
     * 所属月份
     */
    @EntityParaAnno(zhName = "所属月份")
    private String paMonth;
    /**
     * 机构
     */
    @EntityParaAnno(zhName = "机构")
    private String paOrgan;
    /**
     * 贷种
     */
    @EntityParaAnno(zhName = "贷种")
    private String paProdCode;
    /**
     * 目前可用额度
     */
    @EntityParaAnno(zhName = "目前可用额度")
    private BigDecimal paQuotaAvail;
    /**
     * 本月总计划
     */
    @EntityParaAnno(zhName = "本月总计划")
    private BigDecimal paPlan;
    /**
     * 额度类型1机构2条线
     */
    @EntityParaAnno(zhName = "额度类型1机构2条线")
    private Integer quotaType;
    /**
     * 额度级别 0总行制定的计划额度1一分制定的计划额度
     */
    @EntityParaAnno(zhName = "额度级别 0总行制定的计划额度1一分制定的计划额度")
    private Integer quotaLevel;

    /** setter\getter方法 */
	public String getPaId() {
		return paId;
	}

	public void setPaId(String paId) {
		this.paId = paId;
	}

	public String getPaMonth() {
		return paMonth;
	}

	public void setPaMonth(String paMonth) {
		this.paMonth = paMonth;
	}

	public String getPaOrgan() {
		return paOrgan;
	}

	public void setPaOrgan(String paOrgan) {
		this.paOrgan = paOrgan;
	}

	public String getPaProdCode() {
		return paProdCode;
	}

	public void setPaProdCode(String paProdCode) {
		this.paProdCode = paProdCode;
	}

	public BigDecimal getPaQuotaAvail() {
		return paQuotaAvail;
	}

	public void setPaQuotaAvail(BigDecimal paQuotaAvail) {
		this.paQuotaAvail = paQuotaAvail;
	}

	public BigDecimal getPaPlan() {
		return paPlan;
	}

	public void setPaPlan(BigDecimal paPlan) {
		this.paPlan = paPlan;
	}

	public Integer getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(Integer quotaType) {
		this.quotaType = quotaType;
	}

	public Integer getQuotaLevel() {
		return quotaLevel;
	}

	public void setQuotaLevel(Integer quotaLevel) {
		this.quotaLevel = quotaLevel;
	}
}