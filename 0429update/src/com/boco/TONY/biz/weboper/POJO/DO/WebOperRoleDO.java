package com.boco.TONY.biz.weboper.POJO.DO;

import java.util.Date;

/**
 * 柜员角色DO
 * @author tony
 * @describe WebOperRoleDO
 * @date 2019-09-07
 */
public class WebOperRoleDO {
    /**系统id*/
    private String id;
    /**柜员码*/
    private String operCode;
    /**角色ID*/
    private String roleId;
    /**创建时间*/
    private Date createTime;
    /**更新时间*/
    private Date updateTime;
    /**修改权限的柜员号*/
    private String modifyOper;
    private int state;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getModifyOper() {
        return modifyOper;
    }

    public void setModifyOper(String modifyOper) {
        this.modifyOper = modifyOper;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WebOperRoleDO{" +
                "id='" + id + '\'' +
                ", operCode='" + operCode + '\'' +
                ", roleId='" + roleId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", modifyOper='" + modifyOper + '\'' +
                ", state=" + state +
                '}';
    }
}
