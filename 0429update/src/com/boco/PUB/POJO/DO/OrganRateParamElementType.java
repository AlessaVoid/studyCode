package com.boco.PUB.POJO.DO;

/**
 * @Author: liujinbo
 * @Description: �������ֵ÷������ͳ���
 * @Date: 2020/1/15
 * @Version: 1.0
 */
public class OrganRateParamElementType {

    // bad_condition                        �������
    // deposit_loan_ratio                   ��Ӫ���������
    // new_deposit_ratio                    �·�����������
    // deposit_struct                       ����ṹ
    // scale_amount                         ����ģռ�÷Ѻ͹�ģ���÷�
    // plan_submit_deviation                �ƻ�����ƫ���
    // plan_execute_deviation_month_mid     �ƻ�ִ��ƫ���-��ĩ����ƫ���
    // plan_execute_deviation_month_init    �ƻ�ִ��ƫ���-��ĩ�³�ƫ���
    // adj_count                            ����Ƶ��
    // scale_and_adj_count                  δ��������ģռ�÷Ѻ͹�ģ���÷Ѻ͵���Ƶ��
    // quarter_grade                        ��������
    // quarter_weight                       ��������Ȩ��


    //�������
    public static String BAD_CONDITION = "bad_condition";
    //��Ӫ���������
    public static String DEPOSIT_LOAN_RATIO = "deposit_loan_ratio";
    //�·�����������
    public static String NEW_LOAN_RATIO = "new_loan_ratio";
    //����ṹ
    public static String LOAN_STRUCT = "loan_struct";
    //����ģռ�÷Ѻ͹�ģ���÷�
    public static String SCALE_AMOUNT = "scale_amount";
    //�ƻ�����ƫ���
    public static String PLAN_SUBMIT_DEVIATION = "plan_submit_deviation";
    //�ƻ�ִ��ƫ���-��ĩ����ƫ���
    public static String PLAN_EXECUTE_DEVIATION_MONTH_MID = "plan_execute_deviation_month_mid";
    //�ƻ�ִ��ƫ���-��ĩ�³�ƫ���
    public static String PLAN_EXECUTE_DEVIATION_MONTH_INIT = "plan_execute_deviation_month_init";
    //����Ƶ��`
    public static String ADJ_COUNT = "adj_count";
    //δ��������ģռ�÷Ѻ͹�ģ���÷Ѻ͵���Ƶ��
    public static String SCALE_AMOUNT_AND_ADJ_COUNT = "scale_amount_and_adj_count";
    //��������
    public static String QUARTER_GRADE = "quarter_grade";
    //��������Ȩ��
    public static String QUARTER_WEIGHT = "quarter_weight";





    //��������  1-�¶�����
    public static final int RATE_MONTH = 1;
    //��������  2-��������
    public static final int RATE_QUARTER = 2;
}
