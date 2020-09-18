package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * TbReqresetListʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqresetListDTO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * ����Ҫ���·���
     */
    public static final Integer REQ_TO_PRODUCER = 1;
    /**
     * ����Ҫ�������
     */
    public static final Integer REQ_TO_CONSUMER = 2;

    /** ���� */
    /**
     * ���id
     */
    @EntityParaAnno(zhName = "���id")
    private Integer reqresetId;
    /**
     * �������������·�
     */
    @EntityParaAnno(zhName = "�������������·�")
    private String reqresetMonth;
    /**
     * �������󷢲�����
     */
    @EntityParaAnno(zhName = "�������󷢲�����")
    private String reqresetOrgan;
    /**
     * ������������
     */
    @EntityParaAnno(zhName = "������������")
    private Integer reqresetType;
    /**
     * ��������״̬
     */
    @EntityParaAnno(zhName = "��������״̬")
    private Integer reqresetState;
    /**
     * �����������ʼʱ��
     */
    @EntityParaAnno(zhName = "�����������ʼʱ��")
    private String reqresetDateStart;
    /**
     * �������������ʱ��
     */
    @EntityParaAnno(zhName = "�������������ʱ��")
    private String reqresetDateEnd;
    /**
     * ���������id
     */
    @EntityParaAnno(zhName = "���������id")
    private Integer reqresetAttachmentId;
    /**
     * �����˵��
     */
    @EntityParaAnno(zhName = "�����˵��")
    private String reqresetNote;
    /**
     * �·�������
     */
    @EntityParaAnno(zhName = "�·�������")
    private Integer reqresetTo;
    /**
     * ������Աid
     */
    @EntityParaAnno(zhName = "������Աid")
    private String reqresetCreateOper;
    /**
     * ������Աid
     */
    @EntityParaAnno(zhName = "������Աid")
    private String reqresetUpdateOper;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String reqresetCreatetime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String reqresetUpdatetime;

    /** setter\getter���� */
    /**
     * ���id
     */
    public void setReqresetId(Integer reqresetId) {
        this.reqresetId = reqresetId;
    }

    public Integer getReqresetId() {
        return this.reqresetId;
    }

    /**
     * �������������·�
     */
    public void setReqresetMonth(String reqresetMonth) {
        this.reqresetMonth = reqresetMonth == null ? null : reqresetMonth.trim();
    }

    public String getReqresetMonth() {
        return this.reqresetMonth;
    }

    /**
     * �������󷢲�����
     */
    public void setreqresetOrgan(String reqresetOrgan) {
        this.reqresetOrgan = reqresetOrgan == null ? null : reqresetOrgan.trim();
    }

    public String getreqresetOrgan() {
        return this.reqresetOrgan;
    }

    /**
     * ������������
     */
    public void setReqresetType(Integer reqresetType) {
        this.reqresetType = reqresetType;
    }

    public Integer getReqresetType() {
        return this.reqresetType;
    }

    /**
     * ��������״̬
     */
    public void setReqresetState(Integer reqresetState) {
        this.reqresetState = reqresetState;
    }

    public Integer getReqresetState() {
        return this.reqresetState;
    }

    /**
     * �����������ʼʱ��
     */
    public void setReqresetDateStart(String reqresetDateStart) {
        this.reqresetDateStart = reqresetDateStart == null ? null : reqresetDateStart.trim();
    }

    public String getReqresetDateStart() {
        return this.reqresetDateStart;
    }

    /**
     * �������������ʱ��
     */
    public void setReqresetDateEnd(String reqresetDateEnd) {
        this.reqresetDateEnd = reqresetDateEnd == null ? null : reqresetDateEnd.trim();
    }

    public String getReqresetDateEnd() {
        return this.reqresetDateEnd;
    }

    /**
     * ���������id
     */
    public void setReqresetAttachmentId(Integer reqresetAttachmentId) {
        this.reqresetAttachmentId = reqresetAttachmentId;
    }

    public Integer getReqresetAttachmentId() {
        return this.reqresetAttachmentId;
    }

    /**
     * �����˵��
     */
    public void setReqresetNote(String reqresetNote) {
        this.reqresetNote = reqresetNote == null ? null : reqresetNote.trim();
    }

    public String getReqresetNote() {
        return this.reqresetNote;
    }

    /**
     * �·�������
     */
    public void setReqresetTo(Integer reqresetTo) {
        this.reqresetTo = reqresetTo;
    }

    public Integer getReqresetTo() {
        return this.reqresetTo;
    }

    /**
     * ������Աid
     */
    public void setReqresetCreateOper(String reqresetCreateOper) {
        this.reqresetCreateOper = reqresetCreateOper == null ? null : reqresetCreateOper.trim();
    }

    public String getReqresetCreateOper() {
        return this.reqresetCreateOper;
    }

    /**
     * ������Աid
     */
    public void setReqresetUpdateOper(String reqresetUpdateOper) {
        this.reqresetUpdateOper = reqresetUpdateOper == null ? null : reqresetUpdateOper.trim();
    }

    public String getReqresetUpdateOper() {
        return this.reqresetUpdateOper;
    }

    /**
     * ����ʱ��
     */
    public void setReqresetCreatetime(String reqresetCreatetime) {
        this.reqresetCreatetime = reqresetCreatetime;
    }

    public String getReqresetCreatetime() {
        return this.reqresetCreatetime;
    }

    /**
     * ����ʱ��
     */
    public void setReqresetUpdatetime(String reqresetUpdatetime) {
        this.reqresetUpdatetime = reqresetUpdatetime;
    }

    public String getReqresetUpdatetime() {
        return this.reqresetUpdatetime;
    }
}