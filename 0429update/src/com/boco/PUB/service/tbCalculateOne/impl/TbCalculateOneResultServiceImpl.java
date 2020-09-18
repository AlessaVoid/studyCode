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
 * ���� �����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
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


    /*���ɲ����� ģʽһ*/
    @Override
    public void addCalculateOneResult() throws Exception {
        //����Ƿ��Ѿ����ڲ����� ������ھ�ɾ��
        //��ȡ�����·�
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

        //���ɲ������list<map>
        List<Map<String, Object>> calculateOneParamList = buildCalculateOneParam();

        //���ɲ�����
        List<TbCalculateOneResult> tbCalculateOneResultList = buildCalCulateOneResult(calculateOneParamList);

        //�����������
        tbCalculateOneResultMapper.insertBatch(tbCalculateOneResultList);

        //�������Ȩ�ز�����ʷ��
        saveCalculateParamHistory(month,CalculateOneParam.CALCULATE_MONTH);

    }
    // �������Ȩ�ز�����ʷ��
    private void saveCalculateParamHistory(String month, BigDecimal type) {
        // ��ѯ�Ƿ������ʷ��������ھ�ɾ��
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
        //������ʷ����
        List<TbCalculateOneProportionHistory> historyList = buildProportionHistoryList(tbCalculateOneProportionList,month,type);

        tbCalculateOneProportionHistoryMapper.insertBatch(historyList);
    }

    //������ʷ����
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


    //���ݲ������ɲ�����
    private List<TbCalculateOneResult> buildCalCulateOneResult(List<Map<String, Object>> calculateOneParamList) {

        // ��ѯȨ��ϵ��������ָ�꣬������ռ��ϵ��
        List<TbCalculateOneProportion> tbCalculateOneProportionList = tbCalculateOneProportionMapper.selectByAttr(new TbCalculateOneProportion());
        //��ָ��
        HashMap<String, TbCalculateOneProportion> majorIndexMap = new HashMap<>();
        //��ָ��
        ArrayList<TbCalculateOneProportion> secondIndexList = new ArrayList<>();
        //4����ÿ�����õ���ָ��
        ArrayList<TbCalculateOneProportion> depositList = new ArrayList<>();
        ArrayList<TbCalculateOneProportion> structList = new ArrayList<>();
        ArrayList<TbCalculateOneProportion> marketList = new ArrayList<>();
        ArrayList<TbCalculateOneProportion> benefitList = new ArrayList<>();
        //4�����ռ��
        BigDecimal depositRatio = BigDecimal.ZERO;
        BigDecimal structRatio = BigDecimal.ZERO;
        BigDecimal marketRatio = BigDecimal.ZERO;
        BigDecimal benefitRatio = BigDecimal.ZERO;

        for (TbCalculateOneProportion proportion : tbCalculateOneProportionList) {
            //�ж�����ָ��
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

            // 4����ÿ�����õ���ָ��
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

        //������ָ��
        buildMajorIndex(calculateOneParamList, majorIndexMap);

        //���㸱ָ��
        buildSecondIndex(calculateOneParamList,secondIndexList);

        //����ÿ�������Ȩ��
        buildWeight(calculateOneParamList, depositList, structList, marketList, benefitList);

        //����ƻ��������
        buildPlanRatio(calculateOneParamList, depositRatio, structRatio, marketRatio, benefitRatio);

        //��������������
        ArrayList<TbCalculateOneResult> tbCalculateOneResultList = buildCalculateOneResultList(calculateOneParamList);


        return tbCalculateOneResultList;
    }

    // ��������������
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

    //����ƻ��������
    private void buildPlanRatio(List<Map<String, Object>> calculateOneParamList, BigDecimal depositRatio, BigDecimal structRatio, BigDecimal marketRatio, BigDecimal benefitRatio) {

        for (Map<String, Object> map : calculateOneParamList) {
            BigDecimal deposit = getSafeCount(map.get("depositWeight")).multiply(depositRatio);
            BigDecimal struct = getSafeCount(map.get("structWeight")).multiply(structRatio);
            BigDecimal market = getSafeCount(map.get("marketWeight")).multiply(marketRatio);
            BigDecimal benefit = getSafeCount(map.get("benefitWeight")).multiply(benefitRatio);
            //�������
            BigDecimal planWeight = deposit.add(struct)
                    .add(market)
                    .add(benefit);
            map.put("planWeight", planWeight);
            map.put("planAmount", getSafeCount(map.get("totalAmount")).multiply(planWeight));

        }
    }

    //����ÿ�������Ȩ��
    private void buildWeight(List<Map<String, Object>> calculateOneParamList, ArrayList<TbCalculateOneProportion> depositList, ArrayList<TbCalculateOneProportion> structList, ArrayList<TbCalculateOneProportion> marketList, ArrayList<TbCalculateOneProportion> benefitList) {

        //���������Ч�� ����ָ����ʳ˻��ĺ�
        BigDecimal depositCount = BigDecimal.ZERO;
        BigDecimal structCount = BigDecimal.ZERO;
        BigDecimal marketCount = BigDecimal.ZERO;
        BigDecimal benefitCount = BigDecimal.ZERO;

        for (Map<String, Object> map : calculateOneParamList) {
            //���������
            BigDecimal deposit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : depositList) {
                deposit = deposit.multiply(getSafeCount(map.get(proportion.getCode()+"Result")));
            }
            depositCount = depositCount.add(deposit);
            //�ṹ�Ż���
            BigDecimal struct = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : structList) {
                struct = struct.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            structCount = structCount.add(struct);
            //�г�������
            BigDecimal market = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : marketList) {
                market = market.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            marketCount = marketCount.add(market);
            //��ֵ������
            BigDecimal benefit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : benefitList) {
                benefit = benefit.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            benefitCount = benefitCount.add(benefit);
        }

        //��ʼ����Ȩ��
        for (Map<String, Object> map : calculateOneParamList) {
            //���������
            BigDecimal deposit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : depositList) {
                deposit = deposit.multiply(getSafeCount(map.get(proportion.getCode()+"Result")));
            }
            map.put("depositWeight", BigDecimalUtil.divide(deposit, depositCount));
            //�ṹ�Ż���
            BigDecimal struct = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : structList) {
                struct = struct.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("structWeight", BigDecimalUtil.divide(struct, structCount));
            //�г�������
            BigDecimal market = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : marketList) {
                market = market.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("marketWeight", BigDecimalUtil.divide(market, marketCount));
            //��ֵ������
            BigDecimal benefit = BigDecimal.ONE;
            for (TbCalculateOneProportion proportion : benefitList) {
                benefit = benefit.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("benefitWeight", BigDecimalUtil.divide(benefit, benefitCount));
        }
    }

    //���㸱ָ��
    private void buildSecondIndex(List<Map<String, Object>> tbCalculateOneResultList, ArrayList<TbCalculateOneProportion> secondIndexList) {

        //��ѯ����ϵ��
        TbCalculateOneRank tbCalculateOneRankQuery = new TbCalculateOneRank();
        tbCalculateOneRankQuery.setStatus(1);
        //���������
        tbCalculateOneRankQuery.setType(CalculateOneParam.DEPOSIT.intValue());
        HashMap<Integer, BigDecimal> depositRankMap = buildRankMap(tbCalculateOneRankQuery);
        //�ṹ�Ż���
        tbCalculateOneRankQuery.setType(CalculateOneParam.STRUCT.intValue());
        HashMap<Integer, BigDecimal> structRankMap = buildRankMap(tbCalculateOneRankQuery);
        //�г�������
        tbCalculateOneRankQuery.setType(CalculateOneParam.STRUCT.intValue());
        HashMap<Integer, BigDecimal> marketRankMap = buildRankMap(tbCalculateOneRankQuery);
        //��ֵ������
        tbCalculateOneRankQuery.setType(CalculateOneParam.STRUCT.intValue());
        HashMap<Integer, BigDecimal> benefitRankMap = buildRankMap(tbCalculateOneRankQuery);

//-------------------------���㸱ָ�������������������������
        for (TbCalculateOneProportion proportion : secondIndexList) {
            //��ȡ��ָ��
            String code = proportion.getCode();
            //��ȡȨ��
            BigDecimal weight = proportion.getWeight();
            //��ȡ��������,��������ϵ��
            BigDecimal sortType = proportion.getSortType();
            int  sort = 1;
            if (sortType.compareTo(new BigDecimal("1")) == 0) {
                sort = -1;
            }
            // ��ȡ4��������
            BigDecimal classType = proportion.getClassType();
            //��ȡ��Ӧ������ϵ��
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

            //���ݸ�ָ������
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
                // ����ϵ��
                BigDecimal rank = getSafeCount(rankMap.get(i + 1));
                //���㸱ָ��
                BigDecimal secondIndex = getSecondIndex(rank, weight);

                map.put(code + "Result", secondIndex);
            }
        }
    }


    //��ָ����㹫ʽ
    private BigDecimal getSecondIndex(BigDecimal rank, BigDecimal depositPlanRate) {
        //����ϵ��*��ָ��Ȩ��+1
        BigDecimal secondIndex = rank.multiply(depositPlanRate).add(BigDecimal.ONE);

        return secondIndex;
    }

    //��ѯ����ϵ��map
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

    //������ָ��
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
        //��������������ָ���ܺ�
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


    //���ɲ���list
    private List<Map<String, Object>> buildCalculateOneParam() {
        //��ȡ��ʷ����ʹ���ϸ����·� ��ʷ���ݵ����ϸ����·ݵ�����
        //list����������·�
        //��ȡ�ƻ����ʹ��������·�
        ArrayList<Map<String, Object>> paramList = new ArrayList<>();

        //��ȡ�ϸ����·�
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m1 = c.getTime();
        String lastMonth = format.format(m1);
        //��ȡ�����·�
        String month = format.format(new Date());

        //��ȡ��ʷ����
        HashMap<String, TbCalculateOneImportData> dataMap = new HashMap<>();
        TbCalculateOneImportData tbCalculateOneImportDataQuery = new TbCalculateOneImportData();
        tbCalculateOneImportDataQuery.setMonth(lastMonth);
        tbCalculateOneImportDataQuery.setType(CalculateOneParam.CALCULATE_MONTH);
        List<TbCalculateOneImportData> tbCalculateOneImportDataList = tbCalculateOneImportDataMapper.selectByAttr(tbCalculateOneImportDataQuery);
        for (TbCalculateOneImportData tbCalculateOneImportData : tbCalculateOneImportDataList) {
            dataMap.put(tbCalculateOneImportData.getOrgancode(), tbCalculateOneImportData);
        }

        //��ȡ�ܽ��
        TbTradeParam tbTradeParamQuery = new TbTradeParam();
        tbTradeParamQuery.setParamPeriod(month);
        List<TbTradeParam> tbTradeParamList = tbTradeParamMapper.selectByAttr(tbTradeParamQuery);
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (tbTradeParamList != null && tbTradeParamList.size() != 0) {
            totalAmount = tbTradeParamList.get(0).getParamMechIncrement();
        }


        //��ȡ���л���
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setOrganlevel("1");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrganQuery);
        //������
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

            //�ܽ��
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