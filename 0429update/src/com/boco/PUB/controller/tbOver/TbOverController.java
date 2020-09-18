package com.boco.PUB.controller.tbOver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.boco.PUB.service.ITbOverService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbOver;
import com.boco.SYS.entity.TbOverDO;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.util.JsonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 机构超限额申请管理
 * 单一机构调整计划
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/over/")
public class TbOverController extends BaseController {
    @Autowired
    private ITbOverService tbOverService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;
    @Autowired
    private TbPlanService tbPlanService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbOverManage/tbQuotaApply/tbQuotaApplyList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01-03", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbOver tbQuotaApply) throws Exception {
        tbQuotaApply = tbOverService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbOver", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        setAttr(tbQuotaApply);
        return basePath + "/PUB/tbOverManage/tbQuotaApply/tbQuotaApplyInfo";
    }

    @RequestMapping("infoShowUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-02-01", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoShowUI(TbOver tbQuotaApply) throws Exception {
        setAttribute("TbOver", tbOverService.selectByPK(tbQuotaApply.getQaId()));
        setAttr(tbOverService.selectByPK(tbQuotaApply.getQaId()));
        return basePath + "/PUB/tbOverManage/tbQuotaApplyShow/tbQuotaApplyInfoShow";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        setAttr(null);
        return basePath + "/PUB/tbOverManage/tbQuotaApply/tbQuotaApplyAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbOver tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApply = tbOverService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbOver", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        setAttr(tbQuotaApply);
        return basePath + "/PUB/tbOverManage/tbQuotaApply/tbQuotaApplyEdit";
    }

    /**
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB3", funName = "信贷计划详情数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String qaId, HttpSession session) throws Exception {
        TbOver tbOver = tbOverService.selectByPK(Integer.valueOf(qaId));
        String lineCombtr = tbOver.getQaComb();
        String[] lineCombArr = lineCombtr.split(",");
        String numStr = tbOver.getQaAmt();
        String appNumStr = tbOver.getQaOneInfo();
        String[] numArr = numStr.split(",");
        String[] appNumArr = appNumStr.split(",");

        List<TbOverDO> tbOverDOS = new ArrayList<>();
        for (int i = 0; i < lineCombArr.length; i++) {
            TbOverDO tb = new TbOverDO();
            tb.setQaComb(lineCombArr[i]);
            tb.setQaAmt(BigDecimalUtil.getSafeCount(numArr[i]));
            tb.setQaOverAmt(BigDecimalUtil.getSafeCount(appNumArr[i]));
            tbOverDOS.add(tb);
        }
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbOverDOS", tbOverDOS);
        return JSON.toJSONString(resultMap);
    }


    /**
     * 通用方法
     *
     * @param
     * @throws Exception
     */
    private void setAttr(TbOver tbQuotaApply) throws Exception {
        TbPlan searchTb = new TbPlan();
        searchTb.setPlanOrgan(getSessionOrgan().getUporgan());
        if (null == tbQuotaApply) {
            searchTb.setPlanMonth(BocoUtils.getMonth());
        } else {
            searchTb.setPlanMonth(tbQuotaApply.getQaMonth());
        }
        searchTb.setPlanType(TbPlan.PLAN);

        List<TbPlan> tbPlanList = tbPlanService.selectByAttr(searchTb);
        int level = 2;
        if (tbPlanList != null && tbPlanList.size() > 0) {
            level = tbPlanList.get(0).getCombLevel();
        }

        String organLevel = getSessionOrgan().getOrganlevel();
        FdOper fdOper = getSessionUser();
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(fdOper.getOpercode());
        /*拿到当前登录用户所辖条线列表*/
        List<Map<String, String>> combList = new ArrayList<>();
        List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
        if (webOperLineDOList != null && webOperLineDOList.size() > 0) {
            for (WebOperLineDO operLineDO : webOperLineDOList) {
                ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                if (Objects.nonNull(lineInfoDO)) {
                    String lineId = lineInfoDO.getLineId();
                    List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
                    for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                        LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                        if (Objects.nonNull(loanComposeDO)) {
                            Map<String, String> combMap = new HashMap<>(2);
                            combMap.put("combCode", loanComposeDO.getCombCode());
                            combMap.put("combName", loanComposeDO.getCombName());
                            combList.add(combMap);
                        }
                    }
                }
            }
        } else if ("1".equals(organLevel)) {
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(level);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        } else if ("2".equals(organLevel)) {
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(level);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        }
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("combList", combList);
    }

    /**
     * TODO 查询本机填报的tb_quota_apply分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbOver tbQuotaApply) throws Exception {
        setPageParam();
        tbQuotaApply.setQaOrgan(getSessionOperInfo().getOrganCode());
        tbQuotaApply.setQaState(TbOver.STATE_NEW);
        List<TbOver> list = tbOverService.selectByAttr(tbQuotaApply);
        for (TbOver tb : list) {
            String qaAmtStr = tb.getQaAmt();
            String[] numArr = qaAmtStr.split(",");
            BigDecimal totalNum = new BigDecimal(0);
            for (int i = 0; i < numArr.length; i++) {
                totalNum = totalNum.add(new BigDecimal(numArr[i]));
            }
            tb.setQaAmt(totalNum.toString());
        }
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-13", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb(HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(fdOper.getOpercode());
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
        for (WebOperLineDO operLineDO : webOperLineDOList) {
            ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
            if (Objects.nonNull(lineInfoDO)) {
                String lineId = lineInfoDO.getLineId();//条线id
                List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
                for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                    LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                    if (Objects.nonNull(loanComposeDO)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("value", loanComposeDO.getCombCode());
                        jsonObject.put("key", loanComposeDO.getCombCode());
                        jsonArray.add(jsonObject);
                    }
                }
            }
        }

        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    /**
     * 查询贷种组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "showComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-13", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(2);
        JSONObject listObj = new JSONObject();
        Map<String, String> map = new HashMap<>(32);
        for (LoanCombDO tb : loanCombDOS) {
            map.put(tb.getCombCode(), tb.getCombName());
        }
        listObj.put("combMap", map);
        return listObj.toString();
    }


    /**
     * TODO 查询下级填写的tb_quota_apply分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "showPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showPage(TbOver tbQuotaApply) throws Exception {
        setPageParam();
        //TODO 这里查看下级机构的申请信息
        List<TbOver> list = tbOverService.selectByAttr(tbQuotaApply);
        return pageData(list);
    }

    /**
     * TODO 新增tb_quota_apply.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        //最后操作员
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //最后操作员
        String fileName = "";
        if (file != null) {
            fileName = tbPlanService.uploadFile(file);
        }
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbOver tbQuotaApply = new TbOver();
        String tbOverDetail = request.getParameter("tbOverDetail");
        String qaReason = request.getParameter("qaReason");
        int unit = Integer.parseInt(request.getParameter("unit"));

        String[] tbOverDetailArr = tbOverDetail.split("&");
        String numStr = "";
        String combCodeStr = "";
        for (int i = 0; i < tbOverDetailArr.length; i++) {
            String[] planDetailArr1 = tbOverDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            String num = planDetailArr1[1];
            String combCode = planDetailArr2[0];
            numStr += (num + ",");
            combCodeStr += (combCode + ",");
        }
        if (numStr.endsWith(",")) {
            numStr = numStr.substring(0, numStr.length() - 1);
        }
        if (combCodeStr.endsWith(",")) {
            combCodeStr = combCodeStr.substring(0, combCodeStr.length() - 1);
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        tbQuotaApply.setQaMonth(sdf.format(now));
        tbQuotaApply.setQaState(TbOver.STATE_NEW);
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setUnit(unit);
        tbQuotaApply.setQaAmt(numStr);
        tbQuotaApply.setQaComb(combCodeStr);
        tbQuotaApply.setQaReason(qaReason);
        tbQuotaApply.setQaFileId(fileName);//上传附件id
        //以下是系统计算得来
        tbQuotaApply.setQaOneInfo(numStr);
        tbQuotaApply.setQaTwoInfo("0_0");
        tbQuotaApply.setQaThreeInfo("0_0");
        tbQuotaApply.setQaOverAmt(new BigDecimal("0"));
        tbQuotaApply.setQaPlanAmt(new BigDecimal("0"));
        tbQuotaApply.setQaYearRate(new BigDecimal(0));
        tbQuotaApply.setOrganname(getSessionOrgan().getOrganname());
        tbOverService.insertEntity(tbQuotaApply);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }


    /**
     * TODO 新增tb_quota_apply.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insertDraft")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insertDraft(TbOver tbQuotaApply, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaState(TbOver.STATE_DRAFT);
        tbOverService.insertEntity(tbQuotaApply);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改tb_quota_apply.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //最后操作员
        String fileName = "";
        if (file != null) {
            fileName = tbPlanService.uploadFile(file);
        }
        String qaId = request.getParameter("qaId");
        int unit = Integer.parseInt(request.getParameter("unit"));
        String tbOverDetail = request.getParameter("tbOverDetail");
        String qaReason = request.getParameter("qaReason");
        String[] tbOverDetailArr = tbOverDetail.split("&");
        String numStr = "";
        String combCodeStr = "";
        for (int i = 0; i < tbOverDetailArr.length; i++) {
            String[] planDetailArr1 = tbOverDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            String num = planDetailArr1[1];
            String combCode = planDetailArr2[0];
            numStr += (num + ",");
            combCodeStr += (combCode + ",");
        }
        if (numStr.endsWith(",")) {
            numStr = numStr.substring(0, numStr.length() - 1);
        }
        if (combCodeStr.endsWith(",")) {
            combCodeStr = combCodeStr.substring(0, combCodeStr.length() - 1);
        }
        TbOver tbQuotaApply = new TbOver();
        tbQuotaApply.setQaId(Integer.parseInt(qaId));
        tbQuotaApply.setQaComb(combCodeStr);
        tbQuotaApply.setUnit(unit);
        tbQuotaApply.setQaAmt(numStr);
        tbQuotaApply.setQaOneInfo(numStr);
        tbQuotaApply.setQaReason(qaReason);
        if (!"".equals(fileName)) {
            tbQuotaApply.setQaFileId(fileName);
        }

        tbQuotaApply.setQaState(TbOver.STATE_NEW);
        tbOverService.updateByPK(tbQuotaApply);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除tb_quota_apply
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-13-01-05", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbOver tbQuotaApply, HttpSession session) throws Exception {
        tbOverService.deleteByPK(tbQuotaApply.getQaId());
        return this.json.returnMsg("true", "删除成功!").toJson();
    }


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectQaId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB3", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectQaId(TbOver tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaId = request.getParameter("qaId").replace("'", "");
        if (qaId != null && "".equals(qaId)) {
            tbQuotaApply.setQaId(Integer.valueOf(qaId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbQuotaApplyList = tbOverService.selectQaId(tbQuotaApply);
        for (Map<String, Integer> deptInfo : tbQuotaApplyList) {
            String data = String.valueOf(deptInfo.get("qa_id"));
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
    @RequestMapping(value = "selectQaMonth")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB3", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectQaMonth(TbOver tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaMonth = request.getParameter("qaMonth");
        if (qaMonth != null && "".equals(qaMonth)) {
            tbQuotaApply.setQaMonth(qaMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbQuotaApplyList = tbOverService.selectQaMonth(tbQuotaApply);
        for (Map<String, String> deptInfo : tbQuotaApplyList) {
            String data = deptInfo.get("qa_month");
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