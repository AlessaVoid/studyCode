package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 测算 权重表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbCalculateOneProportion extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * 4个类别(1-存款类，2-贷款类，4-需求类，8-效益类)
     */
    @EntityParaAnno(zhName = "4个类别(1-存款类，2-贷款类，4-需求类，8-效益类)")
    private BigDecimal classType;
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
     * 类别占比系数
     */
    @EntityParaAnno(zhName = "类别占比系数")
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

    /** setter\getter方法 */
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