package com.boco.PUB.controller.tbPlanadjStripe;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjServiceStripe;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbPlanadj;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: �ύ�������߼ƻ�����
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjStripeApp")
public class TbLoanPlanadjStripeAppController extends BaseController {

    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanadjServiceStripe planadjServiceStripe;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IWebLoanCombService loanCombService;

    /*���߼ƻ�����ҳ��*/
    @RequestMapping("/commitTbPlanadjUI")
    @SystemLog(tradeName = "�ύ���߼ƻ�����", funCode = "PUB-06-01-05", funName = "�ύ���߼ƻ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbPlanUI(@RequestParam String planadjId) throws Exception {
        authButtons();

        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_LINE;
            auditorPrefix = AuditMix.PLAN_RESET_TOTAL_LINE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_LINE;
            auditorPrefix = AuditMix.PLAN_RESET_ONE_LINE_AUDITOR_PREFIX;
        }

        //��ѯ�ƻ�����
        TbPlanadj tbPlanadj = planadjServiceStripe.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //��ѯ�ƻ���������
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //��Ԫת��Ԫ
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //��ȡ�����·�
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //��ȡ������
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ������ϼ��� ���������
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);


        //ͨ������key��ȡ���°汾��id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> usermap = new HashMap<>();
        usermap.put("organLevel", getSessionOrgan().getOrganlevel());
        usermap.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey,ProcessDefinitionId , auditorPrefix, map);


        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);
        setAttribute("rolecode", rolecode);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustSubmit/loanPlanAdjustDetailCommitPage";
    }

    @ResponseBody
    @RequestMapping("/startLoanPlanadjAudit")
    @SystemLog(tradeName = "������������", funCode = "PUB-06-01", funName = "������������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(String planadjId, String auditOper,String comment) throws Exception {
        PlainResult<String> result = planadjServiceStripe.startLoanReqAuditProcess(planadjId, getSessionOperInfo().getOrganCode(),
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
