package com.boco.TONY.enums;

import com.boco.TONY.common.AuditMix;

/**
 * �Ŵ���������ö��
 *
 * @author tony
 * @describe ReqStateEnums
 * @date 2019-10-05
 */
public enum ReqStateEnums {
    /**
     * �´���,δ�·�
     */
    STATE_NEW(0, "�½�,δ�·�"),
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

    ReqStateEnums(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static String sourceOf(int status) {
        for (ReqStateEnums source : ReqStateEnums.values()) {
            if (source.status == status) {
                return source.description;
            }
        }
        return "δ֪״̬";
    }

    public static String sourceKeyOf(int status) {
        if (status == ReqStateEnums.STATE_APPROVED.status) {
            return AuditMix.AUDIT_PASS_SUFFIX;
        }
        if (status == ReqStateEnums.STATE_DISMISSED.status) {
            return AuditMix.AUDIT_REJECT_SUFFIX;
        }
        return AuditMix.AUDIT_PASS_SUFFIX;
    }

}
