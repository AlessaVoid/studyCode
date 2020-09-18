package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * TbCalculateThreeProportion实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCalculateThreeProportion extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * code
     */
    @EntityParaAnno(zhName = "code")
    private String code;
    /**
     * name
     */
    @EntityParaAnno(zhName = "name")
    private String name;
    /**
     * orderNum
     */
    @EntityParaAnno(zhName = "orderNum")
    private Integer orderNum;
    /**
     * proportion
     */
    @EntityParaAnno(zhName = "proportion")
    private BigDecimal proportion;
    /**
     * commonProportion
     */
    @EntityParaAnno(zhName = "commonProportion")
    private BigDecimal commonProportion;

    /** setter\getter方法 */
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
     * code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCode() {
        return this.code;
    }

    /**
     * name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getName() {
        return this.name;
    }

    /**
     * orderNum
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    /**
     * proportion
     */
    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public BigDecimal getProportion() {
        return this.proportion;
    }

    /**
     * commonProportion
     */
    public void setCommonProportion(BigDecimal commonProportion) {
        this.commonProportion = commonProportion;
    }

    public BigDecimal getCommonProportion() {
        return this.commonProportion;
    }
}