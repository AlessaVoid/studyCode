package com.boco.TONY.common;

/**
 * 信贷计划/需求常量类
 *
 * @author tony
 * @describe PlanMix
 * @date 2019-10-16
 */
public final class AuditMix {
    //审批驳回标识
    public static final String AUDIT_REJECT = "0";

    //审批通过标识
    public static final String AUDIT_PASS = "1";



    //计划审批流程节点标识前缀
    public static final String PLAN_ADJUST_AUDITOR_PREFIX = "planAdjustAuditor_";
    //信贷计划主键标识
    public static final String PLAN_KEY = "planId";
    //信贷计划主键标识
    public static final String PLAN_ADJUST_KEY = "planAdjustId";
    //流程进度标识
    public static final String WHERE_KEY = "where";

    //审批人标识
    public static final String ASSIGNEE_KEY = "assignee";

    //计划制定者标识
    public static final String PLAN_OPER_KEY = "planOper";

    //计划制定者标识
    public static final String PLAN_ADJUST_OPER_KEY = "planAdjustOper";

    //流程实例ID
    public static final String PROCESS_INSTANCE_ID = "processInstanceId";

    //审批任务Id
    public static final String TASK_KEY = "taskId";

    //审批评语标识
    public static final String COMMENT_KEY = "comment";

    //审批结果标识 0-未通过,1-通过
    public static final String IS_AGREE_KEY = "isAgree";

    //上次审批人
    public static final String LAST_ASSIGNEE_KEY = "lastAssignee";

    //上次审批人名称
    public static final String LAST_ASSIGNEE_NAME_KEY = "lastAssigneeName";

    //审批驳回标识
    public static final String AUDIT_REJECT_SUFFIX = "reject";

    //审批通过标识
    public static final String AUDIT_PASS_SUFFIX = "audit";

    //冒号标识

    public static final String COLON = ":";
    //网关标识
    public static final String EXCLUSIVE_GATEWAY_KEY = "Exclusive Gateway";

    //结束事件标识
    public static final String END_EVENT_KEY = "End Event";

    //信贷需求审批流程标识
    public static final String REQ_BASE_PROCESS_KEY = "LoanReq";

    public static final String REQ_BASE_EL_KEY = "${isAgree=='1'}";

    public static final String REQ_QUOTA_EL_KEY = "${isAgree=='1'}";

    //------------------------分界线---以下------------

    /**
     * 新的流程图-信贷需求统计
     */
    //需求统计：一分机构->一分
    public static final String REQ_ONE_MECH_KEY = "Req_One_Mech";
    //需求统计：总行条线->总行
    public static final String REQ_TOTAL_LINE_KEY = "Req_Total_Line";
    //需求统计：二分机构->二分
    public static final String REQ_TWO_MECH_KEY = "Req_Two_Mech";
    //需求统计：一分条线->一分
    public static final String REQ_ONE_LINE_KEY = "Req_One_Line";
    //第一节点审批人
    public static final String REQ_BASE_AUDITOR_PREFIX = "reqAuditor_1";
    /**
     * 新的流程图-调整信贷需求统计
     */
    //调整需求统计：一分机构->一分
    public static final String REQRESET_ONE_MECH_KEY = "ReqReset_One_Mech";
    //调整需求统计：总行条线->总行
    public static final String REQRESET_TOTAL_LINE_KEY = "ReqReset_Total_Line";
    //调整需求统计：二分机构->二分
    public static final String REQRESET_TWO_MECH_KEY = "ReqReset_Two_Mech";
    //调整需求统计：一分条线->一分
    public static final String REQRESET_ONE_LINE_KEY = "ReqReset_One_Line";
    //第一节点审批人
    public static final String REQRESET_BASE_AUDITOR_PREFIX = "reqAuditor_1";
    /**
     * 新的流程图-超限额统计
     */
    //超限额：一分机构->总行
    public static final String OVER_MECH_KEY = "Over_Mech";

    //超限额：总行条线->总行
    public static final String OVER_TOTAL_LINE_KEY = "Over_Total_Line";
    //超限额：一分条线->一分
    public static final String OVER_ONE_LINE_KEY = "Over_One_Line";

    //低标准第一节点
    public static final String REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX = "_101";
    //高标准第一节点
    public static final String REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX = "_201";

    //高标准第一节点
    public static final String REQ_QUOTA_TWO_HIGH_BASE_AUDITOR_PREFIX = "_301";
    //低标准第一节点
    public static final String  REQ_QUOTA_TWO_LOW_BASE_AUDITOR_PREFIX = "_401";
    /**
     * 新的流程图-单笔专项统计
     */
    //单笔专项：一分机构->总行
    public static final String SINGLE_ONE_MECH_KEY = "Single_One_Mech";


    /**
     * 新的流程图-罚息结果审批
     */
    //罚息结果：总行机构->总行  (临时使用需求的)
    public static final String PUNISH_TOTAL_MECH_KEY = "Punish_Total_Mech";


    //第一节点审批人
    public static final String PUNISH_BASE_AUDITOR_PREFIX = "plan_1";




    /*评分流程图---------------↓↓↓↓----------------------------------------*/

    //评分流程图
    public static final String RATE_SCORE = "rate_score";
    public static final String RATE_SCORE_AUDITOR_PREFIX = "plan_1";

    /*评分流程图----------↑↑↑↑-------------------------------------------------*/


    /**
     * 新的流程图-临时额度统计
     */
    //临时额度：总行条线->总行
    public static final String TEMP_TOTAL_LINE_KEY = "Temp_Total_Line";
    //临时额度：一分机构->总行
    public static final String TEMP_ONE_MECH_KEY = "Temp_One_Mech";



    /*计划流程图---------------↓↓↓↓----------------------------------------*/

    //机构计划---总行
    public static final String PLAN_TOTAL_MECH = "plan_total_mech";
    public static final String PLAN_TOTAL_MECH_AUDITOR_PREFIX = "plan_1";
    public static final String PLAN_BASE_EL_KEY = "${isAgree=='1'}";
    //机构计划调整---总行
    public static final String PLAN_RESET_TOTAL_MECH = "plan_reset_total_mech";
    public static final String PLAN_RESET_TOTAL_MECH_AUDITOR_PREFIX = "planReset_1";
    //机构计划---一级分行
    public static final String PLAN_ONE_MECH = "plan_one_mech";
    public static final String PLAN_ONE_MECH_AUDITOR_PREFIX = "plan_1";
    //机构计划调整---一级分行
    public static final String PLAN_RESET_ONE_MECH = "plan_reset_one_mech";
    public static final String PLAN_RESET_ONE_MECH_AUDITOR_PREFIX = "planReset_1";

    //条线计划---总行
    public static final String PLAN_TOTAL_LINE = "plan_total_line";
    public static final String PLAN_TOTAL_LINE_AUDITOR_PREFIX = "plan_1";
    //条线计划调整审批---总行
    public static final String PLAN_RESET_TOTAL_LINE = "plan_reset_total_line";
    public static final String PLAN_RESET_TOTAL_LINE_AUDITOR_PREFIX = "planReset_1";
    //条线计划---一级分行
    public static final String PLAN_ONE_LINE = "plan_one_line";
    public static final String PLAN_ONE_LINE_AUDITOR_PREFIX = "plan_1";
    //条线计划调整---一级分行
    public static final String PLAN_RESET_ONE_LINE = "plan_reset_one_line";
    public static final String PLAN_RESET_ONE_LINE_AUDITOR_PREFIX = "planReset_1";

    /*信贷计划流程图----------↑↑↑↑-------------------------------------------------*/






//    //测试机
    public static final String REQ_QUOTA_PROCESS_KEY_NUM = "OverQuota:5:340316";





    //信贷需求录入调整审批流程标识
    public static final String REQRESET_BASE_EL_KEY = "${isAgree=='1'}";



    public static final String REQ_QUOTA_PROCESS_KEY = "OverQuota";







    //信贷需求标识
    public static final String REQ_KEY = "reqId";

    //超限额申请标识
    public static final String QUOTA_KEY = "qaId";

    //信贷需求录入柜员号
    public static final String REQ_OPER_KEY = "reqOper";

    //信贷需求节点标识
    public static final String REQ_AUDITOR_PREFIX = "reqAuditor_";

    //超限额申请柜员标识
    public static final String QUOTA_OPER_KEY = "quotaOper";
    //罚息额度单笔阈值
    public static final String QUOTA_AMT_SINGLE_THRESHOLD = "QUOTA_AMT_THRESHOLD";
    //罚息额度总量
    public static final String QUOTA_AMT_TOTAL_THRESHOLD = "QUOTA_AMT_TOTAL_THRESHOLD";
    //超限额开关
    public static final String QUOTA_AMT_SWITCH = "QUOTA_AMT_SWITCH";
    //超限额关闭
    public static final String QUOTA_AMT_SWITCH_OFF = "OFF";
    //超限额开启
    public static final String QUOTA_AMT_SWITCH_ON = "ON";
    //超限额终极审批员
    public static final String QUOTA_AMT_SUPER_AUDITER = "quotaAmtSuperAuditor";
    //超限额基础审批员
    public static final String QUOTA_AMT_BASE_AUDITOR = "quotaAmtBaseAuditor";
    //评语状态提交
    public static final String COMMON_STATE_SUBMIT = "submit";
    //评语状态同意
    public static final String COMMON_STATE_AGREE = "agree";
    //评语状态拒绝
    public static final String COMMON_STATE_REJECT = "reject";
    //评语状态重新提交
    public static final String COMMON_STATE_RESUBMIT = "resubmit";
    //评语状态通过
    public static final String COMMON_STATE_PASS = "pass";

    public static final String INIT_COMMENT = "流程启动:已提交审批";

    public static final String BUSINESS_KEY = "businessKey";

}


