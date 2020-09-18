package com.boco.TONY.biz.weboper.POJO.VO;

/**
 * 柜员角色VO
 * @author tony
 * @describe WebOperRoleDTO
 * @date 2019-09-07
 */
public class WebOperRoleVO {
    /**唯一标识*/
    private String id;
    /**柜员码*/
    private String operCode;
    /**角色唯一标识*/
    private String roleId;
    /**修改权限的柜员号*/
    private String modifyOper;
    /**更新标识符 根据select状态判断*/
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
        return "WebOperRoleVO{" +
                "id='" + id + '\'' +
                ", operCode='" + operCode + '\'' +
                ", roleId='" + roleId + '\'' +
                ", modifyOper='" + modifyOper + '\'' +
                ", state=" + state +
                '}';
    }
}
