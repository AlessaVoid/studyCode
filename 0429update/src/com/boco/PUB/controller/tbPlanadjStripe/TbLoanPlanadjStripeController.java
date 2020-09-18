package com.boco.PUB.controller.tbPlanadjStripe;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjServiceStripe;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
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
 * @Description:  ���߼ƻ����� ������ѯ
 * @Date: 2019/11/23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tbPlanadjStripe")
public class TbLoanPlanadjStripeController extends BaseController {

    @Autowired
    private TbPlanadjServiceStripe planadjServiceStripe;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private TbPlanadjService tbPlanadjService;


    @RequestMapping("/loanPlanadjIndexPage")
    @SystemLog(tradeName = "���߼ƻ�������ҳ", funCode = "PUB-06-01", funName = "���߼ƻ�������ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanIndexPage() throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustList/tbPlanAdjustList";
    }

    @RequestMapping("/loadAllLoanadjPlanInfo")
    @ResponseBody
    @SystemLog(tradeName = "�������߼ƻ������б�", funCode = "PUB-06-01", funName = "�������߼ƻ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadAllLoanPlanInfo(HttpServletRequest request) throws Exception {
        authButtons();
        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");
        String planMonth = getParameter("planMonth");

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

        setPageParam();
        Map<String, Object> jsonMap = planadjServiceStripe.selectTbplanadjByMonth(sort,planMonth,Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        return JSON.toJSONString(jsonMap);
    }



    /*���߼ƻ�������������ҳ*/
    @RequestMapping("/loadLoanPlanadjDetailInfoInsertPage")
    @SystemLog(tradeName = "���߼ƻ����������·�¼��ҳ", funCode = "PUB-06-01-01", funName = "���߼ƻ����������·�¼��ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String loadLoanPlanadjDetailInfoInsertPage() throws Exception {
        authButtons();

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

        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("organlevel", organlevel);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjustAdd";
    }

    /**
     * ���ߵ�������ҳ����
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


        //��ȡ���߼ƻ���ϸ
        Map<String, Object> creditPlanList = planadjServiceStripe.getCreditPlanDetail(month);
        //��ȡ���߼ƻ�
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.STRIPE);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        //��Ԫת��Ԫ
        for (TbPlan plan : tbPlans) {
            plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));
            plan.setPlanRealIncrement(plan.getPlanRealIncrement().divide(new BigDecimal("10000")));
        }

        //��ȡ��������
        Map<String, Object> reqList = planadjServiceStripe.getReqDetail(month);

        //�Ƿ���ڼƻ�����
        Integer planadjStatus = -1;
        TbPlanadj queryPlanadj = new TbPlanadj();
        queryPlanadj.setPlanadjMonth(month);
        queryPlanadj.setPlanadjType(TbPlan.STRIPE);
        queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
        List<TbPlanadj> tbPlanadjs = planadjServiceStripe.selectByAttr(queryPlanadj);
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

        // ���Ϊ�գ���˵������µ����߼ƻ� ��û�����������û�����߼ƻ�
        if (tbPlans != null && tbPlans.size() > 0) {
            resultMap.put("plan", tbPlans.get(0));
        }
        return JSON.toJSONString(resultMap);
    }


    /*�������߼ƻ�����*/
    @ResponseBody
    @RequestMapping("/savePlanadj")
    @SystemLog(tradeName = "���߼ƻ��������鱣��", funCode = "PUB-06-01-01", funName = "���߼ƻ��������鱣��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String savePlanadj(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        //�ϼ�����
        String uporgan = sessionOrgan.getUporgan();

        PlainResult<String> result  = planadjServiceStripe.savePlanadj(request, operInfo.getOperCode(), sessionOrgan.getThiscode(),uporgan);
        return JSON.toJSONString(result);

    }

    /*���߼ƻ���������ҳ��*/
    @RequestMapping("/listTbPlanadjDetailAuditUI")
    @SystemLog(tradeName = "���߼ƻ���������ҳ��", funCode = "PUB-06-01-02", funName = "���߼ƻ���������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listTbPlanadjDetailAuditUI(String planadjId) throws Exception {
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


        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);
        setAttribute("organlevel", organlevel);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjustEdit";
    }

    /*���߼ƻ���������*/
    @RequestMapping("/updatePlanadj")
    @ResponseBody
    @SystemLog(tradeName = "���߼ƻ���������", funCode = "PUB-06-01-02", funName = "���߼ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String updatePlanadj(HttpServletRequest request) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        FdOrgan sessionOrgan = getSessionOrgan();
        //�ϼ�����
        String uporgan = sessionOrgan.getUporgan();
        PlainResult<String> result = planadjServiceStripe.updatePlanadj(request, operInfo.getOperCode(), sessionOrgan.getThiscode(),uporgan);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/deleteTbPlanadjDetail")
    @ResponseBody
    @SystemLog(tradeName = "ɾ�����߼ƻ�����", funCode = "PUB-06-01-03", funName = "ɾ�����߼ƻ�����", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String deleteTbPlanadjDetail(String planadjId) throws Exception {
        authButtons();
        PlainResult<String> result = planadjServiceStripe.deleteTbPlanadjDetail(planadjId);
        return JSON.toJSONString(result);
    }




    /*���߼ƻ���������ҳ��*/
    @RequestMapping("/TbPlanadjInfo")
    @SystemLog(tradeName = "���߼ƻ���������ҳ��", funCode = "PUB-06-01-04", funName = "���߼ƻ���������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String TbPlanadjInfo(String planadjId) throws Exception {
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


        setAttribute("organList",organList);
        setAttribute("combList",combList);
        setAttribute("planadjId", planadjId);
        setAttribute("tbPlanadj", tbPlanadj);
        setAttribute("adjMap",map);
        setAttribute("tbPlanadjDetailList", tbPlanadjDetailList);

        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjustInfo";
    }

    //����¼��ƻ�����ҳ��
    @RequestMapping(value = "/toEnterReport")
    @SystemLog(tradeName = "����¼��ƻ�����ҳ��", funCode = "PUB-06-01", funName = "����¼��ƻ�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/LoanPlanAdjustStripe/LoanPlanAdjustDetail/loanPlanAdjEnterReport";
    }

    //�����ƻ�����ģ��
    @RequestMapping(value = "/downPlanadjTemplate")
    @SystemLog(tradeName = "�����ƻ�ģ��", funCode = "PUB-06-01", funName = "�����ƻ�ģ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downPlanadjTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String type = request.getParameter("type");
            //��ȡ��¼��������
            String organlevel = getSessionOrgan().getOrganlevel();

            tbPlanadjService.downPlanadjTemplate(request, type,response, organlevel);
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

            resultMap = planadjServiceStripe.enterReportPlanadjByMonth(file, operCode, organCode, request, organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "¼��ʧ��,����!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }

}
