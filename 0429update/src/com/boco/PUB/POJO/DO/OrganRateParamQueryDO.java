package com.boco.PUB.POJO.DO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: liujinbo
 * @Description: �������ֵ÷������ͳ���
 * @Date: 2020/1/15
 * @Version: 1.0
 */
public class OrganRateParamQueryDO implements Serializable {
    // �·�
    private String month;
    // ������
    private String organCode;
    // ��ĩ������
    private BigDecimal monthEndRatio;
    // ���������
    private BigDecimal yearBeginRatio;
    // �������������
    private BigDecimal loanYearIncrement;
    // ������Ӫ���������
    private BigDecimal personDepositYearIncrement;
    // ��˾���������
    private BigDecimal companyDepositYearIncrement;
    // �·�����������
    private BigDecimal newLoanRatio;
    // ȫ��ƽ����������
    private BigDecimal bankAverageLoanRatio;
    // �³�ʵ��������
    private BigDecimal monthBeginEntityLoan;
    // ��ĩʵ��������
    private BigDecimal monthEndEntityLoan;
    // �ܴ���
    private BigDecimal loanCount;
    // ���з�Ϣ���
    private BigDecimal organScaleAmount;
    // ȫ�з�Ϣ���
    private BigDecimal bankScaleAmount;
    // ��ĩʵ��������
    private BigDecimal monthEndIncrement;
    // �³���������
    private BigDecimal monthBeginReport;
    // ����ͳһ��̬������ƻ�
    private BigDecimal monthMidPlan;
    // ��ĩͳһ��̬������ƻ�
    private BigDecimal MonthEndPlan;
    // �³������´�ƻ�
    private BigDecimal monthBeginPlan;
    // ����ͳһ��̬�����ƻ�֪ͨ���к���ж���ĵ�������
    private BigDecimal adjCount;
    // �ü�����û�в�������ģռ�÷Ѻ͹�ģ���÷ѵ��·���
    private BigDecimal scaleAmountMonthCount;
    // �ü����ڵ���Ƶ��ָ��δ�۷ֵ��·���
    private BigDecimal adjCountMonthCount;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public BigDecimal getMonthEndRatio() {
        return monthEndRatio;
    }

    public void setMonthEndRatio(BigDecimal monthEndRatio) {
        this.monthEndRatio = monthEndRatio;
    }

    public BigDecimal getYearBeginRatio() {
        return yearBeginRatio;
    }

    public void setYearBeginRatio(BigDecimal yearBeginRatio) {
        this.yearBeginRatio = yearBeginRatio;
    }

    public BigDecimal getLoanYearIncrement() {
        return loanYearIncrement;
    }

    public void setLoanYearIncrement(BigDecimal loanYearIncrement) {
        this.loanYearIncrement = loanYearIncrement;
    }

    public BigDecimal getPersonDepositYearIncrement() {
        return personDepositYearIncrement;
    }

    public void setPersonDepositYearIncrement(BigDecimal personDepositYearIncrement) {
        this.personDepositYearIncrement = personDepositYearIncrement;
    }

    public BigDecimal getCompanyDepositYearIncrement() {
        return companyDepositYearIncrement;
    }

    public void setCompanyDepositYearIncrement(BigDecimal companyDepositYearIncrement) {
        this.companyDepositYearIncrement = companyDepositYearIncrement;
    }

    public BigDecimal getNewLoanRatio() {
        return newLoanRatio;
    }

    public void setNewLoanRatio(BigDecimal newLoanRatio) {
        this.newLoanRatio = newLoanRatio;
    }

    public BigDecimal getBankAverageLoanRatio() {
        return bankAverageLoanRatio;
    }

    public void setBankAverageLoanRatio(BigDecimal bankAverageLoanRatio) {
        this.bankAverageLoanRatio = bankAverageLoanRatio;
    }

    public BigDecimal getMonthBeginEntityLoan() {
        return monthBeginEntityLoan;
    }

    public void setMonthBeginEntityLoan(BigDecimal monthBeginEntityLoan) {
        this.monthBeginEntityLoan = monthBeginEntityLoan;
    }

    public BigDecimal getMonthEndEntityLoan() {
        return monthEndEntityLoan;
    }

    public void setMonthEndEntityLoan(BigDecimal monthEndEntityLoan) {
        this.monthEndEntityLoan = monthEndEntityLoan;
    }

    public BigDecimal getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(BigDecimal loanCount) {
        this.loanCount = loanCount;
    }

    public BigDecimal getOrganScaleAmount() {
        return organScaleAmount;
    }

    public void setOrganScaleAmount(BigDecimal organScaleAmount) {
        this.organScaleAmount = organScaleAmount;
    }

    public BigDecimal getBankScaleAmount() {
        return bankScaleAmount;
    }

    public void setBankScaleAmount(BigDecimal bankScaleAmount) {
        this.bankScaleAmount = bankScaleAmount;
    }

    public BigDecimal getMonthEndIncrement() {
        return monthEndIncrement;
    }

    public void setMonthEndIncrement(BigDecimal monthEndIncrement) {
        this.monthEndIncrement = monthEndIncrement;
    }

    public BigDecimal getMonthBeginReport() {
        return monthBeginReport;
    }

    public void setMonthBeginReport(BigDecimal monthBeginReport) {
        this.monthBeginReport = monthBeginReport;
    }

    public BigDecimal getMonthMidPlan() {
        return monthMidPlan;
    }

    public void setMonthMidPlan(BigDecimal monthMidPlan) {
        this.monthMidPlan = monthMidPlan;
    }

    public BigDecimal getMonthEndPlan() {
        return MonthEndPlan;
    }

    public void setMonthEndPlan(BigDecimal monthEndPlan) {
        MonthEndPlan = monthEndPlan;
    }

    public BigDecimal getMonthBeginPlan() {
        return monthBeginPlan;
    }

    public void setMonthBeginPlan(BigDecimal monthBeginPlan) {
        this.monthBeginPlan = monthBeginPlan;
    }

    public BigDecimal getAdjCount() {
        return adjCount;
    }

    public void setAdjCount(BigDecimal adjCount) {
        this.adjCount = adjCount;
    }

    public BigDecimal getScaleAmountMonthCount() {
        return scaleAmountMonthCount;
    }

    public void setScaleAmountMonthCount(BigDecimal scaleAmountMonthCount) {
        this.scaleAmountMonthCount = scaleAmountMonthCount;
    }

    public BigDecimal getAdjCountMonthCount() {
        return adjCountMonthCount;
    }

    public void setAdjCountMonthCount(BigDecimal adjCountMonthCount) {
        this.adjCountMonthCount = adjCountMonthCount;
    }
}
