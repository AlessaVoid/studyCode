package com.boco.TONY.biz.loanplan.POJO.VO;

import java.io.Serializable;

/**
 * 信贷计划详情VO
 *
 * @author tony
 * @describe TbPlanDetailVO
 * @date 2019-09-29
 */
public class TbPlanDetailVO implements Serializable {
    private static final long serialVersionUID = 2173056926670363796L;

    /**信贷计划详情Id*/
    private String pdId;
    /**信贷计划Id*/
    private String pdRefId;
    /**信贷计划机构号-36个机构*/
    private String pdOrgan;
    /**信贷计划贷种*/
    private String pdProdType;
    /**信贷计划额度*/
    private String pdProdAmt;
    /**计划制定人*/
    private String pdOper;
    /**计划制定时间*/
    private String pdCreateTime;
    /**条目状态*/
    private Integer pdState;
    /**小额贷款需求量*/
    private Long pdSmallAmountLoanReq;
    /**其他贷款需求量*/
    private Long pdOtherLoanReq;
    /**个商贷款需求量*/
    private Long pdPerBusinessLoanReq;
    /**小企业需求量*/
    private Long pdSmallBusinessReq;
    /**小企业保理需求量*/
    private Long pdSmallBusinessFactorReq;
    /**住房按揭贷款需求量*/
    private Long pdHouseMortLoanReq;
    /**其他消费贷款需求量*/
    private Long pdOtherConsumeLoanReq;
    /**供应链需求量*/
    private Long pdSupplyLineReq;
    /**国内贸易融资需求量*/
    private Long pdDomesticTradeFinanceReq;
    /**国际贸易融资需求量*/
    private Long pdInterTradeFinanceReq;
    /**其他公司贷款需求量*/
    private Long pdOtherCompanyLoanReq;
    /**三农公司贷款需求量*/
    private Long pdSanCompanyLoanReq;
    /**并购贷款需求量*/
    private Long pdMergeLoanReq;
    /**各项垫款需求量*/
    private Long pdAllAdvanceLoanReq;
    /**单位透支需求量/转贴需求量*/
    private Long pdRepostReq;
    /**直贴需求量*/
    private Long pdStraightReq;
    /**-买入福费廷（人民币）需求量*/
    private Long pdBuyForfeitingRMBReq;
    /**国际公司贷款需求量*/
    private Long pdInterCompanyLoanReq;
    /**专项融资需求量*/
    private Long pdSpecialFinanceReq;
    /**个人透支需求量*/
    private Long pdPersonOverdraftReq;
    /**外币贷款需求量*/
    private Long pdForeignCurrencyLoanReq;
    /**买入福费廷（外币）需求量*/
    private Long pdBuyForfeitingForeignCurReq;
    /**其他需求量*/
    private Long pdOtherReq;

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

    public String getPdOrgan() {
        return pdOrgan;
    }

    public void setPdOrgan(String pdOrgan) {
        this.pdOrgan = pdOrgan;
    }

    public String getPdProdType() {
        return pdProdType;
    }

    public void setPdProdType(String pdProdType) {
        this.pdProdType = pdProdType;
    }

    public String getPdProdAmt() {
        return pdProdAmt;
    }

    public void setPdProdAmt(String pdProdAmt) {
        this.pdProdAmt = pdProdAmt;
    }

    public String getPdOper() {
        return pdOper;
    }

    public void setPdOper(String pdOper) {
        this.pdOper = pdOper;
    }

    public String getPdCreateTime() {
        return pdCreateTime;
    }

    public void setPdCreateTime(String pdCreateTime) {
        this.pdCreateTime = pdCreateTime;
    }
    public Integer getPdState() {
        return pdState;
    }

    public void setPdState(Integer pdState) {
        this.pdState = pdState;
    }

    public Long getPdSmallAmountLoanReq() {
        return pdSmallAmountLoanReq;
    }

    public void setPdSmallAmountLoanReq(Long pdSmallAmountLoanReq) {
        this.pdSmallAmountLoanReq = pdSmallAmountLoanReq;
    }

    public Long getPdOtherLoanReq() {
        return pdOtherLoanReq;
    }

    public void setPdOtherLoanReq(Long pdOtherLoanReq) {
        this.pdOtherLoanReq = pdOtherLoanReq;
    }

    public Long getPdPerBusinessLoanReq() {
        return pdPerBusinessLoanReq;
    }

    public void setPdPerBusinessLoanReq(Long pdPerBusinessLoanReq) {
        this.pdPerBusinessLoanReq = pdPerBusinessLoanReq;
    }

    public Long getPdSmallBusinessReq() {
        return pdSmallBusinessReq;
    }

    public void setPdSmallBusinessReq(Long pdSmallBusinessReq) {
        this.pdSmallBusinessReq = pdSmallBusinessReq;
    }

    public Long getPdSmallBusinessFactorReq() {
        return pdSmallBusinessFactorReq;
    }

    public void setPdSmallBusinessFactorReq(Long pdSmallBusinessFactorReq) {
        this.pdSmallBusinessFactorReq = pdSmallBusinessFactorReq;
    }

    public Long getPdHouseMortLoanReq() {
        return pdHouseMortLoanReq;
    }

    public void setPdHouseMortLoanReq(Long pdHouseMortLoanReq) {
        this.pdHouseMortLoanReq = pdHouseMortLoanReq;
    }

    public Long getPdOtherConsumeLoanReq() {
        return pdOtherConsumeLoanReq;
    }

    public void setPdOtherConsumeLoanReq(Long pdOtherConsumeLoanReq) {
        this.pdOtherConsumeLoanReq = pdOtherConsumeLoanReq;
    }

    public Long getPdSupplyLineReq() {
        return pdSupplyLineReq;
    }

    public void setPdSupplyLineReq(Long pdSupplyLineReq) {
        this.pdSupplyLineReq = pdSupplyLineReq;
    }

    public Long getPdDomesticTradeFinanceReq() {
        return pdDomesticTradeFinanceReq;
    }

    public void setPdDomesticTradeFinanceReq(Long pdDomesticTradeFinanceReq) {
        this.pdDomesticTradeFinanceReq = pdDomesticTradeFinanceReq;
    }

    public Long getPdInterTradeFinanceReq() {
        return pdInterTradeFinanceReq;
    }

    public void setPdInterTradeFinanceReq(Long pdInterTradeFinanceReq) {
        this.pdInterTradeFinanceReq = pdInterTradeFinanceReq;
    }

    public Long getPdOtherCompanyLoanReq() {
        return pdOtherCompanyLoanReq;
    }

    public void setPdOtherCompanyLoanReq(Long pdOtherCompanyLoanReq) {
        this.pdOtherCompanyLoanReq = pdOtherCompanyLoanReq;
    }

    public Long getPdSanCompanyLoanReq() {
        return pdSanCompanyLoanReq;
    }

    public void setPdSanCompanyLoanReq(Long pdSanCompanyLoanReq) {
        this.pdSanCompanyLoanReq = pdSanCompanyLoanReq;
    }

    public Long getPdMergeLoanReq() {
        return pdMergeLoanReq;
    }

    public void setPdMergeLoanReq(Long pdMergeLoanReq) {
        this.pdMergeLoanReq = pdMergeLoanReq;
    }

    public Long getPdAllAdvanceLoanReq() {
        return pdAllAdvanceLoanReq;
    }

    public void setPdAllAdvanceLoanReq(Long pdAllAdvanceLoanReq) {
        this.pdAllAdvanceLoanReq = pdAllAdvanceLoanReq;
    }

    public Long getPdRepostReq() {
        return pdRepostReq;
    }

    public void setPdRepostReq(Long pdRepostReq) {
        this.pdRepostReq = pdRepostReq;
    }

    public Long getPdStraightReq() {
        return pdStraightReq;
    }

    public void setPdStraightReq(Long pdStraightReq) {
        this.pdStraightReq = pdStraightReq;
    }

    public Long getPdBuyForfeitingRMBReq() {
        return pdBuyForfeitingRMBReq;
    }

    public void setPdBuyForfeitingRMBReq(Long pdBuyForfeitingRMBReq) {
        this.pdBuyForfeitingRMBReq = pdBuyForfeitingRMBReq;
    }

    public Long getPdInterCompanyLoanReq() {
        return pdInterCompanyLoanReq;
    }

    public void setPdInterCompanyLoanReq(Long pdInterCompanyLoanReq) {
        this.pdInterCompanyLoanReq = pdInterCompanyLoanReq;
    }
    public Long getPdSpecialFinanceReq() {
        return pdSpecialFinanceReq;
    }

    public void setPdSpecialFinanceReq(Long pdSpecialFinanceReq) {
        this.pdSpecialFinanceReq = pdSpecialFinanceReq;
    }

    public Long getPdPersonOverdraftReq() {
        return pdPersonOverdraftReq;
    }

    public void setPdPersonOverdraftReq(Long pdPersonOverdraftReq) {
        this.pdPersonOverdraftReq = pdPersonOverdraftReq;
    }

    public Long getPdForeignCurrencyLoanReq() {
        return pdForeignCurrencyLoanReq;
    }

    public void setPdForeignCurrencyLoanReq(Long pdForeignCurrencyLoanReq) {
        this.pdForeignCurrencyLoanReq = pdForeignCurrencyLoanReq;
    }

    public Long getPdBuyForfeitingForeignCurReq() {
        return pdBuyForfeitingForeignCurReq;
    }

    public void setPdBuyForfeitingForeignCurReq(Long pdBuyForfeitingForeignCurReq) {
        this.pdBuyForfeitingForeignCurReq = pdBuyForfeitingForeignCurReq;
    }

    public Long getPdOtherReq() {
        return pdOtherReq;
    }

    public void setPdOtherReq(Long pdOtherReq) {
        this.pdOtherReq = pdOtherReq;
    }

    @Override
    public String toString() {
        return "TbPlanDetailVO{" +
                "pdId='" + pdId + '\'' +
                ", pdRefId='" + pdRefId + '\'' +
                ", pdOrgan='" + pdOrgan + '\'' +
                ", pdProdType='" + pdProdType + '\'' +
                ", pdProdAmt='" + pdProdAmt + '\'' +
                ", pdOper='" + pdOper + '\'' +
                ", pdCreateTime='" + pdCreateTime + '\'' +
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
