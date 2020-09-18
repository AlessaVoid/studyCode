package com.boco.TONY.biz.weboper.POJO.DO;

import java.io.Serializable;

/**
 * 角色更新DO
 * @author tony
 * @describe RoleUpdateInfo
 * @date 2019-09-07
 */
public class RoleUpdateInfo implements Serializable {
    /**角色标识*/
    private String roleId;
    /**操作标识符 1增加,2标识删除 表中存在记录直接更新状态即可*/
    private int state;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
