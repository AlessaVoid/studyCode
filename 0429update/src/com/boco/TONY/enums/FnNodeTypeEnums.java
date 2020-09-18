package com.boco.TONY.enums;

/**
 * 审批流程节点类型枚举
 *
 * @author tony
 * @describe FnNodeTypeEnums
 * @date 2019-11-11
 */
public enum FnNodeTypeEnums {
    /**
     * 新创建,未下发
     */
    LOAN_REQ("0", "信贷需求"),
    /**
     * 已下发
     */
    LOAN_PLAN("1", "信贷计划"),
    /**
     * 信贷计划调整
     */
    LOAN_PLAN_ADJUST("2", "信贷调整");
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
        return "未知";
    }
}
