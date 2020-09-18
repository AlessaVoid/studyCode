package com.boco.TONY.enums;

/**
 * ����ͳһ״̬ö��
 *
 * @author tony
 * @describe LoanStateEnums
 * @date 2019-10-05
 */
public enum LoanStateEnums {
    /**
     * �´���,δ�·�
     */
    STATE_NEW(0, "�½�,δ����"),
    /**
     * ���·�
     */
    STATE_ISSUED(1, "���·�"),
    /**
     * ����д,���ύ
     */
    STATE_FILL(2, "����д,���ύ"),
    /**
     * ���ύ
     */
    STATE_SUBMITTED(4, "���ύ"),
    /**
     * ������
     */
    STATE_APPROVING(8, "������"),

    /**
     * ����ͨ�������ϱ�
     */
    STATE_APPROVED(16, "����ͨ�������ϱ�"),
    /**
     * �Ѳ���
     */
    STATE_DISMISSED(32, "�Ѳ���");

    public int status;
    public String description;

    LoanStateEnums(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public static String sourceOf(int status) {
        for (ReqStateEnums source : ReqStateEnums.values()) {
            if (source.status == status) {
                return source.description;
            }
        }
        return "δ֪";
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
