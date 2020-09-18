package com.boco.RE.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.RE.service.IReportService;
import com.boco.RE.service.ReportIndustryService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbRptBaseinfoIndustry;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.Constant;
import com.boco.SYS.util.POIExportUtil;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportIndustryServiceImpl implements ReportIndustryService {

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
    @Autowired
    private TbRptBaseinfoIndustryMapper tbRptBaseinfoIndustryMapper;

    /**
     * 构建报表数据
     *
     * @param date    yyyymmdd
     * @param cycel   报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan 机构
     *                **行业在数据库用字母代替，a-t代表对应的行业
     * @return
     */
    @Override
    public List<ProductReport> getReportData(String date, String cycel, FdOrgan fdorgan) {

        List<ProductReport> resultList = new ArrayList<>();

        /*--------获取数据list 汇总到行业----------*/
        List<TbRptBaseinfoIndustry> industryDataList = getIndustryDataList(date, fdorgan);
        /*---------组装数据---------------*/
        // 构建以行业为基础的基础list
        List<ProductReport> industryReportList = getIndustryList();
        if (industryReportList == null || industryReportList.size() == 0) {
            return new ArrayList<>();
        }
        //复制List数据,处理同比
        List<ProductReport> organReportListCooy = ReportOrganServiceImpl.copyOrganReportList(industryReportList);
        // 组装基础数据
        buildList(industryReportList, industryDataList, cycel, date);
        //余额占比
        getBalanceRatio(industryReportList);
        //余额占比较期初
        getBalanceRatioCompareBeginDate(industryReportList, organReportListCooy, date, cycel, fdorgan, industryDataList);
        // 本期净增占比
        getCurrentIncreaseNumProportion(industryReportList);
        // 同比 当前值-去年值
        getIndustryYoy(industryReportList, organReportListCooy, date, cycel, fdorgan);

        /*--排序*/
        ReportService.rankProductReportList(industryReportList);

        //添加求和，中位数，众数，最大值，最小值等 前五大行业合计，十大行业合计
        addProductOtherList(industryReportList, ReportConstant.INDUSTRY, ReportConstant.TREE_LEVEL_TWO);

        //转换为树形列表
        resultList = ProductReport.transToTree(industryReportList, 8, ReportConstant.INDUSTRY, ReportConstant.TREE_LEVEL_TWO);

        return resultList;
    }

    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, FdOrgan fdorgan, String unit)throws Exception {
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
        List<ProductReport> data = getReportData(date, cycel, fdorgan);
        List<ProductReport> productReportList = ProductReport.treeTransTo(data,new ArrayList<ProductReport>(),4);

        /*---------写入文件---------*/

        String filename = "行业表-"+date;
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 24);
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
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getBalance(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getBalanceRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBalanceRatio()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBalanceRatioCompareDateBegin()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getDay_increase_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_increase_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getCurrent_increase_num_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_increase_num_yoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getCurrent_increase_num_proportion()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getCurrent_increase_num_growth_rate()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getCurrent_increase_num_growth_rate_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getCurrent_increase_num_growth_rate_yoy()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getOccRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOccYoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireEstimate(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getExpireEstimateRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireEstimateYoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getExpireRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireYoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireEstimateLeft(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getExpireEstimateLeftRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireEstimateLeftYoy(),unit) , cellStyle);
            row++;
            column = 0;
        }

        //设置列宽
        for (int i = 0; i <25 ; i++) {
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
        POIExportUtil.setCell(sheet, row, column++, "行业" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "余额" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "余额占比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "余额占比较期初" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "当日净增" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "排名" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增同比" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "本期净增占比" , cellStyle);
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


    //添加求和，中位数，众数，最大值，最小值等
    private void addProductOtherList(List<ProductReport> industryReportList, String parentId, int level) {
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

        for (ProductReport report : industryReportList) {
            balance = BigDecimalUtil.add(balance, report.getBalance());
            balanceRatio = BigDecimalUtil.add(balanceRatio, report.getBalanceRatio());
            balanceRatioCompareDateBegin = BigDecimalUtil.add(balanceRatioCompareDateBegin, report.getBalanceRatioCompareDateBegin());
            day_increase_num = BigDecimalUtil.add(day_increase_num, report.getDay_increase_num());
            current_increase_num = BigDecimalUtil.add(current_increase_num, report.getCurrent_increase_num());
            current_increase_num_yoy = BigDecimalUtil.add(current_increase_num_yoy, report.getCurrent_increase_num_yoy());
            current_increase_num_proportion = BigDecimalUtil.add(current_increase_num_proportion, report.getCurrent_increase_num_proportion());
            occ = BigDecimalUtil.add(occ, report.getOcc());
            expireEstimate = BigDecimalUtil.add(expireEstimate, report.getExpireEstimate());
            expire = BigDecimalUtil.add(expire, report.getExpire());
            expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, report.getExpireEstimateLeft());
            current_increase_num_growth_rate = BigDecimalUtil.add(current_increase_num_growth_rate, report.getCurrent_increase_num_growth_rate());
            current_increase_num_growth_rate_yoy = BigDecimalUtil.add(current_increase_num_growth_rate_yoy, report.getCurrent_increase_num_growth_rate_yoy());
            occYoy = BigDecimalUtil.add(occYoy, report.getOccYoy());
            expireEstimateYoy = BigDecimalUtil.add(expireEstimateYoy, report.getExpireEstimateYoy());
            expireYoy = BigDecimalUtil.add(expireYoy, report.getExpireYoy());
            expireEstimateLeftYoy = BigDecimalUtil.add(expireEstimateLeftYoy, report.getExpireEstimateLeftYoy());
        }

        ProductReport productReportSum = new ProductReport();
        productReportSum.setId("sum");
        productReportSum.setParentId(parentId);
        productReportSum.setName("公司类贷款");
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

        /*--------前五大行业小计----*/
        balance = BigDecimal.ZERO;
        balanceRatio = BigDecimal.ZERO;
        balanceRatioCompareDateBegin = BigDecimal.ZERO;
        current_increase_req_line = BigDecimal.ZERO;
        current_increase_req_organ = BigDecimal.ZERO;
        planAmount = BigDecimal.ZERO;
        day_increase_num = BigDecimal.ZERO;
        current_increase_num = BigDecimal.ZERO;
        current_over_plan_num = BigDecimal.ZERO;
        current_increase_num_yoy = BigDecimal.ZERO;
        current_increase_num_proportion = BigDecimal.ZERO;
        current_plan_completion_rate = BigDecimal.ZERO;
        current_plan_completion_rate_yoy = BigDecimal.ZERO;
        current_increase_num_growth_rate = BigDecimal.ZERO;
        current_increase_num_growth_rate_yoy = BigDecimal.ZERO;
        occ = BigDecimal.ZERO;
        occYoy = BigDecimal.ZERO;
        expireEstimate = BigDecimal.ZERO;
        expireEstimateYoy = BigDecimal.ZERO;
        expire = BigDecimal.ZERO;
        expireYoy = BigDecimal.ZERO;
        expireEstimateLeft = BigDecimal.ZERO;
        expireEstimateLeftYoy = BigDecimal.ZERO;

        for (ProductReport report : industryReportList) {
            if ("abcdeABCDE".contains(report.getId())) {
                balance = BigDecimalUtil.add(balance, report.getBalance());
                balanceRatio = BigDecimalUtil.add(balanceRatio, report.getBalanceRatio());
                balanceRatioCompareDateBegin = BigDecimalUtil.add(balanceRatioCompareDateBegin, report.getBalanceRatioCompareDateBegin());
                day_increase_num = BigDecimalUtil.add(day_increase_num, report.getDay_increase_num());
                current_increase_num = BigDecimalUtil.add(current_increase_num, report.getCurrent_increase_num());
                current_increase_num_yoy = BigDecimalUtil.add(current_increase_num_yoy, report.getCurrent_increase_num_yoy());
                current_increase_num_proportion = BigDecimalUtil.add(current_increase_num_proportion, report.getCurrent_increase_num_proportion());
                occ = BigDecimalUtil.add(occ, report.getOcc());
                expireEstimate = BigDecimalUtil.add(expireEstimate, report.getExpireEstimate());
                expire = BigDecimalUtil.add(expire, report.getExpire());
                expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, report.getExpireEstimateLeft());
                current_increase_num_growth_rate = BigDecimalUtil.add(current_increase_num_growth_rate, report.getCurrent_increase_num_growth_rate());
                current_increase_num_growth_rate_yoy = BigDecimalUtil.add(current_increase_num_growth_rate_yoy, report.getCurrent_increase_num_growth_rate_yoy());
                occYoy = BigDecimalUtil.add(occYoy, report.getOccYoy());
                expireEstimateYoy = BigDecimalUtil.add(expireEstimateYoy, report.getExpireEstimateYoy());
                expireYoy = BigDecimalUtil.add(expireYoy, report.getExpireYoy());
                expireEstimateLeftYoy = BigDecimalUtil.add(expireEstimateLeftYoy, report.getExpireEstimateLeftYoy());
            }

        }

        ProductReport fiveSum = new ProductReport();
        fiveSum.setId("tenSum");
        fiveSum.setParentId(parentId);
        fiveSum.setName("前五大行业小计");
        fiveSum.setLevel(level);

        fiveSum.setBalance(balance);
        fiveSum.setCurrent_increase_req_line(current_increase_req_line);
        fiveSum.setCurrent_increase_req_organ(current_increase_req_organ);
        fiveSum.setPlanAmount(planAmount);
        fiveSum.setDay_increase_num(day_increase_num);
        fiveSum.setCurrent_increase_num(current_increase_num);
        fiveSum.setCurrent_over_plan_num(current_over_plan_num);
        fiveSum.setCurrent_plan_completion_rate(current_plan_completion_rate);
        fiveSum.setOcc(occ);
        fiveSum.setExpireEstimate(expireEstimate);
        fiveSum.setExpire(expire);
        fiveSum.setExpireEstimateLeft(expireEstimateLeft);
        fiveSum.setBalanceRatio(balanceRatio);
        fiveSum.setBalanceRatioCompareDateBegin(balanceRatioCompareDateBegin);
        fiveSum.setCurrent_increase_num_yoy(current_increase_num_yoy);
        fiveSum.setCurrent_increase_num_proportion(current_increase_num_proportion);
        fiveSum.setCurrent_plan_completion_rate_yoy(current_plan_completion_rate_yoy);
        fiveSum.setCurrent_increase_num_growth_rate(current_increase_num_growth_rate);
        fiveSum.setCurrent_increase_num_growth_rate_yoy(current_increase_num_growth_rate_yoy);
        fiveSum.setOccYoy(occYoy);
        fiveSum.setExpireEstimateYoy(expireEstimateYoy);
        fiveSum.setExpireYoy(expireYoy);
        fiveSum.setExpireEstimateLeftYoy(expireEstimateLeftYoy);
        fiveSum.setBalanceRank(null);
        fiveSum.setOccRank(null);
        fiveSum.setCurrent_increase_req_rank(null);
        fiveSum.setCurrent_increase_num_rank(null);
        fiveSum.setCurrent_plan_completion_rate_rank(null);
        fiveSum.setCurrent_increase_num_growth_rate_rank(null);
        fiveSum.setExpireEstimateRank(null);
        fiveSum.setExpireRank(null);
        fiveSum.setExpireEstimateLeftRank(null);


        /*---前十大行业小计-----*/
        balance = BigDecimal.ZERO;
        balanceRatio = BigDecimal.ZERO;
        balanceRatioCompareDateBegin = BigDecimal.ZERO;
        current_increase_req_line = BigDecimal.ZERO;
        current_increase_req_organ = BigDecimal.ZERO;
        planAmount = BigDecimal.ZERO;
        day_increase_num = BigDecimal.ZERO;
        current_increase_num = BigDecimal.ZERO;
        current_over_plan_num = BigDecimal.ZERO;
        current_increase_num_yoy = BigDecimal.ZERO;
        current_increase_num_proportion = BigDecimal.ZERO;
        current_plan_completion_rate = BigDecimal.ZERO;
        current_plan_completion_rate_yoy = BigDecimal.ZERO;
        current_increase_num_growth_rate = BigDecimal.ZERO;
        current_increase_num_growth_rate_yoy = BigDecimal.ZERO;
        occ = BigDecimal.ZERO;
        occYoy = BigDecimal.ZERO;
        expireEstimate = BigDecimal.ZERO;
        expireEstimateYoy = BigDecimal.ZERO;
        expire = BigDecimal.ZERO;
        expireYoy = BigDecimal.ZERO;
        expireEstimateLeft = BigDecimal.ZERO;
        expireEstimateLeftYoy = BigDecimal.ZERO;

        for (ProductReport report : industryReportList) {
            if ("abcdeABCDE-fghijFGHIJ".contains(report.getId())) {
                balance = BigDecimalUtil.add(balance, report.getBalance());
                balanceRatio = BigDecimalUtil.add(balanceRatio, report.getBalanceRatio());
                balanceRatioCompareDateBegin = BigDecimalUtil.add(balanceRatioCompareDateBegin, report.getBalanceRatioCompareDateBegin());
                day_increase_num = BigDecimalUtil.add(day_increase_num, report.getDay_increase_num());
                current_increase_num = BigDecimalUtil.add(current_increase_num, report.getCurrent_increase_num());
                current_increase_num_yoy = BigDecimalUtil.add(current_increase_num_yoy, report.getCurrent_increase_num_yoy());
                current_increase_num_proportion = BigDecimalUtil.add(current_increase_num_proportion, report.getCurrent_increase_num_proportion());
                occ = BigDecimalUtil.add(occ, report.getOcc());
                expireEstimate = BigDecimalUtil.add(expireEstimate, report.getExpireEstimate());
                expire = BigDecimalUtil.add(expire, report.getExpire());
                expireEstimateLeft = BigDecimalUtil.add(expireEstimateLeft, report.getExpireEstimateLeft());
                current_increase_num_growth_rate = BigDecimalUtil.add(current_increase_num_growth_rate, report.getCurrent_increase_num_growth_rate());
                current_increase_num_growth_rate_yoy = BigDecimalUtil.add(current_increase_num_growth_rate_yoy, report.getCurrent_increase_num_growth_rate_yoy());
                occYoy = BigDecimalUtil.add(occYoy, report.getOccYoy());
                expireEstimateYoy = BigDecimalUtil.add(expireEstimateYoy, report.getExpireEstimateYoy());
                expireYoy = BigDecimalUtil.add(expireYoy, report.getExpireYoy());
                expireEstimateLeftYoy = BigDecimalUtil.add(expireEstimateLeftYoy, report.getExpireEstimateLeftYoy());
            }

        }

        ProductReport tenSum = new ProductReport();
        tenSum.setId("tenSum");
        tenSum.setParentId(parentId);
        tenSum.setName("前十大行业小计");
        tenSum.setLevel(level);

        tenSum.setBalance(balance);
        tenSum.setCurrent_increase_req_line(current_increase_req_line);
        tenSum.setCurrent_increase_req_organ(current_increase_req_organ);
        tenSum.setPlanAmount(planAmount);
        tenSum.setDay_increase_num(day_increase_num);
        tenSum.setCurrent_increase_num(current_increase_num);
        tenSum.setCurrent_over_plan_num(current_over_plan_num);
        tenSum.setCurrent_plan_completion_rate(current_plan_completion_rate);
        tenSum.setOcc(occ);
        tenSum.setExpireEstimate(expireEstimate);
        tenSum.setExpire(expire);
        tenSum.setExpireEstimateLeft(expireEstimateLeft);
        tenSum.setBalanceRatio(balanceRatio);
        tenSum.setBalanceRatioCompareDateBegin(balanceRatioCompareDateBegin);
        tenSum.setCurrent_increase_num_yoy(current_increase_num_yoy);
        tenSum.setCurrent_increase_num_proportion(current_increase_num_proportion);
        tenSum.setCurrent_plan_completion_rate_yoy(current_plan_completion_rate_yoy);
        tenSum.setCurrent_increase_num_growth_rate(current_increase_num_growth_rate);
        tenSum.setCurrent_increase_num_growth_rate_yoy(current_increase_num_growth_rate_yoy);
        tenSum.setOccYoy(occYoy);
        tenSum.setExpireEstimateYoy(expireEstimateYoy);
        tenSum.setExpireYoy(expireYoy);
        tenSum.setExpireEstimateLeftYoy(expireEstimateLeftYoy);
        tenSum.setBalanceRank(null);
        tenSum.setOccRank(null);
        tenSum.setCurrent_increase_req_rank(null);
        tenSum.setCurrent_increase_num_rank(null);
        tenSum.setCurrent_plan_completion_rate_rank(null);
        tenSum.setCurrent_increase_num_growth_rate_rank(null);
        tenSum.setExpireEstimateRank(null);
        tenSum.setExpireRank(null);
        tenSum.setExpireEstimateLeftRank(null);


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

        industryReportList.add(0, productReportSum);
        industryReportList.add(6,fiveSum);
        industryReportList.add(12,tenSum);
        // productReportList.add(productReportAvg);
        // productReportList.add(productReportMid);
        // productReportList.add(productReportMod);
        // productReportList.add(productReportMax);
        // productReportList.add(productReportMin);
    }

    //同比 当前值-去年值
    private void getIndustryYoy(List<ProductReport> industryReportList, List<ProductReport> organReportListCooy, String date, String cycel, FdOrgan fdorgan) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------获取数据list 汇总到行业----------*/
        List<TbRptBaseinfoIndustry> industryDataList = getIndustryDataList(beginDate, fdorgan);

        // 组装基础数据
        buildList(organReportListCooy, industryDataList, cycel, beginDate);
        // 本期净增占比
        getCurrentIncreaseNumProportion(organReportListCooy);
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

        for (ProductReport productReport : industryReportList) {
            for (ProductReport report : organReportListCooy) {
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

    // 本期净增占比
    private void getCurrentIncreaseNumProportion(List<ProductReport> productReportList) {
        BigDecimal totalNum = BigDecimal.ZERO;
        for (ProductReport tempPr : productReportList) {
            totalNum = BigDecimalUtil.add(totalNum, tempPr.getCurrent_increase_num());
        }
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_proportion(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), totalNum, 4).multiply(ReportConstant.RATIO_NUM));
        }


    }

    // 余额占比较期初
    private void getBalanceRatioCompareBeginDate(List<ProductReport> reportList, List<ProductReport> reportListCopy, String date, String cycel, FdOrgan fdorgan, List<TbRptBaseinfoIndustry> industryDataList) {
        buildBalanceRatioOfBeginData(reportListCopy, industryDataList, cycel);
        for (ProductReport productReport : reportList) {
            for (ProductReport copy : reportListCopy) {
                if (productReport.getId().equals(copy.getId())) {
                    productReport.setBalanceRatioCompareDateBegin(BigDecimalUtil.subtract(productReport.getBalanceRatio(), copy.getBalanceRatio()));
                    break;
                }
            }
        }
    }

    /**
     * 设置初期余额占比
     *
     * @param reportListCopy
     * @param baseInfoList   基础数据
     * @param cycel          报表周期 1-年 2-季 3-月 4-日
     */
    private void buildBalanceRatioOfBeginData(List<ProductReport> reportListCopy, List<TbRptBaseinfoIndustry> baseInfoList, String cycel) {
        //期初总余额
        BigDecimal balanceCount = BigDecimal.ZERO;

        //余额
        for (ProductReport productReport : reportListCopy) {
            String industry = productReport.getId();
            BigDecimal balance = BigDecimal.ZERO;
            for (TbRptBaseinfoIndustry info : baseInfoList) {
                if (industry.equals(info.getIndustry())) {
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

        balanceCount = BigDecimalUtil.divide(balanceCount, ReportConstant.MONEY_NUM);
        //设置余额占比
        for (ProductReport productReport : reportListCopy) {
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    // 余额占比
    private void getBalanceRatio(List<ProductReport> reportList) {
        BigDecimal balanceCount = BigDecimal.ZERO;
        for (ProductReport productReport : reportList) {
            balanceCount = BigDecimalUtil.add(productReport.getBalance(), balanceCount);
        }
        BigDecimal balanceRatio = BigDecimal.ZERO;
        for (ProductReport productReport : reportList) {
            balanceRatio = BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM);
            productReport.setBalanceRatio(balanceRatio);
        }
    }

    // 组装基础数据
    private void buildList(List<ProductReport> reportList, List<TbRptBaseinfoIndustry> tbRptBaseinfoLoankindList, String cycel, String date) {
        for (ProductReport productReport : reportList) {
            for (TbRptBaseinfoIndustry info : tbRptBaseinfoLoankindList) {
                if (productReport.getId().equals(info.getIndustry())) {
                    //余额
                    productReport.setBalance(BigDecimalUtil.divide(info.getBalance(), ReportConstant.MONEY_NUM));
                    //当日净增
                    productReport.setDay_increase_num(BigDecimalUtil.divide(info.getDayIncrease(), ReportConstant.MONEY_NUM));

                    if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                        //本期净增
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getYearIncrease(), ReportConstant.MONEY_NUM));
                        // 本期发生量
                        productReport.setOcc(BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM));
                        // 本期预计到期量
                        productReport.setExpireEstimate(BigDecimalUtil.divide(info.getYearExpireEstimate(), ReportConstant.MONEY_NUM));
                        // 本期已到期量
                        productReport.setExpire(BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM));
                        // 本期剩余预计到期量
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(info.getYearExpireEstimateLeft(), ReportConstant.MONEY_NUM));
                        // 本期净增增速
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getYearIncrease(), info.getBalanceYear(), 4).multiply(ReportConstant.RATIO_NUM));

                    } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getSeasonIncrease(), ReportConstant.MONEY_NUM));
                        productReport.setOcc(BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimate(BigDecimalUtil.divide(info.getSeasonExpireEstimate(), ReportConstant.MONEY_NUM));
                        productReport.setExpire(BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(info.getSeasonExpireEstimateLeft(), ReportConstant.MONEY_NUM));
                        // 本期净增增速
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getSeasonIncrease(), info.getBalanceSeason(), 4).multiply(ReportConstant.RATIO_NUM));


                    } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getMonthIncrease(), ReportConstant.MONEY_NUM));
                        productReport.setOcc(BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimate(BigDecimalUtil.divide(info.getMonthExpireEstimate(), ReportConstant.MONEY_NUM));
                        productReport.setExpire(BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(info.getMonthExpireEstimateLeft(), ReportConstant.MONEY_NUM));
                        // 本期净增增速
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getMonthIncrease(), info.getBalanceMonth(), 4).multiply(ReportConstant.RATIO_NUM));


                    } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getDayIncrease(), ReportConstant.MONEY_NUM));
                        productReport.setOcc(BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimate(BigDecimalUtil.divide(BigDecimal.ZERO, ReportConstant.MONEY_NUM));
                        productReport.setExpire(BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(BigDecimal.ZERO, ReportConstant.MONEY_NUM));
                        // 本期净增增速
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getDayIncrease(), BigDecimalUtil.subtract(info.getBalance(),info.getDayExpire()), 4).multiply(ReportConstant.RATIO_NUM));

                    }
                    break;
                }
            }
        }
    }

    //获取数据list 汇总到行业
    private List<TbRptBaseinfoIndustry> getIndustryDataList(String date, FdOrgan fdorgan) {
        HashMap<String, Object> param = new HashMap<>();
        List<TbRptBaseinfoIndustry> tbRptBaseinfoLoankindList = new ArrayList<>();

        param.put("rptDate", date);
        //对于总行和一分 分别处理
        if (Constant.ORGAN_LEVEL_0.equals(fdorgan.getOrganlevel())) {
            tbRptBaseinfoLoankindList = tbRptBaseinfoIndustryMapper.selectLevelOneOrganData(param);
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoIndustryMapper.selectLevelOhterOrganData(param);
        }
        return tbRptBaseinfoLoankindList;

    }

    //构建以行业为基础的基础list
    private List<ProductReport> getIndustryList() {
        ArrayList<ProductReport> organReportList = new ArrayList<>();

        //查询行业
        List<Map<String, Object>> industryList = tbRptBaseinfoIndustryMapper.selectIndustry();
        int order = 10;
        for (Map<String, Object> map : industryList) {
            ProductReport organReport = new ProductReport();
            //id为机构号
            organReport.setId(map.get("industry").toString());
            organReport.setParentId(ReportConstant.INDUSTRY);
            organReport.setName(getIndustryName(organReport.getId()));
            organReport.setLevel(ReportConstant.TREE_LEVEL_TWO);
            organReport.setCombOrder(order);
            order += 10;
            organReportList.add(organReport);
        }

        return organReportList;

    }

    //获取行业名字
    private String getIndustryName(String industry) {
        industry = industry.toLowerCase();
        //字典表查询行业名字
        String name = DicCache.getNameByKey_(industry, "INDUSTRY_NAME");
        if (StringUtils.isEmpty(name)) {
            name = industry;
        }
        return name;
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
