package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 计划执行情况表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbPlanExecute extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 主键
     */
    @EntityParaAnno(zhName = "主键")
    private String peId;
    /**
     * 所属月份
     */
    @EntityParaAnno(zhName = "所属月份")
    private String peMonth;
    /**
     * 机构
     */
    @EntityParaAnno(zhName = "机构")
    private String peOrgan;
    /**
     * 贷种
     */
    @EntityParaAnno(zhName = "贷种")
    private String peProdCode;
    /**
     * 该贷种上期余额
     */
    @EntityParaAnno(zhName = "该贷种上期余额")
    private String peLastBalance;
    /**
     * 该贷种本期计划净增
     */
    @EntityParaAnno(zhName = "该贷种本期计划净增")
    private String peQuota;
    /**
     * 该贷种本期到期量
     */
    @EntityParaAnno(zhName = "该贷种本期到期量")
    private BigDecimal peExpire;
    /**
     * 该贷种本期发生两
     */
    @EntityParaAnno(zhName = "该贷种本期发生两")
    private BigDecimal peOccurrence;
    /**
     * 目前可用额度
     */
    @EntityParaAnno(zhName = "目前可用额度")
    private String peQuotaAvail;
    /**
     * 流程中的余额
     */
    @EntityParaAnno(zhName = "流程中的余额")
    private String peInprogress;

    /** setter\getter方法 */
    /**
     * 主键
     */
    public void setPeId(String peId) {
        this.peId = peId == null ? null : peId.trim();
    }

    public String getPeId() {
        return this.peId;
    }

    /**
     * 所属月份
     */
    public void setPeMonth(String peMonth) {
        this.peMonth = peMonth == null ? null : peMonth.trim();
    }

    public String getPeMonth() {
        return this.peMonth;
    }

    /**
     * 机构
     */
    public void setPeOrgan(String peOrgan) {
        this.peOrgan = peOrgan == null ? null : peOrgan.trim();
    }

    public String getPeOrgan() {
        return this.peOrgan;
    }

    /**
     * 贷种
     */
    public void setPeProdCode(String peProdCode) {
        this.peProdCode = peProdCode == null ? null : peProdCode.trim();
    }

    public String getPeProdCode() {
        return this.peProdCode;
    }

    /**
     * 该贷种上期余额
     */
    public void setPeLastBalance(String peLastBalance) {
        this.peLastBalance = peLastBalance == null ? null : peLastBalance.trim();
    }

    public String getPeLastBalance() {
        return this.peLastBalance;
    }

    /**
     * 该贷种本期计划净增
     */
    public void setPeQuota(String peQuota) {
        this.peQuota = peQuota == null ? null : peQuota.trim();
    }

    public String getPeQuota() {
        return this.peQuota;
    }

    public BigDecimal getPeExpire() {
        return peExpire;
    }

    public void setPeExpire(BigDecimal peExpire) {
        this.peExpire = peExpire;
    }

    public BigDecimal getPeOccurrence() {
        return peOccurrence;
    }

    public void setPeOccurrence(BigDecimal peOccurrence) {
        this.peOccurrence = peOccurrence;
    }

    /**
     * 目前可用额度
     */
    public void setPeQuotaAvail(String peQuotaAvail) {
        this.peQuotaAvail = peQuotaAvail == null ? null : peQuotaAvail.trim();
    }

    public String getPeQuotaAvail() {
        return this.peQuotaAvail;
    }

    /**
     * 流程中的余额
     */
    public void setPeInprogress(String peInprogress) {
        this.peInprogress = peInprogress == null ? null : peInprogress.trim();
    }

    public String getPeInprogress() {
        return this.peInprogress;
    }
}