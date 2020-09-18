package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 超限额申请信息表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbQuotaApply extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;


    /**
     * 草稿
     */
    public static final int STATE_DRAFT = 0;
    /**
     * 新建
     */
    public static final int STATE_NEW = 1;
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

    /**
     * 申请主键
     */
    @EntityParaAnno(zhName = "申请主键")
    private java.lang.Integer qaId;
    /**
     * 所属月份
     */
    @EntityParaAnno(zhName = "所属月份")
    private java.lang.String qaMonth;
    /**
     * 机构id
     */
    @EntityParaAnno(zhName = "机构id")
    private java.lang.String qaOrgan;
    /**
     * 贷种组合id
     */
    @EntityParaAnno(zhName = "贷种组合id")
    private java.lang.String qaComb;
    /**
     * 调整额度/临时额度
     */
    @EntityParaAnno(zhName = "调整额度/临时额度")
    private BigDecimal qaAmt;
    /**
     * 类型 0超限额；1临时额度
     */
    @EntityParaAnno(zhName = "类型 0条线；1机构")
    private java.lang.Integer qaType;
    /**
     * 临时额度有效时间
     */
    @EntityParaAnno(zhName = "临时额度有效时间")
    private java.lang.String qaExpDate;
    /**
     * 事由
     */
    @EntityParaAnno(zhName = "事由")
    private java.lang.String qaReason;
    /**
     * 上传文件id
     */
    @EntityParaAnno(zhName = "上传文件id")
    private java.lang.String qaFileId;
    /**
     * 本月计划
     */
    @EntityParaAnno(zhName = "本月计划")
    private BigDecimal qaPlanAmt;
    /**
     * 本月超计划额度
     */
    @EntityParaAnno(zhName = "本月超计划额度")
    private BigDecimal qaOverAmt;
    /**
     * 之前第三个月闲置超规模情况（闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    @EntityParaAnno(zhName = "之前第三个月闲置超规模情况（闲置或超规模金额_百分比例如：-100_20%  ")
    private java.lang.String qaThreeInfo;
    /**
     * 之前第二个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    @EntityParaAnno(zhName = "之前第二个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  ")
    private java.lang.String qaTwoInfo;
    /**
     * 之前第一个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    @EntityParaAnno(zhName = "之前第一个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  ")
    private java.lang.String qaOneInfo;
    /**
     * 全年计划进度 (67)
     */
    @EntityParaAnno(zhName = "全年计划进度 (67)")
    private BigDecimal qaYearRate;
    /**
     * 状态
     */
    @EntityParaAnno(zhName = "状态")
    private java.lang.Integer qaState;
    /**
     * 创建人
     */
    @EntityParaAnno(zhName = "创建人")
    private java.lang.String qaCreateOper;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private java.lang.String qaCreateTime;
    /**
     * qaStartDate
     */
    @EntityParaAnno(zhName = "qaStartDate")
    private java.lang.String qaStartDate;
    /**
     * 单位
     */
    @EntityParaAnno(zhName = "单位")
    private java.lang.Integer unit;
    /**
     * 临时额度使用机构
     */
    @EntityParaAnno(zhName = "临时额度使用机构")
    private java.lang.String organCode;
    /**
     * 临时额度使用机构名称
     */
    @EntityParaAnno(zhName = "临时额度使用机构名称")
    private java.lang.String organName;

    /** setter\getter方法 */
    /**
     * 申请主键
     */
    public void setQaId(java.lang.Integer qaId) {
        this.qaId = qaId;
    }

    public java.lang.Integer getQaId() {
        return this.qaId;
    }

    /**
     * 所属月份
     */
    public void setQaMonth(java.lang.String qaMonth) {
        this.qaMonth = qaMonth == null ? null : qaMonth.trim();
    }

    public java.lang.String getQaMonth() {
        return this.qaMonth;
    }

    /**
     * 机构id
     */
    public void setQaOrgan(java.lang.String qaOrgan) {
        this.qaOrgan = qaOrgan == null ? null : qaOrgan.trim();
    }

    public java.lang.String getQaOrgan() {
        return this.qaOrgan;
    }

    /**
     * 贷种组合id
     */
    public void setQaComb(java.lang.String qaComb) {
        this.qaComb = qaComb == null ? null : qaComb.trim();
    }

    public java.lang.String getQaComb() {
        return this.qaComb;
    }

    /**
     * 调整额度/临时额度
     */
    public void setQaAmt(BigDecimal qaAmt) {
        this.qaAmt = qaAmt;
    }

    public BigDecimal getQaAmt() {
        return this.qaAmt;
    }

    /**
     * 类型 0超限额；1临时额度
     */
    public void setQaType(java.lang.Integer qaType) {
        this.qaType = qaType;
    }

    public java.lang.Integer getQaType() {
        return this.qaType;
    }

    /**
     * 临时额度有效时间
     */
    public void setQaExpDate(java.lang.String qaExpDate) {
        this.qaExpDate = qaExpDate == null ? null : qaExpDate.trim();
    }

    public java.lang.String getQaExpDate() {
        return this.qaExpDate;
    }

    /**
     * 事由
     */
    public void setQaReason(java.lang.String qaReason) {
        this.qaReason = qaReason == null ? null : qaReason.trim();
    }

    public java.lang.String getQaReason() {
        return this.qaReason;
    }

    /**
     * 上传文件id
     */
    public void setQaFileId(java.lang.String qaFileId) {
        this.qaFileId = qaFileId == null ? null : qaFileId.trim();
    }

    public java.lang.String getQaFileId() {
        return this.qaFileId;
    }

    /**
     * 本月计划
     */
    public void setQaPlanAmt(BigDecimal qaPlanAmt) {
        this.qaPlanAmt = qaPlanAmt;
    }

    public BigDecimal getQaPlanAmt() {
        return this.qaPlanAmt;
    }

    /**
     * 本月超计划额度
     */
    public void setQaOverAmt(BigDecimal qaOverAmt) {
        this.qaOverAmt = qaOverAmt;
    }

    public BigDecimal getQaOverAmt() {
        return this.qaOverAmt;
    }

    /**
     * 之前第三个月闲置超规模情况（闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    public void setQaThreeInfo(java.lang.String qaThreeInfo) {
        this.qaThreeInfo = qaThreeInfo == null ? null : qaThreeInfo.trim();
    }

    public java.lang.String getQaThreeInfo() {
        return this.qaThreeInfo;
    }

    /**
     * 之前第二个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    public void setQaTwoInfo(java.lang.String qaTwoInfo) {
        this.qaTwoInfo = qaTwoInfo == null ? null : qaTwoInfo.trim();
    }

    public java.lang.String getQaTwoInfo() {
        return this.qaTwoInfo;
    }

    /**
     * 之前第一个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    public void setQaOneInfo(java.lang.String qaOneInfo) {
        this.qaOneInfo = qaOneInfo == null ? null : qaOneInfo.trim();
    }

    public java.lang.String getQaOneInfo() {
        return this.qaOneInfo;
    }

    /**
     * 全年计划进度 (67)
     */
    public void setQaYearRate(BigDecimal qaYearRate) {
        this.qaYearRate = qaYearRate;
    }

    public BigDecimal getQaYearRate() {
        return this.qaYearRate;
    }

    /**
     * 状态
     */
    public void setQaState(java.lang.Integer qaState) {
        this.qaState = qaState;
    }

    public java.lang.Integer getQaState() {
        return this.qaState;
    }

    /**
     * 创建人
     */
    public void setQaCreateOper(java.lang.String qaCreateOper) {
        this.qaCreateOper = qaCreateOper == null ? null : qaCreateOper.trim();
    }

    public java.lang.String getQaCreateOper() {
        return this.qaCreateOper;
    }

    /**
     * 创建时间
     */
    public void setQaCreateTime(java.lang.String qaCreateTime) {
        this.qaCreateTime = qaCreateTime == null ? null : qaCreateTime.trim();
    }

    public java.lang.String getQaCreateTime() {
        return this.qaCreateTime;
    }

    /**
     * qaStartDate
     */
    public void setQaStartDate(java.lang.String qaStartDate) {
        this.qaStartDate = qaStartDate == null ? null : qaStartDate.trim();
    }

    public java.lang.String getQaStartDate() {
        return this.qaStartDate;
    }

    /**
     * 单位
     */
    public void setUnit(java.lang.Integer unit) {
        this.unit = unit;
    }

    public java.lang.Integer getUnit() {
        return this.unit;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}