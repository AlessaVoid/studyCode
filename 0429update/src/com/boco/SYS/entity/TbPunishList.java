package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * TbPunishListʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbPunishList extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;


    /**
     * �ݸ�
     */
    public static final int STATE_DRAFT = 0;
    /**
     * �½�
     */
    public static final int STATE_NEW = 1;
    /**
     * ���·�
     */
    public static final int STATE_FILL = 2;
    /**
     * ���ύ��������
     */
    public static final int STATE_SUBMITED = 4;
    /**
     * ������
     */
    public static final int STATE_APPROVALING = 8;
    /**
     * ����ͨ�������ϱ�
     */
    public static final int STATE_APPROVED = 16;
    /**
     * �Ѳ���
     */
    public static final int STATE_DISMISSED = 32;

    /** ���� */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private Integer id;
    /**
     * �����·�
     */
    @EntityParaAnno(zhName = "�����·�")
    private String month;
    /**
     * ��Ϣ����
     */
    @EntityParaAnno(zhName = "��Ϣ����")
    private String name;
    /**
     * ��Ϣ˵��
     */
    @EntityParaAnno(zhName = "��Ϣ˵��")
    private String note;
    /**
     * ��Ϣ״̬
     */
    @EntityParaAnno(zhName = "��Ϣ״̬")
    private Integer state;
    /**
     * ���������ֹʱ��
     */
    @EntityParaAnno(zhName = "���������ֹʱ��")
    private String collectEndTime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String creaeTime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String updateTime;

    /** setter\getter���� */
    /**
     * id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    /**
     * �����·�
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonth() {
        return this.month;
    }

    /**
     * ��Ϣ����
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getName() {
        return this.name;
    }

    /**
     * ��Ϣ˵��
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getNote() {
        return this.note;
    }

    /**
     * ��Ϣ״̬
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    /**
     * ���������ֹʱ��
     */
    public void setCollectEndTime(String collectEndTime) {
        this.collectEndTime = collectEndTime == null ? null : collectEndTime.trim();
    }

    public String getCollectEndTime() {
        return this.collectEndTime;
    }

    /**
     * ����ʱ��
     */
    public void setCreaeTime(String creaeTime) {
        this.creaeTime = creaeTime == null ? null : creaeTime.trim();
    }

    public String getCreaeTime() {
        return this.creaeTime;
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
}