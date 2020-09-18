package com.boco.TONY.enums;

/**
 * 角色更新状态枚举
 *
 * @author tony
 * @describe RoleUpdateStateEnum
 * @date 2019-09-07
 */
public enum RoleUpdateStateEnum {
    /**
     * 更新角色
     */
    ROLE_UP(1),
    /**
     * 删除角色
     */
    ROLE_DOWN(2);
    public int state;

    RoleUpdateStateEnum(int state) {
        this.state = state;
    }
}
