package com.boco.PUB.controller.tbOver;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbOverService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbOver;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName TbOverAppovedConteoller
 * @Description 条线临时额度已审批记录
 * @Author tangxn
 * @Date 20191117 下午2:27
 * @Version 2.0
 **/
@Controller
@RequestMapping("/overApplyApproved")
public class TbOverAppovedConteoller extends BaseController {
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    private ITbOverService tbOverService;
    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @RequestMapping("/loanApprovedAuditHistoryRecordUI")
    @SystemLog(tradeName = "已审批条线临时额度申请", funCode = "PUB-13-05", funName = "已审批条线临时额度申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbOverManage/approved/tbQuotaApplyApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "已审批条线临时额度申请", funCode = "PUB-13-05", funName = "已审批条线临时额度申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(String auditStatus, String qaMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String, Object>> list = tbOverService.getApprovedRecord(sessionOperInfo.getOperCode(), auditStatus, qaMonth);
        for (Map<String, Object> tbLineOver : list) {
            String qaAmtStr = String.valueOf(tbLineOver.get("qaamt"));
            BigDecimal total = BigDecimal.ZERO;
            String[] qaAmtArr = qaAmtStr.split(",");
            for (int i = 0; i < qaAmtArr.length; i++) {
                total = total.add(new BigDecimal(qaAmtArr[i]));
            }
            tbLineOver.put("qacreateoper", total.toString());
            String appNumStr = String.valueOf(tbLineOver.get("qaoneinfo"));
            BigDecimal totalAppNum = BigDecimal.ZERO;
            String[] appAmtArr = appNumStr.split(",");
            for (int i = 0; i < appAmtArr.length; i++) {
                totalAppNum=totalAppNum.add(new BigDecimal(appAmtArr[i]));
            }
            tbLineOver.put("appTotalNum",totalAppNum.toString());
        }
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("qastate")))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }

    @RequestMapping("/approvedDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-13-05-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int qaId, String processInstanceId) throws Exception {
        authButtons();
        TbOver tbQuotaApply = tbOverService.selectByPK(qaId);
        setAttr(tbQuotaApply);
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        String oneInfo = tbQuotaApply.getQaTwoInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);
        setAttribute("qaId", qaId);
        setAttribute("comments", BocoUtils.translateComments(comments, "over"));
        setAttribute("TbOver", tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbOverManage/approved/tbQuotaApplyApprovedDetailPage";
    }

    /**
     * 通用方法
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
        int level = 2;
        if (tbPlanList != null && tbPlanList.size() > 0) {
            level = tbPlanList.get(0).getCombLevel();
        }
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(tbOver.getQaCreateOper());
        FdOrgan f = organService.selectByPK(tbOver.getQaOrgan());
        String organLevel = f.getOrganlevel();
        /*拿到当前登录用户所辖条线列表*/
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
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(level);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        } else if ("2".equals(organLevel)) {
            List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(level);
            for (LoanCombDO comb : loanCombDOS) {
                Map<String, String> combMap = new HashMap<>(2);
                combMap.put("combCode", comb.getCombCode());
                combMap.put("combName", comb.getCombName());
                combList.add(combMap);
            }
        }
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("combList", combList);
    }
}
