package com.boco.TONY.enums;

/**
 * �������̽ڵ�����ö��
 *
 * @author tony
 * @describe FnNodeTypeEnums
 * @date 2019-11-11
 */
public enum FnNodeTypeEnums {
    /**
     * �´���,δ�·�
     */
    LOAN_REQ("0", "�Ŵ�����"),
    /**
     * ���·�
     */
    LOAN_PLAN("1", "�Ŵ��ƻ�"),
    /**
     * �Ŵ��ƻ�����
     */
    LOAN_PLAN_ADJUST("2", "�Ŵ�����");
    public String type;
    public String description;

    FnNodeTypeEnums(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static String sourceOf(String type) {
        for (FnNodeTypeEnums source : FnNodeTypeEnums.values()) {
            if (source.type.equals(type)) {
                return source.description;
            }
        }
        return "δ֪";
    }
}
