package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ����� Ȩ�ز������ñ�ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbCalculateTwoProportion extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * ���� code
     */
    @EntityParaAnno(zhName = "���� code")
    private String code;
    /**
     * ����name
     */
    @EntityParaAnno(zhName = "����name")
    private String name;
    /**
     * ָ�����1-��ָ�ꣻ2-��ָ�ꣻ4-����ָ�꣩
     */
    @EntityParaAnno(zhName = "ָ�����1-��ָ�ꣻ2-��ָ�ꣻ4-����ָ�꣩")
    private BigDecimal indexType;
    /**
     * ˳�����(1-����2-����)
     */
    @EntityParaAnno(zhName = "˳�����(1-����2-����)")
    private BigDecimal sortType;
    /**
     * Ȩ��ϵ��
     */
    @EntityParaAnno(zhName = "Ȩ��ϵ��")
    private BigDecimal weight;

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
     * ���� code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCode() {
        return this.code;
    }

    /**
     * ����name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getName() {
        return this.name;
    }

    /**
     * ָ�����1-��ָ�ꣻ2-��ָ�ꣻ4-����ָ�꣩
     */
    public void setIndexType(BigDecimal indexType) {
        this.indexType = indexType;
    }

    public BigDecimal getIndexType() {
        return this.indexType;
    }

    /**
     * ˳�����(1-����2-����)
     */
    public void setSortType(BigDecimal sortType) {
        this.sortType = sortType;
    }

    public BigDecimal getSortType() {
        return this.sortType;
    }

    /**
     * Ȩ��ϵ��
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }
}