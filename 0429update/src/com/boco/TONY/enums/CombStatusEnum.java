package com.boco.TONY.enums;

/**
 * �������״̬ö��
 *
 * @author tony
 * @describe CombStatusEnum
 * @date 2019-10-23
 */
public enum CombStatusEnum {
    COMB_DELETE(-1, "ɾ��"),
    COMB_FREE(0, "�����"),
    COMB_TAKEN(1, "ռ��");

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
