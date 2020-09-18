package com.boco.TONY.biz.planadjust.POJO.DTO;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * �Ŵ��ƻ�����DO
 *
 * @author tony
 * @describe TbPlanAdjustDetailDTO
 * @date 2019-09-29
 */
public class TbPlanAdjustDetailDTO implements Serializable {
    private static final long serialVersionUID = -618113236240784223L;

    /**
     * �ƻ��������ID
     */
    private String pdRefId;

    /**
     * �Ŵ��ƻ�������-36������
     */
    private String pdOrgan;

    /**
     * �ƻ��ƶ�ʱ��
     */
    private LocalDateTime pdCreateTime;

    /**
     * С�����������
     */
    private double pdSmallAmountLoanReq;
    /**
     * ��������������
     */
    private double pdOtherLoanReq;
    /**
     * ���̴���������
     */
    private double pdPerBusinessLoanReq;
    /**
     * С��ҵ������
     */
    private double pdSmallBusinessReq;
    /**
     * С��ҵ����������
     */
    private double pdSmallBusinessFactorReq;
    /**
     * ס�����Ҵ���������
     */
    private double pdHouseMortLoanReq;
    /**
     * �������Ѵ���������
     */
    private double pdOtherConsumeLoanReq;
    /**
     * ��Ӧ��������
     */
    private double pdSupplyLineReq;
    /**
     * ����ó������������
     */
    private double pdDomesticTradeFinanceReq;
    /**
     * ����ó������������
     */
    private double pdInterTradeFinanceReq;
    /**
     * ������˾����������
     */
    private double pdOtherCompanyLoanReq;
    /**
     * ��ũ��˾����������
     */
    private double pdSanCompanyLoanReq;
    /**
     * ��������������
     */
    private double pdMergeLoanReq;
    /**
     * ������������
     */
    private double pdAllAdvanceLoanReq;

    /**
     * ��λ͸֧������
     */
    private double pdUnitOverdraftReq;
    /**
     * ת��������
     */
    private double pdRepostReq;
    /**
     * ֱ��������
     */
    private double pdStraightReq;
    /**
     * -���븣��͢������ң�������
     */
    private double pdBuyForfeitingRMBReq;
    /**
     * ���ʹ�˾����������
     */
    private double pdInterCompanyLoanReq;
    /**
     * ר������������
     */
    private double pdSpecialFinanceReq;
    /**
     * ����͸֧������
     */
    private double pdPersonOverdraftReq;
    /**
     * ��Ҵ���������
     */
    private double pdForeignCurrencyLoanReq;
    /**
     * ���븣��͢����ң�������
     */
    private double pdBuyForfeitingForeignCurReq;
    /**
     * ����������
     */
    private double pdOtherReq;

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

    public LocalDateTime getPdCreateTime() {
        return pdCreateTime;
    }

    public void setPdCreateTime(LocalDateTime pdCreateTime) {
        this.pdCreateTime = pdCreateTime;
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
        return "TbPlanDetailDO{" +
                ", pdRefId='" + pdRefId + '\'' +
                ", pdOrgan='" + pdOrgan + '\'' +
                ", pdCreateTime=" + pdCreateTime +
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
