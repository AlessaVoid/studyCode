package com.boco.RE.service.impl;

import com.boco.RE.entity.BankIndustryReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.RE.service.ReportAreaIndustryService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbBankRmbBusi;
import com.boco.SYS.entity.TbPsbcRmbBusi;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.TbBankInfoMapper;
import com.boco.SYS.mapper.TbBankRmbBusiMapper;
import com.boco.SYS.mapper.TbPsbcRmbBusiMapper;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Constant;
import com.boco.SYS.util.POIExportUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportAreaIndustryServiceImpl implements ReportAreaIndustryService {

    @Autowired
    private TbBankInfoMapper tbBankInfoMapper;
    @Autowired
    private TbPsbcRmbBusiMapper tbPsbcRmbBusiMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;


    private static SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf_yyyy_MM = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat sdf_yyyy = new SimpleDateFormat("yyyy");


    /**
     * 查询报表数据
     *
     * @param date  日期 yyyymm
     * @param cycel 周期
     * @return
     */
    @Override
    public List<BankIndustryReport> getReportData(String date, String cycel) {


        List<BankIndustryReport> resultList = new ArrayList<>();

        /*--------获取数据list 汇总到机构----------*/
        List<TbPsbcRmbBusi> tbBankRmbBusiList = getBankIndustryDataList(date);
        //获取上个周期数据
        String cycelDate = getCycelDate(date, cycel);
        List<TbPsbcRmbBusi> tbBankRmbBusiLastcycelList = getBankIndustryDataList(cycelDate);
        /*---------组装数据---------------*/
        // 构建基础的基础list
        List<BankIndustryReport> bankIndustryList = getReportBankIndustryList();
        if (bankIndustryList == null || bankIndustryList.size() == 0) {
            return new ArrayList<>();
        }

        // 组装数据
        builBankIndustryDataList(bankIndustryList, tbBankRmbBusiList, tbBankRmbBusiLastcycelList);
        /*--排序--*/
        rankBankIndustryList(bankIndustryList);


        //转换为树形列表
        resultList = BankIndustryReport.transToTree(bankIndustryList, 8, ReportConstant.AREAINDUSTRY, ReportConstant.TREE_LEVEL_TWO);


        return resultList;

    }


    /**
     * 地区表下载
     * @param response
     * @param request
     * @param date  日期 yyyyMM
     * @param cycel 周期
     * @param unit 单位 1-万元 2-亿元
     */
    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, String unit) throws Exception {
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
        List<BankIndustryReport> data = getReportData(date, cycel);

        int level = 5;
        List<BankIndustryReport> reportList = BankIndustryReport.treeTransTo(data, new ArrayList<BankIndustryReport>(), level);

        /*---------写入文件---------*/
        //表名

       /* String filename = "地区表-" + date + "(" + cycel + ")";*/
        String filename = getFileName(date,cycel);

        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 81);
        POIExportUtil.setCell(sheet, 0, 0, filename, cellStyle);
        //单位
        String amountUnit = "万元";
        if (ReportConstant.UNIT_2.equals(unit)) {
            amountUnit = "亿元";
        }
        POIExportUtil.setCell(sheet, 1, 0, "单位:" + amountUnit, cellStyle);

        //数据
        setExcelTop(sheet, 2, 0, cellStyle);
        int row = 5;
        int column = 0;
        for (BankIndustryReport report : reportList) {
            POIExportUtil.setCell(sheet, row, column++, report.getName(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getGxdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getGxdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkIncrementGrowthRatio()), cellStyle);
           /* POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkSelfIncrementRatio()), cellStyle);*/
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGxdkAllIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getStdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getStdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getStdkAllIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getGrdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getGrdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGrdkAllIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getXfdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getXfdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getXfdkAllIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getGsdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getGsdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getGsdkAllIncrementRatio()), cellStyle);
            /*POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getBgdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getBgdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBgdkAllIncrementRatio()), cellStyle);*/
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getPjrzBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getPjrzIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getPjrzAllIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getFcdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getFcdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getFcdkAllIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getJwdkBalance(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getJwdkIncrement(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkIncrementGrowthRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkSelfBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkSelfBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkSelfIncrementRatio()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkAllBalanceRaito()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkAllBalanceRaitoCompareDateBegin()), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getJwdkAllIncrementRatio()), cellStyle);
            row++;
            column = 0;
        }

        //设置列宽
        for (int i = 0; i < 82; i++) {
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

        POIExportUtil.CellRangeAddress(sheet, row, row + 2, column, column);
        POIExportUtil.setCell(sheet, row, column++, "机构", cellStyle);
        /*各项贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "各项贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        /*POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);*/
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*实体贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "实体贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*境内个人贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "境内个人贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*消费贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "消费贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*境内公司贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "境内公司贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*并购贷款*/
        /*POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "并购贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);*/

        /*票据融资*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "票据融资", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*非存款类金融机构贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "非存款类金融机构贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);

        /*境外贷款*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 8);
        POIExportUtil.setCell(sheet, row, column, "境外贷款", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 5);
        POIExportUtil.setCell(sheet, row + 1, column, "本地区", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "周期净增", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增增速", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row + 1, row + 1, column, column + 2);
        POIExportUtil.setCell(sheet, row + 1, column, "全国", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "余额占比较期初", cellStyle);
        POIExportUtil.setCell(sheet, row + 2, column++, "净增占比", cellStyle);


    }

    //组装数据
    private void builBankIndustryDataList(List<BankIndustryReport> bankIndustryList, List<TbPsbcRmbBusi> tbBankRmbBusiList, List<TbPsbcRmbBusi> tbBankRmbBusiLastcycelList) {
        //复制List
        List<BankIndustryReport> bankIndustryListtCooy = copyBankIndustryList(bankIndustryList);

        // 组装余额 余额占比
        BuildBalance(bankIndustryList, tbBankRmbBusiList);
        BuildBalance(bankIndustryListtCooy, tbBankRmbBusiLastcycelList);

        //组装净增等
        buildIncreament(bankIndustryList, bankIndustryListtCooy);

    }

    //组装净增等
    private void buildIncreament(List<BankIndustryReport> bankIndustryList, List<BankIndustryReport> bankIndustryListtCooy) {
        // 周期净增 余额-期初余额
        for (BankIndustryReport report : bankIndustryList) {
            String id = report.getId();
            for (BankIndustryReport reportCopy : bankIndustryListtCooy) {
                if (id.equals(reportCopy.getId())) {
                    report.setGxdkIncrement(BigDecimalUtil.subtract(report.getGxdkBalance(), reportCopy.getGxdkBalance()));
                    report.setStdkIncrement(BigDecimalUtil.subtract(report.getStdkBalance(), reportCopy.getStdkBalance()));
                    report.setGrdkIncrement(BigDecimalUtil.subtract(report.getGrdkBalance(), reportCopy.getGrdkBalance()));
                    report.setXfdkIncrement(BigDecimalUtil.subtract(report.getXfdkBalance(), reportCopy.getXfdkBalance()));
                    report.setGsdkIncrement(BigDecimalUtil.subtract(report.getGsdkBalance(), reportCopy.getGsdkBalance()));
                    report.setBgdkIncrement(BigDecimalUtil.subtract(report.getBgdkBalance(), reportCopy.getBgdkBalance()));
                    report.setPjrzIncrement(BigDecimalUtil.subtract(report.getPjrzBalance(), reportCopy.getPjrzBalance()));
                    report.setFcdkIncrement(BigDecimalUtil.subtract(report.getFcdkBalance(), reportCopy.getFcdkBalance()));
                    report.setJwdkIncrement(BigDecimalUtil.subtract(report.getJwdkBalance(), reportCopy.getJwdkBalance()));
                    report.setGxdk2Increment(BigDecimalUtil.subtract(report.getGxdk2Balance(), reportCopy.getGxdk2Balance()));

                }
            }
        }

        // 净增增速 净增量/期初余额
        for (BankIndustryReport report : bankIndustryList) {
            String id = report.getId();
            for (BankIndustryReport reportCopy : bankIndustryListtCooy) {
                if (id.equals(reportCopy.getId())) {
                    report.setGxdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getGxdkIncrement(), reportCopy.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setStdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getStdkIncrement(), reportCopy.getStdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setGrdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getGrdkIncrement(), reportCopy.getGrdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setXfdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getXfdkIncrement(), reportCopy.getXfdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setGsdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getGsdkIncrement(), reportCopy.getGsdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setBgdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getBgdkIncrement(), reportCopy.getBgdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setPjrzIncrementGrowthRatio(BigDecimalUtil.divide(report.getPjrzIncrement(), reportCopy.getPjrzBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setFcdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getFcdkIncrement(), reportCopy.getFcdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setJwdkIncrementGrowthRatio(BigDecimalUtil.divide(report.getJwdkIncrement(), reportCopy.getJwdkBalance()).multiply(ReportConstant.RATIO_NUM));
                    report.setGxdk2IncrementGrowthRatio(BigDecimalUtil.divide(report.getGxdk2Increment(), reportCopy.getGxdk2Balance()).multiply(ReportConstant.RATIO_NUM));

                }
            }
        }

        // 本行净增占比 净增量/本行各项贷款净增量
        for (BankIndustryReport report : bankIndustryList) {
            report.setGxdkSelfIncrementRatio(BigDecimalUtil.divide(report.getGxdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setStdkSelfIncrementRatio(BigDecimalUtil.divide(report.getStdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setGrdkSelfIncrementRatio(BigDecimalUtil.divide(report.getGrdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setXfdkSelfIncrementRatio(BigDecimalUtil.divide(report.getXfdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setGsdkSelfIncrementRatio(BigDecimalUtil.divide(report.getGsdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setBgdkSelfIncrementRatio(BigDecimalUtil.divide(report.getBgdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setPjrzSelfIncrementRatio(BigDecimalUtil.divide(report.getPjrzIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setFcdkSelfIncrementRatio(BigDecimalUtil.divide(report.getFcdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setJwdkSelfIncrementRatio(BigDecimalUtil.divide(report.getJwdkIncrement(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));
            report.setGxdk2SelfIncrementRatio(BigDecimalUtil.divide(report.getGxdk2Increment(), report.getGxdkIncrement()).multiply(ReportConstant.RATIO_NUM));

        }

        // 全国净增占比 净增量/全国各项贷款净增量
        BigDecimal allIncrement = BigDecimal.ZERO;
        for (BankIndustryReport report : bankIndustryList) {
            if ("1".equals(report.getId())) {
                allIncrement = report.getGxdkIncrement();
                break;
            }
        }
        for (BankIndustryReport report : bankIndustryList) {

            report.setGxdkAllIncrementRatio(BigDecimalUtil.divide(report.getGxdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setStdkAllIncrementRatio(BigDecimalUtil.divide(report.getStdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setGrdkAllIncrementRatio(BigDecimalUtil.divide(report.getGrdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setXfdkAllIncrementRatio(BigDecimalUtil.divide(report.getXfdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setGsdkAllIncrementRatio(BigDecimalUtil.divide(report.getGsdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setBgdkAllIncrementRatio(BigDecimalUtil.divide(report.getBgdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setPjrzAllIncrementRatio(BigDecimalUtil.divide(report.getPjrzIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setFcdkAllIncrementRatio(BigDecimalUtil.divide(report.getFcdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setJwdkAllIncrementRatio(BigDecimalUtil.divide(report.getJwdkIncrement(), allIncrement).multiply(ReportConstant.RATIO_NUM));
            report.setGxdk2AllIncrementRatio(BigDecimalUtil.divide(report.getGxdk2Increment(), allIncrement).multiply(ReportConstant.RATIO_NUM));


        }

        // 本行余额占比较期初 本行余额占比-期初本行余额占比
        for (BankIndustryReport report : bankIndustryList) {
            String id = report.getId();
            for (BankIndustryReport reportCopy : bankIndustryListtCooy) {
                if (id.equals(reportCopy.getId())) {
                    report.setGxdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGxdkSelfBalanceRaito(), reportCopy.getGxdkSelfBalanceRaito()));
                    report.setStdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getStdkSelfBalanceRaito(), reportCopy.getStdkSelfBalanceRaito()));
                    report.setGrdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGrdkSelfBalanceRaito(), reportCopy.getGrdkSelfBalanceRaito()));
                    report.setXfdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getXfdkSelfBalanceRaito(), reportCopy.getXfdkSelfBalanceRaito()));
                    report.setGsdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGsdkSelfBalanceRaito(), reportCopy.getGsdkSelfBalanceRaito()));
                    report.setBgdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getBgdkSelfBalanceRaito(), reportCopy.getBgdkSelfBalanceRaito()));
                    report.setPjrzSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getPjrzSelfBalanceRaito(), reportCopy.getPjrzSelfBalanceRaito()));
                    report.setFcdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getFcdkSelfBalanceRaito(), reportCopy.getFcdkSelfBalanceRaito()));
                    report.setJwdkSelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getJwdkSelfBalanceRaito(), reportCopy.getJwdkSelfBalanceRaito()));
                    report.setGxdk2SelfBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGxdk2SelfBalanceRaito(), reportCopy.getGxdk2SelfBalanceRaito()));
                }
            }
        }

        // 全国余额占比较期初 全国余额占比-期初全国余额占比
        for (BankIndustryReport report : bankIndustryList) {
            String id = report.getId();
            for (BankIndustryReport reportCopy : bankIndustryListtCooy) {
                if (id.equals(reportCopy.getId())) {
                    report.setGxdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGxdkAllBalanceRaito(), reportCopy.getGxdkAllBalanceRaito()));
                    report.setStdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getStdkAllBalanceRaito(), reportCopy.getStdkAllBalanceRaito()));
                    report.setGrdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGrdkAllBalanceRaito(), reportCopy.getGrdkAllBalanceRaito()));
                    report.setXfdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getXfdkAllBalanceRaito(), reportCopy.getXfdkAllBalanceRaito()));
                    report.setGsdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGsdkAllBalanceRaito(), reportCopy.getGsdkAllBalanceRaito()));
                    report.setBgdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getBgdkAllBalanceRaito(), reportCopy.getBgdkAllBalanceRaito()));
                    report.setPjrzAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getPjrzAllBalanceRaito(), reportCopy.getPjrzAllBalanceRaito()));
                    report.setFcdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getFcdkAllBalanceRaito(), reportCopy.getFcdkAllBalanceRaito()));
                    report.setJwdkAllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getJwdkAllBalanceRaito(), reportCopy.getJwdkAllBalanceRaito()));
                    report.setGxdk2AllBalanceRaitoCompareDateBegin(BigDecimalUtil.subtract(report.getGxdk2AllBalanceRaito(), reportCopy.getGxdk2AllBalanceRaito()));
                }
            }
        }


    }


    // 组装余额 余额占比
    private void BuildBalance(List<BankIndustryReport> bankIndustryList, List<TbPsbcRmbBusi> List) {
        //余额
        for (BankIndustryReport report : bankIndustryList) {
            String id = report.getId();
            for (TbPsbcRmbBusi busi : List) {
                if (id.equals(busi.getArea())) {
                    if (ReportConstant.GXDK.equals(busi.getLoanType())) {
                        report.setGxdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_GRDK.equals(busi.getLoanType())) {
                        report.setGrdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_GRDK_GRXF.equals(busi.getLoanType())) {
                        report.setXfdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_GSDK.equals(busi.getLoanType())) {
                        report.setGsdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_GSDK_BGDK.equals(busi.getLoanType())) {
                        report.setBgdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_PJRZ.equals(busi.getLoanType())) {
                        report.setPjrzBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_FCKLJRJGDK.equals(busi.getLoanType())) {
                        report.setFcdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_JWDK.equals(busi.getLoanType())) {
                        report.setJwdkBalance(getSafeCount(busi.getBalance()));

                    } else if (ReportConstant.GXDK_GXDK.equals(busi.getLoanType())) {
                        report.setGxdkBalance(getSafeCount(busi.getBalance()));

                    }
                }
            }
        }

        //实体贷款余额  各项贷款-票据-非存-境外贷款
        for (BankIndustryReport report : bankIndustryList) {
            BigDecimal stdkBalance = BigDecimalUtil.subtract(report.getGxdkBalance(), report.getPjrzBalance());
            stdkBalance = BigDecimalUtil.subtract(stdkBalance, report.getFcdkBalance());
            stdkBalance = BigDecimalUtil.subtract(stdkBalance, report.getJwdkBalance());
            report.setStdkBalance(stdkBalance);
        }


        //本行余额占比 余额/各项贷款余额
        for (BankIndustryReport report : bankIndustryList) {
            report.setGxdkSelfBalanceRaito(BigDecimalUtil.divide(report.getGxdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setStdkSelfBalanceRaito(BigDecimalUtil.divide(report.getStdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setGrdkSelfBalanceRaito(BigDecimalUtil.divide(report.getGrdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setXfdkSelfBalanceRaito(BigDecimalUtil.divide(report.getXfdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setGsdkSelfBalanceRaito(BigDecimalUtil.divide(report.getGsdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setBgdkSelfBalanceRaito(BigDecimalUtil.divide(report.getBgdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setPjrzSelfBalanceRaito(BigDecimalUtil.divide(report.getPjrzBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setFcdkSelfBalanceRaito(BigDecimalUtil.divide(report.getFcdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setJwdkSelfBalanceRaito(BigDecimalUtil.divide(report.getJwdkBalance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));
            report.setGxdk2SelfBalanceRaito(BigDecimalUtil.divide(report.getGxdk2Balance(), report.getGxdkBalance()).multiply(ReportConstant.RATIO_NUM));

        }

        // 全国余额占比 余额/全国余额
        BigDecimal allBalance = BigDecimal.ZERO;
        for (BankIndustryReport report : bankIndustryList) {
            if ("1".equals(report.getId())) {
                allBalance = report.getGxdkBalance();
                break;
            }
        }
        for (BankIndustryReport report : bankIndustryList) {
            report.setGxdkAllBalanceRaito(BigDecimalUtil.divide(report.getGxdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setStdkAllBalanceRaito(BigDecimalUtil.divide(report.getStdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setGrdkAllBalanceRaito(BigDecimalUtil.divide(report.getGrdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setXfdkAllBalanceRaito(BigDecimalUtil.divide(report.getXfdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setGsdkAllBalanceRaito(BigDecimalUtil.divide(report.getGsdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setBgdkAllBalanceRaito(BigDecimalUtil.divide(report.getBgdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setPjrzAllBalanceRaito(BigDecimalUtil.divide(report.getPjrzBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setFcdkAllBalanceRaito(BigDecimalUtil.divide(report.getFcdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setJwdkAllBalanceRaito(BigDecimalUtil.divide(report.getJwdkBalance(), allBalance).multiply(ReportConstant.RATIO_NUM));
            report.setGxdk2AllBalanceRaito(BigDecimalUtil.divide(report.getGxdk2Balance(), allBalance).multiply(ReportConstant.RATIO_NUM));

        }

    }

    //复制list
    public static List<BankIndustryReport> copyBankIndustryList(List<BankIndustryReport> reportList) {
        ArrayList<BankIndustryReport> list = new ArrayList<>();
        for (BankIndustryReport report : reportList) {
            BankIndustryReport report2 = (BankIndustryReport) BocoUtils.deepCopy(report);
            list.add(report2);
        }
        return list;
    }

    //获取文件名
    private String getFileName(String date,String cycel){
        String cycel2 = null;
        switch(cycel){
            case "1" :
                cycel2 ="年";
                break;
            case "2" :
                cycel2 ="季";
                break;
            case "3" :
                cycel2 ="月";
                break;
        }
        String filename = "地区表-" + date + "(" + cycel2 + ")";

        return filename;
    }

    //获取上个周期月份
    private String getCycelDate(String date, String cycel) {


        String resultDate = date;
        try {
            Date month = sdf_yyyyMM.parse(date);
            Calendar c = Calendar.getInstance();
            if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                c.setTime(month);
                c.add(Calendar.YEAR, -1);
                String format = sdf_yyyy.format(c.getTime());
                resultDate = format + "12";
            } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                c.setTime(month);
                c.set(Calendar.MONTH, ((int) c.get(Calendar.MONTH) / 3) * 3);
                c.add(Calendar.MONTH, -1);
                String format = sdf_yyyyMM.format(c.getTime());
                resultDate = format;
            } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                c.setTime(month);
                c.add(Calendar.MONTH, -1);
                String format = sdf_yyyyMM.format(c.getTime());
                resultDate = format;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;

    }

    //通过月份获取当月最新数据
    private List<TbPsbcRmbBusi> getBankIndustryDataList(String date) {
        //通过月份获取当月最新的数据日期
        String dataDate = getDataDate(date);
        //查询数据
        TbPsbcRmbBusi tbPsbcParm = new TbPsbcRmbBusi();
        tbPsbcParm.setStatisticsDay(dataDate);
        List<TbPsbcRmbBusi> psbcRmbBusiList = tbPsbcRmbBusiMapper.selectByAttr(tbPsbcParm);
        return psbcRmbBusiList;
    }

    //通过月份获取当月最新的数据日期 yyyymm->yyyy-mm-dd
    private String getDataDate(String date) {
        String resultDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-01";
        try {
            //格式化当前月份
            Date month = sdf_yyyyMM.parse(date);
            String monthString = sdf_yyyy_MM.format(month);
            //查询当前月份最新数据的日期
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("date", monthString);
            List<Map<String, Object>> dateList = tbPsbcRmbBusiMapper.selectDataDate(paramMap);
            if (dateList != null && dateList.size() > 0) {
                Map<String, Object> map = dateList.get(0);
                resultDate = map.get("statistics_day").toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    //排序
    private void rankBankIndustryList(List<BankIndustryReport> organReportList) {
        Collections.sort(organReportList, new Comparator<BankIndustryReport>() {
            @Override
            public int compare(BankIndustryReport o1, BankIndustryReport o2) {
                //从大到小
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });
    }

    //构建基础的基础list
    private List<BankIndustryReport> getReportBankIndustryList() {

        FdOrgan fdOrganParam = new FdOrgan();
        fdOrganParam.setUporgan(Constant.HEAD_OFFICE_CODE);
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(fdOrganParam);
        //移除 大连，宁波，厦门，青岛，深圳
        Iterator<FdOrgan> it = fdOrganList.iterator();
        while (it.hasNext()) {
            FdOrgan organ = it.next();
            if ("21014952".equals(organ.getThiscode())) {
                it.remove();
            } else if ("33000072".equals(organ.getThiscode())) {
                it.remove();
            } else if ("35008816".equals(organ.getThiscode())) {
                it.remove();
            } else if ("37000013".equals(organ.getThiscode())) {
                it.remove();
            } else if ("44008995".equals(organ.getThiscode())) {
                it.remove();
            }

        }
        //新增总行
        FdOrgan headOrgan = fdOrganMapper.selectByPK(Constant.HEAD_OFFICE_CODE);
        headOrgan.setOrganname("总行");
        fdOrganList.add(headOrgan);

        //新增全国
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode("1");
        fdOrgan.setOrganname("全国");
        fdOrgan.setLeveloneorder(-1);
        fdOrganList.add(fdOrgan);


        ArrayList<BankIndustryReport> resultList = new ArrayList<>();
        for (FdOrgan organ : fdOrganList) {
            BankIndustryReport bankIndustryReport = new BankIndustryReport();
            bankIndustryReport.setParentId(ReportConstant.AREAINDUSTRY);
            bankIndustryReport.setId(organ.getThiscode());
            bankIndustryReport.setName(organ.getOrganname());
            bankIndustryReport.setOrder(organ.getLeveloneorder());
            bankIndustryReport.setLevel(ReportConstant.TREE_LEVEL_TWO);
            resultList.add(bankIndustryReport);
        }

        return resultList;
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
