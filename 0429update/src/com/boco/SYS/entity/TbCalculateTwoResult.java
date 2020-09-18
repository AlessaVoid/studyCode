package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ����� �����ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbCalculateTwoResult extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * organcode
     */
    @EntityParaAnno(zhName = "organcode")
    private String organcode;
    /**
     * month
     */
    @EntityParaAnno(zhName = "month")
    private String month;
    /**
     * ����Ŵ��ƻ�
     */
    @EntityParaAnno(zhName = "����Ŵ��ƻ�")
    private BigDecimal code1;
    /**
     * ��ȼƻ��ڷ��м����
     */
    @EntityParaAnno(zhName = "��ȼƻ��ڷ��м����")
    private BigDecimal code2;
    /**
     * ��ȼƻ�ͳһ�зֶ��
     */
    @EntityParaAnno(zhName = "��ȼƻ�ͳһ�зֶ��")
    private BigDecimal code3;
    /**
     * ������ĩEVA
     */
    @EntityParaAnno(zhName = "������ĩEVA")
    private BigDecimal code4;
    /**
     * �����������ĩ�·�����
     */
    @EntityParaAnno(zhName = "�����������ĩ�·�����")
    private BigDecimal code5;
    /**
     * ������ĩ������
     */
    @EntityParaAnno(zhName = "������ĩ������")
    private BigDecimal code6;
    /**
     * ������ĩ��Ӫ���ƻ������
     */
    @EntityParaAnno(zhName = "������ĩ��Ӫ���ƻ������")
    private BigDecimal code7;
    /**
     * ������ĩ�����ʱ��㱨��
     */
    @EntityParaAnno(zhName = "������ĩ�����ʱ��㱨��")
    private BigDecimal code8;
    /**
     * ԭ�ݶ�
     */
    @EntityParaAnno(zhName = "ԭ�ݶ�")
    private BigDecimal code9;
    /**
     * �·������ʵ���
     */
    @EntityParaAnno(zhName = "�·������ʵ���")
    private BigDecimal code10;
    /**
     * �����ʵ���
     */
    @EntityParaAnno(zhName = "�����ʵ���")
    private BigDecimal code11;
    /**
     * ��Ӫ���ƻ�����ʵ���
     */
    @EntityParaAnno(zhName = "��Ӫ���ƻ�����ʵ���")
    private BigDecimal code12;
    /**
     * �����ʱ��㱨�ʵ���
     */
    @EntityParaAnno(zhName = "�����ʱ��㱨�ʵ���")
    private BigDecimal code13;
    /**
     * ������ݶ�
     */
    @EntityParaAnno(zhName = "������ݶ�")
    private BigDecimal code14;
    /**
     * �Ż��������
     */
    @EntityParaAnno(zhName = "�Ż��������")
    private BigDecimal code15;
    /**
     * �����ƻ��������
     */
    @EntityParaAnno(zhName = "�����ƻ��������")
    private BigDecimal code16;
    /**
     * ��ԭ�ݶ����
     */
    @EntityParaAnno(zhName = "��ԭ�ݶ����")
    private BigDecimal code17;
    /**
     * �ݶ��������
     */
    @EntityParaAnno(zhName = "�ݶ��������")
    private BigDecimal code18;
    /**
     * ��������
     */
    @EntityParaAnno(zhName = "��������")
    private String code19;
    /**
     * ���������ƽ�����ƻ������ý��
     */
    @EntityParaAnno(zhName = "���������ƽ�����ƻ������ý��")
    private BigDecimal code20;
    /**
     * �ֹ�����ǰ�ƻ�
     */
    @EntityParaAnno(zhName = "�ֹ�����ǰ�ƻ�")
    private BigDecimal code21;
    /**
     * �ֹ�������
     */
    @EntityParaAnno(zhName = "�ֹ�������")
    private BigDecimal code22;
    /**
     * �����¶ȼƻ����
     */
    @EntityParaAnno(zhName = "�����¶ȼƻ����")
    private BigDecimal code23;

    /**
     * ʱ��ƻ�ά���� Ԥ�Ƽƻ��ܽ��
     */
    @EntityParaAnno(zhName = "�����¶ȼƻ����")
    private BigDecimal totalNum;


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
     * organcode
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    public BigDecimal getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(BigDecimal totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * month
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonth() {
        return this.month;
    }

    /**
     * ����Ŵ��ƻ�
     */
    public void setCode1(BigDecimal code1) {
        this.code1 = code1;
    }

    public BigDecimal getCode1() {
        return this.code1;
    }

    /**
     * ��ȼƻ��ڷ��м����
     */
    public void setCode2(BigDecimal code2) {
        this.code2 = code2;
    }

    public BigDecimal getCode2() {
        return this.code2;
    }

    /**
     * ��ȼƻ�ͳһ�зֶ��
     */
    public void setCode3(BigDecimal code3) {
        this.code3 = code3;
    }

    public BigDecimal getCode3() {
        return this.code3;
    }

    /**
     * ������ĩEVA
     */
    public void setCode4(BigDecimal code4) {
        this.code4 = code4;
    }

    public BigDecimal getCode4() {
        return this.code4;
    }

    /**
     * �����������ĩ�·�����
     */
    public void setCode5(BigDecimal code5) {
        this.code5 = code5;
    }

    public BigDecimal getCode5() {
        return this.code5;
    }

    /**
     * ������ĩ������
     */
    public void setCode6(BigDecimal code6) {
        this.code6 = code6;
    }

    public BigDecimal getCode6() {
        return this.code6;
    }

    /**
     * ������ĩ��Ӫ���ƻ������
     */
    public void setCode7(BigDecimal code7) {
        this.code7 = code7;
    }

    public BigDecimal getCode7() {
        return this.code7;
    }

    /**
     * ������ĩ�����ʱ��㱨��
     */
    public void setCode8(BigDecimal code8) {
        this.code8 = code8;
    }

    public BigDecimal getCode8() {
        return this.code8;
    }

    /**
     * ԭ�ݶ�
     */
    public void setCode9(BigDecimal code9) {
        this.code9 = code9;
    }

    public BigDecimal getCode9() {
        return this.code9;
    }

    /**
     * �·������ʵ���
     */
    public void setCode10(BigDecimal code10) {
        this.code10 = code10;
    }

    public BigDecimal getCode10() {
        return this.code10;
    }

    /**
     * �����ʵ���
     */
    public void setCode11(BigDecimal code11) {
        this.code11 = code11;
    }

    public BigDecimal getCode11() {
        return this.code11;
    }

    /**
     * ��Ӫ���ƻ�����ʵ���
     */
    public void setCode12(BigDecimal code12) {
        this.code12 = code12;
    }

    public BigDecimal getCode12() {
        return this.code12;
    }

    /**
     * �����ʱ��㱨�ʵ���
     */
    public void setCode13(BigDecimal code13) {
        this.code13 = code13;
    }

    public BigDecimal getCode13() {
        return this.code13;
    }

    /**
     * ������ݶ�
     */
    public void setCode14(BigDecimal code14) {
        this.code14 = code14;
    }

    public BigDecimal getCode14() {
        return this.code14;
    }

    /**
     * �Ż��������
     */
    public void setCode15(BigDecimal code15) {
        this.code15 = code15;
    }

    public BigDecimal getCode15() {
        return this.code15;
    }

    /**
     * �����ƻ��������
     */
    public void setCode16(BigDecimal code16) {
        this.code16 = code16;
    }

    public BigDecimal getCode16() {
        return this.code16;
    }

    /**
     * ��ԭ�ݶ����
     */
    public void setCode17(BigDecimal code17) {
        this.code17 = code17;
    }

    public BigDecimal getCode17() {
        return this.code17;
    }

    /**
     * �ݶ��������
     */
    public void setCode18(BigDecimal code18) {
        this.code18 = code18;
    }

    public BigDecimal getCode18() {
        return this.code18;
    }

    /**
     * ��������
     */
    public void setCode19(String code19) {
        this.code19 = code19;
    }

    public String getCode19() {
        return this.code19;
    }

    /**
     * ���������ƽ�����ƻ������ý��
     */
    public void setCode20(BigDecimal code20) {
        this.code20 = code20;
    }

    public BigDecimal getCode20() {
        return this.code20;
    }

    /**
     * �ֹ�����ǰ�ƻ�
     */
    public void setCode21(BigDecimal code21) {
        this.code21 = code21;
    }

    public BigDecimal getCode21() {
        return this.code21;
    }

    /**
     * �ֹ�������
     */
    public void setCode22(BigDecimal code22) {
        this.code22 = code22;
    }

    public BigDecimal getCode22() {
        return this.code22;
    }

    /**
     * �����¶ȼƻ����
     */
    public void setCode23(BigDecimal code23) {
        this.code23 = code23;
    }

    public BigDecimal getCode23() {
        return this.code23;
    }
}