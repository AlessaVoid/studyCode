package com.boco.TONY.biz.loanplan.POJO.DTO;

import java.io.Serializable;

/**
 * �Ŵ��ƻ�DTO
 *
 * @author tony
 * @describe TbPlanDTO
 * @date 2019-09-29
 */
public class TbPlanDTO implements Serializable {
    private static final long serialVersionUID = 530070982970664212L;

    /**
     * �Ŵ��ƻ�ID
     */
    private String planId;

    /**
     * �Ŵ��ƻ�����
     */
    private String planOrgan;

    /**
     * �Ŵ��ƻ������·�
     */
    private String planMonth;

    /**
     * �ƻ���������ID
     */
    private String planRefId;

    /**
     * �ƻ��ƶ���ʼʱ��
     */
    private String planDateStart;

    /**
     * �ƻ��ƶ�����ʱ��
     */
    private String planDateEnd;

    /**
     * �ƻ��ƶ���
     */
    private String planOper;

    /**
     * �ƻ�״̬
     */
    private int planStatus;
    /**
     * �ƻ�������
     */
    private String planUpdater;
    /**
     * �ƻ�����ʱ��
     */
    private String planUpdateTime;
    /**
     * ��λ
     */
    private int planUnit;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanOrgan() {
        return planOrgan;
    }

    public void setPlanOrgan(String planOrgan) {
        this.planOrgan = planOrgan;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public String getPlanRefId() {
        return planRefId;
    }

    public void setPlanRefId(String planRefId) {
        this.planRefId = planRefId;
    }

    public String getPlanDateStart() {
        return planDateStart;
    }

    public void setPlanDateStart(String planDateStart) {
        this.planDateStart = planDateStart;
    }

    public String getPlanDateEnd() {
        return planDateEnd;
    }

    public void setPlanDateEnd(String planDateEnd) {
        this.planDateEnd = planDateEnd;
    }

    public String getPlanOper() {
        return planOper;
    }

    public void setPlanOper(String planOper) {
        this.planOper = planOper;
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanUpdater() {
        return planUpdater;
    }

    public void setPlanUpdater(String planUpdater) {
        this.planUpdater = planUpdater;
    }

    public String getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(String planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    public int getPlanUnit() {
        return planUnit;
    }

    public void setPlanUnit(int planUnit) {
        this.planUnit = planUnit;
    }

    @Override
    public String toString() {
        return "TbPlanDTO{" +
                "planId='" + planId + '\'' +
                ", planOrgan='" + planOrgan + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", planRefId='" + planRefId + '\'' +
                ", planDateStart='" + planDateStart + '\'' +
                ", planDateEnd='" + planDateEnd + '\'' +
                ", planOper='" + planOper + '\'' +
                ", planStatus=" + planStatus +
                ", planUpdater='" + planUpdater + '\'' +
                ", planUpdateTime=" + planUpdateTime +
                '}';
    }
}
