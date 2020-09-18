package com.boco.AL.controller.punish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.AL.service.ITbPunishListService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbOverCommitController
 * @Description ��Ϣ������� �ύ
 * @Author tangxn
 * @Date 2019-11-14 ����8:15
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/tbTradeManger/punishListCommit/")
public class TbPunishCommitController extends BaseController {
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    private ITbPunishListService tbPunishListService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("commitTbQuotaUI")
    @SystemLog(tradeName = "AL", funCode = "AL", funName = "��Ϣ��������ύ", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int id) throws Exception {
        //��ȡ�Ŵ�����
        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);
        setAttribute("TbPunishList", tbPunishList);
        setAttr(tbPunishList);
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();

        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        /*����*/
        String lineName = getSessionOperInfo().getLineName();

        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        auditorPrefix = AuditMix.PUNISH_BASE_AUDITOR_PREFIX;


        //ͨ������key��ȡ���°汾��id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);

        return basePath + "/AL/tbPunishManage/tbPunishList/punishList/CommitPage";

    }


    /**
     * @param tbPunishList ��Ϣ�б�
     * @throws Exception
     */
    public void setAttr(TbPunishList tbPunishList) throws Exception {

        FdOrgan thisOrgan = getSessionOrgan();
        List<Map<String, Object>> organList = organService.selectByUporgan(thisOrgan.getThiscode());
        setAttribute("organList", organList);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();

        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "״̬");
        map0.put("code", "state");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "�����ܼƻ�(��Ԫ)");
        map1.put("code", "planMount");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "��ĩ���ö��(��Ԫ)");
        map2.put("code", "monthVacancyAmt");
        combAmountNameList.add(map2);
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "��ĩ������(%)");
        map3.put("code", "monthVacancyRate");
        combAmountNameList.add(map3);
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "�����÷�(��Ԫ)");
        map4.put("code", "monthFiveVacancy");
        combAmountNameList.add(map4);
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "ʵ�忼�˼ƻ�(��Ԫ)");
        map5.put("code", "monthShitiPlanMount");
        combAmountNameList.add(map5);
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "ʵ����ĩ���ƻ����(��Ԫ)");
        map6.put("code", "monthShitiOverAmt");
        combAmountNameList.add(map6);
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "ʵ����ĩ���ƻ�����(%)");
        map7.put("code", "monthShitiOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "ʵ�峬�ƻ���(��Ԫ)");
        map7.put("code", "monthFiveShitiOver");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ�ݿ��˼ƻ�(��Ԫ)");
        map7.put("code", "monthPiapjuPlanMount");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ����ĩ���ƻ����(��Ԫ)");
        map7.put("code", "monthPiaojuOverAmt");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ����ĩ���ƻ�����(%)");
        map7.put("code", "monthPiaojuOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ�ݳ��ƻ���(��Ԫ)");
        map7.put("code", "monthFivePiaojuOver");
        combAmountNameList.add(map7);
        Map<String, String> map8 = new HashMap<>(2);
        map8.put("name", "��Ϣ�ܼ�(��Ԫ)");
        map8.put("code", "monthTotalPunish");
        combAmountNameList.add(map8);
        map7 = new HashMap<>(2);
        map7.put("name", "�������");
        map7.put("code", "note");
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);

    }

    @ResponseBody
    @RequestMapping("getOperInfoListByRolecode")
    @SystemLog(tradeName = "��Ϣ�������", funCode = "AL", funName = "��ȡ��Ϣ���������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        String thisOrganCode = getSessionOperInfo().getOrganCode();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(thisOrganCode, rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (FdOper tb : fdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }


    /**
     * @return java.lang.String
     * @Author txn
     * @Description //������������
     * @Date 2019/11/17
     * @Param [qaId, auditOper]
     **/
    @ResponseBody
    @RequestMapping("startLoanQuotaApplyAudit")
    @SystemLog(tradeName = "������������", funCode = "AL", funName = "������������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int id, String auditOper) throws Exception {
        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);
        /*1����Ϣ�����*/
        /*����key*/
        String processKey = "";

        processKey = AuditMix.PUNISH_TOTAL_MECH_KEY;

        ProcessInstance pi = tbPunishListService.startLoanReqAuditProcess(id, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, processKey);
        PlainResult<String> result = tbPunishListService.compleLoanReqAuditProcess(pi, getSessionOperInfo().getOperCode(), auditOper, id, getSessionOperInfo().getOrganCode());
        return JSON.toJSONString(result);
    }


}
