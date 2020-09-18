package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * WebOperInfo实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class WebOperInfo extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /**
     * 所属机构编码
     */
    private java.lang.String organCode;
    /**
     * 机构名称
     */
    private java.lang.String organName;
    /**
     * 所属部门名称
     */
    private java.lang.String lineName;
    /**
     * 拥有的角色
     */
    private java.lang.String roleName;
    /**
     * 柜员号
     */
    private java.lang.String operCode;
    /**
     * 姓名
     */
    private java.lang.String operName;

    /**
     * 最后修改日期
     */
    private java.lang.String latestModifyDate;
    /**
     * 最后修改时间
     */
    private java.lang.String latestModifyTime;
    /**
     * 最后操作员
     */
    private java.lang.String latestOperCode;


    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public java.lang.String getOrganCode() {
        return this.organCode;
    }

    public void setOperCode(java.lang.String operCode) {
        this.operCode = operCode == null ? null : operCode.trim();
    }

    public java.lang.String getOperCode() {
        return this.operCode;
    }

    public void setOperName(java.lang.String operName) {
        this.operName = operName == null ? null : operName.trim();
    }

    public java.lang.String getOperName() {
        return this.operName;
    }

    public void setLatestModifyDate(java.lang.String latestModifyDate) {
        this.latestModifyDate = latestModifyDate == null ? null : latestModifyDate.trim();
    }

    public java.lang.String getLatestModifyDate() {
        return this.latestModifyDate;
    }

    public void setLatestModifyTime(java.lang.String latestModifyTime) {
        this.latestModifyTime = latestModifyTime == null ? null : latestModifyTime.trim();
    }

    public java.lang.String getLatestModifyTime() {
        return this.latestModifyTime;
    }

    public void setLatestOperCode(java.lang.String latestOperCode) {
        this.latestOperCode = latestOperCode == null ? null : latestOperCode.trim();
    }

    public java.lang.String getLatestOperCode() {
        return this.latestOperCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}