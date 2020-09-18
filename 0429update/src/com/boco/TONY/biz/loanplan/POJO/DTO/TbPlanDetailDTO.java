package com.boco.TONY.biz.loanplan.POJO.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 信贷计划详情DTO
 * @author tony
 * @describe TbPlanDetailDTO
 * @date 2019-09-29
 */
public class TbPlanDetailDTO implements Serializable {
    private static final long serialVersionUID = -618113236240784223L;
    /**
     * 信贷计划ID
     */
    private String pdId;

    /**
     * 计划详情关联ID
     */
    private String pdRefId;

    /**
     * 信贷计划所属月份
     */
    private String pdMonth;


    /**
     * 信贷计划机构号-36个机构
     */
    private String pdOrgan;

    /**
     * 计划制定时间
     */
    private LocalDateTime pdCreateTime;

    /**
     * 条目状态
     */
    private Integer pdState;
    /**
     * 小额贷款需求量
     */
    private double pdSmallAmountLoanReq;
    /**
     * 其他贷款需求量
     */
    private double pdOtherLoanReq;
    /**
     * 个商贷款需求量
     */
    private double pdPerBusinessLoanReq;
    /**
     * 小企业需求量
     */
    private double pdSmallBusinessReq;
    /**
     * 小企业保理需求量
     */
    private double pdSmallBusinessFactorReq;
    /**
     * 住房按揭贷款需求量
     */
    private double pdHouseMortLoanReq;
    /**
     * 其他消费贷款需求量
     */
    private double pdOtherConsumeLoanReq;
    /**
     * 供应链需求量
     */
    private double pdSupplyLineReq;
    /**
     * 国内贸易融资需求量
     */
    private double pdDomesticTradeFinanceReq;
    /**
     * 国际贸易融资需求量
     */
    private double pdInterTradeFinanceReq;
    /**
     * 其他公司贷款需求量
     */
    private double pdOtherCompanyLoanReq;
    /**
     * 三农公司贷款需求量
     */
    private double pdSanCompanyLoanReq;
    /**
     * 并购贷款需求量
     */
    private double pdMergeLoanReq;
    /**
     * 各项垫款需求量
     */
    private double pdAllAdvanceLoanReq;

    /**
     * 单位透支需求量
     */
    private double pdUnitOverdraftReq;
    /**
     * 转贴需求量
     */
    private double pdRepostReq;
    /**
     * 直贴需求量
     */
    private double pdStraightReq;
    /**
     * -买入福费廷（人民币）需求量
     */
    private double pdBuyForfeitingRMBReq;
    /**
     * 国际公司贷款需求量
     */
    private double pdInterCompanyLoanReq;
    /**
     * 专项融资需求量
     */
    private double pdSpecialFinanceReq;
    /**
     * 个人透支需求量
     */
    private double pdPersonOverdraftReq;
    /**
     * 外币贷款需求量
     */
    private double pdForeignCurrencyLoanReq;
    /**
     * 买入福费廷（外币）需求量
     */
    private double pdBuyForfeitingForeignCurReq;
    /**
     * 其他需求量
     */
    private double pdOtherReq;

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPdRefId() {
        return pdRefId;
    }

    public void setPdRefId(String pdRefId) {
        this.pdRefId = pdRefId;
    }

    public String getPdMonth() {
        return pdMonth;
    }

    public void setPdMonth(String pdMonth) {
        this.pdMonth = pdMonth;
    }

    public String getPdOrgan() {
        return pdOrgan;
    }

    public void setPdOrgan(String pdOrgan) {
        this.pdOrgan = pdOrgan;
    }

    public LocalDateTime getPdCreateTime() {
        return pdCreateTime;
    }

    public void setPdCreateTime(LocalDateTime pdCreateTime) {
        this.pdCreateTime = pdCreateTime;
    }

    public Integer getPdState() {
        return pdState;
    }

    public void setPdState(Integer pdState) {
        this.pdState = pdState;
    }

    public double getPdSmallAmountLoanReq() {
        return pdSmallAmountLoanReq;
    }

    public void setPdSmallAmountLoanReq(double pdSmallAmountLoanReq) {
        this.pdSmallAmountLoanReq = pdSmallAmountLoanReq;
    }

    public double getPdOtherLoanReq() {
        return pdOtherLoanReq;
    }

    public void setPdOtherLoanReq(double pdOtherLoanReq) {
        this.pdOtherLoanReq = pdOtherLoanReq;
    }

    public double getPdPerBusinessLoanReq() {
        return pdPerBusinessLoanReq;
    }

    public void setPdPerBusinessLoanReq(double pdPerBusinessLoanReq) {
        this.pdPerBusinessLoanReq = pdPerBusinessLoanReq;
    }

    public double getPdSmallBusinessReq() {
        return pdSmallBusinessReq;
    }

    public void setPdSmallBusinessReq(double pdSmallBusinessReq) {
        this.pdSmallBusinessReq = pdSmallBusinessReq;
    }

    public double getPdSmallBusinessFactorReq() {
        return pdSmallBusinessFactorReq;
    }

    public void setPdSmallBusinessFactorReq(double pdSmallBusinessFactorReq) {
        this.pdSmallBusinessFactorReq = pdSmallBusinessFactorReq;
    }

    public double getPdHouseMortLoanReq() {
        return pdHouseMortLoanReq;
    }

    public void setPdHouseMortLoanReq(double pdHouseMortLoanReq) {
        this.pdHouseMortLoanReq = pdHouseMortLoanReq;
    }

    public double getPdOtherConsumeLoanReq() {
        return pdOtherConsumeLoanReq;
    }

    public void setPdOtherConsumeLoanReq(double pdOtherConsumeLoanReq) {
        this.pdOtherConsumeLoanReq = pdOtherConsumeLoanReq;
    }

    public double getPdSupplyLineReq() {
        return pdSupplyLineReq;
    }

    public void setPdSupplyLineReq(double pdSupplyLineReq) {
        this.pdSupplyLineReq = pdSupplyLineReq;
    }

    public double getPdDomesticTradeFinanceReq() {
        return pdDomesticTradeFinanceReq;
    }

    public void setPdDomesticTradeFinanceReq(double pdDomesticTradeFinanceReq) {
        this.pdDomesticTradeFinanceReq = pdDomesticTradeFinanceReq;
    }

    public double getPdInterTradeFinanceReq() {
        return pdInterTradeFinanceReq;
    }

    public void setPdInterTradeFinanceReq(double pdInterTradeFinanceReq) {
        this.pdInterTradeFinanceReq = pdInterTradeFinanceReq;
    }

    public double getPdOtherCompanyLoanReq() {
        return pdOtherCompanyLoanReq;
    }

    public void setPdOtherCompanyLoanReq(double pdOtherCompanyLoanReq) {
        this.pdOtherCompanyLoanReq = pdOtherCompanyLoanReq;
    }

    public double getPdSanCompanyLoanReq() {
        return pdSanCompanyLoanReq;
    }

    public void setPdSanCompanyLoanReq(double pdSanCompanyLoanReq) {
        this.pdSanCompanyLoanReq = pdSanCompanyLoanReq;
    }

    public double getPdMergeLoanReq() {
        return pdMergeLoanReq;
    }

    public void setPdMergeLoanReq(double pdMergeLoanReq) {
        this.pdMergeLoanReq = pdMergeLoanReq;
    }

    public double getPdAllAdvanceLoanReq() {
        return pdAllAdvanceLoanReq;
    }

    public void setPdAllAdvanceLoanReq(double pdAllAdvanceLoanReq) {
        this.pdAllAdvanceLoanReq = pdAllAdvanceLoanReq;
    }

    public double getPdUnitOverdraftReq() {
        return pdUnitOverdraftReq;
    }

    public void setPdUnitOverdraftReq(double pdUnitOverdraftReq) {
        this.pdUnitOverdraftReq = pdUnitOverdraftReq;
    }

    public double getPdRepostReq() {
        return pdRepostReq;
    }

    public void setPdRepostReq(double pdRepostReq) {
        this.pdRepostReq = pdRepostReq;
    }

    public double getPdStraightReq() {
        return pdStraightReq;
    }

    public void setPdStraightReq(double pdStraightReq) {
        this.pdStraightReq = pdStraightReq;
    }

    public double getPdBuyForfeitingRMBReq() {
        return pdBuyForfeitingRMBReq;
    }

    public void setPdBuyForfeitingRMBReq(double pdBuyForfeitingRMBReq) {
        this.pdBuyForfeitingRMBReq = pdBuyForfeitingRMBReq;
    }

    public double getPdInterCompanyLoanReq() {
        return pdInterCompanyLoanReq;
    }

    public void setPdInterCompanyLoanReq(double pdInterCompanyLoanReq) {
        this.pdInterCompanyLoanReq = pdInterCompanyLoanReq;
    }

    public double getPdSpecialFinanceReq() {
        return pdSpecialFinanceReq;
    }

    public void setPdSpecialFinanceReq(double pdSpecialFinanceReq) {
        this.pdSpecialFinanceReq = pdSpecialFinanceReq;
    }

    public double getPdPersonOverdraftReq() {
        return pdPersonOverdraftReq;
    }

    public void setPdPersonOverdraftReq(double pdPersonOverdraftReq) {
        this.pdPersonOverdraftReq = pdPersonOverdraftReq;
    }

    public double getPdForeignCurrencyLoanReq() {
        return pdForeignCurrencyLoanReq;
    }

    public void setPdForeignCurrencyLoanReq(double pdForeignCurrencyLoanReq) {
        this.pdForeignCurrencyLoanReq = pdForeignCurrencyLoanReq;
    }

    public double getPdBuyForfeitingForeignCurReq() {
        return pdBuyForfeitingForeignCurReq;
    }

    public void setPdBuyForfeitingForeignCurReq(double pdBuyForfeitingForeignCurReq) {
        this.pdBuyForfeitingForeignCurReq = pdBuyForfeitingForeignCurReq;
    }

    public double getPdOtherReq() {
        return pdOtherReq;
    }

    public void setPdOtherReq(double pdOtherReq) {
        this.pdOtherReq = pdOtherReq;
    }

    @Override
    public String toString() {
        return "TbPlanDetailDTO{" +
                "pdId='" + pdId + '\'' +
                ", pdRefId='" + pdRefId + '\'' +
                ", pdMonth='" + pdMonth + '\'' +
                ", pdOrgan='" + pdOrgan + '\'' +
                ", pdCreateTime=" + pdCreateTime +
                ", pdState=" + pdState +
                ", pdSmallAmountLoanReq=" + pdSmallAmountLoanReq +
                ", pdOtherLoanReq=" + pdOtherLoanReq +
                ", pdPerBusinessLoanReq=" + pdPerBusinessLoanReq +
                ", pdSmallBusinessReq=" + pdSmallBusinessReq +
                ", pdSmallBusinessFactorReq=" + pdSmallBusinessFactorReq +
                ", pdHouseMortLoanReq=" + pdHouseMortLoanReq +
                ", pdOtherConsumeLoanReq=" + pdOtherConsumeLoanReq +
                ", pdSupplyLineReq=" + pdSupplyLineReq +
                ", pdDomesticTradeFinanceReq=" + pdDomesticTradeFinanceReq +
                ", pdInterTradeFinanceReq=" + pdInterTradeFinanceReq +
                ", pdOtherCompanyLoanReq=" + pdOtherCompanyLoanReq +
                ", pdSanCompanyLoanReq=" + pdSanCompanyLoanReq +
                ", pdMergeLoanReq=" + pdMergeLoanReq +
                ", pdAllAdvanceLoanReq=" + pdAllAdvanceLoanReq +
                ", pdUnitOverdraftReq=" + pdUnitOverdraftReq +
                ", pdRepostReq=" + pdRepostReq +
                ", pdStraightReq=" + pdStraightReq +
                ", pdBuyForfeitingRMBReq=" + pdBuyForfeitingRMBReq +
                ", pdInterCompanyLoanReq=" + pdInterCompanyLoanReq +
                ", pdSpecialFinanceReq=" + pdSpecialFinanceReq +
                ", pdPersonOverdraftReq=" + pdPersonOverdraftReq +
                ", pdForeignCurrencyLoanReq=" + pdForeignCurrencyLoanReq +
                ", pdBuyForfeitingForeignCurReq=" + pdBuyForfeitingForeignCurReq +
                ", pdOtherReq=" + pdOtherReq +
                '}';
    }
}
