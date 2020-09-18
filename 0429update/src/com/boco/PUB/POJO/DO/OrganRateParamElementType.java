package com.boco.PUB.POJO.DO;

/**
 * @Author: liujinbo
 * @Description: 机构评分得分项类型常量
 * @Date: 2020/1/15
 * @Version: 1.0
 */
public class OrganRateParamElementType {

    // bad_condition                        不良情况
    // deposit_loan_ratio                   自营新增存贷比
    // new_deposit_ratio                    新发生贷款利率
    // deposit_struct                       贷款结构
    // scale_amount                         超规模占用费和规模闲置费
    // plan_submit_deviation                计划报送偏离度
    // plan_execute_deviation_month_mid     计划执行偏离度-月末月中偏离度
    // plan_execute_deviation_month_init    计划执行偏离度-月末月初偏离度
    // adj_count                            调整频次
    // scale_and_adj_count                  未产生超规模占用费和规模闲置费和调整频次
    // quarter_grade                        季度评级
    // quarter_weight                       季度评分权重


    //不良情况
    public static String BAD_CONDITION = "bad_condition";
    //自营新增存贷比
    public static String DEPOSIT_LOAN_RATIO = "deposit_loan_ratio";
    //新发生贷款利率
    public static String NEW_LOAN_RATIO = "new_loan_ratio";
    //贷款结构
    public static String LOAN_STRUCT = "loan_struct";
    //超规模占用费和规模闲置费
    public static String SCALE_AMOUNT = "scale_amount";
    //计划报送偏离度
    public static String PLAN_SUBMIT_DEVIATION = "plan_submit_deviation";
    //计划执行偏离度-月末月中偏离度
    public static String PLAN_EXECUTE_DEVIATION_MONTH_MID = "plan_execute_deviation_month_mid";
    //计划执行偏离度-月末月初偏离度
    public static String PLAN_EXECUTE_DEVIATION_MONTH_INIT = "plan_execute_deviation_month_init";
    //调整频次`
    public static String ADJ_COUNT = "adj_count";
    //未产生超规模占用费和规模闲置费和调整频次
    public static String SCALE_AMOUNT_AND_ADJ_COUNT = "scale_amount_and_adj_count";
    //季度评级
    public static String QUARTER_GRADE = "quarter_grade";
    //季度评分权重
    public static String QUARTER_WEIGHT = "quarter_weight";





    //评分类型  1-月度评分
    public static final int RATE_MONTH = 1;
    //评分类型  2-季度评分
    public static final int RATE_QUARTER = 2;
}
