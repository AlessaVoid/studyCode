package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 下发信贷需求报送要求表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqList extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * 报送要求下发者
     */
    public static final Integer REQ_TO_PRODUCER = 1;
    /**
     * 报送要求接收者
     */
    public static final Integer REQ_TO_CONSUMER = 2;


    /**
     * 尚未填报完成
     */
    public static final Integer STATE_UNDONE = 1;
    /**
     * 填报完成
     */
    public static final Integer STATE_COMPLETE = 2;
    /**
     * 编号id
     */
    @EntityParaAnno(zhName = "编号id")
    private java.lang.Integer reqId;
    /**
     * 需求所属月份
     */
    @EntityParaAnno(zhName = "需求所属月份")
    private java.lang.String reqMonth;
    /**
     * 需求发布机构
     */
    @EntityParaAnno(zhName = "需求发布机构")
    private java.lang.String reqOrgan;
    /**
     * 需求类型
     * 0-机构
     * 1-条线
     */
    @EntityParaAnno(zhName = "需求类型")
    private java.lang.Integer reqType;
    /**
     * 需求状态
     */
    @EntityParaAnno(zhName = "需求状态")
    private java.lang.Integer reqState;
    /**
     * 需求填报开始时间
     */
    @EntityParaAnno(zhName = "需求填报开始时间")
    private java.lang.String reqDateStart;
    /**
     * 需求填报结束时间
     */
    @EntityParaAnno(zhName = "需求填报结束时间")
    private java.lang.String reqDateEnd;
    /**
     * 填报说明
     */
    @EntityParaAnno(zhName = "填报说明")
    private java.lang.String reqNote;
    /**
     * 下发给机构
     */
    @EntityParaAnno(zhName = "下发给机构")
    private java.lang.Integer reqTo;
    /**
     * 创建人员id
     */
    @EntityParaAnno(zhName = "创建人员id")
    private java.lang.String reqCreateOper;
    /**
     * 更新人员id
     */
    @EntityParaAnno(zhName = "更新人员id")
    private java.lang.String reqUpdateOper;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private java.lang.String reqCreatetime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private java.lang.String reqUpdatetime;
    /**
     * 下发机构组合
     */
    @EntityParaAnno(zhName = "下发机构组合")
    private java.lang.String reqOrganList;
    /**
     * 下发贷种组合组合
     */
    @EntityParaAnno(zhName = "下发贷种组合组合")
    private java.lang.String reqCombList;
    /**
     * 下发条线组合
     */
    @EntityParaAnno(zhName = "下发条线组合")
    private java.lang.String reqProdLine;
    /**
     * 周期开始日期
     */
    @EntityParaAnno(zhName = "周期开始日期")
    private java.lang.String reqTimeStart;
    /**
     * 周期结束日期
     */
    @EntityParaAnno(zhName = "周期结束日期")
    private java.lang.String reqTimeEnd;
    /**
     * 需求名称
     */
    @EntityParaAnno(zhName = "需求名称")
    private java.lang.String reqName;

    /**
     * 需求单位
     */
    @EntityParaAnno(zhName = "需求单位")
    private java.lang.Integer reqUnit;

    /**
     * 到期量开始日期
     */
    @EntityParaAnno(zhName = "到期量开始日期")
    private java.lang.String expTimeStart;
    /**
     * 到期量结束日期
     */
    @EntityParaAnno(zhName = "到期量结束日期")
    private java.lang.String expTimeEnd;
    /**
     * rateTimeStart
     */
    @EntityParaAnno(zhName = "rateTimeStart")
    private java.lang.String rateTimeStart;
    /**
     * rateTimeEnd
     */
    @EntityParaAnno(zhName = "rateTimeEnd")
    private java.lang.String rateTimeEnd;
    /**
     * balanceTimeStart
     */
    @EntityParaAnno(zhName = "balanceTimeStart")
    private java.lang.String balanceTimeStart;
    /**
     * balanceTimeEnd
     */
    @EntityParaAnno(zhName = "balanceTimeEnd")
    private java.lang.String balanceTimeEnd;

    /**
     * numType
     */
    @EntityParaAnno(zhName = "numType")
    private java.lang.String numType;

    public String getNumType() {
        return numType;
    }

    public void setNumType(String numType) {
        this.numType = numType;
    }
/** setter\getter方法 */
    /**
     * 编号id
     */
    public void setReqId(java.lang.Integer reqId) {
        this.reqId = reqId;
    }

    public java.lang.Integer getReqId() {
        return this.reqId;
    }

    /**
     * 需求所属月份
     */
    public void setReqMonth(java.lang.String reqMonth) {
        this.reqMonth = reqMonth == null ? null : reqMonth.trim();
    }

    public java.lang.String getReqMonth() {
        return this.reqMonth;
    }

    public String getRateTimeStart() {
        return rateTimeStart;
    }

    public void setRateTimeStart(String rateTimeStart) {
        this.rateTimeStart = rateTimeStart;
    }

    public String getRateTimeEnd() {
        return rateTimeEnd;
    }

    public void setRateTimeEnd(String rateTimeEnd) {
        this.rateTimeEnd = rateTimeEnd;
    }

    public String getBalanceTimeStart() {
        return balanceTimeStart;
    }

    public void setBalanceTimeStart(String balanceTimeStart) {
        this.balanceTimeStart = balanceTimeStart;
    }

    public String getBalanceTimeEnd() {
        return balanceTimeEnd;
    }

    public void setBalanceTimeEnd(String balanceTimeEnd) {
        this.balanceTimeEnd = balanceTimeEnd;
    }

    /**
     * 需求发布机构
     */
    public void setReqOrgan(java.lang.String reqOrgan) {
        this.reqOrgan = reqOrgan == null ? null : reqOrgan.trim();
    }

    public java.lang.String getReqOrgan() {
        return this.reqOrgan;
    }

    public String getExpTimeStart() {
        return expTimeStart;
    }

    public void setExpTimeStart(String expTimeStart) {
        this.expTimeStart = expTimeStart;
    }

    public String getExpTimeEnd() {
        return expTimeEnd;
    }

    public void setExpTimeEnd(String expTimeEnd) {
        this.expTimeEnd = expTimeEnd;
    }

    /**
     * 需求类型
     */
    public void setReqType(java.lang.Integer reqType) {
        this.reqType = reqType;
    }

    public java.lang.Integer getReqType() {
        return this.reqType;
    }

    /**
     * 需求状态
     */
    public void setReqState(java.lang.Integer reqState) {
        this.reqState = reqState;
    }

    public java.lang.Integer getReqState() {
        return this.reqState;
    }

    /**
     * 需求填报开始时间
     */
    public void setReqDateStart(java.lang.String reqDateStart) {
        this.reqDateStart = reqDateStart == null ? null : reqDateStart.trim();
    }

    public java.lang.String getReqDateStart() {
        return this.reqDateStart;
    }

    /**
     * 需求填报结束时间
     */
    public void setReqDateEnd(java.lang.String reqDateEnd) {
        this.reqDateEnd = reqDateEnd == null ? null : reqDateEnd.trim();
    }

    public java.lang.String getReqDateEnd() {
        return this.reqDateEnd;
    }

    /**
     * 填报说明
     */
    public void setReqNote(java.lang.String reqNote) {
        this.reqNote = reqNote == null ? null : reqNote.trim();
    }

    public java.lang.String getReqNote() {
        return this.reqNote;
    }

    /**
     * 下发给机构
     */
    public void setReqTo(java.lang.Integer reqTo) {
        this.reqTo = reqTo;
    }

    public java.lang.Integer getReqTo() {
        return this.reqTo;
    }

    /**
     * 创建人员id
     */
    public void setReqCreateOper(java.lang.String reqCreateOper) {
        this.reqCreateOper = reqCreateOper == null ? null : reqCreateOper.trim();
    }

    public java.lang.String getReqCreateOper() {
        return this.reqCreateOper;
    }

    /**
     * 更新人员id
     */
    public void setReqUpdateOper(java.lang.String reqUpdateOper) {
        this.reqUpdateOper = reqUpdateOper == null ? null : reqUpdateOper.trim();
    }

    public java.lang.String getReqUpdateOper() {
        return this.reqUpdateOper;
    }

    /**
     * 创建时间
     */
    public void setReqCreatetime(java.lang.String reqCreatetime) {
        this.reqCreatetime = reqCreatetime == null ? null : reqCreatetime.trim();
    }

    public java.lang.String getReqCreatetime() {
        return this.reqCreatetime;
    }

    /**
     * 更新时间
     */
    public void setReqUpdatetime(java.lang.String reqUpdatetime) {
        this.reqUpdatetime = reqUpdatetime == null ? null : reqUpdatetime.trim();
    }

    public java.lang.String getReqUpdatetime() {
        return this.reqUpdatetime;
    }

    /**
     * 下发机构组合
     */
    public void setReqOrganList(java.lang.String reqOrganList) {
        this.reqOrganList = reqOrganList == null ? null : reqOrganList.trim();
    }

    public java.lang.String getReqOrganList() {
        return this.reqOrganList;
    }

    /**
     * 下发贷种组合组合
     */
    public void setReqCombList(java.lang.String reqCombList) {
        this.reqCombList = reqCombList == null ? null : reqCombList.trim();
    }

    public java.lang.String getReqCombList() {
        return this.reqCombList;
    }

    /**
     * 下发条线组合
     */
    public void setReqProdLine(java.lang.String reqProdLine) {
        this.reqProdLine = reqProdLine == null ? null : reqProdLine.trim();
    }

    public java.lang.String getReqProdLine() {
        return this.reqProdLine;
    }

    /**
     * 周期开始日期
     */
    public void setReqTimeStart(java.lang.String reqTimeStart) {
        this.reqTimeStart = reqTimeStart == null ? null : reqTimeStart.trim();
    }

    public java.lang.String getReqTimeStart() {
        return this.reqTimeStart;
    }

    /**
     * 周期结束日期
     */
    public void setReqTimeEnd(java.lang.String reqTimeEnd) {
        this.reqTimeEnd = reqTimeEnd == null ? null : reqTimeEnd.trim();
    }

    public java.lang.String getReqTimeEnd() {
        return this.reqTimeEnd;
    }

    /**
     * 需求名称
     */
    public void setReqName(java.lang.String reqName) {
        this.reqName = reqName == null ? null : reqName.trim();
    }

    public java.lang.String getReqName() {
        return this.reqName;
    }

    /**
     * 需求单位
     */
    public void setReqUnit(java.lang.Integer reqUnit) {
        this.reqUnit = reqUnit;
    }

    public java.lang.Integer getReqUnit() {
        return this.reqUnit;
    }

}