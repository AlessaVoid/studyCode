package com.boco.PUB.controller.tbOver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.IFdOperServer;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbOverService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbOver;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbOverCommitController
 * @Description ������ʱ������� �ύ
 * @Author tangxn
 * @Date 2019-11-14 ����8:15
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/tbTradeManger/overCommit/")
public class TbOverCommitController extends BaseController {
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    IFdOperServer fdOperServer;
    @Autowired
    IFdOrganService fdOrganService;
    @Autowired
    private ITbOverService tbLineTempService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("commitTbQuotaUI")
    @SystemLog(tradeName = "PUB-13-01", funCode = "PUB-13-01", funName = "������ʱ��������ύ", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String commitTbReqUI(@RequestParam int qaId) throws Exception {
        //��ȡ�Ŵ�����
        TbOver tbQuotaApply = tbLineTempService.selectByPK(qaId);
        setAttr(tbQuotaApply);
        //TODO ����������ݿ�����
        setAttribute("TbOver", tbQuotaApply);
        String oneInfo = tbQuotaApply.getQaTwoInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);
        //��ȡ��һ�ڵ������˽�ɫ
        Map<String, Object> map = new HashMap<>();

        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);
        List<TbTradeParam> list = tbTradeParamService.selectByAttr(searchTbTradeParam);
        /*���޶��׼��*/
        BigDecimal overNum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            overNum = list.get(0).getParamOverMount();
        }
        /*��������д���*/
        String qaAmtStr = tbQuotaApply.getQaAmt();
        String[] amtStrArr = qaAmtStr.split(",");
        BigDecimal qaAmt = new BigDecimal(0);
        for (int i = 0; i < amtStrArr.length; i++) {
            qaAmt = qaAmt.add(new BigDecimal(amtStrArr[i]));
        }
        int unit = tbQuotaApply.getUnit();
        BigDecimal unitNum = BigDecimal.ONE;
        if (unit == 2) {
            unitNum = new BigDecimal(10000);
        }
        qaAmt = qaAmt.abs().multiply(unitNum);
        /*����key*/
        String processKey = "";
        /*��ʼ������*/
        String auditorPrefix = "";
        /*��������*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        /*��������*/
        String lineName = getSessionOperInfo().getLineName();

        if ("0".equals(organLevel1)) {

        } else if ("1".equals(organLevel1)) {
            processKey = AuditMix.OVER_MECH_KEY;
            if (qaAmt.compareTo(overNum) == 1) {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_HIGH_BASE_AUDITOR_PREFIX;
            } else {
                auditorPrefix = AuditMix.REQ_QUOTA_ONE_LOW_BASE_AUDITOR_PREFIX;
            }
        } else if ("2".equals(organLevel1)) {
            processKey = AuditMix.OVER_MECH_KEY;
            if (qaAmt.compareTo(overNum) == 1) {
                auditorPrefix = AuditMix.REQ_QUOTA_TWO_HIGH_BASE_AUDITOR_PREFIX;
            } else {
                auditorPrefix = AuditMix.REQ_QUOTA_TWO_LOW_BASE_AUDITOR_PREFIX;
            }
        }

        //ͨ������key��ȡ���°汾��id
        List<ProcessDefinition> processDefinitionList = workFlowService.getProcessDefinitionList(processKey);
        String ProcessDefinitionId = processDefinitionList.get(0).getId();

        map.put("custType", "1");
        String rolecode = webTaskRoleInfoService.getAppUserRole(processKey, ProcessDefinitionId, auditorPrefix, map);
        setAttribute("rolecode", rolecode);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "���޸��������ϴ�";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);

        return basePath + "/PUB/tbOverManage/tbQuotaApply/tbQuotaListCommitPage";

    }

    /**
     * ͨ�÷���
     *
     * @param
     * @throws Exception
     */
    private void setAttr(TbOver tbOver) throws Exception {
        TbPlan searchTb = new TbPlan();
        searchTb.setPlanOrgan(getSessionOrgan().getUporgan());
        searchTb.setPlanMonth(tbOver.getQaMonth());
        searchTb.setPlanType(TbPlan.PLAN);

        List<TbPlan> tbPlanList = tbPlanService.selectByAttr(searchTb);
        String organLevel = String.valueOf(tbPlanList.get(0).getCombLevel());
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(tbOver.getQaCreateOper());
        /*�õ���ǰ��¼�û���Ͻ�����б�*/
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
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(1);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        } else if ("2".equals(organLevel)) {
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(2);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        }
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("combList", combList);
    }

    @ResponseBody
    @RequestMapping("getOperInfoListByRolecode")
    @SystemLog(tradeName = "������ʱ�������", funCode = "PUB-13-01", funName = "��ȡ������ʱ���������Ա", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getOperInfoListByRolecode(String rolecode) throws Exception {
        authButtons();
        String thisOrganCode = getSessionOperInfo().getOrganCode();
        List<FdOper> fdOperList = fdOperServer.getOperByRolecode(thisOrganCode, rolecode, getSessionOperInfo().getOperCode());
        org.json.JSONObject listObj = new org.json.JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (FdOper tb : fdOperList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getOpercode());
            jsonObject.put("key", tb.getOpername() + "-" + tb.getOperpassword());
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
    @SystemLog(tradeName = "������������", funCode = "PUB-01", funName = "������������", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String startLoanReqAudit(int qaId, String auditOper, String comment) throws Exception {
        TbOver tbQuotaApply = tbLineTempService.selectByPK(qaId);
        /*1��������ʱ��ȣ�2����ר��*/
        String month = tbQuotaApply.getQaMonth();
        TbTradeParam searchTbTradeParam = new TbTradeParam();
        searchTbTradeParam.setParamPeriod(month);
        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel1 = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel1)) {
        } else if ("1".equals(organLevel1)) {
            processKey = AuditMix.OVER_MECH_KEY;
        } else if ("2".equals(organLevel1)) {
            processKey = AuditMix.OVER_MECH_KEY;
        }

        ProcessInstance pi = tbLineTempService.startLoanReqAuditProcess(qaId, getSessionOperInfo().getOrganCode(),
                getSessionOperInfo().getOperCode(), getSessionOperInfo().getOperName(), auditOper, processKey);
        PlainResult<String> result = tbLineTempService.compleLoanReqAuditProcess(pi, getSessionOperInfo().getOperCode(), auditOper, qaId, getSessionOperInfo().getOrganCode(), comment);
        return JSON.toJSONString(result);
    }


}