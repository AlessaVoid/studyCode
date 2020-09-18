package com.boco.TONY.enums;

/**
 * 产品状态枚举
 *
 * @author tony
 * @describe ProductStatusEnum
 * @date 2019-10-23
 */
public enum ProductStatusEnum {
    PRODUCT_FREE(1, "可组合"),
    PRODUCT_TAKEN(2, "占用");
    public int status;
    public String description;

    ProductStatusEnum(int status, String description) {
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
