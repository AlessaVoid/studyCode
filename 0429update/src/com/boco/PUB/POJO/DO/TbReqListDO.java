package com.boco.PUB.POJO.DO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * �·��Ŵ�������Ҫ���  ҳ�����ݴ������
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqListDO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = -5072824609477947413L;


    /** ���� */
    /**
     * ���id
     */
    @EntityParaAnno(zhName = "���id")
    private Integer reqId;
    /**
     * ���������·�
     */
    @EntityParaAnno(zhName = "���������·�")
    private String reqMonth;
    /**
     * ���󷢲�����
     */
    @EntityParaAnno(zhName = "���󷢲�����")
    private Integer reqOragn;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private Integer reqType;
    /**
     * ����״̬
     */
    @EntityParaAnno(zhName = "����״̬")
    private Integer reqState;
    /**
     * �������ʼʱ��
     */
    @EntityParaAnno(zhName = "�������ʼʱ��")
    private String reqDateStart;
    /**
     * ���������ʱ��
     */
    @EntityParaAnno(zhName = "���������ʱ��")
    private String reqDateEnd;
    /**
     * �����id
     */
    @EntityParaAnno(zhName = "�����id")
    private Integer reqAttachmentId;
    /**
     * �˵��
     */
    @EntityParaAnno(zhName = "�˵��")
    private String reqNote;
    /**
     * �·�������
     */
    @EntityParaAnno(zhName = "�·�������")
    private Integer reqTo;
    /**
     * ������Աid
     */
    @EntityParaAnno(zhName = "������Աid")
    private String reqCreateOper;
    /**
     * ������Աid
     */
    @EntityParaAnno(zhName = "������Աid")
    private String reqUpdateOper;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String reqCreatetime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String reqUpdatetime;

    /** setter\getter���� */
    /**
     * ���id
     */
    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getReqId() {
        return this.reqId;
    }

    /**
     * ���������·�
     */
    public void setReqMonth(String reqMonth) {
        this.reqMonth = reqMonth == null ? null : reqMonth.trim();
    }

    public String getReqMonth() {
        return this.reqMonth;
    }

    /**
     * ���󷢲�����
     */
    public void setReqOragn(Integer reqOragn) {
        this.reqOragn = reqOragn;
    }

    public Integer getReqOragn() {
        return this.reqOragn;
    }

    /**
     * ��������
     */
    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public Integer getReqType() {
        return this.reqType;
    }

    /**
     * ����״̬
     */
    public void setReqState(Integer reqState) {
        this.reqState = reqState;
    }

    public Integer getReqState() {
        return this.reqState;
    }

    /**
     * �������ʼʱ��
     */
    public void setReqDateStart(String reqDateStart) {
        this.reqDateStart = reqDateStart == null ? null : reqDateStart.trim();
    }

    public String getReqDateStart() {
        return this.reqDateStart;
    }

    /**
     * ���������ʱ��
     */
    public void setReqDateEnd(String reqDateEnd) {
        this.reqDateEnd = reqDateEnd == null ? null : reqDateEnd.trim();
    }

    public String getReqDateEnd() {
        return this.reqDateEnd;
    }

    /**
     * �����id
     */
    public void setReqAttachmentId(Integer reqAttachmentId) {
        this.reqAttachmentId = reqAttachmentId;
    }

    public Integer getReqAttachmentId() {
        return this.reqAttachmentId;
    }

    /**
     * �˵��
     */
    public void setReqNote(String reqNote) {
        this.reqNote = reqNote == null ? null : reqNote.trim();
    }

    public String getReqNote() {
        return this.reqNote;
    }

    /**
     * �·�������
     */
    public void setReqTo(Integer reqTo) {
        this.reqTo = reqTo;
    }

    public Integer getReqTo() {
        return this.reqTo;
    }

    /**
     * ������Աid
     */
    public void setReqCreateOper(String reqCreateOper) {
        this.reqCreateOper = reqCreateOper == null ? null : reqCreateOper.trim();
    }

    public String getReqCreateOper() {
        return this.reqCreateOper;
    }

    /**
     * ������Աid
     */
    public void setReqUpdateOper(String reqUpdateOper) {
        this.reqUpdateOper = reqUpdateOper == null ? null : reqUpdateOper.trim();
    }

    public String getReqUpdateOper() {
        return this.reqUpdateOper;
    }

    /**
     * ����ʱ��
     */
    public void setReqCreatetime(String reqCreatetime) {
        this.reqCreatetime = reqCreatetime == null ? null : reqCreatetime.trim();
    }

    public String getReqCreatetime() {
        return this.reqCreatetime;
    }

    /**
     * ����ʱ��
     */
    public void setReqUpdatetime(String reqUpdatetime) {
        this.reqUpdatetime = reqUpdatetime == null ? null : reqUpdatetime.trim();
    }

    public String getReqUpdatetime() {
        return this.reqUpdatetime;
    }
}