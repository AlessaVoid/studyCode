package com.boco.PUB.POJO.DTO;

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
public class TbReqresetListDTO extends BaseEntity implements java.io.Serializable {
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
    /**
     * 编号id
     */
    @EntityParaAnno(zhName = "编号id")
    private Integer reqresetId;
    /**
     * 调整需求所属月份
     */
    @EntityParaAnno(zhName = "调整需求所属月份")
    private String reqresetMonth;
    /**
     * 调整需求发布机构
     */
    @EntityParaAnno(zhName = "调整需求发布机构")
    private String reqresetOrgan;
    /**
     * 调整需求类型
     */
    @EntityParaAnno(zhName = "调整需求类型")
    private Integer reqresetType;
    /**
     * 调整需求状态
     */
    @EntityParaAnno(zhName = "调整需求状态")
    private Integer reqresetState;
    /**
     * 调整需求填报开始时间
     */
    @EntityParaAnno(zhName = "调整需求填报开始时间")
    private String reqresetDateStart;
    /**
     * 调整需求填报结束时间
     */
    @EntityParaAnno(zhName = "调整需求填报结束时间")
    private String reqresetDateEnd;
    /**
     * 调整填报附件id
     */
    @EntityParaAnno(zhName = "调整填报附件id")
    private Integer reqresetAttachmentId;
    /**
     * 调整填报说明
     */
    @EntityParaAnno(zhName = "调整填报说明")
    private String reqresetNote;
    /**
     * 下发给机构
     */
    @EntityParaAnno(zhName = "下发给机构")
    private Integer reqresetTo;
    /**
     * 创建人员id
     */
    @EntityParaAnno(zhName = "创建人员id")
    private String reqresetCreateOper;
    /**
     * 更新人员id
     */
    @EntityParaAnno(zhName = "更新人员id")
    private String reqresetUpdateOper;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String reqresetCreatetime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private String reqresetUpdatetime;

    /** setter\getter方法 */
    /**
     * 编号id
     */
    public void setReqresetId(Integer reqresetId) {
        this.reqresetId = reqresetId;
    }

    public Integer getReqresetId() {
        return this.reqresetId;
    }

    /**
     * 调整需求所属月份
     */
    public void setReqresetMonth(String reqresetMonth) {
        this.reqresetMonth = reqresetMonth == null ? null : reqresetMonth.trim();
    }

    public String getReqresetMonth() {
        return this.reqresetMonth;
    }

    /**
     * 调整需求发布机构
     */
    public void setreqresetOrgan(String reqresetOrgan) {
        this.reqresetOrgan = reqresetOrgan == null ? null : reqresetOrgan.trim();
    }

    public String getreqresetOrgan() {
        return this.reqresetOrgan;
    }

    /**
     * 调整需求类型
     */
    public void setReqresetType(Integer reqresetType) {
        this.reqresetType = reqresetType;
    }

    public Integer getReqresetType() {
        return this.reqresetType;
    }

    /**
     * 调整需求状态
     */
    public void setReqresetState(Integer reqresetState) {
        this.reqresetState = reqresetState;
    }

    public Integer getReqresetState() {
        return this.reqresetState;
    }

    /**
     * 调整需求填报开始时间
     */
    public void setReqresetDateStart(String reqresetDateStart) {
        this.reqresetDateStart = reqresetDateStart == null ? null : reqresetDateStart.trim();
    }

    public String getReqresetDateStart() {
        return this.reqresetDateStart;
    }

    /**
     * 调整需求填报结束时间
     */
    public void setReqresetDateEnd(String reqresetDateEnd) {
        this.reqresetDateEnd = reqresetDateEnd == null ? null : reqresetDateEnd.trim();
    }

    public String getReqresetDateEnd() {
        return this.reqresetDateEnd;
    }

    /**
     * 调整填报附件id
     */
    public void setReqresetAttachmentId(Integer reqresetAttachmentId) {
        this.reqresetAttachmentId = reqresetAttachmentId;
    }

    public Integer getReqresetAttachmentId() {
        return this.reqresetAttachmentId;
    }

    /**
     * 调整填报说明
     */
    public void setReqresetNote(String reqresetNote) {
        this.reqresetNote = reqresetNote == null ? null : reqresetNote.trim();
    }

    public String getReqresetNote() {
        return this.reqresetNote;
    }

    /**
     * 下发给机构
     */
    public void setReqresetTo(Integer reqresetTo) {
        this.reqresetTo = reqresetTo;
    }

    public Integer getReqresetTo() {
        return this.reqresetTo;
    }

    /**
     * 创建人员id
     */
    public void setReqresetCreateOper(String reqresetCreateOper) {
        this.reqresetCreateOper = reqresetCreateOper == null ? null : reqresetCreateOper.trim();
    }

    public String getReqresetCreateOper() {
        return this.reqresetCreateOper;
    }

    /**
     * 更新人员id
     */
    public void setReqresetUpdateOper(String reqresetUpdateOper) {
        this.reqresetUpdateOper = reqresetUpdateOper == null ? null : reqresetUpdateOper.trim();
    }

    public String getReqresetUpdateOper() {
        return this.reqresetUpdateOper;
    }

    /**
     * 创建时间
     */
    public void setReqresetCreatetime(String reqresetCreatetime) {
        this.reqresetCreatetime = reqresetCreatetime;
    }

    public String getReqresetCreatetime() {
        return this.reqresetCreatetime;
    }

    /**
     * 更新时间
     */
    public void setReqresetUpdatetime(String reqresetUpdatetime) {
        this.reqresetUpdatetime = reqresetUpdatetime;
    }

    public String getReqresetUpdatetime() {
        return this.reqresetUpdatetime;
    }
}