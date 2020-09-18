package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 时间计划维护表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbTradeParam extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 参数id
     */
    @EntityParaAnno(zhName = "参数id  ")
    private java.lang.Integer paramId;
    /**
     * 所属周期
     */
    @EntityParaAnno(zhName = "所属周期")
    private java.lang.String paramPeriod;
    /**
     * 计划分配模式
     */
    @EntityParaAnno(zhName = "计划分配模式")
    private java.lang.Integer paramMode;
    /**
     * 罚息模板编号
     */
    @EntityParaAnno(zhName = "罚息模板编号")
    private java.lang.Integer paramPunishMode;
    /**
     * 需求录入开始时间
     */
    @EntityParaAnno(zhName = "需求录入开始时间")
    private java.lang.String paramReqStart;
    /**
     * 需求录入结束时间
     */
    @EntityParaAnno(zhName = "需求录入结束时间")
    private java.lang.String paramReqEnd;
    /**
     * 计划制定开始时间
     */
    @EntityParaAnno(zhName = "计划制定开始时间")
    private java.lang.String paramPlanStart;
    /**
     * 计划制定结束时间
     */
    @EntityParaAnno(zhName = "计划制定结束时间")
    private java.lang.String paramPlanEnd;
    /**
     * 罚息开始时间
     */
    @EntityParaAnno(zhName = "罚息开始时间")
    private java.lang.String paramPunishStart;
    /**
     * 罚息结束时间
     */
    @EntityParaAnno(zhName = "罚息结束时间")
    private java.lang.String paramPunsihEnd;
    /**
     * 参数创建时间
     */
    @EntityParaAnno(zhName = "参数创建时间")
    private java.lang.String paramCreatetime;
    /**
     * 参数更新时间
     */
    @EntityParaAnno(zhName = "参数更新时间")
    private java.lang.String paramUpdatetime;
    /**
     * 参数创建人员id
     */
    @EntityParaAnno(zhName = "参数创建人员id")
    private java.lang.String paramCreateuserid;
    /**
     * 参数更新人员id
     */
    @EntityParaAnno(zhName = "参数更新人员id")
    private java.lang.String paramUpdateuserid;
    /**
     * 当月机构计划净增量
     */
    @EntityParaAnno(zhName = "当月机构计划净增量")
    private BigDecimal paramMechIncrement;
    /**
     * 当月条线计划净增量
     */
    @EntityParaAnno(zhName = "当月条线计划净增量")
    private BigDecimal paramLineIncrement;
    /**
     * 当月超限额标准量
     */
    @EntityParaAnno(zhName = "当月超限额标准量")
    private BigDecimal paramOverMount;
    /**
     * 是否按照条线管控：0否，1是
     */
    @EntityParaAnno(zhName = "是否按照条线管控：0否，1是")
    private java.lang.Integer paramIsLine;
    /**
     * 当月临时额度标准量
     */
    @EntityParaAnno(zhName = "当月临时额度标准量")
    private BigDecimal paramTempMount;
    /**
     * 当月单笔专项标准量
     */
    @EntityParaAnno(zhName = "当月单笔专项标准量")
    private BigDecimal paramSingleMount;

    /** setter\getter方法 */
    /**
     * 参数id
     */
    public void setParamId(java.lang.Integer paramId) {
        this.paramId = paramId;
    }

    public java.lang.Integer getParamId() {
        return this.paramId;
    }

    /**
     * 所属周期
     */
    public void setParamPeriod(java.lang.String paramPeriod) {
        this.paramPeriod = paramPeriod == null ? null : paramPeriod.trim();
    }

    public java.lang.String getParamPeriod() {
        return this.paramPeriod;
    }

    /**
     * 计划分配模式
     */
    public void setParamMode(java.lang.Integer paramMode) {
        this.paramMode = paramMode;
    }

    public java.lang.Integer getParamMode() {
        return this.paramMode;
    }

    /**
     * 罚息模板编号
     */
    public void setParamPunishMode(java.lang.Integer paramPunishMode) {
        this.paramPunishMode = paramPunishMode;
    }

    public java.lang.Integer getParamPunishMode() {
        return this.paramPunishMode;
    }

    /**
     * 需求录入开始时间
     */
    public void setParamReqStart(java.lang.String paramReqStart) {
        this.paramReqStart = paramReqStart == null ? null : paramReqStart.trim();
    }

    public java.lang.String getParamReqStart() {
        return this.paramReqStart;
    }

    /**
     * 需求录入结束时间
     */
    public void setParamReqEnd(java.lang.String paramReqEnd) {
        this.paramReqEnd = paramReqEnd == null ? null : paramReqEnd.trim();
    }

    public java.lang.String getParamReqEnd() {
        return this.paramReqEnd;
    }

    /**
     * 计划制定开始时间
     */
    public void setParamPlanStart(java.lang.String paramPlanStart) {
        this.paramPlanStart = paramPlanStart == null ? null : paramPlanStart.trim();
    }

    public java.lang.String getParamPlanStart() {
        return this.paramPlanStart;
    }

    /**
     * 计划制定结束时间
     */
    public void setParamPlanEnd(java.lang.String paramPlanEnd) {
        this.paramPlanEnd = paramPlanEnd == null ? null : paramPlanEnd.trim();
    }

    public java.lang.String getParamPlanEnd() {
        return this.paramPlanEnd;
    }

    /**
     * 罚息开始时间
     */
    public void setParamPunishStart(java.lang.String paramPunishStart) {
        this.paramPunishStart = paramPunishStart == null ? null : paramPunishStart.trim();
    }

    public java.lang.String getParamPunishStart() {
        return this.paramPunishStart;
    }

    /**
     * 罚息结束时间
     */
    public void setParamPunsihEnd(java.lang.String paramPunsihEnd) {
        this.paramPunsihEnd = paramPunsihEnd == null ? null : paramPunsihEnd.trim();
    }

    public java.lang.String getParamPunsihEnd() {
        return this.paramPunsihEnd;
    }

    /**
     * 参数创建时间
     */
    public void setParamCreatetime(java.lang.String paramCreatetime) {
        this.paramCreatetime = paramCreatetime == null ? null : paramCreatetime.trim();
    }

    public java.lang.String getParamCreatetime() {
        return this.paramCreatetime;
    }

    /**
     * 参数更新时间
     */
    public void setParamUpdatetime(java.lang.String paramUpdatetime) {
        this.paramUpdatetime = paramUpdatetime == null ? null : paramUpdatetime.trim();
    }

    public java.lang.String getParamUpdatetime() {
        return this.paramUpdatetime;
    }

    /**
     * 参数创建人员id
     */
    public void setParamCreateuserid(java.lang.String paramCreateuserid) {
        this.paramCreateuserid = paramCreateuserid == null ? null : paramCreateuserid.trim();
    }

    public java.lang.String getParamCreateuserid() {
        return this.paramCreateuserid;
    }

    /**
     * 参数更新人员id
     */
    public void setParamUpdateuserid(java.lang.String paramUpdateuserid) {
        this.paramUpdateuserid = paramUpdateuserid == null ? null : paramUpdateuserid.trim();
    }

    public java.lang.String getParamUpdateuserid() {
        return this.paramUpdateuserid;
    }

    /**
     * 当月机构计划净增量
     */
    public void setParamMechIncrement(BigDecimal paramMechIncrement) {
        this.paramMechIncrement = paramMechIncrement ;
    }

    public BigDecimal getParamMechIncrement() {
        return this.paramMechIncrement;
    }

    /**
     * 当月条线计划净增量
     */
    public void setParamLineIncrement(BigDecimal paramLineIncrement) {
        this.paramLineIncrement = paramLineIncrement ;
    }

    public BigDecimal getParamLineIncrement() {
        return this.paramLineIncrement;
    }

    /**
     * 当月超限额标准量
     */
    public void setParamOverMount(BigDecimal paramOverMount) {
        this.paramOverMount = paramOverMount;
    }

    public BigDecimal getParamOverMount() {
        return this.paramOverMount;
    }

    /**
     * 是否按照条线管控：0否，1是
     */
    public void setParamIsLine(java.lang.Integer paramIsLine) {
        this.paramIsLine = paramIsLine;
    }

    public java.lang.Integer getParamIsLine() {
        return this.paramIsLine;
    }

    /**
     * 当月临时额度标准量
     */
    public void setParamTempMount(BigDecimal paramTempMount) {
        this.paramTempMount = paramTempMount;
    }

    public BigDecimal getParamTempMount() {
        return this.paramTempMount;
    }

    /**
     * 当月单笔专项标准量
     */
    public void setParamSingleMount(BigDecimal paramSingleMount) {
        this.paramSingleMount = paramSingleMount;
    }

    public BigDecimal getParamSingleMount() {
        return this.paramSingleMount;
    }


}