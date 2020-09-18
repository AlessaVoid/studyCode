package com.boco.PUB.service.creditPlan.impl;

import com.boco.PUB.service.creditPlan.CreditPlanServiceStripe;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.mapper.LoanPlanDetailMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.mapper.TbPlanMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �Ŵ��ƻ�Service
 * @Author zhuhongjiang
 * @Date 2019/11/29 ����2:23
 **/
@Service
public class CreditPlanServiceStripeImpl extends GenericService<TbPlan, String> implements CreditPlanServiceStripe {

    public static Logger logger = Logger.getLogger(CreditPlanServiceStripeImpl.class);

    @Autowired
    private TbPlanMapper tbPlanMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private LoanPlanDetailMapper loanPlanDetailMapper;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private TbPlanService tbPlanService;

    /**
     * ¼���Ŵ��ƻ�-��ҳ��ѯ�б�ҳ����
     * @Author zhuhongjiang
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) {

        List<Map<String, Object>> maps = tbPlanMapper.selectListByPage(paramMap);
        for (Map<String, Object> map : maps) {
            map.put("planincrement", new BigDecimal(map.get("planincrement").toString()).divide(new BigDecimal("10000")));
            map.put("planrealincrement", new BigDecimal(map.get("planrealincrement").toString()).divide(new BigDecimal("10000")));
        }

        return maps;
    }

    /**
     * ɾ���Ŵ��ƻ�
     * @Author zhuhongjiang
     * @param planId
     * @return
     */
    @Override
    public PlainResult<String> deleteCreditPlan(String planId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            tbPlanMapper.deleteLoanPlanInfo(planId);
            loanPlanDetailMapper.deleteLoanPlanDetail(planId);
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete credit plan.");
        }
        return result.success("delete", "delete credit plan success.");
    }



    /**
     * �����Ŵ��ƻ�
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param uporgan
     * @return
     */
    @Override
    public PlainResult<String> addCreditPlan(HttpServletRequest request, String operCode, String organCode, Integer planType, String organlevel, String uporgan) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planDetailStr = request.getParameter("planDetail");
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String amountCount = request.getParameter("amountCount");

            //У�������ֶηǿ�
            if(StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit) || StringUtils.isEmpty(planDetailStr)|| StringUtils.isEmpty(amountCount)){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "����ʧ�ܣ���������Ϊ��");
            }

            //У��Ψһ��
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(planType);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·����߼ƻ��Ѵ���");
            }
            //���� ���ߺͼƻ�ֻ���ƶ�һ��
            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.PLAN);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·��Ѿ��ƶ������ƻ����������ƶ����߼ƻ�");
                }
            }

            //�����ƻ�
            String planId = IDGeneratorUtils.getSequence();
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanId(planId);
            tbPlan.setPlanOrgan(organCode);
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOper(operCode);
            tbPlan.setPlanStatus(LoanStateEnums.STATE_NEW.status);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            BigDecimal planRealIncrement = new BigDecimal(StringUtils.isEmpty(amountCount) ? "0" : amountCount);
            if ("2".equals(planUnit)) {
                planRealIncrement = planRealIncrement.multiply(new BigDecimal("10000"));
            }
            tbPlan.setPlanIncrement(planIncrement);
            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlan.setPlanCreateTime(BocoUtils.getTime());
            tbPlan.setPlanUnit(Integer.valueOf(planUnit));
            tbPlan.setPlanUpdater(operCode);
            tbPlan.setPlanUpdateTime(BocoUtils.getTime());
            tbPlan.setPlanType(TbPlan.STRIPE);
            tbPlan.setCombLevel(TbPlan.COMB_TWO);

            //�����ƻ���ϸ
            List<TbPlanDetail> tbPlanDetailList = buildTbPlanDetail(planDetailStr, tbPlan);

            //һ�ָ������ƶ��ƻ����ƶ��������ڼƻ������������ƶ�
            if ("1".equals(organlevel)) {

                // һ�ִ��ּ���
                int combLevelOne = tbPlan.getCombLevel();
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                }

                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "�ƶ�������Ϊ" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ���ƻ�������Ϊ"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ�������");
                    }
                }


            }
            tbPlanMapper.insertEntity(tbPlan);

            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

        } catch (Exception e) {
            e.printStackTrace();
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "����ʧ��");
        }
        return result.success("add", "�����ɹ�");
    }

    /**
     * �޸��Ŵ��ƻ�
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param uporgan
     * @return
     */
    @Override
    public PlainResult<String> updateCreditPlan(HttpServletRequest request, String operCode, String organCode, String organlevel, String uporgan) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planDetailStr = request.getParameter("planDetail");
            String planId = request.getParameter("planId");
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String amountCount = request.getParameter("amountCount");

            //У�������ֶηǿ�
            if(StringUtils.isEmpty(amountCount)||StringUtils.isEmpty(planId) || StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit) || StringUtils.isEmpty(planDetailStr)){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update credit plan param not to be null");
            }

            //У��Ψһ��(�ų�����)
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                TbPlan tbPlanCheck = tbPlanListCheck.get(0);
                if(!planId.equals(tbPlanCheck.getPlanId())){
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "update credit plan month is existed.");
                }
            }

            //�����ƻ�
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanId(planId);
            tbPlan.setPlanUpdater(operCode);
            tbPlan.setPlanUpdateTime(BocoUtils.getTime());
            tbPlan.setPlanCreateTime(BocoUtils.getTime());
            tbPlan.setPlanUnit(Integer.parseInt(planUnit));
            tbPlan.setPlanMonth(planMonth);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            BigDecimal planRealIncrement = new BigDecimal(StringUtils.isEmpty(amountCount) ? "0" : amountCount);
            if ("2".equals(planUnit)) {
                planRealIncrement = planRealIncrement.multiply(new BigDecimal("10000"));
            }
            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlan.setCombLevel(TbPlan.COMB_TWO);



            //�����ƻ���ϸ
            List<TbPlanDetail> tbPlanDetailList = buildTbPlanDetail(planDetailStr, tbPlan);

            //һ�ָ������ƶ��ƻ����ƶ��������ڼƻ������������ƶ�
            if ("1".equals(organlevel)) {

                // һ�ִ��ּ���
                int combLevelOne = tbPlan.getCombLevel();
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                }

                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "�ƶ�������Ϊ" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ���ƻ�������Ϊ"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ�������");
                    }
                }
            }

            tbPlanMapper.updateByPK(tbPlan);

            //����������ϸ
            tbPlanDetailMapper.deleteLoanPlanDetail(planId);
            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        return result.success("add", "add credit plan success.");
    }

    /*�жϸ��·��Ƿ���ڼƻ�*/
    @Override
    public PlainResult<String> creditPlanJudgeMonth(String organlevel, HttpServletRequest request, String organCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planMonth = request.getParameter("planMonth");
            //У�������ֶηǿ�
            if(StringUtils.isEmpty(planMonth) ){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "��ѡ���·�");
            }

            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·����߼ƻ��Ѵ���");
            }

            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.PLAN);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·��Ѿ��ƶ������ƻ����������ƶ����߼ƻ�");
                }
            }

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        return result.success("success", "add credit plan success.");

    }

    /*�жϸ��·��Ƿ���ڼƻ� -- ����ҳ��*/
    @Override
    public PlainResult<String> creditPlanEnterJudgeMonth(HttpServletRequest request,String organCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planMonth = request.getParameter("planMonth");
            //У�������ֶηǿ�
            if(StringUtils.isEmpty(planMonth) ){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "��ѡ���·�");
            }

            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                if (TbReqDetail.STATE_DISMISSED == tbPlanListCheck.get(0).getPlanStatus().intValue()) {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·ݼƻ��ѱ����أ����Ե���");
                } else if (TbReqDetail.STATE_NEW == tbPlanListCheck.get(0).getPlanStatus().intValue()) {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·ݼƻ��Ѿ���д�����Ե���");
                } else {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "�����·ݼƻ����������У����ܵ���");
                }
            }

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        return result.success("success", "add credit plan success.");

    }

    /**
     * �����ƻ���ϸ����
     * @param planDetailStr
     * @param tbPlan
     * @return
     */
    public List<TbPlanDetail>  buildTbPlanDetail(String planDetailStr, TbPlan tbPlan){
        List<TbPlanDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = planDetailStr.split("&");
        for(int i = 0; i < planDetailArr.length; i++){
            String[] planDetailArr2 = planDetailArr[i].split("=");
            String[] planDetailArr3 = planDetailArr2[0].split("_");

            //eg: 11005293_x001=123 (����_����=���&����_����=���)
            String pdAmount = planDetailArr2[1];
            String pdOrgan = planDetailArr3[0];
            String pdLoanType = planDetailArr3[1];

           String planDetailId = IDGeneratorUtils.getSequence();
            TbPlanDetail tbPlanDetail = new TbPlanDetail();
            tbPlanDetail.setPdId(planDetailId);
            tbPlanDetail.setPdRefId(tbPlan.getPlanId());
            tbPlanDetail.setPdOrgan(pdOrgan);
            tbPlanDetail.setPdMonth(tbPlan.getPlanMonth());
            tbPlanDetail.setPdLoanType(pdLoanType);
            BigDecimal amount = new BigDecimal(pdAmount);
            //��Ԫת��Ϊ��Ԫ
            if (tbPlan.getPlanUnit() == 2) {
                amount = amount.multiply(new BigDecimal("10000"));
            }
            tbPlanDetail.setPdAmount(amount);
            tbPlanDetail.setPdUnit(tbPlan.getPlanUnit());
            tbPlanDetail.setPdCreateTime(BocoUtils.getTime());
            tbPlanDetail.setPdUpdateTime(BocoUtils.getTime());
            tbPlanDetailList.add(tbPlanDetail);
        }
        return tbPlanDetailList;
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
