package com.boco.PUB.controller.lineOver;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbLineOverService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbLineOver;
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
 * @ClassName TbLineOverAppovedConteoller
 * @Description 条线临时额度已审批记录
 * @Author txn
 * @Date 20191117 下午2:27
 * @Version 2.0
 **/
@Controller
@RequestMapping("/lineOverApplyApproved")
public class TbLineOverAppovedConteoller extends BaseController {

    @Autowired
    private IFdOrganService organService;
    @Autowired
    private ITbLineOverService tbOverService;
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
    @SystemLog(tradeName = "已审批条线临时额度申请", funCode = "PUB-14-05", funName = "已审批条线临时额度申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/lineOverManage/approved/tbQuotaApplyApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "已审批条线临时额度申请", funCode = "PUB-14-05", funName = "已审批条线临时额度申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(String auditStatus, String qaMonth) throws Exception {
        setPageParam();
        List<Map<String, Object>> list = tbOverService.getApprovedRecord(getSessionOperInfo().getOperCode(), auditStatus, qaMonth);
        for (Map<String, Object> tbLineOver : list) {
            String qaAmtStr = String.valueOf(tbLineOver.get("qaamt"));
            BigDecimal total = BigDecimal.ZERO;
            String[] qaAmtArr = qaAmtStr.split(",");
            for (int i = 0; i < qaAmtArr.length; i++) {
                total = total.add(new BigDecimal(qaAmtArr[i]));
            }
            tbLineOver.put("qacreateoper", total.toString());
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
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-14-05-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int qaId, String processInstanceId) throws Exception {
        authButtons();
        TbLineOver tbQuotaApply = tbOverService.selectByPK(qaId);
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
        setAttribute("TbLineOver", tbQuotaApply);
        setAttr(tbQuotaApply);
        String fileId = tbQuotaApply.getQaFileId();
        String fileName = "暂无附件，请上传";
        if (!"".equals(fileId) && fileId.length() > 0) {
            fileName = fileId.substring(fileId.lastIndexOf("_-") + 2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/lineOverManage/approved/tbQuotaApplyApprovedDetailPage";
    }

    /**
     * 通用方法
     *
     * @param
     * @throws Exception
     */
    private void setAttr(TbLineOver tbOver) throws Exception {
        FdOrgan f = organService.selectByPK(tbOver.getQaOrgan());
        String organLevel = f.getOrganlevel();
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1).setOperCode(tbOver.getQaCreateOper());
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
        map1.put("name", "调整量");
        map1.put("code", "Num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("combList", combList);
    }
}
