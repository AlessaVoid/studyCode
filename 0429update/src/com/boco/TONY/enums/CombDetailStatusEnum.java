package com.boco.TONY.enums;

/**
 * �����������״̬
 * @author tony
 * @describe CombDetailStatusEnum
 * @date 2019-10-23
 */
public enum CombDetailStatusEnum {
    COMB_DETAIL_DELETE(-1, "ɾ��"),
    COMB_DETAIL_NORMAL(1, "����");

    public int status;
    public String description;

    CombDetailStatusEnum(int status, String description) {
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
