package com.boco.PUB.controller.reqReset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbReqresetDetailService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbReqresetDetail/")
public class TbReqresetDetailController extends BaseController {
    @Autowired
    private ITbReqresetDetailService tbReqresetDetailService;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private IFdOrganService fdOrganService;


    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbReqresetDetail/tbReqresetDetailList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(String reqresetId) throws Exception {
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", reqresetId);
        int unit = tbReqresetList.getReqresetUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));

        setAttr(tbReqresetList);
        return basePath + "/PUB/tbReqStatistics/tbReqresetDetail/tbReqresetDetailInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI(TbReqresetList tbReqresetList) throws Exception {
        tbReqresetList = tbReqresetListService.selectByPK(tbReqresetList.getReqresetId());
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", tbReqresetList.getReqresetId());
        BigDecimal unitAmount = BigDecimal.ONE;
        if (tbReqresetList.getReqresetUnit() == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));
        setAttr(tbReqresetList);
        return basePath + "/PUB/tbReqStatistics/tbReqresetDetail/tbReqresetDetailAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(String reqresetId) throws Exception {
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", reqresetId);
        int unit = tbReqresetList.getReqresetUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));

        setAttr(tbReqresetList);
        return basePath + "/PUB/tbReqStatistics/tbReqresetDetail/tbReqresetDetailEdit";
    }


    //月份  需要查询的机构号
    private BigDecimal getPlanCount(String month, String organ) throws Exception {

        //获取上级机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(organ);
        List<FdOrgan> fdOrgans = fdOrganService.selectByAttr(fdOrgan);
        String upOrgan = "";
        if (fdOrgans != null && fdOrgans.size() != 0) {
            upOrgan = fdOrgans.get(0).getUporgan();
        }

        //获取计划
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(upOrgan);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlans != null && tbPlans.size() != 0) {
            planId = tbPlans.get(0).getPlanId();
        }

        //获取计划详情
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(planId);
        tbPlanDetail.setPdOrgan(organ);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

        //计算总金额
        BigDecimal planCount = BigDecimal.ZERO;
        for (TbPlanDetail planDetail : tbPlanDetails) {
            planCount = planCount.add(planDetail.getPdAmount());
        }
        return planCount;

    }

    /**
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB2", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String reqresetId, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
        tbReqresetDetail.setReqdresetRefId(Integer.valueOf(reqresetId));
        tbReqresetDetail.setReqdresetOrgan(organCode);
        List<TbReqresetDetail> tbReqDetailList = tbReqresetDetailService.selectByAttr(tbReqresetDetail);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        Map<String, Integer> combCodeLevelMap = new HashMap<>();
        for (LoanCombDO tempComb : loanCombDOS) {
            combCodeLevelMap.put(tempComb.getCombCode(), tempComb.getCombLevel());
        }
        BigDecimal unit = new BigDecimal(1);

        if (tbReqDetailList != null && tbReqDetailList.size() > 0) {
            int reqUnit = tbReqDetailList.get(0).getReqdresetUnit();
            if (reqUnit == 2) {
                unit = new BigDecimal(10000);
            }
            for (TbReqresetDetail tbReqDetail1 : tbReqDetailList) {
                tbReqDetail1.setReqdresetNum(tbReqDetail1.getReqdresetNum().divide(unit));
                tbReqDetail1.setReqdresetRefId(combCodeLevelMap.get(tbReqDetail1.getReqdresetCombCode()));
            }
        }

        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("tbreqresetDetailList", tbReqDetailList);
        return JSON.toJSONString(resultMap);
    }


    /**
     * 通用方法
     *
     * @param tbReqresetList
     * @throws Exception
     */
    private void setAttr(TbReqresetList tbReqresetList) throws Exception {
        int reqresetType = tbReqresetList.getReqresetType();
        String combListStr = "";

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //原计划，调整量，调整后金额

        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        String month = tbReqresetList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            //机构的 计划 查询条件 需要修改为上级机构 code
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 1);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }

        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbReqresetList.getReqresetUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }


        BigDecimal one_oldTotalNum = BigDecimal.ZERO;
        BigDecimal two_oldTotalNum = BigDecimal.ZERO;
        BigDecimal three_oldTotalNum = BigDecimal.ZERO;
        if (reqresetType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            combListStr = tbReqresetList.getReqresetCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                String codeStr = loanCombDO.getCombCode();
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(codeStr)) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combMap.put("oldNum", "0");
                        if (planIsOk) {
                            for (Map<String, Object> map : planList) {
                                String planCombCode = map.get("loantype").toString();
                                if (planCombCode.equals(codeStr)) {
                                    BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                    combMap.put("oldNum", oldPlan.divide(unit).toString());
                                    if (1 == loanCombDO.getCombLevel()) {
                                        one_oldTotalNum = BigDecimalUtil.add(one_oldTotalNum, oldPlan.divide(unit));
                                    } else if (2 == loanCombDO.getCombLevel()) {
                                        two_oldTotalNum = BigDecimalUtil.add(two_oldTotalNum, oldPlan.divide(unit));
                                    } else if (3 == loanCombDO.getCombLevel()) {
                                        three_oldTotalNum = BigDecimalUtil.add(three_oldTotalNum, oldPlan.divide(unit));
                                    }
                                    break;
                                }
                            }
                        }
                        combList.add(combMap);
                        break;
                    }
                }
            }

            setAttribute("combList", combList);
            setAttribute("one_oldTotalNum", one_oldTotalNum);
            setAttribute("two_oldTotalNum", two_oldTotalNum);
            setAttribute("three_oldTotalNum", three_oldTotalNum);
        }
    }


    /**
     * TODO 查询tb_req_detail分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbReqresetDetail tbReqresetDetail) throws Exception {
        setPageParam();
        List<TbReqresetDetail> list = tbReqresetDetailService.selectByAttr(tbReqresetDetail);
        return pageData(list);
    }


    /**
     * 查询贷种组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-05-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(3);
        JSONObject listObj = new JSONObject();
        List<Map<String, String>> list = new ArrayList<>();
        for (LoanCombDO tb : loanCombDOS) {
            Map<String, String> map = new HashMap<>();
            map.put("value", tb.getCombCode());
            map.put("key", tb.getCombName());
            list.add(map);
        }
        listObj.put("combList", list);
        return listObj.toString();
    }


    /**
     * TODO 新增tb_req_detail.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        int testNum = Integer.parseInt(request.getParameter("testNum"));
        PlainResult<String> result = new PlainResult<>();
        try {
            List<TbReqresetDetail> tbPlanDetailList = getList(request, result, organCode);
            if (testNum == 9999) {
                TbReqresetDetail searchTb = new TbReqresetDetail();
                searchTb.setReqdresetRefId(tbPlanDetailList.get(0).getReqdresetRefId());
                searchTb.setReqdresetOrgan(getSessionOrgan().getThiscode());
                List<TbReqresetDetail> tempList = tbReqresetDetailService.selectByAttr(searchTb);
                if (tempList != null && tempList.size() > 0) {
                    result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "当前批次的需求调整已录入，请转至菜单查看");
                    WebMsg webMsg = new WebMsg();
                    webMsg.setMsgType(DicCache.getKeyByName_("录入需求调整", "MSG_TYPE"));
//                    webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
                    webMsg.setWebMsgStatus("1");
                    webMsg.setOperDescribe("录入需求调整：" + tbPlanDetailList.get(0).getReqdresetRefId() + "_" + getSessionOrgan().getThiscode());
                    List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
                    if (webMsgs != null && webMsgs.size() != 0) {
                        for (int i = 0; i < webMsgs.size(); i++) {
                            //当前批次id的 当前 机构号的 所有消息都删掉
                            webMsgService.deleteByPK(webMsgs.get(i).getMsgNo());
                        }
                    }
                } else {
                    result.error(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED, "当前批次的需求已录入，请转至菜单查看");

                    return JSON.toJSONString(result);
                }
                return JSON.toJSONString(result);
            }

            tbReqresetDetailService.insertBatch(tbPlanDetailList);
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("录入需求调整", "MSG_TYPE"));
            webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("录入需求调整：" + tbPlanDetailList.get(0).getReqdresetRefId() + "_" + getSessionOrgan().getThiscode());
            List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
            if (webMsgs != null && webMsgs.size() != 0) {
                for (int i = 0; i < webMsgs.size(); i++) {
                    //当前批次id的 当前 机构号的 所有消息都删掉
                    webMsgService.deleteByPK(webMsgs.get(i).getMsgNo());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        result = result.success("add", "add credit plan success.");
        return JSON.toJSONString(result);
    }

    /**
     * 解析前台传来的参数
     *
     * @param request
     * @param result
     * @param organCode
     * @return
     */
    private List<TbReqresetDetail> getList(HttpServletRequest request, PlainResult<String> result, String organCode) {
        String tbReqDetailStr = request.getParameter("tbreqresetDetail");
        String reqUnit = request.getParameter("reqresetUnit");
        String reqId = request.getParameter("reqresetId");
        String state = request.getParameter("state");

        List<TbReqresetDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = tbReqDetailStr.split("&");
        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            //eg: 11005293_x001=123 (贷种code_金额分类=金额&贷种code_金额分类=金额)
            String num = planDetailArr1[1];
            String combCodeStr = planDetailArr2[0];
            String combCode = planDetailArr2[1];
            TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
            tbReqresetDetail.setReqdresetCombCode(combCodeStr);
            tbReqresetDetail.setReqdresetUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqresetDetail.setReqdresetRefId(Integer.valueOf(reqId));
            if (combCode.endsWith("Num")) {
                tbReqresetDetail.setReqdresetNum(new BigDecimal(num).multiply(unit));
            }
            if (combCode.endsWith("oldNum")) {
                tbReqresetDetail.setReqdresetOper(new BigDecimal(num).multiply(unit).toString());
            }
            if (combCode.endsWith("newNum")) {
                tbReqresetDetail.setReqdresetUpdateoper(new BigDecimal(num).multiply(unit).toString());
            }
            tbReqresetDetail.setReqdresetOrgan(organCode);
            tbReqresetDetail.setReqdresetState(Integer.valueOf(state));
            tbReqresetDetail.setReqdresetCreatetime(BocoUtils.getTime());
            tbReqresetDetail.setReqdresetUpdatetime(BocoUtils.getTime());
            tbPlanDetailList.add(tbReqresetDetail);
        }
        return tbPlanDetailList;
    }


    /**
     * TODO 修改tb_req_detail.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        PlainResult<String> result = new PlainResult<>();
        try {
            String reqId = request.getParameter("reqresetId");
            List<TbReqresetDetail> tbPlanDetailList = getList(request, result, organCode);
            TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
            tbReqresetDetail.setReqdresetOrgan(organCode);
            tbReqresetDetail.setReqdresetRefId(Integer.valueOf(reqId));
            tbReqresetDetailService.deleteByAttr(tbReqresetDetail);
            tbReqresetDetailService.insertBatch(tbPlanDetailList);
        } catch (Exception e) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update req detail error");
        }
        result = result.success("update", "update req detail success.");
        return JSON.toJSONString(result);
    }


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "showReqresetId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-05-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqdId(TbReqresetDetail tbReqresetDetail, HttpServletRequest request) throws Exception {
        String reqdId = request.getParameter("reqdresetId");
        if (reqdId != null && "".equals(reqdId)) {
            tbReqresetDetail.setReqdresetId(Integer.valueOf(reqdId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbReqresetDetailList = tbReqresetDetailService.selectReqdId(tbReqresetDetail);
        for (Map<String, Integer> deptInfo : tbReqresetDetailList) {
            String data = String.valueOf(deptInfo.get("reqdreset_id"));
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }

    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectReqdRefId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-05-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqdRefId(TbReqresetDetail tbReqresetDetail, HttpServletRequest request) throws Exception {
        String reqdRefId = request.getParameter("reqdresetRefId").replace("'", "");
        if (reqdRefId != null && "".equals(reqdRefId)) {
            tbReqresetDetail.setReqdresetRefId(Integer.valueOf(reqdRefId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> webOperInfoList = tbReqresetDetailService.selectReqdRefId(tbReqresetDetail);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("reqdreset_ref_id");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }

}