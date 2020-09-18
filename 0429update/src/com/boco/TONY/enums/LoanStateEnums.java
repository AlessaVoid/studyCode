package com.boco.TONY.enums;

/**
 * 审批统一状态枚举
 *
 * @author tony
 * @describe LoanStateEnums
 * @date 2019-10-05
 */
public enum LoanStateEnums {
    /**
     * 新创建,未下发
     */
    STATE_NEW(0, "新建,未审批"),
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
        return "未知";
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
