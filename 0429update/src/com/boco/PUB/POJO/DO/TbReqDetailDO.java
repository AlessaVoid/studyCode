package com.boco.PUB.POJO.DO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 信贷需求录入详细表 页面数据传输对象
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqDetailDO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5439696657692723291L;


    /** 属性 */
    /**
     * 编码id
     */
    @EntityParaAnno(zhName = "编码id")
    private Integer reqdId;
    /**
     * 所属批次id
     */
    @EntityParaAnno(zhName = "所属批次id")
    private Integer reqdRefId;
    /**
     * 填报机构id
     */
    @EntityParaAnno(zhName = "填报机构id")
    private String reqdOrgan;
    /**
     * 上报贷种
     */
    @EntityParaAnno(zhName = "上报贷种")
    private Integer reqdProdCode;
    /**
     * 上报贷种级别
     */
    @EntityParaAnno(zhName = "上报贷种级别")
    private Integer reqdProdLevel;
    /**
     * 需求金额
     */
    @EntityParaAnno(zhName = "需求金额")
    private BigDecimal reqdReqAmount;
    /**
     * 单位
     */
    @EntityParaAnno(zhName = "单位")
    private Integer reqdUnit;
    /**
     * 上报需求
     */
    @EntityParaAnno(zhName = "上报需求")
    private String reqdAmt;
    /**
     * 上报人
     */
    @EntityParaAnno(zhName = "上报人")
    private Integer reqdOper;
    /**
     * 条目状态
     */
    @EntityParaAnno(zhName = "条目状态")
    private Integer reqdState;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String reqdCreatetime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private String reqdUpdatetime;

    /** setter\getter方法 */
    /**
     * 编码id
     */
    public void setReqdId(Integer reqdId) {
        this.reqdId = reqdId;
    }

    public Integer getReqdId() {
        return this.reqdId;
    }

    /**
     * 所属批次id
     */
    public void setReqdRefId(Integer reqdRefId) {
        this.reqdRefId = reqdRefId;
    }

    public Integer getReqdRefId() {
        return this.reqdRefId;
    }

    /**
     * 填报机构id
     */
    public void setReqdOrgan(String reqdOrgan) {
        this.reqdOrgan = reqdOrgan;
    }

    public String getReqdOrgan() {
        return this.reqdOrgan;
    }

    /**
     * 上报贷种
     */
    public void setReqdProdCode(Integer reqdProdCode) {
        this.reqdProdCode = reqdProdCode;
    }

    public Integer getReqdProdCode() {
        return this.reqdProdCode;
    }

    /**
     * 上报贷种级别
     */
    public void setReqdProdLevel(Integer reqdProdLevel) {
        this.reqdProdLevel = reqdProdLevel;
    }

    public Integer getReqdProdLevel() {
        return this.reqdProdLevel;
    }

    /**
     * 需求金额
     */
    public void setReqdReqAmount(BigDecimal reqdReqAmount) {
        this.reqdReqAmount = reqdReqAmount;
    }

    public BigDecimal getReqdReqAmount() {
        return this.reqdReqAmount;
    }

    /**
     * 单位
     */
    public void setReqdUnit(Integer reqdUnit) {
        this.reqdUnit = reqdUnit;
    }

    public Integer getReqdUnit() {
        return this.reqdUnit;
    }

    /**
     * 上报需求
     */
    public void setReqdAmt(String reqdAmt) {
        this.reqdAmt = reqdAmt == null ? null : reqdAmt.trim();
    }

    public String getReqdAmt() {
        return this.reqdAmt;
    }

    /**
     * 上报人
     */
    public void setReqdOper(Integer reqdOper) {
        this.reqdOper = reqdOper;
    }

    public Integer getReqdOper() {
        return this.reqdOper;
    }

    /**
     * 条目状态
     */
    public void setReqdState(Integer reqdState) {
        this.reqdState = reqdState;
    }

    public Integer getReqdState() {
        return this.reqdState;
    }

    /**
     * 创建时间
     */
    public void setReqdCreatetime(String reqdCreatetime) {
        this.reqdCreatetime = reqdCreatetime == null ? null : reqdCreatetime.trim();
    }

    public String getReqdCreatetime() {
        return this.reqdCreatetime;
    }

    /**
     * 更新时间
     */
    public void setReqdUpdatetime(String reqdUpdatetime) {
        this.reqdUpdatetime = reqdUpdatetime == null ? null : reqdUpdatetime.trim();
    }

    public String getReqdUpdatetime() {
        return this.reqdUpdatetime;
    }
}