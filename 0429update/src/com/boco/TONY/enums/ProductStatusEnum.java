package com.boco.TONY.enums;

/**
 * ��Ʒ״̬ö��
 *
 * @author tony
 * @describe ProductStatusEnum
 * @date 2019-10-23
 */
public enum ProductStatusEnum {
    PRODUCT_FREE(1, "�����"),
    PRODUCT_TAKEN(2, "ռ��");
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
