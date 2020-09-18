package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * ����-�����ص�����Ϣ��ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbKeyReportOrgan extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private String organcode;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String organname;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String organlevel;
    /**
     * �ϼ�������
     */
    @EntityParaAnno(zhName = "�ϼ�������")
    private String uporgan;
    /**
     * �ϼ���������
     */
    @EntityParaAnno(zhName = "�ϼ���������")
    private String uporganname;
    /**
     * �����ֶ�
     */
    @EntityParaAnno(zhName = "�����ֶ�")
    private Integer organorder;

    /** setter\getter���� */
    /**
     * ������
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    /**
     * ��������
     */
    public void setOrganname(String organname) {
        this.organname = organname == null ? null : organname.trim();
    }

    public String getOrganname() {
        return this.organname;
    }

    /**
     * ��������
     */
    public void setOrganlevel(String organlevel) {
        this.organlevel = organlevel == null ? null : organlevel.trim();
    }

    public String getOrganlevel() {
        return this.organlevel;
    }

    /**
     * �ϼ�������
     */
    public void setUporgan(String uporgan) {
        this.uporgan = uporgan == null ? null : uporgan.trim();
    }

    public String getUporgan() {
        return this.uporgan;
    }

    /**
     * �ϼ���������
     */
    public void setUporganname(String uporganname) {
        this.uporganname = uporganname == null ? null : uporganname.trim();
    }

    public String getUporganname() {
        return this.uporganname;
    }

    /**
     * �����ֶ�
     */
    public void setOrganorder(Integer organorder) {
        this.organorder = organorder;
    }

    public Integer getOrganorder() {
        return this.organorder;
    }
}