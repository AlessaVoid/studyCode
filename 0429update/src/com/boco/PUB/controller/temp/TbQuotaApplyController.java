package com.boco.PUB.controller.temp;

import com.alibaba.fastjson.JSONArray;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbQuotaApplyService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbQuotaAllocateMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
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
 * 临时额度申请管理
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbQuotaApply/")
public class TbQuotaApplyController extends BaseController {
    @Autowired
    private ITbQuotaApplyService tbQuotaApplyService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    TbQuotaAllocateMapper tbQuotaAllocateMapper;
    @Autowired
    private IFdOrganService fdOrganService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyList";
    }


    //访问列表、详细信息、新增、修改页面
    @RequestMapping("showUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-02", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String showUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbQuotaMange/tbQuotaApplyShow/tbQuotaApplyListShow";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01-03", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbQuotaApply tbQuotaApply) throws Exception {
        tbQuotaApply = tbQuotaApplyService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbQuotaApply", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyInfo";
    }

    @RequestMapping("infoShowUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-02-01", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoShowUI(TbQuotaApply tbQuotaApply) throws Exception {
        setAttribute("TbQuotaApply", tbQuotaApplyService.selectByPK(tbQuotaApply.getQaId()));
        return basePath + "/PUB/tbQuotaMange/tbQuotaApplyShow/tbQuotaApplyInfoShow";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyAdd";
    }


    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbQuotaApply tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApply = tbQuotaApplyService.selectByPK(tbQuotaApply.getQaId());
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        setAttribute("TbQuotaApply", tbQuotaApply);
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyEdit";
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbQuotaApply tbQuotaApply) throws Exception {
        setPageParam();
        tbQuotaApply.setQaOrgan(getSessionOperInfo().getOrganCode());
        tbQuotaApply.setQaState(TbQuotaApply.STATE_NEW);
        List<TbQuotaApply> list = tbQuotaApplyService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-04", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb(int organLevel) throws Exception {

        TbPlan searchTb = new TbPlan();
        if (organLevel == 0) {
            searchTb.setPlanOrgan(getSessionOrgan().getUporgan());
            searchTb.setPlanType(TbPlan.PLAN);
        } else if (organLevel == 1) {
            searchTb.setPlanOrgan(getSessionOrgan().getThiscode());
            searchTb.setPlanType(TbPlan.STRIPE);
        } else if (organLevel == 2) {
            searchTb.setPlanOrgan(getSessionOrgan().getThiscode());
            searchTb.setPlanType(TbPlan.PLAN);
        }


        searchTb.setPlanMonth(BocoUtils.getMonth());

        List<TbPlan> tbPlanList = tbPlanService.selectByAttr(searchTb);
        int level = 2;
        if (tbPlanList != null && tbPlanList.size() > 0) {
            level = tbPlanList.get(0).getCombLevel();
        } else {
            return this.json.returnMsg("false", "该机构级别尚未制定计划!").toJson();
        }
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(level);
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (LoanCombDO tb : loanCombDOS) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getCombCode());
            jsonObject.put("key", tb.getCombName());
            jsonArray.add(jsonObject);
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB3", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showPage(TbQuotaApply tbQuotaApply) throws Exception {
        setPageParam();
        //TODO 这里查看下级机构的申请信息
        List<TbQuotaApply> list = tbQuotaApplyService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //最后操作员
        String fileName = "";
        if (file != null) {
            fileName = tbPlanService.uploadFile(file);
        }
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbQuotaApply tbQuotaApply = new TbQuotaApply();
        String qaComb = request.getParameter("qaComb");
        String qaAmt = request.getParameter("qaAmt");
        int unit = Integer.parseInt(request.getParameter("unit"));
        String qaExpDate = request.getParameter("qaExpDate");
        String qaStartDate = request.getParameter("qaStartDate");
        String qaReason = request.getParameter("qaReason");
        String organName = request.getParameter("organName");
        Integer qaType = Integer.parseInt(request.getParameter("qaType"));
        FdOrgan searchFdOrgan = new FdOrgan();
        searchFdOrgan.setOrganname(organName);
        List<FdOrgan> list = fdOrganService.selectByAttr(searchFdOrgan);
        if (list != null && list.size() > 0) {
            tbQuotaApply.setOrganCode(list.get(0).getThiscode());
            tbQuotaApply.setOrganName(organName);
        } else {
            return this.json.returnMsg("false", "新增失败，请确保机构真实存在!").toJson();
        }

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        tbQuotaApply.setQaMonth(sdf.format(now));
        tbQuotaApply.setQaType(qaType);
        TbQuotaAllocate searchTb = new TbQuotaAllocate();
        searchTb.setPaMonth(sdf.format(now));
        searchTb.setPaProdCode(qaComb);
        searchTb.setPaOrgan(list.get(0).getThiscode());
        List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
        if (listResult == null || listResult.size() == 0) {
            return this.json.returnMsg("false", "新增失败，此机构的该贷种组合计划额度尚未生效!").toJson();
        }

        tbQuotaApply.setQaState(TbQuotaApply.STATE_NEW);
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaAmt(new BigDecimal(qaAmt));
        tbQuotaApply.setQaComb(qaComb);
        tbQuotaApply.setUnit(unit);
        tbQuotaApply.setQaExpDate(qaExpDate);
        tbQuotaApply.setQaStartDate(qaStartDate);
        tbQuotaApply.setQaReason(qaReason);
        tbQuotaApply.setQaFileId(fileName);//上传附件id
        // todo 以下是系统计算得来
        tbQuotaApply.setQaOneInfo("0_0");
        tbQuotaApply.setQaTwoInfo("0_0");
        tbQuotaApply.setQaThreeInfo("0_0");
        tbQuotaApply.setQaOverAmt(new BigDecimal("0"));
        tbQuotaApply.setQaPlanAmt(new BigDecimal("0"));
        tbQuotaApply.setQaYearRate(new BigDecimal(0));
        tbQuotaApplyService.insertEntity(tbQuotaApply);
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
    @RequestMapping(value = "checkPlan")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04", funName = "校验", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String checkPlan(HttpServletRequest request, HttpSession session) throws Exception {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        TbQuotaAllocate searchTb = new TbQuotaAllocate();
        searchTb.setPaMonth(sdf.format(now));
        searchTb.setPaOrgan(getSessionOrgan().getThiscode());
        List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
        if (listResult == null || listResult.size() == 0) {
            return this.json.returnMsg("false", "新增失败，该机构计划额度尚未生效,暂时无法申请临时额度!").toJson();
        }

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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insertDraft(TbQuotaApply tbQuotaApply, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaState(TbQuotaApply.STATE_DRAFT);
        tbQuotaApplyService.insertEntity(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //最后操作员
        String fileName = "";
        if (file != null) {
            fileName = tbPlanService.uploadFile(file);
        }
        String qaComb = request.getParameter("qaComb");
        String qaId = request.getParameter("qaId");
        int unit = Integer.parseInt(request.getParameter("unit"));
        String qaAmt = request.getParameter("qaAmt");
        String qaExpDate = request.getParameter("qaExpDate");
        String qaStartDate = request.getParameter("qaStartDate");
        String qaReason = request.getParameter("qaReason");
        String organName = request.getParameter("organName");
        TbQuotaApply tbQuotaApply = new TbQuotaApply();
        FdOrgan searchFdOrgan = new FdOrgan();
        if(organName!=null&&!"".equals(organName.trim())){
            searchFdOrgan.setOrganname(organName);
            List<FdOrgan> list = fdOrganService.selectByAttr(searchFdOrgan);
            if (list != null && list.size() > 0) {
                tbQuotaApply.setOrganCode(list.get(0).getThiscode());
                tbQuotaApply.setOrganName(organName);
            } else {
                return this.json.returnMsg("false", "新增失败，请确保机构真实存在!").toJson();
            }
        }

        tbQuotaApply.setQaId(Integer.parseInt(qaId));
        tbQuotaApply.setQaComb(qaComb);
        tbQuotaApply.setUnit(unit);
        tbQuotaApply.setQaAmt(new BigDecimal(qaAmt));
        tbQuotaApply.setQaStartDate(qaStartDate);
        tbQuotaApply.setQaExpDate(qaExpDate);
        tbQuotaApply.setQaReason(qaReason);
        if (!"".equals(fileName)) {
            tbQuotaApply.setQaFileId(fileName);
        }
        tbQuotaApply.setQaState(TbQuotaApply.STATE_NEW);
        tbQuotaApplyService.updateByPK(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-04-01-05", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbQuotaApply tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApplyService.deleteByPK(tbQuotaApply.getQaId());
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
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-04", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectQaId(TbQuotaApply tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaId = request.getParameter("qaId").replace("'", "");
        if (qaId != null && "".equals(qaId)) {
            tbQuotaApply.setQaId(Integer.valueOf(qaId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbQuotaApplyList = tbQuotaApplyService.selectQaId(tbQuotaApply);
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
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-04", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectQaMonth(TbQuotaApply tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaMonth = request.getParameter("qaMonth").replace("'", "");
        if (qaMonth != null && "".equals(qaMonth)) {
            tbQuotaApply.setQaMonth(qaMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbQuotaApplyList = tbQuotaApplyService.selectQaMonth(tbQuotaApply);
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