package com.boco.RE.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.RE.service.IReportService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.FdReportOrgan;
import com.boco.SYS.entity.TbKeyReportOrgan;
import com.boco.SYS.entity.TbRptBaseinfoLoankind;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.boco.RE.service.impl.ReportService.getTimePeriods;

@Service
public class ReportOrganServiceImpl implements com.boco.RE.service.ReportOrganService {
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
    private IReportService reportService;
    @Autowired
    private FdReportOrganMapper fdReportOrganMapper;
    @Autowired
    private TbKeyReportOrganMapper tbKeyReportOrganMapper;


    //三级一级贷种map
    private static HashMap<String, String> threeToOneMap = new HashMap<>();
    //三级二级贷种map
    private static HashMap<String, String> threeToTwoMap = new HashMap<>();


    // 构建报表数据
    @Override
    public List<ProductReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception {

        List<ProductReport> resultList = new ArrayList<>();
        // 汇总维度：  1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
        if (ReportConstant.DIMENSION_KEY_ORGAN.equals(dimension)) {
            // 重点行

            /*--------获取数据list 汇总到重点机构 ----------*/
            List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfKeyOrganList = getKeyOrganDataList(date, combType);

            /*---------组装数据---------------*/
            // 构建以重点机构为基础的基础list
            List<ProductReport> keyOrganReportList = getReportKeyOrganList();
            if (keyOrganReportList == null || keyOrganReportList.size() == 0) {
                return new ArrayList<>();
            }

            //复制List数据,处理同比
            List<ProductReport> keyOrganReportListCooy = copyOrganReportList(keyOrganReportList);
            // 组装基础数据
            buildList(keyOrganReportList, tbRptBaseinfoLoankindOfKeyOrganList, combType, cycel, date);
            //余额占比
            getOrganBalanceRatio(keyOrganReportList);
            //余额占比较期初
            getKeyOrganBalanceRatioCompareBeginDate(keyOrganReportList, keyOrganReportListCooy, date, cycel, combType, tbRptBaseinfoLoankindOfKeyOrganList);
            //本期计划
            getKeyOrganPlanAmount(keyOrganReportList, fdorgan, combType, date, cycel);
            //本期条线需求
            getKeyOrganCurrentIncreaseReqLine(keyOrganReportList, date, cycel);
            //本期机构需求
            getKeyOrganCurrentIncreaseReqOrgan(keyOrganReportList, date, cycel);
            //本期超计划
            ReportService.getCurrentOverPlanNum(keyOrganReportList);
            // 本期净增占比
            getCurrentIncreaseNumProportion(keyOrganReportList);
            // 本期计划完成率
            ReportService.getCurrentPlanCompletionRate(keyOrganReportList);
            // 同比 当前值-去年值
            getKeyOrganYoy(keyOrganReportList, keyOrganReportListCooy, date, cycel, combType, fdorgan);

            //排序
            ReportService.rankProductReportList(keyOrganReportList);

            //添加求和，中位数，众数，最大值，最小值等
            addProductOtherList(keyOrganReportList, ReportConstant.KEY_ORGAN, ReportConstant.TREE_LEVEL_ONE);

            //转换为树形列表
            resultList = ProductReport.transToTree(keyOrganReportList, 8, ReportConstant.KEY_ORGAN, ReportConstant.TREE_LEVEL_ONE);


        } else {
            //查询机构数据

            /*--------获取数据list 汇总到一分机构----------*/
            List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfOrganList = getOrganDataList(date, combType, fdorgan);

            /*---------组装数据---------------*/
            // 构建以机构为基础的基础list
            List<ProductReport> organReportList = getReportOrganList(fdorgan);
            if (organReportList == null || organReportList.size() == 0) {
                return new ArrayList<>();
            }

            //复制List数据,处理同比
            List<ProductReport> organReportListCooy = copyOrganReportList(organReportList);
            // 组装基础数据
            buildList(organReportList, tbRptBaseinfoLoankindOfOrganList, combType, cycel, date);
            //余额占比
            getOrganBalanceRatio(organReportList);
            //余额占比较期初
            getOrganBalanceRatioCompareBeginDate(organReportList, organReportListCooy, date, cycel, combType, fdorgan, tbRptBaseinfoLoankindOfOrganList);
            //本期计划
            getOrganPlanAmount(organReportList, fdorgan, combType, date, cycel);
            //本期条线需求
            getKeyOrganCurrentIncreaseReqLine(organReportList, date, cycel);
            //本期机构需求
            getKeyOrganCurrentIncreaseReqOrgan(organReportList, date, cycel);

            //本期超计划
            ReportService.getCurrentOverPlanNum(organReportList);
            // 本期净增占比
            getCurrentIncreaseNumProportion(organReportList);
            // 本期计划完成率
            ReportService.getCurrentPlanCompletionRate(organReportList);
            // 同比 当前值-去年值
            getOrganYoy(organReportList, organReportListCooy, date, cycel, combType, fdorgan);

            /*--排序  本部不参与排序--*/
            rankOrganReportList(organReportList);



            /*--------获取机构对应的贷种信息-----------*/
            ArrayList<ProductReport> productReportCombList = new ArrayList<>();
            for (ProductReport productReport : organReportList) {
                FdOrgan fdOrganParam = new FdOrgan();
                fdOrganParam.setThiscode(productReport.getId());
                FdOrgan fdOrgan = fdOrganMapper.selectByAttr(fdOrganParam).get(0);
                List<ProductReport> productReportList = reportService.getProductReportDataOfOrganExcel(combType, date, cycel, fdOrgan);

                productReportCombList.addAll(productReportList);
            }


            if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
                // 分行

                //添加求和，中位数，众数，最大值，最小值等
                addProductOtherList(organReportList, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);

                //把贷种数据加入到机构List里面
                organReportList.addAll(productReportCombList);

                //转换为树形列表
                resultList = ProductReport.transToTree(organReportList, 8, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);

            } else {
                // 区域


                /*--------获取数据list 汇总到区域----------*/
                List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfAreaList = getAreaDataList(date, combType, dimension);

                /*---------组装数据---------------*/
                // 构建以区域为基础的基础list,并且改变organReportList的parentId
                List<ProductReport> areaReportList = getAreaReportList(dimension, organReportList);
                if (areaReportList == null || areaReportList.size() == 0) {
                    return new ArrayList<>();
                }

                //复制List数据,处理同比
                List<ProductReport> areaReportListCooy = copyOrganReportList(areaReportList);
                // 组装基础数据
                buildList(areaReportList, tbRptBaseinfoLoankindOfAreaList, combType, cycel, date);
                //余额占比
                getOrganBalanceRatio(areaReportList);
                //余额占比较期初
                getAreaBalanceRatioCompareBeginDate(areaReportList, areaReportListCooy, date, cycel, combType, dimension, tbRptBaseinfoLoankindOfAreaList);

                //查询区域信息
                List<FdReportOrgan> fdReportOrganList = fdReportOrganMapper.selectByAttr(new FdReportOrgan());
                //本期计划
                getAreaPlanAmount(areaReportList, fdorgan, combType, date, cycel, dimension, fdReportOrganList);
                //本期条线需求
                getAreaLineReq(areaReportList, organReportList, dimension, fdReportOrganList);
                //本期机构需求
                getAreaOrganReq(areaReportList, organReportList, dimension, fdReportOrganList);
                //本期超计划
                ReportService.getCurrentOverPlanNum(areaReportList);
                // 本期净增占比
                getCurrentIncreaseNumProportion(areaReportList);
                // 本期计划完成率
                ReportService.getCurrentPlanCompletionRate(areaReportList);
                // 同比 当前值-去年值
                getAreaYoy(areaReportList, areaReportListCooy, date, cycel, combType, fdorgan, dimension);
                //排序
                ReportService.rankProductReportList(areaReportList);

                //添加求和，中位数，众数，最大值，最小值等
                addProductOtherList(areaReportList, ReportConstant.AREA, ReportConstant.TREE_LEVEL_ONE);

                //把贷种数据加入到机构List里面
                organReportList.addAll(productReportCombList);
                //把机构List加入到区域List
                areaReportList.addAll(organReportList);

                //转换为树形列表
                resultList = ProductReport.transToTree(areaReportList, 8, ReportConstant.AREA, ReportConstant.TREE_LEVEL_ONE);
            }
        }

        return resultList;

    }

    /**
     *  报表下载
     * @param response
     * @param request
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date yyyymmdd
     * @param cycel 报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan 机构
     * @param dimension 汇总维度：1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
     * @param unit 报表单位 1-万元 2-亿元
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
        List<ProductReport> data = getReportData(combType, date, cycel, fdorgan, dimension);

        int level = 2;
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            level = 2;
        } else if (ReportConstant.DIMENSION_KEY_ORGAN.equals(dimension)) {
            level = 1;
        }
        List<ProductReport> productReportList = ProductReport.treeTransTo(data,new ArrayList<ProductReport>(),level);

        /*---------写入文件---------*/
        //表名
        String filename = "-" + date;
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            filename = "机构表" + filename;
        } else {
            filename = "区域重点行表" + filename;
        }
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
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getBalance(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getBalanceRank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBalanceRatio()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getBalanceRatioCompareDateBegin()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_increase_req_line(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_increase_req_organ(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getCurrent_increase_req_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getPlanAmount(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getDay_increase_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_increase_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getCurrent_increase_num_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_over_plan_num(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getCurrent_increase_num_yoy(),unit) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getCurrent_increase_num_proportion()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getCurrent_plan_completion_rate()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRank(report.getCurrent_plan_completion_rate_rank()) , cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportRatio(report.getCurrent_plan_completion_rate_yoy()) , cellStyle);
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

           /* String GGName = report.getName();
            String[] arr1 = {"总行本部","华北地区","东北地区","华东地区","华中地区","华南地区","西南地区","西北地区"};
            if(Arrays.asList(arr1).contains(GGName)){

            }*/

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
        POIExportUtil.setCell(sheet, row, column++, "机构" , cellStyle);
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

    //排序
    private void rankOrganReportList(List<ProductReport> organReportList) throws Exception {
        Iterator<ProductReport> it = organReportList.iterator();
        //取出本部
        ProductReport organ = new ProductReport();
        while (it.hasNext()) {
            ProductReport next = it.next();
            if (WebContextUtil.getSessionOrgan().getThiscode().equals(next.getId())) {
                organ = next;
                it.remove();
            }
        }

        ReportService.rankProductReportList(organReportList);
        //放入本部
        organReportList.add(organ);
        // 最后对机构排名
        Collections.sort(organReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //从大到小
                return o1.getCombOrder().compareTo(o2.getCombOrder());
            }
        });
    }

    /**
     * 获取本期機構需求 区域汇总
     *
     * @param areaReportList
     * @param organReportList
     * @param dimension
     * @param fdReportOrganList
     */
    private void getAreaOrganReq(List<ProductReport> areaReportList, List<ProductReport> organReportList, String dimension, List<FdReportOrgan> fdReportOrganList) {
        HashMap<String, BigDecimal> areaLineMap = new HashMap<>();
        for (ProductReport tempPr : organReportList) {
            String organCode = tempPr.getId();
            for (FdReportOrgan tempFo : fdReportOrganList) {
                if (organCode != null && organCode.equals(tempFo.getOrgancode())) {
                    if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
                        areaLineMap.put(ReportConstant.AREA + tempFo.getType1(), BigDecimalUtil.add(areaLineMap.get(ReportConstant.AREA + tempFo.getType1()), tempPr.getCurrent_increase_req_organ()));
                    } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
                        areaLineMap.put(ReportConstant.AREA + tempFo.getType2(), BigDecimalUtil.add(areaLineMap.get(ReportConstant.AREA + tempFo.getType2()), tempPr.getCurrent_increase_req_organ()));
                    } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
                        areaLineMap.put(ReportConstant.AREA + tempFo.getType3(), BigDecimalUtil.add(areaLineMap.get(ReportConstant.AREA + tempFo.getType3()), tempPr.getCurrent_increase_req_organ()));
                    }
                }
            }
        }
        for (ProductReport tempPr : areaReportList) {
            String areaId = tempPr.getId();
            tempPr.setCurrent_increase_req_organ(getSafeCount(areaLineMap.get(areaId)));
        }

    }

    /**
     * 获取本期条线需求 区域汇总
     *
     * @param areaReportList
     * @param organReportList
     * @param dimension
     * @param fdReportOrganList
     */
    private void getAreaLineReq(List<ProductReport> areaReportList, List<ProductReport> organReportList, String dimension, List<FdReportOrgan> fdReportOrganList) {
        HashMap<String, BigDecimal> areaLineMap = new HashMap<>();
        for (ProductReport tempPr : organReportList) {
            String organCode = tempPr.getId();
            for (FdReportOrgan tempFo : fdReportOrganList) {
                if (organCode != null && organCode.equals(tempFo.getOrgancode())) {
                    if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
                        areaLineMap.put(ReportConstant.AREA + tempFo.getType1(), BigDecimalUtil.add(areaLineMap.get(ReportConstant.AREA + tempFo.getType1()), tempPr.getCurrent_increase_req_line()));
                    } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
                        areaLineMap.put(ReportConstant.AREA + tempFo.getType2(), BigDecimalUtil.add(areaLineMap.get(ReportConstant.AREA + tempFo.getType2()), tempPr.getCurrent_increase_req_line()));
                    } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
                        areaLineMap.put(ReportConstant.AREA + tempFo.getType3(), BigDecimalUtil.add(areaLineMap.get(ReportConstant.AREA + tempFo.getType3()), tempPr.getCurrent_increase_req_line()));
                    }
                }
            }
        }
        for (ProductReport tempPr : areaReportList) {
            String areaId = tempPr.getId();
            tempPr.setCurrent_increase_req_line(getSafeCount(areaLineMap.get(areaId)));
        }

    }

    /**
     * 获取 本期机构需求-重点行
     *
     * @param keyOrganReportList
     * @param date
     * @param cycel
     */
    private void getKeyOrganCurrentIncreaseReqOrgan(List<ProductReport> keyOrganReportList, String date, String cycel) {
        String[] timePeriodArr = getTimePeriods(date, cycel);
        ArrayList<String> levelOtherOrganList = new ArrayList<>();
        //重点行 id 是机构号；分为一分和二分的条线需求
        for (ProductReport tempPr : keyOrganReportList) {
            String id = tempPr.getId();//一分或二分机构号
            levelOtherOrganList.add(id);
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("months", timePeriodArr);
        param.put("organList", levelOtherOrganList);
        List<Map<String, Object>> lineReqDetailList = tbReqDetailMapper.getOrganReq(param);
        HashMap<String, BigDecimal> lineReqOfKeyOrganMap = new HashMap<>();
        for (Map<String, Object> tempMap : lineReqDetailList) {
            String organCode = tempMap.get("reqd_organ").toString();
            Object reqnum = tempMap.get("reqd_reqnum");
            lineReqOfKeyOrganMap.put(organCode, BigDecimalUtil.add(lineReqOfKeyOrganMap.get(organCode), getSafeCount(reqnum)));
        }

        for (ProductReport tempPr : keyOrganReportList) {
            BigDecimal amount = getSafeCount(lineReqOfKeyOrganMap.get(tempPr.getId()));
            tempPr.setCurrent_increase_req_organ(amount);
        }
    }

    /**
     * 获取 本期条线需求-重点行
     *
     * @param keyOrganReportList
     * @param date
     * @param cycel
     */
    private void getKeyOrganCurrentIncreaseReqLine(List<ProductReport> keyOrganReportList, String date, String cycel) {

        String[] timePeriodArr = getTimePeriods(date, cycel);
        ArrayList<String> levelOtherOrganList = new ArrayList<>();
        //重点行 id 是机构号；分为一分和二分的条线需求
        for (ProductReport tempPr : keyOrganReportList) {
            String id = tempPr.getId();//一分或二分机构号
            levelOtherOrganList.add(id);
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("months", timePeriodArr);
        param.put("organList", levelOtherOrganList);

        //查询 一段时间 部分机构的 条线需求
        List<Map<String, Object>> lineReqDetailList = tbLineReqDetailMapper.getOrganLineReq(param);
        HashMap<String, BigDecimal> lineReqOfKeyOrganMap = new HashMap<>();
        for (Map<String, Object> tempMap : lineReqDetailList) {
            String organCode = tempMap.get("line_organ").toString();
            Object lineReqnum = tempMap.get("line_reqnum");
            if (null != lineReqnum && !"".equals(lineReqnum.toString().trim())) {
                String[] lineReqArr = lineReqnum.toString().split(",");
                BigDecimal totalLineReqNum = BigDecimal.ZERO;
                for (int i = 0; i < lineReqArr.length; i++) {
                    totalLineReqNum = BigDecimalUtil.add(totalLineReqNum, new BigDecimal(lineReqArr[i]));
                }
                lineReqOfKeyOrganMap.put(organCode, BigDecimalUtil.add(lineReqOfKeyOrganMap.get(organCode), totalLineReqNum));
            }
        }
        for (ProductReport tempPr : keyOrganReportList) {
            BigDecimal amount = getSafeCount(lineReqOfKeyOrganMap.get(tempPr.getId()));
            tempPr.setCurrent_increase_req_line(amount);
        }

    }

    private void getKeyOrganYoy(List<ProductReport> keyOrganReportList, List<ProductReport> keyOrganReportListCooy, String date, String cycel, String combType, FdOrgan fdorgan) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------获取数据list 汇总到区域----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfKeyOrganList = getKeyOrganDataList(beginDate, combType);

        /*----------操作机构List-------*/
        // 组装基础数据
        buildList(keyOrganReportListCooy, tbRptBaseinfoLoankindOfKeyOrganList, combType, cycel, beginDate);
        //本期计划
        getKeyOrganPlanAmount(keyOrganReportListCooy, fdorgan, combType, date, cycel);
        //本期超计划
        ReportService.getCurrentOverPlanNum(keyOrganReportListCooy);
        // 本期净增占比
        getCurrentIncreaseNumProportion(keyOrganReportListCooy);
        // 本期计划完成率
        ReportService.getCurrentPlanCompletionRate(keyOrganReportListCooy);

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

        for (ProductReport productReport : keyOrganReportList) {
            for (ProductReport report : keyOrganReportListCooy) {
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

    //重点行本期计划
    private void getKeyOrganPlanAmount(List<ProductReport> keyOrganReportList, FdOrgan fdorgan, String combType, String date, String cycel) {

        /*-----查出重点行并且对不同级别的进行区分处理---*/
        List<TbKeyReportOrgan> tbKeyReportOrganList = tbKeyReportOrganMapper.selectByAttr(new TbKeyReportOrgan());
        ArrayList<String> levelOneOrganList = new ArrayList<>();
        ArrayList<String> levelOtherOrganList = new ArrayList<>();
        for (TbKeyReportOrgan keyReportOrgan : tbKeyReportOrganList) {
            if (Constant.ORGAN_LEVEL_1.equals(keyReportOrgan.getOrganlevel())) {
                levelOneOrganList.add(keyReportOrgan.getOrgancode());
            } else {
                levelOtherOrganList.add(keyReportOrgan.getOrgancode());
            }
        }

        /*----组装数据-------*/
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
        }

        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        param.put("organ", fdorgan.getThiscode());
        param.put("organList", levelOneOrganList);
        //查询出一级分行的计划
        List<Map<String, Object>> levelOnePlanAmounList = reportCombMapper.getOrganPlan(param);

        param.put("organ", null);
        param.put("organList", levelOtherOrganList);
        //查询二级分行计划
        List<Map<String, Object>> levelOtherPlanAmounList = reportCombMapper.getOrganPlan(param);

        HashMap<String, BigDecimal> planOfKeyOrganMap = new HashMap<>();
        for (Map<String, Object> map : levelOnePlanAmounList) {
            String organCode = map.get("organ").toString();
            planOfKeyOrganMap.put(organCode, BigDecimalUtil.add(planOfKeyOrganMap.get(organCode), getSafeCount(map.get("amount"))));
        }
        for (Map<String, Object> map : levelOtherPlanAmounList) {
            String organCode = map.get("organ").toString();
            planOfKeyOrganMap.put(organCode, BigDecimalUtil.add(planOfKeyOrganMap.get(organCode), getSafeCount(map.get("amount"))));
        }

        for (ProductReport productReport : keyOrganReportList) {
            BigDecimal amount = getSafeCount(planOfKeyOrganMap.get(productReport.getId()));
            productReport.setPlanAmount(amount);
        }

    }

    //重点行余额占比较初期
    private void getKeyOrganBalanceRatioCompareBeginDate(List<ProductReport> keyOrganReportList, List<ProductReport> keyOrganReportListCooy, String date, String cycel, String combType, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfKeyOrganList) {
        buildBalanceRatioOfBeginData(keyOrganReportListCooy, tbRptBaseinfoLoankindOfKeyOrganList, cycel);
        for (ProductReport productReport : keyOrganReportList) {
            for (ProductReport copy : keyOrganReportListCooy) {
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
    private void buildBalanceRatioOfBeginData(List<ProductReport> reportListCopy, List<TbRptBaseinfoLoankind> baseInfoList, String cycel) {
        //期初总余额
        BigDecimal balanceCount = BigDecimal.ZERO;

        //余额
        for (ProductReport productReport : reportListCopy) {
            String organ = productReport.getId();
            BigDecimal balance = BigDecimal.ZERO;
            for (TbRptBaseinfoLoankind info : baseInfoList) {
                if (organ.equals(info.getRptOrgan())) {
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

    // 获取数据list 汇总到重点机构
    private List<TbRptBaseinfoLoankind> getKeyOrganDataList(String date, String combType) {
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = new ArrayList<>();
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
        }

        param.put("rptDate", date);
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        //汇总到一分的数据
        List<TbRptBaseinfoLoankind> OneOrganList = tbRptBaseinfoLoankindMapper.selectByDateAndComb(param);

        ArrayList<String> organList = new ArrayList<>();
        //如果没有二分重点行，就不查数据，所以添加空字符串
        organList.add("");
        List<TbKeyReportOrgan> tbKeyReportOrganList = tbKeyReportOrganMapper.selectByAttr(new TbKeyReportOrgan());
        for (TbKeyReportOrgan keyReportOrgan : tbKeyReportOrganList) {
            if (Constant.ORGAN_LEVEL_1.equals(keyReportOrgan.getOrganlevel())) {
                for (TbRptBaseinfoLoankind info : OneOrganList) {
                    if (keyReportOrgan.getOrgancode().equals(info.getRptOrgan())) {
                        tbRptBaseinfoLoankindList.add(info);
                        break;
                    }
                }
            } else {
                organList.add(keyReportOrgan.getOrgancode());
            }
        }

        //查询除一分外的其他重点行
        param.put("organList", organList);
        List<TbRptBaseinfoLoankind> otherKeyOrganList = tbRptBaseinfoLoankindMapper.selectOtherKeyOrgan(param);
        tbRptBaseinfoLoankindList.addAll(otherKeyOrganList);

        return tbRptBaseinfoLoankindList;
    }

    //构建以重点机构为基础的基础list
    private List<ProductReport> getReportKeyOrganList() {
        ArrayList<ProductReport> organReportList = new ArrayList<>();

        List<TbKeyReportOrgan> tbKeyReportOrganList = tbKeyReportOrganMapper.selectByAttr(new TbKeyReportOrgan());


        for (TbKeyReportOrgan keyOrgan : tbKeyReportOrganList) {
            ProductReport organReport = new ProductReport();
            organReport.setId(keyOrgan.getOrgancode());
            organReport.setParentId(ReportConstant.KEY_ORGAN);
            organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
            organReport.setCombOrder(keyOrgan.getOrganorder() == null ? Integer.MAX_VALUE : keyOrgan.getOrganorder());
            if ("1".equals(keyOrgan.getOrganlevel())) {
                organReport.setName(keyOrgan.getOrganname());
            } else {
                organReport.setName(keyOrgan.getUporganname() + "-" + keyOrgan.getOrganname());
            }
            organReportList.add(organReport);
        }
        return organReportList;
    }

    //计算区域本期计划
    private void getAreaPlanAmount(List<ProductReport> areaReportList, FdOrgan fdorgan, String combType, String date, String cycel, String dimension, List<FdReportOrgan> fdReportOrganList) {
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
        }

        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        param.put("organ", fdorgan.getThiscode());
        List<Map<String, Object>> planAmlounList = reportCombMapper.getOrganPlan(param);


        /*---------组装数据----------*/
        HashMap<String, BigDecimal> planOfAreaMap = new HashMap<>();
        for (Map<String, Object> map : planAmlounList) {
            String organCode = map.get("organ").toString();
            for (FdReportOrgan fdReportOrgan : fdReportOrganList) {
                if (organCode.equals(fdReportOrgan.getOrgancode())) {
                    if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
                        planOfAreaMap.put(ReportConstant.AREA + fdReportOrgan.getType1(), BigDecimalUtil.add(planOfAreaMap.get(ReportConstant.AREA + fdReportOrgan.getType1()), getSafeCount(map.get("amount"))));
                    } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
                        planOfAreaMap.put(ReportConstant.AREA + fdReportOrgan.getType2(), BigDecimalUtil.add(planOfAreaMap.get(ReportConstant.AREA + fdReportOrgan.getType2()), getSafeCount(map.get("amount"))));
                    } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
                        planOfAreaMap.put(ReportConstant.AREA + fdReportOrgan.getType3(), BigDecimalUtil.add(planOfAreaMap.get(ReportConstant.AREA + fdReportOrgan.getType3()), getSafeCount(map.get("amount"))));
                    }
                    break;
                }
            }
        }

        for (ProductReport report : areaReportList) {
            String area = report.getId();
            BigDecimal amount = planOfAreaMap.get(area);
            report.setPlanAmount(getSafeCount(amount));
        }
    }

    /**
     * 计算本期净增占比
     *
     * @param productReportList
     */
    private void getCurrentIncreaseNumProportion(List<ProductReport> productReportList) {
        BigDecimal totalNum = BigDecimal.ZERO;
        for (ProductReport tempPr : productReportList) {
            totalNum = BigDecimalUtil.add(totalNum, tempPr.getCurrent_increase_num());
        }
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_proportion(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), totalNum, 4).multiply(ReportConstant.RATIO_NUM));
        }

    }

    //计算同比
    private void getAreaYoy(List<ProductReport> areaReportList, List<ProductReport> areaReportListCooy, String date, String cycel, String combType, FdOrgan fdorgan, String dimension) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------获取数据list 汇总到区域----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfAreaList = getAreaDataList(beginDate, combType, dimension);

        /*----------操作机构List-------*/
        // 组装基础数据
        buildList(areaReportListCooy, tbRptBaseinfoLoankindOfAreaList, combType, cycel, beginDate);

        //查询区域信息
        List<FdReportOrgan> fdReportOrganList = fdReportOrganMapper.selectByAttr(new FdReportOrgan());
        //本期计划
        getAreaPlanAmount(areaReportListCooy, fdorgan, combType, beginDate, cycel, dimension, fdReportOrganList);
        //本期超计划
        ReportService.getCurrentOverPlanNum(areaReportListCooy);
        // 本期净增占比
        getCurrentIncreaseNumProportion(areaReportListCooy);
        // 本期计划完成率
        ReportService.getCurrentPlanCompletionRate(areaReportListCooy);

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

        for (ProductReport productReport : areaReportList) {
            for (ProductReport report : areaReportListCooy) {
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

    // 余额占比较期初
    private void getAreaBalanceRatioCompareBeginDate(List<ProductReport> areaReportList, List<ProductReport> areaReportListCooy, String date, String cycel, String combType, String dimension, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfAreaList) {

        buildBalanceRatioOfBeginData(areaReportListCooy, tbRptBaseinfoLoankindOfAreaList, cycel);
        for (ProductReport productReport : areaReportList) {
            for (ProductReport copy : areaReportListCooy) {
                if (productReport.getId().equals(copy.getId())) {
                    productReport.setBalanceRatioCompareDateBegin(BigDecimalUtil.subtract(productReport.getBalanceRatio(), copy.getBalanceRatio()));
                    break;
                }
            }
        }
    }

    // 构建以区域为基础的基础list,并且改变organReportList的parentId
    private List<ProductReport> getAreaReportList(String dimension, List<ProductReport> organReportList) {
        ArrayList<ProductReport> areaReportList = new ArrayList<>();

        List<FdReportOrgan> fdReportOrganList = fdReportOrganMapper.selectByAttr(new FdReportOrgan());
        HashMap<String, Integer> type1Map = new HashMap<>();
        HashMap<String, Integer> type2Map = new HashMap<>();
        HashMap<String, Integer> type3Map = new HashMap<>();
        for (FdReportOrgan organ : fdReportOrganList) {
            type1Map.put(ReportConstant.AREA + organ.getType1(), organ.getType1());
            type2Map.put(ReportConstant.AREA + organ.getType2(), organ.getType2());
            type3Map.put(ReportConstant.AREA + organ.getType3(), organ.getType3());
        }


        if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
            for (String key : type1Map.keySet()) {
                ProductReport organReport = new ProductReport();
                //id为机构号
                organReport.setId(key);
                //parent为上级机构号，总行的也为总行
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type1Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setCombOrder(type1Map.get(key) == 0 ? Integer.MAX_VALUE : type1Map.get(key));
                areaReportList.add(organReport);
            }
            //设置organReportList的parentId
            for (ProductReport productReport : organReportList) {
                for (FdReportOrgan fdReportOrgan : fdReportOrganList) {
                    if (productReport.getId().equals(fdReportOrgan.getOrgancode())) {
                        productReport.setParentId(ReportConstant.AREA + fdReportOrgan.getType1());
                        break;
                    }
                }
            }
        } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
            for (String key : type2Map.keySet()) {
                ProductReport organReport = new ProductReport();
                //id为机构号
                organReport.setId(key);
                //parent为上级机构号，总行的也为总行
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type2Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setCombOrder(type2Map.get(key) == 0 ? Integer.MAX_VALUE : type2Map.get(key));
                areaReportList.add(organReport);
            }
            //设置organReportList的parentId
            for (ProductReport productReport : organReportList) {
                for (FdReportOrgan fdReportOrgan : fdReportOrganList) {
                    if (productReport.getId().equals(fdReportOrgan.getOrgancode())) {
                        productReport.setParentId(ReportConstant.AREA + fdReportOrgan.getType2());
                        break;
                    }
                }
            }
        } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
            for (String key : type3Map.keySet()) {
                ProductReport organReport = new ProductReport();
                //id为机构号
                organReport.setId(key);
                //parent为上级机构号，总行的也为总行
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type3Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setCombOrder(type3Map.get(key) == 0 ? Integer.MAX_VALUE : type3Map.get(key));
                areaReportList.add(organReport);
            }
            //设置organReportList的parentId
            for (ProductReport productReport : organReportList) {
                for (FdReportOrgan fdReportOrgan : fdReportOrganList) {
                    if (productReport.getId().equals(fdReportOrgan.getOrgancode())) {
                        productReport.setParentId(ReportConstant.AREA + fdReportOrgan.getType3());
                        break;
                    }
                }
            }
        }

        return areaReportList;

    }

    //获取区域名称
    private String getAreaName(String dimension, int type) {
        if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
            if (0 == type) {
                return "其他";
            } else if (1 == type) {
                return "总行本部";
            } else if (2 == type) {
                return "华北地区";
            } else if (3 == type) {
                return "东北地区";
            } else if (4 == type) {
                return "华东地区";
            } else if (5 == type) {
                return "华中地区";
            } else if (6 == type) {
                return "华南地区";
            } else if (7 == type) {
                return "西南地区";
            } else if (8 == type) {
                return "西北地区";
            }

        } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
            if (0 == type) {
                return "其他";
            } else if (1 == type) {
                return "总行本部";
            } else if (2 == type) {
                return "长三角";
            } else if (3 == type) {
                return "珠三角";
            } else if (4 == type) {
                return "环渤海";
            } else if (5 == type) {
                return "中部地区";
            } else if (6 == type) {
                return "西部地区";
            } else if (7 == type) {
                return "东北地区";
            }

        } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
            if (0 == type) {
                return "其他";
            } else if (1 == type) {
                return "总行本部";
            } else if (2 == type) {
                return "第一组";
            } else if (3 == type) {
                return "第二组";
            } else if (4 == type) {
                return "第三组";
            } else if (5 == type) {
                return "第四组";
            }
        }
        return "";
    }

    /**
     * 查询区域基础数据List
     *
     * @param date      yyyymmdd
     * @param combType  贷种组合类型 1 全部；2少拆放；4 实体
     * @param dimension 汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
     * @return
     */
    private List<TbRptBaseinfoLoankind> getAreaDataList(String date, String combType, String dimension) {
        HashMap<String, Object> param = new HashMap<>();
        //需要查询的贷种 1级贷种
        String[] combListParam = null;
        //需要排除的贷种 1，2,3级贷种
        String[] excludeCombListParam = null;
        String type = "type1";
        if (ReportConstant.combType_1.equals(combType)) {

        } else if (ReportConstant.combType_2.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_CFFY2;
            excludeCombListParam = combParam.split(",");
        } else if (ReportConstant.combType_4.equals(combType)) {
            String combParam = ReportConstant.COMB_CODE_QTSTDK1;
            combListParam = combParam.split(",");
        }

        //设置汇总维度
        if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
            type = "type1";
        } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
            type = "type2";
        } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
            type = "type3";
        }
        param.put("rptDate", date);
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("type", type);
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectAreaData(param);
        return tbRptBaseinfoLoankindList;
    }

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

    //计算同比信息
    private void getOrganYoy(List<ProductReport> reportList, List<ProductReport> reportListCooy, String date, String cycel, String combType, FdOrgan fdorgan) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------获取数据list 汇总到一分机构----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getOrganDataList(beginDate, combType, fdorgan);

        /*----------操作机构List-------*/
        // 组装基础数据
        buildList(reportListCooy, tbRptBaseinfoLoankindList, combType, cycel, beginDate);
        //本期计划
        getOrganPlanAmount(reportListCooy, fdorgan, combType, beginDate, cycel);
        //本期超计划
        ReportService.getCurrentOverPlanNum(reportListCooy);
        // 本期净增占比
        getCurrentIncreaseNumProportion(reportListCooy);
        // 本期计划完成率
        ReportService.getCurrentPlanCompletionRate(reportListCooy);

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

        for (ProductReport productReport : reportList) {
            for (ProductReport report : reportListCooy) {
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

    // 本期计划
    private void getOrganPlanAmount(List<ProductReport> reportList, FdOrgan fdorgan, String combType, String date, String cycel) {
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
        List<Map<String, Object>> planAmlounList = reportCombMapper.getOrganPlan(param);

        /*---------组装数据----------*/
        HashMap<String, Map<String, Object>> planOfOrganMap = new HashMap<>();
        for (Map<String, Object> map : planAmlounList) {
            planOfOrganMap.put(map.get("organ").toString(), map);
        }


        for (ProductReport productReport : reportList) {
            String organ = productReport.getId();
            Map<String, Object> map = planOfOrganMap.get(organ);
            if (map != null && map.size() > 0) {
                productReport.setPlanAmount(getSafeCount(map.get("amount")));
            } else {
                productReport.setPlanAmount(BigDecimal.ZERO);
            }
        }
    }

    // 余额占比较期初
    private void getOrganBalanceRatioCompareBeginDate(List<ProductReport> reportList, List<ProductReport> reportListCopy, String date, String cycel, String combType, FdOrgan fdorgan, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfOrganList) {
        buildBalanceRatioOfBeginData(reportListCopy, tbRptBaseinfoLoankindOfOrganList, cycel);
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
     * 组装基础数据
     *
     * @param reportList                基础list
     * @param tbRptBaseinfoLoankindList 数据list
     * @param combType                  贷种组合类型 1 全部；2少拆放；4 实体
     * @param cycel                     报表周期 1-年 2-季 3-月 4-日
     * @param date                      yyyymmdd
     */
    private void buildList(List<ProductReport> reportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String combType, String cycel, String date) {
        for (ProductReport productReport : reportList) {
            for (TbRptBaseinfoLoankind info : tbRptBaseinfoLoankindList) {
                if (productReport.getId().equals(info.getRptOrgan())) {
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

    //获取机构基础数据
    private List<TbRptBaseinfoLoankind> getOrganDataList(String date, String combType, FdOrgan fdorgan) {
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
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByDateAndComb(param);
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectLevelOtherOrganDataByDateAndComb(param);
        }
        return tbRptBaseinfoLoankindList;
    }

    //复制list
    public static List<ProductReport> copyOrganReportList(List<ProductReport> reportList) {
        ArrayList<ProductReport> list = new ArrayList<>();
        for (ProductReport productReport : reportList) {
            ProductReport productReport2 = (ProductReport) BocoUtils.deepCopy(productReport);
            list.add(productReport2);
        }
        return list;
    }

    //设置余额占比
    private void getOrganBalanceRatio(List<ProductReport> reportList) {
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

    //构建以机构为基础的基础list
    private List<ProductReport> getReportOrganList(FdOrgan fdOrgan) throws Exception {
        ArrayList<ProductReport> organReportList = new ArrayList<>();
        FdOrgan fdOrganParam = new FdOrgan();
        fdOrganParam.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(fdOrganParam);
        //添加本部
        FdOrgan fdOrganCopy = (FdOrgan) BocoUtils.deepCopy(fdOrgan);
        fdOrganCopy.setOrganname(fdOrganCopy.getOrganname() + "本部");
        fdOrganList.add(fdOrganCopy);

        for (FdOrgan organ : fdOrganList) {
            ProductReport organReport = new ProductReport();
            //id为机构号
            organReport.setId(organ.getThiscode());
            //parent为上级机构号，本部就为本部机构号
            organReport.setParentId(organ.getThiscode().equals(WebContextUtil.getSessionOrgan().getThiscode()) ? organ.getThiscode() : organ.getUporgan());
            organReport.setName(organ.getOrganname());
            organReport.setLevel(ReportConstant.TREE_LEVEL_TWO);
            organReport.setCombOrder(organ.getLeveloneorder() == null ? Integer.MAX_VALUE : organ.getLeveloneorder());
            organReportList.add(organReport);
        }
        return organReportList;
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
