package com.boco.PUB.service.tbCalculateTwo.impl;


import com.boco.PUB.POJO.DO.CalculateOneParam;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.tbCalculateTwo.ITbCalculateTwoResultService;
import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ����� �����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbCalculateTwoResultService extends GenericService<TbCalculateTwoResult, String> implements ITbCalculateTwoResultService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbCalculateTwoResultMapper tbCalculateTwoResultMapper;
    @Autowired
    private TbCalculateTwoImportDataMapper tbCalculateTwoImportDataMapper;
    @Autowired
    private TbCalculateTwoProportionHistoryMapper tbCalculateTwoProportionHistoryMapper;
    @Autowired
    private TbTradeParamMapper tbTradeParamMapper;
    @Autowired
    private TbPunishDetailMapper tbPunishDetailMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private TbCalculateOneRankMapper tbCalculateOneRankMapper;
    @Autowired
    private TbCalculateTwoProportionMapper tbCalculateTwoProportionMapper;

    private static SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    /*���ɲ����� ģʽ��*/
    @Override
    public void addCalculateOneResult() {
        //����Ƿ��Ѿ����ڲ����� ������ھ�ɾ��
        //��ȡ�����·�
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String month = format.format(new Date());

        TbCalculateTwoResult resultQuery = new TbCalculateTwoResult();
        resultQuery.setMonth(month);
        List<TbCalculateTwoResult> resultList = tbCalculateTwoResultMapper.selectByAttr(resultQuery);
        if (resultList != null && resultList.size() != 0) {
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("month", month);
            tbCalculateTwoResultMapper.deleteByMonthAndType(queryMap);
        }

        //���ɲ������list<map>
        List<Map<String, Object>> calculateTwoParamList = buildCalculateTwoParam();

        //���ɲ�����
        List<TbCalculateTwoResult> tbCalculateOneResultList = buildCalCulateTwoResult(calculateTwoParamList);

        //todo
        //���Ʋ����� 1.ǰX����ƽ�����ƻ������ý��

        getAverageOverOrIdle(tbCalculateOneResultList);


        //�����������
        tbCalculateTwoResultMapper.insertBatch(tbCalculateOneResultList);

        //�������Ȩ�ز�����ʷ��
        saveCalculateParamHistory(month, CalculateOneParam.CALCULATE_MONTH);
    }


    /**
     * ���ݾ������� ���������͵õ�  �·��б�
     *
     * @param date  ��������  yyyymmdd
     * @param cycel ������ 1-�� 2-�� 3-�� 4-��
     * @return ["202007","202008"]
     */
    public static String[] getTimePeriods(String date, String cycel) {
        String timePeriodStr = "";
        Calendar cal = Calendar.getInstance();
        try {
            Date searchDate = sdf_yyyyMMdd.parse(date);
            cal.setTime(searchDate);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            int startMonth = 1;
            if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                startMonth = 1;
            } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                if (month >= 1 && month <= 3) {
                    startMonth = 1;
                } else if (month >= 4 && month <= 6) {
                    startMonth = 4;
                } else if (month >= 7 && month <= 9) {
                    startMonth = 7;
                } else if (month >= 10 && month <= 12) {
                    startMonth = 10;
                }
            } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                startMonth = month;
            } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                startMonth = month;
            }
            while (startMonth <= month) {
                String startMonthStr;
                if (startMonth < 10) {
                    startMonthStr = "0" + startMonth;
                } else {
                    startMonthStr = String.valueOf(startMonth);
                }
                timePeriodStr = timePeriodStr + year + startMonthStr + ",";
                startMonth++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timePeriodStr.split(",");

    }

    /**
     * ��ֵ ���û򳬼ƻ����
     *
     * @param tbCalculateOneResultList
     */
    private void getAverageOverOrIdle(List<TbCalculateTwoResult> tbCalculateOneResultList) {
        //��ȡ�ϸ����·�
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m1 = c.getTime();
        String month = format.format(m1);

        String[] timePeriods = getTimePeriods(month, "1");
        //������� �ϸ���
        Map<String, BigDecimal> totalMap = new HashMap<>();
        for (String tempMonth : timePeriods) {
            TbPunishDetail searchTb = new TbPunishDetail();
            searchTb.setLeftDay(BigDecimal.ZERO);
            searchTb.setComb("totalLeft");
            searchTb.setMonth(tempMonth);
            List<TbPunishDetail> tbPunishDetails = tbPunishDetailMapper.selectByAttr(searchTb);
            for (TbPunishDetail tempTbPunishDetail : tbPunishDetails) {
                totalMap.put(tempTbPunishDetail.getOrgan(), BigDecimalUtil.add(getSafeCount(totalMap.get(tempTbPunishDetail.getOrgan())), tempTbPunishDetail.getDeparture()));
            }
        }
        BigDecimal monthsNum = getSafeCount(timePeriods.length);
        for (TbCalculateTwoResult tempTb : tbCalculateOneResultList) {
            tempTb.setCode20(BigDecimalUtil.divide(totalMap.get(tempTb.getOrgancode()), monthsNum));
        }
    }

    // �������Ȩ�ز�����ʷ��
    private void saveCalculateParamHistory(String month, BigDecimal type) {
        // ��ѯ�Ƿ������ʷ��������ھ�ɾ��
        TbCalculateTwoProportionHistory Query = new TbCalculateTwoProportionHistory();
        Query.setMonth(month);
        List<TbCalculateTwoProportionHistory> tbCalculateOneProportionHistorieList = tbCalculateTwoProportionHistoryMapper.selectByAttr(Query);
        if (tbCalculateOneProportionHistorieList != null && tbCalculateOneProportionHistorieList.size() != 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("month", month);
            tbCalculateTwoProportionHistoryMapper.deleteByMonthAndType(map);
        }

        List<TbCalculateTwoProportion> tbCalculateTwoProportionList = tbCalculateTwoProportionMapper.selectByAttr(new TbCalculateTwoProportion());
        //������ʷ����
        List<TbCalculateTwoProportionHistory> historyList = buildProportionHistoryList(tbCalculateTwoProportionList, month, type);

        tbCalculateTwoProportionHistoryMapper.insertBatch(historyList);
    }


    //������ʷ����
    private List<TbCalculateTwoProportionHistory> buildProportionHistoryList(List<TbCalculateTwoProportion> tbCalculateTwoProportionList, String month, BigDecimal type) {
        ArrayList<TbCalculateTwoProportionHistory> list = new ArrayList<>();
        for (TbCalculateTwoProportion proportion : tbCalculateTwoProportionList) {
            TbCalculateTwoProportionHistory his = new TbCalculateTwoProportionHistory();
            his.setId(IDGeneratorUtils.getSequence());
            his.setCode(proportion.getCode());
            his.setName(proportion.getName());
            his.setSortType(proportion.getSortType());
            his.setWeight(proportion.getWeight());
            his.setMonth(month);
            list.add(his);
        }
        return list;
    }

    //���ݲ������ɲ�����
    private List<TbCalculateTwoResult> buildCalCulateTwoResult(List<Map<String, Object>> calculateTwoParamList) {

        // ��ѯȨ��ϵ��������ָ�꣬������ռ��ϵ��
        List<TbCalculateTwoProportion> tbCalculateTwoProportionList = tbCalculateTwoProportionMapper.selectByAttr(new TbCalculateTwoProportion());

        //������ָ��
        buildMajorIndex(calculateTwoParamList);

        //����ָ��
        buildSecondIndex(calculateTwoParamList, tbCalculateTwoProportionList);

        buildWeight(calculateTwoParamList, tbCalculateTwoProportionList);

        //��������������
        ArrayList<TbCalculateTwoResult> tbCalculateOneResultList = buildCalculateTwoResultList(calculateTwoParamList);


        return tbCalculateOneResultList;
    }

    //����ÿ�������Ȩ��
    private void buildWeight(List<Map<String, Object>> calculateTwoParamList, List<TbCalculateTwoProportion> tbCalculateTwoProportionList) {

        //����ָ����ʳ˻��ĺ�
        BigDecimal depositCount = BigDecimal.ZERO;
        TbCalculateTwoProportion tempP = new TbCalculateTwoProportion();
        tempP.setCode("code1");
        tbCalculateTwoProportionList.add(tempP);

        for (Map<String, Object> map : calculateTwoParamList) {
            //���������
            BigDecimal deposit = BigDecimal.ONE;
            for (TbCalculateTwoProportion proportion : tbCalculateTwoProportionList) {
                deposit = deposit.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            depositCount = depositCount.add(deposit);
        }

        //��ʼ����Ȩ��
        for (Map<String, Object> map : calculateTwoParamList) {
            //���������
            BigDecimal deposit = BigDecimal.ONE;
            for (TbCalculateTwoProportion proportion : tbCalculateTwoProportionList) {
                deposit = deposit.multiply(getSafeCount(map.get(proportion.getCode() + "Result")));
            }
            map.put("weight", BigDecimalUtil.divide(deposit, depositCount));
        }
    }


    //������ָ��
    private void buildMajorIndex(List<Map<String, Object>> calculateTwoParamList) {

        BigDecimal count = BigDecimal.ZERO;

        //��������������ָ���ܺ�
        for (Map<String, Object> map : calculateTwoParamList) {
            count = BigDecimalUtil.add(count, getSafeCount(map.get("code4")));
        }

        for (Map<String, Object> map : calculateTwoParamList) {
            map.put("code1Result", BigDecimalUtil.divide(getSafeCount(map.get("code4")), count));
        }
    }


    // ��������������
    private ArrayList<TbCalculateTwoResult> buildCalculateTwoResultList(List<Map<String, Object>> calculateTwoParamList) {
        ArrayList<TbCalculateTwoResult> resultList = new ArrayList<>();


        for (Map<String, Object> map : calculateTwoParamList) {
            TbCalculateTwoResult result = new TbCalculateTwoResult();

            result.setId(IDGeneratorUtils.getSequence());
            result.setOrgancode(map.get("organcode").toString());
            result.setMonth(map.get("month").toString());

            //todo
            //������ һ���� ��ȼƻ�����ȼƻ���������ȼƻ����
            result.setCode1(getSafeCount(map.get("code1")));
            result.setCode2(new BigDecimal(0.7));
            result.setCode3(BigDecimalUtil.multiply(result.getCode1(),result.getCode2()));

            //��5�� �ǵ��������
            result.setCode4(getSafeCount(map.get("code4")));
            result.setCode5(getSafeCount(map.get("code5")));
            result.setCode6(getSafeCount(map.get("code6")));
            result.setCode7(getSafeCount(map.get("code7")));
            result.setCode8(getSafeCount(map.get("code8")));


            //����5��ָ����
            result.setCode9(getSafeCount(map.get("code1Result")));
            result.setCode10(getSafeCount(map.get("code5Result")));
            result.setCode11(getSafeCount(map.get("code6Result")));
            result.setCode12(getSafeCount(map.get("code7Result")));
            result.setCode13(getSafeCount(map.get("code8Result")));

            //todo
            result.setCode14(getSafeCount(map.get("weight")));
            result.setCode15(BigDecimalUtil.multiply(getSafeCount(map.get("totalAmount")), getSafeCount(map.get("weight"))));
            result.setCode16(getSafeCount(map.get("code16"))); //rank
            result.setCode17(BigDecimalUtil.subtract(result.getCode14(), result.getCode9()));
            result.setCode18(getSafeCount(map.get("code18")));//rank
            result.setCode19(map.get("organname").toString());
            result.setCode20(getSafeCount(map.get("code20")));//���ý��
//            result.setCode21(result.getCode15());//����ǰ�ƻ�
            result.setCode21(BigDecimalUtil.add(BigDecimalUtil.multiply(result.getCode3(),new BigDecimal(0.7)),BigDecimalUtil.multiply(result.getCode15(),new BigDecimal(0.3))));//����ǰ�ƻ�
            result.setCode22(getSafeCount(map.get("code22")));////���ڼƻ�
            result.setCode23(getSafeCount(map.get("code23")));////���ں�ƻ�
            resultList.add(result);
        }

        rankList(resultList);

        return resultList;
    }


    void rankList(ArrayList<TbCalculateTwoResult> resultList) {
        //�����ڻ��������ֶ�
        Collections.sort(resultList, new Comparator<TbCalculateTwoResult>() {
            @Override
            public int compare(TbCalculateTwoResult o1, TbCalculateTwoResult o2) {
                //�Ӵ�С
                return o2.getCode15().compareTo(o1.getCode15());
            }
        });
        int i = 1;
        for (TbCalculateTwoResult tempPr : resultList) {
            tempPr.setCode16(getSafeCount(i));
            i++;
        }

        //�����ڻ��������ֶ�
        Collections.sort(resultList, new Comparator<TbCalculateTwoResult>() {
            @Override
            public int compare(TbCalculateTwoResult o1, TbCalculateTwoResult o2) {
                //�Ӵ�С
                return o2.getCode17().compareTo(o1.getCode17());
            }
        });
        i = 1;
        for (TbCalculateTwoResult tempPr : resultList) {
            tempPr.setCode18(getSafeCount(i));
            i++;
        }

    }

    //���㸱ָ��
    private void buildSecondIndex(List<Map<String, Object>> tbCalculateTwoResultList, List<TbCalculateTwoProportion> tbCalculateTwoProportionList) {

        //��ѯ����ϵ��
        TbCalculateOneRank tbCalculateOneRankQuery = new TbCalculateOneRank();
        tbCalculateOneRankQuery.setStatus(1);
        tbCalculateOneRankQuery.setType(16);
        //�����-��ʾ
        tbCalculateOneRankQuery.setType(CalculateOneParam.CSTWO.intValue());
        HashMap<Integer, BigDecimal> ceTwoRankMap = buildRankMap(tbCalculateOneRankQuery);

        for (TbCalculateTwoProportion proportion : tbCalculateTwoProportionList) {
            //��ȡָ��
            String code = proportion.getCode();
            //��ȡȨ��
            BigDecimal weight = proportion.getWeight();
            //��ȡ��������,��������ϵ��
            BigDecimal sortType = proportion.getSortType();
            int sort = 1;
            if (sortType.compareTo(new BigDecimal("1")) == 0) {
                sort = -1;
            }

            //���ݸ�ָ������
            int finalSort = sort;
            Collections.sort(tbCalculateTwoResultList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    if (getSafeCount(o1.get(code)).compareTo(getSafeCount(o2.get(code))) == -1) {
                        return 1 * finalSort;
                    } else if (getSafeCount(o1.get(code)).compareTo(getSafeCount(o2.get(code))) == 1) {
                        return -1 * finalSort;
                    } else {
                        return 0;
                    }
                }
            });

            for (int i = 0; i < tbCalculateTwoResultList.size(); i++) {
                Map<String, Object> map = tbCalculateTwoResultList.get(i);
                // ����ϵ��
                BigDecimal rank = getSafeCount(ceTwoRankMap.get(i + 1));
                //����ָ��
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

    //���ɲ���list
    private List<Map<String, Object>> buildCalculateTwoParam() {
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
        HashMap<String, TbCalculateTwoImportData> dataMap = new HashMap<>();
        TbCalculateTwoImportData tbCalculateOneImportDataQuery = new TbCalculateTwoImportData();
        tbCalculateOneImportDataQuery.setMonth(lastMonth);
        tbCalculateOneImportDataQuery.setType(Integer.parseInt(CalculateOneParam.CALCULATE_MONTH.toString()));
        List<TbCalculateTwoImportData> tbCalculateOneImportDataList = tbCalculateTwoImportDataMapper.selectByAttr(tbCalculateOneImportDataQuery);
        for (TbCalculateTwoImportData tbCalculateOneImportData : tbCalculateOneImportDataList) {
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
            TbCalculateTwoImportData importData = dataMap.get(organCode);

            if (importData == null) {
                importData = new TbCalculateTwoImportData();
            }

            HashMap<String, Object> result = new HashMap<>();
            result.put("organcode", organ.getThiscode());
            result.put("organname", organ.getOrganname());
            result.put("month", month);
            result.put("type", CalculateOneParam.CALCULATE_MONTH);

            result.put("code1", getSafeCount(importData.getCode6()));

            result.put("code4", getSafeCount(importData.getCode1()));
            result.put("code5", getSafeCount(importData.getCode2()));
            result.put("code6", getSafeCount(importData.getCode3()));
            result.put("code7", getSafeCount(importData.getCode4()));
            result.put("code8", getSafeCount(importData.getCode5()));

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

    @Override
    public List<TbCalculateTwoResult> selectMonth() {
        List<TbCalculateTwoResult> list = new ArrayList<>();
        try {
            List<Map<String, String>> stringStringMap = tbCalculateTwoResultMapper.selectMonth();
            List<TbTradeParam> tbTradeParams = tbTradeParamMapper.selectByAttr(new TbTradeParam());
            for (Map<String, String> value : stringStringMap) {
                TbCalculateTwoResult tempTb = new TbCalculateTwoResult();
                tempTb.setMonth(value.get("month"));
                for (TbTradeParam tb : tbTradeParams) {
                    if (value.get("month").equals(tb.getParamPeriod())) {
                        tempTb.setTotalNum(tb.getParamMechIncrement());
                        break;
                    }
                }
                list.add(tempTb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deleleAndInsert(HttpServletRequest request) throws Exception {
        List<TbCalculateTwoResult> list = getList(request);
        HashMap<String, Object> queryMap = new HashMap<>();
        String month = request.getParameter("month");
        queryMap.put("month", month);
        tbCalculateTwoResultMapper.deleteByMonthAndType(queryMap);
        tbCalculateTwoResultMapper.insertBatch(list);

    }

    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param request
     * @return
     */
    private List<TbCalculateTwoResult> getList(HttpServletRequest request) {
        String tbDetailStr = request.getParameter("tbDetail");
        String month = request.getParameter("month");
        TbCalculateTwoResult searchTb = new TbCalculateTwoResult();
        searchTb.setMonth(month);
        List<TbCalculateTwoResult> tempList = tbCalculateTwoResultMapper.selectByAttr(searchTb);

        String[] twoTetailArr = tbDetailStr.split("&");

        Map<String, BigDecimal> map = new HashMap<>();
        for (int i = 0; i < twoTetailArr.length; i++) {
            String[] planDetailArr1 = twoTetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //organ,code1
            String num = planDetailArr1[1]; //һ����ֵ
            String organCode = planDetailArr2[0]; //organ
            String code = planDetailArr2[1]; //code22
            if ("code22".equals(code)) {
                map.put(organCode, getSafeCount(num));

            }
        }
        for (TbCalculateTwoResult tb : tempList) {
            BigDecimal resetNum = getSafeCount(map.get(tb.getOrgancode()));
            tb.setCode22(resetNum);
            tb.setCode23(BigDecimalUtil.add(tb.getCode21(), resetNum));
        }
        return tempList;
    }

}