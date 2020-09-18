package com.boco.loanreq.controller;

import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.loanreq.service.ILoanReqAppService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbLoanReqSubController
 * @Description 信贷需求审批已审批记录
 * @Author daice
 * @Date 2019/11/14 下午9:16
 * @Version 2.0
 **/
@Controller
@RequestMapping("/TbLoanReqApproved")
public class TbLoanReqApprovedController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    ILoanReqAppService loanReqAppService;
    @Autowired
    ITbReqListService tbReqListService;
    @Autowired
    ITbReqDetailService tbReqDetailService;
    @Autowired
    IWorkFlowService workFlowService;

    @RequestMapping("/loanApprovedAuditHistoryRecordUI")
    @SystemLog(tradeName = "已审批信贷需求", funCode = "PUB-01-06", funName = "已审批信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanReqApp/reqApprovd/loanReqApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "已审批信贷需求", funCode = "PUB-01", funName = "已审批信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(String auditStatus, String reqMonth) throws Exception {
        setPageParam();
        if ("请选择".equals(reqMonth)) {
            reqMonth = "";
        }
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        List<Map<String, Object>> list = loanReqAppService.getApprovedRecord(sessionOperInfo, auditStatus);
        if (!"".equals(reqMonth) && reqMonth != null && reqMonth.trim().length() > 0 && ("".equals(auditStatus) || auditStatus == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (reqMonth.equals(map.get("reqmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0 && ("".equals(reqMonth) || reqMonth == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("state")))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0 && (!"".equals(reqMonth) && reqMonth != null && reqMonth.trim().length() > 0)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("state")))) {
                    if (reqMonth.equals(map.get("reqmonth"))) {
                        tempList.add(map);
                    }
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }

    @RequestMapping("/listReqApprovedDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-01-06-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int reqId, String processInstanceId) throws Exception {
        authButtons();
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        if (tbReqList.getExpTimeStart() != null) {
            tbReqList.setReqDateStart(tbReqList.getReqDateStart().substring(0, 4) + "-" + tbReqList.getReqDateStart().substring(4, 6) + "-" + tbReqList.getReqDateStart().substring(6, 8));
            tbReqList.setReqDateEnd(tbReqList.getReqDateEnd().substring(0, 4) + "-" + tbReqList.getReqDateEnd().substring(4, 6) + "-" + tbReqList.getReqDateEnd().substring(6, 8));
            tbReqList.setReqTimeStart(tbReqList.getReqTimeStart().substring(0, 4) + "-" + tbReqList.getReqTimeStart().substring(4, 6) + "-" + tbReqList.getReqTimeStart().substring(6, 8));
            tbReqList.setReqTimeEnd(tbReqList.getReqTimeEnd().substring(0, 4) + "-" + tbReqList.getReqTimeEnd().substring(4, 6) + "-" + tbReqList.getReqTimeEnd().substring(6, 8));
            tbReqList.setExpTimeStart(tbReqList.getExpTimeStart().substring(0, 4) + "-" + tbReqList.getExpTimeStart().substring(4, 6) + "-" + tbReqList.getExpTimeStart().substring(6, 8));
            tbReqList.setExpTimeEnd(tbReqList.getExpTimeEnd().substring(0, 4) + "-" + tbReqList.getExpTimeEnd().substring(4, 6) + "-" + tbReqList.getExpTimeEnd().substring(6, 8));
            tbReqList.setRateTimeStart(tbReqList.getRateTimeStart().substring(0, 4) + "-" + tbReqList.getRateTimeStart().substring(4, 6) + "-" + tbReqList.getRateTimeStart().substring(6, 8));
            tbReqList.setRateTimeEnd(tbReqList.getRateTimeEnd().substring(0, 4) + "-" + tbReqList.getRateTimeEnd().substring(4, 6) + "-" + tbReqList.getRateTimeEnd().substring(6, 8));
            tbReqList.setBalanceTimeStart(tbReqList.getBalanceTimeStart().substring(0, 4) + "-" + tbReqList.getBalanceTimeStart().substring(4, 6) + "-" + tbReqList.getBalanceTimeStart().substring(6, 8));
            tbReqList.setBalanceTimeEnd(tbReqList.getBalanceTimeEnd().substring(0, 4) + "-" + tbReqList.getBalanceTimeEnd().substring(4, 6) + "-" + tbReqList.getBalanceTimeEnd().substring(6, 8));
        }
        setAttribute("TbReqListDTO", tbReqList);
        setAttribute("reqId", reqId);
        setAttr(tbReqList);
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("reqId", reqId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB/LoanReqApp/reqApprovd/loanReqApprovedDetailPage";
    }

    /**
     * 通用方法
     *
     * @param tbReqList
     * @throws Exception
     */
    private void setAttr(TbReqList tbReqList) throws Exception {
        int reqType = tbReqList.getReqType();
        String combListStr = "";
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "到期量");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "净增量");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "预计新发生利率(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "预计期末时点余额");
        map4.put("code", "balance");
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

        if (reqType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            combListStr = tbReqList.getReqCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combList.add(combMap);
                        break;
                    }
                }
            }
            setAttribute("combList", combList);
        }
    }


}
