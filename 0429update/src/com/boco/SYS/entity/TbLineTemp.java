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
public class TbLineTemp extends BaseEntity implements java.io.Serializable {


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
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 申请主键
     */
    @EntityParaAnno(zhName = "申请主键")
    private Integer qaId;
    /**
     * 所属月份
     */
    @EntityParaAnno(zhName = "所属月份")
    private String qaMonth;
    /**
     * 机构id
     */
    @EntityParaAnno(zhName = "机构id")
    private String qaOrgan;
    /**
     * 贷种组合id
     */
    @EntityParaAnno(zhName = "贷种组合id")
    private String qaComb;
    /**
     * 调整额度/临时额度
     */
    @EntityParaAnno(zhName = "调整额度/临时额度")
    private BigDecimal qaAmt;
    /**
     * 临时额度失效日期
     */
    @EntityParaAnno(zhName = "临时额度失效日期")
    private String qaExpDate;
    /**
     * 事由
     */
    @EntityParaAnno(zhName = "事由")
    private String qaReason;
    /**
     * 上传文件id
     */
    @EntityParaAnno(zhName = "上传文件id")
    private String qaFileId;
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
    private String qaThreeInfo;
    /**
     * 之前第二个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    @EntityParaAnno(zhName = "之前第二个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  ")
    private String qaTwoInfo;
    /**
     * 之前第一个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    @EntityParaAnno(zhName = "之前第一个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  ")
    private String qaOneInfo;
    /**
     * 全年计划进度 (67)
     */
    @EntityParaAnno(zhName = "全年计划进度 (67)")
    private BigDecimal qaYearRate;
    /**
     * 状态
     */
    @EntityParaAnno(zhName = "状态")
    private Integer qaState;
    /**
     * 创建人
     */
    @EntityParaAnno(zhName = "创建人")
    private String qaCreateOper;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String qaCreateTime;
    /**
     * 临时额度生效日期
     */
    @EntityParaAnno(zhName = "临时额度生效日期")
    private String qaStartDate;
    /**
     * 单位
     */
    @EntityParaAnno(zhName = "单位")
    private java.lang.Integer unit;

    /**
     * lineCode
     */
    @EntityParaAnno(zhName = "lineCode")
    private java.lang.String lineCode;
    /**
     * lineName
     */
    @EntityParaAnno(zhName = "lineName")
    private java.lang.String lineName;
    /** setter\getter方法 */

    /**
     * lineCode
     */
    public void setLineCode(java.lang.String lineCode) {
        this.lineCode = lineCode == null ? null : lineCode.trim();
    }

    public java.lang.String getLineCode() {
        return this.lineCode;
    }

    /**
     * lineName
     */
    public void setLineName(java.lang.String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public java.lang.String getLineName() {
        return this.lineName;
    }

    /**
     * 申请主键
     */

    public void setQaId(Integer qaId) {
        this.qaId = qaId;
    }

    public Integer getQaId() {
        return this.qaId;
    }

    /**
     * 所属月份
     */
    public void setQaMonth(String qaMonth) {
        this.qaMonth = qaMonth == null ? null : qaMonth.trim();
    }

    public String getQaMonth() {
        return this.qaMonth;
    }

    /**
     * 机构id
     */
    public void setQaOrgan(String qaOrgan) {
        this.qaOrgan = qaOrgan == null ? null : qaOrgan.trim();
    }

    public String getQaOrgan() {
        return this.qaOrgan;
    }

    /**
     * 贷种组合id
     */
    public void setQaComb(String qaComb) {
        this.qaComb = qaComb == null ? null : qaComb.trim();
    }

    public String getQaComb() {
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
     * 临时额度失效日期
     */
    public void setQaExpDate(String qaExpDate) {
        this.qaExpDate = qaExpDate == null ? null : qaExpDate.trim();
    }

    public String getQaExpDate() {
        return this.qaExpDate;
    }

    /**
     * 事由
     */
    public void setQaReason(String qaReason) {
        this.qaReason = qaReason == null ? null : qaReason.trim();
    }

    public String getQaReason() {
        return this.qaReason;
    }

    /**
     * 上传文件id
     */
    public void setQaFileId(String qaFileId) {
        this.qaFileId = qaFileId == null ? null : qaFileId.trim();
    }

    public String getQaFileId() {
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
    public void setQaThreeInfo(String qaThreeInfo) {
        this.qaThreeInfo = qaThreeInfo == null ? null : qaThreeInfo.trim();
    }

    public String getQaThreeInfo() {
        return this.qaThreeInfo;
    }

    /**
     * 之前第二个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    public void setQaTwoInfo(String qaTwoInfo) {
        this.qaTwoInfo = qaTwoInfo == null ? null : qaTwoInfo.trim();
    }

    public String getQaTwoInfo() {
        return this.qaTwoInfo;
    }

    /**
     * 之前第一个月闲置超规模情况 闲置或超规模金额_百分比例如：-100_20%  |  +200_30%）
     */
    public void setQaOneInfo(String qaOneInfo) {
        this.qaOneInfo = qaOneInfo == null ? null : qaOneInfo.trim();
    }

    public String getQaOneInfo() {
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
    public void setQaState(Integer qaState) {
        this.qaState = qaState;
    }

    public Integer getQaState() {
        return this.qaState;
    }

    /**
     * 创建人
     */
    public void setQaCreateOper(String qaCreateOper) {
        this.qaCreateOper = qaCreateOper == null ? null : qaCreateOper.trim();
    }

    public String getQaCreateOper() {
        return this.qaCreateOper;
    }

    /**
     * 创建时间
     */
    public void setQaCreateTime(String qaCreateTime) {
        this.qaCreateTime = qaCreateTime == null ? null : qaCreateTime.trim();
    }

    public String getQaCreateTime() {
        return this.qaCreateTime;
    }

    /**
     * 临时额度生效日期
     */
    public void setQaStartDate(String qaStartDate) {
        this.qaStartDate = qaStartDate == null ? null : qaStartDate.trim();
    }

    public String getQaStartDate() {
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
}