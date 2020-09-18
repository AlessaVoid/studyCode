package com.boco.PUB.controller.tbLineReqReset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbLineReqresetDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * ������ϸ��controller
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbLineReqResetDetail/")
public class TbLineReqResetDetailController extends BaseController {
    @Autowired
    private ITbLineReqresetDetailService tbLineReqresetDetailService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    WebOperRoleMapper webOperRoleMapper;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanadjService tbPlanadjService;


    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-10", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqResetManage/tbLineReqResetDetail/list";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-10-01-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(String lineReqresetId, HttpSession session) throws Exception {
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());
        setAttribute("TbReqresetList", tbReqresetList);
        setAttribute("planAmonut", getPlanCount(tbLineReqresetDetail.getLineReqMonth(), getSessionOrgan().getThiscode()));
        setAttr(tbLineReqresetDetail);
        return basePath + "/PUB/tbLineReqResetManage/tbLineReqResetDetail/info";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-10-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI(String lineReqresetId, HttpSession session) throws Exception {
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        int unit = tbLineReqresetDetail.getLineUnit();
        BigDecimal unitAmount = BigDecimal.ONE;
        if (unit == 2) {
            unitAmount = new BigDecimal(10000);
        }
        setAttribute("planAmonut", getPlanCount(tbLineReqresetDetail.getLineReqMonth(), getSessionOrgan().getThiscode()).divide(unitAmount));
        setAttr(tbLineReqresetDetail);
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());
        setAttribute("TbReqresetList", tbReqresetList);
        return basePath + "/PUB/tbLineReqResetManage/tbLineReqResetDetail/add";


    }

    //�·�  ��Ҫ��ѯ�Ļ�����
    private BigDecimal getPlanCount(String month, String organ) throws Exception {

        //��ȡ�ϼ�����
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(organ);
        List<FdOrgan> fdOrgans = fdOrganService.selectByAttr(fdOrgan);
        String upOrgan = "";
        if (fdOrgans != null && fdOrgans.size() != 0) {
            upOrgan = fdOrgans.get(0).getUporgan();
        }

        //��ȡ�ƻ�
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(upOrgan);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlans != null && tbPlans.size() != 0) {
            planId = tbPlans.get(0).getPlanId();
        }

        //��ȡ�ƻ�����
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(planId);
        tbPlanDetail.setPdOrgan(organ);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);
        //�����ܽ��
        BigDecimal planCount = BigDecimal.ZERO;
        if (tbPlanDetails != null && tbPlanDetails.size() > 0) {
            for (TbPlanDetail planDetail : tbPlanDetails) {
                planCount = planCount.add(planDetail.getPdAmount());
            }
        }
        return planCount;
    }

    /**
     * ͨ�÷���
     *
     * @param tbLineReqresetDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqresetDetail tbLineReqresetDetail) throws Exception {
        String combCodeStr = tbLineReqresetDetail.getLineCombCode();

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //����������������������ʡ����Ǳ�����
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "num");
        combAmountNameList.add(map1);
        setAttribute("combAmountNameList", combAmountNameList);

        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());

        String month = tbReqresetList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //û��ָ���·ݣ�����û�мƻ�
        } else {
            //������ �ƻ� ��ѯ���� ��Ҫ�޸�Ϊ�ϼ����� code
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 2);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }
        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbReqresetList.getReqresetUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }


        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        String[] combArr = combCodeStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
            String codeStr = loanCombDO.getCombCode();
        for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    combMap.put("combCode", combArr[i]);
                    combMap.put("combName", loanCombDO.getCombName());
                    combMap.put("oldNum", "0");
                    if (planIsOk) {
                        for (Map<String, Object> map : planList) {
                            String planCombCode = map.get("loantype").toString();
                            if (planCombCode.equals(codeStr)) {
                                BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                combMap.put("oldNum", oldPlan.divide(unit).toString());
                                break;
                            }
                        }
                    }
                    combList.add(combMap);
                    break;
                }
            }
        }
        setAttribute("combList", combList);
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-10-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(String lineReqresetId, HttpSession session) throws Exception {
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        setAttribute("tbLineReqresetDetail", tbLineReqresetDetail);
        setAttribute("lineReqresetId", lineReqresetId);
        setAttribute("planAmonut", getPlanCount(tbLineReqresetDetail.getLineReqMonth(), getSessionOrgan().getThiscode()));
        setAttr(tbLineReqresetDetail);
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());
        setAttribute("TbReqresetList", tbReqresetList);
        return basePath + "/PUB/tbLineReqResetManage/tbLineReqResetDetail/edit";
    }

    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB2", funName = "�Ŵ��ƻ���������", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String lineReqresetId, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        TbLineReqresetDetail tbLineReqresetDetail = tbLineReqresetDetailService.selectByPK(Integer.valueOf(lineReqresetId));
        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbLineReqresetDetail.getLineUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }
        TbReqresetList searchTbList = tbReqresetListService.selectByPK(tbLineReqresetDetail.getLineResetrefId());
        String month = searchTbList.getReqresetMonth();
        String lineCombtr = tbLineReqresetDetail.getLineCombCode();
        String[] lineCombArr = lineCombtr.split(",");
        String numStr = tbLineReqresetDetail.getLineNum();
        String[] numArr = new String[0];
        if (numStr != null && numStr.trim().length() > 0) {
            numArr = numStr.split(",");
        }
        boolean planIsOk = false;
        List<Map<String, Object>> planList = null;
        if (month == null || "".equals(month)) {
            //û��ָ���·ݣ�����û�мƻ�
        } else {
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getThiscode(), month, 2);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }
        List<TbLineReqResetDetailDTO> tbLineReqResetDetailDTOS = new ArrayList<>();
        for (int i = 0; i < lineCombArr.length; i++) {
            TbLineReqResetDetailDTO tb = new TbLineReqResetDetailDTO();
            tb.setLineCombCode(lineCombArr[i]);
            tb.setLineNum(BigDecimalUtil.getSafeCount(numArr[i]).divide(unit));
            String combCode = lineCombArr[i];
            if (planIsOk) {
                for (Map<String, Object> map : planList) {
                    String planCombCode = map.get("loantype").toString();
                    if (planCombCode.equals(combCode)) {
                        BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                        BigDecimal newPlan = oldPlan.add(new BigDecimal(numArr[i]));
                        tb.setOldPlan(oldPlan.divide(unit).toString());
                        tb.setNewPlan(newPlan.divide(unit).toString());
                        tb.setLineUpdateTime(tbLineReqresetDetail.getLineUpdateTime());
                        break;
                    }
                }
            }
            tbLineReqResetDetailDTOS.add(tb);

        }
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbLineReqResetDetailDTOS", tbLineReqResetDetailDTOS);
        return JSON.toJSONString(resultMap);
    }


    /**
     * ��ѯtb_line_req_list��ҳ����
     * ��������Ա�������߼� ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showFindPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-10-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showFindPage(HttpSession session, TbReqList tbReqList) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbLineReqresetDetail tbLineReqresetDetail = new TbLineReqresetDetail();
        tbLineReqresetDetail.setLineState(7);
        tbLineReqresetDetail.setLineOrgan(organCode);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //��ʼҳ��
        int pageNum = Integer.valueOf(request.getParameter("pageNo"));
        //ÿҳ��ʾ����
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        List<TbLineReqresetDetail> tbLineReqDetails = tbLineReqresetDetailService.selectAll(tbLineReqresetDetail, fdOper);
        List<TbLineReqresetDetail> newList = getListPage(pageNum, pageSize, tbLineReqDetails);
        //����ҳ��ķ�ҳ����
        Map<String, Object> results = new Hashtable<String, Object>();
        if (newList == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
            return JsonUtils.toJson(results);
        }
        //��PageInfo�Խ�����а�װ
        results.put("pager.pageNo", pageNum);
        results.put("pager.totalRows", tbLineReqDetails.size());
        results.put("rows", newList);
        return JsonUtils.toJson(results);
    }


    private static List<TbLineReqresetDetail> getListPage(int page, int pageSize, List<TbLineReqresetDetail> list) {
        if (list != null) {
            int totalCount = list.size();
            page = page - 1;
            int fromIndex = page * pageSize;
            if (fromIndex < totalCount) {
                int toIndex = (page + 1) * pageSize;
                if (toIndex > totalCount) {
                    toIndex = totalCount;
                }
                return list.subList(fromIndex, toIndex);

            }

        }
        return null;
    }


    /**
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB-10-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(3);
        JSONObject listObj = new JSONObject();
        List<Map<String, String>> list = new ArrayList<>();
        for (LoanCombDO tb : loanCombDOS) {
            Map<String, String> map = new HashMap<>();
            map.put("value", tb.getCombCode());
            map.put("key", tb.getCombName());
            list.add(map);
        }
        listObj.put("combList", list);
        return listObj.toString();
    }


    /**
     * TODO ����tb_req_detail.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "PUB-10-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
//        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        int testNum = Integer.parseInt(request.getParameter("testNum"));
        PlainResult<String> result = new PlainResult<>();
        try {
            TbLineReqresetDetail tbLineReqDetails = getList(request, result, organCode);
            if (testNum == 9999) {
                TbLineReqresetDetail searchTb = new TbLineReqresetDetail();
                searchTb.setLineResetrefId(tbLineReqDetails.getLineResetrefId());
                searchTb.setLineState(2);
                searchTb.setLineReqresetId(tbLineReqDetails.getLineReqresetId());
                searchTb.setLineOrgan(getSessionOrgan().getThiscode());
                List<TbLineReqresetDetail> tempList = tbLineReqresetDetailService.selectByAttr(searchTb);
                if (tempList != null && tempList.size() > 0) {
                    result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "��ǰ���ε��������������¼�룬��ת���˵��鿴");
                    WebMsg webMsg = new WebMsg();
                    webMsg.setMsgType(DicCache.getKeyByName_("¼���������", "MSG_TYPE"));
                    webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
                    webMsg.setWebMsgStatus("1");
                    webMsg.setOperDescribe("¼�����������" + tbLineReqDetails.getLineReqresetId());
                    List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
                    if (webMsgs != null && webMsgs.size() != 0) {
                        webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
                    }
                    return JSON.toJSONString(result);

                } else {
                    result.error(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED, "��ǰ���ε�������¼�룬��ת���˵��鿴");

                    return JSON.toJSONString(result);
                }

            }
            tbLineReqresetDetailService.updateByPK(tbLineReqDetails);
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("¼���������", "MSG_TYPE"));
            webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("¼�����������" + tbLineReqDetails.getLineReqresetId());
            List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
            if (webMsgs != null && webMsgs.size() != 0) {
                webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
            }


        } catch (Exception e) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        result = result.success("add", "add credit plan success.");
        return JSON.toJSONString(result);
    }


    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param request
     * @param result
     * @param organCode
     * @return
     */
    private TbLineReqresetDetail getList(HttpServletRequest request, PlainResult<String> result, String organCode) {
        String tbLineReqresetDetailStr = request.getParameter("tbLineReqResetDetail");
        String lineUnit = request.getParameter("lineUnit");
        String lineReqresetId = request.getParameter("lineReqresetId");
        String lineResetRefId = request.getParameter("lineResetrefId");
        String state = request.getParameter("state");

        //У�������ֶηǿ�
        if (StringUtils.isEmpty(lineUnit) || StringUtils.isEmpty(tbLineReqresetDetailStr)) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add lineReqDetail  param not to be null");
        }
        List<TbLineReqResetDetailDTO> tbLineReqResetDetailDTOS = new ArrayList<>();
        String[] lineReqDetailArr = tbLineReqresetDetailStr.split("&");
        for (int i = 0; i < lineReqDetailArr.length; i++) {
            String[] planDetailArr1 = lineReqDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            //eg: 11005293_x001=123 (����code_������=���&����code_������=���)
            String num = planDetailArr1[1];
            String combCode = planDetailArr2[0];
            TbLineReqResetDetailDTO tbLineReqResetDetailDTO = new TbLineReqResetDetailDTO();
            tbLineReqResetDetailDTO.setLineCombCode(combCode);
            tbLineReqResetDetailDTO.setLineUnit(Integer.valueOf(lineUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(lineUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbLineReqResetDetailDTO.setLineReqresetId(Integer.valueOf(lineReqresetId));
            tbLineReqResetDetailDTO.setLineResetrefId(Integer.valueOf(lineResetRefId));
            tbLineReqResetDetailDTO.setLineNum(new BigDecimal(num).multiply(unit));
            tbLineReqResetDetailDTOS.add(tbLineReqResetDetailDTO);
        }
        String numStr = "";
        for (TbLineReqResetDetailDTO tbDTO : tbLineReqResetDetailDTOS) {
            numStr += (tbDTO.getLineNum() + ",");
        }
        if (numStr.endsWith(",")) {
            numStr = numStr.substring(0, numStr.length() - 1);
        }
        TbLineReqresetDetail tbLineReqresetDetail = new TbLineReqresetDetail();
        tbLineReqresetDetail.setLineNum(numStr);
        tbLineReqresetDetail.setLineState(Integer.valueOf(state));
        tbLineReqresetDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqresetDetail.setLineCreateTime(

                BocoUtils.getTime());
        tbLineReqresetDetail.setLineReqresetId(Integer.parseInt(lineReqresetId));
        tbLineReqresetDetail.setLineResetrefId(Integer.parseInt(lineResetRefId));

        return tbLineReqresetDetail;
    }

    /**
     * TODO �޸�tb_req_detail.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-10-01", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        PlainResult<String> result = new PlainResult<>();
        try {
            TbLineReqresetDetail tbLineReqresetDetail = getList(request, result, organCode);
            tbLineReqresetDetailService.updateByPK(tbLineReqresetDetail);
        } catch (Exception e) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update req detail error");
        }
        result = result.success("update", "update req detail success.");
        return JSON.toJSONString(result);
    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "showLineReqMonth")
    @SystemLog(tradeName = "��ѯ���������·�", funCode = "PUB-10", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showReqMonth(TbLineReqresetDetail tbLineReqresetDetail, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbLineReqresetDetail.setLineOrgan(fdOper.getOrgancode());
        String lineReqMonth = request.getParameter("lineReqMonth");
        if (lineReqMonth != null && "".equals(lineReqMonth)) {
            tbLineReqresetDetail.setLineReqMonth(lineReqMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbLineReqListList = tbLineReqresetDetailService.showLineReqResetMonth(tbLineReqresetDetail);
        if (tbLineReqListList == null || tbLineReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : tbLineReqListList) {
            String data = deptInfo.get("line_req_month");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }


}