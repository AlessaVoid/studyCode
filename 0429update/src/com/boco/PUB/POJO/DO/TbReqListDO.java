package com.boco.PUB.POJO.DO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 下发信贷需求报送要求表  页面数据传输对象
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqListDO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = -5072824609477947413L;


    /** 属性 */
    /**
     * 编号id
     */
    @EntityParaAnno(zhName = "编号id")
    private Integer reqId;
    /**
     * 需求所属月份
     */
    @EntityParaAnno(zhName = "需求所属月份")
    private String reqMonth;
    /**
     * 需求发布机构
     */
    @EntityParaAnno(zhName = "需求发布机构")
    private Integer reqOragn;
    /**
     * 需求类型
     */
    @EntityParaAnno(zhName = "需求类型")
    private Integer reqType;
    /**
     * 需求状态
     */
    @EntityParaAnno(zhName = "需求状态")
    private Integer reqState;
    /**
     * 需求填报开始时间
     */
    @EntityParaAnno(zhName = "需求填报开始时间")
    private String reqDateStart;
    /**
     * 需求填报结束时间
     */
    @EntityParaAnno(zhName = "需求填报结束时间")
    private String reqDateEnd;
    /**
     * 填报附件id
     */
    @EntityParaAnno(zhName = "填报附件id")
    private Integer reqAttachmentId;
    /**
     * 填报说明
     */
    @EntityParaAnno(zhName = "填报说明")
    private String reqNote;
    /**
     * 下发给机构
     */
    @EntityParaAnno(zhName = "下发给机构")
    private Integer reqTo;
    /**
     * 创建人员id
     */
    @EntityParaAnno(zhName = "创建人员id")
    private String reqCreateOper;
    /**
     * 更新人员id
     */
    @EntityParaAnno(zhName = "更新人员id")
    private String reqUpdateOper;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String reqCreatetime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private String reqUpdatetime;

    /** setter\getter方法 */
    /**
     * 编号id
     */
    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getReqId() {
        return this.reqId;
    }

    /**
     * 需求所属月份
     */
    public void setReqMonth(String reqMonth) {
        this.reqMonth = reqMonth == null ? null : reqMonth.trim();
    }

    public String getReqMonth() {
        return this.reqMonth;
    }

    /**
     * 需求发布机构
     */
    public void setReqOragn(Integer reqOragn) {
        this.reqOragn = reqOragn;
    }

    public Integer getReqOragn() {
        return this.reqOragn;
    }

    /**
     * 需求类型
     */
    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public Integer getReqType() {
        return this.reqType;
    }

    /**
     * 需求状态
     */
    public void setReqState(Integer reqState) {
        this.reqState = reqState;
    }

    public Integer getReqState() {
        return this.reqState;
    }

    /**
     * 需求填报开始时间
     */
    public void setReqDateStart(String reqDateStart) {
        this.reqDateStart = reqDateStart == null ? null : reqDateStart.trim();
    }

    public String getReqDateStart() {
        return this.reqDateStart;
    }

    /**
     * 需求填报结束时间
     */
    public void setReqDateEnd(String reqDateEnd) {
        this.reqDateEnd = reqDateEnd == null ? null : reqDateEnd.trim();
    }

    public String getReqDateEnd() {
        return this.reqDateEnd;
    }

    /**
     * 填报附件id
     */
    public void setReqAttachmentId(Integer reqAttachmentId) {
        this.reqAttachmentId = reqAttachmentId;
    }

    public Integer getReqAttachmentId() {
        return this.reqAttachmentId;
    }

    /**
     * 填报说明
     */
    public void setReqNote(String reqNote) {
        this.reqNote = reqNote == null ? null : reqNote.trim();
    }

    public String getReqNote() {
        return this.reqNote;
    }

    /**
     * 下发给机构
     */
    public void setReqTo(Integer reqTo) {
        this.reqTo = reqTo;
    }

    public Integer getReqTo() {
        return this.reqTo;
    }

    /**
     * 创建人员id
     */
    public void setReqCreateOper(String reqCreateOper) {
        this.reqCreateOper = reqCreateOper == null ? null : reqCreateOper.trim();
    }

    public String getReqCreateOper() {
        return this.reqCreateOper;
    }

    /**
     * 更新人员id
     */
    public void setReqUpdateOper(String reqUpdateOper) {
        this.reqUpdateOper = reqUpdateOper == null ? null : reqUpdateOper.trim();
    }

    public String getReqUpdateOper() {
        return this.reqUpdateOper;
    }

    /**
     * 创建时间
     */
    public void setReqCreatetime(String reqCreatetime) {
        this.reqCreatetime = reqCreatetime == null ? null : reqCreatetime.trim();
    }

    public String getReqCreatetime() {
        return this.reqCreatetime;
    }

    /**
     * 更新时间
     */
    public void setReqUpdatetime(String reqUpdatetime) {
        this.reqUpdatetime = reqUpdatetime == null ? null : reqUpdatetime.trim();
    }

    public String getReqUpdatetime() {
        return this.reqUpdatetime;
    }
}