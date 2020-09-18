package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ��ʱ��Ƚ����ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbTempResultInfo extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;



    /**
     * ������
     */
    public static final Integer TEMP_NEW = 1;
    /**
     * ����Ч
     */
    public static final Integer TEMP_ING = 2;

    /**
     * ��ʧЧ
     */
    public static final Integer TEMP_OLD = 4;
    /** ���� */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * �����·ݣ�yyyymm��202004
     */
    @EntityParaAnno(zhName = "�����·ݣ�yyyymm��202004")
    private String month;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String organcode;
    /**
     * �������id
     */
    @EntityParaAnno(zhName = "�������id")
    private String combId;
    /**
     * ��ʱ��ȣ���Ԫ��
     */
    @EntityParaAnno(zhName = "��ʱ��ȣ���Ԫ��")
    private BigDecimal tempamt;
    /**
     * ��Чʱ��
     */
    @EntityParaAnno(zhName = "��Чʱ��")
    private String startDate;
    /**
     * ʧЧʱ��
     */
    @EntityParaAnno(zhName = "ʧЧʱ��")
    private String endDate;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String createTime;
    /**
     * ������Ա
     */
    @EntityParaAnno(zhName = "������Ա")
    private String createOper;

    /**
     * state
     */
    @EntityParaAnno(zhName = "state")
    private java.lang.Integer state;

    /**
     * state
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }

    public java.lang.Integer getState() {
        return this.state;
    }

    /** setter\getter���� */
    /**
     * id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getId() {
        return this.id;
    }

    /**
     * �����·ݣ�yyyymm��202004
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonth() {
        return this.month;
    }

    /**
     * ��������
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    /**
     * �������id
     */
    public void setCombId(String combId) {
        this.combId = combId == null ? null : combId.trim();
    }

    public String getCombId() {
        return this.combId;
    }

    /**
     * ��ʱ��ȣ���Ԫ��
     */
    public void setTempamt(BigDecimal tempamt) {
        this.tempamt = tempamt;
    }

    public BigDecimal getTempamt() {
        return this.tempamt;
    }

    /**
     * ��Чʱ��
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getStartDate() {
        return this.startDate;
    }

    /**
     * ʧЧʱ��
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getEndDate() {
        return this.endDate;
    }

    /**
     * ����ʱ��
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateTime() {
        return this.createTime;
    }

    /**
     * ������Ա
     */
    public void setCreateOper(String createOper) {
        this.createOper = createOper == null ? null : createOper.trim();
    }

    public String getCreateOper() {
        return this.createOper;
    }
}