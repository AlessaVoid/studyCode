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
 * ��ʱ����������
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
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

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyList";
    }


    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("showUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-02", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String showUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbQuotaMange/tbQuotaApplyShow/tbQuotaApplyListShow";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01-03", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbQuotaApply tbQuotaApply) throws Exception {
        tbQuotaApply = tbQuotaApplyService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbQuotaApply", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyInfo";
    }

    @RequestMapping("infoShowUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-02-01", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoShowUI(TbQuotaApply tbQuotaApply) throws Exception {
        setAttribute("TbQuotaApply", tbQuotaApplyService.selectByPK(tbQuotaApply.getQaId()));
        return basePath + "/PUB/tbQuotaMange/tbQuotaApplyShow/tbQuotaApplyInfoShow";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyAdd";
    }


    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbQuotaApply tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApply = tbQuotaApplyService.selectByPK(tbQuotaApply.getQaId());
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        setAttribute("TbQuotaApply", tbQuotaApply);
        return basePath + "/PUB/tbQuotaMange/tbQuotaApply/tbQuotaApplyEdit";
    }


    /**
     * TODO ��ѯ�������tb_quota_apply��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbQuotaApply tbQuotaApply) throws Exception {
        setPageParam();
        tbQuotaApply.setQaOrgan(getSessionOperInfo().getOrganCode());
        tbQuotaApply.setQaState(TbQuotaApply.STATE_NEW);
        List<TbQuotaApply> list = tbQuotaApplyService.selectByAttr(tbQuotaApply);
        return pageData(list);
    }


    /**
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB-04", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
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
            return this.json.returnMsg("false", "�û���������δ�ƶ��ƻ�!").toJson();
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
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB3", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * TODO ��ѯ�¼���д��tb_quota_apply��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-04-02", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showPage(TbQuotaApply tbQuotaApply) throws Exception {
        setPageParam();
        //TODO ����鿴�¼�������������Ϣ
        List<TbQuotaApply> list = tbQuotaApplyService.selectByAttr(tbQuotaApply);
        return pageData(list);
    }

    /**
     * TODO ����tb_quota_apply.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //������Ա
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
            return this.json.returnMsg("false", "����ʧ�ܣ���ȷ��������ʵ����!").toJson();
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
            return this.json.returnMsg("false", "����ʧ�ܣ��˻����ĸô�����ϼƻ������δ��Ч!").toJson();
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
        tbQuotaApply.setQaFileId(fileName);//�ϴ�����id
        // todo ������ϵͳ�������
        tbQuotaApply.setQaOneInfo("0_0");
        tbQuotaApply.setQaTwoInfo("0_0");
        tbQuotaApply.setQaThreeInfo("0_0");
        tbQuotaApply.setQaOverAmt(new BigDecimal("0"));
        tbQuotaApply.setQaPlanAmt(new BigDecimal("0"));
        tbQuotaApply.setQaYearRate(new BigDecimal(0));
        tbQuotaApplyService.insertEntity(tbQuotaApply);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }


    /**
     * TODO ����tb_quota_apply.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "checkPlan")
    @SystemLog(tradeName = "��������", funCode = "PUB-04", funName = "У��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String checkPlan(HttpServletRequest request, HttpSession session) throws Exception {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        TbQuotaAllocate searchTb = new TbQuotaAllocate();
        searchTb.setPaMonth(sdf.format(now));
        searchTb.setPaOrgan(getSessionOrgan().getThiscode());
        List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
        if (listResult == null || listResult.size() == 0) {
            return this.json.returnMsg("false", "����ʧ�ܣ��û����ƻ������δ��Ч,��ʱ�޷�������ʱ���!").toJson();
        }

        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }


    /**
     * TODO ����tb_quota_apply.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insertDraft")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insertDraft(TbQuotaApply tbQuotaApply, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaState(TbQuotaApply.STATE_DRAFT);
        tbQuotaApplyService.insertEntity(tbQuotaApply);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�tb_quota_apply.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //������Ա
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
                return this.json.returnMsg("false", "����ʧ�ܣ���ȷ��������ʵ����!").toJson();
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
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��tb_quota_apply
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "PUB-04-01-05", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbQuotaApply tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApplyService.deleteByPK(tbQuotaApply.getQaId());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectQaId")
    @SystemLog(tradeName = "ʱ��ƻ�ά��", funCode = "PUB-01-04", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectQaMonth")
    @SystemLog(tradeName = "ʱ��ƻ�ά��", funCode = "PUB-01-04", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
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