package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * ���ֹ��������ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbEvaluateParam extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /**
     * �½�
     */
    public static final Integer STATE_NEW = 1;
    /**
     * �Ѳ���
     */
    public static final Integer STATE_DEPLOYED = 2;


    /** ���� */
    /**
     * ���۲���id
     */
    @EntityParaAnno(zhName = "���۲���id")
    private Integer tpId;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String tpName;
    /**
     * �������id
     */
    @EntityParaAnno(zhName = "�������id")
    private String tpComb;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private Integer tpFullScore;
    /**
     * ������Сֵ
     */
    @EntityParaAnno(zhName = "������Сֵ")
    private Double tpMin;
    /**
     * �������ֵ
     */
    @EntityParaAnno(zhName = "�������ֵ")
    private Double tpMax;
    /**
     * �۷ֱ�׼
     */
    @EntityParaAnno(zhName = "�۷ֱ�׼")
    private Integer tpDeduction;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String tpCreateTime;
    /**
     * ������Ա
     */
    @EntityParaAnno(zhName = "������Ա")
    private String tpCreateOper;
    /**
     * ״̬
     */
    @EntityParaAnno(zhName = "״̬")
    private Integer tpState;

    /** setter\getter���� */
    /**
     * ���۲���id
     */
    public void setTpId(Integer tpId) {
        this.tpId = tpId;
    }

    public Integer getTpId() {
        return this.tpId;
    }

    /**
     * ��������
     */
    public void setTpName(String tpName) {
        this.tpName = tpName == null ? null : tpName.trim();
    }

    public String getTpName() {
        return this.tpName;
    }

    /**
     * �������id
     */
    public void setTpComb(String tpComb) {
        this.tpComb = tpComb == null ? null : tpComb.trim();
    }

    public String getTpComb() {
        return this.tpComb;
    }

    /**
     * ����
     */
    public void setTpFullScore(Integer tpFullScore) {
        this.tpFullScore = tpFullScore;
    }

    public Integer getTpFullScore() {
        return this.tpFullScore;
    }

    /**
     * ������Сֵ
     */
    public void setTpMin(Double tpMin) {
        this.tpMin = tpMin;
    }

    public Double getTpMin() {
        return this.tpMin;
    }

    /**
     * �������ֵ
     */
    public void setTpMax(Double tpMax) {
        this.tpMax = tpMax;
    }

    public Double getTpMax() {
        return this.tpMax;
    }

    /**
     * �۷ֱ�׼
     */
    public void setTpDeduction(Integer tpDeduction) {
        this.tpDeduction = tpDeduction;
    }

    public Integer getTpDeduction() {
        return this.tpDeduction;
    }

    /**
     * ����ʱ��
     */
    public void setTpCreateTime(String tpCreateTime) {
        this.tpCreateTime = tpCreateTime == null ? null : tpCreateTime.trim();
    }

    public String getTpCreateTime() {
        return this.tpCreateTime;
    }

    /**
     * ������Ա
     */
    public void setTpCreateOper(String tpCreateOper) {
        this.tpCreateOper = tpCreateOper == null ? null : tpCreateOper.trim();
    }

    public String getTpCreateOper() {
        return this.tpCreateOper;
    }

    /**
     * ״̬
     */
    public void setTpState(Integer tpState) {
        this.tpState = tpState;
    }

    public Integer getTpState() {
        return this.tpState;
    }
}