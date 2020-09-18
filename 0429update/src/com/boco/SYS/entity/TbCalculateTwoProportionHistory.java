package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 测算二 权重参数 历史表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCalculateTwoProportionHistory extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * 参数code
     */
    @EntityParaAnno(zhName = "参数code")
    private String code;
    /**
     * 参数name
     */
    @EntityParaAnno(zhName = "参数name")
    private String name;
    /**
     * 指标类别(1-主指标，2-副指标，4-无用指标)
     */
    @EntityParaAnno(zhName = "指标类别(1-主指标，2-副指标，4-无用指标)")
    private BigDecimal indexType;
    /**
     * 顺序类别(1-升序，2-降序)
     */
    @EntityParaAnno(zhName = "顺序类别(1-升序，2-降序)")
    private BigDecimal sortType;
    /**
     * 权重系数
     */
    @EntityParaAnno(zhName = "权重系数")
    private BigDecimal weight;
    /**
     * 历史配置月份
     */
    @EntityParaAnno(zhName = "历史配置月份")
    private String month;

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
     * 参数code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCode() {
        return this.code;
    }

    /**
     * 参数name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getName() {
        return this.name;
    }

    /**
     * 指标类别(1-主指标，2-副指标，4-无用指标)
     */
    public void setIndexType(BigDecimal indexType) {
        this.indexType = indexType;
    }

    public BigDecimal getIndexType() {
        return this.indexType;
    }

    /**
     * 顺序类别(1-升序，2-降序)
     */
    public void setSortType(BigDecimal sortType) {
        this.sortType = sortType;
    }

    public BigDecimal getSortType() {
        return this.sortType;
    }

    /**
     * 权重系数
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    /**
     * 历史配置月份
     */
    public void setMonth(String month) {
        this.month = month.trim();
    }

    public String getMonth() {
        return this.month;
    }
}