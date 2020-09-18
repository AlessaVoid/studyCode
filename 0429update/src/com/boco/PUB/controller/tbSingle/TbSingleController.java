package com.boco.PUB.controller.tbSingle;

import com.alibaba.fastjson.JSONArray;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.tbSingle.ITbSingleService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbSingle;
import com.boco.SYS.global.Dic;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.util.JsonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/tbTradeManger/single/")
public class TbSingleController extends BaseController {
    @Autowired
    private ITbSingleService tbSingleService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IFdOrganService fdOrganService;

    @Autowired
    private TbPlanService tbPlanService;



    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01-03", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbSingle tbQuotaApply) throws Exception {
        tbQuotaApply = tbSingleService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbSingle", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyInfo";
    }

    @RequestMapping("infoShowUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-02-01", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoShowUI(TbSingle tbQuotaApply) throws Exception {
        setAttribute("TbSingle", tbSingleService.selectByPK(tbQuotaApply.getQaId()));
        return basePath + "/PUB/tbSingle/tbQuotaApplyShow/tbQuotaApplyInfoShow";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbSingle tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApply = tbSingleService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbSingle", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyEdit";
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbSingle tbQuotaApply) throws Exception {
        setPageParam();
        tbQuotaApply.setQaOrgan(getSessionOperInfo().getOrganCode());
        tbQuotaApply.setQaState(TbSingle.STATE_NEW);
        List<TbSingle> list = tbSingleService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-12", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(2);
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
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-12", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showPage(TbSingle tbQuotaApply) throws Exception {
        setPageParam();
        //TODO 这里查看下级机构的申请信息
        List<TbSingle> list = tbSingleService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
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
        TbSingle tbQuotaApply = new TbSingle();
        String qaComb = request.getParameter("qaComb");
        int unit = Integer.parseInt(request.getParameter("unit"));
        String qaAmt = request.getParameter("qaAmt");
        String qaSingleId = request.getParameter("qaSingleId");

        String qaReason = request.getParameter("qaReason");
        String thiscode = request.getParameter("thiscode");

        tbQuotaApply.setQaSingleId(qaSingleId);
        List<TbSingle> searchTbList = tbSingleService.selectByAttr(tbQuotaApply);
        if (searchTbList != null && searchTbList.size() > 0) {
            return this.json.returnMsg("false", "新增失败，借据号重复!").toJson();
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        tbQuotaApply.setQaMonth(sdf.format(now));
        tbQuotaApply.setQaState(TbSingle.STATE_NEW);
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaAmt(new BigDecimal(qaAmt));
        tbQuotaApply.setQaComb(qaComb);

        FdOrgan searchFdOrgan = new FdOrgan();

        searchFdOrgan.setThiscode(thiscode);
        String QaSingleOrganName = fdOrganService.selectOrganNameZX(searchFdOrgan.getThiscode());
        searchFdOrgan.setOrganname(QaSingleOrganName);

            List<FdOrgan> list = fdOrganService.selectByAttr(searchFdOrgan);


        if (list != null && list.size() > 0) {
            tbQuotaApply.setQaSingleOrgan(list.get(0).getThiscode());
        }else{
            return this.json.returnMsg("false", "新增失败，请确保机构真实存在!").toJson();
        }
        tbQuotaApply.setQaSingleOrganName(QaSingleOrganName);
        tbQuotaApply.setUnit(unit);
        tbQuotaApply.setQaReason(qaReason);
        tbQuotaApply.setQaFileId(fileName);//上传附件id
        //以下是系统计算得来
        tbQuotaApply.setQaOneInfo("0_0");
        tbQuotaApply.setQaTwoInfo("0_0");
        tbQuotaApply.setQaThreeInfo("0_0");
        tbQuotaApply.setQaOverAmt(new BigDecimal("0"));
        tbQuotaApply.setQaPlanAmt(new BigDecimal("0"));
        tbQuotaApply.setQaYearRate(new BigDecimal(0));
        tbSingleService.insertEntity(tbQuotaApply);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    //超限额附件下载
    @RequestMapping(value = "/download")
    @SystemLog(tradeName = "导出计划模板", funCode = "PUB3", funName = "导出计划模板", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public HttpServletResponse downloadPlanTemplate(String qaFileId, HttpServletResponse response) throws Exception {

        //todo 获取文件名
        String fileName = qaFileId;

        response = tbPlanService.downloadFile(fileName, response);
        return response;
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insertDraft(TbSingle tbQuotaApply, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaState(TbSingle.STATE_DRAFT);
        tbSingleService.insertEntity(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
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
        String thiscode = request.getParameter("thiscode");
        String qaAmt = request.getParameter("qaAmt");
        String qaSingleId = request.getParameter("qaSingleId");
/*        String qaSingleOrganName = request.getParameter("qaSingleOrganName");*/
        String qaReason = request.getParameter("qaReason");
        TbSingle tbQuotaApply = new TbSingle();
        tbQuotaApply.setQaSingleId(qaSingleId);

        List<TbSingle> searchTbList = tbSingleService.selectByAttr(tbQuotaApply);
        if (searchTbList != null && searchTbList.size() > 0) {
            if (!qaId.equals(searchTbList.get(0).getQaId().toString())) {
                return this.json.returnMsg("false", "更新失败，借据号重复!").toJson();
            }
        }
        tbQuotaApply.setQaId(Integer.parseInt(qaId));
        tbQuotaApply.setQaComb(qaComb);
        tbQuotaApply.setQaAmt(new BigDecimal(qaAmt));
        tbQuotaApply.setUnit(unit);
        String QaSingleOrganName = fdOrganService.selectOrganNameZX(thiscode);
        if (!"".equals(QaSingleOrganName)) {
            FdOrgan searchFdOrgan = new FdOrgan();
            searchFdOrgan.setOrganname(QaSingleOrganName);
            List<FdOrgan> list = fdOrganService.selectByAttr(searchFdOrgan);
            if (list != null && list.size() > 0) {
                tbQuotaApply.setQaSingleOrgan(list.get(0).getThiscode());
            }
            tbQuotaApply.setQaSingleOrganName(QaSingleOrganName);
        }
        tbQuotaApply.setQaReason(qaReason);
        if (!"".equals(fileName)) {
            tbQuotaApply.setQaFileId(fileName);
        }

        tbQuotaApply.setQaState(TbSingle.STATE_NEW);
        tbSingleService.updateByPK(tbQuotaApply);
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
    @SystemLog(tradeName = "交易名称", funCode = "PUB-12-01-05", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbSingle tbQuotaApply, HttpSession session) throws Exception {
        tbSingleService.deleteByPK(tbQuotaApply.getQaId());
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
    String selectQaId(TbSingle tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaId = request.getParameter("qaId").replace("'", "");
        if (qaId != null && "".equals(qaId)) {
            tbQuotaApply.setQaId(Integer.valueOf(qaId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbQuotaApplyList = tbSingleService.selectQaId(tbQuotaApply);
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
    String selectQaMonth(TbSingle tbQuotaApply, HttpServletRequest request) throws Exception {
        String qaMonth = request.getParameter("qaMonth").replace("'", "");
        if (qaMonth != null && "".equals(qaMonth)) {
            tbQuotaApply.setQaMonth(qaMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbQuotaApplyList = tbSingleService.selectQaMonth(tbQuotaApply);
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


    /**
     * TODO 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      秦海洲      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectName")
    @SystemLog(tradeName = "机构名称联想输入", funCode = "PUB3", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectName() throws Exception {
        String organName = getParameter("organname").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganname(organName);
        fdOrgan.setProvincecode(getSessionOrgan().getThiscode());
        List<String> organNameList = fdOrganService.selectByName(fdOrgan);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : organNameList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }


    @RequestMapping(value = "selectOrganName", method = RequestMethod.POST)
    @SystemLog(tradeName="根据机构号查询机构名称",funCode="PM-06", funName="联想输入", accessType=Dic.AccessType.READ, level=Dic.Debug.DEBUG)
    @ResponseBody public String selectOrganName (HttpServletRequest request) throws Exception{
        String thiscode = getParameter("thiscode");
        Map resultMap = new HashMap();
        if(thiscode !=null && thiscode !=""){
            String OrganName =this.fdOrganService.selectOrganName(thiscode);

            resultMap.put("key",OrganName);
        }
        return JsonUtils.toJson(resultMap);
    }


    @RequestMapping({"selectCode"})
    @SystemLog(tradeName="机构代码联想输入", funCode="PM-06", funName="联想输入", accessType=Dic.AccessType.READ, level=Dic.Debug.DEBUG)
    @ResponseBody
    public String selectCode(HttpServletRequest request)
            throws Exception
    {
        String thisCode = getParameter("thiscode").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(thisCode);
        fdOrgan.setProvincecode(getSessionOrgan().getThiscode());

        Map resultMap = new HashMap();
        List<Map<String, String>> list = new ArrayList();
        if (thisCode.length() > 3) {
            List<String> organCodeList = this.fdOrganService.selectByThisCodeZX(fdOrgan);
            for (String data : organCodeList) {
                Map<String,String> map = new HashMap();
                map.put("key", data);
                map.put("value", data);
                list.add(map);
            }
            resultMap.put("list", list);
        }
        return JsonUtils.toJson(resultMap);
    }
}


