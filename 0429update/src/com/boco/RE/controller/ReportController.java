package com.boco.RE.controller;

import com.boco.RE.entity.BankIndustryReport;
import com.boco.RE.entity.DataFlowReport;
import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.TimeLimitReport;
import com.boco.RE.service.*;
import com.boco.RE.util.EnumEntity;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbReportComb;
import com.boco.SYS.entity.TbRptBaseinfoLoankind;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.ReportCombMapper;
import com.boco.SYS.mapper.TbRptBaseinfoLoankindMapper;
import com.boco.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * �������
 *
 * @Author zhuhongjiang
 * @Date 2019/12/20 ����3:24
 **/
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private IReportService reportService;
    @Autowired
    private ReportOrganService reportOrganService;
    @Autowired
    private ReportDataFlowService reportDataFlowService;
    @Autowired
    private ReportTimeLimitService reportTimeLimitService;
    @Autowired
    private ReportIndustryService reportIndustryService;
    @Autowired
    private ReportCombMapper reportCombMapper;
    @Autowired
    private TbRptBaseinfoLoankindMapper tbRptBaseinfoLoankindMapper;
    @Autowired
    private ReportBankIndustryService reportBankIndustryService;
    @Autowired
    private ReportAreaIndustryService reportAreaIndustryService;


    public static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyyMMdd");

    /**
     * �Ŵ���ģ�ձ���
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditScaleDaily")
    @SystemLog(tradeName = "�������", funCode = "RE-01", funName = "�Ŵ���ģ�ձ���", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportCreditScaleDaily(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��ǰ��¼������Ϣ
        FdOrgan fdOrgan = getSessionOrgan();
        //ͳ������ yyyy-MM-dd
        String statisticsDate = request.getParameter("statisticsDate");
        //ͳ������ yyyyMMdd
        String rptDate = null;

        if (StringUtils.isEmpty(statisticsDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            statisticsDate = SDF1.format(calendar.getTime());
        }
        rptDate = SDF2.format(SDF1.parse(statisticsDate));

        //��ѯ�������
        Map<String, Object> combMap = new HashMap<>();
        combMap.put("combLevel", 3);
        List<Map<String, Object>> combList = reportCombMapper.selectComb(combMap);

        //��ѯ����
        List<FdOrgan> oraganAll = new ArrayList<>();
        List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
        //��ӻ����ϼ�
        FdOrgan totalOrgan = new FdOrgan();
        totalOrgan.setOrganname("�����ϼ�");
        totalOrgan.setThiscode("-1");
        oraganAll.add(totalOrgan);
        oraganAll.addAll(organList);

        //��ѯ�������
        int type = 1;
        List<Map<String, Object>> baseInfoList = reportService.getReportCreditDailyInfo(rptDate, fdOrgan.getOrganlevel(), fdOrgan.getThiscode(), type);
        Map<String, Object> baseInfoMap = baseInfoList.stream().collect(
                Collectors.toMap(x -> x.get("organ") + "_" + x.get("loanKind"),
                        x -> x.get("balance").toString()));

        request.setAttribute("combList", combList);
        request.setAttribute("organList", oraganAll);
        request.setAttribute("baseInfoMap", baseInfoMap);
        request.setAttribute("statisticsDate", statisticsDate);
        return basePath + "/RE/reportCreditScaleDaily";
    }

    /**
     * �Ŵ���ģ�ձ�������
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditScaleDailyDownload")
    @SystemLog(tradeName = "�������", funCode = "RE-01", funName = "�Ŵ���ģ�ձ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void reportCreditScaleDailyDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //ͳ������ yyyy-MM-dd
            String statisticsDate = request.getParameter("statisticsDate");
            //ͳ������ yyyyMMdd
            String rptDate = null;

            if (StringUtils.isEmpty(statisticsDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                statisticsDate = SDF1.format(calendar.getTime());
            }
            rptDate = SDF2.format(SDF1.parse(statisticsDate));

            //��ѯ�������
            Map<String, Object> combMap = new HashMap<>();
            combMap.put("combLevel", 3);
            List<Map<String, Object>> combList = reportCombMapper.selectComb(combMap);

            //��ѯ����
            List<FdOrgan> oraganAll = new ArrayList<>();
            List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
            //��ӻ����ϼ�
            FdOrgan totalOrgan = new FdOrgan();
            totalOrgan.setOrganname("�����ϼ�");
            totalOrgan.setThiscode("-1");
            oraganAll.add(totalOrgan);
            oraganAll.addAll(organList);

            //��ѯ�������
            int type = 1;
            List<Map<String, Object>> baseInfoList = reportService.getReportCreditDailyInfo(rptDate, fdOrgan.getOrganlevel(), fdOrgan.getThiscode(), type);
            Map<String, Object> baseInfoMap = baseInfoList.stream().collect(
                    Collectors.toMap(x -> x.get("organ") + "_" + x.get("loanKind"),
                            x -> x.get("balance").toString()));

            reportService.reportCreditScaleDailyDownload(response, request, combList, oraganAll, baseInfoMap, rptDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * �Ŵ��������ձ���
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditMaturityDaily")
    @SystemLog(tradeName = "�������", funCode = "RE-02", funName = "�Ŵ��������ձ���", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportCreditMaturityDaily(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��ǰ��¼������Ϣ
        FdOrgan fdOrgan = getSessionOrgan();
        //ͳ������ yyyy-MM-dd
        String statisticsDate = request.getParameter("statisticsDate");
        //ͳ������ yyyyMMdd
        String rptDate = null;

        if (StringUtils.isEmpty(statisticsDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            statisticsDate = SDF1.format(calendar.getTime());
        }
        rptDate = SDF2.format(SDF1.parse(statisticsDate));

        //��ѯ�������
        Map<String, Object> combMap = new HashMap<>();
        combMap.put("combLevel", 3);
        List<Map<String, Object>> combList = reportCombMapper.selectComb(combMap);

        //��ѯ����
        List<FdOrgan> oraganAll = new ArrayList<>();
        List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
        //��ӻ����ϼ�
        FdOrgan totalOrgan = new FdOrgan();
        totalOrgan.setOrganname("�����ϼ�");
        totalOrgan.setThiscode("-1");
        oraganAll.add(totalOrgan);
        oraganAll.addAll(organList);

        //��ѯ���������
        int type = 2;
        List<Map<String, Object>> baseInfoList = reportService.getReportCreditDailyInfo(rptDate, fdOrgan.getOrganlevel(), fdOrgan.getThiscode(), type);
        Map<String, Object> baseInfoMap = baseInfoList.stream().collect(
                Collectors.toMap(x -> x.get("organ") + "_" + x.get("loanKind"),
                        x -> x.get("dayLimit").toString()));

        request.setAttribute("combList", combList);
        request.setAttribute("organList", oraganAll);
        request.setAttribute("baseInfoMap", baseInfoMap);
        request.setAttribute("statisticsDate", statisticsDate);
        return basePath + "/RE/reportCreditMaturityDaily";
    }

    /**
     * �Ŵ��������ձ�������
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditMaturityDailyDownload")
    @SystemLog(tradeName = "�������", funCode = "RE-01", funName = "�Ŵ��������ձ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void reportCreditMaturityDailyDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //ͳ������ yyyy-MM-dd
            String statisticsDate = request.getParameter("statisticsDate");
            //ͳ������ yyyyMMdd
            String rptDate = null;

            if (StringUtils.isEmpty(statisticsDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                statisticsDate = SDF1.format(calendar.getTime());
            }
            rptDate = SDF2.format(SDF1.parse(statisticsDate));

            //��ѯ�������
            Map<String, Object> combMap = new HashMap<>();
            combMap.put("combLevel", 3);
            List<Map<String, Object>> combList = loanCombMapper.selectComb(combMap);

            //��ѯ����
            List<FdOrgan> oraganAll = new ArrayList<>();
            List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
            //��ӻ����ϼ�
            FdOrgan totalOrgan = new FdOrgan();
            totalOrgan.setOrganname("�����ϼ�");
            totalOrgan.setThiscode("-1");
            oraganAll.add(totalOrgan);
            oraganAll.addAll(organList);

            //��ѯ���������
            int type = 2;
            List<Map<String, Object>> baseInfoList = reportService.getReportCreditDailyInfo(rptDate, fdOrgan.getOrganlevel(), fdOrgan.getThiscode(), type);
            Map<String, Object> baseInfoMap = baseInfoList.stream().collect(
                    Collectors.toMap(x -> x.get("organ") + "_" + x.get("loanKind"),
                            x -> x.get("dayLimit").toString()));


            reportService.reportCreditMaturityDailyDownload(response, request, combList, oraganAll, baseInfoMap, rptDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<----------------����Ҵ������������� - begin-------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * ����Ҵ������������� - ��Ʒ�ṹ����
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBProMix")
    @SystemLog(tradeName = "�������", funCode = "RE-04-01", funName = "����Ҵ��������Ʒ�ṹ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBProMix(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //�������ͣ�����
        List<Map<String, String>> loanTypeList = EnumEntity.RMBProMixLoanTypeEnum.getList();

        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBProMix";
    }

    /**
     * ����Ҵ������������� - ����������ṹ(��)����
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBOrganAreaYear")
    @SystemLog(tradeName = "�������", funCode = "RE-04-02", funName = "����Ҵ����������������ṹ(��)����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBOrganAreaYear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��ѯ����������
        List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());

        //�������ͣ�����
        List<Map<String, String>> loanTypeList = EnumEntity.RMBOrganAreaLoanTypeEnum.getList();

        request.setAttribute("organList", organList);
        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBOrganAreaYear";
    }

    /**
     * ����Ҵ������������� - ����������ṹ(��)����
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBOrganAreaMonth")
    @SystemLog(tradeName = "�������", funCode = "RE-04-03", funName = "����Ҵ����������������ṹ(��)����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBOrganAreaMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��ѯ����������
        List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());

        //�������ͣ�����
        List<Map<String, String>> loanTypeList = EnumEntity.RMBOrganAreaLoanTypeEnum.getList();

        request.setAttribute("organList", organList);
        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBOrganAreaMonth";
    }

    /**
     * ����Ҵ������������� - �����ṹ����
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBFlowMix")
    @SystemLog(tradeName = "�������", funCode = "RE-04-04", funName = "����Ҵ�����������ṹ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBFlowMix(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��ѯ����
        List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());

        //�������ͣ����� - ������
        List<Map<String, String>> loanTypeList = EnumEntity.RMBFlowMixLoanTypeEnum.getList();

        request.setAttribute("organList", organList);
        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBFlowMix";
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<----------------����Ҵ������������� - end-------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /*����������������������������������������������������������������������------������----------��������������������������������������������������������������������������������������������������*/

    /**
     * ����һ��Ʒ��ҳ��
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toProductExcel")
    @SystemLog(tradeName = "����һ��Ʒ��ҳ��", funCode = "RE-05", funName = "����һ��Ʒ��ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toProductExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/productExcel";
    }

    /*��ѯһ�����������б�*/
    @RequestMapping("/combLevelOneTreeList")
    @SystemLog(tradeName = "��ѯһ�����������б�", funCode = "RE-05", funName = "��ѯһ�����������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String combLevelOneTreeList() {

        String jsonStr = "";
        try {
            jsonStr = reportService.combLevelOneTreeList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * ����һ��Ʒ��ҳ������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/productExcel")
    @SystemLog(tradeName = "����һ��Ʒ��ҳ������", funCode = "RE-05", funName = "����һ��Ʒ��ҳ������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String productExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType)) {
                return result;
            }

            //��ѯ��������
            List<ProductReport> data = reportService.getProductReportData(combType, date, cycel, fdorgan);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //����һ��Ʒ������
    @RequestMapping(value = "/downloadProductExcel")
    @SystemLog(tradeName = "����һ��Ʒ������", funCode = "RE-05", funName = "����һ��Ʒ������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadProductExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(unit)) {
                return;
            }

            //����
            reportService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * �����������ҳ��
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toOrganExcel")
    @SystemLog(tradeName = "�����������ҳ��", funCode = "RE-05", funName = "�����������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/organExcel";
    }

    /**
     * �����������ص��б�ҳ��
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toKeyOrganExcel")
    @SystemLog(tradeName = "�����������ҳ��", funCode = "RE-05", funName = "�����������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toKeyOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/areaKeyOrganExcel";
    }

    /**
     * �����������ҳ������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/organExcel")
    @SystemLog(tradeName = "�������ҳ������", funCode = "RE-05", funName = "�������ҳ������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String organExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
            String dimension = request.getParameter("dimension");


            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension)) {
                return result;
            }

            //��ѯ��������
            List<ProductReport> data = reportOrganService.getReportData(combType, date, cycel, fdorgan, dimension);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * �����������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadOrganExcel")
    @SystemLog(tradeName = "�����������", funCode = "RE-05", funName = "�����������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
            String dimension = request.getParameter("dimension");
            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType)
                    || StringUtils.isEmpty(dimension) || StringUtils.isEmpty(unit)) {
                return;
            }

            //����
            reportOrganService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, dimension, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ����-������ҳ��
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toDataFlowOrganExcel")
    @SystemLog(tradeName = "����-������ҳ��", funCode = "RE-05", funName = "����-������ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDataFlowOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/dataFlowOrganExcel";
    }


    /**
     * ����-�����ҳ��
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toDataFlowAreaExcel")
    @SystemLog(tradeName = "����-�����ҳ��", funCode = "RE-05", funName = "����-�����ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDataFlowAreaExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/dataFlowAreaExcel";
    }

    /**
     * ������ҳ������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/dataFlowOrganExcel")
    @SystemLog(tradeName = "������ҳ������", funCode = "RE-05", funName = "������ҳ������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String dataFlowOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
            String dimension = request.getParameter("dimension");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension)) {
                return result;
            }

            //��ѯ��������
            List<DataFlowReport> data = reportDataFlowService.getReportData(combType, date, cycel, fdorgan, dimension);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ����������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadDataFlowOrganExcel")
    @SystemLog(tradeName = "����������", funCode = "RE-05", funName = "����������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadDataFlowOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
            String dimension = request.getParameter("dimension");
            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType)
                    || StringUtils.isEmpty(dimension) || StringUtils.isEmpty(unit)) {
                return;
            }

            //����
            reportDataFlowService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, dimension, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ͬҵ���б�ҳ
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toBankIndustryExcel")
    @SystemLog(tradeName = "ͬҵ���б�ҳ", funCode = "RE-06", funName = "ͬҵ���б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toBankIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/bankIndustryExcel";
    }

    /**
     * ͬҵ���б�ҳ����
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bankIndustryExcel")
    @SystemLog(tradeName = "ͬҵ���б�ҳ����", funCode = "RE-05", funName = "ͬҵ���б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String bankIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");


            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel)) {
                return result;
            }

            //��ѯ��������
            List<BankIndustryReport> data = reportBankIndustryService.getReportData(date, cycel);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ͬҵ������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadBankIndustryExcel")
    @SystemLog(tradeName = "ͬҵ������", funCode = "RE-05", funName = "ͬҵ������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadBankIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            String cycel1 = null;
            switch(cycel){
                case "1":
                    cycel1 = "��";
                    break;
                case "2":
                    cycel1 = "��";
                    break;
                case "3":
                    cycel1 = "��";
                    break;

            }
            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(unit)) {
                return;
            }

            //����
            reportBankIndustryService.downloadProductExcel(response, request, date, cycel, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * �������б�ҳ
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toAreaIndustryExcel")
    @SystemLog(tradeName = "�������б�ҳ", funCode = "RE-07", funName = "�������б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAreaIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/areaIndustryExcel";
    }

    /**
     * �������б�ҳ����
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/areaIndustryExcel")
    @SystemLog(tradeName = "�������б�ҳ����", funCode = "RE-05", funName = "�������б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String areaIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");


            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel)) {
                return result;
            }

            //��ѯ��������
            List<BankIndustryReport> data = reportAreaIndustryService.getReportData(date, cycel);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ����������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadAreaIndustryExcel")
    @SystemLog(tradeName = "����������", funCode = "RE-05", funName = "����������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadAreaIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            String cycel1 = null;
            switch(cycel){
                case "1":
                    cycel1 = "��";
                    break;
                case "2":
                    cycel1 = "��";
                    break;
                case "3":
                    cycel1 = "��";
                    break;
            }

            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");


            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(unit)) {
                return;
            }

            //����
            reportAreaIndustryService.downloadProductExcel(response, request, date, cycel, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ޱ��б�ҳ
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toTimeLimitOrganExcel")
    @SystemLog(tradeName = "���ޱ��б�ҳ", funCode = "RE-05", funName = "���ޱ��б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toTimeLimitOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/timeLimitOrganExcel";
    }

    /**
     * ���ޱ��б�ҳ����
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/timeLimitOrganExcel")
    @SystemLog(tradeName = "���ޱ��б�ҳ����", funCode = "RE-05", funName = "���ޱ��б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String timeLimitOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
            String dimension = request.getParameter("dimension");


            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension)) {
                return result;
            }

            //��ѯ��������
            List<TimeLimitReport> data = reportTimeLimitService.getReportData(combType, date, cycel, fdorgan, dimension);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ���ޱ�����
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadTimeLimitOrganExcel")
    @SystemLog(tradeName = "���ޱ�����", funCode = "RE-05", funName = "���ޱ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadTimeLimitOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
            String combType = request.getParameter("combType");
            //����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
            String dimension = request.getParameter("dimension");
            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension) || StringUtils.isEmpty(unit)) {
                return;
            }

            // ����
            reportTimeLimitService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, dimension, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ҵ���б�ҳ
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toIndustryExcel")
    @SystemLog(tradeName = "��ҵ���б�ҳ", funCode = "RE-05", funName = "��ҵ���б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/industryExcel";
    }

    /**
     * ��ҵ���б�ҳ����
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/industryExcel")
    @SystemLog(tradeName = "��ҵ���б�ҳ����", funCode = "RE-05", funName = "��ҵ���б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String industryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");


            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel)) {
                return result;
            }

            //��ѯ��������
            List<ProductReport> data = reportIndustryService.getReportData(date, cycel, fdorgan);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ��ҵ������
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadIndustryExcel")
    @SystemLog(tradeName = "��ҵ������", funCode = "RE-05", funName = "��ҵ������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //��ǰ��¼����
            FdOrgan fdorgan = getSessionOrgan();
            //�������� yyyymmdd
            String date = request.getParameter("date");
            //�������� 1-�� 2-�� 3-�� 4-��
            String cycel = request.getParameter("cycel");
            //����λ 1-��Ԫ 2-��Ԫ
            String unit = request.getParameter("unit");

            //У�����
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(unit)) {
                return;
            }

            //����
            reportIndustryService.downloadProductExcel(response, request, date, cycel, fdorgan, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*������������������������������������������������������������������������-------������---------������������������������������������������������������������������*/

}
