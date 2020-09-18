package com.boco.PUB.service.tbOrganRateParam;

import java.math.BigDecimal;

/**
 * @Author: liujinbo
 * @Description: 机构评分计算类
 * @Date: 2020/1/15
 * @Version: 1.0
 */
public interface OrganRateParamCalculateService {

// 1.不良情况得分
// 2.自营新增存贷比得分
// 3.新发生贷款利率得分
// 4.贷款结构得分
// 5.超规模占用费和规模闲置费得分
// 6.计划报送偏离度得分
// 7.计划执行偏离度--月末月中偏离度得分
// 8.计划执行偏离度--月末月初偏离度得分
// 9.调整频次得分
// 10.均未产生超规模占用费和规模闲置费得分
// 11.调整频次未扣分得分
// 12.季度评级得分

    /**
     * @Description 1.不良情况得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndRatio 月末不良率
     * @param yearBeginRatio 年初不良率
     * @Return java.math.BigDecimal
     */
    BigDecimal getBadCondition(BigDecimal monthEndRatio, BigDecimal yearBeginRatio);

    /**
     * @Description 2.自营新增存贷比得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param loanYearIncrement 各项贷款年增量
     * @param personDepositYearIncrement 个人自营存款年增量
     * @param companyDepositYearIncrement 公司存款年增量
     * @Return java.math.BigDecimal
     */
    BigDecimal getDepositLoanRatio(BigDecimal loanYearIncrement, BigDecimal personDepositYearIncrement, BigDecimal companyDepositYearIncrement);

    /**
     * @Description 3.新发生贷款利率得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param newLoanRatio 新发生贷款利率
     * @param BankAverageLoanRatio  全行平均贷款利率
     * @Return java.math.BigDecimal
     */
    BigDecimal getNewLoanRatio(BigDecimal newLoanRatio, BigDecimal BankAverageLoanRatio);

    /**
     * @Description 4.贷款结构得分
     * @Author liujinbo
     * @Date 2020/1/16
     * @param monthBeginEntityLoan 月初实体贷款余额
     * @param monthEndEntityLoan 月末实体贷款余额
     * @param loanCount 总贷款
     * @Return java.math.BigDecimal
     */
    BigDecimal getLoanStruct(BigDecimal monthBeginEntityLoan, BigDecimal monthEndEntityLoan, BigDecimal loanCount);


    /**
     * @Description 5.超规模占用费和规模闲置费得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param organScaleAmount 分行罚息金额
     * @param bankScaleAmount 全行罚息金额
     * @Return java.math.BigDecimal
     */
    BigDecimal getScaleAmount(BigDecimal organScaleAmount, BigDecimal bankScaleAmount);

    /**
     * @Description 6.计划报送偏离度得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndIncrement 月末实际月增量
     * @param monthBeginReport 月初报送需求
     * @Return java.math.BigDecimal
     */
    BigDecimal getPlanSubmitDeviation(BigDecimal monthEndIncrement, BigDecimal monthBeginReport);

    /** 7.计划执行偏离度--月末月中偏离度得分
     * @Description
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndIncrement 月末实际月增量
     * @param monthMidPlan   月中统一动态调整后计划
     * @param monthEndPlan   月末统一动态调整后计划
     * @Return java.math.BigDecimal
     */
    BigDecimal getPlanExecuteDeviationMonthMid(BigDecimal monthEndIncrement, BigDecimal monthMidPlan, BigDecimal monthEndPlan);

    /**
     * @Description 8.计划执行偏离度--月末月初偏离度得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndIncrement 月末实际月增量
     * @param monthBeginPlan    月初总行下达计划
     * @Return java.math.BigDecimal
     */
    BigDecimal getPlanExecuteDeviationMonthInit(BigDecimal monthEndIncrement, BigDecimal monthBeginPlan);

    /**
     * @Description  9.调整频次得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param adjCount 月中统一动态调整计划通知分行后分行额外的调整次数
     * @Return java.math.BigDecimal
     */
    BigDecimal getAdjCount(BigDecimal adjCount);

    /**
     * @Description 10.均未产生超规模占用费和规模闲置费得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param scaleAmountMonthCount 该季度内没有产生超规模占用费和规模闲置费的月份数
     * @Return java.math.BigDecimal
     */
    BigDecimal getScaleAmountQuarter(BigDecimal scaleAmountMonthCount);

    /**
     * @Description 11.调整频次未扣分得分
     * @Author liujinbo
     * @Date 2020/1/15
     * @param adjCountMonthCount 该季度内调整频次指标未扣分的月份数
     * @Return java.math.BigDecimal
     */
    BigDecimal getAdjCountQuarter(BigDecimal adjCountMonthCount);


    /**
     * @Author: Liujinbo
     * @Description:  12.季度评级
     * @Date: 2020/2/4
     * @param grade : 机构当前名次
     * @param count : 总机构数
     * @return: java.math.BigDecimal
     **/
    BigDecimal getQuarterLevel(BigDecimal grade, BigDecimal count);

}
