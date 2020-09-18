package com.boco.PUB.controller.lineTemp;

import com.alibaba.fastjson.JSONArray;
import com.boco.PUB.service.lineTemp.ITbLineTempService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbLineTemp;
import com.boco.SYS.entity.TbQuotaAllocate;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.*;
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
 * 条线临时额度申请管理
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/lineTbQuotaApply/")
public class TbLineTempController extends BaseController {
    @Autowired
    private ITbLineTempService tbLineTempService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    TbQuotaAllocateMapper tbQuotaAllocateMapper;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();

        return basePath + "/PUB/lineTbQuotaMange/tbQuotaApply/tbQuotaApplyList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01-03", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbLineTemp tbQuotaApply) throws Exception {
        tbQuotaApply = tbLineTempService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbLineTemp", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/lineTbQuotaMange/tbQuotaApply/tbQuotaApplyInfo";
    }

    @RequestMapping("infoShowUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-02-01", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoShowUI(TbLineTemp tbQuotaApply) throws Exception {
        setAttribute("TbLineTemp", tbLineTempService.selectByPK(tbQuotaApply.getQaId()));
        return basePath + "/PUB/lineTbQuotaMange/tbQuotaApplyShow/tbQuotaApplyInfoShow";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/lineTbQuotaMange/tbQuotaApply/tbQuotaApplyAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbLineTemp tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApply = tbLineTempService.selectByPK(tbQuotaApply.getQaId());
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        setAttribute("TbLineTemp", tbQuotaApply);
        return basePath + "/PUB/lineTbQuotaMange/tbQuotaApply/tbQuotaApplyEdit";
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbLineTemp tbQuotaApply) throws Exception {
        tbQuotaApply.setQaOrgan(getSessionOperInfo().getOrganCode());
        tbQuotaApply.setQaState(TbLineTemp.STATE_NEW);
        tbQuotaApply.setQaCreateOper(getSessionOperInfo().getOperCode());
        List<TbLineTemp> list = tbLineTempService.selectByAttr(tbQuotaApply);
        List<TbLineTemp> newList = new ArrayList<>();
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(getSessionOperInfo().getOperCode());
        List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
        for (WebOperLineDO operLineDO : webOperLineDOList) {
            ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
            if (Objects.nonNull(lineInfoDO)) {
                String lineId = lineInfoDO.getLineId();//条线id
                List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
                for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                    LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                    if (Objects.nonNull(loanComposeDO)) {
                        for (TbLineTemp tbLineTemp : list) {
                            if (loanComposeDO.getCombCode().equals(tbLineTemp.getQaComb())) {
                                newList.add(tbLineTemp);
                            }
                        }
                    }
                }
            }
        }

        return pageData(newList);
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-11", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
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
                        jsonObject.put("key", loanComposeDO.getCombName());
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-11", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showPage(TbLineTemp tbQuotaApply) throws Exception {
        setPageParam();
        //TODO 这里查看下级机构的申请信息
        List<TbLineTemp> list = tbLineTempService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
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
        TbLineTemp tbQuotaApply = new TbLineTemp();
        String qaComb = request.getParameter("qaComb");
        int unit = Integer.parseInt(request.getParameter("unit"));
        String qaAmt = request.getParameter("qaAmt");
        String qaExpDate = request.getParameter("qaExpDate");
        String qaStartDate = request.getParameter("qaStartDate");
        String qaReason = request.getParameter("qaReason");

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        tbQuotaApply.setQaMonth(sdf.format(now));
        TbQuotaAllocate searchTb = new TbQuotaAllocate();
        searchTb.setPaMonth(sdf.format(now));
        searchTb.setPaProdCode(qaComb);
        searchTb.setPaOrgan(organCode);
        List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
//        if (listResult == null || listResult.size() == 0) {
//            return this.json.returnMsg("false", "新增失败，该贷种组合尚未制定计划!").toJson();
//        }


        tbQuotaApply.setQaState(TbLineTemp.STATE_NEW);
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
        //以下是系统计算得来
        tbQuotaApply.setQaOneInfo("0_0");
        tbQuotaApply.setQaTwoInfo("0_0");
        WebOperLineDO searchOper = new WebOperLineDO();
        searchOper.setOperCode(getSessionOperInfo().getOperCode());
        searchOper.setStatus(1);
        List<WebOperLineDO> tempList = webOperLineMapper.getAllWebOperLineByOperCode(searchOper);
        String lineCodeStr ="";
        if (tempList != null && tempList.size() > 0) {
            for(WebOperLineDO tempDo:tempList){
                lineCodeStr =lineCodeStr+tempDo.getLineId()+",";
            }
        }
        tbQuotaApply.setLineCode(lineCodeStr);
        tbQuotaApply.setQaThreeInfo("0_0");
        tbQuotaApply.setQaOverAmt(new BigDecimal("0"));
        tbQuotaApply.setQaPlanAmt(new BigDecimal("0"));
        tbQuotaApply.setQaYearRate(new BigDecimal(0));
        tbLineTempService.insertEntity(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insertDraft(TbLineTemp tbQuotaApply, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaState(TbLineTemp.STATE_DRAFT);
        tbLineTempService.insertEntity(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
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
        TbLineTemp tbQuotaApply = new TbLineTemp();
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

        tbQuotaApply.setQaState(TbLineTemp.STATE_NEW);
        tbLineTempService.updateByPK(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-11-01-05", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbLineTemp tbQuotaApply, HttpSession session) throws Exception {
        tbLineTempService.deleteByPK(tbQuotaApply.getQaId());
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
    String selectQaId(TbLineTemp tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaId = request.getParameter("qaId").replace("'", "");
        if (qaId != null && "".equals(qaId)) {
            tbQuotaApply.setQaId(Integer.valueOf(qaId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbQuotaApplyList = tbLineTempService.selectQaId(tbQuotaApply);
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
    String selectQaMonth(TbLineTemp tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaMonth = request.getParameter("qaMonth").replace("'", "");
        if (qaMonth != null && "".equals(qaMonth)) {
            tbQuotaApply.setQaMonth(qaMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbQuotaApplyList = tbLineTempService.selectQaMonth(tbQuotaApply);
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