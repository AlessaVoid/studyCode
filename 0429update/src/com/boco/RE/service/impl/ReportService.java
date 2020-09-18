package com.boco.RE.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.RE.service.IReportService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.*;
import com.boco.util.JsonUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报表统计Service
 *
 * @Author zhuhongjiang
 * @Date 2020/3/13 上午9:25
 **/
@Service
public class ReportService implements IReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    private static SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private TbReqListMapper tbReqListMapper;
    @Autowired
    private TbLineReqDetailMapper tbLineReqDetailMapper;
    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private TbRptBaseinfoMapper tbRptBaseinfoMapper;
    @Autowired
    private TbPlanMapper tbPlanMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private ReportCombMapper reportCombMapper;
    @Autowired
    private TbRptBaseinfoLoankindMapper tbRptBaseinfoLoankindMapper;
    @Autowired
    private GfDictMapper gfDictMapper;

    //三级一级贷种map
    private static HashMap<String, String> threeToOneMap = new HashMap<>();
    //三级二级贷种map
    private static HashMap<String, String> threeToTwoMap = new HashMap<>();

    /**
     * 查询下级所属机构
     *
     * @param thiscode
     * @return
     */
    @Override
    public List<FdOrgan> selectSubOrgan(String thiscode, String organlevel) {
        List<FdOrgan> subList = new ArrayList<>();

        if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
            //总行查一分
            FdOrgan thisOrgan = fdOrganMapper.selectByPK(thiscode);
            thisOrgan.setOrganname("总行");
            subList.add(thisOrgan);

            FdOrgan searchOrgan = new FdOrgan();
            searchOrgan.setUporgan(thiscode);
            subList.addAll(fdOrganMapper.selectByAttr2(searchOrgan));

        } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
            //一分查二分
            FdOrgan thisOrgan = fdOrganMapper.selectByPK(thiscode);
            subList.add(thisOrgan);

            FdOrgan searchOrgan2 = new FdOrgan();
            searchOrgan2.setUporgan(thiscode);
            subList.addAll(fdOrganMapper.selectByAttr2(searchOrgan2));
        }

        return subList;
    }

    /**
     * 查询信贷日报信息（贷款余额、到期量余额）
     *
     * @param rptDate    统计日期（yyyyMMdd）
     * @param organlevel 机构级别（0总行、1一级分行）
     * @param organCode  机构编码
     * @param type
     * @return
     */
    @Override
    public List<Map<String, Object>> getReportCreditDailyInfo(String rptDate, String organlevel, String organCode, int type) {
        List<Map<String, Object>> resultList = new ArrayList<>();


        if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
            /** <<<<<<<<<<<<<<<<<<<<---------------总行登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("rptDate", rptDate);
            resultList = tbRptBaseinfoMapper.selectForReportOrganOne(paramMap);
        } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
            /** <<<<<<<<<<<<<<<<<<<<---------------一级分行登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("rptDate", rptDate);
            paramMap.put("organCode", organCode);
            resultList = tbRptBaseinfoMapper.selectForReportOrganTwo(paramMap);
        } else {
            throw new RuntimeException("当前机构级别不支持报表查询！");
        }

        //特殊处理信贷规模日报表的拆放非银
        if (type == 1) {

            // 查询拆放非银贷种code
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("dicNo", "OTHER_COMB_CODE");
            List<GfDict> dictList = gfDictMapper.findByValINKeys(paramMap);
            // 特殊处理
            for (GfDict gfDict : dictList) {
                String dictKey = gfDict.getDictKeyIn();
                for (Map<String, Object> map : resultList) {
                    if (dictKey.equals(map.get("loanKind"))) {
                        map.put("balance", BigDecimalUtil.add(map.get("balance").toString(), map.get("dayLimit").toString()));
                    }
                }
            }
        }

        //合计
        if (resultList != null && !resultList.isEmpty()) {
            Map<Object, DoubleSummaryStatistics> balanceCollect = resultList.stream().collect(Collectors.groupingBy(d -> d.get("loanKind"), Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("balance"))))));
            Map<Object, DoubleSummaryStatistics> dayLimitCollect = resultList.stream().collect(Collectors.groupingBy(d -> d.get("loanKind"), Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("dayLimit"))))));
            for (Object key : balanceCollect.keySet()) {
                Map<String, Object> allMap = new HashMap<>();
                allMap.put("organ", "-1");
                allMap.put("loanKind", key);
                allMap.put("balance", balanceCollect.get(key).getSum());
                allMap.put("dayLimit", dayLimitCollect.get(key).getSum());
                resultList.add(allMap);
            }
        }
        return resultList;
    }

    /**
     * 信贷规模日报表下载
     *  @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate 统计日期
     */
    @Override
    public void reportCreditScaleDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception {
        // 获取输出流
        OutputStream os = response.getOutputStream();

        //创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //居中样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        /*---------写入文件---------*/

        String filename = "信贷规模日报表-"+rptDate;
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 32);
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //单位
        POIExportUtil.setCell(sheet, 1, 0, "单位:万元" , cellStyle);


        //数据
        //设置贷种
        int combColumn = 1;
        HashMap<String, Integer> combMap = new HashMap<>();
        for (Map<String, Object> map : combList) {
            POIExportUtil.setCell(sheet, 2, combColumn, map.get("combname").toString() , cellStyle);
            combMap.put(map.get("combcode").toString(), combColumn);
            combColumn++;
        }
        //设置机构
        int organRow = 3;
        HashMap<String, Integer> organMap = new HashMap<>();
        for (FdOrgan fdOrgan : organList) {
            POIExportUtil.setCell(sheet, organRow, 0, fdOrgan.getOrganname() , cellStyle);
            organMap.put(fdOrgan.getThiscode(), organRow);
            organRow++;
        }
        //插入数据
        for (String organ : organMap.keySet()) {
            Integer row = organMap.get(organ);
            for (String comb : combMap.keySet()) {
                Integer column = combMap.get(comb);
                POIExportUtil.setCell(sheet, row, column, BigDecimalUtil.divide(getSafeCount(baseInfoMap.get(organ+"_"+comb)),new BigDecimal("10000")) , cellStyle);
            }
        }

        //设置列宽
        for (int i = 0; i <combMap.size()+1; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet,1,3);

        //文件名
        filename = filename+ ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         清空response
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * 信贷到期量日报表下载
     * @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate
     */
    @Override
    public void reportCreditMaturityDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception{
        // 获取输出流
        OutputStream os = response.getOutputStream();

        //创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //居中样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        /*---------写入文件---------*/

        String filename = "信贷到期量日报表-"+rptDate;
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 32);
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //单位
        POIExportUtil.setCell(sheet, 1, 0, "单位:万元" , cellStyle);


        //数据
        //设置贷种
        int combColumn = 1;
        HashMap<String, Integer> combMap = new HashMap<>();
        for (Map<String, Object> map : combList) {
            POIExportUtil.setCell(sheet, 2, combColumn, map.get("combname").toString() , cellStyle);
            combMap.put(map.get("combcode").toString(), combColumn);
            combColumn++;
        }
        //设置机构
        int organRow = 3;
        HashMap<String, Integer> organMap = new HashMap<>();
        for (FdOrgan fdOrgan : organList) {
            POIExportUtil.setCell(sheet, organRow, 0, fdOrgan.getOrganname() , cellStyle);
            organMap.put(fdOrgan.getThiscode(), organRow);
            organRow++;
        }
        //插入数据
        for (String organ : organMap.keySet()) {
            Integer row = organMap.get(organ);
            for (String comb : combMap.keySet()) {
                Integer column = combMap.get(comb);
                POIExportUtil.setCell(sheet, row, column, BigDecimalUtil.divide(getSafeCount(baseInfoMap.get(organ+"_"+comb)),new BigDecimal("10000")) , cellStyle);
            }
        }

        //设置列宽
        for (int i = 0; i <combMap.size()+1 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet,1,3);

        //文件名
        filename = filename+ ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         清空response
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * 首页 - 获取机构本月净增量信息（柱形图）
     *
     * @param organlevel
     * @param organCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexOrganBarInfo(String organlevel, String organCode, String organName, String operCode) throws Exception {
        String month = sdf.format(new Date());
        List<Map<String, Object>> resultListAll = new ArrayList<>(); //含合计
        List<Map<String, Object>> resultList = new ArrayList<>(); //不含合计

        try {
            //判断当前柜员是否有所属条线
            List<Map<String, Object>> operLineList = tbRptBaseinfoMapper.selectOperLine(operCode);

            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------总行登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                if (operLineList != null && !operLineList.isEmpty()) {
                    //总行本部/所有一分
                    List<FdOrgan> oneOrganList = this.selectSubOrgan(organCode, organlevel);
                    for (FdOrgan oneOrgan : oneOrganList) {

                        Integer planType = null;
                        TbPlan tbPlan = new TbPlan();
                        tbPlan.setPlanMonth(month);
                        tbPlan.setPlanOrgan(oneOrgan.getThiscode());
                        tbPlan.setPlanType(2);
                        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                        List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                        planType = (planList != null && !planList.isEmpty()) ? 2 : 1;


                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("month", month);
                        paramMap.put("planType", planType);
                        paramMap.put("oneOrganCode", oneOrgan.getThiscode());
                        paramMap.put("operCode", operCode);
                        Map<String, Object> result = tbRptBaseinfoMapper.selectIndexOrganBarInfoOnehasLine(paramMap);
                        resultList.add(result);
                    }
                } else {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("month", month);
                    paramMap.put("loginOrganCode", organCode);
                    List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexOrganBarInfoOneNohasLine(paramMap);
                    resultList.addAll(result);
                }

                //更新总行名称
                for (Map<String, Object> resultMap : resultList) {
                    if (Constant.HEAD_OFFICE_CODE.equals(resultMap.get("organcode"))) {
                        resultMap.put("organname", "总行");
                        break;
                    }
                }

                //合计
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                //合计-机构合计 如果总行只制定了条线计划，合计就展示条线的
                BigDecimal planamount = new BigDecimal(planamountCollect.getSum() + "");
                if (planamount.compareTo(new BigDecimal("0")) == 0) {
                    TbPlan tbPlan = new TbPlan();
                    tbPlan.setPlanMonth(month);
                    tbPlan.setPlanOrgan(organCode);
                    tbPlan.setPlanType(TbPlan.PLAN);
                    tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                    List<TbPlan> tbPlanList = tbPlanMapper.selectByAttr(tbPlan);
                    if (tbPlanList == null || tbPlanList.size() == 0) {
                        TbPlan tbPlan2 = new TbPlan();
                        tbPlan2.setPlanMonth(month);
                        tbPlan2.setPlanOrgan(organCode);
                        tbPlan2.setPlanType(TbPlan.STRIPE);
                        tbPlan2.setPlanStatus(TbReqDetail.STATE_APPROVED);
                        List<TbPlan> tbPlanList2 = tbPlanMapper.selectByAttr(tbPlan2);
                        for (TbPlan plan : tbPlanList2) {
                            planamount = planamount.add(plan.getPlanRealIncrement().multiply(new BigDecimal("10000")));
                        }
                    }

                }


                Map<String, Object> result = new HashMap<>();
                result.put("organname", "机构合计");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                result.put("planamount", planamount);
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------一分登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                if (operLineList != null && !operLineList.isEmpty()) {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("month", month);
                    paramMap.put("loginOrganCode", organCode);
                    paramMap.put("operCode", operCode);
                    List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexOrganBarInfoTwohasLine(paramMap);
                    resultList.addAll(result);
                } else {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("month", month);
                    paramMap.put("loginOrganCode", organCode);
                    List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexOrganBarInfoTwoNohasLine(paramMap);
                    resultList.addAll(result);
                }

                //更新添加本部字段
                for (Map<String, Object> resultMap : resultList) {
                    if (organCode.equals(resultMap.get("organcode"))) {
                        resultMap.put("organname", resultMap.get("organname") + "本部");
                        break;
                    }
                }


                //汇总
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                BigDecimal planamount = new BigDecimal(planamountCollect.getSum() + "");
                if (planamount.compareTo(new BigDecimal("0")) == 0) {
                    //-----汇总-计划净增量  如果该月未制定计划，那么汇总值显示上级机构给他制定的计划；如果制定了条线计划，汇总条线计划值-----
                    TbPlan tbPlan = new TbPlan();
                    tbPlan.setPlanMonth(month);
                    tbPlan.setPlanOrgan(organCode);
                    tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                    List<TbPlan> tbPlanList = tbPlanMapper.selectByAttr(tbPlan);
                    if (tbPlanList == null || tbPlanList.size() == 0) {
                        //机构 和 条线 都没有制定 查询上级机构制定的计划
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("organCode", organCode);
                        paramMap.put("planMonth", month);
                        List<Map<String, Object>> planList = tbPlanMapper.selectUporganPlan(paramMap);
                        if (planList != null && planList.size() != 0) {
                            Map<String, Object> map = planList.get(0);
                            planamount = new BigDecimal(map.get("amount").toString()).multiply(new BigDecimal("10000"));
                        }
                    } else {
                        TbPlan tbPlan1 = tbPlanList.get(0);
                        if (tbPlan1.getPlanType() == TbPlan.PLAN) {
                            // 制定了机构计划，不作处理
                        } else {
                            // 制定了条线计划 汇总值为条线计划汇总值
                            TbPlanDetail tbPlanDetail = new TbPlanDetail();
                            tbPlanDetail.setPdRefId(tbPlan1.getPlanId());
                            List<TbPlanDetail> tbPlanDetailList = tbPlanDetailMapper.selectByAttr(tbPlanDetail);
                            for (TbPlanDetail planDetail : tbPlanDetailList) {
                                planamount = planamount.add(planDetail.getPdAmount().multiply(new BigDecimal("10000")));
                            }
                        }

                    }
                }
                Map<String, Object> result = new HashMap<>();
                result.put("organname", "机构合计");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                result.put("planamount", planamount);
                resultListAll.add(result);
                resultListAll.addAll(resultList);
            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------二分或其他登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                if (operLineList != null && !operLineList.isEmpty()) {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("month", month);
                    paramMap.put("loginOrganCode", WebContextUtil.getSessionOrgan().getUporgan());
                    paramMap.put("operCode", operCode);
                    List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexOrganBarInfoTwohasLine(paramMap);
                    resultList.addAll(result);
                } else {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("month", month);
                    paramMap.put("loginOrganCode", WebContextUtil.getSessionOrgan().getUporgan());
                    List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexOrganBarInfoTwoNohasLine(paramMap);
                    resultList.addAll(result);
                }

                //删除其他机构，只留下当前登录机构
                Iterator<Map<String, Object>> iterator = resultList.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> next = iterator.next();
                    if (!organCode.equals(next.get("organcode").toString())) {
                        iterator.remove();
                    }
                }

                //更新添加本部字段
                for (Map<String, Object> resultMap : resultList) {
                    if (organCode.equals(resultMap.get("organcode"))) {
                        resultMap.put("organname", resultMap.get("organname") + "本部");
                        break;
                    }
                }

                resultListAll.addAll(resultList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("首页 - 获取机构本月净增量信息", e);
            throw e;
        }
        return resultListAll;
    }

    /**
     * 首页 - 二级贷种本月净增量（柱形图）
     *
     * @param organlevel
     * @param organCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexCombBarInfo(String organlevel, String organCode, String operCode) throws Exception {
        String month = sdf.format(new Date());
        List<Map<String, Object>> resultListAll = new ArrayList<>(); //含合计
        List<Map<String, Object>> resultList = new ArrayList<>(); //不含合计
        try {
            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoOne(paramMap);

                //合计
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                Map<String, Object> result = new HashMap<>();
                result.put("combname", "贷种合计");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("planamount", planamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoTwo(paramMap);

                //合计
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                Map<String, Object> result = new HashMap<>();
                result.put("combname", "贷种合计");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("planamount", planamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------二分或其他登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("planOrganCode", WebContextUtil.getSessionOrgan().getUporgan());
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoThree(paramMap);

                //合计
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                Map<String, Object> result = new HashMap<>();
                result.put("combname", "贷种合计");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("planamount", planamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("首页 - 二级贷种本月净增量", e);
            throw e;
        }
        return resultListAll;
    }

    /**
     * 首页 - 二级贷种本月计划完成率（折线图）
     *
     * @param organlevel
     * @param organCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexCombLineInfo(String organlevel, String organCode, String operCode) throws Exception {
        String month = sdf.format(new Date());
        List<Map<String, Object>> resultList = new ArrayList<>(); //不含合计

        try {
            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                //计划净增量
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoForPlanOne(paramMap);

                //实际净增量
                if (resultList != null && !resultList.isEmpty()) {
                    Map<String, Object> paramMap2 = new HashMap<>();
                    for (Map<String, Object> map : resultList) {
                        paramMap2.put("month", month);
                        paramMap2.put("combcode", map.get("combcode"));
                        map.put("realityamount", tbRptBaseinfoMapper.selectIndexCombBarInfoForRealOne(paramMap2));
                    }
                }
            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;


                //计划净增量
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoForPlanTwo(paramMap);

                //实际净增量
                if (resultList != null && !resultList.isEmpty()) {
                    Map<String, Object> paramMap2 = new HashMap<>();
                    for (Map<String, Object> map : resultList) {
                        paramMap2.put("month", month);
                        paramMap2.put("combcode", map.get("combcode"));
                        paramMap2.put("organCode", organCode);
                        map.put("realityamount", tbRptBaseinfoMapper.selectIndexCombBarInfoForRealTwo(paramMap2));
                    }
                }
            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------二分或其他登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;


                //计划净增量
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("planOrganCode", WebContextUtil.getSessionOrgan().getUporgan());
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoForPlanThree(paramMap);

                //实际净增量
                if (resultList != null && !resultList.isEmpty()) {
                    Map<String, Object> paramMap2 = new HashMap<>();
                    for (Map<String, Object> map : resultList) {
                        paramMap2.put("month", month);
                        paramMap2.put("combcode", map.get("combcode"));
                        paramMap2.put("organCode", organCode);
                        map.put("realityamount", tbRptBaseinfoMapper.selectIndexCombBarInfoForRealThree(paramMap2));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("首页 - 二级贷种本月计划完成率", e);
            throw e;
        }
        return resultList;
    }

    /**
     * 首页 - 预警线完成率
     *
     * @param organlevel
     * @param organCode
     * @param operCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexWarnCompleteInfo(String organlevel, String organCode, String operCode) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            String month = sdf.format(new Date());

            //判断当前柜员是否有所属条线
            boolean isExistLine = false;
            List<Map<String, Object>> operLineList = tbRptBaseinfoMapper.selectOperLine(operCode);
            if (operLineList != null && !operLineList.isEmpty()) {
                isExistLine = true;
            }

            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------总行登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */

                // 2级贷种机构计划优先级最高，条线计划，1级贷种机构计划优先级最低
                Integer planType = 1;

                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                tbPlan.setPlanType(1);
                tbPlan.setCombLevel(2);
                List<TbPlan> planList2 = tbPlanMapper.selectByAttr(tbPlan);
                planType = (planList2 != null && !planList2.isEmpty()) ? 1 : planType;

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 1);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnCompleteOne(paramMap);
                resultList.addAll(result);

            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------一分登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 1);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnCompleteTwo(paramMap);
                resultList.addAll(result);
            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------二分或其他登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */

                //二分只查看一分给自己制定的机构计划
                Integer planType = 1;

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 1);
                paramMap.put("planOrganCode", WebContextUtil.getSessionOrgan().getUporgan());
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnCompleteThree(paramMap);
                resultList.addAll(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("首页 - 预警线完成率", e);
            throw e;
        }
        return resultList;
    }

    /**
     * 首页 - 预警线绝对值
     *
     * @param organlevel
     * @param organCode
     * @param operCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexWarnAbsInfo(String organlevel, String organCode, String operCode) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            String month = sdf.format(new Date());

            //判断当前柜员是否有所属条线
            boolean isExistLine = false;
            List<Map<String, Object>> operLineList = tbRptBaseinfoMapper.selectOperLine(operCode);
            if (operLineList != null && !operLineList.isEmpty()) {
                isExistLine = true;
            }

            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------总行登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 0);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnAbsOne(paramMap);
                resultList.addAll(result);

            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------一分登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 0);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnAbsTwo(paramMap);
                resultList.addAll(result);
            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------二分或其他登录查询------------>>>>>>>>>>>>>>>>>>>>>>>> */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 0);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnAbsThree(paramMap);
                resultList.addAll(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("首页 - 预警线绝对值", e);
            throw e;
        }
        return resultList;
    }

    /*一级贷种树形多选列表*/
    @Override
    public String combLevelOneTreeList() {

        HashMap<String, Object> resultMap = new HashMap<>();

        //查询一级贷种
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("combLevel", 1);
        List<Map<String, Object>> combList = reportCombMapper.selectComb(paramMap);

        //组装树形多选
        ArrayList<HashMap<String, String>> treeMapList = new ArrayList<>();
        HashMap<String, String> faMap = new HashMap<>();
        faMap.put("id", "father");
        faMap.put("name", "全选");
        faMap.put("parentId", "0");
        treeMapList.add(faMap);
        for (Map<String, Object> map : combList) {
            HashMap<String, String> treeMap = new HashMap<>();
            treeMap.put("id", map.get("combcode").toString());
            treeMap.put("name", map.get("combname").toString());
            treeMap.put("parentId", "father");
            treeMapList.add(treeMap);
        }
        resultMap.put("treeNodes", treeMapList);
        return JsonUtils.toJson(resultMap);
    }

    /*通过贷种级别和一级贷种 查出所属该一级贷种该级别的所有贷种*/


    /**
     * 查询本期需求
     *
     * @param prodectReportList list
     * @param date              报表日期  yyyymmdd
     * @param cycel             表周期 1-年 2-季 3-月 4-日
     * @param fdorgan           机构号
     */
    private void getCurrentIncreaseReqOrgan(List<ProductReport> prodectReportList, String date, String cycel, FdOrgan fdorgan) {
        String[] timePeriodArr = getTimePeriods(date, cycel);
        Map<String, BigDecimal> comb_num_Map = new HashMap<>();
        for (String timePeriod : timePeriodArr) {
            TbReqList searchTbReqList = new TbReqList();
            searchTbReqList.setReqMonth(timePeriod);
            searchTbReqList.setReqType(1);
            searchTbReqList.setReqOrgan(fdorgan.getThiscode());
            List<TbReqList> tbReqLists = tbReqListMapper.selectByAttr(searchTbReqList);
            if (tbReqLists != null && tbReqLists.size() > 0) {
                int req_ref_id = tbReqLists.get(0).getReqId();
                TbReqDetail searchTb = new TbReqDetail();
                searchTb.setReqdRefId(req_ref_id);
                searchTb.setReqdState(TbReqDetail.STATE_APPROVED);
                List<TbReqDetail> tbReqDetails = tbReqDetailMapper.selectByAttr(searchTb);
                if (tbReqDetails != null && tbReqDetails.size() > 0) {
                    for (TbReqDetail tempTb : tbReqDetails) {
                        String combCode = tempTb.getReqdCombCode();
                        BigDecimal tempNum = comb_num_Map.get(combCode);
                        if (tempNum == null) {
                            tempNum = BigDecimal.ZERO;
                        }
                        tempNum = tempNum.add(tempTb.getReqdReqnum());
                        comb_num_Map.put(combCode, tempNum);
                    }
                }
            }
        }
        //从map中遍历取值 set到 list中
        for (ProductReport pr : prodectReportList) {
            String combCodeStr = pr.getId();
            BigDecimal num = comb_num_Map.get(combCodeStr);
            if (num != null) {
                pr.setCurrent_increase_req_organ(num);
            } else {
                pr.setCurrent_increase_req_organ(BigDecimal.ZERO);
            }
        }


    }


    /**
     * 查询本期需求条线
     *
     * @param prodectReportList list
     * @param date              报表日期  yyyymmdd
     * @param cycel             表周期 1-年 2-季 3-月 4-日
     * @param fdorgan           机构号
     */
    private void getCurrentIncreaseReqLine(List<ProductReport> prodectReportList, String date, String cycel, FdOrgan fdorgan) {
        String[] timePeriodArr = getTimePeriods(date, cycel);
        Map<String, BigDecimal> comb_num_Map = new HashMap<>();
        for (String timePeriod : timePeriodArr) {
            TbReqList searchTbReqList = new TbReqList();
            searchTbReqList.setReqMonth(timePeriod);
            searchTbReqList.setReqType(1);
            searchTbReqList.setReqOrgan(fdorgan.getThiscode());
            List<TbReqList> tbReqLists = tbReqListMapper.selectByAttr(searchTbReqList);
            if (tbReqLists != null && tbReqLists.size() > 0) {
                int line_ref_id = tbReqLists.get(0).getReqId();
                TbLineReqDetail searchTbLineReqDetail = new TbLineReqDetail();
                searchTbLineReqDetail.setLineOrgan(fdorgan.getThiscode());
                searchTbLineReqDetail.setLineRefId(line_ref_id);
                searchTbLineReqDetail.setLineState(TbLineReqDetail.STATE_APPROVED);
                List<TbLineReqDetail> tbLineReqDetails = tbLineReqDetailMapper.selectByAttr(searchTbLineReqDetail);
                if (tbLineReqDetails != null && tbLineReqDetails.size() > 0) {
                    for (TbLineReqDetail tempTb : tbLineReqDetails) {
                        String line_comb_code = tempTb.getLineCombCode();
                        String line_reqNum = tempTb.getLineReqnum();
                        if (line_comb_code != null && !line_comb_code.trim().equals("")) {
                            String[] lineCodeArr = line_comb_code.split(",");
                            String[] lineReqNum = line_reqNum.split(",");
                            for (int i = 0; i < lineCodeArr.length; i++) {
                                String combStr = lineCodeArr[i];
                                BigDecimal tempNum = comb_num_Map.get(combStr);
                                if (tempNum == null) {
                                    tempNum = BigDecimal.ZERO;
                                }
                                if (null != lineReqNum[i] && !"".equals(lineReqNum[i].trim())) {
                                    tempNum = tempNum.add(new BigDecimal(lineReqNum[i]));
                                    comb_num_Map.put(combStr, tempNum);
                                }
                            }
                        }
                    }
                }
            }
        }
        //从map中遍历取值 set到 list中
        for (ProductReport pr : prodectReportList) {
            String combCodeStr = pr.getId();
            BigDecimal num = comb_num_Map.get(combCodeStr);
            if (num != null) {
                pr.setCurrent_increase_req_line(num);
            } else {
                pr.setCurrent_increase_req_line(BigDecimal.ZERO);
            }
        }
    }

    /**
     * 根据具体日期 和周期类型得到  月份列表
     *
     * @param date  报表日期  yyyymmdd
     * @param cycel 表周期 1-年 2-季 3-月 4-日
     * @return ["202007","202008"]
     */
    public static String[] getTimePeriods(String date, String cycel) {
        String timePeriodStr = "";
        Calendar cal = Calendar.getInstance();
        try {
            Date searchDate = sdf_yyyyMMdd.parse(date);
            cal.setTime(searchDate);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            int startMonth = 1;
            if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                startMonth = 1;
            } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                if (month >= 1 && month <= 3) {
                    startMonth = 1;
                } else if (month >= 4 && month <= 6) {
                    startMonth = 4;
                } else if (month >= 7 && month <= 9) {
                    startMonth = 7;
                } else if (month >= 10 && month <= 12) {
                    startMonth = 10;
                }
            } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                startMonth = month;
            } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                startMonth = month;
            }
            while (startMonth <= month) {
                String startMonthStr;
                if (startMonth < 10) {
                    startMonthStr = "0" + startMonth;
                } else {
                    startMonthStr = String.valueOf(startMonth);
                }
                timePeriodStr = timePeriodStr + year + startMonthStr + ",";
                startMonth++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timePeriodStr.split(",");

    }

    /**
     * 获取去年的今天
     *
     * @param date yyyymmdd
     * @return
     */
    public static String getLastYearDay(String date) {
        int dateNum = 0;
        try {
            dateNum = Integer.parseInt(date);
            dateNum -= 10000;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dateNum + "";
    }


    /**
     * 排序list
     * 各个字段排序自行添加
     *
     * @param productReportList
     */
    public static void rankProductReportList(List<ProductReport> productReportList) {
        //排序本期机构需求字段
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getCurrent_increase_req_organ().compareTo(o1.getCurrent_increase_req_organ());
            }
        });
        int i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_req_rank(i);
            i++;
        }

        //本期净增排名
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getCurrent_increase_num().compareTo(o1.getCurrent_increase_num());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_rank(i);
            i++;
        }

        //增速排名
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getCurrent_increase_num_growth_rate().compareTo(o1.getCurrent_increase_num_growth_rate());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_growth_rate_rank(i);
            i++;
        }

        //排序余额
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getBalance().compareTo(o1.getBalance());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setBalanceRank(i);
            i++;
        }

        // 本期发生量排名
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getOcc().compareTo(o1.getOcc());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setOccRank(i);
            i++;
        }

        //本期计划完成率
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getCurrent_plan_completion_rate().compareTo(o1.getCurrent_plan_completion_rate());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_plan_completion_rate_rank(i);
            i++;
        }

        //本期预计到期量排名
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getExpireEstimate().compareTo(o1.getExpireEstimate());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setExpireEstimateRank(i);
            i++;
        }

        //本期已到期量排名
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getExpire().compareTo(o1.getExpire());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setExpireRank(i);
            i++;
        }

        //本期剩余预计到期量排名
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o2.getExpireEstimateLeft().compareTo(o1.getExpireEstimateLeft());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setExpireEstimateLeftRank(i);
            i++;
        }


        //排序贷种组合字段
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o1.getCombOrder().compareTo(o2.getCombOrder());
            }
        });
    }


    /**
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date     报表日期  yyyymmdd
     * @param cycel    报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan  当前登录机构
     * @return
     */
    @Override
    public List<ProductReport> getProductReportData(String combType, String date, String cycel, FdOrgan fdorgan) {

        initComb();

        /*--------获取数据list 汇总到贷种 ----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getCombDataList(date, combType, fdorgan);


        /*---------组装数据----------*/
        // 构建以贷种为基础的基础list
        List<ProductReport> productReportList = getProductReportListBycomb(combType);

        if (productReportList == null || productReportList.size() == 0) {
            return new ArrayList<>();
        }

        //复制一条基础list
        List<ProductReport> productReportListCopy = copyProductList(productReportList);
        // 余额 余额占比
        getProductBalance(productReportList, tbRptBaseinfoLoankindList);
        // 余额占比较期初
        getProductbalanceCompareDateBegin(productReportList, productReportListCopy, date, cycel, combType, fdorgan, tbRptBaseinfoLoankindList);
        // 本期净增需求(条线)
        getCurrentIncreaseReqLine(productReportList, date, cycel, fdorgan);
        // 本期净增需求(分行)
        getCurrentIncreaseReqOrgan(productReportList, date, cycel, fdorgan);
        // 本期计划
        getProductPlanAmount(productReportList, combType, date, cycel, fdorgan);
        // 当日净增
        getTodayIncreaseNum(productReportList, tbRptBaseinfoLoankindList);
        // 本期净增
        getCurrentIncreaseNum(productReportList, tbRptBaseinfoLoankindList, cycel);
        // 本期超计划
        getCurrentOverPlanNum(productReportList);
        // 本期净增占比
        getCurrentIncreaseNumProportion(productReportList);
        // 本期计划完成率
        getCurrentPlanCompletionRate(productReportList);
        // 本期增速 本期净增 除以 期初余额
        getCurrentIncreaseNumGrowthRate(productReportList, tbRptBaseinfoLoankindList, cycel);
        // 本期发生量 本期预计到期量 本期已到期量 本期剩余预计到期量
        getProductOccAndExpire(productReportList, tbRptBaseinfoLoankindList, cycel);

        //同比计算---- 本期发生量同比  本期预计到期量同比 本期已到期量同比 本期剩余预计到期量同比 本期净增同比  本期计划完成率同比 本期增速同比
        getProductYoy(productReportList, cycel, productReportListCopy, date, combType, fdorgan);

        //排名
        rankProductReportList(productReportList);
        //添加求和，中位数，众数，最大值，最小值等
        addProductOtherList(productReportList, "-1", 1);


        //转换为树形列表
        List<ProductReport> resultList = ProductReport.transToTree(productReportList, 3, "-1", 1);
        return resultList;
    }


    //获取数据list 汇总到贷种
    private List<TbRptBaseinfoLoankind> getCombDataList(String date, String combType, FdOrgan fdorgan) {
        //当天基础数据List
        HashMap<String, Object> param = new HashMap<>();
        String[] combListParam = null;
        String[] excludeCombListParam = null;
        // 1 全部；2少拆放；4 实体
        if (ReportConstant.combType_1.equals(combType)) {

        } else if (ReportConstant.combType_2.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_CFFY2;
            excludeCombListParam = combParam.split(",");
        } else if (ReportConstant.combType_4.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_QTSTDK1;
            combListParam = combParam.split(",");
        } else {

        }
        param.put("rptDate", date);
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("rptOrgan", fdorgan.getThiscode());
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByDateAndOrgan(param);
        return tbRptBaseinfoLoankindList;
    }

    //计算产品表--同比
    private void getProductYoy(List<ProductReport> productReportList, String cycel, List<ProductReport> productReportListCopy, String date, String combType, FdOrgan fdorgan) {
        String beginDate = getLastYearDay(date);
        //当天基础数据List
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getCombDataList(beginDate, combType, fdorgan);
        // 发生量 ...
        getProductOccAndExpire(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // 净增
        getTodayIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList);
        //计划完成率同比
        // 本期计划
        getProductPlanAmount(productReportListCopy, combType, beginDate, cycel, fdorgan);
        // 本期净增
        getCurrentIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // 本期计划完成率
        getCurrentPlanCompletionRate(productReportListCopy);
        //本期增速
        //余额 余额占比
        getProductBalance(productReportListCopy, tbRptBaseinfoLoankindList);
        // 本期增速 当前净增 除以 期初余额
        getCurrentIncreaseNumGrowthRate(productReportListCopy, tbRptBaseinfoLoankindList, cycel);


        // 本期发生量同比
        BigDecimal occYoy = BigDecimal.ZERO;
        // 本期预计到期量同比
        BigDecimal expireEstimateYoy = BigDecimal.ZERO;
        // 本期已到期量同比
        BigDecimal expireYoy = BigDecimal.ZERO;
        // 本期剩余预计到期量同比
        BigDecimal expireEstimateLeftYoy = BigDecimal.ZERO;
        //本期净增同比
        BigDecimal current_increase_num_yoy = BigDecimal.ZERO;
        // 计划完成率同比
        BigDecimal current_plan_completion_rate_yoy = BigDecimal.ZERO;
        // 本期增速同比
        BigDecimal current_increase_num_growth_rate_yoy = BigDecimal.ZERO;


        for (ProductReport productReport : productReportList) {
            for (ProductReport report : productReportListCopy) {
                if (productReport.getId().equals(report.getId())) {
                    //同比  当前值-去年值
                    occYoy = BigDecimalUtil.subtract(productReport.getOcc(), report.getOcc());
                    expireEstimateYoy = BigDecimalUtil.subtract(productReport.getExpireEstimate(), report.getExpireEstimate());
                    expireYoy = BigDecimalUtil.subtract(productReport.getExpire(), report.getExpire());
                    expireEstimateLeftYoy = BigDecimalUtil.subtract(productReport.getExpireEstimateLeft(), report.getExpireEstimateLeft());
                    current_increase_num_yoy = BigDecimalUtil.subtract(productReport.getCurrent_increase_num(), report.getCurrent_increase_num());
                    current_plan_completion_rate_yoy = BigDecimalUtil.subtract(productReport.getCurrent_plan_completion_rate(), report.getCurrent_plan_completion_rate());
                    current_increase_num_growth_rate_yoy = BigDecimalUtil.subtract(productReport.getCurrent_increase_num_growth_rate(), report.getCurrent_increase_num_growth_rate());


                    productReport.setOccYoy(occYoy);
                    productReport.setExpireEstimateYoy(expireEstimateYoy);
                    productReport.setExpireYoy(expireYoy);
                    productReport.setExpireEstimateLeftYoy(expireEstimateLeftYoy);
                    productReport.setCurrent_increase_num_yoy(current_increase_num_yoy);
                    productReport.setCurrent_plan_completion_rate_yoy(current_plan_completion_rate_yoy);
                    productReport.setCurrent_increase_num_growth_rate_yoy(current_increase_num_growth_rate_yoy);
                    break;
                }
            }
        }
    }


    /**
     * 计算本期增速
     *
     * @param productReportList
     * @param tbRptBaseinfoLoankindList
     * @param cycel                     报表周期 1-年 2-季 3-月 4-日
     */
    private void getCurrentIncreaseNumGrowthRate(List<ProductReport> productReportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String cycel) {
        for (ProductReport tempPr : productReportList) {
            String combCode = tempPr.getId();
            BigDecimal count = BigDecimal.ZERO;
            for (TbRptBaseinfoLoankind tempTbl : tbRptBaseinfoLoankindList) {
                if (combCode.equals(tempTbl.getLoanKind())) {
                    if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                        count = BigDecimalUtil.add(count, tempTbl.getBalanceYear());
                    } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                        count = BigDecimalUtil.add(count, tempTbl.getBalanceSeason());
                    } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                        count = BigDecimalUtil.add(count, tempTbl.getBalanceMonth());
                    } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                        count = BigDecimalUtil.add(count, BigDecimalUtil.subtract(tempTbl.getBalance(),tempTbl.getDayExpire()));
                    }
                }
            }
            tempPr.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), count.divide(ReportConstant.MONEY_NUM), 4).multiply(ReportConstant.RATIO_NUM));
        }

        //计算一级，二级贷种的本期增速
        HashMap<String, BigDecimal> rateMap = new HashMap<>();
        for (ProductReport productReport : productReportList) {
            String combLevelOne = threeToOneMap.get(productReport.getId());
            if (combLevelOne != null) {
                rateMap.put(combLevelOne, getSafeCount(rateMap.get(combLevelOne)).add(getSafeCount(productReport.getCurrent_increase_num_growth_rate())));
            }
            String combLevelTwo = threeToTwoMap.get(productReport.getId());
            if (combLevelTwo != null) {
                rateMap.put(combLevelTwo, getSafeCount(rateMap.get(combLevelTwo)).add(getSafeCount(productReport.getCurrent_increase_num_growth_rate())));
            }
        }

        //设置本期增速
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal current_increase_num_growth_rate = getSafeCount(rateMap.get(combCode));
            productReport.setCurrent_increase_num_growth_rate(getSafeCount(productReport.getCurrent_increase_num_growth_rate()).add(current_increase_num_growth_rate));
        }
    }


    /**
     * 产品表--本期发生量 本期预计到期量 本期已到期量 本期剩余预计到期量
     *
     * @param productReportList
     * @param tbRptBaseinfoLoankindList
     * @param cycel                     报表周期 1-年 2-季 3-月 4-日
     */
    private void getProductOccAndExpire(List<ProductReport> productReportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String cycel) {
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal occ = BigDecimal.ZERO;
            BigDecimal expireEstimate = BigDecimal.ZERO;
            BigDecimal expire = BigDecimal.ZERO;
            BigDecimal expireEstimateLeft = BigDecimal.ZERO;
            for (TbRptBaseinfoLoankind rptInfo : tbRptBaseinfoLoankindList) {
                if (combCode.equals(rptInfo.getLoanKind())) {
                    if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                        occ = BigDecimalUtil.add(occ, rptInfo.getYearOcc());
                        expireEstimate = BigDecimalUtil.add(expireEstimate, rptInfo.getYearExpireEstimate());
                        expire = BigDecimalUtil.add(expire, rptInfo.getYearExpire());
                        expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, rptInfo.getYearExpireEstimateLeft());

                    } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                        occ = BigDecimalUtil.add(occ, rptInfo.getSeasonOcc());
                        expireEstimate = BigDecimalUtil.add(expireEstimate, rptInfo.getSeasonExpireEstimate());
                        expire = BigDecimalUtil.add(expire, rptInfo.getSeasonExpire());
                        expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, rptInfo.getSeasonExpireEstimateLeft());

                    } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                        occ = BigDecimalUtil.add(occ, rptInfo.getMonthOcc());
                        expireEstimate = BigDecimalUtil.add(expireEstimate, rptInfo.getMonthExpireEstimate());
                        expire = BigDecimalUtil.add(expire, rptInfo.getMonthExpire());
                        expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, rptInfo.getMonthExpireEstimateLeft());

                    } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                        occ = BigDecimalUtil.add(occ, rptInfo.getDayOcc());
                        expireEstimate = BigDecimalUtil.add(expireEstimate, BigDecimal.ZERO);
                        expire = BigDecimalUtil.add(expire, rptInfo.getDayExpire());
                        expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, BigDecimal.ZERO);

                    }
                }
            }
            productReport.setOcc(BigDecimalUtil.divide(occ,ReportConstant.MONEY_NUM));
            productReport.setExpireEstimate(BigDecimalUtil.divide(expireEstimate,ReportConstant.MONEY_NUM));
            productReport.setExpire(BigDecimalUtil.divide(expire,ReportConstant.MONEY_NUM));
            productReport.setExpireEstimateLeft(BigDecimalUtil.divide(expireEstimateLeft,ReportConstant.MONEY_NUM));
        }

        //计算一级，二级贷种的本期发生量 本期预计到期量 本期已到期量 本期剩余预计到期量
        HashMap<String, BigDecimal> occeMap = new HashMap<>();
        HashMap<String, BigDecimal> expireEstimateMap = new HashMap<>();
        HashMap<String, BigDecimal> expireMap = new HashMap<>();
        HashMap<String, BigDecimal> expireEstimateLeftMap = new HashMap<>();
        for (ProductReport productReport : productReportList) {
            String combLevelOne = threeToOneMap.get(productReport.getId());
            if (combLevelOne != null) {
                occeMap.put(combLevelOne, getSafeCount(occeMap.get(combLevelOne)).add(getSafeCount(productReport.getOcc())));
                expireEstimateMap.put(combLevelOne, getSafeCount(expireEstimateMap.get(combLevelOne)).add(getSafeCount(productReport.getExpireEstimate())));
                expireMap.put(combLevelOne, getSafeCount(expireMap.get(combLevelOne)).add(getSafeCount(productReport.getExpire())));
                expireEstimateLeftMap.put(combLevelOne, getSafeCount(expireEstimateLeftMap.get(combLevelOne)).add(getSafeCount(productReport.getExpireEstimateLeft())));
            }
            String combLevelTwo = threeToTwoMap.get(productReport.getId());
            if (combLevelTwo != null) {
                occeMap.put(combLevelTwo, getSafeCount(occeMap.get(combLevelTwo)).add(getSafeCount(productReport.getOcc())));
                expireEstimateMap.put(combLevelTwo, getSafeCount(expireEstimateMap.get(combLevelTwo)).add(getSafeCount(productReport.getExpireEstimate())));
                expireMap.put(combLevelTwo, getSafeCount(expireMap.get(combLevelTwo)).add(getSafeCount(productReport.getExpire())));
                expireEstimateLeftMap.put(combLevelTwo, getSafeCount(expireEstimateLeftMap.get(combLevelTwo)).add(getSafeCount(productReport.getExpireEstimateLeft())));
            }
        }

        //设置一级，二级贷种的本期发生量 本期预计到期量 本期已到期量 本期剩余预计到期量
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal occ = getSafeCount(occeMap.get(combCode));
            BigDecimal expireEstimate = getSafeCount(expireEstimateMap.get(combCode));
            BigDecimal expire = getSafeCount(expireMap.get(combCode));
            BigDecimal expireEstimateLeft = getSafeCount(expireEstimateLeftMap.get(combCode));
            productReport.setOcc(getSafeCount(productReport.getOcc()).add(occ));
            productReport.setExpireEstimate(getSafeCount(productReport.getExpireEstimate()).add(expireEstimate));
            productReport.setExpire(getSafeCount(productReport.getExpire()).add(expire));
            productReport.setExpireEstimateLeft(getSafeCount(productReport.getExpireEstimateLeft()).add(expireEstimateLeft));
        }
    }

    /**
     * 本期计划完成率
     *
     * @param productReportList
     */
    public static void getCurrentPlanCompletionRate(List<ProductReport> productReportList) {
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_plan_completion_rate(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), tempPr.getPlanAmount(), 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    /**
     * 计算本期净增占比
     *
     * @param productReportList
     */
    public static void getCurrentIncreaseNumProportion(List<ProductReport> productReportList) {
        BigDecimal totalNum = BigDecimal.ZERO;
        for (ProductReport tempPr : productReportList) {
            totalNum = BigDecimalUtil.add(totalNum, tempPr.getCurrent_increase_num());
        }
        totalNum = BigDecimalUtil.divide(totalNum, new BigDecimal("3"));
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_proportion(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), totalNum, 4).multiply(ReportConstant.RATIO_NUM));
        }

    }


    /**
     * 产品表--余额占比较期初
     *
     * @param productReportList         基础list
     * @param productReportListCopy     复制的list
     * @param date                      报表日期  yyyymmdd
     * @param cycel                     报表周期 1-年 2-季 3-月 4-日
     * @param combType
     * @param fdorgan
     * @param tbRptBaseinfoLoankindList
     */
    private void getProductbalanceCompareDateBegin(List<ProductReport> productReportList, List<ProductReport> productReportListCopy, String date, String cycel, String combType, FdOrgan fdorgan, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList) {
        buildBalanceRatioOfBeginData(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        for (ProductReport productReport : productReportList) {
            for (ProductReport copy : productReportListCopy) {
                if (productReport.getId().equals(copy.getId())) {
                    BigDecimal subtract = BigDecimalUtil.subtract(productReport.getBalanceRatio(), copy.getBalanceRatio());
                    productReport.setBalanceRatioCompareDateBegin(subtract);
                    break;
                }
            }
        }
    }


    /**
     * 设置初期余额占比
     *
     * @param productReportListCopy
     * @param tbRptBaseinfoLoankindList 基础数据
     * @param cycel                     报表周期 1-年 2-季 3-月 4-日
     */
    private void buildBalanceRatioOfBeginData(List<ProductReport> productReportListCopy, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String cycel) {

        //期初总余额
        BigDecimal balanceCount = BigDecimal.ZERO;

        //组装三级贷种组合
        for (ProductReport productReport : productReportListCopy) {
            String combCode = productReport.getId();
            BigDecimal balance = BigDecimal.ZERO;
            for (TbRptBaseinfoLoankind info : tbRptBaseinfoLoankindList) {
                if (combCode.equals(info.getLoanKind())) {
                    if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                        balance = balance.add(getSafeCount(info.getBalanceYear()).divide(ReportConstant.MONEY_NUM));
                        balanceCount = BigDecimalUtil.add(balanceCount, info.getBalanceYear());
                    } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                        balance = balance.add(getSafeCount(info.getBalanceSeason()).divide(ReportConstant.MONEY_NUM));
                        balanceCount = BigDecimalUtil.add(balanceCount, info.getBalanceSeason());
                    } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                        balance = balance.add(getSafeCount(info.getBalanceMonth()).divide(ReportConstant.MONEY_NUM));
                        balanceCount = BigDecimalUtil.add(balanceCount, info.getBalanceMonth());
                    } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                        balance = balance.add(BigDecimalUtil.subtract(info.getBalance(),info.getDayExpire()).divide(ReportConstant.MONEY_NUM));
                        balanceCount = BigDecimalUtil.add(balanceCount, BigDecimalUtil.subtract(info.getBalance(),info.getDayExpire()));
                    }
                }
            }
            productReport.setBalance(getSafeCount(productReport.getBalance()).add(balance));
        }

        //计算一级，二级贷种的余额
        HashMap<String, BigDecimal> balanceMap = new HashMap<>();
        for (ProductReport productReport : productReportListCopy) {
            String combLevelOne = threeToOneMap.get(productReport.getId());
            if (combLevelOne != null) {
                balanceMap.put(combLevelOne, getSafeCount(balanceMap.get(combLevelOne)).add(getSafeCount(productReport.getBalance())));
            }
            String combLevelTwo = threeToTwoMap.get(productReport.getId());
            if (combLevelTwo != null) {
                balanceMap.put(combLevelTwo, getSafeCount(balanceMap.get(combLevelTwo)).add(getSafeCount(productReport.getBalance())));
            }
        }

        balanceCount = BigDecimalUtil.divide(balanceCount, ReportConstant.MONEY_NUM);

        //设置余额，余额占比
        for (ProductReport productReport : productReportListCopy) {
            String combCode = productReport.getId();
            BigDecimal balance = getSafeCount(balanceMap.get(combCode));
            productReport.setBalance(getSafeCount(productReport.getBalance()).add(balance));
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }

    }

    //复制一条list
    private List<ProductReport> copyProductList(List<ProductReport> productReportList) {
        ArrayList<ProductReport> list = new ArrayList<>();
        for (ProductReport productReport : productReportList) {
            ProductReport productReport2 = (ProductReport) BocoUtils.deepCopy(productReport);
            list.add(productReport2);
        }
        return list;
    }

    /**
     * 计算本期超计划=本期净增-本期计划
     *
     * @param productReportList
     */
    public static void getCurrentOverPlanNum(List<ProductReport> productReportList) {
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_over_plan_num(tempPr.getCurrent_increase_num().subtract(tempPr.getPlanAmount()));
        }

    }

    /**
     * 计算本期净增
     *
     * @param productReportList         报表详情list
     * @param tbRptBaseinfoLoankindList 数据库查的全量数据列表
     * @param cycel                     报表周期 1-年 2-季 3-月 4-日
     */
    private void getCurrentIncreaseNum(List<ProductReport> productReportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String cycel) {
        Map<String, BigDecimal> totoalMap = new HashMap<>();
        for (ProductReport tempPr : productReportList) {
            String combCode = tempPr.getId();
            for (TbRptBaseinfoLoankind tempTbl : tbRptBaseinfoLoankindList) {
                String loanKind = tempTbl.getLoanKind();
                BigDecimal dayIncrease = BigDecimal.ZERO;
                if (combCode.equals(loanKind)) {
                    if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                        dayIncrease = tempTbl.getYearIncrease();
                    } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                        dayIncrease = tempTbl.getSeasonIncrease();
                    } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                        dayIncrease = tempTbl.getMonthIncrease();
                    } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                        dayIncrease = tempTbl.getDayIncrease();
                    }

                    BigDecimal tempTotalNum = BigDecimalUtil.add(getSafeCount(totoalMap.get(combCode)), dayIncrease);
                    totoalMap.put(combCode, tempTotalNum);
                }
            }
        }
        for (ProductReport tempPr : productReportList) {
            String combCode = tempPr.getId();
            BigDecimal tempTotalNum = totoalMap.get(combCode);
            tempPr.setCurrent_increase_num(getSafeCount(tempTotalNum).divide(ReportConstant.MONEY_NUM));
        }


        //计算一级，二级贷种的本期净增
        HashMap<String, BigDecimal> balanceMap = new HashMap<>();
        for (ProductReport productReport : productReportList) {
            String combLevelOne = threeToOneMap.get(productReport.getId());
            if (combLevelOne != null) {
                balanceMap.put(combLevelOne, getSafeCount(balanceMap.get(combLevelOne)).add(getSafeCount(productReport.getCurrent_increase_num())));
            }
            String combLevelTwo = threeToTwoMap.get(productReport.getId());
            if (combLevelTwo != null) {
                balanceMap.put(combLevelTwo, getSafeCount(balanceMap.get(combLevelTwo)).add(getSafeCount(productReport.getCurrent_increase_num())));
            }
        }

        //设置本期净增
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal current_increase_num = getSafeCount(balanceMap.get(combCode));
            productReport.setCurrent_increase_num(BigDecimalUtil.add(productReport.getCurrent_increase_num(), current_increase_num));
        }

    }

    /**
     * 写入当日净增
     *
     * @param productReportList         报表详情list
     * @param tbRptBaseinfoLoankindList 数据库查的全量数据列表
     */
    private void getTodayIncreaseNum(List<ProductReport> productReportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList) {

        for (ProductReport tempPr : productReportList) {
            String combCode = tempPr.getId();
            BigDecimal tempTotalNum = BigDecimal.ZERO;
            for (TbRptBaseinfoLoankind tempTbl : tbRptBaseinfoLoankindList) {
                String loanKind = tempTbl.getLoanKind();
                if (combCode.equals(loanKind)) {
                    tempTotalNum = BigDecimalUtil.add(tempTotalNum, tempTbl.getDayIncrease());
                }
            }
            tempPr.setDay_increase_num(tempTotalNum.divide(ReportConstant.MONEY_NUM));
        }


        //计算一级，二级贷种的当日净增
        HashMap<String, BigDecimal> balanceMap = new HashMap<>();
        for (ProductReport productReport : productReportList) {
            String combLevelOne = threeToOneMap.get(productReport.getId());
            if (combLevelOne != null) {
                balanceMap.put(combLevelOne, getSafeCount(balanceMap.get(combLevelOne)).add(getSafeCount(productReport.getDay_increase_num())));
            }
            String combLevelTwo = threeToTwoMap.get(productReport.getId());
            if (combLevelTwo != null) {
                balanceMap.put(combLevelTwo, getSafeCount(balanceMap.get(combLevelTwo)).add(getSafeCount(productReport.getDay_increase_num())));
            }
        }

        //设置当日净增
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal day_increase_num = getSafeCount(balanceMap.get(combCode));
            productReport.setDay_increase_num(BigDecimalUtil.add(productReport.getDay_increase_num(), day_increase_num));
        }


    }

    /**
     * 余额 余额占比
     *
     * @param productReportList
     * @param tbRptBaseinfoLoankindList
     */
    private void getProductBalance(List<ProductReport> productReportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList) {
        //余额的总和
        BigDecimal balanceCount = BigDecimal.ZERO;
        //组装三级贷种组合
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal balance = BigDecimal.ZERO;
            for (TbRptBaseinfoLoankind baseinfo : tbRptBaseinfoLoankindList) {
                if (combCode.equals(baseinfo.getLoanKind())) {
                    balance = balance.add(getSafeCount(baseinfo.getBalance()).divide(ReportConstant.MONEY_NUM));
                    balanceCount = balanceCount.add(getSafeCount(baseinfo.getBalance()).divide(ReportConstant.MONEY_NUM));
                }
            }
            productReport.setBalance(getSafeCount(productReport.getBalance()).add(balance));
        }

        //计算一级，二级贷种的余额
        HashMap<String, BigDecimal> balanceMap = new HashMap<>();
        for (ProductReport productReport : productReportList) {
            String combLevelOne = threeToOneMap.get(productReport.getId());
            if (combLevelOne != null) {
                balanceMap.put(combLevelOne, getSafeCount(balanceMap.get(combLevelOne)).add(getSafeCount(productReport.getBalance())));
            }
            String combLevelTwo = threeToTwoMap.get(productReport.getId());
            if (combLevelTwo != null) {
                balanceMap.put(combLevelTwo, getSafeCount(balanceMap.get(combLevelTwo)).add(getSafeCount(productReport.getBalance())));
            }
        }

        //设置余额，余额占比
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal balance = getSafeCount(balanceMap.get(combCode));
            productReport.setBalance(getSafeCount(productReport.getBalance()).add(balance));
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    //产品表--添加求和，中位数，众数，最大值，最小值等
    private void addProductOtherList(List<ProductReport> productReportList, String parentId, int level) {

        /*-----------总数-------------*/
        //余额
        BigDecimal balance = BigDecimal.ZERO;
        //余额占比
        BigDecimal balanceRatio = BigDecimal.ZERO;
        //余额占比较期初
        BigDecimal balanceRatioCompareDateBegin = BigDecimal.ZERO;
        //本期净增需求(条线)
        BigDecimal current_increase_req_line = BigDecimal.ZERO;
        //本期净增需求(分行)
        BigDecimal current_increase_req_organ = BigDecimal.ZERO;
        //本期计划
        BigDecimal planAmount = BigDecimal.ZERO;
        //当日净增
        BigDecimal day_increase_num = BigDecimal.ZERO;
        //本期净增
        BigDecimal current_increase_num = BigDecimal.ZERO;
        //本期超计划
        BigDecimal current_over_plan_num = BigDecimal.ZERO;
        //本期净增同比
        BigDecimal current_increase_num_yoy = BigDecimal.ZERO;
        //本期净增占比
        BigDecimal current_increase_num_proportion = BigDecimal.ZERO;
        //本期计划完成率
        BigDecimal current_plan_completion_rate = BigDecimal.ZERO;
        //本期计划完成率同比
        BigDecimal current_plan_completion_rate_yoy = BigDecimal.ZERO;
        //本期净增增速
        BigDecimal current_increase_num_growth_rate = BigDecimal.ZERO;
        //本期净增增速同比
        BigDecimal current_increase_num_growth_rate_yoy = BigDecimal.ZERO;
        //本期发生量
        BigDecimal occ = BigDecimal.ZERO;
        //同比
        BigDecimal occYoy = BigDecimal.ZERO;
        //本期预计到期量
        BigDecimal expireEstimate = BigDecimal.ZERO;
        //同比
        BigDecimal expireEstimateYoy = BigDecimal.ZERO;
        //本期已到期量
        BigDecimal expire = BigDecimal.ZERO;
        //同比
        BigDecimal expireYoy = BigDecimal.ZERO;
        //本期剩余预计到期量
        BigDecimal expireEstimateLeft = BigDecimal.ZERO;
        //同比
        BigDecimal expireEstimateLeftYoy = BigDecimal.ZERO;

        for (ProductReport report : productReportList) {
            if (1 == report.getLevel()) {
                balance = BigDecimalUtil.add(balance, report.getBalance());
                balanceRatio = BigDecimalUtil.add(balanceRatio, report.getBalanceRatio());
                balanceRatioCompareDateBegin = BigDecimalUtil.add(balanceRatioCompareDateBegin, report.getBalanceRatioCompareDateBegin());
                current_increase_req_line = BigDecimalUtil.add(current_increase_req_line, report.getCurrent_increase_req_line());
                current_increase_req_organ = BigDecimalUtil.add(current_increase_req_organ, report.getCurrent_increase_req_organ());
                planAmount = BigDecimalUtil.add(planAmount, report.getPlanAmount());
                day_increase_num = BigDecimalUtil.add(day_increase_num, report.getDay_increase_num());
                current_increase_num = BigDecimalUtil.add(current_increase_num, report.getCurrent_increase_num());
                current_over_plan_num = BigDecimalUtil.add(current_over_plan_num, report.getCurrent_over_plan_num());
                current_increase_num_yoy = BigDecimalUtil.add(current_increase_num_yoy, report.getCurrent_increase_num_yoy());
                current_increase_num_proportion = BigDecimalUtil.add(current_increase_num_proportion, report.getCurrent_increase_num_proportion());
                current_plan_completion_rate = BigDecimalUtil.add(current_plan_completion_rate, report.getCurrent_plan_completion_rate());
                occ = BigDecimalUtil.add(occ, report.getOcc());
                expireEstimate = BigDecimalUtil.add(expireEstimate, report.getExpireEstimate());
                expire = BigDecimalUtil.add(expire, report.getExpire());
                expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, report.getExpireEstimateLeft());
                current_plan_completion_rate_yoy = BigDecimalUtil.add(current_plan_completion_rate_yoy, report.getCurrent_plan_completion_rate_yoy());
                current_increase_num_growth_rate = BigDecimalUtil.add(current_increase_num_growth_rate, report.getCurrent_increase_num_growth_rate());
                current_increase_num_growth_rate_yoy = BigDecimalUtil.add(current_increase_num_growth_rate_yoy, report.getCurrent_increase_num_growth_rate_yoy());
                occYoy = BigDecimalUtil.add(occYoy, report.getOccYoy());
                expireEstimateYoy = BigDecimalUtil.add(expireEstimateYoy, report.getExpireEstimateYoy());
                expireYoy = BigDecimalUtil.add(expireYoy, report.getExpireYoy());
                expireEstimateLeftYoy = BigDecimalUtil.add(expireEstimateLeftYoy, report.getExpireEstimateLeftYoy());
            }
        }

        ProductReport productReportSum = new ProductReport();
        productReportSum.setId("sum");
        productReportSum.setParentId(parentId);
        productReportSum.setName("合计");
        productReportSum.setLevel(level);

        productReportSum.setBalance(balance);
        productReportSum.setCurrent_increase_req_line(current_increase_req_line);
        productReportSum.setCurrent_increase_req_organ(current_increase_req_organ);
        productReportSum.setPlanAmount(planAmount);
        productReportSum.setDay_increase_num(day_increase_num);
        productReportSum.setCurrent_increase_num(current_increase_num);
        productReportSum.setCurrent_over_plan_num(current_over_plan_num);
        productReportSum.setCurrent_plan_completion_rate(current_plan_completion_rate);
        productReportSum.setOcc(occ);
        productReportSum.setExpireEstimate(expireEstimate);
        productReportSum.setExpire(expire);
        productReportSum.setExpireEstimateLeft(expireEstimateLeft);
        productReportSum.setBalanceRatio(balanceRatio);
        productReportSum.setBalanceRatioCompareDateBegin(balanceRatioCompareDateBegin);
        productReportSum.setCurrent_increase_num_yoy(current_increase_num_yoy);
        productReportSum.setCurrent_increase_num_proportion(current_increase_num_proportion);
        productReportSum.setCurrent_plan_completion_rate_yoy(current_plan_completion_rate_yoy);
        productReportSum.setCurrent_increase_num_growth_rate(current_increase_num_growth_rate);
        productReportSum.setCurrent_increase_num_growth_rate_yoy(current_increase_num_growth_rate_yoy);
        productReportSum.setOccYoy(occYoy);
        productReportSum.setExpireEstimateYoy(expireEstimateYoy);
        productReportSum.setExpireYoy(expireYoy);
        productReportSum.setExpireEstimateLeftYoy(expireEstimateLeftYoy);
        productReportSum.setBalanceRank(null);
        productReportSum.setOccRank(null);
        productReportSum.setCurrent_increase_req_rank(null);
        productReportSum.setCurrent_increase_num_rank(null);
        productReportSum.setCurrent_plan_completion_rate_rank(null);
        productReportSum.setCurrent_increase_num_growth_rate_rank(null);
        productReportSum.setExpireEstimateRank(null);
        productReportSum.setExpireRank(null);
        productReportSum.setExpireEstimateLeftRank(null);


        // // 平均数
        // ProductReport productReportAvg = new ProductReport();
        // productReportAvg.setId("avg");
        // productReportAvg.setParentId("-1");
        // productReportAvg.setName("平均数");
        // productReportAvg.setLevel(1);
        // // 中位数
        // ProductReport productReportMid = new ProductReport();
        // productReportSum.setId("mid");
        // productReportSum.setParentId("-1");
        // productReportSum.setName("中位数");
        // productReportSum.setLevel(1);
        // // 众数
        // ProductReport productReportMod = new ProductReport();
        // productReportMod.setId("mod");
        // productReportMod.setParentId("-1");
        // productReportMod.setName("众数");
        // productReportMod.setLevel(1);
        // // 最大值
        // ProductReport productReportMax = new ProductReport();
        // productReportMax.setId("max");
        // productReportMax.setParentId("-1");
        // productReportMax.setName("最大值");
        // productReportMax.setLevel(1);
        // // 最小值
        // ProductReport productReportMin = new ProductReport();
        // productReportMin.setId("min");
        // productReportMin.setParentId("-1");
        // productReportMin.setName("最小值");
        // productReportMin.setLevel(1);

        productReportList.add(0, productReportSum);
        // productReportList.add(productReportAvg);
        // productReportList.add(productReportMid);
        // productReportList.add(productReportMod);
        // productReportList.add(productReportMax);
        // productReportList.add(productReportMin);
    }

    //产品表--组装本期计划
    private void getOrganPlanAmount(List<ProductReport> productReportList, String combType, String date, String cycel, FdOrgan fdorgan) throws Exception {
        String[] months = null;

        /*---------查询数据-------*/
        String[] combListParam = null;
        String[] excludeCombListParam = null;
        // 1 全部；2少拆放；4 实体
        if (ReportConstant.combType_1.equals(combType)) {

        } else if (ReportConstant.combType_2.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_CFFY2;
            excludeCombListParam = combParam.split(",");
        } else if (ReportConstant.combType_4.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_QTSTDK1;
            combListParam = combParam.split(",");
        } else {

        }
        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        //详情表的organ
        param.put("pdorgan", fdorgan.getThiscode());
        //批次表的organ
        param.put("organ", WebContextUtil.getSessionOrgan().getThiscode());
        /*------------查询一级贷种计划(合计)---------*/
        // List<Map<String, Object>> planOfCombLevelOne = reportCombMapper.getPlanByLevelOne(param);
        List<Map<String, Object>> planOfCombLevelOne = reportCombMapper.getOrganPlanByLevelOne(param);
        /*------------查询二级贷种计划(合计)---------*/
        // List<Map<String, Object>> planOfCombLevelTwo = reportCombMapper.getPlanByLevelTwo(param);
        List<Map<String, Object>> planOfCombLevelTwo = reportCombMapper.getOrganPlanByLevelTwo(param);

        /*---------组装数据----------*/
        HashMap<String, Map<String, Object>> planOfCombMap = new HashMap<>();
        for (Map<String, Object> map : planOfCombLevelOne) {
            planOfCombMap.put(map.get("combcode").toString(), map);
        }
        for (Map<String, Object> map : planOfCombLevelTwo) {
            planOfCombMap.put(map.get("combcode").toString(), map);
        }

        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            Map<String, Object> map = planOfCombMap.get(combCode);
            if (map != null && map.size() > 0) {
                productReport.setPlanAmount(getSafeCount(map.get("amount")));
            } else {
                productReport.setPlanAmount(BigDecimal.ZERO);
            }
        }

    }

    //产品表--组装本期计划
    private void getProductPlanAmount(List<ProductReport> productReportList, String combType, String date, String cycel, FdOrgan fdorgan) {
        String[] months = null;

        /*---------查询数据-------*/
        String[] combListParam = null;
        String[] excludeCombListParam = null;
        // 1 全部；2少拆放；4 实体
        if (ReportConstant.combType_1.equals(combType)) {

        } else if (ReportConstant.combType_2.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_CFFY2;
            excludeCombListParam = combParam.split(",");
        } else if (ReportConstant.combType_4.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_QTSTDK1;
            combListParam = combParam.split(",");
        } else {

        }
        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        param.put("organ", fdorgan.getThiscode());
        /*------------查询一级贷种计划(合计)---------*/
        List<Map<String, Object>> planOfCombLevelOne = reportCombMapper.getPlanByLevelOne(param);
        /*------------查询二级贷种计划(合计)---------*/
        List<Map<String, Object>> planOfCombLevelTwo = reportCombMapper.getPlanByLevelTwo(param);

        /*---------组装数据----------*/
        HashMap<String, Map<String, Object>> planOfCombMap = new HashMap<>();
        for (Map<String, Object> map : planOfCombLevelOne) {
            planOfCombMap.put(map.get("combcode").toString(), map);
        }
        for (Map<String, Object> map : planOfCombLevelTwo) {
            planOfCombMap.put(map.get("combcode").toString(), map);
        }

        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            Map<String, Object> map = planOfCombMap.get(combCode);
            if (map != null && map.size() > 0) {
                productReport.setPlanAmount(getSafeCount(map.get("amount")));
            } else {
                productReport.setPlanAmount(BigDecimal.ZERO);
            }
        }

    }

    //产品表--构建以贷种为基础的基础list
    private List<ProductReport> getProductReportListBycomb(String combType) {
        String[] combListParam = null;
        String[] excludeCombListParam = null;
        // 1 全部；2少拆放；4 实体
        if (ReportConstant.combType_1.equals(combType)) {

        } else if (ReportConstant.combType_2.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_CFFY2;
            excludeCombListParam = combParam.split(",");
        } else if (ReportConstant.combType_4.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_QTSTDK1;
            combListParam = combParam.split(",");
        } else {

        }
        ArrayList<ProductReport> List = new ArrayList<>();
        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        List<Map<String, Object>> combList = reportCombMapper.getCombAllByLevelOne(param);

        for (Map<String, Object> comb : combList) {
            ProductReport productReport = new ProductReport();
            productReport.setId(comb.get("combcode").toString());
            productReport.setParentId(comb.get("upcombcode").toString());
            productReport.setName(comb.get("combname").toString());
            productReport.setLevel(Integer.parseInt(comb.get("comblevel").toString()));
            productReport.setCombOrder(Integer.parseInt(comb.get("comborder").toString()));
            List.add(productReport);
        }

        return List;
    }

    private void initComb() {
        List<Map<String, String>> threeToOneMapList = reportCombMapper.getThreeToOneMap();
        for (Map<String, String> map : threeToOneMapList) {
            threeToOneMap.put(map.get("combcode"), map.get("upcombcode"));
        }
        List<Map<String, String>> threeToTwoMapList = reportCombMapper.getThreeToTwoMap();
        for (Map<String, String> map : threeToTwoMapList) {
            threeToTwoMap.put(map.get("combcode"), map.get("upcombcode"));
        }
    }


    /**
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date     报表日期  yyyymmdd
     * @param cycel    报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan  机构
     * @return
     */
    @Override
    public List<ProductReport> getProductReportDataOfOrganExcel(String combType, String date, String cycel, FdOrgan fdorgan) throws Exception {

        initComb();

        //当天基础数据List
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getOrganCombDatalist(date, combType, fdorgan);


        /*---------组装数据----------*/
        // 构建以贷种为基础的基础list
        List<ProductReport> productReportList = getProductReportListBycomb(combType);

        if (productReportList == null || productReportList.size() == 0) {
            return new ArrayList<>();
        }

        if (tbRptBaseinfoLoankindList != null && tbRptBaseinfoLoankindList.size() > 0) {
            //复制一条基础list
            List<ProductReport> productReportListCopy = copyProductList(productReportList);
            // 余额 余额占比
            getProductBalance(productReportList, tbRptBaseinfoLoankindList);
            // 余额占比较期初
            getProductbalanceCompareDateBegin(productReportList, productReportListCopy, date, cycel, combType, fdorgan,tbRptBaseinfoLoankindList);
            // 本期净增需求(条线)
            getCurrentIncreaseReqLine(productReportList, date, cycel, fdorgan);
            // 本期净增需求(分行)
            getCurrentIncreaseReqOrgan(productReportList, date, cycel, fdorgan);
            // 本期计划
            // getProductPlanAmount(productReportList, combType, date, cycel, fdorgan);
            getOrganPlanAmount(productReportList, combType, date, cycel, fdorgan);
            // 当日净增
            getTodayIncreaseNum(productReportList, tbRptBaseinfoLoankindList);
            // 本期净增
            getCurrentIncreaseNum(productReportList, tbRptBaseinfoLoankindList, cycel);
            // 本期超计划
            getCurrentOverPlanNum(productReportList);
            // 本期净增占比
            getCurrentIncreaseNumProportion(productReportList);
            // 本期计划完成率
            getCurrentPlanCompletionRate(productReportList);
            // 本期增速 本期净增 除以 期初余额
            getCurrentIncreaseNumGrowthRate(productReportList, tbRptBaseinfoLoankindList, cycel);
            // 本期发生量 本期预计到期量 本期已到期量 本期剩余预计到期量
            getProductOccAndExpire(productReportList, tbRptBaseinfoLoankindList, cycel);

            //同比计算---- 本期发生量同比  本期预计到期量同比 本期已到期量同比 本期剩余预计到期量同比 本期净增同比  本期计划完成率同比 本期增速同比
            // getProductYoy(productReportList, cycel, productReportListCopy,date,combType,fdorgan);
            getOrganYoy(productReportList, cycel, productReportListCopy, date, combType, fdorgan);

            //排名
            rankProductReportList(productReportList);
        }

        //设置以及贷种的parentid为所属机构号 层级统一加2（机构，区域）,id 和parentid 带上机构号
        for (ProductReport productReport : productReportList) {
            if ("-1".equals(productReport.getParentId())) {
                productReport.setParentId(fdorgan.getThiscode());
                productReport.setId(productReport.getId() + fdorgan.getThiscode());
            } else {
                productReport.setId(productReport.getId() + fdorgan.getThiscode());
                productReport.setParentId(productReport.getParentId() + fdorgan.getThiscode());
            }
            productReport.setLevel(productReport.getLevel() + 2);
        }


        return productReportList;
    }

    /**
     * 下载报表一产品表
     * @param response
     * @param request
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date     报表日期  yyyymmdd
     * @param cycel    报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan  当前登录机构
     * @param unit
     */
    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String unit) throws Exception {
        // 获取输出流
        OutputStream os = response.getOutputStream();

        //创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //居中样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        /*------需要导出的数据-----*/
        List<ProductReport> data = getProductReportData(combType, date,cycel,fdorgan);
        List<ProductReport> productReportList = ProductReport.treeTransTo(data,new ArrayList<ProductReport>(),4);

        /*---------写入文件---------*/

        String filename = "产品表-"+date;
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 32);
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //单位
        String amountUnit = "万元";
        if (ReportConstant.UNIT_2.equals(unit)) {
            amountUnit = "亿元";
        }
        POIExportUtil.setCell(sheet, 1, 0, "单位:"+amountUnit , cellStyle);

        //数据
        setExcelTop(sheet, 2, 0, cellStyle);
        int row= 3;
        int column = 0;
        for (ProductReport report : productReportList) {
            POIExportUtil.setCell(sheet, row, column++, report.getName() , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getBalance(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getBalanceRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getBalanceRatio()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getBalanceRatioCompareDateBegin()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getCurrent_increase_req_line(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getCurrent_increase_req_organ(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getCurrent_increase_req_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getPlanAmount(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getDay_increase_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getCurrent_increase_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getCurrent_increase_num_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getCurrent_over_plan_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getCurrent_increase_num_yoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getCurrent_increase_num_proportion()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getCurrent_plan_completion_rate()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getCurrent_plan_completion_rate_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getCurrent_plan_completion_rate_yoy()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getCurrent_increase_num_growth_rate()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getCurrent_increase_num_growth_rate_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRatio(report.getCurrent_increase_num_growth_rate_yoy()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getOcc(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getOccRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getOccYoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getExpireEstimate(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getExpireEstimateRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getExpireEstimateYoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getExpire(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getExpireRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getExpireYoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getExpireEstimateLeft(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportRank(report.getExpireEstimateLeftRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, getReportAmount(report.getExpireEstimateLeftYoy(),unit) , cellStyle);
            row++;
            column = 0;
        }

        //设置列宽
        for (int i = 0; i <33 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet,1,3);

        //文件名
        filename = filename+ ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         清空response
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }

    /*设置表头*/
    private void setExcelTop(HSSFSheet sheet, int row, int column, HSSFCellStyle cellStyle) throws Exception {
        POIExportUtil.setCell(sheet, row, column++, "贷种" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "余额" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "余额占比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "余额占比较期初" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增需求(条线)" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增需求(分行)" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期计划" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "当日净增" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期超计划" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增占比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期计划完成率" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期计划完成率同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期增速" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期增速同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期发生量" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期预计到期量" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期已到期量" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期剩余预计到期量" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "同比" , cellStyle);
    }


    //下载时处理排名
    public static String getReportRank(Integer integer) {
        if (integer == null || integer == 0) {
            return "-";
        }
        return integer + "";
    }
    //下载时处理百分比
    public static String getReportRatio(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return "-";
        } else if (bigDecimal.compareTo(BigDecimal.ZERO)==0){
            return "0";
        }
        return BigDecimalUtil.formatNum(bigDecimal) + "%";
    }
    /**
     * 下载时处理单位进行换算
     * @param amount 金额（单位：万元）
     * @param unit 报表单位：1-万元 2-亿元
     * @return
     */
    public static String getReportAmount(BigDecimal amount,String unit) {
        BigDecimal big = BigDecimal.ONE;
        if (ReportConstant.UNIT_2.equals(unit)) {
            big = new BigDecimal("10000");
        }
        amount = BigDecimalUtil.divide(amount, big);
        return BigDecimalUtil.formatNum(amount).toPlainString();
    }


    //计算同比
    private void getOrganYoy(List<ProductReport> productReportList, String cycel, List<ProductReport> productReportListCopy, String date, String combType, FdOrgan fdorgan) throws Exception {
        String beginDate = getLastYearDay(date);
        //当天基础数据List
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getOrganCombDatalist(beginDate, combType, fdorgan);
        // 发生量 ...
        getProductOccAndExpire(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // 净增
        getTodayIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList);
        //计划完成率同比
        // 本期计划
        getProductPlanAmount(productReportListCopy, combType, beginDate, cycel, fdorgan);
        // 本期净增
        getCurrentIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // 本期计划完成率
        getCurrentPlanCompletionRate(productReportListCopy);
        //本期增速
        //余额 余额占比
        getProductBalance(productReportListCopy, tbRptBaseinfoLoankindList);
        // 本期增速 当前净增 除以 期初余额
        getCurrentIncreaseNumGrowthRate(productReportListCopy, tbRptBaseinfoLoankindList, cycel);


        // 本期发生量同比
        BigDecimal occYoy = BigDecimal.ZERO;
        // 本期预计到期量同比
        BigDecimal expireEstimateYoy = BigDecimal.ZERO;
        // 本期已到期量同比
        BigDecimal expireYoy = BigDecimal.ZERO;
        // 本期剩余预计到期量同比
        BigDecimal expireEstimateLeftYoy = BigDecimal.ZERO;
        //本期净增同比
        BigDecimal current_increase_num_yoy = BigDecimal.ZERO;
        // 计划完成率同比
        BigDecimal current_plan_completion_rate_yoy = BigDecimal.ZERO;
        // 本期增速同比
        BigDecimal current_increase_num_growth_rate_yoy = BigDecimal.ZERO;


        for (ProductReport productReport : productReportList) {
            for (ProductReport report : productReportListCopy) {
                if (productReport.getId().equals(report.getId())) {
                    //同比  当前值-去年值
                    occYoy = BigDecimalUtil.subtract(productReport.getOcc(), report.getOcc());
                    expireEstimateYoy = BigDecimalUtil.subtract(productReport.getExpireEstimate(), report.getExpireEstimate());
                    expireYoy = BigDecimalUtil.subtract(productReport.getExpire(), report.getExpire());
                    expireEstimateLeftYoy = BigDecimalUtil.subtract(productReport.getExpireEstimateLeft(), report.getExpireEstimateLeft());
                    current_increase_num_yoy = BigDecimalUtil.subtract(productReport.getDay_increase_num(), report.getDay_increase_num());
                    current_plan_completion_rate_yoy = BigDecimalUtil.subtract(productReport.getCurrent_plan_completion_rate(), report.getCurrent_plan_completion_rate());
                    current_increase_num_growth_rate_yoy = BigDecimalUtil.subtract(productReport.getCurrent_increase_num_growth_rate(), report.getCurrent_increase_num_growth_rate());


                    productReport.setOccYoy(occYoy);
                    productReport.setExpireEstimateYoy(expireEstimateYoy);
                    productReport.setExpireYoy(expireYoy);
                    productReport.setExpireEstimateLeftYoy(expireEstimateLeftYoy);
                    productReport.setCurrent_increase_num_yoy(current_increase_num_yoy);
                    productReport.setCurrent_plan_completion_rate_yoy(current_plan_completion_rate_yoy);
                    productReport.setCurrent_increase_num_growth_rate_yoy(current_increase_num_growth_rate_yoy);
                    break;
                }
            }
        }
    }


    private List<TbRptBaseinfoLoankind> getOrganCombDatalist(String date, String combType, FdOrgan fdorgan) throws Exception {

        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = new ArrayList<>();


        if (fdorgan.getThiscode().equals(WebContextUtil.getSessionOrgan().getThiscode())) {
            //如果机构和当前登录即构相等，那么就是求本部数据
            TbRptBaseinfoLoankind tbRptBaseinfoLoankindParam = new TbRptBaseinfoLoankind();
            tbRptBaseinfoLoankindParam.setRptDate(date);
            tbRptBaseinfoLoankindParam.setRptOrgan(fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByAttr(tbRptBaseinfoLoankindParam);

        } else {
            //当天基础数据List
            HashMap<String, Object> param = new HashMap<>();
            String[] combListParam = null;
            String[] excludeCombListParam = null;
            // 1 全部；2少拆放；4 实体
            if (ReportConstant.combType_1.equals(combType)) {

            } else if (ReportConstant.combType_2.equals(combType)) {
                String combParam = ReportConstant.COMB_CODE_CFFY2;
                excludeCombListParam = combParam.split(",");
            } else if (ReportConstant.combType_4.equals(combType)) {
                String combParam = ReportConstant.COMB_CODE_QTSTDK1;
                combListParam = combParam.split(",");
            } else {

            }
            param.put("rptDate", date);
            param.put("combList", combListParam);
            param.put("excludeCombList", excludeCombListParam);
            param.put("rptOrgan", fdorgan.getThiscode());

            //总行机构登录和一分机构登录 分开查询
            if (Constant.ORGAN_LEVEL_0.equals(WebContextUtil.getSessionOrgan().getOrganlevel())) {
                tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByDateAndOrgan(param);
            } else if (Constant.ORGAN_LEVEL_1.equals(WebContextUtil.getSessionOrgan().getOrganlevel())) {
                tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectOrganDataByDateAndOrgan(param);
            }

        }

        return tbRptBaseinfoLoankindList;

    }


    private BigDecimal getSafeCount(BigDecimal b1) {
        if (b1 == null) {
            return BigDecimal.ZERO;
        }
        return b1;
    }

    private BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }
}
