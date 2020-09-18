package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * TbPunishResult实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbPunishResult extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */


    /**
     * 草稿
     */
    public static final int STATE_DRAFT = 0;
    /**
     * 新建
     */
    public static final int STATE_NEW = 1;
    /**
     * 暂无异议
     */
    public static final int STATE_FILL = 2;

    /**
     * 有歧义
     */
    public static final int STATE_APPROVALING = 8;
    /**
     * 罚息id
     */
    @EntityParaAnno(zhName = "罚息id")
    private Integer punishId;
    /**
     * 机构代码
     */
    @EntityParaAnno(zhName = "机构代码")
    private String organCode;
    /**
     * 机构名称
     */
    @EntityParaAnno(zhName = "机构名称")
    private String organName;
    /**
     * 意见征集截止时间
     */
    @EntityParaAnno(zhName = "意见征集截止时间")
    private String endTime;
    /**
     * 总考核计划
     */
    @EntityParaAnno(zhName = "总考核计划")
    private BigDecimal planMount;
    /**
     * 31日闲置额度
     */
    @EntityParaAnno(zhName = "31日闲置额度")
    private BigDecimal monthVacancyAmt;
    /**
     * 31日闲置率
     */
    @EntityParaAnno(zhName = "31日闲置率")
    private BigDecimal monthVacancyRate;
    /**
     * 总闲置费（后五天）
     */
    @EntityParaAnno(zhName = "总闲置费（后五天）")
    private BigDecimal monthFiveVacancy;
    /**
     * 实体考核计划
     */
    @EntityParaAnno(zhName = "实体考核计划")
    private BigDecimal monthShitiPlanMount;
    /**
     * 实体31日超计划额度
     */
    @EntityParaAnno(zhName = "实体31日超计划额度")
    private BigDecimal monthShitiOverAmt;
    /**
     * 实体31日超计划幅度
     */
    @EntityParaAnno(zhName = "实体31日超计划幅度")
    private BigDecimal monthShitiOverRate;
    /**
     * 实体超计划费（后五天）
     */
    @EntityParaAnno(zhName = "实体超计划费（后五天）")
    private BigDecimal monthFiveShitiOver;
    /**
     * 票据考核计划
     */
    @EntityParaAnno(zhName = "票据考核计划")
    private BigDecimal monthPiapjuPlanMount;
    /**
     * 票据31日超计划额度
     */
    @EntityParaAnno(zhName = "票据31日超计划额度")
    private BigDecimal monthPiaojuOverAmt;
    /**
     * 票据31日超计划幅度
     */
    @EntityParaAnno(zhName = "票据31日超计划幅度")
    private BigDecimal monthPiaojuOverRate;
    /**
     * 票据超计划费（后五天）
     */
    @EntityParaAnno(zhName = "票据超计划费（后五天）")
    private BigDecimal monthFivePiaojuOver;
    /**
     * 罚息总计
     */
    @EntityParaAnno(zhName = "罚息总计")
    private BigDecimal monthTotalPunish;
    /**
     * 创建用户id
     */
    @EntityParaAnno(zhName = "创建用户id")
    private String createUserid;
    /**
     * 更新用户id
     */
    @EntityParaAnno(zhName = "更新用户id")
    private String updateUserid;
    /**
     * 状态
     */
    @EntityParaAnno(zhName = "状态")
    private Integer state;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String createTime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private String updateTime;
    /**
     * 罚息所属月份
     */
    @EntityParaAnno(zhName = "罚息所属月份")
    private String punishMonth;
    /**
     * 罚息列表id
     */
    @EntityParaAnno(zhName = "罚息列表id")
    private Integer punishListId;
    /**
     * 反馈意见
     */
    @EntityParaAnno(zhName = "反馈意见")
    private String note;

    /** setter\getter方法 */
    /**
     * 罚息id
     */
    public void setPunishId(Integer punishId) {
        this.punishId = punishId;
    }

    public Integer getPunishId() {
        return this.punishId;
    }

    /**
     * 机构代码
     */
    public void setOrganCode(String organCode) {
        this.organCode = organCode == null ? null : organCode.trim();
    }

    public String getOrganCode() {
        return this.organCode;
    }

    /**
     * 机构名称
     */
    public void setOrganName(String organName) {
        this.organName = organName == null ? null : organName.trim();
    }

    public String getOrganName() {
        return this.organName;
    }

    /**
     * 总考核计划
     */
    public void setPlanMount(BigDecimal planMount) {
        this.planMount = planMount;
    }

    public BigDecimal getPlanMount() {
        return this.planMount;
    }

    /**
     * 意见征集截止时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 31日闲置额度
     */
    public void setMonthVacancyAmt(BigDecimal monthVacancyAmt) {
        this.monthVacancyAmt = monthVacancyAmt ;
    }

    public BigDecimal getMonthVacancyAmt() {
        return this.monthVacancyAmt;
    }

    /**
     * 31日闲置率
     */
    public void setMonthVacancyRate(BigDecimal monthVacancyRate) {
        this.monthVacancyRate = monthVacancyRate ;
    }

    public BigDecimal getMonthVacancyRate() {
        return this.monthVacancyRate;
    }

    /**
     * 总闲置费（后五天）
     */
    public void setMonthFiveVacancy(BigDecimal monthFiveVacancy) {
        this.monthFiveVacancy = monthFiveVacancy ;
    }

    public BigDecimal getMonthFiveVacancy() {
        return this.monthFiveVacancy;
    }

    /**
     * 实体考核计划
     */
    public void setMonthShitiPlanMount(BigDecimal monthShitiPlanMount) {
        this.monthShitiPlanMount = monthShitiPlanMount ;
    }

    public BigDecimal getMonthShitiPlanMount() {
        return this.monthShitiPlanMount;
    }

    /**
     * 实体31日超计划额度
     */
    public void setMonthShitiOverAmt(BigDecimal monthShitiOverAmt) {
        this.monthShitiOverAmt = monthShitiOverAmt ;
    }

    public BigDecimal getMonthShitiOverAmt() {
        return this.monthShitiOverAmt;
    }

    /**
     * 实体31日超计划幅度
     */
    public void setMonthShitiOverRate(BigDecimal monthShitiOverRate) {
        this.monthShitiOverRate = monthShitiOverRate ;
    }

    public BigDecimal getMonthShitiOverRate() {
        return this.monthShitiOverRate;
    }

    /**
     * 实体超计划费（后五天）
     */
    public void setMonthFiveShitiOver(BigDecimal monthFiveShitiOver) {
        this.monthFiveShitiOver = monthFiveShitiOver;
    }

    public BigDecimal getMonthFiveShitiOver() {
        return this.monthFiveShitiOver;
    }

    /**
     * 票据考核计划
     */
    public void setMonthPiapjuPlanMount(BigDecimal monthPiapjuPlanMount) {
        this.monthPiapjuPlanMount = monthPiapjuPlanMount ;
    }

    public BigDecimal getMonthPiapjuPlanMount() {
        return this.monthPiapjuPlanMount;
    }

    /**
     * 票据31日超计划额度
     */
    public void setMonthPiaojuOverAmt(BigDecimal monthPiaojuOverAmt) {
        this.monthPiaojuOverAmt = monthPiaojuOverAmt ;
    }

    public BigDecimal getMonthPiaojuOverAmt() {
        return this.monthPiaojuOverAmt;
    }

    /**
     * 票据31日超计划幅度
     */
    public void setMonthPiaojuOverRate(BigDecimal monthPiaojuOverRate) {
        this.monthPiaojuOverRate = monthPiaojuOverRate ;
    }

    public BigDecimal getMonthPiaojuOverRate() {
        return this.monthPiaojuOverRate;
    }

    /**
     * 票据超计划费（后五天）
     */
    public void setMonthFivePiaojuOver(BigDecimal monthFivePiaojuOver) {
        this.monthFivePiaojuOver = monthFivePiaojuOver ;
    }

    public BigDecimal getMonthFivePiaojuOver() {
        return this.monthFivePiaojuOver;
    }

    /**
     * 罚息总计
     */
    public void setMonthTotalPunish(BigDecimal monthTotalPunish) {
        this.monthTotalPunish = monthTotalPunish ;
    }

    public BigDecimal getMonthTotalPunish() {
        return this.monthTotalPunish;
    }

    /**
     * 创建用户id
     */
    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid == null ? null : createUserid.trim();
    }

    public String getCreateUserid() {
        return this.createUserid;
    }

    /**
     * 更新用户id
     */
    public void setUpdateUserid(String updateUserid) {
        this.updateUserid = updateUserid == null ? null : updateUserid.trim();
    }

    public String getUpdateUserid() {
        return this.updateUserid;
    }

    /**
     * 状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateTime() {
        return this.createTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 罚息所属月份
     */
    public void setPunishMonth(String punishMonth) {
        this.punishMonth = punishMonth == null ? null : punishMonth.trim();
    }

    public String getPunishMonth() {
        return this.punishMonth;
    }

    /**
     * 罚息列表id
     */
    public void setPunishListId(Integer punishListId) {
        this.punishListId = punishListId;
    }

    public Integer getPunishListId() {
        return this.punishListId;
    }

    /**
     * 反馈意见
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getNote() {
        return this.note;
    }
}