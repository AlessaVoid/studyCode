package com.boco.PUB.controller.tbPlanadjStripe;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjServiceStripe;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description:  条线计划调整 基础查询
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjStripe")
public class TbLoanPlanadjStripeController extends BaseController {

    @Autowired
    private TbPlanadjServiceStripe planadjServiceStripe;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private TbPlanadjService tbPlanadjService;


    @RequestMapping("/loanPlanadjIndexPage")
    @SystemLog(tradeName = "条线计划调整首页", funCode = "PUB-06-01", funName = "条线计划调整首页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanIndexPage() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustList/tbPlanAdjustList";
    }

    @RequestMapping("/loadAllLoanadjPlanInfo")
    @ResponseBody
    @SystemLog(tradeName = "加载条线计划调整列表", funCode = "PUB-06-01", funName = "加载条线计划调整列表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadAllLoanPlanInfo(HttpServletRequest request) throws Exception {
        authButtons();
        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        String planMonth = getParameter("planMonth");

        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");

        if ("planadjmonth".equals(sort)) {
            sort = "planadj_month";

        } else if ("planadjadjamount".equals(sort)) {
            sort = "planadj_adj_amount";

        } else if ("planadjrealincrement".equals(sort)) {
            sort = "planadj_real_increment";

        } else if ("planadjunit".equals(sort)) {
            sort = "planadj_unit";

        } else if ("planadjstatus".equals(sort)) {
            sort = "planadj_status";

        }else if ("planadjcreatetime".equals(sort)) {
            sort = "planadj_create_time";
        }
        if (sort != null) {
            sort = sort + " " + direction;
        }

        setPageParam();
        Map<String, Object> jsonMap = planadjServiceStripe.selectTbplanadjByMonth(sort,planMonth,Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        return JSON.toJSONString(jsonMap);
    }



    /*条线计划调整调整新增页*/
    @RequestMapping("/loadLoanPlanadjDetailInfoInsertPage")
    @SystemLog(tradeName = "条线计划调整其他月份录入页", funCode = "PUB-06-01-01", funName = "条线计划调整其他月份录入页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanadjDetailInfoInsertPage() throws Exception {
        authButtons();

        //获取机构号
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);

        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("organlevel", organlevel);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjustAdd";
    }

    /**
     * 条线调整新增页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tbPlanadjDetail")
    @SystemLog(tradeName = "信贷计划", funCode = "PUB-03-05", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String tbPlanadjDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String month = request.getParameter("planMonth");
        //获取机构号
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);


        //获取条线计划明细
        Map<String, Object> creditPlanList = planadjServiceStripe.getCreditPlanDetail(month);
        //获取条线计划
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.STRIPE);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        //万元转亿元
        for (TbPlan plan : tbPlans) {
            plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));
            plan.setPlanRealIncrement(plan.getPlanRealIncrement().divide(new BigDecimal("10000")));
        }

        //获取分行需求
        Map<String, Object> reqList = planadjServiceStripe.getReqDetail(month);

        //是否存在计划调整
        Integer planadjStatus = -1;
        TbPlanadj queryPlanadj = new TbPlanadj();
        queryPlanadj.setPlanadjMonth(month);
        queryPlanadj.setPlanadjType(TbPlan.STRIPE);
        queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
        List<TbPlanadj> tbPlanadjs = planadjServiceStripe.selectByAttr(queryPlanadj);
        for (TbPlanadj tbPlanadj : tbPlanadjs) {
            if (TbReqDetail.STATE_APPROVED != tbPlanadj.getPlanadjStatus()) {
                if (TbReqDetail.STATE_NEW == tbPlanadj.getPlanadjStatus()) {
                    planadjStatus = TbReqDetail.STATE_NEW;
                } else if (TbReqDetail.STATE_DISMISSED == tbPlanadj.getPlanadjStatus()) {
                    planadjStatus = TbReqDetail.STATE_DISMISSED;
                } else if (TbReqDetail.STATE_APPROVALING == tbPlanadj.getPlanadjStatus()) {
                    planadjStatus = TbReqDetail.STATE_APPROVALING;
                }
            }
        }


        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("reqList", reqList);
        resultMap.put("creditPlanList", creditPlanList);
        resultMap.put("organList", organList);
        resultMap.put("combList", combList);
        resultMap.put("organlevel", organlevel);
        resultMap.put("planadjStatus", planadjStatus);

        // 如果为空，则说明这个月的条线计划 还没有审批完或者没有条线计划
        if (tbPlans != null && tbPlans.size() > 0) {
            resultMap.put("plan", tbPlans.get(0));
        }
        return JSON.toJSONString(resultMap);
    }


    /*保存条线计划调整*/
    @ResponseBody
    @RequestMapping("/savePlanadj")
    @SystemLog(tradeName = "条线计划调整详情保存", funCode = "PUB-06-01-01", funName = "条线计划调整详情保存", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String savePlanadj(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        //上级机构
        String uporgan = sessionOrgan.getUporgan();

        PlainResult<String> result  = planadjServiceStripe.savePlanadj(request, operInfo.getOperCode(), sessionOrgan.getThiscode(),uporgan);
        return JSON.toJSONString(result);

    }

    /*条线计划调整更新页面*/
    @RequestMapping("/listTbPlanadjDetailAuditUI")
    @SystemLog(tradeName = "条线计划调整更新页面", funCode = "PUB-06-01-02", funName = "条线计划调整更新页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listTbPlanadjDetailAuditUI(String planadjId) throws Exception {
        authButtons();

        //查询计划调整
        TbPlanadj tbPlanadj = planadjServiceStripe.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //查询计划调整详情
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //获取所属月份
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //获取机构号
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);


        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);
        setAttribute("organlevel", organlevel);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjustEdit";
    }

    /*条线计划调整更新*/
    @RequestMapping("/updatePlanadj")
    @ResponseBody
    @SystemLog(tradeName = "条线计划调整更新", funCode = "PUB-06-01-02", funName = "条线计划调整更新", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String updatePlanadj(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        //上级机构
        String uporgan = sessionOrgan.getUporgan();
        PlainResult<String> result = planadjServiceStripe.updatePlanadj(request, operInfo.getOperCode(), sessionOrgan.getThiscode(),uporgan);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/deleteTbPlanadjDetail")
    @ResponseBody
    @SystemLog(tradeName = "删除条线计划调整", funCode = "PUB-06-01-03", funName = "删除条线计划调整", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String deleteTbPlanadjDetail(String planadjId) throws Exception {
        authButtons();
        PlainResult<String> result = planadjServiceStripe.deleteTbPlanadjDetail(planadjId);
        return JSON.toJSONString(result);
    }




    /*条线计划调整详情页面*/
    @RequestMapping("/TbPlanadjInfo")
    @SystemLog(tradeName = "条线计划调整详情页面", funCode = "PUB-06-01-04", funName = "条线计划调整详情页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String TbPlanadjInfo(String planadjId) throws Exception {
        authButtons();

        //查询计划调整
        TbPlanadj tbPlanadj = planadjServiceStripe.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //查询计划调整详情
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //获取所属月份
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //获取机构号
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //获取登录机构等级
        String organlevel = getSessionOrgan().getOrganlevel();

        //获取贷种组合级别 查二级贷种
        int combLevel = 2;
        //查询贷种组合
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);


        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjustInfo";
    }

    //报表录入计划调整页面
    @RequestMapping(value = "/toEnterReport")
    @SystemLog(tradeName = "报表录入计划调整页面", funCode = "PUB-06-01", funName = "报表录入计划调整页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjEnterReport";
    }

    //导出计划调整模板
    @RequestMapping(value = "/downPlanadjTemplate")
    @SystemLog(tradeName = "导出计划模板", funCode = "PUB-06-01", funName = "导出计划模板", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downPlanadjTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String type = request.getParameter("type");
            //获取登录机构级别
            String organlevel = getSessionOrgan().getOrganlevel();

            tbPlanadjService.downPlanadjTemplate(request, type,response, organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //导入计划调整
    @ResponseBody
    @RequestMapping(value = "/enterReportPlanadjByMonth", method = RequestMethod.POST)
    @SystemLog(tradeName = "导入计划调整", funCode = "PUB-06-01", funName = "导入计划调整", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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

            resultMap = planadjServiceStripe.enterReportPlanadjByMonth(file, operCode, organCode, request, organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "录入失败,请检查!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }

}
