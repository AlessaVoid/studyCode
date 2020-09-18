package com.boco.PUB.controller.tbPlanStripe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.creditPlan.CreditPlanServiceStripe;
import com.boco.PUB.service.loanPlan.TbPlanDetailService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.global.Dic;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ¼���Ŵ�����
 * @Author zhuhongjiang
 * @Date 2019/11/29 ����2:09
 **/
@Controller
@RequestMapping("/creditPlanStripe")
public class CreditPlanStripeController extends BaseController {

    @Autowired
    private CreditPlanServiceStripe creditPlanServiceStripe;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailService tbPlanDetailService;
    @Autowired
    private TbPlanService tbPlanService;

    /**
     * �б�ҳ
     * @Author zhuhongjiang
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanIndex")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-03-05", funName = "���߼ƻ��б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanIndex";
    }

    /**
     * �б�ҳ����
     * @Author zhuhongjiang
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanData")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-03-05", funName = "�������߼ƻ��б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> data = null;
        try{
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("planmonth".equals(sort)) {
                sort = "plan_month";

            } else if ("planrealincrement".equals(sort)) {
                sort = "plan_real_increment";

            } else if ("plancreatetime".equals(sort)) {
                sort = "plan_create_time";

            } else if ("planoper".equals(sort)) {
                sort = "plan_oper";

            } else if ("planupdatetime".equals(sort)) {
                sort = "plan_update_time";
            }
            if (sort != null) {
                sort = sort + " " + direction;
            }
            //��������
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("planMonth", request.getParameter("planMonth"));
            paramMap.put("planOrgan", getSessionOrgan().getThiscode());
            paramMap.put("planStatus", TbReqDetail.STATE_NEW);
            paramMap.put("planType", TbPlan.STRIPE);
            paramMap.put("sort", sort);

            setPageParam();
            data = creditPlanServiceStripe.selectListByPage(paramMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * ��ʼ���·�
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findTradeParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "�����б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody String findTradeParam(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //��ȡ��ǰ�·�
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int dateNum = Integer.parseInt(sdf.format(date));

        //��ȡ��¼�������� 0-����  1-һ������
        String organlevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organlevel)) {
            TbTradeParam tbTradeParam = new TbTradeParam();
            List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);

            for (TbTradeParam tradeParam : tbPunishParamList) {
                JSONObject jsonObject = new JSONObject();
                if (dateNum <= Integer.parseInt(tradeParam.getParamPeriod())) {
                    jsonObject.put("value", tradeParam.getParamPeriod());
                    jsonObject.put("key", tradeParam.getParamPeriod());
                    jsonArray.add(jsonObject);
                }
            }
        } else if ("1".equals(organlevel)) {
            String uporgan = getSessionOrgan().getUporgan();
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanOrgan(uporgan);
            tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
            tbPlan.setPlanType(TbPlan.PLAN);
            tbPlan.setSortColumn("plan_month");
            List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

            for (TbPlan plan : tbPlans) {
                JSONObject jsonObject = new JSONObject();
                if (dateNum <= Integer.parseInt((plan.getPlanMonth()))) {
                    jsonObject.put("value", plan.getPlanMonth());
                    jsonObject.put("key", plan.getPlanMonth());
                    jsonArray.add(jsonObject);
                }
            }

        }

        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    /**
     * ���������ʼ����   ���¼ƻ������� �ܿ�ģʽ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPlanTime", method = RequestMethod.POST)
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "�����б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody String getPlanTime(HttpServletRequest request) throws Exception {

        //��ȡ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();

        String planMonth = request.getParameter("planMonth");
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(planMonth);
        List<TbTradeParam> tbPunishParamList = tbTradeParamService.selectByAttr(tbTradeParam);
        JSONObject listObj = new JSONObject();
        listObj.put("startTime", tbPunishParamList.get(0).getParamPlanStart());
        listObj.put("endTime", tbPunishParamList.get(0).getParamPlanEnd());
        listObj.put("paramMode", tbPunishParamList.get(0).getParamMode());
        //��ͬ������ȡ��ͬ�ƻ�������
        if ("0".equals(organlevel)) {
            //����ֱ��ȡʱ��ƻ��ľ�����
            // listObj.put("increment", tbPunishParamList.get(0).getParamLineIncrement().divide(new BigDecimal("10000")));
            listObj.put("increment", 0);
        } else if ("1".equals(organlevel)) {
            //����ȡ�ƻ����Ƶľ�����
            //��ȡ��¼�����ŵĸ�������
            String uporgan = getSessionOrgan().getUporgan();
            //��ѯ�ƻ�
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOrgan(uporgan);
            tbPlan.setPlanType(TbPlan.PLAN);
            List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

            if (tbPlans == null || tbPlans.size() == 0) {
                listObj.put("increment", 0);
            } else {
                //��ѯ�ƻ�����
                TbPlanDetail tbPlanDetail = new TbPlanDetail();
                tbPlanDetail.setPdRefId(tbPlans.get(0).getPlanId());
                tbPlanDetail.setPdOrgan(getSessionOrgan().getThiscode());
                List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByAttr(tbPlanDetail);
                BigDecimal increment = new BigDecimal("0");
                for (TbPlanDetail planDetail : tbPlanDetails) {
                    increment = increment.add(planDetail.getPdAmount());
                }
                listObj.put("increment", increment.divide(new BigDecimal("10000")));
            }
        }
        return listObj.toString();
    }

    /**
     * ɾ��
     * @param planId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCreditPlan")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01-03", funName = "ɾ�����߼ƻ�", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody String deleteCreditPlan(String planId) throws Exception {
        PlainResult<String> result = creditPlanServiceStripe.deleteCreditPlan(planId);
        return JSON.toJSONString(result);
    }

    /**
     * ����ҳ
     * @return
     * @throws Exception
     */
    @RequestMapping("/toAddCreditPlan")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "���߼ƻ�����ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAddCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();
        //��ȡ������ϼ��� ���������
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("organlevel", organlevel);
        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanAdd";
    }

    /**
     * �������߼ƻ�
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanAdd", method = RequestMethod.POST)
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "�������߼ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanAdd(HttpServletRequest request) throws Exception {
        //��ǰ��¼��
        String operCode = getSessionOperInfo().getOperCode();
        //��ǰ��¼����
        String organCode = getSessionOrgan().getThiscode();
        //��ǰ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();
        //�ϼ�����
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanServiceStripe.addCreditPlan(request, operCode, organCode, TbPlan.STRIPE,organlevel,uporgan);
        return JSON.toJSONString(result);
    }

    /**
     * ����ҳ
     * @return
     * @throws Exception
     */
    @RequestMapping("/toDetailCreditPlan")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "���߼ƻ�����ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //��ѯ���߼ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ���߼ƻ�����map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }



        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ������ϼ��� ���������
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);

        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanDetail";
    }

    /**
     * ����ҳ����
     * @return
     * @throws Exception
     */
    @RequestMapping("/creditPlanDetailData")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "���߼ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanDetailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //��ѯ�ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��ѯ�ƻ�����
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(plan.getPlanId());
        List<TbPlanDetail> planDetails = tbPlanDetailService.selectByAttr(tbPlanDetail);
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail planDetail : planDetails) {
                planDetail.setPdAmount(planDetail.getPdAmount().divide(new BigDecimal("10000")));
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("plan", plan);
        resultMap.put("planDetails", planDetails);
        return JSON.toJSONString(resultMap);
    }

    /**
     * �޸�ҳ
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpdateCreditPlan")
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "���߼ƻ��޸�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planId = request.getParameter("planId");

        //��ѯ���߼ƻ�
        TbPlan plan = tbPlanService.selectByPK(planId);
        plan.setPlanIncrement(plan.getPlanIncrement().divide(new BigDecimal("10000")));

        //��װ���߼ƻ�����map
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectByWhere("pd_ref_id = \'" + planId + "\'");
        HashMap<String, Object> planMap = new HashMap<>();
        if (plan.getPlanUnit() == 2) {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        } else {
            for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
                planMap.put(tbPlanDetail.getPdOrgan() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
            }
        }



        //��ѯ�ܿ�ģʽ
        TbTradeParam tbTradeParam = new TbTradeParam();
        tbTradeParam.setParamPeriod(tbPlanDetails.get(0).getPdMonth());
        List<TbTradeParam> tradeParams = tbTradeParamService.selectByAttr(tbTradeParam);

        //��ȡ��¼�����ȼ�
        String organlevel = getSessionOrgan().getOrganlevel();

        //��ȡ������ϼ��� ���������
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = null;
        combList = loanCombService.selectCombOfBind(combMap);


        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        request.setAttribute("combList", combList);
        request.setAttribute("organList", organList);
        request.setAttribute("planMap", planMap);
        request.setAttribute("tradeParam", tradeParams.get(0));
        request.setAttribute("planId", planId);
        request.setAttribute("plan", plan);
        request.setAttribute("organlevel", organlevel);

        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanUpdate";
    }

    /**
     * �޸����߼ƻ�
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creditPlanUpdate", method = RequestMethod.POST)
    @SystemLog(tradeName = "���߼ƻ�", funCode = "PUB-07-01", funName = "�޸����߼ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //��ǰ��¼��
        String operCode = getSessionOperInfo().getOperCode();
        //��ǰ��¼����
        String organCode = getSessionOrgan().getThiscode();
        //��ǰ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();
        //�ϼ�����
        String uporgan = getSessionOrgan().getUporgan();

        PlainResult<String> result = creditPlanServiceStripe.updateCreditPlan(request, operCode, organCode,organlevel,uporgan);
        return JSON.toJSONString(result);
    }


    //����¼�����߼ƻ�ҳ��
    @RequestMapping(value = "/toEnterReportPlan")
    @SystemLog(tradeName = "����¼�����߼ƻ�ҳ��", funCode = "PUB-07-01-07", funName = "����¼�����߼ƻ�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public  String toEnterReportPlan(HttpServletRequest request) throws Exception {
        authButtons();
        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanEnterReport";
    }

    //����¼�����߼ƻ�
    @ResponseBody
    @RequestMapping(value = "/enterReportPlanByMonth",method = RequestMethod.POST)
    @SystemLog(tradeName = "����¼�����߼ƻ�", funCode = "PUB-07-01-07", funName = "����¼�����߼ƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
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


            resultMap = tbPlanService.enterReportPlanStripeByMonth(file, operCode, organCode, request,organlevel,uporgan);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "¼��ʧ��,����!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();

    }


    //�鿴���߼ƻ�����ҳ��
    @RequestMapping(value = "/toPlanDetail")
    @SystemLog(tradeName = "�鿴���߼ƻ�����ҳ��", funCode = "PUB-03-01", funName = "�鿴���߼ƻ�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public  String toPlanDetail(HttpServletRequest request) throws Exception {
        authButtons();

        //���ò�ѯ�·�
        String planMonth = request.getParameter("planMonth");
        //��ѯ�·�
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(planMonth);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        tbPlan.setPlanType(TbPlan.STRIPE);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        tbPlan.setSortColumn("plan_month desc");
        List<TbPlan> tbPlans = tbPlanService.selectByAttr(tbPlan);

        HashMap<String, Integer> planUnitMap = new HashMap<>();
        for (TbPlan plan : tbPlans) {
            planUnitMap.put(plan.getPlanMonth(), plan.getPlanUnit());
        }

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("organCode", getSessionOrgan().getThiscode());
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailService.selectAllStripe(paramMap);

        HashMap<String, Object> planMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetails) {
            Integer planUnit = planUnitMap.get(tbPlanDetail.getPdMonth());
            if (planUnit != null && planUnit == 2) {
                tbPlanDetail.setPdAmount(tbPlanDetail.getPdAmount().divide(new BigDecimal("10000")));
            }
            planMap.put(tbPlanDetail.getPdMonth() + "_" + tbPlanDetail.getPdLoanType(), tbPlanDetail);
        }



        //��ѯ�������
        String opercode = getSessionOperInfo().getOperCode();
        HashMap<String, Object> map = new HashMap<>();
        map.put("opercode", opercode);
        List<Map<String, Object>> combList = loanCombService.selectCombByOpercode(map);

        setAttribute("tbPlans", tbPlans);
        setAttribute("combList", combList);
        setAttribute("planMap", planMap);

        return basePath + "/PUB/tbPlanStripe/tbPlanList/creditPlanDetailOrgan";
    }

    //�жϸ��·��Ƿ���ڼƻ�
    @ResponseBody
    @RequestMapping(value = "/creditPlanJudgeMonth")
    @SystemLog(tradeName = "�жϸ��·��Ƿ���ڼƻ�", funCode = "PUB-03-01", funName = "�жϸ��·��Ƿ���ڼƻ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        String organlevel = getSessionOrgan().getOrganlevel();
        PlainResult<String> result  = creditPlanServiceStripe.creditPlanJudgeMonth(organlevel,request,getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }

    //�жϸ��·��Ƿ���ڼƻ�--����ҳ��
    @ResponseBody
    @RequestMapping(value = "/creditPlanEnterJudgeMonth")
    @SystemLog(tradeName = "�жϸ��·��Ƿ���ڼƻ�--����ҳ��", funCode = "PUB-03-01", funName = "�жϸ��·��Ƿ���ڼƻ�--����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanEnterJudgeMonth(HttpServletRequest request) throws Exception {
        authButtons();
        PlainResult<String> result  = creditPlanServiceStripe.creditPlanEnterJudgeMonth(request,getSessionOrgan().getThiscode());
        return JSON.toJSONString(result);
    }

    //�����ƻ�ģ��
    @RequestMapping(value = "/downloadPlanTemplate")
    @SystemLog(tradeName = "�����ƻ�ģ��", funCode = "PUB-03-05-07", funName = "�����ƻ�ģ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadPlanTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        //��ȡ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();

        tbPlanService.downloadPlanTemplate(request, type,response, organlevel);
        // return response;
    }
}
