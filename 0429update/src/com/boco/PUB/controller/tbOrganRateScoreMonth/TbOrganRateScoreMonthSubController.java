package com.boco.PUB.controller.tbOrganRateScoreMonth;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetail;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description: �¶ȼƻ��������ύ��¼
 * @Date: �¶�����
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbOrganRateScoreMonthSub")
public class TbOrganRateScoreMonthSubController extends BaseController {

    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "���ύ�¶������б�", funCode = "AL-03-02", funName = "���ύ�¶������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthSubmitted/tbOrganRateScoreMonthSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "���ύ�¶������б�����", funCode = "AL-03-02", funName = "���ύ�¶������б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(HttpServletRequest request,String auditStatus, String rateMonth) throws Exception {
        authButtons();
        List<Map<String, Object>> list = null;
        try {
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("rateMonth".equals(sort)) {
                sort = "rate_month";

            } else if ("rateType".equals(sort)) {
                sort = "rate_type";

            } else if ("rateStatus".equals(sort)) {
                sort = "rate_status";

            } else if ("createTime".equals(sort)) {
                sort = "create_time";

            } else if ("updateTime".equals(sort)) {
                sort = "update_time";

            }
            if (sort != null) {
                sort = sort + " " + direction;
            }
            WebOperInfo sessionOperInfo = getSessionOperInfo();
            setPageParam();
            list = tbOrganRateScoreService.getAuditRecordHistRecord(sort,sessionOperInfo.getOperCode(), auditStatus,
                    rateMonth,getSessionOperInfo(), OrganRateParamElementType.RATE_MONTH);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }


    @RequestMapping("/listRateScoreubmitDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "AL-03-02-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String id, String processInstanceId) throws Exception {
        authButtons();

        //��ѯ�¶�����
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //��ѯ��ע
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));
        setAttribute("organList", organList);

        return basePath +  "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthSubmitted/tbOrganRateScoreMonthSubmitDetailPage";
    }


}
