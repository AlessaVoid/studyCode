package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * TbPunishResultʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbPunishResult extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */


    /**
     * �ݸ�
     */
    public static final int STATE_DRAFT = 0;
    /**
     * �½�
     */
    public static final int STATE_NEW = 1;
    /**
     * ��������
     */
    public static final int STATE_FILL = 2;

    /**
     * ������
     */
    public static final int STATE_APPROVALING = 8;
    /**
     * ��Ϣid
     */
    @EntityParaAnno(zhName = "��Ϣid")
    private Integer punishId;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String organCode;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String organName;
    /**
     * ���������ֹʱ��
     */
    @EntityParaAnno(zhName = "���������ֹʱ��")
    private String endTime;
    /**
     * �ܿ��˼ƻ�
     */
    @EntityParaAnno(zhName = "�ܿ��˼ƻ�")
    private BigDecimal planMount;
    /**
     * 31�����ö��
     */
    @EntityParaAnno(zhName = "31�����ö��")
    private BigDecimal monthVacancyAmt;
    /**
     * 31��������
     */
    @EntityParaAnno(zhName = "31��������")
    private BigDecimal monthVacancyRate;
    /**
     * �����÷ѣ������죩
     */
    @EntityParaAnno(zhName = "�����÷ѣ������죩")
    private BigDecimal monthFiveVacancy;
    /**
     * ʵ�忼�˼ƻ�
     */
    @EntityParaAnno(zhName = "ʵ�忼�˼ƻ�")
    private BigDecimal monthShitiPlanMount;
    /**
     * ʵ��31�ճ��ƻ����
     */
    @EntityParaAnno(zhName = "ʵ��31�ճ��ƻ����")
    private BigDecimal monthShitiOverAmt;
    /**
     * ʵ��31�ճ��ƻ�����
     */
    @EntityParaAnno(zhName = "ʵ��31�ճ��ƻ�����")
    private BigDecimal monthShitiOverRate;
    /**
     * ʵ�峬�ƻ��ѣ������죩
     */
    @EntityParaAnno(zhName = "ʵ�峬�ƻ��ѣ������죩")
    private BigDecimal monthFiveShitiOver;
    /**
     * Ʊ�ݿ��˼ƻ�
     */
    @EntityParaAnno(zhName = "Ʊ�ݿ��˼ƻ�")
    private BigDecimal monthPiapjuPlanMount;
    /**
     * Ʊ��31�ճ��ƻ����
     */
    @EntityParaAnno(zhName = "Ʊ��31�ճ��ƻ����")
    private BigDecimal monthPiaojuOverAmt;
    /**
     * Ʊ��31�ճ��ƻ�����
     */
    @EntityParaAnno(zhName = "Ʊ��31�ճ��ƻ�����")
    private BigDecimal monthPiaojuOverRate;
    /**
     * Ʊ�ݳ��ƻ��ѣ������죩
     */
    @EntityParaAnno(zhName = "Ʊ�ݳ��ƻ��ѣ������죩")
    private BigDecimal monthFivePiaojuOver;
    /**
     * ��Ϣ�ܼ�
     */
    @EntityParaAnno(zhName = "��Ϣ�ܼ�")
    private BigDecimal monthTotalPunish;
    /**
     * �����û�id
     */
    @EntityParaAnno(zhName = "�����û�id")
    private String createUserid;
    /**
     * �����û�id
     */
    @EntityParaAnno(zhName = "�����û�id")
    private String updateUserid;
    /**
     * ״̬
     */
    @EntityParaAnno(zhName = "״̬")
    private Integer state;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String createTime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String updateTime;
    /**
     * ��Ϣ�����·�
     */
    @EntityParaAnno(zhName = "��Ϣ�����·�")
    private String punishMonth;
    /**
     * ��Ϣ�б�id
     */
    @EntityParaAnno(zhName = "��Ϣ�б�id")
    private Integer punishListId;
    /**
     * �������
     */
    @EntityParaAnno(zhName = "�������")
    private String note;

    /** setter\getter���� */
    /**
     * ��Ϣid
     */
    public void setPunishId(Integer punishId) {
        this.punishId = punishId;
    }

    public Integer getPunishId() {
        return this.punishId;
    }

    /**
     * ��������
     */
    public void setOrganCode(String organCode) {
        this.organCode = organCode == null ? null : organCode.trim();
    }

    public String getOrganCode() {
        return this.organCode;
    }

    /**
     * ��������
     */
    public void setOrganName(String organName) {
        this.organName = organName == null ? null : organName.trim();
    }

    public String getOrganName() {
        return this.organName;
    }

    /**
     * �ܿ��˼ƻ�
     */
    public void setPlanMount(BigDecimal planMount) {
        this.planMount = planMount;
    }

    public BigDecimal getPlanMount() {
        return this.planMount;
    }

    /**
     * ���������ֹʱ��
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 31�����ö��
     */
    public void setMonthVacancyAmt(BigDecimal monthVacancyAmt) {
        this.monthVacancyAmt = monthVacancyAmt ;
    }

    public BigDecimal getMonthVacancyAmt() {
        return this.monthVacancyAmt;
    }

    /**
     * 31��������
     */
    public void setMonthVacancyRate(BigDecimal monthVacancyRate) {
        this.monthVacancyRate = monthVacancyRate ;
    }

    public BigDecimal getMonthVacancyRate() {
        return this.monthVacancyRate;
    }

    /**
     * �����÷ѣ������죩
     */
    public void setMonthFiveVacancy(BigDecimal monthFiveVacancy) {
        this.monthFiveVacancy = monthFiveVacancy ;
    }

    public BigDecimal getMonthFiveVacancy() {
        return this.monthFiveVacancy;
    }

    /**
     * ʵ�忼�˼ƻ�
     */
    public void setMonthShitiPlanMount(BigDecimal monthShitiPlanMount) {
        this.monthShitiPlanMount = monthShitiPlanMount ;
    }

    public BigDecimal getMonthShitiPlanMount() {
        return this.monthShitiPlanMount;
    }

    /**
     * ʵ��31�ճ��ƻ����
     */
    public void setMonthShitiOverAmt(BigDecimal monthShitiOverAmt) {
        this.monthShitiOverAmt = monthShitiOverAmt ;
    }

    public BigDecimal getMonthShitiOverAmt() {
        return this.monthShitiOverAmt;
    }

    /**
     * ʵ��31�ճ��ƻ�����
     */
    public void setMonthShitiOverRate(BigDecimal monthShitiOverRate) {
        this.monthShitiOverRate = monthShitiOverRate ;
    }

    public BigDecimal getMonthShitiOverRate() {
        return this.monthShitiOverRate;
    }

    /**
     * ʵ�峬�ƻ��ѣ������죩
     */
    public void setMonthFiveShitiOver(BigDecimal monthFiveShitiOver) {
        this.monthFiveShitiOver = monthFiveShitiOver;
    }

    public BigDecimal getMonthFiveShitiOver() {
        return this.monthFiveShitiOver;
    }

    /**
     * Ʊ�ݿ��˼ƻ�
     */
    public void setMonthPiapjuPlanMount(BigDecimal monthPiapjuPlanMount) {
        this.monthPiapjuPlanMount = monthPiapjuPlanMount ;
    }

    public BigDecimal getMonthPiapjuPlanMount() {
        return this.monthPiapjuPlanMount;
    }

    /**
     * Ʊ��31�ճ��ƻ����
     */
    public void setMonthPiaojuOverAmt(BigDecimal monthPiaojuOverAmt) {
        this.monthPiaojuOverAmt = monthPiaojuOverAmt ;
    }

    public BigDecimal getMonthPiaojuOverAmt() {
        return this.monthPiaojuOverAmt;
    }

    /**
     * Ʊ��31�ճ��ƻ�����
     */
    public void setMonthPiaojuOverRate(BigDecimal monthPiaojuOverRate) {
        this.monthPiaojuOverRate = monthPiaojuOverRate ;
    }

    public BigDecimal getMonthPiaojuOverRate() {
        return this.monthPiaojuOverRate;
    }

    /**
     * Ʊ�ݳ��ƻ��ѣ������죩
     */
    public void setMonthFivePiaojuOver(BigDecimal monthFivePiaojuOver) {
        this.monthFivePiaojuOver = monthFivePiaojuOver ;
    }

    public BigDecimal getMonthFivePiaojuOver() {
        return this.monthFivePiaojuOver;
    }

    /**
     * ��Ϣ�ܼ�
     */
    public void setMonthTotalPunish(BigDecimal monthTotalPunish) {
        this.monthTotalPunish = monthTotalPunish ;
    }

    public BigDecimal getMonthTotalPunish() {
        return this.monthTotalPunish;
    }

    /**
     * �����û�id
     */
    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid == null ? null : createUserid.trim();
    }

    public String getCreateUserid() {
        return this.createUserid;
    }

    /**
     * �����û�id
     */
    public void setUpdateUserid(String updateUserid) {
        this.updateUserid = updateUserid == null ? null : updateUserid.trim();
    }

    public String getUpdateUserid() {
        return this.updateUserid;
    }

    /**
     * ״̬
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
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
     * ����ʱ��
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     * ��Ϣ�����·�
     */
    public void setPunishMonth(String punishMonth) {
        this.punishMonth = punishMonth == null ? null : punishMonth.trim();
    }

    public String getPunishMonth() {
        return this.punishMonth;
    }

    /**
     * ��Ϣ�б�id
     */
    public void setPunishListId(Integer punishListId) {
        this.punishListId = punishListId;
    }

    public Integer getPunishListId() {
        return this.punishListId;
    }

    /**
     * �������
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getNote() {
        return this.note;
    }
}