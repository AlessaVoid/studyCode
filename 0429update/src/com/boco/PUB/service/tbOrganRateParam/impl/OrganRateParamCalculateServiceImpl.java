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
 * @Description: 机构评分计算类
 * 计算分值的过程中不进行精度处理，最后处理所得分数就行，得分保留小数点后4位
 * 1.所有按照百分比的都按百分比扣分（每0.1扣1分，那么0.01扣0.1分），最后处理得分，结果保留小数点后4位
 * 2.所有按百分比得分（指标在0-100%之间得15分；指标在100%-200%之间得11分；100%就得15分）
 * @Date: 2020/1/15
 * @Version: 1.0
 */
@Service
public class OrganRateParamCalculateServiceImpl implements OrganRateParamCalculateService {

    @Autowired
    private TbOrganRateParamMapper organMapper;

    /*1.不良情况得分
     * “不良情况”指标分值10分，考核分行各项贷款月末不良率较年初变化情况。
     * 基础分值为10分，不良率较年初每下降0.01个百分点加0.4分；
     * 不良率较年初每上升0.01个百分点扣0.4分。最低得分为0分，最高得分为20分。
     *
     * 不良率 = 月末不良率 - 年初不良率
     */
    @Override
    public BigDecimal getBadCondition(BigDecimal monthEndRatio, BigDecimal yearBeginRatio) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.BAD_CONDITION);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal badRatio = BigDecimalUtil.subtract(monthEndRatio, yearBeginRatio);
        List<TbOrganRateParam> realRateParams = getRealRateParam(badRatio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(badRatio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);


        return score;
    }

    /*2.自营新增存贷比得分
     * “自营新增存贷比” 指标分值10分，
     * 自营新增存贷比=各项贷款年增量/（个人自营存款年增量+公司存款年增量），
     * 指标在0-100%之间得15分；指标在100%-200%之间得11分；指标在200-300%之间得7分；
     * 指标在300-400%之间得3分；指标超过400%得0分；指标为负得0分。
     * 最低得分为0分，最高得分为15分。
     *
     * 自营新增存贷比 = 各项贷款年增量 /（个人自营存款年增量 + 公司存款年增量）
     */
    @Override
    public BigDecimal getDepositLoanRatio(BigDecimal loanYearIncrement, BigDecimal personDepositYearIncrement, BigDecimal companyDepositYearIncrement) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal depositLoanRatio = BigDecimalUtil.divide(loanYearIncrement, BigDecimalUtil.add(personDepositYearIncrement, companyDepositYearIncrement));
        List<TbOrganRateParam> realRateParams = getRealRateParam(depositLoanRatio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(depositLoanRatio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 3.新发生贷款利率得分
     * “新发生贷款利率”指标分值10分，考核分行年初以来新发生贷款利率与全行平均水平情况。
     * 基础分值为10分，指标每高于全行平均水平0.01个百分点加0.1分；
     * 指标每低于全行平均水平0.01个百分点扣0.1分。
     * 最低得分为0分，最高得分为20分。
     *
     * 贷款利率差 = 新发生贷款利率 - 全行平均贷款利率
     */
    @Override
    public BigDecimal getNewLoanRatio(BigDecimal newLoanRatio, BigDecimal bankAverageLoanRatio) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.NEW_LOAN_RATIO);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal ratio = newLoanRatio.subtract(bankAverageLoanRatio);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 4.贷款结构得分
     * “贷款结构”指标分值10分，
     * 月末实体贷款余额占比较月初有所上升，得满分；
     * 月末实体贷款余额占比每低于月初0.5个百分点，扣1分。
     * 最低得分为0分。
     *
     * 月末实体贷款余额占比 = 月末实体贷款余额/总贷款 - 月初实体贷款余额/总贷款
     */
    @Override
    public BigDecimal getLoanStruct(BigDecimal monthBeginEntityLoan, BigDecimal monthEndEntityLoan, BigDecimal loanCount) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.LOAN_STRUCT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal ratio = BigDecimalUtil.divide(monthEndEntityLoan, loanCount).subtract(BigDecimalUtil.divide(monthBeginEntityLoan, loanCount));
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 5.超规模占用费和规模闲置费得分
     * “超规模占用费和规模闲置费”指标分值10分，
     * 当月超规模占用费和规模闲置费金额为0的得10分；分行罚息占全行罚息比例在0-5%之间，每多1%扣0.4分；
     * 分行罚息占全行罚息比例在5-10%之间，每多1%扣0.6分；分行罚息占全行罚息比例在10-15%之间，每多1%扣1分；
     * 分行罚息占全行罚息比例超过15%，得0分。
     * 最低得分为0分。
     *
     *  分行罚息占比 =  分行罚息金额 / 全行罚息金额
     */
    @Override
    public BigDecimal getScaleAmount(BigDecimal organScaleAmount, BigDecimal bankScaleAmount) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal ratio = BigDecimalUtil.divide(organScaleAmount, bankScaleAmount);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /* 6.计划报送偏离度得分
     * “计划报送偏离度”指标分值10分，考核月末实际月增量与月初报送需求间的偏离幅度。
     * 偏离幅度=（月末实际月增量-月初报送需求）/月末实际月增量，
     * 偏离幅度在±20%之间，得10分；偏离幅度在±20%-±30%之间，每多偏离1%扣0.2分；
     * 偏离幅度在±30%-±40%之间，每多偏离1%扣0.3分；偏离幅度>40%或<-40%,每多偏离1%扣0.5分。
     * 最低得分为0分。
     *
     * 偏离幅度 = （月末实际月增量 - 月初报送需求） / 月末实际月增量
     */
    @Override
    public BigDecimal getPlanSubmitDeviation(BigDecimal monthEndIncrement, BigDecimal monthBeginReport) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal ratio = BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginReport), monthEndIncrement);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /*  7.计划执行偏离度--月末月中偏离度得分
     * 考核分行月末实际月增量与月中统一动态调整后计划间的偏离幅度。
     * 偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划，
     * 偏离幅度在-3%-0%之间，得10分；偏离幅度低于-3%或大于0%，每多偏离0.5%扣0.5分；
     * 偏离幅度低于-5%或大于2%，每多偏离0.5%扣0.75分；偏离幅度低于-7%或大于4%,每多偏离0.5%扣1分。
     * 最低得分为0分。
     *
     * 偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划
     */
    @Override
    public BigDecimal getPlanExecuteDeviationMonthMid(BigDecimal monthEndIncrement, BigDecimal monthMidPlan, BigDecimal monthEndPlan) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal ratio = BigDecimalUtil.divide(monthEndIncrement.subtract(monthMidPlan), monthEndPlan);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /*  8.计划执行偏离度--月末月初偏离度得分
     * 考核分行月末实际月增量与月初总行下达计划间的偏离幅度。
     * 偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划，
     * 偏离幅度在-10%-0%之间，得15分；偏离幅度低于-10%或大于0%，每多偏离1%扣0.3分；
     * 偏离幅度低于-20%或大于10%，每多偏离1%扣0.5分；偏离幅度低于-30%或大于20%,每多偏离1%扣0.7分。
     * 最低得分为0分。
     *
     * 偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划
     */
    @Override
    public BigDecimal getPlanExecuteDeviationMonthInit(BigDecimal monthEndIncrement, BigDecimal monthBeginPlan) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比的正负
        // 3.根据正负筛选规则
        BigDecimal ratio = BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginPlan), monthBeginPlan);
        List<TbOrganRateParam> realRateParams = getRealRateParam(ratio, rateList);
        // 4.根据筛选完的规则计算得分
        BigDecimal score = getVarScore(ratio, realRateParams);
        // 5.处理得分情况
        score = getRealScore(score, realRateParams);

        return score;
    }

    /*  9.调整频次得分
     * “调整频次”指标分值15分，
     * 在月中统一动态调整计划通知分行后，分行每额外申请一次计划调整扣5分。
     * 最低得分为0分。
     *
     */
    @Override
    public BigDecimal getAdjCount(BigDecimal adjCount) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.ADJ_COUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.计算得分
        TbOrganRateParam organRateParam = rateList.get(0);
        BigDecimal score = organRateParam.getTargetScore().add(adjCount.multiply(organRateParam.getVarScore()));
        // 3.处理得分情况
        score = getRealScore(score, rateList);

        return score;
    }

    /*  10.均未产生超规模占用费和规模闲置费得分
     * 考评季度内均未产生超规模占用费和规模闲置费的，加15分；
     * 考评季度内有任意两个月未产生超规模占用费和规模闲置费的，加6分；
     */
    @Override
    public BigDecimal getScaleAmountQuarter(BigDecimal scaleAmountMonthCount) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.计算得分
        TbOrganRateParam organRateParam = rateList.get(0);
        BigDecimal score = new BigDecimal("0");
        if (new BigDecimal("2").compareTo(scaleAmountMonthCount) == 0) {
            score = organRateParam.getScaleTwo();
        } else if (new BigDecimal("3").compareTo(scaleAmountMonthCount) == 0) {
            score = organRateParam.getScaleThree();
        }
        // 3.处理得分情况
        score = BigDecimalUtil.divide(score, new BigDecimal("1"), 4, BigDecimal.ROUND_HALF_UP);

        return score;
    }

    /*  11.调整频次未扣分得分
     * 考评季度内“调整频次”指标均未扣分的，加12分。
     * 考评季度内有任意两个月“调整频次”指标未扣分的，加3分。
     */
    @Override
    public BigDecimal getAdjCountQuarter(BigDecimal adjCountMonthCount) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.计算得分
        TbOrganRateParam organRateParam = rateList.get(0);
        BigDecimal score = new BigDecimal("0");
        if (new BigDecimal("2").compareTo(adjCountMonthCount) == 0) {
            score = organRateParam.getAdjCountTwo();
        } else if (new BigDecimal("3").compareTo(adjCountMonthCount) == 0) {
            score = organRateParam.getAdjCountThree();
        }
        // 3.处理得分情况
        score = BigDecimalUtil.divide(score, new BigDecimal("1"), 4, BigDecimal.ROUND_HALF_UP);

        return score;
    }

    @Override
    public BigDecimal getQuarterLevel(BigDecimal grade, BigDecimal count) {
        // 1.查询出该评分项所有的规则
        TbOrganRateParam organRateParamQuery = new TbOrganRateParam();
        organRateParamQuery.setElementType(OrganRateParamElementType.QUARTER_GRADE);
        List<TbOrganRateParam> rateList = organMapper.selectByAttr(organRateParamQuery);
        // 2.判断计算出的百分比
        BigDecimal ratio = BigDecimalUtil.divide(grade, count);
        // 3.计算得分
        BigDecimal score = getVarScore(ratio, rateList);

        return score;
    }

    //根据计算出的百分比的正负获取真正需要的规则，并且进行排序
    private List<TbOrganRateParam> getRealRateParam(BigDecimal ratio, List<TbOrganRateParam> list) {
        ListIterator<TbOrganRateParam> it = list.listIterator();
        //排序标志，正负,默认负规则排序
        boolean sort = true;
        // 筛选规则
        if (ratio.compareTo(new BigDecimal("0")) == -1) {
            while (it.hasNext()) {
                //高区间值大于1的就是正规则
                if (it.next().getHigh().compareTo(new BigDecimal("0")) == 1) {
                    it.remove();
                }
            }

        } else {
            sort = false;
            while (it.hasNext()) {
                //低区间值小于-1的就是负规则
                if (it.next().getLow().compareTo(new BigDecimal("0")) == -1) {
                    it.remove();
                }
            }
        }

        // 对规则进行排序
        if (sort) {
            // 负规则排序
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
            // 正规则排序
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

    //获取得分
    private BigDecimal getVarScore(BigDecimal ratio, List<TbOrganRateParam> list) {

        if (list == null || list.size() == 0 || ratio == null) {
            return new BigDecimal("0");
        }

        //初始得分
        BigDecimal score = list.get(0).getTargetScore();

        if (ratio.compareTo(new BigDecimal("0")) == -1) {
            // 比例小于0的情况
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
            // 比例大于等于0的情况
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


    //格式化得分，保留小数点后4位
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
