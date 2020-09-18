package com.boco.PUB.controller.tbPlanadj;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
 * @Description:  �Ŵ��ƻ����� ������ѯ
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadj")
public class TbLoanPlanadjController extends BaseController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    @Autowired
    private TbPlanadjService planadjService;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;



    @RequestMapping("/loanPlanadjIndexPage")
    @SystemLog(tradeName = "�Ŵ��ƻ�������ҳ", funCode = "PUB-06-01", funName = "�Ŵ��ƻ�������ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanIndexPage() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustList/tbPlanAdjustList";
    }

    @RequestMapping("/loadAllLoanadjPlanInfo")
    @ResponseBody
    @SystemLog(tradeName = "�����Ŵ��ƻ������б�", funCode = "PUB-06-01", funName = "�����Ŵ��ƻ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadAllLoanPlanInfo(HttpServletRequest request) throws Exception {
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

        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        String planMonth = getParameter("planMonth");

        // Date date = new Date();
        // String month = sdf.format(date);
        setPageParam();
        Map<String, Object> jsonMap =planadjService.selectTbplanadjByMonth(sort,planMonth,Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        return JSON.toJSONString(jsonMap);
    }

    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tbPlanadjDetail")
    @SystemLog(tradeName = "�Ŵ��ƻ�", funCode = "PUB-03-05", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String tbPlanadjDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String month = request.getParameter("planMonth");
        String combLevelStr = request.getParameter("combLevel");
        //��ȡ������
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();
        //��ȡ������ϼ��� ���в�һ�����֣�һ�����������
        int combLevel = 0;
        try {
            combLevel = Integer.valueOf(combLevelStr).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //��ȡ�������
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        //��ȡ�Ŵ��ƻ���ϸ
        Map<String, Object> creditPlanList = planadjService.getCreditPlanDetail(month);
        //��ȡ�Ŵ��ƻ�
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        //��Ԫת��Ԫ
        for (TbPlan plan : tbPlans) {
            plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));
            plan.setPlanRealIncrement(plan.getPlanRealIncrement().divide(new BigDecimal("10000")));
        }

        //��ȡ��������
        Map<String, Object> reqList = planadjService.getReqDetail(month);

        //�Ƿ���ڼƻ�����
        Integer planadjStatus = -1;
        TbPlanadj queryPlanadj = new TbPlanadj();
        queryPlanadj.setPlanadjMonth(month);
        queryPlanadj.setPlanadjType(TbPlan.PLAN);
        queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
        List<TbPlanadj> tbPlanadjs = planadjService.selectByAttr(queryPlanadj);
        for (TbPlanadj tbPlanadj : tbPlanadjs) {
            if (TbReqDetail.STATE_APPROVED != tbPlanadj.getPlanadjStatus()) {
                if (TbReqDetail.STATE_NEW == tbPlanadj.getPlanadjStatus()) {
                    planadjStatus = TbReqDetail.STATE_NEW;
                } else if (TbReqDetail.STATE_DISMISSED == tbPlanadj.getPlanadjStatus()) {
                    planadjStatus = TbReqDetail.STATE_DISMISSED;
                } else if (TbReqDetail.STATE_APPROVALING == tbPlanadj.getPlanadjStatus()) {
                    planadjStatus = TbReqDetail.STATE_APPROVALING;
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("reqList", reqList);
        resultMap.put("creditPlanList", creditPlanList);
        resultMap.put("organList", organList);
        resultMap.put("combList", combList);
        resultMap.put("organlevel", organlevel);
        resultMap.put("planadjStatus", planadjStatus);

        // ���Ϊ�գ���˵������µ��Ŵ��ƻ� ��û�����������û���Ŵ��ƻ�
        if (tbPlans != null && tbPlans.size() > 0) {
            resultMap.put("plan", tbPlans.get(0));
        }
        return JSON.toJSONString(resultMap);
    }

    /*�Ŵ��ƻ���������¼��ҳ--��ȡ�·�*/
    @RequestMapping("/loadLoanPlanadjDetailInfoInsertPageBefore")
    @SystemLog(tradeName = "�Ŵ��ƻ����������·�¼��ҳ", funCode = "PUB-06-01-06", funName = "�Ŵ��ƻ����������·�¼��ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanadjDetailInfoInsertPageBefore() throws Exception {
        authButtons();
        String planMonth = getParameter("planMonth");
        setAttribute("planMonth",planMonth);
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjustAddBefore";
    }

    /*�Ŵ��ƻ���������¼��ҳ*/
    @RequestMapping("/loadLoanPlanadjDetailInfoInsertPage")
    @SystemLog(tradeName = "�Ŵ��ƻ����������·�¼��ҳ", funCode = "PUB-06-01-06", funName = "�Ŵ��ƻ����������·�¼��ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanadjDetailInfoInsertPage() throws Exception {
        authButtons();

        String planMonth = getParameter("planMonth");
        //��ȡ������
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());
        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ�������
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanMonth(planMonth);
        tbPlanParam.setPlanType(TbPlan.PLAN);
        tbPlanParam.setPlanOrgan(getSessionOrgan().getThiscode());
        tbPlanParam.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlanList = planService.selectByAttr(tbPlanParam);
        if (tbPlanList == null || tbPlanList.size() == 0) {
            return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjustAdd";
        }
        TbPlan plan = tbPlanList.get(0);
        int combLevel = plan.getCombLevel();
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        //��ȡ��������
        Map<String, Object> reqList = planadjService.getReqDetail(planMonth);
        //��ȡ�Ŵ��ƻ���ϸ
        Map<String, Object> creditPlanList = planadjService.getCreditPlanDetail(planMonth);

        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("organlevel", organlevel);
        setAttribute("combLevel", combLevel);
        setAttribute("planMonth", planMonth);
        setAttribute("reqList", reqList);
        setAttribute("creditPlanList", creditPlanList);
        setAttribute("plan", tbPlanList.get(0));

        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjustAdd";
    }

    /*�����Ŵ��ƻ�����*/
    @ResponseBody
    @RequestMapping("/savePlanadj")
    @SystemLog(tradeName = "�Ŵ��ƻ��������鱣��", funCode = "PUB-06-01-01", funName = "�Ŵ��ƻ��������鱣��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String savePlanadj(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        //�ϼ�����
        String uporgan = sessionOrgan.getUporgan();


        PlainResult<String> result  = planadjService.savePlanadj(request, operInfo.getOperCode(), sessionOrgan.getThiscode(),uporgan);
        return JSON.toJSONString(result);

    }

    /*�Ŵ��ƻ���������ҳ��*/
    @RequestMapping("/listTbPlanadjDetailAuditUI")
    @SystemLog(tradeName = "�Ŵ��ƻ���������ҳ��", funCode = "PUB-06-01-02", funName = "�Ŵ��ƻ���������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listTbPlanadjDetailAuditUI(String planadjId) throws Exception {
        authButtons();

        //��ѯ�ƻ�����
        TbPlanadj tbPlanadj = planadjService.selectByPK(planadjId);
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
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ�������
        int combLevel = tbPlanadj.getCombLevel();
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);
        setAttribute("organlevel", organlevel);

        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjustEdit";
    }

    /*�Ŵ��ƻ���������*/
    @RequestMapping("/updatePlanadj")
    @ResponseBody
    @SystemLog(tradeName = "�Ŵ��ƻ���������", funCode = "PUB-06-01-02", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String updatePlanadj(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        //�ϼ�����
        String uporgan = sessionOrgan.getUporgan();
        PlainResult<String> result = planadjService.updatePlanadj(request, operInfo.getOperCode(), sessionOrgan.getThiscode(),uporgan);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/deleteTbPlanadjDetail")
    @ResponseBody
    @SystemLog(tradeName = "ɾ���Ŵ��ƻ�����", funCode = "PUB-06-01-03", funName = "ɾ���Ŵ��ƻ�����", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String deleteTbPlanadjDetail(String planadjId) throws Exception {
        authButtons();
        PlainResult<String> result = planadjService.deleteTbPlanadjDetail(planadjId);
        return JSON.toJSONString(result);
    }




    /*�Ŵ��ƻ���������ҳ��*/
    @RequestMapping("/TbPlanadjInfo")
    @SystemLog(tradeName = "�Ŵ��ƻ���������ҳ��", funCode = "PUB-06-01-04", funName = "�Ŵ��ƻ���������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String TbPlanadjInfo(String planadjId) throws Exception {
        authButtons();

        //��ѯ�ƻ�����
        TbPlanadj tbPlanadj = planadjService.selectByPK(planadjId);
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
        // List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();
        //��ȡ�������
        int combLevel = tbPlanadj.getCombLevel();
        List<Map<String, Object>> combList = planadjService.getCombList(combLevel);

        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);

        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjustInfo";
    }

    //����¼��ƻ�����ҳ��
    @RequestMapping(value = "/toEnterReport")
    @SystemLog(tradeName = "����¼��ƻ�����ҳ��", funCode = "PUB-06-01", funName = "����¼��ƻ�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjust/LoanPlanAdjustDetail/loanPlanAdjEnterReport";
    }

    //�����ƻ�����ģ��
    @RequestMapping(value = "/downPlanadjTemplate")
    @SystemLog(tradeName = "�����ƻ�ģ��", funCode = "PUB-06-01", funName = "�����ƻ�ģ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downPlanadjTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String type = request.getParameter("type");
            //��ȡ��¼��������
            String organlevel = getSessionOrgan().getOrganlevel();

            planadjService.downPlanadjTemplate(request,type,response, organlevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //����ƻ�����
    @ResponseBody
    @RequestMapping(value = "/enterReportPlanadjByMonth", method = RequestMethod.POST)
    @SystemLog(tradeName = "����ƻ�����", funCode = "PUB-06-01", funName = "����ƻ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //��ǰ��¼��
            String operCode = getSessionOperInfo().getOperCode();
            //��ǰ��¼����
            String organCode = getSessionOrgan().getThiscode();
            //��ǰ��¼��������
            String organlevel = getSessionOrgan().getOrganlevel();
            //�ϼ�����
            String uporgan = getSessionOrgan().getUporgan();

            resultMap = planadjService.enterReportPlanadjByMonth(file, operCode, organCode, request, organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "¼��ʧ��,����!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }


}
