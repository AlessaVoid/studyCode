package com.boco.PUB.controller.lineTemp;

import com.boco.PUB.service.lineTemp.ITbLineTempService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbLineTemp;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbLineTempSubController
 * @Description 条线临时额度申请已提交记录
 * @Author tangxn
 * @Date 20191117 下午2:20
 * @Version 2.0
 **/
@Controller
@RequestMapping("/lineTbLineTempSub")
public class TbLineTempSubController extends BaseController {

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private ITbLineTempService tbLineTempService;

    @RequestMapping("/loanSubmitRecordUI")
    @SystemLog(tradeName = "已提交条线临时额度申请", funCode = "PUB-11-03", funName = "已提交条线临时额度申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/lineTbQuotaMange/submitted/tbQuotaApplySubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "已提交条线临时额度申请", funCode = "PUB-11-03", funName = "已提交条线临时额度申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(String auditStatus, String qaMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String, Object>> list = tbLineTempService.getAuditRecordHistRecord(sessionOperInfo.getOperCode(), auditStatus,
                qaMonth);
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

    @RequestMapping("/listReqSubmitDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "PUB-11-03-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int qaId, String processInstanceId) throws Exception {
        authButtons();

        TbLineTemp tbQuotaApply = tbLineTempService.selectByPK(qaId);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        String oneInfo = tbQuotaApply.getQaOneInfo();
        String twoInfo = tbQuotaApply.getQaTwoInfo();
        String threeInfo = tbQuotaApply.getQaThreeInfo();
        setAttribute("oneNum", oneInfo.split("_")[0]);
        setAttribute("oneRate", oneInfo.split("_")[1]);
        setAttribute("twoNum", twoInfo.split("_")[0]);
        setAttribute("twoRate", twoInfo.split("_")[1]);
        setAttribute("threeNum", threeInfo.split("_")[0]);
        setAttribute("threeRate", threeInfo.split("_")[1]);
        setAttribute("qaId", qaId);
         setAttribute("comments",  BocoUtils.translateComments(comments,"over"));
        setAttribute("TbLineTemp", tbQuotaApply);
        String fileId=tbQuotaApply.getQaFileId();
        String fileName="暂无附件，请上传";
        if(!"".equals(fileId)&&fileId.length()>0){
            fileName =fileId.substring(fileId.lastIndexOf("_-")+2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/lineTbQuotaMange/submitted/tbQuotaApplySubmitDetailPage";
    }


}
