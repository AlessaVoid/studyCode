package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * FdReportOrganʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class FdReportOrgan extends BaseEntity implements java.io.Serializable {
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
     * ��һ�����򻮷�(1-���� 2-�������� 3-�������� 4-�������� 5-���е��� 6-���ϵ��� 7-���ϵ��� 8-��������)
     */
    @EntityParaAnno(zhName = "��һ�����򻮷�(1-���� 2-�������� 3-�������� 4-�������� 5-���е��� 6-���ϵ��� 7-���ϵ��� 8-��������)")
    private Integer type1;
    /**
     * �ڶ������򻮷�(1-���� 2-������ 3-������ 4-������ 5-�в����� 6-�������� 7-��������)
     */
    @EntityParaAnno(zhName = "�ڶ������򻮷�(1-���� 2-������ 3-������ 4-������ 5-�в����� 6-�������� 7-��������)")
    private Integer type2;
    /**
     * ���������򻮷�(1-���� 2-��һ�� 3-�ڶ��� 4-������ 5-������)
     */
    @EntityParaAnno(zhName = "���������򻮷�(1-���� 2-��һ�� 3-�ڶ��� 4-������ 5-������)")
    private Integer type3;
    /**
     * remark
     */
    @EntityParaAnno(zhName = "remark")
    private Integer type4;
    /**
     * remark
     */
    @EntityParaAnno(zhName = "remark")
    private Integer type5;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
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
     * ��һ�����򻮷�(1-���� 2-�������� 3-�������� 4-�������� 5-���е��� 6-���ϵ��� 7-���ϵ��� 8-��������)
     */
    public void setType1(Integer type1) {
        this.type1 = type1;
    }

    public Integer getType1() {
        return this.type1;
    }

    /**
     * �ڶ������򻮷�(1-���� 2-������ 3-������ 4-������ 5-�в����� 6-�������� 7-��������)
     */
    public void setType2(Integer type2) {
        this.type2 = type2;
    }

    public Integer getType2() {
        return this.type2;
    }

    /**
     * ���������򻮷�(1-���� 2-��һ�� 3-�ڶ��� 4-������ 5-������)
     */
    public void setType3(Integer type3) {
        this.type3 = type3;
    }

    public Integer getType3() {
        return this.type3;
    }

    /**
     * remark
     */
    public void setType4(Integer type4) {
        this.type4 = type4;
    }

    public Integer getType4() {
        return this.type4;
    }

    /**
     * remark
     */
    public void setType5(Integer type5) {
        this.type5 = type5;
    }

    public Integer getType5() {
        return this.type5;
    }

    /**
     * ����
     */
    public void setOrganorder(Integer organorder) {
        this.organorder = organorder;
    }

    public Integer getOrganorder() {
        return this.organorder;
    }
}