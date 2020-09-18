package com.boco.PUB.controller.reqReset;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.loanReqReset.ILoanReqResetService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName submitedController
 * @Description ���ύ���Ŵ������������
 * @Author tangxn
 * @Date 20191122 ����10:24
 * @Version 2.0
 **/
@Controller
@RequestMapping(value = "/TbReqresetSub/")
public class submitedController extends BaseController {

    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private ILoanReqResetService loanReqResetService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private IFdOrganService fdOrganService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "���ύ�Ŵ��������", funCode = "PUB-05-04", funName = "���ύ�Ŵ���������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqResetManage/submitted/reqresetSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "���ύ�Ŵ��������", funCode = "PUB-05-04", funName = "���ύ�Ŵ��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(String auditStatus, String reqresetMonth) throws Exception {
        if ("��ѡ��".equals(reqresetMonth)) {
            reqresetMonth = "";
        }
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String, Object>> list = loanReqResetService.getAuditRecordHistRecord(sessionOperInfo, auditStatus);
        if (!"".equals(reqresetMonth) && reqresetMonth != null && reqresetMonth.trim().length() > 0 && ("".equals(auditStatus) || auditStatus == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (reqresetMonth.equals(map.get("reqresetmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0 && ("".equals(reqresetMonth) || reqresetMonth == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("reqresetstate")))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0 && (!"".equals(reqresetMonth) && reqresetMonth != null && reqresetMonth.trim().length() > 0)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("reqresetstate")))) {
                    if (reqresetMonth.equals(map.get("reqresetmonth"))) {
                        tempList.add(map);
                    }
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }

    @RequestMapping("/listReqSubmitDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-05-04-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int reqresetId, String processInstanceId) throws Exception {
        authButtons();

        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        setAttribute("reqresetId", reqresetId);
        setAttr(tbReqresetList);
        //��ȡ��ע
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        setAttribute("reqresetId", reqresetId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        int unit = tbReqresetList.getReqresetUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbReqresetList.getReqresetMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));
        return basePath + "/PUB/tbReqResetManage/submitted/reqresetSubmitDetailPage";
    }


    //�·�  ��Ҫ��ѯ�Ļ�����
    private BigDecimal getPlanCount(String month, String organ) throws Exception {

        //��ȡ�ϼ�����
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(organ);
        List<FdOrgan> fdOrgans = fdOrganService.selectByAttr(fdOrgan);
        String upOrgan = "";
        if (fdOrgans != null && fdOrgans.size() != 0) {
            upOrgan = fdOrgans.get(0).getUporgan();
        }

        //��ȡ�ƻ�
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(upOrgan);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlans != null && tbPlans.size() != 0) {
            planId = tbPlans.get(0).getPlanId();
        }

        //��ȡ�ƻ�����
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(planId);
        tbPlanDetail.setPdOrgan(organ);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

        //�����ܽ��
        BigDecimal planCount = BigDecimal.ZERO;
        for (TbPlanDetail planDetail : tbPlanDetails) {
            planCount = planCount.add(planDetail.getPdAmount());
        }
        return planCount;

    }


    /**
     * ͨ�÷���
     *
     * @param tbReqresetList
     * @throws Exception
     */
    private void setAttr(TbReqresetList tbReqresetList) throws Exception {
        int reqresetType = tbReqresetList.getReqresetType();
        String combListStr = "";

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //ԭ�ƻ�������������������

        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        String month = tbReqresetList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //û��ָ���·ݣ�����û�мƻ�
        } else {
            //������ �ƻ� ��ѯ���� ��Ҫ�޸�Ϊ�ϼ����� code
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 1);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }

        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbReqresetList.getReqresetUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }


        BigDecimal one_oldTotalNum = BigDecimal.ZERO;
        BigDecimal two_oldTotalNum = BigDecimal.ZERO;
        BigDecimal three_oldTotalNum = BigDecimal.ZERO;
        if (reqresetType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            combListStr = tbReqresetList.getReqresetCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                String codeStr = loanCombDO.getCombCode();
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(codeStr)) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combMap.put("oldNum", "0");
                        if (planIsOk) {
                            for (Map<String, Object> map : planList) {
                                String planCombCode = map.get("loantype").toString();
                                if (planCombCode.equals(codeStr)) {
                                    BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                    combMap.put("oldNum", oldPlan.divide(unit).toString());
                                    if (1 == loanCombDO.getCombLevel()) {
                                        one_oldTotalNum = BigDecimalUtil.add(one_oldTotalNum, oldPlan.divide(unit));
                                    } else if (2 == loanCombDO.getCombLevel()) {
                                        two_oldTotalNum = BigDecimalUtil.add(two_oldTotalNum, oldPlan.divide(unit));
                                    } else if (3 == loanCombDO.getCombLevel()) {
                                        three_oldTotalNum = BigDecimalUtil.add(three_oldTotalNum, oldPlan.divide(unit));
                                    }
                                    break;
                                }
                            }
                        }
                        combList.add(combMap);
                        break;
                    }
                }
            }

            setAttribute("combList", combList);
            setAttribute("one_oldTotalNum", one_oldTotalNum);
            setAttribute("two_oldTotalNum", two_oldTotalNum);
            setAttribute("three_oldTotalNum", three_oldTotalNum);
        }
    }


}
