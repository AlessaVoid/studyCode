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
 * ������ʱ����������
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
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



    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01-03", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbSingle tbQuotaApply) throws Exception {
        tbQuotaApply = tbSingleService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbSingle", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyInfo";
    }

    @RequestMapping("infoShowUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-12-02-01", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoShowUI(TbSingle tbQuotaApply) throws Exception {
        setAttribute("TbSingle", tbSingleService.selectByPK(tbQuotaApply.getQaId()));
        return basePath + "/PUB/tbSingle/tbQuotaApplyShow/tbQuotaApplyInfoShow";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbSingle tbQuotaApply, HttpSession session) throws Exception {
        tbQuotaApply = tbSingleService.selectByPK(tbQuotaApply.getQaId());
        setAttribute("TbSingle", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbSingle/tbQuotaApply/tbQuotaApplyEdit";
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
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbSingle tbQuotaApply) throws Exception {
        setPageParam();
        tbQuotaApply.setQaOrgan(getSessionOperInfo().getOrganCode());
        tbQuotaApply.setQaState(TbSingle.STATE_NEW);
        List<TbSingle> list = tbSingleService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB-12", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB-12", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
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
    @SystemLog(tradeName = "��������", funCode = "PUB-12-02", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showPage(TbSingle tbQuotaApply) throws Exception {
        setPageParam();
        //TODO ����鿴�¼�������������Ϣ
        List<TbSingle> list = tbSingleService.selectByAttr(tbQuotaApply);
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
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
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
            return this.json.returnMsg("false", "����ʧ�ܣ���ݺ��ظ�!").toJson();
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
            return this.json.returnMsg("false", "����ʧ�ܣ���ȷ��������ʵ����!").toJson();
        }
        tbQuotaApply.setQaSingleOrganName(QaSingleOrganName);
        tbQuotaApply.setUnit(unit);
        tbQuotaApply.setQaReason(qaReason);
        tbQuotaApply.setQaFileId(fileName);//�ϴ�����id
        //������ϵͳ�������
        tbQuotaApply.setQaOneInfo("0_0");
        tbQuotaApply.setQaTwoInfo("0_0");
        tbQuotaApply.setQaThreeInfo("0_0");
        tbQuotaApply.setQaOverAmt(new BigDecimal("0"));
        tbQuotaApply.setQaPlanAmt(new BigDecimal("0"));
        tbQuotaApply.setQaYearRate(new BigDecimal(0));
        tbSingleService.insertEntity(tbQuotaApply);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    //���޶������
    @RequestMapping(value = "/download")
    @SystemLog(tradeName = "�����ƻ�ģ��", funCode = "PUB3", funName = "�����ƻ�ģ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public HttpServletResponse downloadPlanTemplate(String qaFileId, HttpServletResponse response) throws Exception {

        //todo ��ȡ�ļ���
        String fileName = qaFileId;

        response = tbPlanService.downloadFile(fileName, response);
        return response;
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
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insertDraft(TbSingle tbQuotaApply, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbQuotaApply.setQaOrgan(organCode);
        tbQuotaApply.setQaCreateOper(fdOper.getOpercode());
        tbQuotaApply.setQaCreateTime(BocoUtils.getTime());
        tbQuotaApply.setQaState(TbSingle.STATE_DRAFT);
        tbSingleService.insertEntity(tbQuotaApply);
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
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
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
                return this.json.returnMsg("false", "����ʧ�ܣ���ݺ��ظ�!").toJson();
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
    @SystemLog(tradeName = "��������", funCode = "PUB-12-01-05", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbSingle tbQuotaApply, HttpSession session) throws Exception {
        tbSingleService.deleteByPK(tbQuotaApply.getQaId());
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
    @SystemLog(tradeName = "ʱ��ƻ�ά��", funCode = "PUB3", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectQaMonth")
    @SystemLog(tradeName = "ʱ��ƻ�ά��", funCode = "PUB3", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * TODO ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      �غ���      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectName")
    @SystemLog(tradeName = "����������������", funCode = "PUB3", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
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
    @SystemLog(tradeName="���ݻ����Ų�ѯ��������",funCode="PM-06", funName="��������", accessType=Dic.AccessType.READ, level=Dic.Debug.DEBUG)
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
    @SystemLog(tradeName="����������������", funCode="PM-06", funName="��������", accessType=Dic.AccessType.READ, level=Dic.Debug.DEBUG)
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


