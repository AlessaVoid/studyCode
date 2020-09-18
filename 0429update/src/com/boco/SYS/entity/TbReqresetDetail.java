package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 调整申请报送要求录入详细表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqresetDetail extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /**
     * 未下发
     */
    public static final int STATE_NEW = 0;
    /**
     * 已下发
     */
    public static final int STATE_ISSUED = 1;
    /**
     * 已填写,待提交
     */
    public static final int STATE_FILL = 2;
    /**
     * 已提交，待审批
     */
    public static final int STATE_SUBMITED = 4;
    /**
     * 审批中
     */
    public static final int STATE_APPROVALING = 8;
    /**
     * 审批通过，已上报
     */
    public static final int STATE_APPROVED = 16;
    /**
     * 已驳回
     */
    public static final int STATE_DISMISSED = 32;
    /** 属性 */
    /**
     * 编码id
     */
    @EntityParaAnno(zhName = "编码id")
    private java.lang.Integer reqdresetId;
    /**
     * 所属批次id
     */
    @EntityParaAnno(zhName = "所属批次id")
    private java.lang.Integer reqdresetRefId;
    /**
     * 填报机构id
     */
    @EntityParaAnno(zhName = "填报机构id")
    private java.lang.String reqdresetOrgan;
    /**
     * 单位
     */
    @EntityParaAnno(zhName = "单位")
    private java.lang.Integer reqdresetUnit;
    /**
     * 上报人
     */
    @EntityParaAnno(zhName = "上报人")
    private java.lang.String reqdresetOper;
    /**
     * 条目状态
     */
    @EntityParaAnno(zhName = "条目状态")
    private java.lang.Integer reqdresetState;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private java.lang.String reqdresetCreatetime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private java.lang.String reqdresetUpdatetime;
    /**
     * 操作人员
     */
    @EntityParaAnno(zhName = "操作人员")
    private java.lang.String reqdresetUpdateoper;
    /**
     * 调整量
     */
    @EntityParaAnno(zhName = "调整量")
    private BigDecimal reqdresetNum;
    /**
     * 贷种组合编码
     */
    @EntityParaAnno(zhName = "贷种组合编码")
    private java.lang.String reqdresetCombCode;

    private String oldPlan;

    private String newPlan;

    private String totalOldPlan;

    private String totalNewPlan;


    private String totalNum;


    public String getTotalOldPlan() {
        return totalOldPlan;
    }

    public void setTotalOldPlan(String totalOldPlan) {
        this.totalOldPlan = totalOldPlan;
    }

    public String getTotalNewPlan() {
        return totalNewPlan;
    }

    public void setTotalNewPlan(String totalNewPlan) {
        this.totalNewPlan = totalNewPlan;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getOldPlan() {
        return oldPlan;
    }

    public void setOldPlan(String oldPlan) {
        this.oldPlan = oldPlan;
    }

    public String getNewPlan() {
        return newPlan;
    }

    public void setNewPlan(String newPlan) {
        this.newPlan = newPlan;
    }

/** setter\getter方法 */
    /**
     * 编码id
     */
    public void setReqdresetId(java.lang.Integer reqdresetId) {
        this.reqdresetId = reqdresetId;
    }

    public java.lang.Integer getReqdresetId() {
        return this.reqdresetId;
    }

    /**
     * 所属批次id
     */
    public void setReqdresetRefId(java.lang.Integer reqdresetRefId) {
        this.reqdresetRefId = reqdresetRefId;
    }

    public java.lang.Integer getReqdresetRefId() {
        return this.reqdresetRefId;
    }

    /**
     * 填报机构id
     */
    public void setReqdresetOrgan(java.lang.String reqdresetOrgan) {
        this.reqdresetOrgan = reqdresetOrgan == null ? null : reqdresetOrgan.trim();
    }

    public java.lang.String getReqdresetOrgan() {
        return this.reqdresetOrgan;
    }

    /**
     * 单位
     */
    public void setReqdresetUnit(java.lang.Integer reqdresetUnit) {
        this.reqdresetUnit = reqdresetUnit;
    }

    public java.lang.Integer getReqdresetUnit() {
        return this.reqdresetUnit;
    }

    /**
     * 上报人
     */
    public void setReqdresetOper(java.lang.String reqdresetOper) {
        this.reqdresetOper = reqdresetOper == null ? null : reqdresetOper.trim();
    }

    public java.lang.String getReqdresetOper() {
        return this.reqdresetOper;
    }

    /**
     * 条目状态
     */
    public void setReqdresetState(java.lang.Integer reqdresetState) {
        this.reqdresetState = reqdresetState;
    }

    public java.lang.Integer getReqdresetState() {
        return this.reqdresetState;
    }

    /**
     * 创建时间
     */
    public void setReqdresetCreatetime(java.lang.String reqdresetCreatetime) {
        this.reqdresetCreatetime = reqdresetCreatetime == null ? null : reqdresetCreatetime.trim();
    }

    public java.lang.String getReqdresetCreatetime() {
        return this.reqdresetCreatetime;
    }

    /**
     * 更新时间
     */
    public void setReqdresetUpdatetime(java.lang.String reqdresetUpdatetime) {
        this.reqdresetUpdatetime = reqdresetUpdatetime == null ? null : reqdresetUpdatetime.trim();
    }

    public java.lang.String getReqdresetUpdatetime() {
        return this.reqdresetUpdatetime;
    }

    /**
     * 操作人员
     */
    public void setReqdresetUpdateoper(java.lang.String reqdresetUpdateoper) {
        this.reqdresetUpdateoper = reqdresetUpdateoper == null ? null : reqdresetUpdateoper.trim();
    }

    public java.lang.String getReqdresetUpdateoper() {
        return this.reqdresetUpdateoper;
    }

    /**
     * 调整量
     */
    public void setReqdresetNum(BigDecimal reqdresetNum) {
        this.reqdresetNum = reqdresetNum;
    }

    public BigDecimal getReqdresetNum() {
        return this.reqdresetNum;
    }

    /**
     * 贷种组合编码
     */
    public void setReqdresetCombCode(java.lang.String reqdresetCombCode) {
        this.reqdresetCombCode = reqdresetCombCode == null ? null : reqdresetCombCode.trim();
    }

    public java.lang.String getReqdresetCombCode() {
        return this.reqdresetCombCode;
    }

}