package com.boco.PUB.service.tbOrganRateParam;

import java.math.BigDecimal;

/**
 * @Author: liujinbo
 * @Description: �������ּ�����
 * @Date: 2020/1/15
 * @Version: 1.0
 */
public interface OrganRateParamCalculateService {

// 1.��������÷�
// 2.��Ӫ��������ȵ÷�
// 3.�·����������ʵ÷�
// 4.����ṹ�÷�
// 5.����ģռ�÷Ѻ͹�ģ���÷ѵ÷�
// 6.�ƻ�����ƫ��ȵ÷�
// 7.�ƻ�ִ��ƫ���--��ĩ����ƫ��ȵ÷�
// 8.�ƻ�ִ��ƫ���--��ĩ�³�ƫ��ȵ÷�
// 9.����Ƶ�ε÷�
// 10.��δ��������ģռ�÷Ѻ͹�ģ���÷ѵ÷�
// 11.����Ƶ��δ�۷ֵ÷�
// 12.���������÷�

    /**
     * @Description 1.��������÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndRatio ��ĩ������
     * @param yearBeginRatio ���������
     * @Return java.math.BigDecimal
     */
    BigDecimal getBadCondition(BigDecimal monthEndRatio, BigDecimal yearBeginRatio);

    /**
     * @Description 2.��Ӫ��������ȵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param loanYearIncrement �������������
     * @param personDepositYearIncrement ������Ӫ���������
     * @param companyDepositYearIncrement ��˾���������
     * @Return java.math.BigDecimal
     */
    BigDecimal getDepositLoanRatio(BigDecimal loanYearIncrement, BigDecimal personDepositYearIncrement, BigDecimal companyDepositYearIncrement);

    /**
     * @Description 3.�·����������ʵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param newLoanRatio �·�����������
     * @param BankAverageLoanRatio  ȫ��ƽ����������
     * @Return java.math.BigDecimal
     */
    BigDecimal getNewLoanRatio(BigDecimal newLoanRatio, BigDecimal BankAverageLoanRatio);

    /**
     * @Description 4.����ṹ�÷�
     * @Author liujinbo
     * @Date 2020/1/16
     * @param monthBeginEntityLoan �³�ʵ��������
     * @param monthEndEntityLoan ��ĩʵ��������
     * @param loanCount �ܴ���
     * @Return java.math.BigDecimal
     */
    BigDecimal getLoanStruct(BigDecimal monthBeginEntityLoan, BigDecimal monthEndEntityLoan, BigDecimal loanCount);


    /**
     * @Description 5.����ģռ�÷Ѻ͹�ģ���÷ѵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param organScaleAmount ���з�Ϣ���
     * @param bankScaleAmount ȫ�з�Ϣ���
     * @Return java.math.BigDecimal
     */
    BigDecimal getScaleAmount(BigDecimal organScaleAmount, BigDecimal bankScaleAmount);

    /**
     * @Description 6.�ƻ�����ƫ��ȵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndIncrement ��ĩʵ��������
     * @param monthBeginReport �³���������
     * @Return java.math.BigDecimal
     */
    BigDecimal getPlanSubmitDeviation(BigDecimal monthEndIncrement, BigDecimal monthBeginReport);

    /** 7.�ƻ�ִ��ƫ���--��ĩ����ƫ��ȵ÷�
     * @Description
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndIncrement ��ĩʵ��������
     * @param monthMidPlan   ����ͳһ��̬������ƻ�
     * @param monthEndPlan   ��ĩͳһ��̬������ƻ�
     * @Return java.math.BigDecimal
     */
    BigDecimal getPlanExecuteDeviationMonthMid(BigDecimal monthEndIncrement, BigDecimal monthMidPlan, BigDecimal monthEndPlan);

    /**
     * @Description 8.�ƻ�ִ��ƫ���--��ĩ�³�ƫ��ȵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param monthEndIncrement ��ĩʵ��������
     * @param monthBeginPlan    �³������´�ƻ�
     * @Return java.math.BigDecimal
     */
    BigDecimal getPlanExecuteDeviationMonthInit(BigDecimal monthEndIncrement, BigDecimal monthBeginPlan);

    /**
     * @Description  9.����Ƶ�ε÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param adjCount ����ͳһ��̬�����ƻ�֪ͨ���к���ж���ĵ�������
     * @Return java.math.BigDecimal
     */
    BigDecimal getAdjCount(BigDecimal adjCount);

    /**
     * @Description 10.��δ��������ģռ�÷Ѻ͹�ģ���÷ѵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param scaleAmountMonthCount �ü�����û�в�������ģռ�÷Ѻ͹�ģ���÷ѵ��·���
     * @Return java.math.BigDecimal
     */
    BigDecimal getScaleAmountQuarter(BigDecimal scaleAmountMonthCount);

    /**
     * @Description 11.����Ƶ��δ�۷ֵ÷�
     * @Author liujinbo
     * @Date 2020/1/15
     * @param adjCountMonthCount �ü����ڵ���Ƶ��ָ��δ�۷ֵ��·���
     * @Return java.math.BigDecimal
     */
    BigDecimal getAdjCountQuarter(BigDecimal adjCountMonthCount);


    /**
     * @Author: Liujinbo
     * @Description:  12.��������
     * @Date: 2020/2/4
     * @param grade : ������ǰ����
     * @param count : �ܻ�����
     * @return: java.math.BigDecimal
     **/
    BigDecimal getQuarterLevel(BigDecimal grade, BigDecimal count);

}
