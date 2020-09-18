package com.boco.PUB.controller.tbSingle;

import com.boco.PUB.service.tbSingle.ITbSingleService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbSingle;
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
 * @ClassName TbSingleAppovedConteoller
 * @Description ������ʱ�����������¼
 * @Author tangxn
 * @Date 20191117 ����2:27
 * @Version 2.0
 **/
@Controller
@RequestMapping("/singleApplyApproved")
public class TbSingleAppovedConteoller extends BaseController {
    @Autowired
    private ITbSingleService tbSingleService;
    @Autowired
    IWorkFlowService workFlowService;

    @RequestMapping("/loanApprovedAuditHistoryRecordUI")
    @SystemLog(tradeName = "������������ʱ�������", funCode = "PUB-12-05", funName = "������������ʱ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbSingle/approved/tbQuotaApplyApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "������������ʱ�������", funCode = "PUB-12-05", funName = "������������ʱ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(String auditStatus,String qaMonth) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String,Object>> list= tbSingleService.getApprovedRecord(sessionOperInfo.getOperCode(), auditStatus,qaMonth);
        if(!"".equals(auditStatus)&&auditStatus!=null&&auditStatus.trim().length()>0){
            List<Map<String,Object>> tempList =new ArrayList<>();
            for(Map<String,Object> map:list){
                if(auditStatus.equals(String.valueOf(map.get("qastate")))){
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }

    @RequestMapping("/approvedDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-12-05-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int qaId,String processInstanceId) throws Exception {
        authButtons();
        TbSingle tbQuotaApply =tbSingleService.selectByPK(qaId);

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
        setAttribute("TbSingle", tbQuotaApply);
        String fileId=tbQuotaApply.getQaFileId();
        String fileName="���޸��������ϴ�";
        if(!"".equals(fileId)&&fileId.length()>0){
            fileName =fileId.substring(fileId.lastIndexOf("_-")+2);
        }
        setAttribute("fileName", fileName);
        return basePath + "/PUB/tbSingle/approved/tbQuotaApplyApprovedDetailPage";
    }

}
