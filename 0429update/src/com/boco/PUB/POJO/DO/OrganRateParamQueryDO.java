package com.boco.PUB.POJO.DO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: liujinbo
 * @Description: 机构评分得分项类型常量
 * @Date: 2020/1/15
 * @Version: 1.0
 */
public class OrganRateParamQueryDO implements Serializable {
    // 月份
    private String month;
    // 机构号
    private String organCode;
    // 月末不良率
    private BigDecimal monthEndRatio;
    // 年初不良率
    private BigDecimal yearBeginRatio;
    // 各项贷款年增量
    private BigDecimal loanYearIncrement;
    // 个人自营存款年增量
    private BigDecimal personDepositYearIncrement;
    // 公司存款年增量
    private BigDecimal companyDepositYearIncrement;
    // 新发生贷款利率
    private BigDecimal newLoanRatio;
    // 全行平均贷款利率
    private BigDecimal bankAverageLoanRatio;
    // 月初实体贷款余额
    private BigDecimal monthBeginEntityLoan;
    // 月末实体贷款余额
    private BigDecimal monthEndEntityLoan;
    // 总贷款
    private BigDecimal loanCount;
    // 分行罚息金额
    private BigDecimal organScaleAmount;
    // 全行罚息金额
    private BigDecimal bankScaleAmount;
    // 月末实际月增量
    private BigDecimal monthEndIncrement;
    // 月初报送需求
    private BigDecimal monthBeginReport;
    // 月中统一动态调整后计划
    private BigDecimal monthMidPlan;
    // 月末统一动态调整后计划
    private BigDecimal MonthEndPlan;
    // 月初总行下达计划
    private BigDecimal monthBeginPlan;
    // 月中统一动态调整计划通知分行后分行额外的调整次数
    private BigDecimal adjCount;
    // 该季度内没有产生超规模占用费和规模闲置费的月份数
    private BigDecimal scaleAmountMonthCount;
    // 该季度内调整频次指标未扣分的月份数
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
