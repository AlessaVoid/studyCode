package com.boco.TONY.common;

/**
 * �Ŵ��ƻ�/��������
 *
 * @author tony
 * @describe PlanMix
 * @date 2019-10-16
 */
public final class AuditMix {
    //�������ر�ʶ
    public static final String AUDIT_REJECT = "0";

    //����ͨ����ʶ
    public static final String AUDIT_PASS = "1";



    //�ƻ��������̽ڵ��ʶǰ׺
    public static final String PLAN_ADJUST_AUDITOR_PREFIX = "planAdjustAuditor_";
    //�Ŵ��ƻ�������ʶ
    public static final String PLAN_KEY = "planId";
    //�Ŵ��ƻ�������ʶ
    public static final String PLAN_ADJUST_KEY = "planAdjustId";
    //���̽��ȱ�ʶ
    public static final String WHERE_KEY = "where";

    //�����˱�ʶ
    public static final String ASSIGNEE_KEY = "assignee";

    //�ƻ��ƶ��߱�ʶ
    public static final String PLAN_OPER_KEY = "planOper";

    //�ƻ��ƶ��߱�ʶ
    public static final String PLAN_ADJUST_OPER_KEY = "planAdjustOper";

    //����ʵ��ID
    public static final String PROCESS_INSTANCE_ID = "processInstanceId";

    //��������Id
    public static final String TASK_KEY = "taskId";

    //���������ʶ
    public static final String COMMENT_KEY = "comment";

    //���������ʶ 0-δͨ��,1-ͨ��
    public static final String IS_AGREE_KEY = "isAgree";

    //�ϴ�������
    public static final String LAST_ASSIGNEE_KEY = "lastAssignee";

    //�ϴ�����������
    public static final String LAST_ASSIGNEE_NAME_KEY = "lastAssigneeName";

    //�������ر�ʶ
    public static final String AUDIT_REJECT_SUFFIX = "reject";

    //����ͨ����ʶ
    public static final String AUDIT_PASS_SUFFIX = "audit";

    //ð�ű�ʶ

    public static final String COLON = ":";
    //���ر�ʶ
    public static final String EXCLUSIVE_GATEWAY_KEY = "Exclusive Gateway";

    //�����¼���ʶ
    public static final String END_EVENT_KEY = "End Event";

    //�Ŵ������������̱�ʶ
    public static final String REQ_BASE_PROCESS_KEY = "LoanReq";

    public static final String REQ_BASE_EL_KEY = "${isAgree=='1'}";

    public static final String REQ_QUOTA_EL_KEY = "${isAgree=='1'}";

    //------------------------�ֽ���---����------------

    /**
     * �µ�����ͼ-�Ŵ�����ͳ��
     */
    //����ͳ�ƣ�һ�ֻ���->һ��
    public static final String REQ_ONE_MECH_KEY = "Req_One_Mech";
    //����ͳ�ƣ���������->����
    public static final String REQ_TOTAL_LINE_KEY = "Req_Total_Line";
    //����ͳ�ƣ����ֻ���->����
    public static final String REQ_TWO_MECH_KEY = "Req_Two_Mech";
    //����ͳ�ƣ�һ������->һ��
    public static final String REQ_ONE_LINE_KEY = "Req_One_Line";
    //��һ�ڵ�������
    public static final String REQ_BASE_AUDITOR_PREFIX = "reqAuditor_1";
    /**
     * �µ�����ͼ-�����Ŵ�����ͳ��
     */
    //��������ͳ�ƣ�һ�ֻ���->һ��
    public static final String REQRESET_ONE_MECH_KEY = "ReqReset_One_Mech";
    //��������ͳ�ƣ���������->����
    public static final String REQRESET_TOTAL_LINE_KEY = "ReqReset_Total_Line";
    //��������ͳ�ƣ����ֻ���->����
    public static final String REQRESET_TWO_MECH_KEY = "ReqReset_Two_Mech";
    //��������ͳ�ƣ�һ������->һ��
    public static final String REQRESET_ONE_LINE_KEY = "ReqReset_One_Line";
    //��һ�ڵ�������
    public static final String REQRESET_BASE_AUDITOR_PREFIX = "reqAuditor_1";
    /**
     * �µ�����ͼ-���޶�ͳ��
     */
    //���޶һ�ֻ���->����
    public static final String OVER_MECH_KEY = "Over_Mech";

    //���޶��������->����
    public static final String OVER_TOTAL_LINE_KEY = "Over_Total_Line";
    //���޶һ������->һ��
    public static final String OVER_ONE_LINE_KEY = "Over_One_Line";

    //�ͱ�׼��һ�ڵ�
    public static final String REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX = "_101";
    //�߱�׼��һ�ڵ�
    public static final String REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX = "_201";

    //�߱�׼��һ�ڵ�
    public static final String REQ_QUOTA_TWO_HIGH_BASE_AUDITOR_PREFIX = "_301";
    //�ͱ�׼��һ�ڵ�
    public static final String  REQ_QUOTA_TWO_LOW_BASE_AUDITOR_PREFIX = "_401";
    /**
     * �µ�����ͼ-����ר��ͳ��
     */
    //����ר�һ�ֻ���->����
    public static final String SINGLE_ONE_MECH_KEY = "Single_One_Mech";


    /**
     * �µ�����ͼ-��Ϣ�������
     */
    //��Ϣ��������л���->����  (��ʱʹ�������)
    public static final String PUNISH_TOTAL_MECH_KEY = "Punish_Total_Mech";


    //��һ�ڵ�������
    public static final String PUNISH_BASE_AUDITOR_PREFIX = "plan_1";




    /*��������ͼ---------------��������----------------------------------------*/

    //��������ͼ
    public static final String RATE_SCORE = "rate_score";
    public static final String RATE_SCORE_AUDITOR_PREFIX = "plan_1";

    /*��������ͼ----------��������-------------------------------------------------*/


    /**
     * �µ�����ͼ-��ʱ���ͳ��
     */
    //��ʱ��ȣ���������->����
    public static final String TEMP_TOTAL_LINE_KEY = "Temp_Total_Line";
    //��ʱ��ȣ�һ�ֻ���->����
    public static final String TEMP_ONE_MECH_KEY = "Temp_One_Mech";



    /*�ƻ�����ͼ---------------��������----------------------------------------*/

    //�����ƻ�---����
    public static final String PLAN_TOTAL_MECH = "plan_total_mech";
    public static final String PLAN_TOTAL_MECH_AUDITOR_PREFIX = "plan_1";
    public static final String PLAN_BASE_EL_KEY = "${isAgree=='1'}";
    //�����ƻ�����---����
    public static final String PLAN_RESET_TOTAL_MECH = "plan_reset_total_mech";
    public static final String PLAN_RESET_TOTAL_MECH_AUDITOR_PREFIX = "planReset_1";
    //�����ƻ�---һ������
    public static final String PLAN_ONE_MECH = "plan_one_mech";
    public static final String PLAN_ONE_MECH_AUDITOR_PREFIX = "plan_1";
    //�����ƻ�����---һ������
    public static final String PLAN_RESET_ONE_MECH = "plan_reset_one_mech";
    public static final String PLAN_RESET_ONE_MECH_AUDITOR_PREFIX = "planReset_1";

    //���߼ƻ�---����
    public static final String PLAN_TOTAL_LINE = "plan_total_line";
    public static final String PLAN_TOTAL_LINE_AUDITOR_PREFIX = "plan_1";
    //���߼ƻ���������---����
    public static final String PLAN_RESET_TOTAL_LINE = "plan_reset_total_line";
    public static final String PLAN_RESET_TOTAL_LINE_AUDITOR_PREFIX = "planReset_1";
    //���߼ƻ�---һ������
    public static final String PLAN_ONE_LINE = "plan_one_line";
    public static final String PLAN_ONE_LINE_AUDITOR_PREFIX = "plan_1";
    //���߼ƻ�����---һ������
    public static final String PLAN_RESET_ONE_LINE = "plan_reset_one_line";
    public static final String PLAN_RESET_ONE_LINE_AUDITOR_PREFIX = "planReset_1";

    /*�Ŵ��ƻ�����ͼ----------��������-------------------------------------------------*/






//    //���Ի�
    public static final String REQ_QUOTA_PROCESS_KEY_NUM = "OverQuota:5:340316";





    //�Ŵ�����¼������������̱�ʶ
    public static final String REQRESET_BASE_EL_KEY = "${isAgree=='1'}";



    public static final String REQ_QUOTA_PROCESS_KEY = "OverQuota";







    //�Ŵ������ʶ
    public static final String REQ_KEY = "reqId";

    //���޶������ʶ
    public static final String QUOTA_KEY = "qaId";

    //�Ŵ�����¼���Ա��
    public static final String REQ_OPER_KEY = "reqOper";

    //�Ŵ�����ڵ��ʶ
    public static final String REQ_AUDITOR_PREFIX = "reqAuditor_";

    //���޶������Ա��ʶ
    public static final String QUOTA_OPER_KEY = "quotaOper";
    //��Ϣ��ȵ�����ֵ
    public static final String QUOTA_AMT_SINGLE_THRESHOLD = "QUOTA_AMT_THRESHOLD";
    //��Ϣ�������
    public static final String QUOTA_AMT_TOTAL_THRESHOLD = "QUOTA_AMT_TOTAL_THRESHOLD";
    //���޶��
    public static final String QUOTA_AMT_SWITCH = "QUOTA_AMT_SWITCH";
    //���޶�ر�
    public static final String QUOTA_AMT_SWITCH_OFF = "OFF";
    //���޶��
    public static final String QUOTA_AMT_SWITCH_ON = "ON";
    //���޶��ռ�����Ա
    public static final String QUOTA_AMT_SUPER_AUDITER = "quotaAmtSuperAuditor";
    //���޶��������Ա
    public static final String QUOTA_AMT_BASE_AUDITOR = "quotaAmtBaseAuditor";
    //����״̬�ύ
    public static final String COMMON_STATE_SUBMIT = "submit";
    //����״̬ͬ��
    public static final String COMMON_STATE_AGREE = "agree";
    //����״̬�ܾ�
    public static final String COMMON_STATE_REJECT = "reject";
    //����״̬�����ύ
    public static final String COMMON_STATE_RESUBMIT = "resubmit";
    //����״̬ͨ��
    public static final String COMMON_STATE_PASS = "pass";

    public static final String INIT_COMMENT = "��������:���ύ����";

    public static final String BUSINESS_KEY = "businessKey";

}


