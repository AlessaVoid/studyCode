package com.boco.PUB.service.tbOrganRateParam.impl;

import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.OrganRateParamCalculateService;
import com.boco.SYS.entity.TbOrganRateParam;
import com.boco.SYS.mapper.TbOrganRateParamMapper;
import com.boco.SYS.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * @Author: liujinbo
 * @Description: �������ּ�����
 * �����ֵ�Ĺ����в����о��ȴ�����������÷������У��÷ֱ���С�����4λ
 * 1.���а��հٷֱȵĶ����ٷֱȿ۷֣�ÿ0.1��1�֣���ô0.01��0.1�֣��������÷֣��������С�����4λ
 * 2.���а��ٷֱȵ÷֣�ָ����0-100%֮���15�֣�ָ����100%-200%֮���11�֣�100%�͵�15�֣�
 * @Date: 2020/1/15
 * @Version: 1.0
 */
@Service
public class OrganRateParamCalculateServiceImpl implements OrganRateParamCalculateService {

    @Autowired
    private TbOrganRateParamMapper organMapper;

    /*1.��������÷�
     * �����������ָ���ֵ10�֣����˷��и��������ĩ�����ʽ�����仯�����
     * ������ֵΪ10�֣������ʽ����ÿ�½�0.01���ٷֵ��0.4�֣�
     * �����ʽ����ÿ����0.01���ٷֵ��0.4�֡���͵÷�Ϊ0�֣���ߵ÷�Ϊ20�֡�
     *
     * ������ = ��ĩ������ - ���������
     */
    @Override
    public BigDecimal getBadCondition(BigDecimal monthEndRatio, BigDecimal yearBeginRatio) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.BAD_CONDITION);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal badRatio = BigDecimalUtil.subtract(monthEndRatio, yearBeginRatio);
        List<TbOrganRateParam> realRateParams = getRealRateParam(badRatio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(badRatio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);


        return score;
    }

    /*2.��Ӫ��������ȵ÷�
     * ����Ӫ��������ȡ� ָ���ֵ10�֣�
     * ��Ӫ���������=�������������/��������Ӫ���������+��˾�������������
     * ָ����0-100%֮���15�֣�ָ����100%-200%֮���11�֣�ָ����200-300%֮���7�֣�
     * ָ����300-400%֮���3�֣�ָ�곬��400%��0�֣�ָ��Ϊ����0�֡�
     * ��͵÷�Ϊ0�֣���ߵ÷�Ϊ15�֡�
     *
     * ��Ӫ��������� = ������������� /��������Ӫ��������� + ��˾�����������
     */
    @Override
    public BigDecimal getDepositLoanRatio(BigDecimal loanYearIncrement, BigDecimal personDepositYearIncrement, BigDecimal companyDepositYearIncrement) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal depositLoanRatio = BigDecimalUtil.divide(loanYearIncrement, BigDecimalUtil.add(personDepositYearIncrement, companyDepositYearIncrement));
        List<TbOrganRateParam> realRateParams = getRealRateParam(depositLoanRatio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(depositLoanRatio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 3.�·����������ʵ÷�
     * ���·����������ʡ�ָ���ֵ10�֣����˷�����������·�������������ȫ��ƽ��ˮƽ�����
     * ������ֵΪ10�֣�ָ��ÿ����ȫ��ƽ��ˮƽ0.01���ٷֵ��0.1�֣�
     * ָ��ÿ����ȫ��ƽ��ˮƽ0.01���ٷֵ��0.1�֡�
     * ��͵÷�Ϊ0�֣���ߵ÷�Ϊ20�֡�
     *
     * �������ʲ� = �·����������� - ȫ��ƽ����������
     */
    @Override
    public BigDecimal getNewLoanRatio(BigDecimal newLoanRatio, BigDecimal bankAverageLoanRatio) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.NEW_LOAN_RATIO);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal ratio = newLoanRatio.subtract(bankAverageLoanRatio);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 4.����ṹ�÷�
     * ������ṹ��ָ���ֵ10�֣�
     * ��ĩʵ��������ռ�Ƚ��³����������������֣�
     * ��ĩʵ��������ռ��ÿ�����³�0.5���ٷֵ㣬��1�֡�
     * ��͵÷�Ϊ0�֡�
     *
     * ��ĩʵ��������ռ�� = ��ĩʵ��������/�ܴ��� - �³�ʵ��������/�ܴ���
     */
    @Override
    public BigDecimal getLoanStruct(BigDecimal monthBeginEntityLoan, BigDecimal monthEndEntityLoan, BigDecimal loanCount) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.LOAN_STRUCT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal ratio = BigDecimalUtil.divide(monthEndEntityLoan, loanCount).subtract(BigDecimalUtil.divide(monthBeginEntityLoan, loanCount));
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 5.����ģռ�÷Ѻ͹�ģ���÷ѵ÷�
     * ������ģռ�÷Ѻ͹�ģ���÷ѡ�ָ���ֵ10�֣�
     * ���³���ģռ�÷Ѻ͹�ģ���÷ѽ��Ϊ0�ĵ�10�֣����з�Ϣռȫ�з�Ϣ������0-5%֮�䣬ÿ��1%��0.4�֣�
     * ���з�Ϣռȫ�з�Ϣ������5-10%֮�䣬ÿ��1%��0.6�֣����з�Ϣռȫ�з�Ϣ������10-15%֮�䣬ÿ��1%��1�֣�
     * ���з�Ϣռȫ�з�Ϣ��������15%����0�֡�
     * ��͵÷�Ϊ0�֡�
     *
     *  ���з�Ϣռ�� =  ���з�Ϣ��� / ȫ�з�Ϣ���
     */
    @Override
    public BigDecimal getScaleAmount(BigDecimal organScaleAmount, BigDecimal bankScaleAmount) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal ratio = BigDecimalUtil.divide(organScaleAmount, bankScaleAmount);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 6.�ƻ�����ƫ��ȵ÷�
     * ���ƻ�����ƫ��ȡ�ָ���ֵ10�֣�������ĩʵ�����������³�����������ƫ����ȡ�
     * ƫ�����=����ĩʵ��������-�³���������/��ĩʵ����������
     * ƫ������ڡ�20%֮�䣬��10�֣�ƫ������ڡ�20%-��30%֮�䣬ÿ��ƫ��1%��0.2�֣�
     * ƫ������ڡ�30%-��40%֮�䣬ÿ��ƫ��1%��0.3�֣�ƫ�����>40%��<-40%,ÿ��ƫ��1%��0.5�֡�
     * ��͵÷�Ϊ0�֡�
     *
     * ƫ����� = ����ĩʵ�������� - �³��������� / ��ĩʵ��������
     */
    @Override
    public BigDecimal getPlanSubmitDeviation(BigDecimal monthEndIncrement, BigDecimal monthBeginReport) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal ratio = BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginReport), monthEndIncrement);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /*  7.�ƻ�ִ��ƫ���--��ĩ����ƫ��ȵ÷�
     * ���˷�����ĩʵ��������������ͳһ��̬������ƻ����ƫ����ȡ�
     * ƫ�����=����ĩʵ��������-����ͳһ��̬������ƻ���/��ĩͳһ��̬������ƻ���
     * ƫ�������-3%-0%֮�䣬��10�֣�ƫ����ȵ���-3%�����0%��ÿ��ƫ��0.5%��0.5�֣�
     * ƫ����ȵ���-5%�����2%��ÿ��ƫ��0.5%��0.75�֣�ƫ����ȵ���-7%�����4%,ÿ��ƫ��0.5%��1�֡�
     * ��͵÷�Ϊ0�֡�
     *
     * ƫ�����=����ĩʵ��������-����ͳһ��̬������ƻ���/��ĩͳһ��̬������ƻ�
     */
    @Override
    public BigDecimal getPlanExecuteDeviationMonthMid(BigDecimal monthEndIncrement, BigDecimal monthMidPlan, BigDecimal monthEndPlan) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal ratio = BigDecimalUtil.divide(monthEndIncrement.subtract(monthMidPlan), monthEndPlan);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /*  8.�ƻ�ִ��ƫ���--��ĩ�³�ƫ��ȵ÷�
     * ���˷�����ĩʵ�����������³������´�ƻ����ƫ����ȡ�
     * ƫ�����=����ĩʵ��������-�³������´�ƻ���/�³������´�ƻ���
     * ƫ�������-10%-0%֮�䣬��15�֣�ƫ����ȵ���-10%�����0%��ÿ��ƫ��1%��0.3�֣�
     * ƫ����ȵ���-20%�����10%��ÿ��ƫ��1%��0.5�֣�ƫ����ȵ���-30%�����20%,ÿ��ƫ��1%��0.7�֡�
     * ��͵÷�Ϊ0�֡�
     *
     * ƫ�����=����ĩʵ��������-�³������´�ƻ���/�³������´�ƻ�
     */
    @Override
    public BigDecimal getPlanExecuteDeviationMonthInit(BigDecimal monthEndIncrement, BigDecimal monthBeginPlan) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱȵ�����
        // 3.��������ɸѡ����
        BigDecimal ratio = BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginPlan), monthBeginPlan);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.����ɸѡ��Ĺ������÷�
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.����÷����
        score = getRealScore(score, realRateParams);

        return score;
    }

    /*  9.����Ƶ�ε÷�
     * ������Ƶ�Ρ�ָ���ֵ15�֣�
     * ������ͳһ��̬�����ƻ�֪ͨ���к󣬷���ÿ��������һ�μƻ�������5�֡�
     * ��͵÷�Ϊ0�֡�
     *
     */
    @Override
    public BigDecimal getAdjCount(BigDecimal adjCount) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.ADJ_COUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.����÷�
        TbOrganRateParam organRateParam = rateList.get(0);
        BigDecimal score = organRateParam.getTargetScore().add(adjCount.multiply(organRateParam.getVarScore()));
        // 3.����÷����
        score = getRealScore(score, rateList);

        return score;
    }

    /*  10.��δ��������ģռ�÷Ѻ͹�ģ���÷ѵ÷�
     * ���������ھ�δ��������ģռ�÷Ѻ͹�ģ���÷ѵģ���15�֣�
     * ����������������������δ��������ģռ�÷Ѻ͹�ģ���÷ѵģ���6�֣�
     */
    @Override
    public BigDecimal getScaleAmountQuarter(BigDecimal scaleAmountMonthCount) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.����÷�
        TbOrganRateParam organRateParam = rateList.get(0);
        BigDecimal score = new BigDecimal("0");
        if (new BigDecimal("2").compareTo(scaleAmountMonthCount) == 0) {
            score = organRateParam.getScaleTwo();
        } else if (new BigDecimal("3").compareTo(scaleAmountMonthCount) == 0) {
            score = organRateParam.getScaleThree();
        }
        // 3.����÷����
        score = BigDecimalUtil.divide(score, new BigDecimal("1"), 4, BigDecimal.ROUND_HALF_UP);

        return score;
    }

    /*  11.����Ƶ��δ�۷ֵ÷�
     * ���������ڡ�����Ƶ�Ρ�ָ���δ�۷ֵģ���12�֡�
     * ���������������������¡�����Ƶ�Ρ�ָ��δ�۷ֵģ���3�֡�
     */
    @Override
    public BigDecimal getAdjCountQuarter(BigDecimal adjCountMonthCount) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.����÷�
        TbOrganRateParam organRateParam = rateList.get(0);
        BigDecimal score = new BigDecimal("0");
        if (new BigDecimal("2").compareTo(adjCountMonthCount) == 0) {
            score = organRateParam.getAdjCountTwo();
        } else if (new BigDecimal("3").compareTo(adjCountMonthCount) == 0) {
            score = organRateParam.getAdjCountThree();
        }
        // 3.����÷����
        score = BigDecimalUtil.divide(score, new BigDecimal("1"), 4, BigDecimal.ROUND_HALF_UP);

        return score;
    }

    @Override
    public BigDecimal getQuarterLevel(BigDecimal grade, BigDecimal count) {
        // 1.��ѯ�������������еĹ���
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.QUARTER_GRADE);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.�жϼ�����İٷֱ�
        BigDecimal ratio = BigDecimalUtil.divide(grade, count);
        // 3.����÷�
        BigDecimal score = getVarScore(ratio, rateList);

        return score;
    }

    //���ݼ�����İٷֱȵ�������ȡ������Ҫ�Ĺ��򣬲��ҽ�������
    private List<TbOrganRateParam> getRealRateParam(BigDecimal ratio, List<TbOrganRateParam> list) {
        ListIterator<TbOrganRateParam> it = list.listIterator();
        //�����־������,Ĭ�ϸ���������
        boolean sort = true;
        // ɸѡ����
        if (ratio.compareTo(new BigDecimal("0")) == -1) {
            while (it.hasNext()) {
                //������ֵ����1�ľ���������
                if (it.next().getHigh().compareTo(new BigDecimal("0")) == 1) {
                    it.remove();
                }
            }

        } else {
            sort = false;
            while (it.hasNext()) {
                //������ֵС��-1�ľ��Ǹ�����
                if (it.next().getLow().compareTo(new BigDecimal("0")) == -1) {
                    it.remove();
                }
            }
        }

        // �Թ����������
        if (sort) {
            // ����������
            Collections.sort(list, new Comparator<TbOrganRateParam>() {
                @Override
                public int compare(TbOrganRateParam o1, TbOrganRateParam o2) {
                    if (o1.getLow().compareTo(o2.getLow()) == -1) {
                        return 1;
                    } else if (o1.getLow().compareTo(o2.getLow()) == 1) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

        } else {
            // ����������
            Collections.sort(list, new Comparator<TbOrganRateParam>() {
                @Override
                public int compare(TbOrganRateParam o1, TbOrganRateParam o2) {
                    if (o1.getLow().compareTo(o2.getLow()) == -1) {
                        return -1;
                    } else if (o1.getLow().compareTo(o2.getLow()) == 1) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        return list;
    }

    //��ȡ�÷�
    private BigDecimal getVarScore(BigDecimal ratio, List<TbOrganRateParam> list) {

        if (list == null || list.size() == 0 || ratio == null) {
            return new BigDecimal("0");
        }

        //��ʼ�÷�
        BigDecimal score = list.get(0).getTargetScore();

        if (ratio.compareTo(new BigDecimal("0")) == -1) {
            // ����С��0�����
            for (TbOrganRateParam organRateParam : list) {
                if (ratio.compareTo(organRateParam.getLow()) == -1) {
                    if (organRateParam.getLowHighVar() == null || organRateParam.getLowHighVar().compareTo(new BigDecimal("0")) == 0) {
                        score = organRateParam.getDirectScore();
                    } else {
                        // score +=  |(low - high)/LowHighVar| * varscore
                        score = BigDecimalUtil.divide(organRateParam.getLow().subtract(organRateParam.getHigh()), organRateParam.getLowHighVar()).abs().multiply(organRateParam.getVarScore()).add(score);
                    }
                } else {
                    if (ratio.compareTo(organRateParam.getHigh()) == -1) {
                        if (organRateParam.getLowHighVar() == null || organRateParam.getLowHighVar().compareTo(new BigDecimal("0")) == 0) {
                            score = organRateParam.getDirectScore();
                        } else {
                            // score +=  |(ratio - high)/LowHighVar| * varscore
                            score = BigDecimalUtil.divide(ratio.subtract(organRateParam.getHigh()), organRateParam.getLowHighVar()).abs().multiply(organRateParam.getVarScore()).add(score);
                        }
                    } else if (organRateParam.getHigh().compareTo(organRateParam.getLow()) == 0) {
                        score = organRateParam.getDirectScore();
                    }
                }

            }

        } else {
            // �������ڵ���0�����
            for (TbOrganRateParam organRateParam : list) {
                if (ratio.compareTo(organRateParam.getHigh()) == 1) {
                    if (organRateParam.getLowHighVar() == null || organRateParam.getLowHighVar().compareTo(new BigDecimal("0")) == 0) {
                        score = organRateParam.getDirectScore();
                    } else {
                        // score +=  |(high - low)/LowHighVar| * varscore
                        score = BigDecimalUtil.divide(organRateParam.getHigh().subtract(organRateParam.getLow()), organRateParam.getLowHighVar()).abs().multiply(organRateParam.getVarScore()).add(score);
                    }
                } else {
                    if (ratio.compareTo(organRateParam.getLow()) == 1) {
                        if (organRateParam.getLowHighVar() == null || organRateParam.getLowHighVar().compareTo(new BigDecimal("0")) == 0) {
                            score = organRateParam.getDirectScore();
                        } else {
                            // score +=  |(ratio - low)/LowHighVar| * varscore
                            score = BigDecimalUtil.divide(ratio.subtract(organRateParam.getLow()), organRateParam.getLowHighVar()).abs().multiply(organRateParam.getVarScore()).add(score);
                        }
                    }
                    if (organRateParam.getHigh().compareTo(organRateParam.getLow()) == 0) {
                        score = organRateParam.getDirectScore();
                    } else if (organRateParam.getLow().compareTo(BigDecimal.ZERO) == 0 && ratio.compareTo(BigDecimal.ZERO) == 0) {
                        if (organRateParam.getLowHighVar() == null || organRateParam.getLowHighVar().compareTo(new BigDecimal("0")) == 0) {
                            score = organRateParam.getDirectScore();
                        } else {
                            score = BigDecimalUtil.divide(ratio.subtract(organRateParam.getLow()), organRateParam.getLowHighVar()).abs().multiply(organRateParam.getVarScore()).add(score);
                        }
                    }
                }

            }

        }
        return score;

    }


    //��ʽ���÷֣�����С�����4λ
    private BigDecimal getRealScore(BigDecimal score, List<TbOrganRateParam> list) {
        TbOrganRateParam organRateParam = list.get(0);
        if (score.compareTo(organRateParam.getMinTargetScore()) == -1) {
            score = organRateParam.getMinTargetScore();
        } else if (score.compareTo(organRateParam.getMaxTargetScore()) == 1) {
            score = organRateParam.getMaxTargetScore();
        }
        score = BigDecimalUtil.divide(score, new BigDecimal("1"), 4, BigDecimal.ROUND_HALF_UP);
        return score;
    }


}
