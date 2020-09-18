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
 * @Description: 月度评分 已审批记录
 * @Date: 2019/11/18
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbOrganRateScoreMonthApproved")
public class TbOrganRateScoreMonthApprovedController extends BaseController {


    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;


    @RequestMapping("/loadAssigneeAuditedTaskUI")
    @SystemLog(tradeName = "已审批月度评分列表页", funCode = "AL-03-04", funName = "已审批月度评分列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanApprovedAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthApprovd/tbOrganRateScoreMonthApprovedIndexList";
    }

    @ResponseBody
    @RequestMapping("/getApprovedAuditHistoryRecord")
    @SystemLog(tradeName = "已审批月度评分列表页数据", funCode = "AL-03-04", funName = "已审批月度评分列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getApprovedAuditHistoryRecord(HttpServletRequest request,String auditStatus, String rateMonth) throws Exception {
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
            list = tbOrganRateScoreService.getApprovedRecord(sort,sessionOperInfo.getOperCode(), auditStatus, rateMonth, OrganRateParamElementType.RATE_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }

    @RequestMapping("/listRateScoreApprovedDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "AL-03-04-02", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String id,String processInstanceId) throws Exception {
        authButtons();


        //查询月度评分
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //查询批注
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("organList", organList);
        setAttribute("rateScoreMap", rateScoreMap);
        setAttribute("id", id);
        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.RATESCORE));
        setAttribute("organList", organList);

        return basePath +  "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthApprovd/tbOrganRateScoreMonthApprovedDetailPage";

    }

}
