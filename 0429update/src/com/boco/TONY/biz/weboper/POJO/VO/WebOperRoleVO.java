package com.boco.TONY.biz.weboper.POJO.VO;

/**
 * ��Ա��ɫVO
 * @author tony
 * @describe WebOperRoleDTO
 * @date 2019-09-07
 */
public class WebOperRoleVO {
    /**Ψһ��ʶ*/
    private String id;
    /**��Ա��*/
    private String operCode;
    /**��ɫΨһ��ʶ*/
    private String roleId;
    /**�޸�Ȩ�޵Ĺ�Ա��*/
    private String modifyOper;
    /**���±�ʶ�� ����select״̬�ж�*/
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
