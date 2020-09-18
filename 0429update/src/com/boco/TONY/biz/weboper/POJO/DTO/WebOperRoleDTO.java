package com.boco.TONY.biz.weboper.POJO.DTO;

import java.util.Date;

/**
 * ��Ա��ɫDTO
 * @author tony
 * @describe WebOperRoleDTO
 * @date 2019-09-07
 */
public class WebOperRoleDTO {
    /**ϵͳid*/
    private String id;
    /**��Ա��*/
    private String operCode;
    /**��ɫID*/
    private String roleId;
    /**����ʱ��*/
    private Date createTime;
    /**����ʱ��*/
    private Date updateTime;
    /**�޸�Ȩ�޵Ĺ�Ա��*/
    private String modifyOper;

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

    @Override
    public String toString() {
        return "WebOperRoleDTO{" +
                "id='" + id + '\'' +
                ", operCode='" + operCode + '\'' +
                ", roleId='" + roleId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", modifyOper='" + modifyOper + '\'' +
                '}';
    }
}
