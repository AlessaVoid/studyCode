package com.boco.TONY.biz.weboper.POJO.DO;

import java.io.Serializable;

/**
 * ��ɫ����DO
 * @author tony
 * @describe RoleUpdateInfo
 * @date 2019-09-07
 */
public class RoleUpdateInfo implements Serializable {
    /**��ɫ��ʶ*/
    private String roleId;
    /**������ʶ�� 1����,2��ʶɾ�� ���д��ڼ�¼ֱ�Ӹ���״̬����*/
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
