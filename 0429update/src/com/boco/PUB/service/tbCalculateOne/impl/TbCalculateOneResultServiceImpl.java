package com.boco.PUB.service.tbCalculateOne.impl;

import com.boco.PUB.POJO.DO.CalculateOneParam;
import com.boco.PUB.service.tbCalculateOne.TbCalculateOneResultService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 测算 结果表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbCalculateOneResultServiceImpl extends GenericService<TbCalculateOneResult, String> implements TbCalculateOneResultService {

    @Autowired
    private TbCalculateOneResultMapper tbCalculateOneResultMapper;
    @Autowired
    private TbCalculateOneRankMapper tbCalculateOneRankMapper;
    @Autowired
    private TbCalculateOneProportionMapper tbCalculateOneProportionMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;

    @Autowired
    private TbTradeParamMapper tbTradeParamMapper;
    @Autowired
    private TbCalculateOneImportDataMapper tbCalculateOneImportDataMapper;
    @Autowired
    private TbCalculateOneProportionHistoryMapper tbCalculateOneProportionHistoryMapper;


    /*生成测算结果 模式一*/
    @Override
    public void addCalculateOneResult() throws Exception {
        //检查是否已经存在测算结果 如果存在就删除
        //获取本月月份
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String month = format.format(new Date());

        TbCalculateOneResult resultQuery = new TbCalculateOneResult();
        resultQuery.setMonth(month);
        List<TbCalculateOneResult> resultList = tbCalculateOneResultMapper.selectByAttr(resultQuery);
        if (resultList != null && resultList.size() != 0) {
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("month", month);
            queryMap.put("type", CalculateOneParam.CALCULATE_MONTH);
            tbCalculateOneResultMapper.deleteByMonthAndType(queryMap);
        }

        //生成测算参数list<map>
        List<Map<String, Object>> calculateOneParamList = buildCalculateOneParam();

        //生成测算结果
        List<TbCalculateOneResult> tbCalculateOneResultList = buildCalCulateOneResult(calculateOneParamList);

        //插入测算结果表
        tbCalculateOneResultMapper.insertBatch(tbCalculateOneResultList);

        //插入测算权重参数历史表
        saveCalculateParamHistory(month,CalculateOneParam.CALCULATE_MONTH);

    }
    // 插入测算权重参数历史表
    private void saveCalculateParamHistory(String month, BigDecimal type) {
        // 查询是否存在历史，如果存在就删除
        TbCalculateOneProportionHistory Query = new TbCalculateOneProportionHistory();
        Query.setMonth(month);
        Query.setType(type);
        List<TbCalculateOneProportionHistory> tbCalculateOneProportionHistorieList = tbCalculateOneProportionHistoryMapper.selectByAttr(Query);
        if (tbCalculateOneProportionHistorieList != null && tbCalculateOneProportionHistorieList.size() != 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("month", month);
            map.put("type", type);
            tbCalculateOneProportionHistoryMapper.deleteByMonthAndType(map);
        }

        List<TbCalculateOneProportion> tbCalculateOneProportionList = tbCalculateOneProportionMapper.selectByAttr(new TbCalculateOneProportion());
        //构建历史参数
        List<TbCalculateOneProportionHistory> historyList = buildProportionHistoryList(tbCalculateOneProportionList,month,type);

        tbCalculateOneProportionHistoryMapper.insertBatch(historyList);
    }

    //构建历史参数
    private List<TbCalculateOneProportionHistory> buildProportionHistoryList(List<TbCalculateOneProportion> tbCalculateOneProportionList, String month, BigDecimal type) {
        ArrayList<TbCalculateOneProportionHistory> list = new ArrayList<>();
        for (TbCalculateOneProportion proportion : tbCalculateOneProportionList) {
            TbCalculateOneProportionHistory his = new TbCalculateOneProportionHistory();

            his.setId(IDGeneratorUtils.getSequence());
            his.setClassType(proportion.getClassType());
            his.setCode(proportion.getCode());
            his.setName(proportion.getName());
            his.setIndexType(proportion.getIndexType());
            his.setSortType(proportion.getSortType());
            his.setWeight(proportion.getWeight());
            his.setRatio(proportion.getRatio());
            his.setCreateOper(proportion.getCreateOper());
            his.setCreateTime(proportion.getCreateTime());
            his.setUpdateTime(proportion.getUpdateTime());
            his.setUpdateOper(proportion.getUpdateOper());
            his.setMonth(month);
            his.setType(type);

            list.add(his);
        }
        return list;
    }


    //根据参数生成测算结果
    private List<TbCalculateOneResult> buildCalCulateOneResult(List<Map<String, Object>> calculateOneParamList) {

        // 查询权重系数，主副指标，升序降序，占比系数
        List<TbCalculateOneProportion> tbCalculateOneProportionList = tbCalculateOneProportionMapper.selectByAttr(new TbCalculateOneProportion());
        //主指标
        HashMap<String, TbCalculateOneProportion> majorIndexMap = new HashMap<>();
        //副指标
        ArrayList<TbCalculateOneProportion> secondIndexList = new ArrayList<>();
        //4大类每个类用到的指标
        ArrayList<TbCalculateOneProportion> depositList = new ArrayList<>();
        ArrayList<TbCalculateOneProportion> structList = new ArrayList<>();
        ArrayList<TbCalculateOneProportion> marketList = new ArrayList<>();
        ArrayList<TbCalculateOneProportion> benefitList = new ArrayList<>();
        //4大类的占比
        BigDecimal depositRatio = BigDecimal.ZERO;
        BigDecimal structRatio = BigDecimal.ZERO;
        BigDecimal marketRatio = BigDecimal.ZERO;
        BigDecimal benefitRatio = BigDecimal.ZERO;

        for (TbCalculateOneProportion proportion : tbCalculateOneProportionList) {
            //判断主副指标
            if (proportion.getIndexType().compareTo(CalculateOneParam.MAJOR_INDEX) == 0) {
                if (proportion.getClassType().compareTo(CalculateOneParam.DEPOSIT) == 0) {
                    majorIndexMap.put("deposit", proportion);
                } else if (proportion.getClassType().compareTo(CalculateOneParam.STRUCT) == 0) {
                    majorIndexMap.put("struct", proportion);
                } else if (proportion.getClassType().compareTo(CalculateOneParam.MARKET) == 0) {
                    majorIndexMap.put("market", proportion);
                } else if (proportion.getClassType().compareTo(CalculateOneParam.BENEFIT) == 0) {
                    majorIndexMap.put("benefit", proportion);
                }
            } else if (proportion.getIndexType().compareTo(CalculateOneParam.SECOND_INDEX) == 0) {
                secondIndexList.add(proportion);
            }

            // 4大类每个类用到的指标
            if (proportion.getIndexType().compareTo(CalculateOneParam.OTHER_INDEX) != 0) {
                if (proportion.getClassType().compareTo(CalculateOneParam.DEPOSIT) == 0) {
                    depositList.add(proportion);
                    depositRatio = proportion.getRatio();
                } else if (proportion.getClassType().compareTo(CalculateOneParam.STRUCT) == 0) {
                    structList.add(proportion);
                    structRatio = proportion.getRatio();
                } else if (proportion.getClassType().compareTo(CalculateOneParam.MARKET) == 0) {
                    marketList.add(proportion);
                    marketRatio = proportion.getRatio();
                } else if (proportion.getClassType().compareTo(CalculateOneParam.BENEFIT) == 0) {
                    benefitList.add(proportion);
                    benefitRatio = proportion.getRatio();
                }
            }
        }

        //计算主指标
        buildMajorIndex(calculateOneParamList, majorIndexMap);

        //计算副指标
        buildSecondIndex(calculateOneParamList,secondIndexList);

        //计算每个大类的权重
        buildWeight(calculateOneParamList, depositList, structList, marketList, benefitList);

        //计算计划分配比例
        buildPlanRatio(calculateOneParamList, depositRatio, structRatio, marketRatio, benefitRatio);

        //构建测算结果详情
        ArrayList<TbCalculateOneResult> tbCalculateOneResultList = buildCalculateOneResultList(calculateOneParamList);


        return tbCalculateOneResultList;
    }

    // 构建测算结果详情
    private ArrayList<TbCalculateOneResult> buildCalculateOneResultList(List<Map<String, Object>> calculateOneParamList) {
        ArrayList<TbCalculateOneResult> resultList = new ArrayList<>();
        for (Map<String, Object> map : calculateOneParamList) {
            TbCalculateOneResult result = new TbCalculateOneResult();

            result.setId(IDGeneratorUtils.getSequence());
            result.setOrgancode(map.get("organcode").toString());
            result.setMonth(map.get("month").toString());
            result.setType(getSafeCount(map.get("type")));
            result.setReqAmount(BigDecimal.ZERO);

            result.setCode1(getSafeCount(map.get("code1")));
            result.setCode2(getSafeCount(map.get("code2")));
            result.setCode3(getSafeCount(map.get("code3")));
            result.setCode4(getSafeCount(map.get("code4")));
            result.setCode5(getSafeCount(map.get("code5")));
            result.setCode6(getSafeCount(map.get("code6")));
            result.setCode7(getSafeCount(map.get("code7")));
            result.setCode8(getSafeCount(map.get("code8")));
            result.setCode9(getSafeCount(map.get("code9")));
            result.setCode10(getSafeCount(map.get("code10")));
            result.setCode11(getSafeCount(map.get("code11")));
            result.setCode12(getSafeCount(map.get("code12")));
            result.setCode13(getSafeCount(map.get("code13")));
            result.setCode14(getSafeCount(map.get("code14")));
            result.setCode15(getSafeCount(map.get("code15")));
            result.setCode16(getSafeCount(map.get("code16")));
            result.setCode17(getSafeCount(map.get("code17")));
            result.setCode18(getSafeCount(map.get("code18")));
            result.setCode19(getSafeCount(map.get("code19")));
            result.setCode20(getSafeCount(map.get("code20")));
            result.setCode21(getSafeCount(map.get("code21")));
            result.setCode22(getSafeCount(map.get("code22")));
            result.setCode23(getSafeCount(map.get("code23")));
            result.setCode24(getSafeCount(map.get("code24")));
            result.setCode25(getSafeCount(map.get("code25")));
            result.setCode26(getSafeCount(map.get("code26")));
            result.setCode27(getSafeCount(map.get("code27")));
            result.setCode28(getSafeCount(map.get("code28")));
            result.setCode29(getSafeCount(map.get("code29")));
            result.setCode30(getSafeCount(map.get("code30")));
            result.setCode31(getSafeCount(map.get("code31")));
            result.setCode32(getSafeCount(map.get("code32")));
            result.setCode33(getSafeCount(map.get("code33")));
            result.setCode34(getSafeCount(map.get("code34")));
            result.setCode35(getSafeCount(map.get("code35")));

            result.setCode1Result(getSafeCount(map.get("code1Result")));
            result.setCode2Result(getSafeCount(map.get("code2Result")));
            result.setCode3Result(getSafeCount(map.get("code3Result")));
            result.setCode4Result(getSafeCount(map.get("code4Result")));
            result.setCode5Result(getSafeCount(map.get("code5Result")));
            result.setCode6Result(getSafeCount(map.get("code6Result")));
            result.setCode7Result(getSafeCount(map.get("code7Result")));
            result.setCode8Result(getSafeCount(map.get("code8Result")));
            result.setCode9Result(getSafeCount(map.get("code9Result")));
            result.setCode10Result(getSafeCount(map.get("code10Result")));
            result.setCode11Result(getSafeCount(map.get("code11Result")));
            result.setCode12Result(getSafeCount(map.get("code12Result")));
            result.setCode13Result(getSafeCount(map.get("code13Result")));
            result.setCode14Result(getSafeCount(map.get("code14Result")));
            result.setCode15Result(getSafeCount(map.get("code15Result")));
            result.setCode16Result(getSafeCount(map.get("code16Result")));
            result.setCode17Result(getSafeCount(map.get("code17Result")));
            result.setCode18Result(getSafeCount(map.get("code18Result")));
            result.setCode19Result(getSafeCount(map.get("code19Result")));
            result.setCode20Result(getSafeCount(map.get("code20Result")));
            result.setCode21Result(getSafeCount(map.get("code21Result")));
            result.setCode22Result(getSafeCount(map.get("code22Result")));
            result.setCode23Result(getSafeCount(map.get("code23Result")));
            result.setCode24Result(getSafeCount(map.get("code24Result")));
            result.setCode25Result(getSafeCount(map.get("code25Result")));
            result.setCode26Result(getSafeCount(map.get("code26Result")));
            result.setCode27Result(getSafeCount(map.get("code27Result")));
            result.setCode28Result(getSafeCount(map.get("code28Result")));
            result.setCode29Result(getSafeCount(map.get("code29Result")));
            result.setCode30Result(getSafeCount(map.get("code30Result")));
            result.setCode31Result(getSafeCount(map.get("code31Result")));
            result.setCode32Result(getSafeCount(map.get("code32Result")));
            result.setCode33Result(getSafeCount(map.get("code33Result")));
            result.setCode34Result(getSafeCount(map.get("code34Result")));
            result.setCode35Result(getSafeCount(map.get("code35Result")));

            result.setDepositWeight(getSafeCount(map.get("depositWeight")));
            result.setStructWeight(getSafeCount(map.get("structWeight")));
            result.setMarketWeight(getSafeCount(map.get("marketWeight")));
            result.setBenefitWeight(getSafeCount(map.get("benefitWeight")));

            result.setTotalAmount(getSafeCount(map.get("totalAmount")));
            result.setPlanWeight(getSafeCount(map.get("planWeight")));
            result.setPlanAmount(getSafeCount(map.get("planAmount")));

            result.setCreatTime(BocoUtils.getTime());
            result.setUpdateTime(BocoUtils.getTime());

            resultList.add(result);
        }

        return resultList;
    }

    //计算计划分配比例
    private void buildPlanRatio(List<Map<String, Object>> calculateOneParamList, BigDecimal depositRatio, BigDecimal structRatio, BigDecimal marketRatio, BigDecimal benefitRatio) {

        for (Map<String, Object> map : calculateOneParamList) {
            BigDecimal deposit = getSafeCount(map.get("depositWeight")).multiply(depositRatio);
            BigDecimal struct = getSafeCount(map.get("structWeight")).multiply(structRatio);
            BigDecimal market = getSafeCount(map.get("marketWeight")).multiply(marketRatio);
            BigDecimal benefit = getSafeCount(map.get("benefitWeight")).multiply(benefitRatio);
            //分配比例
            BigDecimal planWeight = deposit.add(struct)
                    .add(market)
                    .add(benefit);
            map.put("planWeight", planWeight);
            map.put("planAmount", getSafeCount(map.get("totalAmount")).multiply(planWeight));

        }
    }

    //计算每个大类的权重
    private void buildWeight(List<Map<String, Object>> calculateOneParamList, ArrayList<TbCalculateOneProportion> depositList, ArrayList<TbCalculateOneProportion> structList, ArrayList<TbCalculateOneProportion> marketList, ArrayList<TbCalculateOneProportion> benefitList) {

        //存款，贷款，需求，效益 所有指标比率乘积的和
        BigDecimal depositCount = BigDecimal.ZERO;
        BigDecimal structCount = BigDecimal.ZERO;
        BigDecimal marketCount = BigDecimal.ZERO;
        BigDecimal benefitCount = BigDecimal.ZERO;

        for (Map<String, Object> map : calculateOneParamList) {
            //存贷联动类
            BigDecimal deposit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : depositList) {
                deposit = deposit.multiply(getSafeCount(map.get(proportion.getCode()+"Result")));
            }
            depositCount = depositCount.add(deposit);
            //结构优化类
            BigDecimal struct = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : structList) {
                struct = struct.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            structCount = structCount.add(struct);
            //市场竞争类
            BigDecimal market = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : marketList) {
                market = market.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            marketCount = marketCount.add(market);
            //价值提升类
            BigDecimal benefit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : benefitList) {
                benefit = benefit.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            benefitCount = benefitCount.add(benefit);
        }

        //开始计算权重
        for (Map<String, Object> map : calculateOneParamList) {
            //存贷联动类
            BigDecimal deposit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : depositList) {
                deposit = deposit.multiply(getSafeCount(map.get(proportion.getCode()+"Result")));
            }
            map.put("depositWeight", BigDecimalUtil.divide(deposit, depositCount));
            //结构优化类
            BigDecimal struct = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : structList) {
                struct = struct.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("structWeight", BigDecimalUtil.divide(struct, structCount));
            //市场竞争类
            BigDecimal market = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : marketList) {
                market = market.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("marketWeight", BigDecimalUtil.divide(market, marketCount));
            //价值提升类
            BigDecimal benefit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : benefitList) {
                benefit = benefit.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("benefitWeight", BigDecimalUtil.divide(benefit, benefitCount));
        }
    }

    //计算副指标
    private void buildSecondIndex(List<Map<String, Object>> tbCalculateOneResultList, ArrayList<TbCalculateOneProportion> secondIndexList) {

        //查询排序系数
        TbCalculateOneRank tbCalculateOneRankQuery = new TbCalculateOneRank();
        tbCalculateOneRankQuery.setStatus(1);
        //存贷联动类
        tbCalculateOneRankQuery.setType(CalculateOneParam.DEPOSIT.intValue());
        HashMap<Integer, BigDecimal> depositRankMap = buildRankMap(tbCalculateOneRankQuery);
        //结构优化类
        tbCalculateOneRankQuery.setType(CalculateOneParam.STRUCT.intValue());
        HashMap<Integer, BigDecimal> structRankMap = buildRankMap(tbCalculateOneRankQuery);
        //市场竞争类
        tbCalculateOneRankQuery.setType(CalculateOneParam.STRUCT.intValue());
        HashMap<Integer, BigDecimal> marketRankMap = buildRankMap(tbCalculateOneRankQuery);
        //价值提升类
        tbCalculateOneRankQuery.setType(CalculateOneParam.STRUCT.intValue());
        HashMap<Integer, BigDecimal> benefitRankMap = buildRankMap(tbCalculateOneRankQuery);

//-------------------------计算副指标↓↓↓↓↓↓↓↓↓↓↓↓
        for (TbCalculateOneProportion proportion : secondIndexList) {
            //获取副指标
            String code = proportion.getCode();
            //获取权重
            BigDecimal weight = proportion.getWeight();
            //获取排序类型,设置排序系数
            BigDecimal sortType = proportion.getSortType();
            int  sort = 1;
            if (sortType.compareTo(new BigDecimal("1")) == 0) {
                sort = -1;
            }
            // 获取4大类类型
            BigDecimal classType = proportion.getClassType();
            //获取对应的排序系数
            HashMap<Integer, BigDecimal> rankMap = new HashMap<>();
            if (classType.compareTo(CalculateOneParam.DEPOSIT) == 0) {
                rankMap = depositRankMap;
            }else if (classType.compareTo(CalculateOneParam.STRUCT) == 0) {
                rankMap = structRankMap;
            }else if (classType.compareTo(CalculateOneParam.MARKET) == 0) {
                rankMap = marketRankMap;
            }else if (classType.compareTo(CalculateOneParam.BENEFIT) == 0) {
                rankMap = benefitRankMap;
            }

            //根据副指标排序
            int finalSort = sort;
            Collections.sort(tbCalculateOneResultList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    if (getSafeCount(o1.get(code)).compareTo(getSafeCount(o2.get(code))) == -1) {
                        return 1* finalSort;
                    } else if (getSafeCount(o1.get(code)).compareTo(getSafeCount(o2.get(code))) == 1) {
                        return -1* finalSort;
                    } else {
                        return 0;
                    }
                }
            });

            for (int i = 0; i < tbCalculateOneResultList.size(); i++) {
                Map<String, Object> map = tbCalculateOneResultList.get(i);
                // 排名系数
                BigDecimal rank = getSafeCount(rankMap.get(i + 1));
                //计算副指标
                BigDecimal secondIndex = getSecondIndex(rank, weight);

                map.put(code + "Result", secondIndex);
            }
        }
    }


    //副指标计算公式
    private BigDecimal getSecondIndex(BigDecimal rank, BigDecimal depositPlanRate) {
        //排名系数*副指标权重+1
        BigDecimal secondIndex = rank.multiply(depositPlanRate).add(BigDecimal.ONE);

        return secondIndex;
    }

    //查询排序系数map
    private HashMap<Integer, BigDecimal> buildRankMap(TbCalculateOneRank tbCalculateOneRankQuery) {
        List<TbCalculateOneRank> tbCalculateOneRankList = tbCalculateOneRankMapper.selectByAttr(tbCalculateOneRankQuery);
        HashMap<Integer, BigDecimal> rankMap = new HashMap<>();
        for (TbCalculateOneRank rank : tbCalculateOneRankList) {
            rankMap.put(1, rank.getRank1());
            rankMap.put(2, rank.getRank2());
            rankMap.put(3, rank.getRank3());
            rankMap.put(4, rank.getRank4());
            rankMap.put(5, rank.getRank5());
            rankMap.put(6, rank.getRank6());
            rankMap.put(7, rank.getRank7());
            rankMap.put(8, rank.getRank8());
            rankMap.put(9, rank.getRank9());
            rankMap.put(10, rank.getRank10());
            rankMap.put(11, rank.getRank11());
            rankMap.put(12, rank.getRank12());
            rankMap.put(13, rank.getRank13());
            rankMap.put(14, rank.getRank14());
            rankMap.put(15, rank.getRank15());
            rankMap.put(16, rank.getRank16());
            rankMap.put(17, rank.getRank17());
            rankMap.put(18, rank.getRank18());
            rankMap.put(19, rank.getRank19());
            rankMap.put(20, rank.getRank20());
            rankMap.put(21, rank.getRank21());
            rankMap.put(22, rank.getRank22());
            rankMap.put(23, rank.getRank23());
            rankMap.put(24, rank.getRank24());
            rankMap.put(25, rank.getRank25());
            rankMap.put(26, rank.getRank26());
            rankMap.put(27, rank.getRank27());
            rankMap.put(28, rank.getRank28());
            rankMap.put(29, rank.getRank29());
            rankMap.put(30, rank.getRank30());
            rankMap.put(31, rank.getRank31());
            rankMap.put(32, rank.getRank32());
            rankMap.put(33, rank.getRank33());
            rankMap.put(34, rank.getRank34());
            rankMap.put(35, rank.getRank35());
            rankMap.put(36, rank.getRank36());
        }
        return rankMap;
    }

    //计算主指标
    private void buildMajorIndex(List<Map<String, Object>> tbCalculateOneResultList, HashMap<String, TbCalculateOneProportion> majorIndexMap) {

        BigDecimal depositCount = BigDecimal.ZERO;
        BigDecimal structCount = BigDecimal.ZERO;
        BigDecimal marketCount = BigDecimal.ZERO;
        BigDecimal benefitCount = BigDecimal.ZERO;

        TbCalculateOneProportion deposit = majorIndexMap.get("deposit");
        TbCalculateOneProportion struct = majorIndexMap.get("struct");
        TbCalculateOneProportion market = majorIndexMap.get("market");
        TbCalculateOneProportion benefit = majorIndexMap.get("benefit");
        String depositCode = "";
        String structCode = "";
        String marketCode = "";
        String benefitCode = "";
        if (deposit != null) {
            depositCode = deposit.getCode();
        }
        if (struct != null) {
            structCode = struct.getCode();
        }
        if (market != null) {
            marketCode = market.getCode();
        }
        if (benefit != null) {
            benefitCode = benefit.getCode();
        }
        //计算各个大类的主指标总和
        for (Map<String, Object> map : tbCalculateOneResultList) {
            depositCount = depositCount.add(getSafeCount(map.get(depositCode)));
            structCount = structCount.add(getSafeCount(map.get(structCode)));
            marketCount = marketCount.add(getSafeCount(map.get(marketCode)));
            benefitCount = benefitCount.add(getSafeCount(map.get(benefitCode)));
        }

        for (Map<String, Object> map  : tbCalculateOneResultList) {
            map.put(depositCode + "Result", BigDecimalUtil.divide(getSafeCount(map.get(depositCode)), depositCount));
            map.put(structCode + "Result", BigDecimalUtil.divide(getSafeCount(map.get(structCode)), structCount));
            map.put(marketCode + "Result", BigDecimalUtil.divide(getSafeCount(map.get(marketCode)), marketCount));
            map.put(benefitCode + "Result", BigDecimalUtil.divide(getSafeCount(map.get(benefitCode)), benefitCount));

        }
    }


    //生成参数list
    private List<Map<String, Object>> buildCalculateOneParam() {
        //获取历史数据使用上个月月份 历史数据导入上个月月份的数据
        //list保存这个月月份
        //获取计划金额使用这个月月份
        ArrayList<Map<String, Object>> paramList = new ArrayList<>();

        //获取上个月月份
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m1 = c.getTime();
        String lastMonth = format.format(m1);
        //获取本月月份
        String month = format.format(new Date());

        //获取历史数据
        HashMap<String, TbCalculateOneImportData> dataMap = new HashMap<>();
        TbCalculateOneImportData tbCalculateOneImportDataQuery = new TbCalculateOneImportData();
        tbCalculateOneImportDataQuery.setMonth(lastMonth);
        tbCalculateOneImportDataQuery.setType(CalculateOneParam.CALCULATE_MONTH);
        List<TbCalculateOneImportData> tbCalculateOneImportDataList = tbCalculateOneImportDataMapper.selectByAttr(tbCalculateOneImportDataQuery);
        for (TbCalculateOneImportData tbCalculateOneImportData : tbCalculateOneImportDataList) {
            dataMap.put(tbCalculateOneImportData.getOrgancode(), tbCalculateOneImportData);
        }

        //获取总金额
        TbTradeParam tbTradeParamQuery = new TbTradeParam();
        tbTradeParamQuery.setParamPeriod(month);
        List<TbTradeParam> tbTradeParamList = tbTradeParamMapper.selectByAttr(tbTradeParamQuery);
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (tbTradeParamList != null && tbTradeParamList.size() != 0) {
            totalAmount = tbTradeParamList.get(0).getParamMechIncrement();
        }


        //获取分行机构
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setOrganlevel("1");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        //填充参数
        for (FdOrgan organ : fdOrganList) {

            String organCode = organ.getThiscode();
            TbCalculateOneImportData importData = dataMap.get(organCode);

            if (importData == null) {
                importData = new TbCalculateOneImportData();
            }

            HashMap<String, Object> result = new HashMap<>();
            result.put("organcode", organ.getThiscode());
            result.put("month", month);
            result.put("type", CalculateOneParam.CALCULATE_MONTH);

            result.put("code1", getSafeCount(importData.getCode1()));
            result.put("code2", getSafeCount(importData.getCode2()));
            result.put("code3", getSafeCount(importData.getCode3()));
            result.put("code4", getSafeCount(importData.getCode4()));
            result.put("code5", getSafeCount(importData.getCode5()));
            result.put("code6", getSafeCount(importData.getCode6()));
            result.put("code7", getSafeCount(importData.getCode7()));
            result.put("code8", getSafeCount(importData.getCode8()));
            result.put("code9", getSafeCount(importData.getCode9()));
            result.put("code10", getSafeCount(importData.getCode10()));
            result.put("code11", getSafeCount(importData.getCode11()));
            result.put("code12", getSafeCount(importData.getCode12()));
            result.put("code13", getSafeCount(importData.getCode13()));
            result.put("code14", getSafeCount(importData.getCode14()));
            result.put("code15", getSafeCount(importData.getCode15()));
            result.put("code16", getSafeCount(importData.getCode16()));
            result.put("code17", getSafeCount(importData.getCode17()));
            result.put("code18", getSafeCount(importData.getCode18()));
            result.put("code19", getSafeCount(importData.getCode19()));
            result.put("code20", getSafeCount(importData.getCode20()));
            result.put("code21", getSafeCount(importData.getCode21()));
            result.put("code22", getSafeCount(importData.getCode22()));
            result.put("code23", getSafeCount(importData.getCode23()));
            result.put("code24", getSafeCount(importData.getCode24()));
            result.put("code25", getSafeCount(importData.getCode25()));
            result.put("code26", getSafeCount(importData.getCode26()));
            result.put("code27", getSafeCount(importData.getCode27()));
            result.put("code28", getSafeCount(importData.getCode28()));
            result.put("code29", getSafeCount(importData.getCode29()));
            result.put("code30", getSafeCount(importData.getCode30()));
            result.put("code31", getSafeCount(importData.getCode31()));
            result.put("code32", getSafeCount(importData.getCode32()));
            result.put("code33", getSafeCount(importData.getCode33()));
            result.put("code34", getSafeCount(importData.getCode34()));
            result.put("code35", getSafeCount(importData.getCode35()));

            //总金额
            result.put("totalAmount", getSafeCount(totalAmount));

            paramList.add(result);
        }

        return paramList;
    }


    private BigDecimal getSafeCount(BigDecimal b1) {
        if (b1 == null) {
            return BigDecimal.ZERO;
        }
        return b1;
    }

    private BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }



}