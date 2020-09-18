package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 测算二 结果表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCalculateTwoResult extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
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
     * 年度信贷计划
     */
    @EntityParaAnno(zhName = "年度信贷计划")
    private BigDecimal code1;
    /**
     * 年度计划在分行间比例
     */
    @EntityParaAnno(zhName = "年度计划在分行间比例")
    private BigDecimal code2;
    /**
     * 年度计划统一切分额度
     */
    @EntityParaAnno(zhName = "年度计划统一切分额度")
    private BigDecimal code3;
    /**
     * 上月月末EVA
     */
    @EntityParaAnno(zhName = "上月月末EVA")
    private BigDecimal code4;
    /**
     * 年初到上月月末新发生率
     */
    @EntityParaAnno(zhName = "年初到上月月末新发生率")
    private BigDecimal code5;
    /**
     * 上月月末不良率
     */
    @EntityParaAnno(zhName = "上月月末不良率")
    private BigDecimal code6;
    /**
     * 上月月末自营存款计划完成率
     */
    @EntityParaAnno(zhName = "上月月末自营存款计划完成率")
    private BigDecimal code7;
    /**
     * 上月月末经济资本汇报率
     */
    @EntityParaAnno(zhName = "上月月末经济资本汇报率")
    private BigDecimal code8;
    /**
     * 原份额
     */
    @EntityParaAnno(zhName = "原份额")
    private BigDecimal code9;
    /**
     * 新发生利率调整
     */
    @EntityParaAnno(zhName = "新发生利率调整")
    private BigDecimal code10;
    /**
     * 不良率调整
     */
    @EntityParaAnno(zhName = "不良率调整")
    private BigDecimal code11;
    /**
     * 自营存款计划完成率调整
     */
    @EntityParaAnno(zhName = "自营存款计划完成率调整")
    private BigDecimal code12;
    /**
     * 经济资本汇报率调整
     */
    @EntityParaAnno(zhName = "经济资本汇报率调整")
    private BigDecimal code13;
    /**
     * 调整后份额
     */
    @EntityParaAnno(zhName = "调整后份额")
    private BigDecimal code14;
    /**
     * 优化额度配置
     */
    @EntityParaAnno(zhName = "优化额度配置")
    private BigDecimal code15;
    /**
     * 调整计划金额排名
     */
    @EntityParaAnno(zhName = "调整计划金额排名")
    private BigDecimal code16;
    /**
     * 与原份额差异
     */
    @EntityParaAnno(zhName = "与原份额差异")
    private BigDecimal code17;
    /**
     * 份额差异排名
     */
    @EntityParaAnno(zhName = "份额差异排名")
    private BigDecimal code18;
    /**
     * 分行名称
     */
    @EntityParaAnno(zhName = "分行名称")
    private String code19;
    /**
     * 年初到上月平均超计划或闲置金额
     */
    @EntityParaAnno(zhName = "年初到上月平均超计划或闲置金额")
    private BigDecimal code20;
    /**
     * 手工调节前计划
     */
    @EntityParaAnno(zhName = "手工调节前计划")
    private BigDecimal code21;
    /**
     * 手工调整项
     */
    @EntityParaAnno(zhName = "手工调整项")
    private BigDecimal code22;
    /**
     * 最终月度计划额度
     */
    @EntityParaAnno(zhName = "最终月度计划额度")
    private BigDecimal code23;

    /**
     * 时间计划维护的 预计计划总金额
     */
    @EntityParaAnno(zhName = "最终月度计划额度")
    private BigDecimal totalNum;


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
     * 年度信贷计划
     */
    public void setCode1(BigDecimal code1) {
        this.code1 = code1;
    }

    public BigDecimal getCode1() {
        return this.code1;
    }

    /**
     * 年度计划在分行间比例
     */
    public void setCode2(BigDecimal code2) {
        this.code2 = code2;
    }

    public BigDecimal getCode2() {
        return this.code2;
    }

    /**
     * 年度计划统一切分额度
     */
    public void setCode3(BigDecimal code3) {
        this.code3 = code3;
    }

    public BigDecimal getCode3() {
        return this.code3;
    }

    /**
     * 上月月末EVA
     */
    public void setCode4(BigDecimal code4) {
        this.code4 = code4;
    }

    public BigDecimal getCode4() {
        return this.code4;
    }

    /**
     * 年初到上月月末新发生率
     */
    public void setCode5(BigDecimal code5) {
        this.code5 = code5;
    }

    public BigDecimal getCode5() {
        return this.code5;
    }

    /**
     * 上月月末不良率
     */
    public void setCode6(BigDecimal code6) {
        this.code6 = code6;
    }

    public BigDecimal getCode6() {
        return this.code6;
    }

    /**
     * 上月月末自营存款计划完成率
     */
    public void setCode7(BigDecimal code7) {
        this.code7 = code7;
    }

    public BigDecimal getCode7() {
        return this.code7;
    }

    /**
     * 上月月末经济资本汇报率
     */
    public void setCode8(BigDecimal code8) {
        this.code8 = code8;
    }

    public BigDecimal getCode8() {
        return this.code8;
    }

    /**
     * 原份额
     */
    public void setCode9(BigDecimal code9) {
        this.code9 = code9;
    }

    public BigDecimal getCode9() {
        return this.code9;
    }

    /**
     * 新发生利率调整
     */
    public void setCode10(BigDecimal code10) {
        this.code10 = code10;
    }

    public BigDecimal getCode10() {
        return this.code10;
    }

    /**
     * 不良率调整
     */
    public void setCode11(BigDecimal code11) {
        this.code11 = code11;
    }

    public BigDecimal getCode11() {
        return this.code11;
    }

    /**
     * 自营存款计划完成率调整
     */
    public void setCode12(BigDecimal code12) {
        this.code12 = code12;
    }

    public BigDecimal getCode12() {
        return this.code12;
    }

    /**
     * 经济资本汇报率调整
     */
    public void setCode13(BigDecimal code13) {
        this.code13 = code13;
    }

    public BigDecimal getCode13() {
        return this.code13;
    }

    /**
     * 调整后份额
     */
    public void setCode14(BigDecimal code14) {
        this.code14 = code14;
    }

    public BigDecimal getCode14() {
        return this.code14;
    }

    /**
     * 优化额度配置
     */
    public void setCode15(BigDecimal code15) {
        this.code15 = code15;
    }

    public BigDecimal getCode15() {
        return this.code15;
    }

    /**
     * 调整计划金额排名
     */
    public void setCode16(BigDecimal code16) {
        this.code16 = code16;
    }

    public BigDecimal getCode16() {
        return this.code16;
    }

    /**
     * 与原份额差异
     */
    public void setCode17(BigDecimal code17) {
        this.code17 = code17;
    }

    public BigDecimal getCode17() {
        return this.code17;
    }

    /**
     * 份额差异排名
     */
    public void setCode18(BigDecimal code18) {
        this.code18 = code18;
    }

    public BigDecimal getCode18() {
        return this.code18;
    }

    /**
     * 分行名称
     */
    public void setCode19(String code19) {
        this.code19 = code19;
    }

    public String getCode19() {
        return this.code19;
    }

    /**
     * 年初到上月平均超计划或闲置金额
     */
    public void setCode20(BigDecimal code20) {
        this.code20 = code20;
    }

    public BigDecimal getCode20() {
        return this.code20;
    }

    /**
     * 手工调节前计划
     */
    public void setCode21(BigDecimal code21) {
        this.code21 = code21;
    }

    public BigDecimal getCode21() {
        return this.code21;
    }

    /**
     * 手工调整项
     */
    public void setCode22(BigDecimal code22) {
        this.code22 = code22;
    }

    public BigDecimal getCode22() {
        return this.code22;
    }

    /**
     * 最终月度计划额度
     */
    public void setCode23(BigDecimal code23) {
        this.code23 = code23;
    }

    public BigDecimal getCode23() {
        return this.code23;
    }
}