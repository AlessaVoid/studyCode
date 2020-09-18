package com.boco.PUB.controller.tbPlanadjStripe;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjServiceStripe;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPlanadj;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description:  ���߼ƻ����� ���ύ
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjStripeSub")
public class TbLoanPlanadjStripeSubController extends BaseController {

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private TbPlanadjServiceStripe planadjServiceStripe;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebLoanCombService loanCombService;

    @RequestMapping("/loanSubmitAuditHistoryRecordUI")
    @SystemLog(tradeName = "���ύ���߼ƻ�", funCode = "PUB-03-06", funName = "���ύ���߼ƻ��б�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustSubmitted/loanPlanAdjustSubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "���ύ���߼ƻ�", funCode = "PUB-03-06", funName = "���ύ���߼ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(HttpServletRequest request,String auditStatus, String planMonth) throws Exception {
        authButtons();
        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");

        if ("planadjmonth".equals(sort)) {
            sort = "planadj_month";

        } else if ("planadjadjamount".equals(sort)) {
            sort = "planadj_adj_amount";

        } else if ("planadjrealincrement".equals(sort)) {
            sort = "planadj_real_increment";

        } else if ("planadjunit".equals(sort)) {
            sort = "planadj_unit";

        } else if ("planadjstatus".equals(sort)) {
            sort = "planadj_status";

        }else if ("planadjcreatetime".equals(sort)) {
            sort = "planadj_create_time";
        }
        if (sort != null) {
            sort = sort + " " + direction;
        }
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String, Object>> list = planadjServiceStripe.getAuditRecordHistRecord(sort,sessionOperInfo.getOperCode(), auditStatus,
                planMonth,getSessionOperInfo());

        return pageData(list);
    }





    @RequestMapping("/listTbPlanSubmitDetailAuditUI")
    @SystemLog(tradeName = "�鿴��������", funCode = "PUB-03-06-02", funName = "�鿴��������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(String planadjId, String processInstanceId) throws Exception {
        authButtons();

        //��ѯ�ƻ�����
        TbPlanadj tbPlanadj = planadjServiceStripe.selectByPK(planadjId);
        tbPlanadj.setPlanadjNetIncrement(tbPlanadj.getPlanadjNetIncrement().divide(new BigDecimal("10000")));
        //��ѯ�ƻ���������
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+tbPlanadj.getPlanadjId()+"\'");

        Map<String,Object> map=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //��Ԫת��Ԫ
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            map.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }

        //��ȡ�����·�
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());
        //��ȡ������
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ������ϼ��� ���������
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);


        //��ȡ������Ϣ
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

        setAttribute("comments", BocoUtils.translateComments(comments,BocoUtils.PLANTYPE));
        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustSubmitted/loanPlanAdjustSubmitDetailPage";
    }

    //�����ƻ�
    @RequestMapping(value = "/downloadPlan")
    @SystemLog(tradeName = "�����ƻ�", funCode = "PUB-03", funName = "�����ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //��ȡ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();
        try {
            planadjServiceStripe.downloadPlan(response,request,organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

