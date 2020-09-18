package com.boco.SYS.entity;

import java.math.BigDecimal;

public class PunishDetail {
    /**
     * "day" varchar(20) NOT NULL, -- ����
     * "month" varchar(10) NULL, -- �����·�
     * organ varchar(10) NULL, -- ������
     * "type" varchar(4) NOT NULL, -- ��Ϣ���� 0-���� 1-���ƻ�
     * comb varchar(20) NOT NULL, -- �������
     * plan numeric NULL, -- ���¼ƻ�
     * diff numeric NULL, -- ������
     * departure numeric NULL, -- ƫ����
     * punish_money numeric NULL, -- ��Ϣ���
     * punish_day numeric NULL, -- �ܷ�Ϣ����
     * left_day numeric NULL--�����ڼ���
     */
    int day;
    int month;
    String organ;
    int type;
    String comb;
    BigDecimal plan;
    BigDecimal diff;
    BigDecimal departure;
    BigDecimal punishMoney;
    int punishDay;
    int leftDay;

    @Override
    public String toString() {
        return "PunishDetail{" +
                "day=" + day +
                ", month=" + month +
                ", organ='" + organ + '\'' +
                ", type=" + type +
                ", comb='" + comb + '\'' +
                ", plan=" + plan +
                ", diff=" + diff +
                ", departure=" + departure +
                ", punishMoney=" + punishMoney +
                ", punishDay=" + punishDay +
                ", leftDay=" + leftDay +
                '}';
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComb() {
        return comb;
    }

    public void setComb(String comb) {
        this.comb = comb;
    }

    public BigDecimal getPlan() {
        return plan;
    }

    public void setPlan(BigDecimal plan) {
        this.plan = plan;
    }

    public BigDecimal getDiff() {
        return diff;
    }

    public void setDiff(BigDecimal diff) {
        this.diff = diff;
    }

    public BigDecimal getDeparture() {
        return departure;
    }

    public void setDeparture(BigDecimal departure) {
        this.departure = departure;
    }

    public BigDecimal getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        this.punishMoney = punishMoney;
    }

    public int getPunishDay() {
        return punishDay;
    }

    public void setPunishDay(int punishDay) {
        this.punishDay = punishDay;
    }

    public int getLeftDay() {
        return leftDay;
    }

    public void setLeftDay(int leftDay) {
        this.leftDay = leftDay;
    }

    public PunishDetail(int day, int month, String organ, int type, String comb, BigDecimal plan, BigDecimal diff, BigDecimal departure, BigDecimal punishMoney, int punishDay, int leftDay) {
        this.day = day;
        this.month = month;
        this.organ = organ;
        this.type = type;
        this.comb = comb;
        this.plan = plan;
        this.diff = diff;
        this.departure = departure;
        this.punishMoney = punishMoney;
        this.punishDay = punishDay;
        this.leftDay = leftDay;
    }

    public PunishDetail() {
    }
}
