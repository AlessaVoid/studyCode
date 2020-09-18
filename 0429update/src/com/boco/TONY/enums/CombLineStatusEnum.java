package com.boco.TONY.enums;

/**
 * 条线状态枚举
 *
 * @author tony
 * @describe CombLineStatusEnum
 * @date 2019-10-23
 */
public enum CombLineStatusEnum {
    COMB_LINE_DELETE(-1, "删除"),
    COMB_LINE_FREE(0, "可组合"),
    COMB_LINE_TAKEN(1, "占用");

    public int status;
    public String description;

    CombLineStatusEnum(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }
}
