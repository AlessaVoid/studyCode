package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ���� Ȩ�ر�ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbCalculateOneProportion extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * 4�����(1-����࣬2-�����࣬4-�����࣬8-Ч����)
     */
    @EntityParaAnno(zhName = "4�����(1-����࣬2-�����࣬4-�����࣬8-Ч����)")
    private BigDecimal classType;
    /**
     * ����code
     */
    @EntityParaAnno(zhName = "����code")
    private String code;
    /**
     * ����name
     */
    @EntityParaAnno(zhName = "����name")
    private String name;
    /**
     * ָ�����(1-��ָ�꣬2-��ָ�꣬4-����ָ��)
     */
    @EntityParaAnno(zhName = "ָ�����(1-��ָ�꣬2-��ָ�꣬4-����ָ��)")
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
    /**
     * ���ռ��ϵ��
     */
    @EntityParaAnno(zhName = "���ռ��ϵ��")
    private BigDecimal ratio;
    /**
     * createTime
     */
    @EntityParaAnno(zhName = "createTime")
    private String createTime;
    /**
     * updateTime
     */
    @EntityParaAnno(zhName = "updateTime")
    private String updateTime;
    /**
     * createOper
     */
    @EntityParaAnno(zhName = "createOper")
    private String createOper;
    /**
     * updateOper
     */
    @EntityParaAnno(zhName = "updateOper")
    private String updateOper;

    /** setter\getter���� */
    /**
     * id
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getClassType() {
        return classType;
    }

    public void setClassType(BigDecimal classType) {
        this.classType = classType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIndexType() {
        return indexType;
    }

    public void setIndexType(BigDecimal indexType) {
        this.indexType = indexType;
    }

    public BigDecimal getSortType() {
        return sortType;
    }

    public void setSortType(BigDecimal sortType) {
        this.sortType = sortType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateOper() {
        return createOper;
    }

    public void setCreateOper(String createOper) {
        this.createOper = createOper;
    }

    public String getUpdateOper() {
        return updateOper;
    }

    public void setUpdateOper(String updateOper) {
        this.updateOper = updateOper;
    }
}