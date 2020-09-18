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
 * 报表管理
 *
 * @Author zhuhongjiang
 * @Date 2019/12/20 下午3:24
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
     * 信贷规模日报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditScaleDaily")
    @SystemLog(tradeName = "报表管理", funCode = "RE-01", funName = "信贷规模日报表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportCreditScaleDaily(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //当前登录机构信息
        FdOrgan fdOrgan = getSessionOrgan();
        //统计日期 yyyy-MM-dd
        String statisticsDate = request.getParameter("statisticsDate");
        //统计日期 yyyyMMdd
        String rptDate = null;

        if (StringUtils.isEmpty(statisticsDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            statisticsDate = SDF1.format(calendar.getTime());
        }
        rptDate = SDF2.format(SDF1.parse(statisticsDate));

        //查询贷种组合
        Map<String, Object> combMap = new HashMap<>();
        combMap.put("combLevel", 3);
        List<Map<String, Object>> combList = reportCombMapper.selectComb(combMap);

        //查询机构
        List<FdOrgan> oraganAll = new ArrayList<>();
        List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
        //添加机构合计
        FdOrgan totalOrgan = new FdOrgan();
        totalOrgan.setOrganname("机构合计");
        totalOrgan.setThiscode("-1");
        oraganAll.add(totalOrgan);
        oraganAll.addAll(organList);

        //查询贷款余额
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
     * 信贷规模日报表下载
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditScaleDailyDownload")
    @SystemLog(tradeName = "报表管理", funCode = "RE-01", funName = "信贷规模日报表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void reportCreditScaleDailyDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //统计日期 yyyy-MM-dd
            String statisticsDate = request.getParameter("statisticsDate");
            //统计日期 yyyyMMdd
            String rptDate = null;

            if (StringUtils.isEmpty(statisticsDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                statisticsDate = SDF1.format(calendar.getTime());
            }
            rptDate = SDF2.format(SDF1.parse(statisticsDate));

            //查询贷种组合
            Map<String, Object> combMap = new HashMap<>();
            combMap.put("combLevel", 3);
            List<Map<String, Object>> combList = reportCombMapper.selectComb(combMap);

            //查询机构
            List<FdOrgan> oraganAll = new ArrayList<>();
            List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
            //添加机构合计
            FdOrgan totalOrgan = new FdOrgan();
            totalOrgan.setOrganname("机构合计");
            totalOrgan.setThiscode("-1");
            oraganAll.add(totalOrgan);
            oraganAll.addAll(organList);

            //查询贷款余额
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
     * 信贷到期量日报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditMaturityDaily")
    @SystemLog(tradeName = "报表管理", funCode = "RE-02", funName = "信贷到期量日报表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportCreditMaturityDaily(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //当前登录机构信息
        FdOrgan fdOrgan = getSessionOrgan();
        //统计日期 yyyy-MM-dd
        String statisticsDate = request.getParameter("statisticsDate");
        //统计日期 yyyyMMdd
        String rptDate = null;

        if (StringUtils.isEmpty(statisticsDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            statisticsDate = SDF1.format(calendar.getTime());
        }
        rptDate = SDF2.format(SDF1.parse(statisticsDate));

        //查询贷种组合
        Map<String, Object> combMap = new HashMap<>();
        combMap.put("combLevel", 3);
        List<Map<String, Object>> combList = reportCombMapper.selectComb(combMap);

        //查询机构
        List<FdOrgan> oraganAll = new ArrayList<>();
        List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
        //添加机构合计
        FdOrgan totalOrgan = new FdOrgan();
        totalOrgan.setOrganname("机构合计");
        totalOrgan.setThiscode("-1");
        oraganAll.add(totalOrgan);
        oraganAll.addAll(organList);

        //查询到期量余额
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
     * 信贷到期量日报表下载
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportCreditMaturityDailyDownload")
    @SystemLog(tradeName = "报表管理", funCode = "RE-01", funName = "信贷到期量日报表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void reportCreditMaturityDailyDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //统计日期 yyyy-MM-dd
            String statisticsDate = request.getParameter("statisticsDate");
            //统计日期 yyyyMMdd
            String rptDate = null;

            if (StringUtils.isEmpty(statisticsDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                statisticsDate = SDF1.format(calendar.getTime());
            }
            rptDate = SDF2.format(SDF1.parse(statisticsDate));

            //查询贷种组合
            Map<String, Object> combMap = new HashMap<>();
            combMap.put("combLevel", 3);
            List<Map<String, Object>> combList = loanCombMapper.selectComb(combMap);

            //查询机构
            List<FdOrgan> oraganAll = new ArrayList<>();
            List<FdOrgan> organList = reportService.selectSubOrgan(fdOrgan.getThiscode(), fdOrgan.getOrganlevel());
            //添加机构合计
            FdOrgan totalOrgan = new FdOrgan();
            totalOrgan.setOrganname("机构合计");
            totalOrgan.setThiscode("-1");
            oraganAll.add(totalOrgan);
            oraganAll.addAll(organList);

            //查询到期量余额
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

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<----------------人民币贷款情况报表管理 - begin-------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 人民币贷款情况报表管理 - 产品结构报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBProMix")
    @SystemLog(tradeName = "报表管理", funCode = "RE-04-01", funName = "人民币贷款情况产品结构报表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBProMix(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //贷款类型（纵向）
        List<Map<String, String>> loanTypeList = EnumEntity.RMBProMixLoanTypeEnum.getList();

        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBProMix";
    }

    /**
     * 人民币贷款情况报表管理 - 机构、区域结构(年)报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBOrganAreaYear")
    @SystemLog(tradeName = "报表管理", funCode = "RE-04-02", funName = "人民币贷款情况机构、区域结构(年)报表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBOrganAreaYear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //查询机构（纵向）
        List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());

        //贷款类型（横向）
        List<Map<String, String>> loanTypeList = EnumEntity.RMBOrganAreaLoanTypeEnum.getList();

        request.setAttribute("organList", organList);
        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBOrganAreaYear";
    }

    /**
     * 人民币贷款情况报表管理 - 机构、区域结构(月)报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBOrganAreaMonth")
    @SystemLog(tradeName = "报表管理", funCode = "RE-04-03", funName = "人民币贷款情况机构、区域结构(月)报表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBOrganAreaMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //查询机构（纵向）
        List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());

        //贷款类型（横向）
        List<Map<String, String>> loanTypeList = EnumEntity.RMBOrganAreaLoanTypeEnum.getList();

        request.setAttribute("organList", organList);
        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBOrganAreaMonth";
    }

    /**
     * 人民币贷款情况报表管理 - 流量结构报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/reportRMBFlowMix")
    @SystemLog(tradeName = "报表管理", funCode = "RE-04-04", funName = "人民币贷款情况流量结构报表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String reportRMBFlowMix(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //查询机构
        List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());

        //贷款类型（横向 - 二级）
        List<Map<String, String>> loanTypeList = EnumEntity.RMBFlowMixLoanTypeEnum.getList();

        request.setAttribute("organList", organList);
        request.setAttribute("loanTypeList", loanTypeList);
        return basePath + "/RE/reportRmb/reportRMBFlowMix";
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<----------------人民币贷款情况报表管理 - end-------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓------报表导出----------↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 报表一产品表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toProductExcel")
    @SystemLog(tradeName = "报表一产品表页面", funCode = "RE-05", funName = "报表一产品表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toProductExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/productExcel";
    }

    /*查询一级贷种树形列表*/
    @RequestMapping("/combLevelOneTreeList")
    @SystemLog(tradeName = "查询一级贷种树形列表", funCode = "RE-05", funName = "查询一级贷种树形列表", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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
     * 报表一产品表页面数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/productExcel")
    @SystemLog(tradeName = "报表一产品表页面数据", funCode = "RE-05", funName = "报表一产品表页面数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String productExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType)) {
                return result;
            }

            //查询报表数据
            List<ProductReport> data = reportService.getProductReportData(combType, date, cycel, fdorgan);

            Map<String, Object> results = new Hashtable<String, Object>();
            results.put("rows", data);
            result = JsonUtils.toJson(results);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //报表一产品表下载
    @RequestMapping(value = "/downloadProductExcel")
    @SystemLog(tradeName = "报表一产品表下载", funCode = "RE-05", funName = "报表一产品表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadProductExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(unit)) {
                return;
            }

            //下载
            reportService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 报表二机构表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toOrganExcel")
    @SystemLog(tradeName = "报表二机构表页面", funCode = "RE-05", funName = "报表二机构表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/organExcel";
    }

    /**
     * 报表三区域重点行表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toKeyOrganExcel")
    @SystemLog(tradeName = "报表二机构表页面", funCode = "RE-05", funName = "报表二机构表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toKeyOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/areaKeyOrganExcel";
    }

    /**
     * 报表二机构表页面数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/organExcel")
    @SystemLog(tradeName = "报表二三页面数据", funCode = "RE-05", funName = "报表二三页面数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String organExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
            String dimension = request.getParameter("dimension");


            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension)) {
                return result;
            }

            //查询报表数据
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
     * 报表二三下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadOrganExcel")
    @SystemLog(tradeName = "报表二三下载", funCode = "RE-05", funName = "报表二三下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
            String dimension = request.getParameter("dimension");
            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType)
                    || StringUtils.isEmpty(dimension) || StringUtils.isEmpty(unit)) {
                return;
            }

            //下载
            reportOrganService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, dimension, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 流量-机构表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toDataFlowOrganExcel")
    @SystemLog(tradeName = "流量-机构表页面", funCode = "RE-05", funName = "流量-机构表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDataFlowOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/dataFlowOrganExcel";
    }


    /**
     * 流量-区域表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toDataFlowAreaExcel")
    @SystemLog(tradeName = "流量-区域表页面", funCode = "RE-05", funName = "流量-区域表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDataFlowAreaExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/dataFlowAreaExcel";
    }

    /**
     * 流量表页面数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/dataFlowOrganExcel")
    @SystemLog(tradeName = "流量表页面数据", funCode = "RE-05", funName = "流量表页面数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String dataFlowOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
            String dimension = request.getParameter("dimension");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension)) {
                return result;
            }

            //查询报表数据
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
     * 流量表下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadDataFlowOrganExcel")
    @SystemLog(tradeName = "流量表下载", funCode = "RE-05", funName = "流量表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadDataFlowOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
            String dimension = request.getParameter("dimension");
            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType)
                    || StringUtils.isEmpty(dimension) || StringUtils.isEmpty(unit)) {
                return;
            }

            //下载
            reportDataFlowService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, dimension, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 同业表列表页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toBankIndustryExcel")
    @SystemLog(tradeName = "同业表列表页", funCode = "RE-06", funName = "同业表列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toBankIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/bankIndustryExcel";
    }

    /**
     * 同业表列表页数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bankIndustryExcel")
    @SystemLog(tradeName = "同业表列表页数据", funCode = "RE-05", funName = "同业表列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String bankIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");


            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel)) {
                return result;
            }

            //查询报表数据
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
     * 同业表下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadBankIndustryExcel")
    @SystemLog(tradeName = "同业表下载", funCode = "RE-05", funName = "同业表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadBankIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            String cycel1 = null;
            switch(cycel){
                case "1":
                    cycel1 = "年";
                    break;
                case "2":
                    cycel1 = "季";
                    break;
                case "3":
                    cycel1 = "月";
                    break;

            }
            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(unit)) {
                return;
            }

            //下载
            reportBankIndustryService.downloadProductExcel(response, request, date, cycel, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 地区表列表页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toAreaIndustryExcel")
    @SystemLog(tradeName = "地区表列表页", funCode = "RE-07", funName = "地区表列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toAreaIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/areaIndustryExcel";
    }

    /**
     * 地区表列表页数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/areaIndustryExcel")
    @SystemLog(tradeName = "地区表列表页数据", funCode = "RE-05", funName = "地区表列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String areaIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");


            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel)) {
                return result;
            }

            //查询报表数据
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
     * 地区表下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadAreaIndustryExcel")
    @SystemLog(tradeName = "地区表下载", funCode = "RE-05", funName = "地区表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadAreaIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            String cycel1 = null;
            switch(cycel){
                case "1":
                    cycel1 = "年";
                    break;
                case "2":
                    cycel1 = "季";
                    break;
                case "3":
                    cycel1 = "月";
                    break;
            }

            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");


            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(unit)) {
                return;
            }

            //下载
            reportAreaIndustryService.downloadProductExcel(response, request, date, cycel, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 期限表列表页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toTimeLimitOrganExcel")
    @SystemLog(tradeName = "期限表列表页", funCode = "RE-05", funName = "期限表列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toTimeLimitOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/timeLimitOrganExcel";
    }

    /**
     * 期限表列表页数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/timeLimitOrganExcel")
    @SystemLog(tradeName = "期限表列表页数据", funCode = "RE-05", funName = "期限表列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String timeLimitOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
            String dimension = request.getParameter("dimension");


            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension)) {
                return result;
            }

            //查询报表数据
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
     * 期限表下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadTimeLimitOrganExcel")
    @SystemLog(tradeName = "期限表下载", funCode = "RE-05", funName = "期限表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadTimeLimitOrganExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //贷种组合类型 1 全部；2少拆放；4 实体
            String combType = request.getParameter("combType");
            //汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
            String dimension = request.getParameter("dimension");
            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(combType) || StringUtils.isEmpty(dimension) || StringUtils.isEmpty(unit)) {
                return;
            }

            // 下载
            reportTimeLimitService.downloadProductExcel(response, request, combType, date, cycel, fdorgan, dimension, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 行业表列表页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toIndustryExcel")
    @SystemLog(tradeName = "行业表列表页", funCode = "RE-05", funName = "行业表列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/RE/exportExcel/industryExcel";
    }

    /**
     * 行业表列表页数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/industryExcel")
    @SystemLog(tradeName = "行业表列表页数据", funCode = "RE-05", funName = "行业表列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String industryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        String result = JsonUtils.toJson("");
        try {

            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");


            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel)) {
                return result;
            }

            //查询报表数据
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
     * 行业表下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadIndustryExcel")
    @SystemLog(tradeName = "行业表下载", funCode = "RE-05", funName = "行业表下载", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void downloadIndustryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //当前登录机构
            FdOrgan fdorgan = getSessionOrgan();
            //报表日期 yyyymmdd
            String date = request.getParameter("date");
            //报表周期 1-年 2-季 3-月 4-日
            String cycel = request.getParameter("cycel");
            //报表单位 1-万元 2-亿元
            String unit = request.getParameter("unit");

            //校验参数
            if (StringUtils.isEmpty(date) || StringUtils.isEmpty(cycel) || StringUtils.isEmpty(unit)) {
                return;
            }

            //下载
            reportIndustryService.downloadProductExcel(response, request, date, cycel, fdorgan, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑-------报表导出---------↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

}
