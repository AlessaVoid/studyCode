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
     * ������������
     *
     * @param date    yyyymmdd
     * @param cycel   �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan ����
     *                **��ҵ�����ݿ�����ĸ���棬a-t�����Ӧ����ҵ
     * @return
     */
    @Override
    public List<ProductReport> getReportData(String date, String cycel, FdOrgan fdorgan) {

        List<ProductReport> resultList = new ArrayList<>();

        /*--------��ȡ����list ���ܵ���ҵ----------*/
        List<TbRptBaseinfoIndustry> industryDataList = getIndustryDataList(date, fdorgan);
        /*---------��װ����---------------*/
        // ��������ҵΪ�����Ļ���list
        List<ProductReport> industryReportList = getIndustryList();
        if (industryReportList == null || industryReportList.size() == 0) {
            return new ArrayList<>();
        }
        //����List����,����ͬ��
        List<ProductReport> organReportListCooy = ReportOrganServiceImpl.copyOrganReportList(industryReportList);
        // ��װ��������
        buildList(industryReportList, industryDataList, cycel, date);
        //���ռ��
        getBalanceRatio(industryReportList);
        //���ռ�Ƚ��ڳ�
        getBalanceRatioCompareBeginDate(industryReportList, organReportListCooy, date, cycel, fdorgan, industryDataList);
        // ���ھ���ռ��
        getCurrentIncreaseNumProportion(industryReportList);
        // ͬ�� ��ǰֵ-ȥ��ֵ
        getIndustryYoy(industryReportList, organReportListCooy, date, cycel, fdorgan);

        /*--����*/
        ReportService.rankProductReportList(industryReportList);

        //�����ͣ���λ�������������ֵ����Сֵ�� ǰ�����ҵ�ϼƣ�ʮ����ҵ�ϼ�
        addProductOtherList(industryReportList, ReportConstant.INDUSTRY, ReportConstant.TREE_LEVEL_TWO);

        //ת��Ϊ�����б�
        resultList = ProductReport.transToTree(industryReportList, 8, ReportConstant.INDUSTRY, ReportConstant.TREE_LEVEL_TWO);

        return resultList;
    }

    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, FdOrgan fdorgan, String unit)throws Exception {
        // ��ȡ�����
        OutputStream os = response.getOutputStream();

        //����������
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //������ʽ
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        /*------��Ҫ����������-----*/
        List<ProductReport> data = getReportData(date, cycel, fdorgan);
        List<ProductReport> productReportList = ProductReport.treeTransTo(data,new ArrayList<ProductReport>(),4);

        /*---------д���ļ�---------*/

        String filename = "��ҵ��-"+date;
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 24);
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //��λ
        String amountUnit = "��Ԫ";
        if (ReportConstant.UNIT_2.equals(unit)) {
            amountUnit = "��Ԫ";
        }
        POIExportUtil.setCell(sheet, 1, 0, "��λ:"+amountUnit , cellStyle);

        //����
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

        //�����п�
        for (int i = 0; i <25 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //��������
        POIExportUtil.createFreezePane(sheet,1,3);

        //�ļ���
        filename = filename+ ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie ������ϵͳ��ie�����
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         ���response
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }

    /*���ñ�ͷ*/
    private void setExcelTop(HSSFSheet sheet, int row, int column, HSSFCellStyle cellStyle) throws Exception {
        POIExportUtil.setCell(sheet, row, column++, "��ҵ" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ռ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ռ�Ƚ��ڳ�" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���վ���" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ���" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ���ͬ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ���ռ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "��������" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "��������ͬ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ڷ�����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "ͬ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����Ԥ�Ƶ�����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "ͬ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "�����ѵ�����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "ͬ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����ʣ��Ԥ�Ƶ�����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "ͬ��" , cellStyle);
    }


    //�����ͣ���λ�������������ֵ����Сֵ��
    private void addProductOtherList(List<ProductReport> industryReportList, String parentId, int level) {
        /*-----------����-------------*/
        //���
        BigDecimal balance = BigDecimal.ZERO;
        //���ռ��
        BigDecimal balanceRatio = BigDecimal.ZERO;
        //���ռ�Ƚ��ڳ�
        BigDecimal balanceRatioCompareDateBegin = BigDecimal.ZERO;
        //���ھ�������(����)
        BigDecimal current_increase_req_line = BigDecimal.ZERO;
        //���ھ�������(����)
        BigDecimal current_increase_req_organ = BigDecimal.ZERO;
        //���ڼƻ�
        BigDecimal planAmount = BigDecimal.ZERO;
        //���վ���
        BigDecimal day_increase_num = BigDecimal.ZERO;
        //���ھ���
        BigDecimal current_increase_num = BigDecimal.ZERO;
        //���ڳ��ƻ�
        BigDecimal current_over_plan_num = BigDecimal.ZERO;
        //���ھ���ͬ��
        BigDecimal current_increase_num_yoy = BigDecimal.ZERO;
        //���ھ���ռ��
        BigDecimal current_increase_num_proportion = BigDecimal.ZERO;
        //���ڼƻ������
        BigDecimal current_plan_completion_rate = BigDecimal.ZERO;
        //���ڼƻ������ͬ��
        BigDecimal current_plan_completion_rate_yoy = BigDecimal.ZERO;
        //���ھ�������
        BigDecimal current_increase_num_growth_rate = BigDecimal.ZERO;
        //���ھ�������ͬ��
        BigDecimal current_increase_num_growth_rate_yoy = BigDecimal.ZERO;
        //���ڷ�����
        BigDecimal occ = BigDecimal.ZERO;
        //ͬ��
        BigDecimal occYoy = BigDecimal.ZERO;
        //����Ԥ�Ƶ�����
        BigDecimal expireEstimate = BigDecimal.ZERO;
        //ͬ��
        BigDecimal expireEstimateYoy = BigDecimal.ZERO;
        //�����ѵ�����
        BigDecimal expire = BigDecimal.ZERO;
        //ͬ��
        BigDecimal expireYoy = BigDecimal.ZERO;
        //����ʣ��Ԥ�Ƶ�����
        BigDecimal expireEstimateLeft = BigDecimal.ZERO;
        //ͬ��
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
        productReportSum.setName("��˾�����");
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

        /*--------ǰ�����ҵС��----*/
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
        fiveSum.setName("ǰ�����ҵС��");
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


        /*---ǰʮ����ҵС��-----*/
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
        tenSum.setName("ǰʮ����ҵС��");
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


        // // ƽ����
        // ProductReport productReportAvg = new ProductReport();
        // productReportAvg.setId("avg");
        // productReportAvg.setParentId("-1");
        // productReportAvg.setName("ƽ����");
        // productReportAvg.setLevel(1);
        // // ��λ��
        // ProductReport productReportMid = new ProductReport();
        // productReportSum.setId("mid");
        // productReportSum.setParentId("-1");
        // productReportSum.setName("��λ��");
        // productReportSum.setLevel(1);
        // // ����
        // ProductReport productReportMod = new ProductReport();
        // productReportMod.setId("mod");
        // productReportMod.setParentId("-1");
        // productReportMod.setName("����");
        // productReportMod.setLevel(1);
        // // ���ֵ
        // ProductReport productReportMax = new ProductReport();
        // productReportMax.setId("max");
        // productReportMax.setParentId("-1");
        // productReportMax.setName("���ֵ");
        // productReportMax.setLevel(1);
        // // ��Сֵ
        // ProductReport productReportMin = new ProductReport();
        // productReportMin.setId("min");
        // productReportMin.setParentId("-1");
        // productReportMin.setName("��Сֵ");
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

    //ͬ�� ��ǰֵ-ȥ��ֵ
    private void getIndustryYoy(List<ProductReport> industryReportList, List<ProductReport> organReportListCooy, String date, String cycel, FdOrgan fdorgan) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------��ȡ����list ���ܵ���ҵ----------*/
        List<TbRptBaseinfoIndustry> industryDataList = getIndustryDataList(beginDate, fdorgan);

        // ��װ��������
        buildList(organReportListCooy, industryDataList, cycel, beginDate);
        // ���ھ���ռ��
        getCurrentIncreaseNumProportion(organReportListCooy);
        // ���ڷ�����ͬ��
        BigDecimal occYoy = BigDecimal.ZERO;
        // ����Ԥ�Ƶ�����ͬ��
        BigDecimal expireEstimateYoy = BigDecimal.ZERO;
        // �����ѵ�����ͬ��
        BigDecimal expireYoy = BigDecimal.ZERO;
        // ����ʣ��Ԥ�Ƶ�����ͬ��
        BigDecimal expireEstimateLeftYoy = BigDecimal.ZERO;
        //���ھ���ͬ��
        BigDecimal current_increase_num_yoy = BigDecimal.ZERO;
        // �ƻ������ͬ��
        BigDecimal current_plan_completion_rate_yoy = BigDecimal.ZERO;
        // ��������ͬ��
        BigDecimal current_increase_num_growth_rate_yoy = BigDecimal.ZERO;

        for (ProductReport productReport : industryReportList) {
            for (ProductReport report : organReportListCooy) {
                if (productReport.getId().equals(report.getId())) {
                    //ͬ��  ��ǰֵ-ȥ��ֵ
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

    // ���ھ���ռ��
    private void getCurrentIncreaseNumProportion(List<ProductReport> productReportList) {
        BigDecimal totalNum = BigDecimal.ZERO;
        for (ProductReport tempPr : productReportList) {
            totalNum = BigDecimalUtil.add(totalNum, tempPr.getCurrent_increase_num());
        }
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_proportion(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), totalNum, 4).multiply(ReportConstant.RATIO_NUM));
        }


    }

    // ���ռ�Ƚ��ڳ�
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
     * ���ó������ռ��
     *
     * @param reportListCopy
     * @param baseInfoList   ��������
     * @param cycel          �������� 1-�� 2-�� 3-�� 4-��
     */
    private void buildBalanceRatioOfBeginData(List<ProductReport> reportListCopy, List<TbRptBaseinfoIndustry> baseInfoList, String cycel) {
        //�ڳ������
        BigDecimal balanceCount = BigDecimal.ZERO;

        //���
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
        //�������ռ��
        for (ProductReport productReport : reportListCopy) {
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    // ���ռ��
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

    // ��װ��������
    private void buildList(List<ProductReport> reportList, List<TbRptBaseinfoIndustry> tbRptBaseinfoLoankindList, String cycel, String date) {
        for (ProductReport productReport : reportList) {
            for (TbRptBaseinfoIndustry info : tbRptBaseinfoLoankindList) {
                if (productReport.getId().equals(info.getIndustry())) {
                    //���
                    productReport.setBalance(BigDecimalUtil.divide(info.getBalance(), ReportConstant.MONEY_NUM));
                    //���վ���
                    productReport.setDay_increase_num(BigDecimalUtil.divide(info.getDayIncrease(), ReportConstant.MONEY_NUM));

                    if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                        //���ھ���
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getYearIncrease(), ReportConstant.MONEY_NUM));
                        // ���ڷ�����
                        productReport.setOcc(BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM));
                        // ����Ԥ�Ƶ�����
                        productReport.setExpireEstimate(BigDecimalUtil.divide(info.getYearExpireEstimate(), ReportConstant.MONEY_NUM));
                        // �����ѵ�����
                        productReport.setExpire(BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM));
                        // ����ʣ��Ԥ�Ƶ�����
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(info.getYearExpireEstimateLeft(), ReportConstant.MONEY_NUM));
                        // ���ھ�������
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getYearIncrease(), info.getBalanceYear(), 4).multiply(ReportConstant.RATIO_NUM));

                    } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getSeasonIncrease(), ReportConstant.MONEY_NUM));
                        productReport.setOcc(BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimate(BigDecimalUtil.divide(info.getSeasonExpireEstimate(), ReportConstant.MONEY_NUM));
                        productReport.setExpire(BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(info.getSeasonExpireEstimateLeft(), ReportConstant.MONEY_NUM));
                        // ���ھ�������
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getSeasonIncrease(), info.getBalanceSeason(), 4).multiply(ReportConstant.RATIO_NUM));


                    } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getMonthIncrease(), ReportConstant.MONEY_NUM));
                        productReport.setOcc(BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimate(BigDecimalUtil.divide(info.getMonthExpireEstimate(), ReportConstant.MONEY_NUM));
                        productReport.setExpire(BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(info.getMonthExpireEstimateLeft(), ReportConstant.MONEY_NUM));
                        // ���ھ�������
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getMonthIncrease(), info.getBalanceMonth(), 4).multiply(ReportConstant.RATIO_NUM));


                    } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                        productReport.setCurrent_increase_num(BigDecimalUtil.divide(info.getDayIncrease(), ReportConstant.MONEY_NUM));
                        productReport.setOcc(BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimate(BigDecimalUtil.divide(BigDecimal.ZERO, ReportConstant.MONEY_NUM));
                        productReport.setExpire(BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM));
                        productReport.setExpireEstimateLeft(BigDecimalUtil.divide(BigDecimal.ZERO, ReportConstant.MONEY_NUM));
                        // ���ھ�������
                        productReport.setCurrent_increase_num_growth_rate(BigDecimalUtil.divide(info.getDayIncrease(), BigDecimalUtil.subtract(info.getBalance(),info.getDayExpire()), 4).multiply(ReportConstant.RATIO_NUM));

                    }
                    break;
                }
            }
        }
    }

    //��ȡ����list ���ܵ���ҵ
    private List<TbRptBaseinfoIndustry> getIndustryDataList(String date, FdOrgan fdorgan) {
        HashMap<String, Object> param = new HashMap<>();
        List<TbRptBaseinfoIndustry> tbRptBaseinfoLoankindList = new ArrayList<>();

        param.put("rptDate", date);
        //�������к�һ�� �ֱ���
        if (Constant.ORGAN_LEVEL_0.equals(fdorgan.getOrganlevel())) {
            tbRptBaseinfoLoankindList = tbRptBaseinfoIndustryMapper.selectLevelOneOrganData(param);
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoIndustryMapper.selectLevelOhterOrganData(param);
        }
        return tbRptBaseinfoLoankindList;

    }

    //��������ҵΪ�����Ļ���list
    private List<ProductReport> getIndustryList() {
        ArrayList<ProductReport> organReportList = new ArrayList<>();

        //��ѯ��ҵ
        List<Map<String, Object>> industryList = tbRptBaseinfoIndustryMapper.selectIndustry();
        int order = 10;
        for (Map<String, Object> map : industryList) {
            ProductReport organReport = new ProductReport();
            //idΪ������
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

    //��ȡ��ҵ����
    private String getIndustryName(String industry) {
        industry = industry.toLowerCase();
        //�ֵ���ѯ��ҵ����
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
