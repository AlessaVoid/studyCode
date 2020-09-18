package com.boco.AL.service.impl;

import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.PunishInterestMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.mapper.TbPunishParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//��Ϣ��ʽ
@Service
public class PunishInterestService {
    private static Object punish;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private PunishInterestMapper punishInterestMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    TbPunishParamMapper tbPunishParamMapper;

    public void punish(TbTradeParam tbTradeParam) throws Exception {
        int year;
        int month;
        int day;
        String rptDate;
        int punishDay;//��Ϣ����
        int start;
        int end;
        BigDecimal pjfft1plan = BigDecimal.ZERO;
        BigDecimal shitiplan = BigDecimal.ZERO;
        BigDecimal lxdk1plan = BigDecimal.ZERO;
        BigDecimal qtstdk1plan = BigDecimal.ZERO;
        BigDecimal totalplan = BigDecimal.ZERO;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String str2 = simpleDateFormat.format(date);

        //�޸������ڣ��ǵøĻ���
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2019-07-01");
        long time = myDate1.getTime();
        Date date2 = new Date();
        date2.setTime(time);
        String str = simpleDateFormat.format(date2);

        if (str.endsWith("01")) {
            year = Integer.parseInt(str.substring(0, 4)) - 1;
            month = 12;
            str = year + "" + month;
        } else {
            str = Integer.parseInt(str) - 1 + "";
        }
        tbTradeParam.setParamPeriod(str);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(tbTradeParam);
        for (TbTradeParam tbTradeparam005 : list) {
            year = Integer.parseInt(tbTradeparam005.getParamPeriod().substring(0, 4));
            month = Integer.parseInt(tbTradeparam005.getParamPeriod().substring(4, 6));
            start = Integer.parseInt(tbTradeparam005.getParamPunishStart().substring(tbTradeparam005.getParamPunishStart().length() - 2, tbTradeparam005.getParamPunishStart().length()));
            end = Integer.parseInt(tbTradeparam005.getParamPunsihEnd().substring(tbTradeparam005.getParamPunsihEnd().length() - 2, tbTradeparam005.getParamPunsihEnd().length()));
            punishDay = end - start + 1;//����-��ʼ+1 31-26+1=6

            List<Map<String, Integer>> oragnMap = punishInterestMapper.selectOrgan1();//��ѯ���л���

            for (int j = 0; j < oragnMap.size(); j++) {
                //�õ�һ��organcode
                String organ = oragnMap.get(j).get("provincecode") + "";
//                String organ = "11000013";
                if (organ.equals("11005293")) {
                    continue;
                }
                pjfft1plan = BigDecimal.ZERO;
                shitiplan = BigDecimal.ZERO;
                lxdk1plan = BigDecimal.ZERO;
                qtstdk1plan = BigDecimal.ZERO;
                //��ȡ�û������·ݵ�Ʊ����ʵ��ļƻ�����
                TbPlanDetail tbPlanDetail = new TbPlanDetail();
                tbPlanDetail.setPdOrgan(organ);
//                ���Ϊ��Ԫ
                tbPlanDetail.setPdMonth(str);
                List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);//���4����qtdk1��lxdk1��qtstdk1��pjfft1
                for (TbPlanDetail tbPlanDetail1 : tbPlanDetails) {
                    if (tbPlanDetail1.getPdLoanType().equals("qjfft1")) {
                        pjfft1plan = new BigDecimal(String.valueOf(tbPlanDetail1.getPdAmount()));
                    } else if (tbPlanDetail1.getPdLoanType().equals("lxdk1")) {
                        lxdk1plan = new BigDecimal(String.valueOf(tbPlanDetail1.getPdAmount()));
                        shitiplan = shitiplan.add(new BigDecimal(String.valueOf(tbPlanDetail1.getPdAmount())));
                    } else if (tbPlanDetail1.getPdLoanType().equals("qtstdk1")) {
                        qtstdk1plan = new BigDecimal(String.valueOf(tbPlanDetail1.getPdAmount()));
                        shitiplan = shitiplan.add(new BigDecimal(String.valueOf(tbPlanDetail1.getPdAmount())));
                    }
                    totalplan = pjfft1plan.add(shitiplan);
                }
                for (int i = 0; i < punishDay; i++) {
                    BigDecimal qjfft1monthOcc = BigDecimal.ZERO;
                    BigDecimal qjfft1monthLimit = BigDecimal.ZERO;
                    BigDecimal shitimonthOcc = BigDecimal.ZERO;
                    BigDecimal shitimonthLimit = BigDecimal.ZERO;
                    BigDecimal lxdk1monthOcc = BigDecimal.ZERO;
                    BigDecimal lxdk1monthLimit = BigDecimal.ZERO;
                    BigDecimal qtstdk1monthOcc = BigDecimal.ZERO;
                    BigDecimal qtstdk1monthLimit = BigDecimal.ZERO;
                    BigDecimal monthOcc = BigDecimal.ZERO;//������
                    BigDecimal monthLimit = BigDecimal.ZERO;//������
                    BigDecimal everyDaysTotallPlan = totalplan;
                    BigDecimal everyDaysShitiPlan = shitiplan;
//                    float diff = monthOcc - monthLimit;
//                    float plan = 0;//�ƻ�
                    int leftDay = end - start - i;
                    day = start + i;//�����ĳ�� rpt_date
                    if ((month + "").length() < 2) {
                        rptDate = year + "0" + month + "" + day;
                    } else {
                        rptDate = year + "" + month + "" + day;

                    }
                    List<RptBaseinfo> rptBaseinfoList = punishInterestMapper.queryDetail(rptDate, organ);//��ȡĳ�죬ĳһ������ȫ��������Ϣ 26��,�����Ԫ
                    for (RptBaseinfo rptBaseinfo : rptBaseinfoList) {
                        String loanKind = rptBaseinfo.getLoanKind();
                        String comb1 = punishInterestMapper.getcomb1(loanKind);//��ȡ��������������һ������
                        RptBaseinfo rptBaseinfo1 = new RptBaseinfo();
                        rptBaseinfo1.setRptDate(rptDate);
                        rptBaseinfo1.setOrgan(organ);
                        rptBaseinfo1.setLoanKind(loanKind);
                        monthOcc = new BigDecimal(String.valueOf(punishInterestMapper.getMonthOcc(rptBaseinfo1)));
                        monthLimit = new BigDecimal(String.valueOf(punishInterestMapper.getMonthLimit(rptBaseinfo1)));
                        if (comb1 != null) {
                            if (comb1.equals("qjfft1")) {
                                qjfft1monthOcc = qjfft1monthOcc.add(monthOcc);
                                qjfft1monthLimit = qjfft1monthLimit.add(monthLimit);
                            } else if (comb1.equals("lxdk1")) {
                                shitimonthOcc = shitimonthOcc.add(monthOcc);
                                shitimonthLimit = shitimonthLimit.add(monthLimit);
                                lxdk1monthOcc = lxdk1monthOcc.add(monthOcc);
                                lxdk1monthLimit = lxdk1monthLimit.add(monthLimit);
                            } else if (comb1.equals("qtstdk1")) {
                                shitimonthOcc = shitimonthOcc.add(monthOcc);
                                shitimonthLimit = shitimonthLimit.add(monthLimit);
                                qtstdk1monthOcc = qtstdk1monthOcc.add(monthOcc);
                                qtstdk1monthLimit = qtstdk1monthLimit.add(monthLimit);
                            }
                        } else {
                            continue;
                        }
                    }
                    BigDecimal lxdk1idff = lxdk1monthOcc.subtract(lxdk1monthLimit);
                    BigDecimal lxdk1departure = lxdk1idff.subtract(lxdk1plan);//ƫ������diff-plan
                    if (lxdk1departure.compareTo(BigDecimal.ZERO) == -1) {
                        everyDaysShitiPlan = shitiplan.add(lxdk1departure);
                        everyDaysTotallPlan = totalplan.add(lxdk1departure);
                    }
                    BigDecimal qtstdk1idff = qtstdk1monthOcc.subtract(qtstdk1monthLimit);
                    BigDecimal qtstdk1departure = qtstdk1idff.subtract(qtstdk1plan);
                    BigDecimal pjfftdiff = qjfft1monthOcc.subtract(qjfft1monthLimit);
                    BigDecimal shitidiff = shitimonthOcc.subtract(shitimonthLimit);
                    BigDecimal pjfftdeparture = pjfftdiff.subtract(pjfft1plan);
                    BigDecimal shitideparture = (lxdk1departure.compareTo(BigDecimal.ZERO) == 1 ? lxdk1departure : BigDecimal.ZERO).add(qtstdk1departure).add(pjfftdeparture.compareTo(BigDecimal.ZERO) == -1 ? pjfftdeparture : BigDecimal.ZERO);

                    BigDecimal pjfftratio = BigDecimal.ZERO;
                    BigDecimal shitiratio = BigDecimal.ZERO;
                    if (pjfft1plan.compareTo(BigDecimal.ZERO) == 0) {
                        pjfft1plan = new BigDecimal("0.0001");
                    }
                    pjfftratio = pjfftdeparture.divide(pjfft1plan, 4, BigDecimal.ROUND_HALF_UP);
                    if (pjfftratio.compareTo(BigDecimal.ZERO) == -1) {
                        pjfftratio.negate();
                    }

                    if (everyDaysShitiPlan.compareTo(BigDecimal.ZERO) == 0) {
                        everyDaysShitiPlan = new BigDecimal("0.0001");
                    }
                    shitiratio = shitideparture.divide(everyDaysShitiPlan, 4, BigDecimal.ROUND_HALF_UP);
                    if (shitiratio.compareTo(BigDecimal.ZERO) == -1) {
                        shitiratio.negate();
                    }

                    BigDecimal totaldeparture = BigDecimal.ZERO;//�ܵĳ��ƻ�/����

                    if (lxdk1departure.compareTo(new BigDecimal("0")) == 1) {
                        totaldeparture = lxdk1departure.add(qtstdk1departure).add(pjfftdeparture.compareTo(BigDecimal.ZERO) == -1 ? pjfftdeparture : BigDecimal.ZERO);
                    } else {//��С���ã�����ʵ���Ʊ�������ã���Ϊ��
                        totaldeparture = lxdk1departure.add(qtstdk1departure).add(pjfftdeparture.compareTo(BigDecimal.ZERO) == -1 ? pjfftdeparture : BigDecimal.ZERO);
                    }
                    BigDecimal totaldiff = everyDaysTotallPlan.add(totaldeparture);
                    BigDecimal leftratio = BigDecimal.ZERO;
                    if (everyDaysTotallPlan.compareTo(BigDecimal.ZERO) == 0) {
                        everyDaysTotallPlan = new BigDecimal("0.0001");
                    }
                    leftratio = totaldeparture.divide(everyDaysTotallPlan, 4, BigDecimal.ROUND_HALF_UP);
                    if (leftratio.compareTo(BigDecimal.ZERO) == 1) {
                        leftratio.negate();
                    }


                    BigDecimal pjfftpunishMoney = BigDecimal.ZERO;
                    if (pjfftdeparture.compareTo(BigDecimal.ZERO) == 1) {
                        pjfftpunishMoney = punishCalculate(year, month, day, punishDay, pjfft1plan.abs(), pjfftdeparture, pjfftratio.abs()).abs();
                    }
                    BigDecimal shitipunishMoney = BigDecimal.ZERO;
                    if (shitideparture.compareTo(BigDecimal.ZERO) == 1) {
                        shitipunishMoney = punishCalculate(year, month, day, punishDay, everyDaysShitiPlan.abs(), shitideparture, shitiratio.abs()).abs();
                    }
                    BigDecimal leftpunishMoney = BigDecimal.ZERO;
                    if (totaldeparture.compareTo(BigDecimal.ZERO) == -1) {
                        leftpunishMoney = punishCalculate(year, month, day, punishDay, everyDaysTotallPlan.abs(), totaldeparture.abs(), leftratio.abs().negate()).abs();
                    }

                    PunishDetail pjfftpunishDetail = new PunishDetail();
                    pjfftpunishDetail.setDay(day);
                    pjfftpunishDetail.setMonth(Integer.parseInt(str));
                    pjfftpunishDetail.setOrgan(organ);
                    pjfftpunishDetail.setType(pjfftdeparture.compareTo(BigDecimal.ZERO) == 1 ? 1 : 2);//����-���ڣ�����0���ƻ���С��0����
                    pjfftpunishDetail.setComb("pjfft");
                    pjfftpunishDetail.setPlan(pjfft1plan);
                    pjfftpunishDetail.setDiff(pjfftdiff);
                    pjfftpunishDetail.setDeparture(pjfftdeparture);
                    pjfftpunishDetail.setPunishMoney(pjfftpunishMoney);
                    pjfftpunishDetail.setPunishDay(punishDay);
                    pjfftpunishDetail.setLeftDay(leftDay);
                    punishInterestMapper.insertPunishDetail(pjfftpunishDetail);

                    PunishDetail shitipunishDetail = new PunishDetail();
                    shitipunishDetail.setDay(day);
                    shitipunishDetail.setMonth(Integer.parseInt(str));
                    shitipunishDetail.setOrgan(organ);
                    shitipunishDetail.setType(shitideparture.compareTo(BigDecimal.ZERO) == 1 ? 1 : 2);//����-���ڣ�����0���ƻ���С��0����
                    shitipunishDetail.setComb("shiti");
                    shitipunishDetail.setPlan(everyDaysShitiPlan);
                    shitipunishDetail.setDiff(shitidiff);
                    shitipunishDetail.setDeparture(shitideparture);
                    shitipunishDetail.setPunishMoney(shitipunishMoney);
                    shitipunishDetail.setPunishDay(punishDay);
                    shitipunishDetail.setLeftDay(leftDay);
                    punishInterestMapper.insertPunishDetail(shitipunishDetail);

                    PunishDetail leftpunishDetail = new PunishDetail();
                    leftpunishDetail.setDay(day);
                    leftpunishDetail.setMonth(Integer.parseInt(str));
                    leftpunishDetail.setOrgan(organ);
                    leftpunishDetail.setType(3);//����-���ڣ�����0���ƻ���С��0����
                    leftpunishDetail.setComb("totalLeft");
                    leftpunishDetail.setPlan(everyDaysTotallPlan);
                    leftpunishDetail.setDiff(totaldiff);
                    leftpunishDetail.setDeparture(totaldeparture);
                    leftpunishDetail.setPunishMoney(leftpunishMoney);
                    leftpunishDetail.setPunishDay(punishDay);
                    leftpunishDetail.setLeftDay(leftDay);
                    punishInterestMapper.insertPunishDetail(leftpunishDetail);

                }
            }
        }
    }

    /**
     * @param type    //��Ϣ���ͣ�0 ��ĩ����,1��ĩ��ĩ,2�Ǽ�ĩ����,3�Ǽ�ĩ��ĩ
     * @param ppType  //1���� 0����
     * @param sortNum //0 1 2 3
     * @return
     */

    public TbPunishParam initTbPunishParam(int type, int ppType, int sortNum) {
        TbPunishParam TbPunishParam = new TbPunishParam();
        TbPunishParam.setType(type);//��Ϣ���ͣ�0 ��ĩ����,1��ĩ��ĩ,2�Ǽ�ĩ����,3�Ǽ�ĩ��ĩ
        TbPunishParam.setPpType(ppType);//1���� 0����

        List<TbPunishParam> tbPunishParams = tbPunishParamMapper.selectByType(TbPunishParam);
        TbPunishParam param = tbPunishParams.get(sortNum);

        BigDecimal minNum = param.getMinnum().divide(new BigDecimal("100"));
        BigDecimal maxNum = param.getMaxnum().divide(new BigDecimal("100"));
        BigDecimal interest = BigDecimal.ZERO;
        if (param.getCollecttype() == 0) {
            interest = param.getInterest().divide(new BigDecimal("100"));
        } else if (param.getCollecttype() == 1) {
            interest = param.getInterest().divide(new BigDecimal("100000000"));
        }
        param.setMinnum(minNum);
        param.setMaxnum(maxNum);
        param.setInterest(interest);
        return param;
    }

    /**
     *
     * @param year ��
     * @param month ��
     * @param day ��
     * @return leftDay ���µ׵�ʣ��ʱ�� 31-27=4
     */
    public int calculateLeftDay(int year,int month, int day) {
        int leftDay = 0;
        if (month == 1) {
            leftDay = 31 - day;
        } else if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                leftDay = 29 - day;
            } else {
                leftDay = 28 - day;
            }
        } else if (month == 4) {
            leftDay = 30 - day;
        } else if (month == 5) {
            leftDay = 31 - day;
        } else if (month == 7) {
            leftDay = 31 - day;
        } else if (month == 8) {
            leftDay = 31 - day;
        } else if (month == 10) {
            leftDay = 31 - day;
        } else if (month == 11) {
            leftDay = 30 - day;
        }else if (month == 3) {
            leftDay = 31 - day;
        } else if (month == 6) {
            leftDay = 30 - day;
        } else if (month == 9) {
            leftDay = 30 - day;
        } else if (month == 12) {
            leftDay = 31 - day;
        }
        return leftDay;
    }

    /**
     * @param year      ��Ϣ���
     * @param month     ��Ϣ�·�
     * @param day       ��Ϣ����
     * @param punishDay ���·�Ϣ���� ��������-��ʼ����+1
     * @param plan      ���¼ƻ�
     * @param departure ƫ������������-������-plan
     * @param ratio     �ƻ�ƫ���� departure/plan
     * @return
     */
    public BigDecimal punishCalculate(int year, int month, int day, int punishDay, BigDecimal plan, BigDecimal departure, BigDecimal ratio) {

        TbPunishParam jimoyuezhongXianzhi1 = initTbPunishParam(0, 1, 0);
        BigDecimal jimoyuezhongXianzhi1Min = (jimoyuezhongXianzhi1.getMinnum());
        BigDecimal jimoyuezhongXianzhi1Max = (jimoyuezhongXianzhi1.getMaxnum());
        BigDecimal jimoyuezhongXianzhi1Interest = (jimoyuezhongXianzhi1.getInterest());

        TbPunishParam jimoyuezhongXianzhi2 = initTbPunishParam(0, 1, 1);
        BigDecimal jimoyuezhongXianzhi2Min = (jimoyuezhongXianzhi2.getMinnum());
        BigDecimal jimoyuezhongXianzhi2Max = (jimoyuezhongXianzhi2.getMaxnum());
        BigDecimal jimoyuezhongXianzhi2Interest = (jimoyuezhongXianzhi2.getInterest());

        TbPunishParam jimoyuezhongXianzhi3 = initTbPunishParam(0, 1, 2);
        BigDecimal jimoyuezhongXianzhi3Min = (jimoyuezhongXianzhi3.getMinnum());
        BigDecimal jimoyuezhongXianzhi3Max = (jimoyuezhongXianzhi3.getMaxnum());
        BigDecimal jimoyuezhongXianzhi3Interest = (jimoyuezhongXianzhi3.getInterest());

        TbPunishParam jimoyuezhongXianzhi4 = initTbPunishParam(0, 1, 3);
        BigDecimal jimoyuezhongXianzhi4Min = (jimoyuezhongXianzhi4.getMinnum());
        BigDecimal jimoyuezhongXianzhi4Max = (jimoyuezhongXianzhi4.getMaxnum());
        BigDecimal jimoyuezhongXianzhi4Interest = (jimoyuezhongXianzhi4.getInterest());

        TbPunishParam jimoyuezhongChaochu1 = initTbPunishParam(0, 0, 0);
        BigDecimal jimoyuezhongChaochu1Min = (jimoyuezhongChaochu1.getMinnum());
        BigDecimal jimoyuezhongChaochu1Max = (jimoyuezhongChaochu1.getMaxnum());
        BigDecimal jimoyuezhongChaochu1Interest = (jimoyuezhongChaochu1.getInterest());

        TbPunishParam jimoyuezhongChaochu2 = initTbPunishParam(0, 0, 1);
        BigDecimal jimoyuezhongChaochu2Min = (jimoyuezhongChaochu2.getMinnum());
        BigDecimal jimoyuezhongChaochu2Max = (jimoyuezhongChaochu2.getMaxnum());
        BigDecimal jimoyuezhongChaochu2Interest = (jimoyuezhongChaochu2.getInterest());

        TbPunishParam jimoyuezhongChaochu3 = initTbPunishParam(0, 0, 2);
        BigDecimal jimoyuezhongChaochu3Min = (jimoyuezhongChaochu3.getMinnum());
        BigDecimal jimoyuezhongChaochu3Max = (jimoyuezhongChaochu3.getMaxnum());
        BigDecimal jimoyuezhongChaochu3Interest = (jimoyuezhongChaochu3.getInterest());

        TbPunishParam jimoyuezhongChaochu4 = initTbPunishParam(0, 0, 3);
        BigDecimal jimoyuezhongChaochu4Min = (jimoyuezhongChaochu4.getMinnum());
        BigDecimal jimoyuezhongChaochu4Max = (jimoyuezhongChaochu4.getMaxnum());
        BigDecimal jimoyuezhongChaochu4Interest = (jimoyuezhongChaochu4.getInterest());

        TbPunishParam jimoyuemoXianzhi1 = initTbPunishParam(1, 1, 0);
        BigDecimal jimoyuemoXianzhi1Min = (jimoyuemoXianzhi1.getMinnum());
        BigDecimal jimoyuemoXianzhi1Max = (jimoyuemoXianzhi1.getMaxnum());
        BigDecimal jimoyuemoXianzhi1Interest = (jimoyuemoXianzhi1.getInterest());

        TbPunishParam jimoyuemoXianzhi2 = initTbPunishParam(1, 1, 1);
        BigDecimal jimoyuemoXianzhi2Min = (jimoyuemoXianzhi2.getMinnum());
        BigDecimal jimoyuemoXianzhi2Max = (jimoyuemoXianzhi2.getMaxnum());
        BigDecimal jimoyuemoXianzhi2Interest = (jimoyuemoXianzhi2.getInterest());

        TbPunishParam jimoyuemoXianzhi3 = initTbPunishParam(1, 1, 2);
        BigDecimal jimoyuemoXianzhi3Min = (jimoyuemoXianzhi3.getMinnum());
        BigDecimal jimoyuemoXianzhi3Max = (jimoyuemoXianzhi3.getMaxnum());
        BigDecimal jimoyuemoXianzhi3Interest = (jimoyuemoXianzhi3.getInterest());

        TbPunishParam jimoyuemoXianzhi4 = initTbPunishParam(1, 1, 3);
        BigDecimal jimoyuemoXianzhi4Min = (jimoyuemoXianzhi4.getMinnum());
        BigDecimal jimoyuemoXianzhi4Max = (jimoyuemoXianzhi4.getMaxnum());
        BigDecimal jimoyuemoXianzhi4Interest = (jimoyuemoXianzhi4.getInterest());

        TbPunishParam jimoyuemoChaochu1 = initTbPunishParam(1, 0, 0);
        BigDecimal jimoyuemoChaochu1Min = (jimoyuemoChaochu1.getMinnum());
        BigDecimal jimoyuemoChaochu1Max = (jimoyuemoChaochu1.getMaxnum());
        BigDecimal jimoyuemoChaochu1Interest = (jimoyuemoChaochu1.getInterest());

        TbPunishParam jimoyuemoChaochu2 = initTbPunishParam(1, 0, 1);
        BigDecimal jimoyuemoChaochu2Min = (jimoyuemoChaochu2.getMinnum());
        BigDecimal jimoyuemoChaochu2Max = (jimoyuemoChaochu2.getMaxnum());
        BigDecimal jimoyuemoChaochu2Interest = (jimoyuemoChaochu2.getInterest());

        TbPunishParam jimoyuemoChaochu3 = initTbPunishParam(1, 0, 2);
        BigDecimal jimoyuemoChaochu3Min = (jimoyuemoChaochu3.getMinnum());
        BigDecimal jimoyuemoChaochu3Max = (jimoyuemoChaochu3.getMaxnum());
        BigDecimal jimoyuemoChaochu3Interest = (jimoyuemoChaochu3.getInterest());

        TbPunishParam jimoyuemoChaochu4 = initTbPunishParam(1, 0, 3);
        BigDecimal jimoyuemoChaochu4Min = (jimoyuemoChaochu4.getMinnum());
        BigDecimal jimoyuemoChaochu4Max = (jimoyuemoChaochu4.getMaxnum());
        BigDecimal jimoyuemoChaochu4Interest = (jimoyuemoChaochu4.getInterest());

        TbPunishParam feijimoyuezhongXianzhi1 = initTbPunishParam(2, 1, 0);
        BigDecimal feijimoyuezhongXianzhi1Min = (feijimoyuezhongXianzhi1.getMinnum());
        BigDecimal feijimoyuezhongXianzhi1Max = (feijimoyuezhongXianzhi1.getMaxnum());
        BigDecimal feijimoyuezhongXianzhi1Interest = (feijimoyuezhongXianzhi1.getInterest());

        TbPunishParam feijimoyuezhongXianzhi2 = initTbPunishParam(2, 1, 1);
        BigDecimal feijimoyuezhongXianzhi2Min = (feijimoyuezhongXianzhi2.getMinnum());
        BigDecimal feijimoyuezhongXianzhi2Max = (feijimoyuezhongXianzhi2.getMaxnum());
        BigDecimal feijimoyuezhongXianzhi2Interest = (feijimoyuezhongXianzhi2.getInterest());

        TbPunishParam feijimoyuezhongXianzhi3 = initTbPunishParam(2, 1, 2);
        BigDecimal feijimoyuezhongXianzhi3Min = (feijimoyuezhongXianzhi3.getMinnum());
        BigDecimal feijimoyuezhongXianzhi3Max = (feijimoyuezhongXianzhi3.getMaxnum());
        BigDecimal feijimoyuezhongXianzhi3Interest = (feijimoyuezhongXianzhi3.getInterest());

        TbPunishParam feijimoyuezhongXianzhi4 = initTbPunishParam(2, 1, 3);
        BigDecimal feijimoyuezhongXianzhi4Min = (feijimoyuezhongXianzhi4.getMinnum());
        BigDecimal feijimoyuezhongXianzhi4Max = (feijimoyuezhongXianzhi4.getMaxnum());
        BigDecimal feijimoyuezhongXianzhi4Interest = (feijimoyuezhongXianzhi4.getInterest());

        TbPunishParam feijimoyuezhongChaochu1 = initTbPunishParam(2, 0, 0);
        BigDecimal feijimoyuezhongChaochu1Min = (feijimoyuezhongChaochu1.getMinnum());
        BigDecimal feijimoyuezhongChaochu1Max = (feijimoyuezhongChaochu1.getMaxnum());
        BigDecimal feijimoyuezhongChaochu1Interest = (feijimoyuezhongChaochu1.getInterest());

        TbPunishParam feijimoyuezhongChaochu2 = initTbPunishParam(2, 0, 1);
        BigDecimal feijimoyuezhongChaochu2Min = (feijimoyuezhongChaochu2.getMinnum());
        BigDecimal feijimoyuezhongChaochu2Max = (feijimoyuezhongChaochu2.getMaxnum());
        BigDecimal feijimoyuezhongChaochu2Interest = (feijimoyuezhongChaochu2.getInterest());

        TbPunishParam feijimoyuezhongChaochu3 = initTbPunishParam(2, 0, 2);
        BigDecimal feijimoyuezhongChaochu3Min = (feijimoyuezhongChaochu3.getMinnum());
        BigDecimal feijimoyuezhongChaochu3Max = (feijimoyuezhongChaochu3.getMaxnum());
        BigDecimal feijimoyuezhongChaochu3Interest = (feijimoyuezhongChaochu3.getInterest());

        TbPunishParam feijimoyuezhongChaochu4 = initTbPunishParam(2, 0, 3);
        BigDecimal feijimoyuezhongChaochu4Min = (feijimoyuezhongChaochu4.getMinnum());
        BigDecimal feijimoyuezhongChaochu4Max = (feijimoyuezhongChaochu4.getMaxnum());
        BigDecimal feijimoyuezhongChaochu4Interest = (feijimoyuezhongChaochu4.getInterest());

        TbPunishParam feijimoyuemoXianzhi1 = initTbPunishParam(3, 1, 0);
        BigDecimal feijimoyuemoXianzhi1Min = (feijimoyuemoXianzhi1.getMinnum());
        BigDecimal feijimoyuemoXianzhi1Max = (feijimoyuemoXianzhi1.getMaxnum());
        BigDecimal feijimoyuemoXianzhi1Interest = (feijimoyuemoXianzhi1.getInterest());

        TbPunishParam feijimoyuemoXianzhi2 = initTbPunishParam(3, 1, 1);
        BigDecimal feijimoyuemoXianzhi2Min = (feijimoyuemoXianzhi2.getMinnum());
        BigDecimal feijimoyuemoXianzhi2Max = (feijimoyuemoXianzhi2.getMaxnum());
        BigDecimal feijimoyuemoXianzhi2Interest = (feijimoyuemoXianzhi2.getInterest());

        TbPunishParam feijimoyuemoXianzhi3 = initTbPunishParam(3, 1, 2);
        BigDecimal feijimoyuemoXianzhi3Min = (feijimoyuemoXianzhi3.getMinnum());
        BigDecimal feijimoyuemoXianzhi3Max = (feijimoyuemoXianzhi3.getMaxnum());
        BigDecimal feijimoyuemoXianzhi3Interest = (feijimoyuemoXianzhi3.getInterest());

        TbPunishParam feijimoyuemoXianzhi4 = initTbPunishParam(3, 1, 3);
        BigDecimal feijimoyuemoXianzhi4Min = (feijimoyuemoXianzhi4.getMinnum());
        BigDecimal feijimoyuemoXianzhi4Max = (feijimoyuemoXianzhi4.getMaxnum());
        BigDecimal feijimoyuemoXianzhi4Interest = (feijimoyuemoXianzhi4.getInterest());

        TbPunishParam feijimoyuemoChaochu1 = initTbPunishParam(3, 0, 0);
        BigDecimal feijimoyuemoChaochu1Min = (feijimoyuemoChaochu1.getMinnum());
        BigDecimal feijimoyuemoChaochu1Max = (feijimoyuemoChaochu1.getMaxnum());
        BigDecimal feijimoyuemoChaochu1Interest = (feijimoyuemoChaochu1.getInterest());

        TbPunishParam feijimoyuemoChaochu2 = initTbPunishParam(3, 0, 1);
        BigDecimal feijimoyuemoChaochu2Min = (feijimoyuemoChaochu2.getMinnum());
        BigDecimal feijimoyuemoChaochu2Max = (feijimoyuemoChaochu2.getMaxnum());
        BigDecimal feijimoyuemoChaochu2Interest = (feijimoyuemoChaochu2.getInterest());

        TbPunishParam feijimoyuemoChaochu3 = initTbPunishParam(3, 0, 2);
        BigDecimal feijimoyuemoChaochu3Min = (feijimoyuemoChaochu3.getMinnum());
        BigDecimal feijimoyuemoChaochu3Max = (feijimoyuemoChaochu3.getMaxnum());
        BigDecimal feijimoyuemoChaochu3Interest = (feijimoyuemoChaochu3.getInterest());

        TbPunishParam feijimoyuemoChaochu4 = initTbPunishParam(3, 0, 3);
        BigDecimal feijimoyuemoChaochu4Min = (feijimoyuemoChaochu4.getMinnum());
        BigDecimal feijimoyuemoChaochu4Max = (feijimoyuemoChaochu4.getMaxnum());
        BigDecimal feijimoyuemoChaochu4Interest = (feijimoyuemoChaochu4.getInterest());

        BigDecimal punishMoney = BigDecimal.ZERO;//��Ϣ���
//            float ratio=departure/plan;//�ƻ�ƫ����

        //3 6 9 12��ĩ��
        if (month == 3 || month == 6 || month == 9 || month == 12) {
            int leftDay = calculateLeftDay(year,month, day);
            if (leftDay > punishDay || leftDay == punishDay) {
                System.out.println("���ڷ�Ϣʱ����");
            } else if (leftDay < 0) {
                System.out.println("���ڲ��Ϸ�");
            } else {
                for (int y = 0; y < punishDay; y++) {
                    if (y < punishDay - 1) {
                        //��ĩ���й���
                        /**
                         * ��������ĩ���г��ƻ�5%���ڣ���5%�����֣���5%��������ȡ����ģռ�÷ѣ�
                         * ���ƻ�5��8%����8%�����֣���10%��������ȡ����ģռ�÷ѣ�
                         *  ���ƻ�8��12%����12%�����֣���������20%��ȡ����ģռ�÷ѣ�
                         *  ���ƻ�12%���ϲ��֣���30%��������ȡ����ģռ�÷ѡ�
                         */
//                        if (ratio > 0 && ratio < 0.05 || ratio == 0.05) {
                        if (ratio.compareTo(jimoyuezhongChaochu1Min) == 1 && ratio.compareTo(jimoyuezhongChaochu1Max) == -1 || ratio.compareTo(jimoyuezhongChaochu1Max) == 0) {
//                            punishMoney = (float) ((diff * 0.05)) / 360;
                            punishMoney = (departure.multiply(jimoyuezhongChaochu1Interest)).divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
//                            (ratio > 0.05 && ratio < 0.08 || ratio == 0.08)
                        } else if (ratio.compareTo(jimoyuezhongChaochu2Min) == 1 && ratio.compareTo(jimoyuezhongChaochu2Max) == -1 || ratio.compareTo(jimoyuezhongChaochu2Max) == 0) {
//                            punishMoney = (float) ((plan * 0.05 * 0.05)
//                            + (diff - plan * 0.05) * 0.1) / 360;
                            BigDecimal a = plan.multiply(jimoyuezhongChaochu1Max).multiply(jimoyuezhongChaochu1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(jimoyuezhongChaochu2Min));
                            BigDecimal c = b.multiply(jimoyuezhongChaochu2Interest);
                            BigDecimal d = a.add(c);
                            punishMoney = d.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                        } else if (ratio.compareTo(jimoyuezhongChaochu3Min) == 1 && ratio.compareTo(jimoyuezhongChaochu3Max) == -1 || ratio.compareTo(jimoyuezhongChaochu3Max) == 0) {
//                            punishMoney = (float) (plan * 0.05 * 0.05
//                            + (plan * 0.08 - plan * 0.05) * 0.1
//                            + (diff - plan * 0.08) * 0.2) / 360;
                            BigDecimal a = plan.multiply(jimoyuezhongChaochu1Max).multiply(jimoyuezhongChaochu1Interest);
                            BigDecimal b = plan.multiply(jimoyuezhongChaochu2Max.subtract(jimoyuezhongChaochu2Min)).multiply(jimoyuezhongChaochu2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(jimoyuezhongChaochu3Min));
                            BigDecimal d = c.multiply(jimoyuezhongChaochu3Interest);
                            BigDecimal e = a.add(b).add(d);
                            punishMoney = e.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
//                            (ratio > 0.12)
                        } else if (ratio.compareTo(jimoyuezhongChaochu4Min) == 1) {
//                            punishMoney = (float) (plan * 0.05 * 0.05
//                                    + (plan * 0.08 - plan * 0.05) * 0.1
//                                    + (plan * 0.12 - plan * 0.08) * 0.2
//                                    + (diff - plan * 0.12) * 0.3)
//                                    / 360;
                            BigDecimal a = plan.multiply(jimoyuezhongChaochu1Max).multiply(jimoyuezhongChaochu1Interest);
                            BigDecimal b = plan.multiply(jimoyuezhongChaochu2Max.subtract(jimoyuezhongChaochu2Min)).multiply(jimoyuezhongChaochu2Interest);
                            BigDecimal c = plan.multiply(jimoyuezhongChaochu3Max.subtract(jimoyuezhongChaochu3Min)).multiply(jimoyuezhongChaochu3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(jimoyuezhongChaochu4Min));
                            BigDecimal e = d.multiply(jimoyuezhongChaochu4Interest);
                            BigDecimal f = a.add(b).add(c).add(e);
                            punishMoney = f.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                        }
                        /**
                         *
                         * ��ĩ���мƻ�����10��17%���ڣ���10%�����֣���5%��������ȡ����ģռ�÷ѣ�
                         * �ƻ�����17��24%����17%�����֣���7%��������ȡ����ģռ�÷ѣ�
                         * �ƻ�����24��31%����24%�����֣���������10%��ȡ����ģռ�÷ѣ�
                         * �ƻ�����31%���ϲ��֣���15%��������ȡ����ģռ�÷ѡ�
                         *
                         * (departure-plan*10%)*5%
                         * plan*(17%-10%)*5%+(departure-plan*17%)*7%
                         * plan*(17%-10%)*5%+plan*(24%-17%)*7%+(departure-plan*24%)*10%
                         * plan*(17%-10%)*5%+plan*(24%-17%)*10%+plan*(31%-24%)*10%+(departure-plan*24%)*10%
                         *
                         *
                         */
//                        (ratio > -0.17 && ratio < -0.10 || ratio == -0.10)
                        else if (ratio.compareTo(jimoyuezhongXianzhi1Max.negate()) == 1 && ratio.compareTo(jimoyuezhongXianzhi1Min.negate()) == -1 || ratio.compareTo(jimoyuezhongXianzhi1Min.negate()) == 0) {
//                            punishMoney = (departure-plan*10%)*5%/360
                            BigDecimal a = departure.subtract(plan.multiply(jimoyuezhongXianzhi1Min));
                            BigDecimal b = a.multiply(jimoyuezhongXianzhi1Interest);
                            punishMoney = b.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > -0.24 && ratio < -0.17 || ratio == -0.17
                        } else if (ratio.compareTo(jimoyuezhongXianzhi2Max.negate()) == 1 && ratio.compareTo(jimoyuezhongXianzhi2Min.negate()) == -1 || ratio.compareTo(jimoyuezhongXianzhi2Min.negate()) == 0) {
//                            plan*(17%-10%)*5%+(departure-plan*17%)*7%
                            BigDecimal a = plan.multiply(jimoyuezhongXianzhi1Max.subtract(jimoyuezhongXianzhi1Min));
                            BigDecimal b = a.multiply(jimoyuezhongXianzhi1Interest);
                            BigDecimal c = plan.multiply(jimoyuezhongXianzhi2Min);
                            BigDecimal d = departure.subtract(c);
                            BigDecimal e = d.multiply(jimoyuezhongXianzhi2Interest);
                            BigDecimal f = b.add(e);
                            punishMoney = f.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > -0.31 && ratio < -0.24 || ratio == -0.24
                        } else if (ratio.compareTo(jimoyuezhongXianzhi3Max.negate()) == 1
                                && ratio.compareTo(jimoyuezhongXianzhi3Min.negate()) == -1
                                || ratio.compareTo(jimoyuezhongXianzhi3Min.negate()) == 0) {
//                              plan*(17%-10%)*5%+plan*(24%-17%)*7%+(departure-plan*24%)*10%
                            BigDecimal a = plan.multiply(jimoyuezhongXianzhi1Max.subtract(jimoyuezhongXianzhi1Min)).multiply(jimoyuezhongXianzhi1Interest);
                            BigDecimal b = plan.multiply(jimoyuezhongXianzhi2Max.subtract(jimoyuezhongXianzhi2Min)).multiply(jimoyuezhongXianzhi2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(jimoyuezhongXianzhi3Min));
                            BigDecimal d = c.multiply(jimoyuezhongXianzhi3Interest);
                            BigDecimal e = a.add(b).add(d);
                            punishMoney = e.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio < -0.31 || ratio == -0.31
                        } else if (ratio.compareTo(jimoyuezhongXianzhi4Min.negate()) == -1
                                || ratio.compareTo(jimoyuezhongXianzhi4Min.negate()) == 0) {
//                          plan*(17%-10%)*5%+plan*(24%-17%)*10%+plan*(31%-24%)*10%+(departure-plan*24%)*15%
                            BigDecimal a = plan.multiply(jimoyuezhongXianzhi1Max.subtract(jimoyuezhongXianzhi1Min)).multiply(jimoyuezhongXianzhi1Interest);
                            BigDecimal b = plan.multiply(jimoyuezhongXianzhi2Max.subtract(jimoyuezhongXianzhi2Min)).multiply(jimoyuezhongXianzhi2Interest);
                            BigDecimal c = plan.multiply(jimoyuezhongXianzhi3Max.subtract(jimoyuezhongXianzhi3Min)).multiply(jimoyuezhongXianzhi3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(jimoyuezhongXianzhi4Min));
                            BigDecimal e = d.multiply(jimoyuezhongXianzhi4Interest);
                            BigDecimal f = a.add(b).add(c).add(e);
                            punishMoney = f.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                        }
                    } else {
                        //��ĩ����
                        //jimoyuemochaochu
                        /**
                         *
                         * ��ĩ���ƻ�5%���ڣ���5%�����֣���ÿ1��Ԫ��ȡ5��Ԫ�ĳ���ģռ�÷ѣ�
                         * ���ƻ�5��8%����8%�����֣���ÿ1��Ԫ��ȡ10��Ԫ�ĳ���ģռ�÷ѣ�
                         * ���ƻ�8��12%����12%�����֣���ÿ1��Ԫ��ȡ15��Ԫ�ĳ���ģռ�÷ѣ�
                         * ���ƻ�12%���ϲ��֣���ÿ1��Ԫ��ȡ20��Ԫ�ĳ���ģռ�÷ѡ�
                         *
                         * dep*5/10000
                         * plan*(5-0)/10000+(dep-plan*5%)*10/10000
                         * plan*(5-0)/10000+plan*(8-5)*10/10000+(dep-plan*8%)*15/10000
                         * plan*(5-0)/10000+plan*(8-5)*10/10000+plan*(12-8)*15/10000+(dep-plan*12)*20/10000
                         */
                        //ratio > 0 && ratio < 0.05 || ratio == 0.05
                        if (
                                ratio.compareTo(jimoyuemoChaochu1Min) == 1
                                        && ratio.compareTo(jimoyuemoChaochu1Max) == -1
                                        || ratio.compareTo(jimoyuemoChaochu1Max) == 0
                        ) {
//                            punishMoney = (float) (diff * 0.0005);
                            punishMoney = departure.multiply(jimoyuemoChaochu1Interest);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > 0.05 && ratio < 0.08 || ratio == 0.08
                        } else if (
                                ratio.compareTo(jimoyuemoChaochu2Min) == 1
                                        && ratio.compareTo(jimoyuemoChaochu2Max) == -1
                                        || ratio.compareTo(jimoyuemoChaochu2Max) == 0
                        ) {
//                            plan*(5-0)/10000+(dep-plan*5%)*10/10000
                            BigDecimal a = plan.multiply(jimoyuemoChaochu1Max.subtract(jimoyuemoChaochu1Min)).multiply(jimoyuemoChaochu1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(jimoyuemoChaochu2Min));
                            BigDecimal c = b.multiply(jimoyuemoChaochu2Interest);
                            punishMoney = a.add(c);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > 0.08 && ratio < 0.12 || ratio == 0.12
                        } else if (
                                ratio.compareTo(jimoyuemoChaochu3Min) == 1
                                        && ratio.compareTo(jimoyuemoChaochu3Max) == -1
                                        || ratio.compareTo(jimoyuemoChaochu3Max) == 0
                        ) {
//                            plan*(5-0)/10000+plan*(8-5)*10/10000+(dep-plan*8%)*15/10000
                            BigDecimal a = plan.multiply(jimoyuemoChaochu1Max.subtract(jimoyuemoChaochu1Min)).multiply(jimoyuemoChaochu1Interest);
                            BigDecimal b = plan.multiply(jimoyuemoChaochu2Max.subtract(jimoyuemoChaochu2Min)).multiply(jimoyuemoChaochu2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(jimoyuemoChaochu3Min));
                            BigDecimal d = c.multiply(jimoyuemoChaochu3Interest);
                            punishMoney = a.add(b).add(d);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > 0.12
                        } else if (ratio.compareTo(jimoyuemoChaochu4Min) == 1) {
//                            plan*(5-0)/10000+plan*(8-5)*10/10000+plan*(12-8)*15/10000+(dep-plan*12)*20/10000
                            BigDecimal a = plan.multiply(jimoyuemoChaochu1Max.subtract(jimoyuemoChaochu1Min)).multiply(jimoyuemoChaochu1Interest);
                            BigDecimal b = plan.multiply(jimoyuemoChaochu2Max.subtract(jimoyuemoChaochu2Min)).multiply(jimoyuemoChaochu2Interest);
                            BigDecimal c = plan.multiply(jimoyuemoChaochu3Max.subtract(jimoyuemoChaochu3Min)).multiply(jimoyuemoChaochu3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(jimoyuemoChaochu4Min));
                            BigDecimal e = d.multiply(jimoyuemoChaochu4Interest);
                            punishMoney = a.add(b).add(c).add(e);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                        }
                        /**
                         *��ĩ�ƻ�����10��17%���ڣ���10%�����֣���ÿ1��Ԫ��ȡ3��Ԫ�ĳ���ģռ�÷ѣ�
                         * �ƻ�����17��24%����17%�����֣���ÿ1��Ԫ��ȡ5��Ԫ�ĳ���ģռ�÷ѣ�
                         * �ƻ�����24��31%����24%�����֣���ÿ1��Ԫ��ȡ7��Ԫ�ĳ���ģռ�÷ѣ�
                         * �ƻ�����31%���ϲ��֣���ÿ1��Ԫ��ȡ10��Ԫ�ĳ���ģռ�÷ѡ�
                         *
                         * (departure-plan*10%)*3/10000
                         * plan*(17%-10%)*3/10000+(departure-plan*17%)*5/10000
                         * plan*(17%-10%)*3/10000+plan*(24%-17%)*5/10000+(departure-plan*24%)*7/10000
                         * plan*(17%-10%)*3/10000+plan*(24%-17%)*5/10000+plan*(31%-24%)*7/10000+(departure-plan*24%)*10/10000
                         */
                        //ratio > -0.17 && ratio < -0.10 || ratio == -0.10
                        else if (
                                ratio.compareTo(jimoyuemoXianzhi1Max.negate()) == 1
                                        && ratio.compareTo(jimoyuemoXianzhi1Min.negate()) == -1
                                        || ratio.compareTo(jimoyuemoXianzhi1Min.negate()) == 0
                        ) {
                            BigDecimal a = departure.subtract(plan.multiply(jimoyuemoXianzhi1Max));
                            punishMoney = a.multiply(jimoyuemoXianzhi1Interest);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > -0.24 && ratio < -0.17 || ratio == -0.17
                        } else if (
                                ratio.compareTo(jimoyuemoXianzhi2Max.negate()) ==
                                        1 && ratio.compareTo(jimoyuemoXianzhi2Min.negate()) == -1
                                        || ratio.compareTo(jimoyuemoXianzhi2Min.negate()) == 0
                        ) {
//                            plan*(17%-10%)*3/10000+(departure-plan*17%)*5/10000
                            BigDecimal a = plan.multiply(jimoyuemoXianzhi1Max.subtract(jimoyuemoXianzhi1Min)).multiply(jimoyuemoXianzhi1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(jimoyuemoXianzhi2Min));
                            BigDecimal c = b.multiply(jimoyuemoXianzhi2Interest);
                            punishMoney = a.add(c);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio > -0.31 && ratio < -0.24 || ratio == -0.24
                        } else if (
                                ratio.compareTo(jimoyuemoXianzhi2Max.negate()) == 1
                                        && ratio.compareTo(jimoyuemoXianzhi2Min.negate()) == -1
                                        || ratio.compareTo(jimoyuemoXianzhi2Min.negate()) == 0
                        ) {
//                            plan*(17%-10%)*3/10000+plan*(24%-17%)*5/10000+(departure-plan*24%)*7/10000
                            BigDecimal a = plan.multiply(jimoyuemoXianzhi1Max.subtract(jimoyuemoXianzhi1Min)).multiply(jimoyuemoXianzhi1Interest);
                            BigDecimal b = plan.multiply(jimoyuemoXianzhi2Max.subtract(jimoyuemoXianzhi2Min)).multiply(jimoyuemoXianzhi2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(jimoyuemoXianzhi3Min));
                            BigDecimal d = c.multiply(jimoyuemoXianzhi3Interest);
                            punishMoney = a.add(b).add(d);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                            //ratio < -0.31 || ratio == -0.31
                        } else if (
                                ratio.compareTo(jimoyuemoXianzhi4Min.negate()) == -1
                                        || ratio.compareTo(jimoyuemoXianzhi4Min.negate()) == 0
                        ) {
//                            plan*(17%-10%)*3/10000+plan*(24%-17%)*5/10000+plan*(31%-24%)*7/10000+(departure-plan*24%)*10/10000
                            BigDecimal a = plan.multiply(jimoyuemoXianzhi1Max.subtract(jimoyuemoXianzhi1Min)).multiply(jimoyuemoXianzhi1Interest);
                            BigDecimal b = plan.multiply(jimoyuemoXianzhi2Max.subtract(jimoyuemoXianzhi2Min)).multiply(jimoyuemoXianzhi2Interest);
                            BigDecimal c = plan.multiply(jimoyuemoXianzhi3Max.subtract(jimoyuemoXianzhi3Min)).multiply(jimoyuemoXianzhi3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(jimoyuemoXianzhi4Min));
                            BigDecimal e = d.multiply(jimoyuemoXianzhi4Interest);
                            punishMoney = a.add(b).add(c).add(e);
                            if (leftDay + 1 == punishDay - y) {
                                break;
                            }
                        }
                    }
                }
            }
        } else if (month == 1 || month == 2 || month == 4 || month == 5 || month == 7 || month == 8 || month == 10 || month == 11) {
            int leftDay = calculateLeftDay(year,month, day);
            if (leftDay > punishDay || leftDay == punishDay) {
                System.out.println("���ڷ�Ϣʱ����");
            } else if (leftDay < 0) {
                System.out.println("���ڲ��Ϸ�");
            } else {
                //�Ǽ�ĩ����
                /**
                 * ���г��ƻ�5%���ڣ���5%�����֣���5%��������ȡ����ģռ�÷ѣ�
                 * ���ƻ�5��10%����10%�����֣���10%��������ȡ����ģռ�÷ѣ�
                 * ���ƻ�10��15%����15%�����֣���������20%��ȡ����ģռ�÷ѣ�
                 * ���ƻ�15%���ϲ��֣���30%��������ȡ����ģռ�÷ѡ�
                 *
                 * (dep*5%)/360
                 * (plan*(5-0)5%+(dep-plan*5%)*10%)/360
                 * (plan*(5-0)5%+plan*(10-5)*10%+(dep-plan*10%)*20%)/360
                 * (plan*(5-0)5%+plan*(10-5)*10%+plan*(15-10)*20%+(dep-plan*15)*30%)/360
                 */
                for (int z = 0; z < punishDay; z++) {
                    if (z < punishDay - 1) {
                        //�Ǽ�ĩ���г���
                        //ratio > 0 && ratio < 0.05 || ratio == 0.05
                        if (
                                ratio.compareTo(feijimoyuezhongChaochu1Min) == 1
                                        && ratio.compareTo(feijimoyuezhongChaochu1Max) == -1
                                        || ratio.compareTo(feijimoyuezhongChaochu1Max) == 0
                        ) {
//                             (dep*5%)/360
                            punishMoney = departure.multiply(feijimoyuezhongChaochu1Interest).divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > 0.05 && ratio < 0.10 || ratio == 0.10
                        } else if (
                                ratio.compareTo(feijimoyuezhongChaochu2Min) == 1
                                        && ratio.compareTo(feijimoyuezhongChaochu2Max) == -1
                                        || ratio.compareTo(feijimoyuezhongChaochu2Max) == 0
                        ) {
//                            (plan*(5-0)5%+(dep-plan*5%)*10%)/360
                            BigDecimal a = plan.multiply(feijimoyuezhongChaochu1Max.subtract(feijimoyuezhongChaochu1Min)).multiply(feijimoyuezhongChaochu1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(feijimoyuezhongChaochu2Min));
                            BigDecimal c = b.multiply(feijimoyuezhongChaochu2Interest);
                            BigDecimal d = a.add(c);
                            punishMoney = d.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > 0.10 && ratio < 0.15 || ratio == 0.15
                        } else if (
                                ratio.compareTo(feijimoyuezhongChaochu3Min) == 1
                                        && ratio.compareTo(feijimoyuezhongChaochu3Max) == -1
                                        || ratio.compareTo(feijimoyuezhongChaochu3Max) == 0
                        ) {
//                            (plan*(5-0)5%+plan*(10-5)*10%+(dep-plan*10%)*20%)/360
                            BigDecimal a = plan.multiply(feijimoyuezhongChaochu1Max.subtract(feijimoyuezhongChaochu1Min)).multiply(feijimoyuezhongChaochu1Interest);
                            BigDecimal b = plan.multiply(feijimoyuezhongChaochu2Max.subtract(feijimoyuezhongChaochu2Min)).multiply(feijimoyuezhongChaochu2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(feijimoyuezhongChaochu3Min));
                            BigDecimal d = c.multiply(feijimoyuezhongChaochu3Interest);
                            BigDecimal e = a.add(b).add(d);
                            punishMoney = e.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > 0.15
                        } else if (ratio.compareTo(feijimoyuezhongChaochu4Min) == 1) {
//                            (plan*(5-0)5%+plan*(10-5)*10%+plan*(15-10)*20%+(dep-plan*15)*30%)/360
                            BigDecimal a = plan.multiply(feijimoyuezhongChaochu1Max.subtract(feijimoyuezhongChaochu1Min)).multiply(feijimoyuezhongChaochu1Interest);
                            BigDecimal b = plan.multiply(feijimoyuezhongChaochu2Max.subtract(feijimoyuezhongChaochu2Min)).multiply(feijimoyuezhongChaochu2Interest);
                            BigDecimal c = plan.multiply(feijimoyuezhongChaochu3Max.subtract(feijimoyuezhongChaochu3Min)).multiply(feijimoyuezhongChaochu3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(feijimoyuezhongChaochu4Min));
                            BigDecimal e = d.multiply(feijimoyuezhongChaochu4Interest);
                            BigDecimal f = a.add(b).add(c).add(e);
                            punishMoney = f.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                        }
                        /**
                         * ���мƻ�����10��20%���ڣ���10%�����֣���5%��������ȡ����ģռ�÷ѣ�
                         * �ƻ�����20��30%����20%�����֣���7%��������ȡ����ģռ�÷ѣ�
                         * �ƻ�����30��40%����30%�����֣���������10%��ȡ����ģռ�÷ѣ�
                         * �ƻ�����40%���ϲ��֣���15%��������ȡ����ģռ�÷ѡ�
                         *
                         * (departure-plan*10%)*5%/360
                         * plan*(20%-10%)*5%+(departure-plan*20%)*7%/360
                         * plan*(20%-10%)*5%+plan*(30%-20%)*7%+(departure-plan*30%)*10%/360
                         * plan*(20%-10%)*5%+plan*(30%-20%)*10%+plan*(40%-30%)*10%+(departure-plan*40%)*15%/360
                         */
                        //�Ǽ�ĩ��������
                        //ratio > -0.20 && ratio < -0.10 || ratio == -0.10
                        else if (
                                ratio.compareTo(feijimoyuezhongXianzhi1Max.negate()) == 1
                                        && ratio.compareTo(feijimoyuezhongXianzhi1Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuezhongXianzhi1Min.negate()) == 0
                        ) {
//                            (departure-plan*10%)*5%/360
                            BigDecimal a = departure.subtract(plan.multiply(feijimoyuezhongXianzhi1Min));
                            BigDecimal b = a.multiply(feijimoyuezhongXianzhi1Interest);
                            punishMoney = b.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > -0.30 && ratio < -0.20 || ratio == -0.20
                        } else if (
                                ratio.compareTo(feijimoyuezhongXianzhi2Max.negate()) == 1
                                        && ratio.compareTo(feijimoyuezhongXianzhi2Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuezhongXianzhi2Min.negate()) == 0
                        ) {
//                            plan*(20%-10%)*5%+(departure-plan*20%)*7%/360
                            BigDecimal a = plan.multiply(feijimoyuezhongXianzhi1Max.subtract(feijimoyuezhongXianzhi1Min)).multiply(feijimoyuezhongXianzhi1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(feijimoyuezhongXianzhi2Min));
                            BigDecimal c = b.multiply(feijimoyuezhongXianzhi2Interest);
                            BigDecimal d = a.add(c);
                            punishMoney = d.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > -0.40 && ratio < -0.30 || ratio == -0.30
                        } else if (
                                ratio.compareTo(feijimoyuezhongXianzhi3Max.negate()) == 1
                                        && ratio.compareTo(feijimoyuezhongXianzhi3Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuezhongXianzhi3Min.negate()) == 0
                        ) {
//                            plan*(20%-10%)*5%+plan*(30%-20%)*7%+(departure-plan*30%)*10%/360
                            BigDecimal a = plan.multiply(feijimoyuezhongXianzhi1Max.subtract(feijimoyuezhongXianzhi1Min)).multiply(feijimoyuezhongXianzhi1Interest);
                            BigDecimal b = plan.multiply(feijimoyuezhongXianzhi2Max.subtract(feijimoyuezhongXianzhi2Min)).multiply(feijimoyuezhongXianzhi2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(feijimoyuezhongXianzhi3Min));
                            BigDecimal d = c.multiply(feijimoyuezhongXianzhi3Interest);
                            BigDecimal e = a.add(b).add(d);
                            punishMoney = e.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio < -0.40 || ratio == -0.40
                        } else if (
                                ratio.compareTo(feijimoyuezhongXianzhi4Min) == -1
                                        || ratio.compareTo(feijimoyuezhongXianzhi4Min) == 0
                        ) {
//                            plan*(20%-10%)*5%+plan*(30%-20%)*10%+plan*(40%-30%)*10%+(departure-plan*40%)*15%/360
                            BigDecimal a = plan.multiply(feijimoyuezhongXianzhi1Max.subtract(feijimoyuezhongXianzhi1Min)).multiply(feijimoyuezhongXianzhi1Interest);
                            BigDecimal b = plan.multiply(feijimoyuezhongXianzhi2Max.subtract(feijimoyuezhongXianzhi2Min)).multiply(feijimoyuezhongXianzhi2Interest);
                            BigDecimal c = plan.multiply(feijimoyuezhongXianzhi3Max.subtract(feijimoyuezhongXianzhi3Min)).multiply(feijimoyuezhongXianzhi3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(feijimoyuezhongXianzhi4Min));
                            BigDecimal e = d.multiply(feijimoyuezhongXianzhi4Interest);
                            BigDecimal f = a.add(b).add(c).add(e);
                            punishMoney = f.divide(new BigDecimal("360"), 4, BigDecimal.ROUND_HALF_UP);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                        }
                    } else {
                        //�Ǽ�ĩ��ĩ����
                        /**
                         * ��ĩ���ƻ�5%���ڣ���5%�����֣���ÿ1��Ԫ��ȡ5��Ԫ�ĳ���ģռ�÷ѣ�
                         * ���ƻ�5��10%����10%�����֣���ÿ1��Ԫ��ȡ10��Ԫ�ĳ���ģռ�÷ѣ�
                         * ���ƻ�10��15%����15%�����֣���ÿ1��Ԫ��ȡ15��Ԫ�ĳ���ģռ�÷ѣ�
                         *  ���ƻ�15%���ϲ��֣���ÿ1��Ԫ��ȡ20��Ԫ�ĳ���ģռ�÷ѡ�
                         *
                         * dep*5/10000
                         * plan*(5-0)*5/10000+(dep-plan*5%)*10/10000
                         * plan*(5-0)*5/10000+plan*(10-5)*10/10000+(dep-plan*10%)*15/10000
                         * plan*(5-0)*5/10000+plan*(10-5)*10/10000+plan*(15-10)*15/10000+(dep-plan*15)*20/10000
                         */
                        //ratio > 0 && ratio < 0.05 || ratio == 0.05
                        if (
                                ratio.compareTo(feijimoyuemoChaochu1Min) == 1
                                        && ratio.compareTo(feijimoyuemoChaochu1Max) == -1
                                        || ratio.compareTo(feijimoyuemoChaochu1Max) == 0
                        ) {
//                            punishMoney = (float) (diff * 0.0005);
                            punishMoney = departure.multiply(feijimoyuemoChaochu1Interest);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > 0.05 && ratio < 0.10 || ratio == 0.10
                        } else if (
                                ratio.compareTo(feijimoyuemoChaochu2Min) == 1
                                        && ratio.compareTo(feijimoyuemoChaochu2Max) == -1
                                        || ratio.compareTo(feijimoyuemoChaochu2Max) == 0
                        ) {
                            // plan*(5-0)*5/10000+(dep-plan*5%)*10/10000
                            BigDecimal a = plan.multiply(feijimoyuemoChaochu1Max.subtract(feijimoyuemoChaochu1Min)).multiply(feijimoyuemoChaochu1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(feijimoyuemoChaochu2Min));
                            BigDecimal c = b.multiply(feijimoyuemoChaochu2Interest);
                            punishMoney = a.add(c);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > 0.10 && ratio < 0.15 || ratio == 0.15
                        } else if (
                                ratio.compareTo(feijimoyuemoChaochu3Min) == 1
                                        && ratio.compareTo(feijimoyuemoChaochu3Max) == -1
                                        || ratio.compareTo(feijimoyuemoChaochu3Max) == 0
                        ) {
                            //plan*(5-0)*5/10000+plan*(10-5)*10/10000+(dep-plan*10%)*15/10000
                            BigDecimal a = plan.multiply(feijimoyuemoChaochu1Max.subtract(feijimoyuemoChaochu1Min)).multiply(feijimoyuemoChaochu1Interest);
                            BigDecimal b = plan.multiply(feijimoyuemoChaochu2Max.subtract(feijimoyuemoChaochu2Min)).multiply(feijimoyuemoChaochu2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(feijimoyuemoChaochu3Min));
                            BigDecimal d = c.multiply(feijimoyuemoChaochu3Interest);
                            punishMoney = a.add(b).add(d);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > 0.15
                        } else if (
                                ratio.compareTo(feijimoyuemoChaochu4Min) == 1
                        ) {
                            //plan*(5-0)*5/10000+plan*(10-5)*10/10000+plan*(15-10)*15/10000+(dep-plan*15)*20/10000
                            BigDecimal a = plan.multiply(feijimoyuemoChaochu1Max.subtract(feijimoyuemoChaochu1Min)).multiply(feijimoyuemoChaochu1Interest);
                            BigDecimal b = plan.multiply(feijimoyuemoChaochu2Max.subtract(feijimoyuemoChaochu2Min)).multiply(feijimoyuemoChaochu2Interest);
                            BigDecimal c = plan.multiply(feijimoyuemoChaochu3Max.subtract(feijimoyuemoChaochu3Min)).multiply(feijimoyuemoChaochu3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(feijimoyuemoChaochu4Min));
                            BigDecimal e = d.multiply(feijimoyuemoChaochu4Interest);
                            punishMoney = a.add(b).add(c).add(e);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                        }
                        /**
                         * ��ĩ�ƻ�����10��20%���ڣ���10%�����֣���ÿ1��Ԫ��ȡ3��Ԫ�ĳ���ģռ�÷ѣ�
                         * �ƻ�����20��30%����20%�����֣���ÿ1��Ԫ��ȡ5��Ԫ�ĳ���ģռ�÷ѣ�
                         * �ƻ�����30��40%����30%�����֣���ÿ1��Ԫ��ȡ7��Ԫ�ĳ���ģռ�÷ѣ�
                         * �ƻ�����40%���ϲ��֣���ÿ1��Ԫ��ȡ10��Ԫ�ĳ���ģռ�÷ѡ�
                         *
                         * (departure-plan*10%)*5%
                         * plan*(20%-10%)*5%+(departure-plan*20%)*7%
                         * plan*(20%-10%)*5%+plan*(30%-20%)*7%+(departure-plan*30%)*10%
                         * plan*(20%-10%)*5%+plan*(30%-20%)*10%+plan*(40%-30%)*10%+(departure-plan*40%)*10%
                         */
                        //�Ǽ�ĩ��ĩ����
                        //ratio > -0.20 && ratio < -0.10 || ratio == -0.10

                        else if (
                                ratio.compareTo(feijimoyuemoXianzhi1Max.negate()) == 1
                                        && ratio.compareTo(feijimoyuemoXianzhi1Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuemoXianzhi1Min.negate()) == 0
                        ) {
//                            (departure-plan*10%)*5%
                            BigDecimal a = departure.subtract(plan.multiply(feijimoyuemoXianzhi1Min));
                            punishMoney = a.multiply(feijimoyuemoXianzhi1Interest);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > -0.30 && ratio < -0.20 || ratio == -0.20
                        } else if (
                                ratio.compareTo(feijimoyuemoXianzhi2Max.negate()) == 1
                                        && ratio.compareTo(feijimoyuemoXianzhi2Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuemoXianzhi2Min.negate()) == 0
                        ) {
//                            plan*(20%-10%)*5%+(departure-plan*20%)*7%
                            BigDecimal a = plan.multiply(feijimoyuemoXianzhi1Max.subtract(feijimoyuemoXianzhi1Min)).multiply(feijimoyuemoXianzhi1Interest);
                            BigDecimal b = departure.subtract(plan.multiply(feijimoyuemoXianzhi2Min));
                            BigDecimal c = b.multiply(feijimoyuemoXianzhi2Interest);
                            punishMoney = a.add(c);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio > -0.40 && ratio < -0.30 || ratio == -0.30
                        } else if (
                                ratio.compareTo(feijimoyuemoXianzhi3Max.negate()) == 1
                                        && ratio.compareTo(feijimoyuemoXianzhi3Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuemoXianzhi3Min.negate()) == 0
                        ) {
//                            plan*(20%-10%)*5%+plan*(30%-20%)*7%+(departure-plan*30%)*10%
                            BigDecimal a = plan.multiply(feijimoyuemoXianzhi1Max.subtract(feijimoyuemoXianzhi1Min)).multiply(feijimoyuemoXianzhi1Interest);
                            BigDecimal b = plan.multiply(feijimoyuemoXianzhi2Max.subtract(feijimoyuemoXianzhi2Min)).multiply(feijimoyuemoXianzhi2Interest);
                            BigDecimal c = departure.subtract(plan.multiply(feijimoyuemoXianzhi3Min));
                            BigDecimal d = c.multiply(feijimoyuemoXianzhi3Interest);
                            punishMoney = a.add(b).add(d);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                            //ratio < -0.40 || ratio == -0.40
                        } else if (
                                ratio.compareTo(feijimoyuemoXianzhi4Min.negate()) == -1
                                        || ratio.compareTo(feijimoyuemoXianzhi4Min.negate()) == 0
                        ) {
//                            plan*(20%-10%)*5%+plan*(30%-20%)*10%+plan*(40%-30%)*10%+(departure-plan*40%)*10%
                            BigDecimal a = plan.multiply(feijimoyuemoXianzhi1Max.subtract(feijimoyuemoXianzhi1Min)).multiply(feijimoyuemoXianzhi1Interest);
                            BigDecimal b = plan.multiply(feijimoyuemoXianzhi2Max.subtract(feijimoyuemoXianzhi2Min)).multiply(feijimoyuemoXianzhi2Interest);
                            BigDecimal c = plan.multiply(feijimoyuemoXianzhi3Max.subtract(feijimoyuemoXianzhi3Min)).multiply(feijimoyuemoXianzhi3Interest);
                            BigDecimal d = departure.subtract(plan.multiply(feijimoyuemoXianzhi4Min));
                            BigDecimal e = d.multiply(feijimoyuemoXianzhi4Interest);
                            punishMoney = a.add(b).add(c).add(e);
                            if (leftDay + 1 == punishDay - z) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return punishMoney.setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}


