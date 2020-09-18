package com.boco.TONY.enums;

import com.boco.TONY.common.AuditMix;

/**
 * 信贷需求审批枚举
 *
 * @author tony
 * @describe ReqStateEnums
 * @date 2019-10-05
 */
public enum ReqStateEnums {
    /**
     * 新创建,未下发
     */
    STATE_NEW(0, "新建,未下发"),
    /**
     * 已下发
     */
    STATE_ISSUED(1, "已下发"),
    /**
     * 已填写,待提交
     */
    STATE_FILL(2, "已填写,待提交"),
    /**
     * 已提交
     */
    STATE_SUBMITTED(4, "已提交"),
    /**
     * 审批中
     */
    STATE_APPROVING(8, "审批中"),

    /**
     * 审批通过，已上报
     */
    STATE_APPROVED(16, "审批通过，已上报"),
    /**
     * 已驳回
     */
    STATE_DISMISSED(32, "已驳回");

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
        return "未知状态";
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
