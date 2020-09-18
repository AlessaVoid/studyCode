package com.boco.PUB.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.ITbPunishParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.TbQuotaStatusMapper;
import com.boco.TONY.biz.loanplan.service.ILoanPlanService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.TONY.common.ListResult;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.BocoUtils;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间计划表-Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbTradeParam/")
public class TbTradeParamController extends BaseController {
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private ILoanPlanService loanPlanService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private TbQuotaStatusMapper tbQuotaStatusMapper;
    @Autowired
    private ITbPunishParamService tbPunishParamService;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbTradeParam/tbTradeParamList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbTradeParam tbTradeParam) throws Exception {
        TbTradeParam tbTradeParam1 = tbTradeParamService.selectByPK(tbTradeParam.getParamId());
        tbTradeParam1.setParamPunishStart(tbTradeParam1.getParamPunishStart().substring(0, 4) + "-" + tbTradeParam1.getParamPunishStart().substring(4, 6) + "-" + tbTradeParam1.getParamPunishStart().substring(6, 8));
        tbTradeParam1.setParamPunsihEnd(tbTradeParam1.getParamPunsihEnd().substring(0, 4) + "-" + tbTradeParam1.getParamPunsihEnd().substring(4, 6) + "-" + tbTradeParam1.getParamPunsihEnd().substring(6, 8));
        tbTradeParam1.setParamMechIncrement(tbTradeParam1.getParamMechIncrement().divide(new BigDecimal(10000)));
        tbTradeParam1.setParamOverMount(tbTradeParam1.getParamOverMount().divide(new BigDecimal(10000)));
        tbTradeParam1.setParamSingleMount(tbTradeParam1.getParamSingleMount().divide(new BigDecimal(10000)));
        tbTradeParam1.setParamTempMount(tbTradeParam1.getParamTempMount().divide(new BigDecimal(10000)));
        setAttribute("TbTradeParam", tbTradeParam1);
        return basePath + "/PUB/tbReqStatistics/tbTradeParam/tbTradeParamInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbReqStatistics/tbTradeParam/tbTradeParamAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbTradeParam tbTradeParam) throws Exception {
        tbTradeParam = tbTradeParamService.selectByPK(tbTradeParam.getParamId());
        tbTradeParam.setParamMechIncrement(tbTradeParam.getParamMechIncrement().divide(new BigDecimal(10000)));
        tbTradeParam.setParamOverMount(tbTradeParam.getParamOverMount().divide(new BigDecimal(10000)));
        tbTradeParam.setParamSingleMount(tbTradeParam.getParamSingleMount().divide(new BigDecimal(10000)));
        tbTradeParam.setParamTempMount(tbTradeParam.getParamTempMount().divide(new BigDecimal(10000)));
        setAttribute("TbTradeParam", tbTradeParam);
        return basePath + "/PUB/tbReqStatistics/tbTradeParam/tbTradeParamEdit";
    }

    /**
     * 查询tb_trade_param分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbTradeParam tbTradeParam) throws Exception {
        setPageParam();
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(tbTradeParam);
        for (TbTradeParam tbTradeParam1 : list) {
            tbTradeParam1.setParamMechIncrement(tbTradeParam1.getParamMechIncrement().divide(new BigDecimal(10000)));
            tbTradeParam1.setParamOverMount(tbTradeParam1.getParamOverMount().divide(new BigDecimal(10000)));
            tbTradeParam1.setParamTempMount(tbTradeParam1.getParamTempMount().divide(new BigDecimal(10000)));
            tbTradeParam1.setParamSingleMount(tbTradeParam1.getParamSingleMount().divide(new BigDecimal(10000)));
        }
        return pageData(list);
    }


    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findTradeParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findTradeParam() throws Exception {
        TbTradeParam tbTradeParam = new TbTradeParam();
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("value", "请选择");
        jsonObject1.put("key", "请选择");
        jsonArray.add(jsonObject1);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int dateNum = Integer.parseInt(sdf.format(date));

        Integer arr[] = new Integer[tbPunishParamList.size()];
        for (int i = 0; i < tbPunishParamList.size(); i++) {
            arr[i] = Integer.parseInt(tbPunishParamList.get(i).getParamPeriod());
        }
        for (int i = 0; i < arr.length; i++) {
            int paramPeriod = arr[i];
            for (int j = 0; j < tbPunishParamList.size(); j++) {
                if (paramPeriod == Integer.parseInt(tbPunishParamList.get(j).getParamPeriod())) {
                    JSONObject jsonObject = new JSONObject();
                    if (dateNum <= Integer.parseInt((tbPunishParamList.get(j).getParamPeriod()))) {
                        jsonObject.put("value", tbPunishParamList.get(j).getParamPeriod());
                        jsonObject.put("key", tbPunishParamList.get(j).getParamPeriod());
                        jsonArray.add(jsonObject);
                        break;
                    }
                }
            }
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }


    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "quotaFindMonth", method = RequestMethod.POST)
    public @ResponseBody
    String quotaFindMonth() throws Exception {
        TbTradeParam tbTradeParam = new TbTradeParam();
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
        int dateNum = Integer.parseInt(sdf.format(date));

        Integer arrTemp[] = new Integer[tbPunishParamList.size()];
        for (int i = 0; i < tbPunishParamList.size(); i++) {
            arrTemp[i] = Integer.parseInt(tbPunishParamList.get(i).getParamPeriod());
        }
        Arrays.sort(arrTemp);
        Integer arr[] = new Integer[2];
        arr[0] = arrTemp[0];
        arr[1] = arrTemp[1];
        for (int i = 0; i < arr.length; i++) {
            int paramPeriod = arr[i];
            for (int j = 0; j < tbPunishParamList.size(); j++) {
                if (paramPeriod == Integer.parseInt(tbPunishParamList.get(j).getParamPeriod())) {
                    JSONObject jsonObject = new JSONObject();
                    if (dateNum <= Integer.parseInt((tbPunishParamList.get(j).getParamPeriod()))) {
                        jsonObject.put("value", tbPunishParamList.get(j).getParamPeriod());
                        jsonObject.put("key", tbPunishParamList.get(j).getParamPeriod());
                        jsonArray.add(jsonObject);
                        break;
                    }
                }
            }
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }


    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "getTime", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getTime(HttpServletRequest request) throws Exception {
        String reqMonth = request.getParameter("reqMonth");
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(reqMonth);
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        listObj.put("startTime", tbPunishParamList.get(0).getParamReqStart());
        listObj.put("endTime", tbPunishParamList.get(0).getParamReqEnd());

        return listObj.toString();
    }

    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "checkUpdate", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String checkUpdate(HttpServletRequest request) throws Exception {
        String paramPeriod = request.getParameter("paramPeriod");
        ListResult<TbPlan> list = loanPlanService.selectLoanPlanByPlanMonth(paramPeriod);
        if (list.getData() != null && list.getData().size() > 0) {
            return this.json.returnMsg("false", "该时间计划已下发计划,不支持修改操作!").toJson();
        }
        return this.json.returnMsg("true", "").toJson();
    }

    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "checkDelete", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String checkDelete(HttpServletRequest request) throws Exception {
        String paramPeriod = request.getParameter("paramPeriod");
        ListResult<TbPlan> listTbPlan = loanPlanService.selectLoanPlanByPlanMonth(paramPeriod);

        TbReqList tbReqList = new TbReqList();
        tbReqList.setReqMonth(paramPeriod);
        List<TbReqList> listTbReqList = tbReqListService.selectByAttr(tbReqList);


        if (listTbPlan.getData() != null && listTbPlan.getData().size() > 0) {
            return this.json.returnMsg("false", "该时间计划已生成计划,不支持删除操作!").toJson();
        }
        if (listTbReqList != null && listTbReqList.size() > 0) {
            return this.json.returnMsg("false", "该时间计划已生成需求,不支持删除操作!").toJson();
        }

        return this.json.returnMsg("true", "").toJson();
    }

    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "getPlanTime", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getPlanTime(HttpServletRequest request) throws Exception {
        String planMonth = request.getParameter("planMonth");
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(planMonth);
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        listObj.put("startTime", tbPunishParamList.get(0).getParamPlanStart());
        listObj.put("endTime", tbPunishParamList.get(0).getParamPlanEnd());
        listObj.put("paramMode", tbPunishParamList.get(0).getParamMode());

        return listObj.toString();
    }

    /**
     * 查询tb_punish_param列表数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findAll(TbPunishParam tbPunishParam) throws Exception {
        List<TbPunishParam> listTbPunishParam = tbPunishParamService.selectByAttr(tbPunishParam);
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (TbPunishParam tb : listTbPunishParam) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getPpId());
            jsonObject.put("key", tb.getPpName());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);

        return listObj.toString();
    }

    /**
     * TODO 新增tb_trade_param.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-01", funName = "新增TbTradeParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbTradeParam tbTradeParam, HttpSession session) throws Exception {

        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(tbTradeParam.getParamPeriod());
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(searchTbTradeParam);
        if (list != null && list.size() > 0) {
            return this.json.returnMsg("false", "所属月份已存在，请重新选择").toJson();
        }
        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbTradeParam.setParamUpdateuserid(fdOper.getOpercode());
        tbTradeParam.setParamCreateuserid(fdOper.getOpercode());
        tbTradeParam.setParamCreatetime(BocoUtils.getTime());
        tbTradeParam.setParamUpdatetime(BocoUtils.getTime());
        tbTradeParam.setParamMechIncrement(new BigDecimal(10000).multiply(tbTradeParam.getParamMechIncrement()));
//        tbTradeParam.setParamLineIncrement(new BigDecimal(10000).multiply(tbTradeParam.getParamLineIncrement()));
        tbTradeParam.setParamOverMount(new BigDecimal(10000).multiply(tbTradeParam.getParamOverMount()));
        tbTradeParam.setParamTempMount(new BigDecimal(10000).multiply(tbTradeParam.getParamTempMount()));
        tbTradeParam.setParamSingleMount(new BigDecimal(10000).multiply(tbTradeParam.getParamSingleMount()));
        int count = tbTradeParamService.insertEntity(tbTradeParam);

        TbQuotaStatus tbQuotaStatus = tbQuotaStatusMapper.selectByPK(tbTradeParam.getParamPeriod());
        if (tbQuotaStatus == null) {
            tbQuotaStatus = new TbQuotaStatus();
            tbQuotaStatus.setPlanMonth(tbTradeParam.getParamPeriod());
            tbQuotaStatus.setExecuteStatus(TbQuotaStatus.status_start);
            tbQuotaStatus.setQuotaStatus(TbQuotaStatus.status_start);
            tbQuotaStatusMapper.insertEntity(tbQuotaStatus);
        }

        if (count == 1) {
            return this.json.returnMsg("true", getErrorInfo("w456")).toJson();
        }
        return this.json.returnMsg("false", getErrorInfo("w446")).toJson();
    }

    /**
     * 校验  新增时间周期 tb_trade_param.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "check")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-01", funName = "新增TbTradeParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String check(HttpServletRequest request, HttpSession session) throws Exception {
        String paramPeriod = request.getParameter("paramPeriod");
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(paramPeriod);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(tbTradeParam);
        if (list != null && list.size() > 0) {
            return this.json.returnMsg("false", getErrorInfo("w446")).toJson();
        } else {
            return this.json.returnMsg("true", getErrorInfo("w456")).toJson();
        }

    }


    /**
     * TODO 修改tb_trade_param.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-02", funName = "修改TbTradeParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbTradeParam tbTradeParam, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbTradeParam.setParamUpdateuserid(fdOper.getOpercode());
        tbTradeParam.setParamMechIncrement(tbTradeParam.getParamMechIncrement().multiply(new BigDecimal(10000)));
        tbTradeParam.setParamOverMount(tbTradeParam.getParamOverMount().multiply(new BigDecimal(10000)));
        tbTradeParam.setParamTempMount(new BigDecimal(10000).multiply(tbTradeParam.getParamTempMount()));
        tbTradeParam.setParamSingleMount(new BigDecimal(10000).multiply(tbTradeParam.getParamSingleMount()));
        tbTradeParam.setParamUpdatetime(BocoUtils.getTime());
        int count = tbTradeParamService.updateByPK(tbTradeParam);
        BigDecimal mechIncrement = tbTradeParam.getParamMechIncrement();
        if (count == 1) {

            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanOrgan(fdOper.getOrgancode());
            tbPlan.setPlanMonth(tbTradeParam.getParamPeriod());
            tbPlan.setPlanIncrement(mechIncrement);
            tbPlanService.updatePlanAndPlanadj(tbPlan);
            tbPlanService.updatePlanStripeAndPlanadjStripe(tbPlan);

            return this.json.returnMsg("true", getErrorInfo("w448")).toJson();
        }
        return this.json.returnMsg("false", getErrorInfo("w447")).toJson();
    }

    /**
     * TODO 删除tb_trade_param
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01-03", funName = "删除TbTradeParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbTradeParam tbTradeParam, HttpSession session) throws Exception {
        int count = tbTradeParamService.deleteByPK(tbTradeParam.getParamId());
        if (count == 1) {
            return this.json.returnMsg("true", "删除成功!").toJson();
        }
        return this.json.returnMsg("false", "删除失败!").toJson();
    }


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectParamId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectParamId(TbTradeParam TbTradeParam, HttpServletRequest request) throws Exception {
        String paramId = request.getParameter("paramId").replace("'", "");
        if (paramId != null && "".equals(paramId)) {
            TbTradeParam.setParamId(Integer.valueOf(paramId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> TbTradeParamList = tbTradeParamService.selectParamId(TbTradeParam);
        for (Map<String, Integer> deptInfo : TbTradeParamList) {
            String data = String.valueOf(deptInfo.get("param_id"));
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<>(2);
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
    @RequestMapping(value = "selectParamPeriod")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectparamPeriod(TbTradeParam TbTradeParam, HttpServletRequest request) throws Exception {
        String paramPeriod = request.getParameter("paramPeriod").replace("'", "");
        if (paramPeriod != null && "".equals(paramPeriod)) {
            TbTradeParam.setParamPeriod(paramPeriod);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> webOperInfoList = tbTradeParamService.selectParamPeriod(TbTradeParam);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("param_period");
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
    @RequestMapping(value = "selectPpOrgan")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectPpOrgan(TbTradeParam TbTradeParam, HttpServletRequest request) throws Exception {
        String paramPeriod = request.getParameter("paramMode").replace("'", "");
        if (paramPeriod != null && "".equals(paramPeriod)) {
            TbTradeParam.setParamMode(Integer.valueOf(paramPeriod));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> webOperInfoList = tbTradeParamService.selectParamMode(TbTradeParam);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("param_mode");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }


}