package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 临时额度结果表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbTempResultInfo extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;



    /**
     * 新增·
     */
    public static final Integer TEMP_NEW = 1;
    /**
     * 已生效
     */
    public static final Integer TEMP_ING = 2;

    /**
     * 已失效
     */
    public static final Integer TEMP_OLD = 4;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * 所属月份：yyyymm：202004
     */
    @EntityParaAnno(zhName = "所属月份：yyyymm：202004")
    private String month;
    /**
     * 机构编码
     */
    @EntityParaAnno(zhName = "机构编码")
    private String organcode;
    /**
     * 贷种组合id
     */
    @EntityParaAnno(zhName = "贷种组合id")
    private String combId;
    /**
     * 临时额度（万元）
     */
    @EntityParaAnno(zhName = "临时额度（万元）")
    private BigDecimal tempamt;
    /**
     * 生效时间
     */
    @EntityParaAnno(zhName = "生效时间")
    private String startDate;
    /**
     * 失效时间
     */
    @EntityParaAnno(zhName = "失效时间")
    private String endDate;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String createTime;
    /**
     * 创建人员
     */
    @EntityParaAnno(zhName = "创建人员")
    private String createOper;

    /**
     * state
     */
    @EntityParaAnno(zhName = "state")
    private java.lang.Integer state;

    /**
     * state
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }

    public java.lang.Integer getState() {
        return this.state;
    }

    /** setter\getter方法 */
    /**
     * id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getId() {
        return this.id;
    }

    /**
     * 所属月份：yyyymm：202004
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonth() {
        return this.month;
    }

    /**
     * 机构编码
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    /**
     * 贷种组合id
     */
    public void setCombId(String combId) {
        this.combId = combId == null ? null : combId.trim();
    }

    public String getCombId() {
        return this.combId;
    }

    /**
     * 临时额度（万元）
     */
    public void setTempamt(BigDecimal tempamt) {
        this.tempamt = tempamt;
    }

    public BigDecimal getTempamt() {
        return this.tempamt;
    }

    /**
     * 生效时间
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getStartDate() {
        return this.startDate;
    }

    /**
     * 失效时间
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getEndDate() {
        return this.endDate;
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
     * 创建人员
     */
    public void setCreateOper(String createOper) {
        this.createOper = createOper == null ? null : createOper.trim();
    }

    public String getCreateOper() {
        return this.createOper;
    }
}