package com.boco.TONY.enums;

/**
 * ���߲�Ʒ״̬ö��
 * @author tony
 * @describe CombLineDetailStatusEnum
 * @date 2019-10-23
 */
public enum CombLineDetailStatusEnum {
    COMB_LINE_DETAIL_DELETE(-1, "ɾ��"),
    COMB_LINE_DETAIL_NORMAL(1, "����");

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
