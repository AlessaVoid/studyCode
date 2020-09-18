package com.boco.PUB.controller.tbOrganRateScoreMonth;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetail;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: �ύ�����¶�����
 * @Date: 2020/02/05
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbOrganRateScoreMonthApp")
public class TbOrganRateScoreMonthAppController extends BaseController {


    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;



    @RequestMapping("/commiTbOrganRateScoreMonthUI")
    @SystemLog(tradeName = "�ύ�¶�����ҳ��", funCode = "AL-03-01-03", funName = "�ύ�¶�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbPlanUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        /*����key*/
        String processKey = AuditMix.RATE_SCORE;
        /*��ʼ������*/
        String auditorPrefix =AuditMix.RATE_SCORE_AUDITOR_PREFIX;


        //ͨ������key��ȡ���°汾��id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);

        String id = request.getParameter("id");

        //��ѯ�¶�����
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("rolecode", rolecode);

        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthSubmit/tbOrganRateScoreMonthDetailCommitPage";

    }

    @ResponseBody
    @RequestMapping("/startTbOrganRateScoreMonthAudit")
    @SystemLog(tradeName = "������������", funCode = "AL-03-01", funName = "������������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(String id, String auditOper,String comment) throws Exception {

        PlainResult<String> result = tbOrganRateScoreService.startRateScoreAuditProcess(id, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper,comment);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "PUB-01", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());
        return JSON.toJSONString(fdOperList);
    }

}
