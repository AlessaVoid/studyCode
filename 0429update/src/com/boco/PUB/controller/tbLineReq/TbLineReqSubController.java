package com.boco.PUB.controller.tbLineReq;

import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.lineReq.ILineReqAppService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
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
 * @Description 信贷需求审批已提交记录
 * @Author daice
 * @Date 2019/11/14 下午9:16
 * @Version 2.0
 **/
@Controller
@RequestMapping("/TbLineReqSub")
public class TbLineReqSubController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    ILineReqAppService lineReqAppService;
    @Autowired
    ITbLineReqDetailService tbLineReqDetailService;
    @Autowired
    IWorkFlowService workFlowService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "已提交信贷需求", funCode = "PUB-09", funName = "已提交信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqManage/submitted/loanReqSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "已提交信贷需求", funCode = "PUB-09", funName = "已提交信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(String auditStatus, String lineReqMonth) throws Exception {
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        if ("请选择".equals(lineReqMonth)) {
            lineReqMonth = "";
        }
        List<Map<String, Object>> list = lineReqAppService.getAuditRecordHistRecord(sessionOperInfo, auditStatus);
        if (!"".equals(lineReqMonth) && lineReqMonth != null && lineReqMonth.trim().length() > 0 && ("".equals(auditStatus) || auditStatus == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (lineReqMonth.equals(map.get("linereqmonth"))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0 && ("".equals(lineReqMonth) || lineReqMonth == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("state")))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0 && (!"".equals(lineReqMonth) && lineReqMonth != null && lineReqMonth.trim().length() > 0)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("state")))) {
                    if (lineReqMonth.equals(map.get("linereqmonth"))) {
                        tempList.add(map);
                    }
                }
            }
            return pageData(tempList);
        }


        return pageData(list);
    }

    @RequestMapping("/listReqSubmitDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-09-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int lineReqId, String processInstanceId) throws Exception {
        authButtons();
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("tbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        setAttr(tbLineReqDetail);
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("lineReqId", lineReqId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB//tbLineReqManage/submitted/loanReqSubmitDetailPage";
    }

    /**
     * 通用方法
     *
     * @param tbLineReqDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqDetail tbLineReqDetail) throws Exception {
        String combCodeStr = tbLineReqDetail.getLineCombCode();
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


}
