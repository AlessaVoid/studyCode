package com.boco.TONY.enums;

/**
 * 条线产品状态枚举
 * @author tony
 * @describe CombLineDetailStatusEnum
 * @date 2019-10-23
 */
public enum CombLineDetailStatusEnum {
    COMB_LINE_DETAIL_DELETE(-1, "删除"),
    COMB_LINE_DETAIL_NORMAL(1, "正常");

    public int status;
    public String description;

    CombLineDetailStatusEnum(int status, String description) {
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
