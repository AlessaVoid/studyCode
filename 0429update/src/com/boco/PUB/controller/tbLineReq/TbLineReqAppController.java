package com.boco.PUB.controller.tbLineReq;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.lineReq.ILineReqAppService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
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
 * @ClassName TbLoanReqAppController
 * @Description �Ŵ�����¼���ύ
 * @Author txn
 * @Date 2019/11/14 ����6:30
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbLineReqApp/")
public class TbLineReqAppController extends BaseController {
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private ITbLineReqDetailService tbLineReqDetailService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IFdOperServer fdOperServer;
    @Autowired
    private ILineReqAppService lineReqAppService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private WebOperLineMapper webOperLineMapper;


    @RequestMapping("commitTbReqUI")
    @SystemLog(tradeName = "PUB-09-01", funCode = "PUB-09-01", funName = "�ύ�Ŵ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int lineReqId) throws Exception {
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("tbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        setAttr(tbLineReqDetail);

        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.REQ_TOTAL_LINE_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.REQ_ONE_LINE_KEY;
            auditorPrefix = AuditMix.REQ_BASE_AUDITOR_PREFIX;
        } else if ("2".equals(organLevel)) {

        }

        //ͨ������key��ȡ���°汾��id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();
        map.put("organLevel", getSessionOrgan().getOrganlevel());
        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);
        return basePath + "/PUB/tbLineReqManage/submit/tbLineReqListCommitPage";
    }


    /**
     * ͨ�÷���
     *
     * @param tbLineReqDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqDetail tbLineReqDetail) throws Exception {
        String combCodeStr = tbLineReqDetail.getLineCombCode();
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //����������������������ʡ����Ǳ�����
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "������");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "Ԥ���·�������(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "Ԥ����ĩʱ�����");
        map4.put("code", "balance");
        TbReqList tbReqList = tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
        String numTypeStr = tbReqList.getNumType();
        if (numTypeStr.contains("1")) {
            combAmountNameList.add(map1);
        }
        if (numTypeStr.contains("2")) {
            combAmountNameList.add(map2);
        }
        if (numTypeStr.contains("4")) {
            combAmountNameList.add(map3);
        }
        if (numTypeStr.contains("8")) {
            combAmountNameList.add(map4);
        }
        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combList.add(combMap);
                    break;
                }
            }
        }
        setAttribute("combList", combList);
    }


    @ResponseBody
    @RequestMapping("/getOperInfoListByRolecode")
    @SystemLog(tradeName = "��ȡ����������Ա", funCode = "PUB-01", funName = "��ȡ����������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode, String lineReqId) throws Exception {
        authButtons();
        List<FdOper> fdOperList;
        fdOperList = fdOperServer.getOperByRolecode(getSessionOrgan().getThiscode(), rolecode, getSessionOperInfo().getOperCode());

        String lineCodeStr = tbLineReqDetailService.selectByPK(Integer.parseInt(lineReqId)).getLineCode();
        List<FdOper> newFdOperList = new ArrayList<>();
        for (FdOper oper : fdOperList) {
            WebOperLineDO searchOper = new WebOperLineDO();
            searchOper.setOperCode(oper.getOpercode());
            searchOper.setStatus(1);
            List<WebOperLineDO> tempList = webOperLineMapper.getAllWebOperLineByOperCode(searchOper);
            if (tempList != null && tempList.size() > 0) {
                for (WebOperLineDO tempOper : tempList) {
                    if (lineCodeStr.equals(tempOper.getLineId())) {
                        newFdOperList.add(oper);
                        break;
                    }
                }
            }
        }
        if (newFdOperList.size() == 0) {
            return JSON.toJSONString(fdOperList);
        }
        return JSON.toJSONString(newFdOperList);
    }

    /**
     * @return java.lang.String
     * @Author txn
     * @Description //������������
     * @Date ����8:55 2019/11/14
     * @Param [lineReqId, auditOper]
     **/
    @ResponseBody
    @RequestMapping("/startLoanReqAudit")
    @SystemLog(tradeName = "������������", funCode = "PUB-09-01", funName = "������������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int lineReqId, String auditOper, String comment) throws Exception {
        PlainResult<String> result = lineReqAppService.startLoanReqAuditProcess(lineReqId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, comment);
        return JSON.toJSONString(result);
    }
}
