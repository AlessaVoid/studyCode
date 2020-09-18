package com.boco.TONY.biz.loanplan.POJO.VO;

import java.io.Serializable;

/**
 * �Ŵ��ƻ�����VO
 *
 * @author tony
 * @describe TbPlanDetailVO
 * @date 2019-09-29
 */
public class TbPlanDetailVO implements Serializable {
    private static final long serialVersionUID = 2173056926670363796L;

    /**�Ŵ��ƻ�����Id*/
    private String pdId;
    /**�Ŵ��ƻ�Id*/
    private String pdRefId;
    /**�Ŵ��ƻ�������-36������*/
    private String pdOrgan;
    /**�Ŵ��ƻ�����*/
    private String pdProdType;
    /**�Ŵ��ƻ����*/
    private String pdProdAmt;
    /**�ƻ��ƶ���*/
    private String pdOper;
    /**�ƻ��ƶ�ʱ��*/
    private String pdCreateTime;
    /**��Ŀ״̬*/
    private Integer pdState;
    /**С�����������*/
    private Long pdSmallAmountLoanReq;
    /**��������������*/
    private Long pdOtherLoanReq;
    /**���̴���������*/
    private Long pdPerBusinessLoanReq;
    /**С��ҵ������*/
    private Long pdSmallBusinessReq;
    /**С��ҵ����������*/
    private Long pdSmallBusinessFactorReq;
    /**ס�����Ҵ���������*/
    private Long pdHouseMortLoanReq;
    /**�������Ѵ���������*/
    private Long pdOtherConsumeLoanReq;
    /**��Ӧ��������*/
    private Long pdSupplyLineReq;
    /**����ó������������*/
    private Long pdDomesticTradeFinanceReq;
    /**����ó������������*/
    private Long pdInterTradeFinanceReq;
    /**������˾����������*/
    private Long pdOtherCompanyLoanReq;
    /**��ũ��˾����������*/
    private Long pdSanCompanyLoanReq;
    /**��������������*/
    private Long pdMergeLoanReq;
    /**������������*/
    private Long pdAllAdvanceLoanReq;
    /**��λ͸֧������/ת��������*/
    private Long pdRepostReq;
    /**ֱ��������*/
    private Long pdStraightReq;
    /**-���븣��͢������ң�������*/
    private Long pdBuyForfeitingRMBReq;
    /**���ʹ�˾����������*/
    private Long pdInterCompanyLoanReq;
    /**ר������������*/
    private Long pdSpecialFinanceReq;
    /**����͸֧������*/
    private Long pdPersonOverdraftReq;
    /**��Ҵ���������*/
    private Long pdForeignCurrencyLoanReq;
    /**���븣��͢����ң�������*/
    private Long pdBuyForfeitingForeignCurReq;
    /**����������*/
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
