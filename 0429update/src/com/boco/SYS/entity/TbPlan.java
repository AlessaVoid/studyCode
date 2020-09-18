package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * �Ŵ��ƻ�DO
 *
 * @author tony
 * @describe TbPlanDO
 * @date 2019-09-29
 */
public class TbPlan extends BaseEntity implements Serializable {


    /**
     * �ƻ�
     */
    public static final int PLAN = 1;
    /**
     * ����
     */
    public static final int STRIPE = 2;
    /**
     * һ������
     */
    public static final int COMB_ONE = 1;
    /**
     * ��������
     */
    public static final int COMB_TWO = 2;

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
     * �ƻ��ƶ���
     */
    private String planOper;

    /**
     * �ƻ�״̬
     */
    private Integer planStatus;

    /**
     * �ƻ�������
     */
    private String planUpdater;
    /**
     * ��λ
     */
    private Integer planUnit;
        /**
     * �ƻ�����ʱ��
     */
    private String planUpdateTime;
    /**
     * �ƻ�����ʱ��
     */
    private String planCreateTime;
    /**
     * ���¼ƻ�������
     */
    private BigDecimal planIncrement;
    /** �����ƶ������� */
    @EntityParaAnno(zhName="�����ƶ�������")
    private BigDecimal planRealIncrement;

    /** �ƻ����� 1-�ƻ�  2-���� */
    @EntityParaAnno(zhName="�ƻ����� 1-�ƻ�  2-����")
    private Integer planType;

    /** ������� ״̬0���ƶ� */
    @EntityParaAnno(zhName="������� ״̬0���ƶ�")
    private Integer quotaStatus;
    /** �����ƻ����ּ��� */
    @EntityParaAnno(zhName="�����ƻ����ּ���")
    private Integer combLevel;

    private String __status;

    public BigDecimal getPlanIncrement() {
        return planIncrement;
    }

    public void setPlanIncrement(BigDecimal planIncrement) {
        this.planIncrement = planIncrement;
    }

    public String getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(String planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    public String getPlanCreateTime() {
        return planCreateTime;
    }

    public void setPlanCreateTime(String planCreateTime) {
        this.planCreateTime = planCreateTime;
    }



    public String getPlanId() {
        return planId;
    }

    public TbPlan setPlanId(String planId) {
        this.planId = planId;
        return this;
    }

    public String getPlanOrgan() {
        return planOrgan;
    }

    public TbPlan setPlanOrgan(String planOrgan) {
        this.planOrgan = planOrgan;
        return this;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public TbPlan setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
        return this;
    }

    public String getPlanOper() {
        return planOper;
    }

    public TbPlan setPlanOper(String planOper) {
        this.planOper = planOper;
        return this;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanUpdater() {
        return planUpdater;
    }

    public TbPlan setPlanUpdater(String planUpdater) {
        this.planUpdater = planUpdater;
        return this;
    }

    public Integer getPlanUnit() {
        return planUnit;
    }

    public void setPlanUnit(Integer planUnit) {
        this.planUnit = planUnit;
    }

    public String get__status() {
        return __status;
    }

    public void set__status(String __status) {
        this.__status = __status;
    }

    public BigDecimal getPlanRealIncrement() {
        return planRealIncrement;
    }

    public void setPlanRealIncrement(BigDecimal planRealIncrement) {
        this.planRealIncrement = planRealIncrement;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getQuotaStatus() {
        return quotaStatus;
    }

    public void setQuotaStatus(Integer quotaStatus) {
        this.quotaStatus = quotaStatus;
    }

    public Integer getCombLevel() {
        return combLevel;
    }

    public void setCombLevel(Integer combLevel) {
        this.combLevel = combLevel;
    }

    @Override
    public String toString() {
        return "TbPlanDO{" +
                "planId='" + planId + '\'' +
                ", planOrgan='" + planOrgan + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", planOper='" + planOper + '\'' +
                ", planStatus=" + planStatus +
                ", planUpdater='" + planUpdater + '\'' +
                ", planUnit=" + planUnit +
                ", planUpdateTime='" + planUpdateTime + '\'' +
                ", planCreateTime='" + planCreateTime + '\'' +
                ", planIncrement='" + planIncrement + '\'' +
                ", __status='" + __status + '\'' +
                '}';
    }

}
