package com.boco.PUB.controller.tbLineReqReset;

import com.boco.PUB.service.ITbLineReqresetDetailService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.lineReqReset.ILineReqResetAppService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbLineReqresetDetail;
import com.boco.SYS.entity.TbReqresetList;
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

import java.math.BigDecimal;
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
@RequestMapping("/TbLineReqResetSub")
public class TbLineReqResetSubController extends BaseController {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;

    @Autowired
    ILineReqResetAppService lineReqResetAppService;
    @Autowired
    ITbLineReqresetDetailService tbLineReqresetDetailService;
    @Autowired
    IWorkFlowService workFlowService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "已提交信贷需求", funCode = "PUB-10", funName = "已提交信贷需求列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqResetManage/submitted/loanReqSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "已提交信贷需求", funCode = "PUB-10", funName = "已提交信贷需求", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(String auditStatus, String lineReqMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        if ("请选择".equals(lineReqMonth)) {
            lineReqMonth = "";
        }
        List<Map<String, Object>> list = lineReqResetAppService.getAuditRecordHistRecord(sessionOperInfo, auditStatus);
        if (!"".equals(lineReqMonth) && lineReqMonth != null && lineReqMonth.trim().length() > 0 && ("".equals(auditStatus) || auditStatus == null)) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (lineReqMonth.equals(map.get("lineReqMonth"))) {
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
                    if (lineReqMonth.equals(map.get("lineReqMonth"))) {
                        tempList.add(map);
                    }
                }
            }
            return pageData(tempList);
        }


        return pageData(list);
    }

    @RequestMapping("/listReqSubmitDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-10-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int lineReqresetId, String processInstanceId) throws Exception {
        authButtons();
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        setAttr(tbLineReqresetDetail);
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("lineReqresetId", lineReqresetId);
        setAttribute("comments", BocoUtils.translateComments(comments, ""));
        return basePath + "/PUB//tbLineReqResetManage/submitted/loanReqSubmitDetailPage";
    }

    /**
     * 通用方法
     *
     * @param tbLineReqresetDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqresetDetail tbLineReqresetDetail) throws Exception {
        String combCodeStr = tbLineReqresetDetail.getLineCombCode();

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);

        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());

        String month = tbReqresetList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            //机构的 计划 查询条件 需要修改为上级机构 code
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


        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
            String codeStr = loanCombDO.getCombCode();
            for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combMap.put("oldNum", "0");
                    if (planIsOk) {
                        for (Map<String, Object> map : planList) {
                            String planCombCode = map.get("loantype").toString();
                            if (planCombCode.equals(codeStr)) {
                                BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                combMap.put("oldNum", oldPlan.divide(unit).toString());
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
    }


}
