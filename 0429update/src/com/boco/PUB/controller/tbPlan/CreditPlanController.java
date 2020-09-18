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
 * 录入信贷计划
 *
 * @Author zhuhongjiang
 * @Date 2019/11/29 下午2:09
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
     * 列表页
     *
     * @return
     * @throws Exception
     * @Author zhuhongjiang
     */
    @RequestMapping("/creditPlanIndex")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanIndex";
    }

    /**
     * 列表页数据
     *
     * @return
     * @throws Exception
     * @Author zhuhongjiang
     */
    @RequestMapping("/creditPlanData")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "加载信贷计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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
            //搜索条件
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
     * 初始化月份
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findTradeParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "加载列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String findTradeParam(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //获取当前月份
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int dateNum = Integer.parseInt(sdf.format(date));

        //获取登录机构级别 0-总行  1-一级分行
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
     * 加载详情初始数据   本月计划净增量 管控模式
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPlanTime", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "加载列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String getPlanTime(HttpServletRequest request) throws Exception {

        //获取登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();

        String planMonth = request.getParameter("planMonth");
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(planMonth);
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        listObj.put("startTime", tbPunishParamList.get(0).getParamPlanStart());
        listObj.put("endTime", tbPunishParamList.get(0).getParamPlanEnd());
        listObj.put("paramMode", tbPunishParamList.get(0).getParamMode());
        //不同机构获取不同计划净增量
        if ("0".equals(organlevel)) {
            //总行直接取时间计划的净增量
            // listObj.put("increment", tbPunishParamList.get(0).getParamLineIncrement().divide(new BigDecimal("10000")));
            listObj.put("increment", 0);
        } else if ("1".equals(organlevel)) {
            //分行取计划定制的净增量
            //获取登录机构号的父机构号
            String uporgan = getSessionOrgan().getUporgan();
            //查询计划
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOrgan(uporgan);
            tbPlan.setPlanType(TbPlan.PLAN);
            List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

            if (tbPlans == null || tbPlans.size() == 0) {
                listObj.put("increment", 0);
            } else {
                //查询计划详情
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
     * 删除
     *
     * @param planId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCreditPlan")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05-03", funName = "删除信贷计划", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String deleteCreditPlan(String planId) throws Exception {
        PlainResult<String> result = creditPlanService.deleteCreditPlan(planId);
        return JSON.toJSONString(result);
    }


    /**
     * 新增页-选择贷种页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toAddCreditPlanBefore")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划新增页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAddCreditPlanBefore(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanAddBefore";
    }

    /**
     * 新增页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toAddCreditPlan")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划新增页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAddCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别
        String combLevelStr = getParameter("combLevel")==null?"1":getParameter("combLevel");
        int combLevel = 0;
        try {
            combLevel = Integer.valueOf(combLevelStr).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1== combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //查询机构
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("organlevel", organlevel);
        request.setAttribute("combLevel", combLevel);

        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanAdd";
    }


    /**
     * 获取需求列表
     * <p>
     * param planMonth
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailData")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB-03", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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
            //查询机构
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
        map1.put("name", "计划分配比例");
        map1.put("code", "planWeight");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "计划分配金额");
        map2.put("code", "planAmount");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "需求金额");
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
        map4.put("name", "存贷联动类占比");
        map4.put("code", "depositWeight");
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "结构优化类占比");
        map5.put("code", "structWeight");
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "市场竞争类占比");
        map6.put("code", "marketWeight");
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "价值提升类占比");
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
     * 获取需求列表
     * <p>
     * param planMonth
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCalculatelData")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB-03", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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
            //查询机构
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
        map1.put("name", "计划分配比例");
        map1.put("code", "planWeight");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "计划分配金额");
        map2.put("code", "planAmount");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "需求金额");
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
        map4.put("name", "存贷联动类占比");
        map4.put("code", "depositWeight");
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "结构优化类占比");
        map5.put("code", "structWeight");
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "市场竞争类占比");
        map6.put("code", "marketWeight");
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "价值提升类占比");
        map7.put("code", "benefitWeight");


        combAmountNameList.add(map4);
        combAmountNameList.add(map5);
        combAmountNameList.add(map6);
        combAmountNameList.add(map7);
//        setAttribute("combAmountNameList", combAmountNameList);
        return JSON.toJSONString(resultMap);
    }


    /**
     * 新增信贷计划
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanAdd", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "新增信贷计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanAdd(HttpServletRequest request) throws Exception {
        //当前登录人
        String operCode = getSessionOperInfo().getOperCode();
        //当前登录机构
        String organCode = getSessionOrgan().getThiscode();
        //当前登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();
        //上级机构
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanService.addCreditPlan(request, operCode, organCode, TbPlan.PLAN, organlevel,uporgan);
        return JSON.toJSONString(result);
    }

    /**
     * 详情页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toDetailCreditPlan")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划详情页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //查询信贷计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装信贷计划详情map
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


        //查询管控模式
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();


        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //查询机构
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
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanDetailData")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //查询计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //查询计划详情
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
     * 修改页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpdateCreditPlan")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划修改页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");
        //查询信贷计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装信贷计划详情map
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

        //查询管控模式
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }


        //查询机构
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
     * 修改信贷计划
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanUpdate", method = RequestMethod.POST)
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "修改信贷计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //当前登录人
        String operCode = getSessionOperInfo().getOperCode();
        //当前登录机构
        String organCode = getSessionOrgan().getThiscode();
        //当前登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();
        //上级机构
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanService.updateCreditPlan(request, operCode, organCode, organlevel,uporgan);
        return JSON.toJSONString(result);
    }


    //报表录入信贷计划页面
    @RequestMapping(value = "/toEnterReportPlan")
    @SystemLog(tradeName = "报表录入信贷计划页面", funCode = "PUB-03-05-07", funName = "报表录入信贷计划页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanEnterReport";
    }

    //报表录入信贷计划
    @ResponseBody
    @RequestMapping(value = "/enterReportPlanByMonth", method = RequestMethod.POST)
    @SystemLog(tradeName = "报表录入信贷计划", funCode = "PUB-03-05-07", funName = "报表录入信贷计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //当前登录人
            String operCode = getSessionOperInfo().getOperCode();
            //当前登录机构
            String organCode = getSessionOrgan().getThiscode();
            //当前登录机构级别
            String organlevel = getSessionOrgan().getOrganlevel();
            //上级机构
            String uporgan = getSessionOrgan().getUporgan();

            resultMap = tbPlanService.enterReportPlanByMonth(file, operCode, organCode, request, organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "录入失败,请检查!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }


    //分行查看信贷计划详情列表页
    @RequestMapping(value = "/toPlanDetail")
    @SystemLog(tradeName = "分行查看信贷计划详情列表页", funCode = "PUB-03-01", funName = "分行查看信贷计划详情列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanDetailOrganIndex";
    }

    // 分行查看信贷计划详情列表页数据
    @RequestMapping(value = "/organPlanDetail")
    @SystemLog(tradeName = "分行查看信贷计划详情列表页数据", funCode = "PUB-03-01", funName = "分行查看信贷计划详情列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String organPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();
        List<Map<String, Object>> data = null;
        try {
            //月份
            String planMonth = request.getParameter("planMonth");

            String columnSort = "month desc";
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if (sort != null) {
                sort = sort + " " + direction;
                columnSort =sort;
            }

            //搜索条件
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

    //分行查看信贷计划详情页面
    @RequestMapping(value = "/toOrganPlanDetail")
    @SystemLog(tradeName = "分行查看信贷计划详情页面", funCode = "PUB-03-01", funName = "分行查看信贷计划详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toOrganPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();
        String planId = request.getParameter("planId");

        //查询信贷计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装信贷计划详情map
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

        //查询管控模式
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        int combLevel = plan.getCombLevel();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        if (1==combLevel) {
            combList = loanCombService.selectComb(combMap);
        } else if (2==combLevel) {
            combList = loanCombService.selectCombOfBind(combMap);
        }

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);
        return basePath + "/PUB/tbPlan/tbPlanList/creditPlanDetailOrgan";
    }

    //判断该月份是否存在计划
    @ResponseBody
    @RequestMapping(value = "/creditPlanJudgeMonth")
    @SystemLog(tradeName = "判断该月份是否存在计划", funCode = "PUB-03", funName = "判断该月份是否存在计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        String organlevel = getSessionOrgan().getOrganlevel();
        //判断该月是否已经存在计划
        PlainResult<String> result = creditPlanService.creditPlanJudgeMonth(organlevel,request, getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }

    //判断该月份是否存在计划--导入页面
    @ResponseBody
    @RequestMapping(value = "/creditPlanEnterJudgeMonth")
    @SystemLog(tradeName = "判断该月份是否存在计划--导入页面", funCode = "PUB-03-01", funName = "判断该月份是否存在计划--导入页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanEnterJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        PlainResult<String> result = creditPlanService.creditPlanEnterJudgeMonth(request, getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }


    //导出计划模板
    @RequestMapping(value = "/downloadPlanTemplate")
    @SystemLog(tradeName = "导出计划模板", funCode = "PUB-03-05-07", funName = "导出计划模板", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String type = request.getParameter("type");
            //获取登录机构级别
            String organlevel = getSessionOrgan().getOrganlevel();

            tbPlanService.downloadPlanTemplate(request,type, response, organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
