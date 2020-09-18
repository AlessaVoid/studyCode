package com.boco.PUB.controller.tbPlan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.TbReqDetailDO;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.creditPlan.CreditPlanService;
import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneProportionHistoryService;
import com.boco.PUB.service.tbCalculateOne.TbCalculateOneResultService;
import com.boco.PUB.service.tbOrganRateParam.OrganRateParamCalculateService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateParamService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.TONY.biz.loancomb.POJO.DTO.combbase.LoanCombDTOV2;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ¼���Ŵ��ƻ�
 *
 * @Author zhuhongjiang
 * @Date 2019/11/29 ����2:09
 **/
@Controller
@RequestMapping("/creditPlan")
public class CreditPlanController extends BaseController {
    @Autowired
    private ITbCalculateOneProportionHistoryService tbCalculateOneProportionHistoryService;
    @Autowired
    private CreditPlanService creditPlanService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailService tbPlanDetailService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateParamService tbOrganRateParamService;
    @Autowired
    private OrganRateParamCalculateService organRateParamCalculateService;
    @Autowired
    private TbCalculateOneResultService tbCalculateOneResultService;
    @Autowired
    private ITbReqDetailService tbReqDetailService;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private IFdOrganService organService;


    /**
     * �б�ҳ
     *
     * @return
     * @throws Exception
     * @Author zhuhongjiang
     */
    @RequestMapping("/creditPlanIndex")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ��б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanIndex";
    }

    /**
     * �б�ҳ����
     *
     * @return
     * @throws Exception
     * @Author zhuhongjiang
     */
    @RequestMapping("/creditPlanData")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�����Ŵ��ƻ��б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");

        if ("planmonth".equals(sort)) {
            sort = "plan_month";

        } else if ("planrealincrement".equals(sort)) {
            sort = "plan_real_increment";

        } else if ("plancreatetime".equals(sort)) {
            sort = "plan_create_time";

        } else if ("planoper".equals(sort)) {
            sort = "plan_oper";

        } else if ("planupdatetime".equals(sort)) {
            sort = "plan_update_time";
        }
        if (sort != null) {
            sort = sort + " " + direction;
        }


        List<Map<String, Object>> data = null;
        try {
            //��������
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("planMonth", request.getParameter("planMonth"));
            paramMap.put("planOrgan", getSessionOrgan().getThiscode());
            paramMap.put("planStatus", TbReqDetail.STATE_NEW);
            paramMap.put("planType", TbPlan.PLAN);

            paramMap.put("sort", sort);
            setPageParam();
            data = creditPlanService.selectListByPage(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * ��ʼ���·�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findTradeParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�����б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String findTradeParam(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //��ȡ��ǰ�·�
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int dateNum = Integer.parseInt(sdf.format(date));

        //��ȡ��¼�������� 0-����  1-һ������
        String organlevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organlevel)) {
            TbTradeParam tbTradeParam = new TbTradeParam();
            List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);

            for (TbTradeParam tradeParam : tbPunishParamList) {
                JSONObject jsonObject = new JSONObject();
                if (dateNum <= Integer.parseInt(tradeParam.getParamPeriod())) {
                    jsonObject.put("value", tradeParam.getParamPeriod());
                    jsonObject.put("key", tradeParam.getParamPeriod());
                    jsonArray.add(jsonObject);
                }
            }

        } else if ("1".equals(organlevel)) {
            String uporgan = getSessionOrgan().getUporgan();
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanOrgan(uporgan);
            tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
            tbPlan.setPlanType(TbPlan.PLAN);
            tbPlan.setSortColumn("plan_month");
            List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

            for (TbPlan plan : tbPlans) {
                JSONObject jsonObject = new JSONObject();
                if (dateNum <= Integer.parseInt((plan.getPlanMonth()))) {
                    jsonObject.put("value", plan.getPlanMonth());
                    jsonObject.put("key", plan.getPlanMonth());
                    jsonArray.add(jsonObject);
                }
            }

        }

        listObj.put("list", jsonArray);
        return listObj.toString();

    }

    /**
     * ���������ʼ����   ���¼ƻ������� �ܿ�ģʽ
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPlanTime", method = RequestMethod.POST)
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�����б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String getPlanTime(HttpServletRequest request) throws Exception {

        //��ȡ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();

        String planMonth = request.getParameter("planMonth");
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(planMonth);
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        listObj.put("startTime", tbPunishParamList.get(0).getParamPlanStart());
        listObj.put("endTime", tbPunishParamList.get(0).getParamPlanEnd());
        listObj.put("paramMode", tbPunishParamList.get(0).getParamMode());
        //��ͬ������ȡ��ͬ�ƻ�������
        if ("0".equals(organlevel)) {
            //����ֱ��ȡʱ��ƻ��ľ�����
            // listObj.put("increment", tbPunishParamList.get(0).getParamLineIncrement().divide(new BigDecimal("10000")));
            listObj.put("increment", 0);
        } else if ("1".equals(organlevel)) {
            //����ȡ�ƻ����Ƶľ�����
            //��ȡ��¼�����ŵĸ�������
            String uporgan = getSessionOrgan().getUporgan();
            //��ѯ�ƻ�
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOrgan(uporgan);
            tbPlan.setPlanType(TbPlan.PLAN);
            List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

            if (tbPlans == null || tbPlans.size() == 0) {
                listObj.put("increment", 0);
            } else {
                //��ѯ�ƻ�����
                TbPlanDetail tbPlanDetail = new TbPlanDetail();
                tbPlanDetail.setPdRefId(tbPlans.get(0).getPlanId());
                tbPlanDetail.setPdOrgan(getSessionOrgan().getThiscode());
                List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByAttr(tbPlanDetail);
                BigDecimal increment = new BigDecimal("0");
                for (TbPlanDetail planDetail : tbPlanDetails) {
                    increment = increment.add(planDetail.getPdAmount());
                }
                listObj.put("increment", increment.divide(new BigDecimal("10000")));
            }
        }
        return listObj.toString();
    }

    /**
     * ɾ��
     *
     * @param planId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCreditPlan")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05-03", funName = "ɾ���Ŵ��ƻ�", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String deleteCreditPlan(String planId) throws Exception {
        PlainResult<String> result = creditPlanService.deleteCreditPlan(planId);
        return JSON.toJSONString(result);
    }


    /**
     * ����ҳ-ѡ�����ҳ��
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toAddCreditPlanBefore")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ�����ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAddCreditPlanBefore(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanAddBefore";
    }

    /**
     * ����ҳ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toAddCreditPlan")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ�����ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAddCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ������ϼ���
        String combLevelStr = getParameter("combLevel")==null?"1":getParameter("combLevel");
        int combLevel = 0;
        try {
            combLevel = Integer.valueOf(combLevelStr).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1== combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //��ѯ����
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("organlevel", organlevel);
        request.setAttribute("combLevel", combLevel);

        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanAdd";
    }


    /**
     * ��ȡ�����б�
     * <p>
     * param planMonth
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailData")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB-03", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String getReqDetailData(String planMonth, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TbReqList searchTbReqList = new TbReqList();
        searchTbReqList.setReqMonth(planMonth);
        searchTbReqList.setReqType(0);
        searchTbReqList.setReqOrgan(getSessionOrgan().getThiscode());
        List<TbReqList> tbReqListList = tbReqListService.selectByAttr(searchTbReqList);
        List<TbReqDetailDO> tbLineReqDetailDTOS = new ArrayList<>();
        TbCalculateOneResult searchTbCalculateOneResult = new TbCalculateOneResult();
        searchTbCalculateOneResult.setType(BigDecimal.ONE);
        searchTbCalculateOneResult.setMonth(planMonth);
        List<TbCalculateOneResult> tbCalculateOneResultList = tbCalculateOneResultService.selectByAttr(searchTbCalculateOneResult);
        if (tbReqListList != null & tbReqListList.size() > 0) {
            //��ѯ����
            // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
            TbReqDetail searchTbReqDetail = new TbReqDetail();
            searchTbReqDetail.setReqdRefId(tbReqListList.get(0).getReqId());
            searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
            List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);
            if (tbReqDetailList != null && tbReqDetailList.size() > 0) {
                Map<String, BigDecimal> map = new HashMap<>();
                for (TbReqDetail tbReqDetail : tbReqDetailList) {
                    String organCode = tbReqDetail.getReqdOrgan();
                    BigDecimal amount = map.get(organCode);
                    if (amount == null) {
                        amount = BigDecimal.ZERO;
                    }
                    amount = amount.add(tbReqDetail.getReqdReqnum());
                    map.put(organCode, amount);
                }
                for (Map<String, Object> map1 : organList) {
                    TbReqDetailDO tbReqDetailDO = new TbReqDetailDO();
                    tbReqDetailDO.setReqdOrgan(map1.get("organcode").toString());
                    tbReqDetailDO.setReqdReqAmount(map.get(map1.get("organcode")));
                    tbLineReqDetailDTOS.add(tbReqDetailDO);
                }
            }
            for (TbReqDetailDO tbReqDetailDO : tbLineReqDetailDTOS) {
                for (TbCalculateOneResult tbCalculateOneResult : tbCalculateOneResultList) {
                    if (tbReqDetailDO.getReqdOrgan().equals(tbCalculateOneResult.getOrgancode())) {
                        tbCalculateOneResult.setReqAmount(tbReqDetailDO.getReqdReqAmount());
                    }
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbCalculateOneResultList", tbCalculateOneResultList);
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel("1");
        searchOrgan.setUporgan(getSessionOrgan().getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
        List<Map<String, String>> organList = new ArrayList<>();
        for (FdOrgan fdOrgan : fdOrganList) {
            Map<String, String> organMap = new HashMap<>(2);
            organMap.put("organCode", fdOrgan.getThiscode());
            organMap.put("organName", fdOrgan.getOrganname());
            organList.add(organMap);
        }
        setAttribute("organList", organList);
        TbCalculateOneProportionHistory searchTbCalculateOneProportionHistory = new TbCalculateOneProportionHistory();
        searchTbCalculateOneProportionHistory.setMonth(planMonth);
        List<TbCalculateOneProportionHistory> tbCalculateOneProportionHistoryList = tbCalculateOneProportionHistoryService.selectByAttr(searchTbCalculateOneProportionHistory);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "�ƻ��������");
        map1.put("code", "planWeight");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "�ƻ�������");
        map2.put("code", "planAmount");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "������");
        map3.put("code", "reqAmount");
        combAmountNameList.add(map1);
        combAmountNameList.add(map2);
        combAmountNameList.add(map3);
        for (TbCalculateOneProportionHistory tbCalculateOneProportionHistory : tbCalculateOneProportionHistoryList) {
            Map<String, String> combMap = new HashMap<>(2);
            combMap.put("name", tbCalculateOneProportionHistory.getName());
            combMap.put("code", tbCalculateOneProportionHistory.getCode());
            combAmountNameList.add(combMap);
        }
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "���������ռ��");
        map4.put("code", "depositWeight");
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "�ṹ�Ż���ռ��");
        map5.put("code", "structWeight");
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "�г�������ռ��");
        map6.put("code", "marketWeight");
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "��ֵ������ռ��");
        map7.put("code", "benefitWeight");

        combAmountNameList.add(map4);
        combAmountNameList.add(map5);
        combAmountNameList.add(map6);
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("planMonth", planMonth);
        return basePath + "/PUB/tbPlan/tbPlanList/calculateResult";
    }


    /**
     * ��ȡ�����б�
     * <p>
     * param planMonth
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCalculatelData")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB-03", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String getCalculatelData(String planMonth, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbReqList searchTbReqList = new TbReqList();
        searchTbReqList.setReqMonth(planMonth);
        searchTbReqList.setReqType(0);
        searchTbReqList.setReqOrgan(getSessionOrgan().getThiscode());
        List<TbReqList> tbReqListList = tbReqListService.selectByAttr(searchTbReqList);
        List<TbReqDetailDO> tbLineReqDetailDTOS = new ArrayList<>();
        TbCalculateOneResult searchTbCalculateOneResult = new TbCalculateOneResult();
        searchTbCalculateOneResult.setType(BigDecimal.ONE);
        searchTbCalculateOneResult.setMonth(planMonth);
        List<TbCalculateOneResult> tbCalculateOneResultList = tbCalculateOneResultService.selectByAttr(searchTbCalculateOneResult);
        if (tbReqListList != null & tbReqListList.size() > 0) {
            //��ѯ����
            // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
            TbReqDetail searchTbReqDetail = new TbReqDetail();
            searchTbReqDetail.setReqdRefId(tbReqListList.get(0).getReqId());
            searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
            List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);
            if (tbReqDetailList != null && tbReqDetailList.size() > 0) {
                Map<String, BigDecimal> map = new HashMap<>();
                for (TbReqDetail tbReqDetail : tbReqDetailList) {
                    String organCode = tbReqDetail.getReqdOrgan();
                    BigDecimal amount = map.get(organCode);
                    if (amount == null) {
                        amount = BigDecimal.ZERO;
                    }
                    amount = amount.add(tbReqDetail.getReqdReqnum());
                    map.put(organCode, amount);
                }
                for (Map<String, Object> map1 : organList) {
                    TbReqDetailDO tbReqDetailDO = new TbReqDetailDO();
                    tbReqDetailDO.setReqdOrgan(map1.get("organcode").toString());
                    tbReqDetailDO.setReqdReqAmount(map.get(map1.get("organcode")));
                    tbLineReqDetailDTOS.add(tbReqDetailDO);
                }
            }
            for (TbReqDetailDO tbReqDetailDO : tbLineReqDetailDTOS) {
                for (TbCalculateOneResult tbCalculateOneResult : tbCalculateOneResultList) {
                    if (tbReqDetailDO.getReqdOrgan().equals(tbCalculateOneResult.getOrgancode())) {
                        tbCalculateOneResult.setReqAmount(tbReqDetailDO.getReqdReqAmount());
                    }
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbCalculateOneResultList", tbCalculateOneResultList);
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel("1");
        searchOrgan.setUporgan(getSessionOrgan().getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
        List<Map<String, String>> organList = new ArrayList<>();
        for (FdOrgan fdOrgan : fdOrganList) {
            Map<String, String> organMap = new HashMap<>(2);
            organMap.put("organCode", fdOrgan.getThiscode());
            organMap.put("organName", fdOrgan.getOrganname());
            organList.add(organMap);
            break;
        }
//        setAttribute("organList", organList);
        TbCalculateOneProportionHistory searchTbCalculateOneProportionHistory = new TbCalculateOneProportionHistory();
        searchTbCalculateOneProportionHistory.setMonth(planMonth);
        List<TbCalculateOneProportionHistory> tbCalculateOneProportionHistoryList = tbCalculateOneProportionHistoryService.selectByAttr(searchTbCalculateOneProportionHistory);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "�ƻ��������");
        map1.put("code", "planWeight");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "�ƻ�������");
        map2.put("code", "planAmount");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "������");
        map3.put("code", "reqAmount");
        combAmountNameList.add(map3);
        combAmountNameList.add(map1);
        combAmountNameList.add(map2);
        for (TbCalculateOneProportionHistory tbCalculateOneProportionHistory : tbCalculateOneProportionHistoryList) {
            Map<String, String> combMap = new HashMap<>(2);
            combMap.put("name", tbCalculateOneProportionHistory.getName());
            combMap.put("code", tbCalculateOneProportionHistory.getCode());
            combAmountNameList.add(combMap);
        }
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "���������ռ��");
        map4.put("code", "depositWeight");
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "�ṹ�Ż���ռ��");
        map5.put("code", "structWeight");
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "�г�������ռ��");
        map6.put("code", "marketWeight");
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "��ֵ������ռ��");
        map7.put("code", "benefitWeight");


        combAmountNameList.add(map4);
        combAmountNameList.add(map5);
        combAmountNameList.add(map6);
        combAmountNameList.add(map7);
//        setAttribute("combAmountNameList", combAmountNameList);
        return JSON.toJSONString(resultMap);
    }


    /**
     * �����Ŵ��ƻ�
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanAdd", method = RequestMethod.POST)
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�����Ŵ��ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanAdd(HttpServletRequest request) throws Exception {
        //��ǰ��¼��
        String operCode = getSessionOperInfo().getOperCode();
        //��ǰ��¼����
        String organCode = getSessionOrgan().getThiscode();
        //��ǰ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();
        //�ϼ�����
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanService.addCreditPlan(request, operCode, organCode, TbPlan.PLAN, organlevel,uporgan);
        return JSON.toJSONString(result);
    }

    /**
     * ����ҳ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toDetailCreditPlan")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ�����ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //��ѯ�Ŵ��ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ�Ŵ��ƻ�����map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }


        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();


        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //��ѯ����
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);

        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanDetail";
    }

    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanDetailData")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //��ѯ�ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��ѯ�ƻ�����
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(plan.getPlanId());
        List<TbPlanDetail> planDetails = tbPlanDetailService.selectByAttr(tbPlanDetail);
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail planDetail : planDetails) {
                planDetail.setPdAmount(planDetail.getPdAmount().divide(new BigDecimal("10000")));
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("plan", plan);
        resultMap.put("planDetails", planDetails);
        return JSON.toJSONString(resultMap);
    }

    /**
     * �޸�ҳ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpdateCreditPlan")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ��޸�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");
        //��ѯ�Ŵ��ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ�Ŵ��ƻ�����map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }

        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }


        //��ѯ����
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);
        request.setAttribute("organlevel", organlevel);

        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanUpdate";
    }

    /**
     * �޸��Ŵ��ƻ�
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanUpdate", method = RequestMethod.POST)
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�޸��Ŵ��ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //��ǰ��¼��
        String operCode = getSessionOperInfo().getOperCode();
        //��ǰ��¼����
        String organCode = getSessionOrgan().getThiscode();
        //��ǰ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();
        //�ϼ�����
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanService.updateCreditPlan(request, operCode, organCode, organlevel,uporgan);
        return JSON.toJSONString(result);
    }


    //����¼���Ŵ��ƻ�ҳ��
    @RequestMapping(value = "/toEnterReportPlan")
    @SystemLog(tradeName = "����¼���Ŵ��ƻ�ҳ��", funCode = "PUB-03-05-07", funName = "����¼���Ŵ��ƻ�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanEnterReport";
    }

    //����¼���Ŵ��ƻ�
    @ResponseBody
    @RequestMapping(value = "/enterReportPlanByMonth", method = RequestMethod.POST)
    @SystemLog(tradeName = "����¼���Ŵ��ƻ�", funCode = "PUB-03-05-07", funName = "����¼���Ŵ��ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //��ǰ��¼��
            String operCode = getSessionOperInfo().getOperCode();
            //��ǰ��¼����
            String organCode = getSessionOrgan().getThiscode();
            //��ǰ��¼��������
            String organlevel = getSessionOrgan().getOrganlevel();
            //�ϼ�����
            String uporgan = getSessionOrgan().getUporgan();

            resultMap = tbPlanService.enterReportPlanByMonth(file, operCode, organCode, request, organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "¼��ʧ��,����!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }


    //���в鿴�Ŵ��ƻ������б�ҳ
    @RequestMapping(value = "/toPlanDetail")
    @SystemLog(tradeName = "���в鿴�Ŵ��ƻ������б�ҳ", funCode = "PUB-03-01", funName = "���в鿴�Ŵ��ƻ������б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanDetailOrganIndex";
    }

    // ���в鿴�Ŵ��ƻ������б�ҳ����
    @RequestMapping(value = "/organPlanDetail")
    @SystemLog(tradeName = "���в鿴�Ŵ��ƻ������б�ҳ����", funCode = "PUB-03-01", funName = "���в鿴�Ŵ��ƻ������б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String organPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();
        List<Map<String, Object>> data = null;
        try {
            //�·�
            String planMonth = request.getParameter("planMonth");

            String columnSort = "month desc";
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if (sort != null) {
                sort = sort + " " + direction;
                columnSort =sort;
            }

            //��������
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("planMonth", planMonth);
            paramMap.put("planOrgan", getSessionOrgan().getThiscode());
            paramMap.put("upOrgan", getSessionOrgan().getUporgan());
            paramMap.put("planStatus", TbReqDetail.STATE_NEW);
            paramMap.put("planType", TbPlan.PLAN);
            paramMap.put("sort", columnSort);

            setPageParam();
            data = tbPlanService.selectLowOrganIncrement(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);

    }

    //���в鿴�Ŵ��ƻ�����ҳ��
    @RequestMapping(value = "/toOrganPlanDetail")
    @SystemLog(tradeName = "���в鿴�Ŵ��ƻ�����ҳ��", funCode = "PUB-03-01", funName = "���в鿴�Ŵ��ƻ�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toOrganPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();
        String planId = request.getParameter("planId");

        //��ѯ�Ŵ��ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ�Ŵ��ƻ�����map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }

        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanDetailOrgan";
    }

    //�жϸ��·��Ƿ���ڼƻ�
    @ResponseBody
    @RequestMapping(value = "/creditPlanJudgeMonth")
    @SystemLog(tradeName = "�жϸ��·��Ƿ���ڼƻ�", funCode = "PUB-03", funName = "�жϸ��·��Ƿ���ڼƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        String organlevel = getSessionOrgan().getOrganlevel();
        //�жϸ����Ƿ��Ѿ����ڼƻ�
        PlainResult<String> result = creditPlanService.creditPlanJudgeMonth(organlevel,request, getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }

    //�жϸ��·��Ƿ���ڼƻ�--����ҳ��
    @ResponseBody
    @RequestMapping(value = "/creditPlanEnterJudgeMonth")
    @SystemLog(tradeName = "�жϸ��·��Ƿ���ڼƻ�--����ҳ��", funCode = "PUB-03-01", funName = "�жϸ��·��Ƿ���ڼƻ�--����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanEnterJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        PlainResult<String> result = creditPlanService.creditPlanEnterJudgeMonth(request, getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }


    //�����ƻ�ģ��
    @RequestMapping(value = "/downloadPlanTemplate")
    @SystemLog(tradeName = "�����ƻ�ģ��", funCode = "PUB-03-05-07", funName = "�����ƻ�ģ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String type = request.getParameter("type");
            //��ȡ��¼��������
            String organlevel = getSessionOrgan().getOrganlevel();

            tbPlanService.downloadPlanTemplate(request,type, response, organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
