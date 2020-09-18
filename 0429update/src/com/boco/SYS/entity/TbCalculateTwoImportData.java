package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 测算二  历史数据导入表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCalculateTwoImportData extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * type
     */
    @EntityParaAnno(zhName = "type")
    private Integer type;
    /**
     * 机构号
     */
    @EntityParaAnno(zhName = "机构号")
    private String organcode;
    /**
     * 月份
     */
    @EntityParaAnno(zhName = "月份")
    private String month;
    /**
     * 上月末EVA
     */
    @EntityParaAnno(zhName = "上月末EVA")
    private BigDecimal code1;
    /**
     * 年初到上月新发生利率
     */
    @EntityParaAnno(zhName = "年初到上月新发生利率")
    private BigDecimal code2;
    /**
     * 上月末不良率
     */
    @EntityParaAnno(zhName = "上月末不良率")
    private BigDecimal code3;
    /**
     * 上月末自营存款计划完成率
     */
    @EntityParaAnno(zhName = "上月末自营存款计划完成率")
    private BigDecimal code4;
    /**
     * 上月末经济资本回报率
     */
    @EntityParaAnno(zhName = "上月末经济资本回报率")
    private BigDecimal code5;
    /**
     * code6
     */
    @EntityParaAnno(zhName = "code6")
    private BigDecimal code6;
    /**
     * code7
     */
    @EntityParaAnno(zhName = "code7")
    private BigDecimal code7;
    /**
     * code8
     */
    @EntityParaAnno(zhName = "code8")
    private BigDecimal code8;

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
     * type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    /**
     * 机构号
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    /**
     * 月份
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonth() {
        return this.month;
    }

    public BigDecimal getCode1() {
        return code1;
    }

    public void setCode1(BigDecimal code1) {
        this.code1 = code1;
    }

    public BigDecimal getCode2() {
        return code2;
    }

    public void setCode2(BigDecimal code2) {
        this.code2 = code2;
    }

    public BigDecimal getCode3() {
        return code3;
    }

    public void setCode3(BigDecimal code3) {
        this.code3 = code3;
    }

    public BigDecimal getCode4() {
        return code4;
    }

    public void setCode4(BigDecimal code4) {
        this.code4 = code4;
    }

    public BigDecimal getCode5() {
        return code5;
    }

    public void setCode5(BigDecimal code5) {
        this.code5 = code5;
    }

    public BigDecimal getCode6() {
        return code6;
    }

    public void setCode6(BigDecimal code6) {
        this.code6 = code6;
    }

    public BigDecimal getCode7() {
        return code7;
    }

    public void setCode7(BigDecimal code7) {
        this.code7 = code7;
    }

    public BigDecimal getCode8() {
        return code8;
    }

    public void setCode8(BigDecimal code8) {
        this.code8 = code8;
    }
}