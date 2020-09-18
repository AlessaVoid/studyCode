package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 报表-机构重点行信息表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbKeyReportOrgan extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 机构号
     */
    @EntityParaAnno(zhName = "机构号")
    private String organcode;
    /**
     * 机构名称
     */
    @EntityParaAnno(zhName = "机构名称")
    private String organname;
    /**
     * 机构级别
     */
    @EntityParaAnno(zhName = "机构级别")
    private String organlevel;
    /**
     * 上级机构号
     */
    @EntityParaAnno(zhName = "上级机构号")
    private String uporgan;
    /**
     * 上级机构名称
     */
    @EntityParaAnno(zhName = "上级机构名称")
    private String uporganname;
    /**
     * 排序字段
     */
    @EntityParaAnno(zhName = "排序字段")
    private Integer organorder;

    /** setter\getter方法 */
    /**
     * 机构号
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    /**
     * 机构名称
     */
    public void setOrganname(String organname) {
        this.organname = organname == null ? null : organname.trim();
    }

    public String getOrganname() {
        return this.organname;
    }

    /**
     * 机构级别
     */
    public void setOrganlevel(String organlevel) {
        this.organlevel = organlevel == null ? null : organlevel.trim();
    }

    public String getOrganlevel() {
        return this.organlevel;
    }

    /**
     * 上级机构号
     */
    public void setUporgan(String uporgan) {
        this.uporgan = uporgan == null ? null : uporgan.trim();
    }

    public String getUporgan() {
        return this.uporgan;
    }

    /**
     * 上级机构名称
     */
    public void setUporganname(String uporganname) {
        this.uporganname = uporganname == null ? null : uporganname.trim();
    }

    public String getUporganname() {
        return this.uporganname;
    }

    /**
     * 排序字段
     */
    public void setOrganorder(Integer organorder) {
        this.organorder = organorder;
    }

    public Integer getOrganorder() {
        return this.organorder;
    }
}