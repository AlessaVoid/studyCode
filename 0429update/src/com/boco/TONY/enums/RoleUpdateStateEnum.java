package com.boco.TONY.enums;

/**
 * ��ɫ����״̬ö��
 *
 * @author tony
 * @describe RoleUpdateStateEnum
 * @date 2019-09-07
 */
public enum RoleUpdateStateEnum {
    /**
     * ���½�ɫ
     */
    ROLE_UP(1),
    /**
     * ɾ����ɫ
     */
    ROLE_DOWN(2);
    public int state;

    RoleUpdateStateEnum(int state) {
        this.state = state;
    }
}
