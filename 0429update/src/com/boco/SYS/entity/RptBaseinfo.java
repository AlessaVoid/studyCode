package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

public class RptBaseinfo extends BaseEntity implements java.io.Serializable {

    private String rptDate;
    private String organ;
    private String rptMonth;
    private String loanKind;
    private String balance;
    private String monthOcc;
    private String monthLimit;
    private String dayOcc;
    private String dayLimit;
    private String monthLimitLeft;

    @Override
    public String toString() {
        return "RptBaseinfo{" +
                "rptDate='" + rptDate + '\'' +
                ", organ='" + organ + '\'' +
                ", rptMonth='" + rptMonth + '\'' +
                ", loanKind='" + loanKind + '\'' +
                ", balance='" + balance + '\'' +
                ", monthOcc='" + monthOcc + '\'' +
                ", monthLimit='" + monthLimit + '\'' +
                ", dayOcc='" + dayOcc + '\'' +
                ", dayLimit='" + dayLimit + '\'' +
                ", monthLimitLeft='" + monthLimitLeft + '\'' +
                '}';
    }

    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getRptMonth() {
        return rptMonth;
    }

    public void setRptMonth(String rptMonth) {
        this.rptMonth = rptMonth;
    }

    public String getLoanKind() {
        return loanKind;
    }

    public void setLoanKind(String loanKind) {
        this.loanKind = loanKind;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMonthOcc() {
        return monthOcc;
    }

    public void setMonthOcc(String monthOcc) {
        this.monthOcc = monthOcc;
    }

    public String getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(String monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getDayOcc() {
        return dayOcc;
    }

    public void setDayOcc(String dayOcc) {
        this.dayOcc = dayOcc;
    }

    public String getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(String dayLimit) {
        this.dayLimit = dayLimit;
    }

    public String getMonthLimitLeft() {
        return monthLimitLeft;
    }

    public void setMonthLimitLeft(String monthLimitLeft) {
        this.monthLimitLeft = monthLimitLeft;
    }

    public RptBaseinfo(String rptDate, String organ, String rptMonth, String loanKind, String balance, String monthOcc, String monthLimit, String dayOcc, String dayLimit, String monthLimitLeft) {
        this.rptDate = rptDate;
        this.organ = organ;
        this.rptMonth = rptMonth;
        this.loanKind = loanKind;
        this.balance = balance;
        this.monthOcc = monthOcc;
        this.monthLimit = monthLimit;
        this.dayOcc = dayOcc;
        this.dayLimit = dayLimit;
        this.monthLimitLeft = monthLimitLeft;
    }

    public RptBaseinfo() {
    }
}