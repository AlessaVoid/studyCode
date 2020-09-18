package com.boco.RE.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.entity.BankIndustryReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.RE.entity.TimeLimitReport;
import com.boco.RE.service.IReportService;
import com.boco.RE.service.ReportTimeLimitService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbRptBaseinfoLoankind;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Service
public class ReportTimeLimitServiceImpl implements ReportTimeLimitService {
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
    private IReportService reportService;
    @Autowired
    private FdReportOrganMapper fdReportOrganMapper;
    @Autowired
    private TbKeyReportOrganMapper tbKeyReportOrganMapper;


    /**
     * 构建报表数据
     *
     * @param combType  贷种组合类型 1 全部；2少拆放；4 实体
     * @param date      yyyymmdd
     * @param cycel     报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan   机构
     * @param dimension 汇总维度：1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
     * @return
     */
    @Override
    public List<TimeLimitReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception {
        List<TimeLimitReport> resultList = new ArrayList<>();

        /*--------获取数据list 汇总到机构----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfOrganList = getTimeLimitOrganDataList(date, combType, fdorgan);
        /*---------组装数据---------------*/
        // 构建以机构为基础的基础list
        List<TimeLimitReport> organReportList = getReportTimeLimitOrganList(fdorgan);
        if (organReportList == null || organReportList.size() == 0) {
            return new ArrayList<>();
        }

        // 组装基础数据
        buildTimeLimitOrganList(organReportList, tbRptBaseinfoLoankindOfOrganList, combType, cycel, date);
        /*--排序  本部不参与排序--*/
        rankTimeLimitOrganList(organReportList);

        // 汇总维度：  1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            // 分行

            //添加求和，中位数，众数，最大值，最小值等
            addProductOtherList(organReportList, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);
            //转换为树形列表
            resultList = TimeLimitReport.transToTree(organReportList, 8, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);
        }

        return resultList;
    }


    /**
     * 期限表下载
     *
     * @param response
     * @param request
     * @param combType  贷种组合类型 1 全部；2少拆放；4 实体
     * @param date      yyyymmdd
     * @param cycel     报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan   机构
     * @param dimension 汇总维度：1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
     * @param unit      报表单位 1-万元 2-亿元
     */
    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String dimension, String unit) throws Exception {
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
        List<TimeLimitReport> data = getReportData(combType, date, cycel, fdorgan, dimension);

        int level = 5;
        List<TimeLimitReport> reportList = TimeLimitReport.treeTransTo(data, new ArrayList<TimeLimitReport>(), level);

        /*---------写入文件---------*/
        //表名
        String filename = "期限表-" + date;

        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 24);
        POIExportUtil.setCell(sheet, 0, 0, filename, cellStyle);
        //单位
        String amountUnit = "万元";
        if (ReportConstant.UNIT_2.equals(unit)) {
            amountUnit = "亿元";
        }
        POIExportUtil.setCell(sheet, 1, 0, "单位:" + amountUnit, cellStyle);

        //数据
        setExcelTop(sheet, 2, 0, cellStyle);
        int row = 4;
        int column = 0;
        for (TimeLimitReport report : reportList) {
            POIExportUtil.setCell(sheet, row, column++, report.getName(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getMonth1Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getMonth1Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getMonth13Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getMonth13Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getMonth36Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getMonth36Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getMonth612Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getMonth612Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getMonthBalanceTotal(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getMonthCountTotal(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getYear12Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getYear12Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getYear23Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getYear23Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getYear35Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getYear35Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getYear510Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getYear510Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getYear10Balance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getYear10Count(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getYearBalanceTotal(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getYearCountTotal(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getBalanceTotal(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, report.getCountTotal(), cellStyle);
            row++;
            column = 0;
        }

        //设置列宽
        for (int i = 0; i < 25; i++) {
            POIExportUtil.setCellWidth(sheet, i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet, 1, 5);

        //文件名
        filename = filename + ".xls";
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

        POIExportUtil.CellRangeAddress(sheet, row, row + 1, column, column);
        POIExportUtil.setCell(sheet, row, column++, "机构", cellStyle);
        /*短期*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 9);
        POIExportUtil.setCell(sheet, row, column, "短期", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "一个月以内", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "1-3月", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "3-6月", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "6-12月", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "短期合计", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);

        /*中长期*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 11);
        POIExportUtil.setCell(sheet, row, column, "中长期", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "1-2年", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "2-3年", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "3-5年", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "5-10年", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "10年以上", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "中长期合计", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "笔数", cellStyle);

        /*总计*/
        POIExportUtil.CellRangeAddress(sheet, row, row + 1, column, column);
        POIExportUtil.setCell(sheet, row, column++, "总计", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row, row + 1, column, column);
        POIExportUtil.setCell(sheet, row, column++, "笔数", cellStyle);

    }

    //添加求和，中位数，众数，最大值，最小值等
    private void addProductOtherList(List<TimeLimitReport> organReportList, String parentId, int level) {
        /*-----------总数-------------*/
        BigDecimal month1Balance = BigDecimal.ZERO;
        Integer month1Count = 0;
        BigDecimal month13Balance = BigDecimal.ZERO;
        Integer month13Count = 0;
        BigDecimal month36Balance = BigDecimal.ZERO;
        Integer month36Count = 0;
        BigDecimal month612Balance = BigDecimal.ZERO;
        Integer month612Count = 0;
        BigDecimal monthBalanceTotal = BigDecimal.ZERO;
        Integer monthCountTotal = 0;
        BigDecimal year12Balance = BigDecimal.ZERO;
        Integer year12Count = 0;
        BigDecimal year23Balance = BigDecimal.ZERO;
        Integer year23Count = 0;
        BigDecimal year35Balance = BigDecimal.ZERO;
        Integer year35Count = 0;
        BigDecimal year510Balance = BigDecimal.ZERO;
        Integer year510Count = 0;
        BigDecimal year10Balance = BigDecimal.ZERO;
        Integer year10Count = 0;
        BigDecimal yearBalanceTotal = BigDecimal.ZERO;
        Integer yearCountTotal = 0;
        BigDecimal balanceTotal = BigDecimal.ZERO;
        Integer countTotal = 0;


        for (TimeLimitReport report : organReportList) {
            month1Balance = BigDecimalUtil.add(month1Balance, report.getMonth1Balance());
            month13Balance = BigDecimalUtil.add(month13Balance, report.getMonth13Balance());
            month36Balance = BigDecimalUtil.add(month36Balance, report.getMonth36Balance());
            month612Balance = BigDecimalUtil.add(month612Balance, report.getMonth612Balance());
            monthBalanceTotal = BigDecimalUtil.add(monthBalanceTotal, report.getMonthBalanceTotal());
            year12Balance = BigDecimalUtil.add(year12Balance, report.getYear12Balance());
            year23Balance = BigDecimalUtil.add(year23Balance, report.getYear23Balance());
            year35Balance = BigDecimalUtil.add(year35Balance, report.getYear35Balance());
            year510Balance = BigDecimalUtil.add(year510Balance, report.getYear510Balance());
            year10Balance = BigDecimalUtil.add(year10Balance, report.getYear10Balance());
            yearBalanceTotal = BigDecimalUtil.add(yearBalanceTotal, report.getYearBalanceTotal());
            balanceTotal = BigDecimalUtil.add(balanceTotal, report.getBalanceTotal());

            month1Count = month1Count + report.getMonth1Count();
            month13Count = month13Count + report.getMonth13Count();
            month36Count = month36Count + report.getMonth36Count();
            month612Count = month612Count + report.getMonth612Count();
            monthCountTotal = monthCountTotal + report.getMonthCountTotal();
            year12Count = year12Count + report.getYear12Count();
            year23Count = year23Count + report.getYear23Count();
            year35Count = year35Count + report.getYear35Count();
            year510Count = year510Count + report.getYear510Count();
            year10Count = year10Count + report.getYear10Count();
            yearCountTotal = yearCountTotal + report.getYearCountTotal();
            countTotal = countTotal + report.getCountTotal();
        }

        TimeLimitReport productReportSum = new TimeLimitReport();
        productReportSum.setId("sum");
        productReportSum.setParentId(parentId);
        productReportSum.setName("合计");
        productReportSum.setLevel(level);

        productReportSum.setMonth1Balance(month1Balance);
        productReportSum.setMonth13Balance(month13Balance);
        productReportSum.setMonth36Balance(month36Balance);
        productReportSum.setMonth612Balance(month612Balance);
        productReportSum.setMonthBalanceTotal(monthBalanceTotal);
        productReportSum.setYear12Balance(year12Balance);
        productReportSum.setYear23Balance(year23Balance);
        productReportSum.setYear35Balance(year35Balance);
        productReportSum.setYear510Balance(year510Balance);
        productReportSum.setYear10Balance(year10Balance);
        productReportSum.setYearBalanceTotal(yearBalanceTotal);
        productReportSum.setBalanceTotal(balanceTotal);
        productReportSum.setMonth1Count(month1Count);
        productReportSum.setMonth13Count(month13Count);
        productReportSum.setMonth36Count(month36Count);
        productReportSum.setMonth612Count(month612Count);
        productReportSum.setMonthCountTotal(monthCountTotal);
        productReportSum.setYear12Count(year12Count);
        productReportSum.setYear23Count(year23Count);
        productReportSum.setYear35Count(year35Count);
        productReportSum.setYear510Count(year510Count);
        productReportSum.setYear10Count(year10Count);
        productReportSum.setYearCountTotal(yearCountTotal);
        productReportSum.setCountTotal(countTotal);


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

        organReportList.add(0, productReportSum);
        // productReportList.add(productReportAvg);
        // productReportList.add(productReportMid);
        // productReportList.add(productReportMod);
        // productReportList.add(productReportMax);
        // productReportList.add(productReportMin);

    }


    //排序  本部不参与排序
    private void rankTimeLimitOrganList(List<TimeLimitReport> organReportList) {
        Collections.sort(organReportList, new Comparator<TimeLimitReport>() {
            @Override
            public int compare(TimeLimitReport o1, TimeLimitReport o2) {
                //从大到小
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });
    }

    //组装基础数据
    private void buildTimeLimitOrganList(List<TimeLimitReport> organReportList, List<TbRptBaseinfoLoankind> organDataList, String combType, String cycel, String date) {
        //根据机构号匹配
        for (TimeLimitReport report : organReportList) {
            String organ = report.getId();
            for (TbRptBaseinfoLoankind info : organDataList) {
                if (organ.equals(info.getRptOrgan())) {
                    report.setMonth1Balance(BigDecimalUtil.add(report.getMonth1Balance(), BigDecimalUtil.divide(info.getMonth1Balance(), ReportConstant.MONEY_NUM)));
                    report.setMonth1Count(report.getMonth1Count() + info.getMonth1Count());
                    report.setMonth13Balance(BigDecimalUtil.add(report.getMonth13Balance(), BigDecimalUtil.divide(info.getMonth13Balance(), ReportConstant.MONEY_NUM)));
                    report.setMonth13Count(report.getMonth13Count() + info.getMonth13Count());
                    report.setMonth36Balance(BigDecimalUtil.add(report.getMonth36Balance(), BigDecimalUtil.divide(info.getMonth36Balance(), ReportConstant.MONEY_NUM)));
                    report.setMonth36Count(report.getMonth36Count() + info.getMonth36Count());
                    report.setMonth612Balance(BigDecimalUtil.add(report.getMonth612Balance(), BigDecimalUtil.divide(info.getMonth612Balance(), ReportConstant.MONEY_NUM)));
                    report.setMonth612Count(report.getMonth612Count() + info.getMonth612Count());

                    report.setYear12Balance(BigDecimalUtil.add(report.getYear12Balance(), BigDecimalUtil.divide(info.getYear12Balance(), ReportConstant.MONEY_NUM)));
                    report.setYear12Count(report.getYear12Count() + info.getYear12Count());
                    report.setYear23Balance(BigDecimalUtil.add(report.getYear23Balance(), BigDecimalUtil.divide(info.getYear23Balance(), ReportConstant.MONEY_NUM)));
                    report.setYear23Count(report.getYear23Count() + info.getYear23Count());
                    report.setYear35Balance(BigDecimalUtil.add(report.getYear35Balance(), BigDecimalUtil.divide(info.getYear35Balance(), ReportConstant.MONEY_NUM)));
                    report.setYear35Count(report.getYear35Count() + info.getYear35Count());
                    report.setYear510Balance(BigDecimalUtil.add(report.getYear510Balance(), BigDecimalUtil.divide(info.getYear510Balance(), ReportConstant.MONEY_NUM)));
                    report.setYear510Count(report.getYear510Count() + info.getYear510Count());
                    report.setYear10Balance(BigDecimalUtil.add(report.getYear10Balance(), BigDecimalUtil.divide(info.getYear10Balance(), ReportConstant.MONEY_NUM)));
                    report.setYear10Count(report.getYear10Count() + info.getYear10Count());

                }
            }
        }

        //计算合计数
        for (TimeLimitReport report : organReportList) {
            //短期合计
            BigDecimal monthBalanceTotal = report.getMonth1Balance()
                    .add(report.getMonth13Balance())
                    .add(report.getMonth36Balance())
                    .add(report.getMonth612Balance());
            Integer monthCountTotal = report.getMonth1Count()
                    + report.getMonth13Count()
                    + report.getMonth36Count()
                    + report.getMonth612Count();
            // 中长期合计
            BigDecimal yearBalanceTotal = report.getYear12Balance()
                    .add(report.getYear23Balance())
                    .add(report.getYear35Balance())
                    .add(report.getYear510Balance())
                    .add(report.getYear10Balance());
            Integer yearCountTotal = report.getYear12Count()
                    + report.getYear23Count()
                    + report.getYear35Count()
                    + report.getYear510Count()
                    + report.getYear10Count();
            //总计
            BigDecimal balanceTotal = monthBalanceTotal.add(yearBalanceTotal);
            Integer countTotal = monthCountTotal + yearCountTotal;

            report.setMonthBalanceTotal(monthBalanceTotal);
            report.setMonthCountTotal(monthCountTotal);
            report.setYearBalanceTotal(yearBalanceTotal);
            report.setYearCountTotal(yearCountTotal);
            report.setBalanceTotal(balanceTotal);
            report.setCountTotal(countTotal);
        }
    }

    //获取数据list 汇总到机构
    private List<TbRptBaseinfoLoankind> getTimeLimitOrganDataList(String date, String combType, FdOrgan fdorgan) {
        HashMap<String, Object> param = new HashMap<>();
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = new ArrayList<>();
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
        //对于总行和一分 分别处理
        if (Constant.ORGAN_LEVEL_0.equals(fdorgan.getOrganlevel())) {
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectTimeLimitLevelOneOrgan(param);
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectTimeLimitLevelOtherOrgan(param);
        }
        return tbRptBaseinfoLoankindList;
    }

    //构建以机构为基础的基础list
    private List<TimeLimitReport> getReportTimeLimitOrganList(FdOrgan fdOrgan) throws Exception {
        ArrayList<TimeLimitReport> organReportList = new ArrayList<>();
        FdOrgan fdOrganParam = new FdOrgan();
        fdOrganParam.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(fdOrganParam);
        //添加本部
        FdOrgan fdOrganCopy = (FdOrgan) BocoUtils.deepCopy(fdOrgan);
        fdOrganCopy.setOrganname(fdOrganCopy.getOrganname() + "本部");
        fdOrganList.add(fdOrganCopy);

        for (FdOrgan organ : fdOrganList) {
            TimeLimitReport organReport = new TimeLimitReport();
            //id为机构号
            organReport.setId(organ.getThiscode());
            //parent为上级机构号，本部就为本部机构号
            organReport.setParentId(organ.getThiscode().equals(WebContextUtil.getSessionOrgan().getThiscode()) ? organ.getThiscode() : organ.getUporgan());
            organReport.setName(organ.getOrganname());
            organReport.setLevel(ReportConstant.TREE_LEVEL_TWO);
            organReport.setOrder(organ.getLeveloneorder() == null ? Integer.MAX_VALUE : organ.getLeveloneorder());
            organReportList.add(organReport);
        }
        return organReportList;

    }
}
