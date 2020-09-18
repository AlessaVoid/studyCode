package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * TbReqresetList实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqresetList extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * 报送要求下发者
     */
    public static final Integer REQ_TO_PRODUCER = 1;
    /**
     * 报送要求接收者
     */
    public static final Integer REQ_TO_CONSUMER = 2;

    /** 属性 */
    /** 编号id */
    @EntityParaAnno(zhName="编号id")
    private java.lang.Integer reqresetId;
    /** 调整需求所属月份 */
    @EntityParaAnno(zhName="调整需求所属月份")
    private java.lang.String reqresetMonth;
    /** 调整需求发布机构 */
    @EntityParaAnno(zhName="调整需求发布机构")
    private java.lang.String reqresetOrgan;
    /** 调整需求类型 */
    @EntityParaAnno(zhName="调整需求类型")
    private java.lang.Integer reqresetType;
    /** 调整需求状态 */
    @EntityParaAnno(zhName="调整需求状态")
    private java.lang.Integer reqresetState;
    /** 调整需求填报开始时间 */
    @EntityParaAnno(zhName="调整需求填报开始时间")
    private java.lang.String reqresetDateStart;
    /** 调整需求填报结束时间 */
    @EntityParaAnno(zhName="调整需求填报结束时间")
    private java.lang.String reqresetDateEnd;
    /** 调整填报附件id */
    @EntityParaAnno(zhName="调整填报附件id")
    private java.lang.Integer reqresetAttachmentId;
    /** 调整填报说明 */
    @EntityParaAnno(zhName="调整填报说明")
    private java.lang.String reqresetNote;
    /** 下发给机构 */
    @EntityParaAnno(zhName="下发给机构")
    private java.lang.Integer reqresetTo;
    /** 创建人员id */
    @EntityParaAnno(zhName="创建人员id")
    private java.lang.String reqresetCreateOper;
    /** 更新人员id */
    @EntityParaAnno(zhName="更新人员id")
    private java.lang.String reqresetUpdateOper;
    /** 创建时间 */
    @EntityParaAnno(zhName="创建时间")
    private java.lang.String reqresetCreatetime;
    /** 更新时间 */
    @EntityParaAnno(zhName="更新时间")
    private java.lang.String reqresetUpdatetime;
    /** 周期开始日期 */
    @EntityParaAnno(zhName="周期开始日期")
    private java.lang.String reqresetTimeStart;
    /** 周期结束日期 */
    @EntityParaAnno(zhName="周期结束日期")
    private java.lang.String reqresetTimeEnd;
    /** 下发机构组合 */
    @EntityParaAnno(zhName="下发机构组合")
    private java.lang.String reqresetOrganList;
    /** 下发贷种组合组合 */
    @EntityParaAnno(zhName="下发贷种组合组合")
    private java.lang.String reqresetCombList;
    /** 下发条线组合 */
    @EntityParaAnno(zhName="下发条线组合")
    private java.lang.String reqresetProdLine;
    /** 需求名称 */
    @EntityParaAnno(zhName="需求名称")
    private java.lang.String reqresetName;
    /** 需求单位 */
    @EntityParaAnno(zhName="需求单位")
    private java.lang.Integer reqresetUnit;

    /** setter\getter方法 */
    /** 编号id */
    public void setReqresetId(java.lang.Integer reqresetId) {
        this.reqresetId = reqresetId;
    }
    public java.lang.Integer getReqresetId() {
        return this.reqresetId;
    }
    /** 调整需求所属月份 */
    public void setReqresetMonth(java.lang.String reqresetMonth) {
        this.reqresetMonth = reqresetMonth == null ? null : reqresetMonth.trim();
    }
    public java.lang.String getReqresetMonth() {
        return this.reqresetMonth;
    }
    /** 调整需求发布机构 */
    public void setReqresetOrgan(java.lang.String reqresetOrgan) {
        this.reqresetOrgan = reqresetOrgan == null ? null : reqresetOrgan.trim();
    }
    public java.lang.String getReqresetOrgan() {
        return this.reqresetOrgan;
    }
    /** 调整需求类型 */
    public void setReqresetType(java.lang.Integer reqresetType) {
        this.reqresetType = reqresetType;
    }
    public java.lang.Integer getReqresetType() {
        return this.reqresetType;
    }
    /** 调整需求状态 */
    public void setReqresetState(java.lang.Integer reqresetState) {
        this.reqresetState = reqresetState;
    }
    public java.lang.Integer getReqresetState() {
        return this.reqresetState;
    }
    /** 调整需求填报开始时间 */
    public void setReqresetDateStart(java.lang.String reqresetDateStart) {
        this.reqresetDateStart = reqresetDateStart == null ? null : reqresetDateStart.trim();
    }
    public java.lang.String getReqresetDateStart() {
        return this.reqresetDateStart;
    }
    /** 调整需求填报结束时间 */
    public void setReqresetDateEnd(java.lang.String reqresetDateEnd) {
        this.reqresetDateEnd = reqresetDateEnd == null ? null : reqresetDateEnd.trim();
    }
    public java.lang.String getReqresetDateEnd() {
        return this.reqresetDateEnd;
    }
    /** 调整填报附件id */
    public void setReqresetAttachmentId(java.lang.Integer reqresetAttachmentId) {
        this.reqresetAttachmentId = reqresetAttachmentId;
    }
    public java.lang.Integer getReqresetAttachmentId() {
        return this.reqresetAttachmentId;
    }
    /** 调整填报说明 */
    public void setReqresetNote(java.lang.String reqresetNote) {
        this.reqresetNote = reqresetNote == null ? null : reqresetNote.trim();
    }
    public java.lang.String getReqresetNote() {
        return this.reqresetNote;
    }
    /** 下发给机构 */
    public void setReqresetTo(java.lang.Integer reqresetTo) {
        this.reqresetTo = reqresetTo;
    }
    public java.lang.Integer getReqresetTo() {
        return this.reqresetTo;
    }
    /** 创建人员id */
    public void setReqresetCreateOper(java.lang.String reqresetCreateOper) {
        this.reqresetCreateOper = reqresetCreateOper == null ? null : reqresetCreateOper.trim();
    }
    public java.lang.String getReqresetCreateOper() {
        return this.reqresetCreateOper;
    }
    /** 更新人员id */
    public void setReqresetUpdateOper(java.lang.String reqresetUpdateOper) {
        this.reqresetUpdateOper = reqresetUpdateOper == null ? null : reqresetUpdateOper.trim();
    }
    public java.lang.String getReqresetUpdateOper() {
        return this.reqresetUpdateOper;
    }
    /** 创建时间 */
    public void setReqresetCreatetime(java.lang.String reqresetCreatetime) {
        this.reqresetCreatetime = reqresetCreatetime == null ? null : reqresetCreatetime.trim();
    }
    public java.lang.String getReqresetCreatetime() {
        return this.reqresetCreatetime;
    }
    /** 更新时间 */
    public void setReqresetUpdatetime(java.lang.String reqresetUpdatetime) {
        this.reqresetUpdatetime = reqresetUpdatetime == null ? null : reqresetUpdatetime.trim();
    }
    public java.lang.String getReqresetUpdatetime() {
        return this.reqresetUpdatetime;
    }
    /** 周期开始日期 */
    public void setReqresetTimeStart(java.lang.String reqresetTimeStart) {
        this.reqresetTimeStart = reqresetTimeStart == null ? null : reqresetTimeStart.trim();
    }
    public java.lang.String getReqresetTimeStart() {
        return this.reqresetTimeStart;
    }
    /** 周期结束日期 */
    public void setReqresetTimeEnd(java.lang.String reqresetTimeEnd) {
        this.reqresetTimeEnd = reqresetTimeEnd == null ? null : reqresetTimeEnd.trim();
    }
    public java.lang.String getReqresetTimeEnd() {
        return this.reqresetTimeEnd;
    }
    /** 下发机构组合 */
    public void setReqresetOrganList(java.lang.String reqresetOrganList) {
        this.reqresetOrganList = reqresetOrganList == null ? null : reqresetOrganList.trim();
    }
    public java.lang.String getReqresetOrganList() {
        return this.reqresetOrganList;
    }
    /** 下发贷种组合组合 */
    public void setReqresetCombList(java.lang.String reqresetCombList) {
        this.reqresetCombList = reqresetCombList == null ? null : reqresetCombList.trim();
    }
    public java.lang.String getReqresetCombList() {
        return this.reqresetCombList;
    }
    /** 下发条线组合 */
    public void setReqresetProdLine(java.lang.String reqresetProdLine) {
        this.reqresetProdLine = reqresetProdLine == null ? null : reqresetProdLine.trim();
    }
    public java.lang.String getReqresetProdLine() {
        return this.reqresetProdLine;
    }
    /** 需求名称 */
    public void setReqresetName(java.lang.String reqresetName) {
        this.reqresetName = reqresetName == null ? null : reqresetName.trim();
    }
    public java.lang.String getReqresetName() {
        return this.reqresetName;
    }
    /** 需求单位 */
    public void setReqresetUnit(java.lang.Integer reqresetUnit) {
        this.reqresetUnit = reqresetUnit;
    }
    public java.lang.Integer getReqresetUnit() {
        return this.reqresetUnit;
    }
}