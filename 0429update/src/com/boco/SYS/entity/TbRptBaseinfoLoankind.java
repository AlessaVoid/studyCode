package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 新报表，机构贷种维度实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbRptBaseinfoLoankind extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 报表日期
     */
    @EntityParaAnno(zhName = "报表日期")
    private String rptDate;
    /**
     * 机构
     */
    @EntityParaAnno(zhName = "机构")
    private String rptOrgan;
    /**
     * 贷种组合
     */
    @EntityParaAnno(zhName = "贷种组合")
    private String loanKind;
    /**
     * 余额
     */
    @EntityParaAnno(zhName = "余额")
    private BigDecimal balance;
    /**
     * 月初余额
     */
    @EntityParaAnno(zhName = "月初余额")
    private BigDecimal balanceMonth;
    /**
     * 季初余额
     */
    @EntityParaAnno(zhName = "季初余额")
    private BigDecimal balanceSeason;
    /**
     * 年初余额
     */
    @EntityParaAnno(zhName = "年初余额")
    private BigDecimal balanceYear;
    /**
     * 当日发生
     */
    @EntityParaAnno(zhName = "当日发生")
    private BigDecimal dayOcc;
    /**
     * 当日到期
     */
    @EntityParaAnno(zhName = "当日到期")
    private BigDecimal dayExpire;
    /**
     * 当日到期-提前还款
     */
    @EntityParaAnno(zhName = "当日到期-提前还款")
    private BigDecimal dayExpireAdv;
    /**
     * 当日到期-正常到期还款
     */
    @EntityParaAnno(zhName = "当日到期-正常到期还款")
    private BigDecimal dayExpireNormal;
    /**
     * 当日净增
     */
    @EntityParaAnno(zhName = "当日净增")
    private BigDecimal dayIncrease;
    /**
     * 当月发生
     */
    @EntityParaAnno(zhName = "当月发生")
    private BigDecimal monthOcc;
    /**
     * 当月预计到期
     */
    @EntityParaAnno(zhName = "当月预计到期")
    private BigDecimal monthExpireEstimate;
    /**
     * 当月到期
     */
    @EntityParaAnno(zhName = "当月到期")
    private BigDecimal monthExpire;
    /**
     * 当月提前还款
     */
    @EntityParaAnno(zhName = "当月提前还款")
    private BigDecimal monthExpireAdv;
    /**
     * 当月正常还款
     */
    @EntityParaAnno(zhName = "当月正常还款")
    private BigDecimal monthExpireNormal;
    /**
     * 当月预计剩余到期
     */
    @EntityParaAnno(zhName = "当月预计剩余到期")
    private BigDecimal monthExpireEstimateLeft;
    /**
     * 当月净增
     */
    @EntityParaAnno(zhName = "当月净增")
    private BigDecimal monthIncrease;
    /**
     * 当季发生
     */
    @EntityParaAnno(zhName = "当季发生")
    private BigDecimal seasonOcc;
    /**
     * 当季预计到期
     */
    @EntityParaAnno(zhName = "当季预计到期")
    private BigDecimal seasonExpireEstimate;
    /**
     * 本季到期
     */
    @EntityParaAnno(zhName = "本季到期")
    private BigDecimal seasonExpire;
    /**
     * 本季提前还款
     */
    @EntityParaAnno(zhName = "本季提前还款")
    private BigDecimal seasonExpireAdv;
    /**
     * 本季正常还款
     */
    @EntityParaAnno(zhName = "本季正常还款")
    private BigDecimal seasonExpireNormal;
    /**
     * 本季预计剩余到期
     */
    @EntityParaAnno(zhName = "本季预计剩余到期")
    private BigDecimal seasonExpireEstimateLeft;
    /**
     * 本季净增
     */
    @EntityParaAnno(zhName = "本季净增")
    private BigDecimal seasonIncrease;
    /**
     * 本年发生
     */
    @EntityParaAnno(zhName = "本年发生")
    private BigDecimal yearOcc;
    /**
     * 本年预计到期
     */
    @EntityParaAnno(zhName = "本年预计到期")
    private BigDecimal yearExpireEstimate;
    /**
     * 本年到期
     */
    @EntityParaAnno(zhName = "本年到期")
    private BigDecimal yearExpire;
    /**
     * 本年提前还款
     */
    @EntityParaAnno(zhName = "本年提前还款")
    private BigDecimal yearExpireAdv;
    /**
     * 本年正常还款
     */
    @EntityParaAnno(zhName = "本年正常还款")
    private BigDecimal yearExpireNormal;
    /**
     * 本年预计剩余到期
     */
    @EntityParaAnno(zhName = "本年预计剩余到期")
    private BigDecimal yearExpireEstimateLeft;
    /**
     * 本年净增
     */
    @EntityParaAnno(zhName = "本年净增")
    private BigDecimal yearIncrease;
    /**
     * 1月以内余额
     */
    @EntityParaAnno(zhName = "1月以内余额")
    private BigDecimal month1Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer month1Count;
    /**
     * 1-3月余额
     */
    @EntityParaAnno(zhName = "1-3月余额")
    private BigDecimal month13Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer month13Count;
    /**
     * 3-6月余额
     */
    @EntityParaAnno(zhName = "3-6月余额")
    private BigDecimal month36Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer month36Count;
    /**
     * 6-12月余额
     */
    @EntityParaAnno(zhName = "6-12月余额")
    private BigDecimal month612Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer month612Count;
    /**
     * 1-2年余额
     */
    @EntityParaAnno(zhName = "1-2年余额")
    private BigDecimal year12Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer year12Count;
    /**
     * 2-3年余额
     */
    @EntityParaAnno(zhName = "2-3年余额")
    private BigDecimal year23Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer year23Count;
    /**
     * 3-5年余额
     */
    @EntityParaAnno(zhName = "3-5年余额")
    private BigDecimal year35Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer year35Count;
    /**
     * 5-10年余额
     */
    @EntityParaAnno(zhName = "5-10年余额")
    private BigDecimal year510Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer year510Count;
    /**
     * 10年以上余额
     */
    @EntityParaAnno(zhName = "10年以上余额")
    private BigDecimal year10Balance;
    /**
     * 笔数
     */
    @EntityParaAnno(zhName = "笔数")
    private Integer year10Count;

    /**
     * setter\getter方法
     */
    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public String getRptOrgan() {
        return rptOrgan;
    }

    public void setRptOrgan(String rptOrgan) {
        this.rptOrgan = rptOrgan;
    }

    public String getLoanKind() {
        return loanKind;
    }

    public void setLoanKind(String loanKind) {
        this.loanKind = loanKind;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalanceMonth() {
        return balanceMonth;
    }

    public void setBalanceMonth(BigDecimal balanceMonth) {
        this.balanceMonth = balanceMonth;
    }

    public BigDecimal getBalanceSeason() {
        return balanceSeason;
    }

    public void setBalanceSeason(BigDecimal balanceSeason) {
        this.balanceSeason = balanceSeason;
    }

    public BigDecimal getBalanceYear() {
        return balanceYear;
    }

    public void setBalanceYear(BigDecimal balanceYear) {
        this.balanceYear = balanceYear;
    }

    public BigDecimal getDayOcc() {
        return dayOcc;
    }

    public void setDayOcc(BigDecimal dayOcc) {
        this.dayOcc = dayOcc;
    }

    public BigDecimal getDayExpire() {
        return dayExpire;
    }

    public void setDayExpire(BigDecimal dayExpire) {
        this.dayExpire = dayExpire;
    }

    public BigDecimal getDayExpireAdv() {
        return dayExpireAdv;
    }

    public void setDayExpireAdv(BigDecimal dayExpireAdv) {
        this.dayExpireAdv = dayExpireAdv;
    }

    public BigDecimal getDayExpireNormal() {
        return dayExpireNormal;
    }

    public void setDayExpireNormal(BigDecimal dayExpireNormal) {
        this.dayExpireNormal = dayExpireNormal;
    }

    public BigDecimal getDayIncrease() {
        return dayIncrease;
    }

    public void setDayIncrease(BigDecimal dayIncrease) {
        this.dayIncrease = dayIncrease;
    }

    public BigDecimal getMonthOcc() {
        return monthOcc;
    }

    public void setMonthOcc(BigDecimal monthOcc) {
        this.monthOcc = monthOcc;
    }

    public BigDecimal getMonthExpireEstimate() {
        return monthExpireEstimate;
    }

    public void setMonthExpireEstimate(BigDecimal monthExpireEstimate) {
        this.monthExpireEstimate = monthExpireEstimate;
    }

    public BigDecimal getMonthExpire() {
        return monthExpire;
    }

    public void setMonthExpire(BigDecimal monthExpire) {
        this.monthExpire = monthExpire;
    }

    public BigDecimal getMonthExpireAdv() {
        return monthExpireAdv;
    }

    public void setMonthExpireAdv(BigDecimal monthExpireAdv) {
        this.monthExpireAdv = monthExpireAdv;
    }

    public BigDecimal getMonthExpireNormal() {
        return monthExpireNormal;
    }

    public void setMonthExpireNormal(BigDecimal monthExpireNormal) {
        this.monthExpireNormal = monthExpireNormal;
    }

    public BigDecimal getMonthExpireEstimateLeft() {
        return monthExpireEstimateLeft;
    }

    public void setMonthExpireEstimateLeft(BigDecimal monthExpireEstimateLeft) {
        this.monthExpireEstimateLeft = monthExpireEstimateLeft;
    }

    public BigDecimal getMonthIncrease() {
        return monthIncrease;
    }

    public void setMonthIncrease(BigDecimal monthIncrease) {
        this.monthIncrease = monthIncrease;
    }

    public BigDecimal getSeasonOcc() {
        return seasonOcc;
    }

    public void setSeasonOcc(BigDecimal seasonOcc) {
        this.seasonOcc = seasonOcc;
    }

    public BigDecimal getSeasonExpireEstimate() {
        return seasonExpireEstimate;
    }

    public void setSeasonExpireEstimate(BigDecimal seasonExpireEstimate) {
        this.seasonExpireEstimate = seasonExpireEstimate;
    }

    public BigDecimal getSeasonExpire() {
        return seasonExpire;
    }

    public void setSeasonExpire(BigDecimal seasonExpire) {
        this.seasonExpire = seasonExpire;
    }

    public BigDecimal getSeasonExpireAdv() {
        return seasonExpireAdv;
    }

    public void setSeasonExpireAdv(BigDecimal seasonExpireAdv) {
        this.seasonExpireAdv = seasonExpireAdv;
    }

    public BigDecimal getSeasonExpireNormal() {
        return seasonExpireNormal;
    }

    public void setSeasonExpireNormal(BigDecimal seasonExpireNormal) {
        this.seasonExpireNormal = seasonExpireNormal;
    }

    public BigDecimal getSeasonExpireEstimateLeft() {
        return seasonExpireEstimateLeft;
    }

    public void setSeasonExpireEstimateLeft(BigDecimal seasonExpireEstimateLeft) {
        this.seasonExpireEstimateLeft = seasonExpireEstimateLeft;
    }

    public BigDecimal getSeasonIncrease() {
        return seasonIncrease;
    }

    public void setSeasonIncrease(BigDecimal seasonIncrease) {
        this.seasonIncrease = seasonIncrease;
    }

    public BigDecimal getYearOcc() {
        return yearOcc;
    }

    public void setYearOcc(BigDecimal yearOcc) {
        this.yearOcc = yearOcc;
    }

    public BigDecimal getYearExpireEstimate() {
        return yearExpireEstimate;
    }

    public void setYearExpireEstimate(BigDecimal yearExpireEstimate) {
        this.yearExpireEstimate = yearExpireEstimate;
    }

    public BigDecimal getYearExpire() {
        return yearExpire;
    }

    public void setYearExpire(BigDecimal yearExpire) {
        this.yearExpire = yearExpire;
    }

    public BigDecimal getYearExpireAdv() {
        return yearExpireAdv;
    }

    public void setYearExpireAdv(BigDecimal yearExpireAdv) {
        this.yearExpireAdv = yearExpireAdv;
    }

    public BigDecimal getYearExpireNormal() {
        return yearExpireNormal;
    }

    public void setYearExpireNormal(BigDecimal yearExpireNormal) {
        this.yearExpireNormal = yearExpireNormal;
    }

    public BigDecimal getYearExpireEstimateLeft() {
        return yearExpireEstimateLeft;
    }

    public void setYearExpireEstimateLeft(BigDecimal yearExpireEstimateLeft) {
        this.yearExpireEstimateLeft = yearExpireEstimateLeft;
    }

    public BigDecimal getYearIncrease() {
        return yearIncrease;
    }

    public void setYearIncrease(BigDecimal yearIncrease) {
        this.yearIncrease = yearIncrease;
    }

    public BigDecimal getMonth1Balance() {
        return month1Balance;
    }

    public void setMonth1Balance(BigDecimal month1Balance) {
        this.month1Balance = month1Balance;
    }

    public Integer getMonth1Count() {
        return month1Count;
    }

    public void setMonth1Count(Integer month1Count) {
        this.month1Count = month1Count;
    }

    public BigDecimal getMonth13Balance() {
        return month13Balance;
    }

    public void setMonth13Balance(BigDecimal month13Balance) {
        this.month13Balance = month13Balance;
    }

    public Integer getMonth13Count() {
        return month13Count;
    }

    public void setMonth13Count(Integer month13Count) {
        this.month13Count = month13Count;
    }

    public BigDecimal getMonth36Balance() {
        return month36Balance;
    }

    public void setMonth36Balance(BigDecimal month36Balance) {
        this.month36Balance = month36Balance;
    }

    public Integer getMonth36Count() {
        return month36Count;
    }

    public void setMonth36Count(Integer month36Count) {
        this.month36Count = month36Count;
    }

    public BigDecimal getMonth612Balance() {
        return month612Balance;
    }

    public void setMonth612Balance(BigDecimal month612Balance) {
        this.month612Balance = month612Balance;
    }

    public Integer getMonth612Count() {
        return month612Count;
    }

    public void setMonth612Count(Integer month612Count) {
        this.month612Count = month612Count;
    }

    public BigDecimal getYear12Balance() {
        return year12Balance;
    }

    public void setYear12Balance(BigDecimal year12Balance) {
        this.year12Balance = year12Balance;
    }

    public Integer getYear12Count() {
        return year12Count;
    }

    public void setYear12Count(Integer year12Count) {
        this.year12Count = year12Count;
    }

    public BigDecimal getYear23Balance() {
        return year23Balance;
    }

    public void setYear23Balance(BigDecimal year23Balance) {
        this.year23Balance = year23Balance;
    }

    public Integer getYear23Count() {
        return year23Count;
    }

    public void setYear23Count(Integer year23Count) {
        this.year23Count = year23Count;
    }

    public BigDecimal getYear35Balance() {
        return year35Balance;
    }

    public void setYear35Balance(BigDecimal year35Balance) {
        this.year35Balance = year35Balance;
    }

    public Integer getYear35Count() {
        return year35Count;
    }

    public void setYear35Count(Integer year35Count) {
        this.year35Count = year35Count;
    }

    public BigDecimal getYear510Balance() {
        return year510Balance;
    }

    public void setYear510Balance(BigDecimal year510Balance) {
        this.year510Balance = year510Balance;
    }

    public Integer getYear510Count() {
        return year510Count;
    }

    public void setYear510Count(Integer year510Count) {
        this.year510Count = year510Count;
    }

    public BigDecimal getYear10Balance() {
        return year10Balance;
    }

    public void setYear10Balance(BigDecimal year10Balance) {
        this.year10Balance = year10Balance;
    }

    public Integer getYear10Count() {
        return year10Count;
    }

    public void setYear10Count(Integer year10Count) {
        this.year10Count = year10Count;
    }
}