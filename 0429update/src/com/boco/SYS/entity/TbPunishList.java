package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * TbPunishList实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbPunishList extends BaseEntity implements java.io.Serializable {
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
     * 已下发
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

    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private Integer id;
    /**
     * 所属月份
     */
    @EntityParaAnno(zhName = "所属月份")
    private String month;
    /**
     * 罚息名称
     */
    @EntityParaAnno(zhName = "罚息名称")
    private String name;
    /**
     * 罚息说明
     */
    @EntityParaAnno(zhName = "罚息说明")
    private String note;
    /**
     * 罚息状态
     */
    @EntityParaAnno(zhName = "罚息状态")
    private Integer state;
    /**
     * 意见征集截止时间
     */
    @EntityParaAnno(zhName = "意见征集截止时间")
    private String collectEndTime;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String creaeTime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private String updateTime;

    /** setter\getter方法 */
    /**
     * id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    /**
     * 所属月份
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonth() {
        return this.month;
    }

    /**
     * 罚息名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getName() {
        return this.name;
    }

    /**
     * 罚息说明
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getNote() {
        return this.note;
    }

    /**
     * 罚息状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    /**
     * 意见征集截止时间
     */
    public void setCollectEndTime(String collectEndTime) {
        this.collectEndTime = collectEndTime == null ? null : collectEndTime.trim();
    }

    public String getCollectEndTime() {
        return this.collectEndTime;
    }

    /**
     * 创建时间
     */
    public void setCreaeTime(String creaeTime) {
        this.creaeTime = creaeTime == null ? null : creaeTime.trim();
    }

    public String getCreaeTime() {
        return this.creaeTime;
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
}