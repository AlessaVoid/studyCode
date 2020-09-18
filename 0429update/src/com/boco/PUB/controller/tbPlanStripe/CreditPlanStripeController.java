package com.boco.PUB.controller.tbPlanStripe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.creditPlan.CreditPlanServiceStripe;
import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.global.Dic;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 录入信贷条线
 * @Author zhuhongjiang
 * @Date 2019/11/29 下午2:09
 **/
@Controller
@RequestMapping("/creditPlanStripe")
public class CreditPlanStripeController extends BaseController {

    @Autowired
    private CreditPlanServiceStripe creditPlanServiceStripe;
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

    /**
     * 列表页
     * @Author zhuhongjiang
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanIndex")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-03-05", funName = "条线计划列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanIndex";
    }

    /**
     * 列表页数据
     * @Author zhuhongjiang
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanData")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-03-05", funName = "加载条线计划列表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> data = null;
        try{
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
            //搜索条件
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("planMonth", request.getParameter("planMonth"));
            paramMap.put("planOrgan", getSessionOrgan().getThiscode());
            paramMap.put("planStatus", TbReqDetail.STATE_NEW);
            paramMap.put("planType", TbPlan.STRIPE);
            paramMap.put("sort", sort);

            setPageParam();
            data = creditPlanServiceStripe.selectListByPage(paramMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * 初始化月份
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findTradeParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "加载列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody String findTradeParam(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPlanTime", method = RequestMethod.POST)
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "加载列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody String getPlanTime(HttpServletRequest request) throws Exception {

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
     * @param planId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCreditPlan")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01-03", funName = "删除条线计划", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody String deleteCreditPlan(String planId) throws Exception {
        PlainResult<String> result = creditPlanServiceStripe.deleteCreditPlan(planId);
        return JSON.toJSONString(result);
    }

    /**
     * 新增页
     * @return
     * @throws Exception
     */
    @RequestMapping("/toAddCreditPlan")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "条线计划新增页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAddCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();
        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("organlevel", organlevel);
        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanAdd";
    }

    /**
     * 新增条线计划
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanAdd", method = RequestMethod.POST)
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "新增条线计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanAdd(HttpServletRequest request) throws Exception {
        //当前登录人
        String operCode = getSessionOperInfo().getOperCode();
        //当前登录机构
        String organCode = getSessionOrgan().getThiscode();
        //当前登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();
        //上级机构
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanServiceStripe.addCreditPlan(request, operCode, organCode, TbPlan.STRIPE,organlevel,uporgan);
        return JSON.toJSONString(result);
    }

    /**
     * 详情页
     * @return
     * @throws Exception
     */
    @RequestMapping("/toDetailCreditPlan")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "条线计划详情页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //查询条线计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装条线计划详情map
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

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);

        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanDetail";
    }

    /**
     * 详情页数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanDetailData")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "条线计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanDetailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpdateCreditPlan")
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "条线计划修改页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //查询条线计划
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //组装条线计划详情map
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

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);


        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);
        request.setAttribute("organlevel", organlevel);

        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanUpdate";
    }

    /**
     * 修改条线计划
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanUpdate", method = RequestMethod.POST)
    @SystemLog(tradeName = "条线计划", funCode = "PUB-07-01", funName = "修改条线计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //当前登录人
        String operCode = getSessionOperInfo().getOperCode();
        //当前登录机构
        String organCode = getSessionOrgan().getThiscode();
        //当前登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();
        //上级机构
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanServiceStripe.updateCreditPlan(request, operCode, organCode,organlevel,uporgan);
        return JSON.toJSONString(result);
    }


    //报表录入条线计划页面
    @RequestMapping(value = "/toEnterReportPlan")
    @SystemLog(tradeName = "报表录入条线计划页面", funCode = "PUB-07-01-07", funName = "报表录入条线计划页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public  String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanEnterReport";
    }

    //报表录入条线计划
    @ResponseBody
    @RequestMapping(value = "/enterReportPlanByMonth",method = RequestMethod.POST)
    @SystemLog(tradeName = "报表录入条线计划", funCode = "PUB-07-01-07", funName = "报表录入条线计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
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


            resultMap = tbPlanService.enterReportPlanStripeByMonth(file, operCode, organCode, request,organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "录入失败,请检查!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();

    }


    //查看条线计划详情页面
    @RequestMapping(value = "/toPlanDetail")
    @SystemLog(tradeName = "查看条线计划详情页面", funCode = "PUB-03-01", funName = "查看条线计划详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public  String toPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();

        //设置查询月份
        String planMonth = request.getParameter("planMonth");
        //查询月份
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(planMonth);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        tbPlan.setPlanType(TbPlan.STRIPE);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        tbPlan.setSortColumn("plan_month desc");
        List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

        HashMap<String, Integer> planUnitMap = new HashMap<>();
        for (TbPlan plan : tbPlans) {
            planUnitMap.put(plan.getPlanMonth(), plan.getPlanUnit());
        }

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("organCode", getSessionOrgan().getThiscode());
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectAllStripe(paramMap);

        HashMap<String, Object> planMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
            Integer planUnit = planUnitMap.get(tbPlanDetail.getPdMonth());
            if (planUnit != null && planUnit == 2) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
            }
            planMap.put(tbPlanDetail.getPdMonth() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
        }



        //查询贷种组合
        String opercode = getSessionOperInfo().getOperCode();
        HashMap<String, Object> map = new HashMap<>();
        map.put("opercode", opercode);
        List<Map<String, Object>> combList = loanCombService.selectCombByOpercode(map);

        setAttribute("tbPlans", tbPlans);
        setAttribute("combList", combList);
        setAttribute("planMap", planMap);

        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanDetailOrgan";
    }

    //判断该月份是否存在计划
    @ResponseBody
    @RequestMapping(value = "/creditPlanJudgeMonth")
    @SystemLog(tradeName = "判断该月份是否存在计划", funCode = "PUB-03-01", funName = "判断该月份是否存在计划", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        String organlevel = getSessionOrgan().getOrganlevel();
        PlainResult<String> result  = creditPlanServiceStripe.creditPlanJudgeMonth(organlevel,request,getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }

    //判断该月份是否存在计划--导入页面
    @ResponseBody
    @RequestMapping(value = "/creditPlanEnterJudgeMonth")
    @SystemLog(tradeName = "判断该月份是否存在计划--导入页面", funCode = "PUB-03-01", funName = "判断该月份是否存在计划--导入页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanEnterJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        PlainResult<String> result  = creditPlanServiceStripe.creditPlanEnterJudgeMonth(request,getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }

    //导出计划模板
    @RequestMapping(value = "/downloadPlanTemplate")
    @SystemLog(tradeName = "导出计划模板", funCode = "PUB-03-05-07", funName = "导出计划模板", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        //获取登录机构级别
        String organlevel = getSessionOrgan().getOrganlevel();

        tbPlanService.downloadPlanTemplate(request, type,response, organlevel);
        // return response;
    }
}
