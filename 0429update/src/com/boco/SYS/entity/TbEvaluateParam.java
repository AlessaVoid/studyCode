package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 评分管理参数表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbEvaluateParam extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /**
     * 新建
     */
    public static final Integer STATE_NEW = 1;
    /**
     * 已部署
     */
    public static final Integer STATE_DEPLOYED = 2;


    /** 属性 */
    /**
     * 评价参数id
     */
    @EntityParaAnno(zhName = "评价参数id")
    private Integer tpId;
    /**
     * 评分名称
     */
    @EntityParaAnno(zhName = "评分名称")
    private String tpName;
    /**
     * 贷种组合id
     */
    @EntityParaAnno(zhName = "贷种组合id")
    private String tpComb;
    /**
     * 满分
     */
    @EntityParaAnno(zhName = "满分")
    private Integer tpFullScore;
    /**
     * 区间最小值
     */
    @EntityParaAnno(zhName = "区间最小值")
    private Double tpMin;
    /**
     * 区间最大值
     */
    @EntityParaAnno(zhName = "区间最大值")
    private Double tpMax;
    /**
     * 扣分标准
     */
    @EntityParaAnno(zhName = "扣分标准")
    private Integer tpDeduction;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String tpCreateTime;
    /**
     * 创建人员
     */
    @EntityParaAnno(zhName = "创建人员")
    private String tpCreateOper;
    /**
     * 状态
     */
    @EntityParaAnno(zhName = "状态")
    private Integer tpState;

    /** setter\getter方法 */
    /**
     * 评价参数id
     */
    public void setTpId(Integer tpId) {
        this.tpId = tpId;
    }

    public Integer getTpId() {
        return this.tpId;
    }

    /**
     * 评分名称
     */
    public void setTpName(String tpName) {
        this.tpName = tpName == null ? null : tpName.trim();
    }

    public String getTpName() {
        return this.tpName;
    }

    /**
     * 贷种组合id
     */
    public void setTpComb(String tpComb) {
        this.tpComb = tpComb == null ? null : tpComb.trim();
    }

    public String getTpComb() {
        return this.tpComb;
    }

    /**
     * 满分
     */
    public void setTpFullScore(Integer tpFullScore) {
        this.tpFullScore = tpFullScore;
    }

    public Integer getTpFullScore() {
        return this.tpFullScore;
    }

    /**
     * 区间最小值
     */
    public void setTpMin(Double tpMin) {
        this.tpMin = tpMin;
    }

    public Double getTpMin() {
        return this.tpMin;
    }

    /**
     * 区间最大值
     */
    public void setTpMax(Double tpMax) {
        this.tpMax = tpMax;
    }

    public Double getTpMax() {
        return this.tpMax;
    }

    /**
     * 扣分标准
     */
    public void setTpDeduction(Integer tpDeduction) {
        this.tpDeduction = tpDeduction;
    }

    public Integer getTpDeduction() {
        return this.tpDeduction;
    }

    /**
     * 创建时间
     */
    public void setTpCreateTime(String tpCreateTime) {
        this.tpCreateTime = tpCreateTime == null ? null : tpCreateTime.trim();
    }

    public String getTpCreateTime() {
        return this.tpCreateTime;
    }

    /**
     * 创建人员
     */
    public void setTpCreateOper(String tpCreateOper) {
        this.tpCreateOper = tpCreateOper == null ? null : tpCreateOper.trim();
    }

    public String getTpCreateOper() {
        return this.tpCreateOper;
    }

    /**
     * 状态
     */
    public void setTpState(Integer tpState) {
        this.tpState = tpState;
    }

    public Integer getTpState() {
        return this.tpState;
    }
}