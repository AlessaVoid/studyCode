package com.boco.TONY.enums;

/**
 * 贷种组合状态枚举
 *
 * @author tony
 * @describe CombStatusEnum
 * @date 2019-10-23
 */
public enum CombStatusEnum {
    COMB_DELETE(-1, "删除"),
    COMB_FREE(0, "可组合"),
    COMB_TAKEN(1, "占用");

    public int status;
    public String description;

    CombStatusEnum(int status, String description) {
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
