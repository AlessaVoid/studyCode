package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * �±�����������ά��ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbRptBaseinfoLoankind extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String rptDate;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String rptOrgan;
    /**
     * �������
     */
    @EntityParaAnno(zhName = "�������")
    private String loanKind;
    /**
     * ���
     */
    @EntityParaAnno(zhName = "���")
    private BigDecimal balance;
    /**
     * �³����
     */
    @EntityParaAnno(zhName = "�³����")
    private BigDecimal balanceMonth;
    /**
     * �������
     */
    @EntityParaAnno(zhName = "�������")
    private BigDecimal balanceSeason;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private BigDecimal balanceYear;
    /**
     * ���շ���
     */
    @EntityParaAnno(zhName = "���շ���")
    private BigDecimal dayOcc;
    /**
     * ���յ���
     */
    @EntityParaAnno(zhName = "���յ���")
    private BigDecimal dayExpire;
    /**
     * ���յ���-��ǰ����
     */
    @EntityParaAnno(zhName = "���յ���-��ǰ����")
    private BigDecimal dayExpireAdv;
    /**
     * ���յ���-�������ڻ���
     */
    @EntityParaAnno(zhName = "���յ���-�������ڻ���")
    private BigDecimal dayExpireNormal;
    /**
     * ���վ���
     */
    @EntityParaAnno(zhName = "���վ���")
    private BigDecimal dayIncrease;
    /**
     * ���·���
     */
    @EntityParaAnno(zhName = "���·���")
    private BigDecimal monthOcc;
    /**
     * ����Ԥ�Ƶ���
     */
    @EntityParaAnno(zhName = "����Ԥ�Ƶ���")
    private BigDecimal monthExpireEstimate;
    /**
     * ���µ���
     */
    @EntityParaAnno(zhName = "���µ���")
    private BigDecimal monthExpire;
    /**
     * ������ǰ����
     */
    @EntityParaAnno(zhName = "������ǰ����")
    private BigDecimal monthExpireAdv;
    /**
     * ������������
     */
    @EntityParaAnno(zhName = "������������")
    private BigDecimal monthExpireNormal;
    /**
     * ����Ԥ��ʣ�ൽ��
     */
    @EntityParaAnno(zhName = "����Ԥ��ʣ�ൽ��")
    private BigDecimal monthExpireEstimateLeft;
    /**
     * ���¾���
     */
    @EntityParaAnno(zhName = "���¾���")
    private BigDecimal monthIncrease;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private BigDecimal seasonOcc;
    /**
     * ����Ԥ�Ƶ���
     */
    @EntityParaAnno(zhName = "����Ԥ�Ƶ���")
    private BigDecimal seasonExpireEstimate;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private BigDecimal seasonExpire;
    /**
     * ������ǰ����
     */
    @EntityParaAnno(zhName = "������ǰ����")
    private BigDecimal seasonExpireAdv;
    /**
     * ������������
     */
    @EntityParaAnno(zhName = "������������")
    private BigDecimal seasonExpireNormal;
    /**
     * ����Ԥ��ʣ�ൽ��
     */
    @EntityParaAnno(zhName = "����Ԥ��ʣ�ൽ��")
    private BigDecimal seasonExpireEstimateLeft;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private BigDecimal seasonIncrease;
    /**
     * ���귢��
     */
    @EntityParaAnno(zhName = "���귢��")
    private BigDecimal yearOcc;
    /**
     * ����Ԥ�Ƶ���
     */
    @EntityParaAnno(zhName = "����Ԥ�Ƶ���")
    private BigDecimal yearExpireEstimate;
    /**
     * ���굽��
     */
    @EntityParaAnno(zhName = "���굽��")
    private BigDecimal yearExpire;
    /**
     * ������ǰ����
     */
    @EntityParaAnno(zhName = "������ǰ����")
    private BigDecimal yearExpireAdv;
    /**
     * ������������
     */
    @EntityParaAnno(zhName = "������������")
    private BigDecimal yearExpireNormal;
    /**
     * ����Ԥ��ʣ�ൽ��
     */
    @EntityParaAnno(zhName = "����Ԥ��ʣ�ൽ��")
    private BigDecimal yearExpireEstimateLeft;
    /**
     * ���꾻��
     */
    @EntityParaAnno(zhName = "���꾻��")
    private BigDecimal yearIncrease;
    /**
     * 1���������
     */
    @EntityParaAnno(zhName = "1���������")
    private BigDecimal month1Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer month1Count;
    /**
     * 1-3�����
     */
    @EntityParaAnno(zhName = "1-3�����")
    private BigDecimal month13Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer month13Count;
    /**
     * 3-6�����
     */
    @EntityParaAnno(zhName = "3-6�����")
    private BigDecimal month36Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer month36Count;
    /**
     * 6-12�����
     */
    @EntityParaAnno(zhName = "6-12�����")
    private BigDecimal month612Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer month612Count;
    /**
     * 1-2�����
     */
    @EntityParaAnno(zhName = "1-2�����")
    private BigDecimal year12Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer year12Count;
    /**
     * 2-3�����
     */
    @EntityParaAnno(zhName = "2-3�����")
    private BigDecimal year23Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer year23Count;
    /**
     * 3-5�����
     */
    @EntityParaAnno(zhName = "3-5�����")
    private BigDecimal year35Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer year35Count;
    /**
     * 5-10�����
     */
    @EntityParaAnno(zhName = "5-10�����")
    private BigDecimal year510Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer year510Count;
    /**
     * 10���������
     */
    @EntityParaAnno(zhName = "10���������")
    private BigDecimal year10Balance;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer year10Count;

    /**
     * setter\getter����
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