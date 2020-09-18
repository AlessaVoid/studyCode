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
     * ������������
     *
     * @param combType  ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date      yyyymmdd
     * @param cycel     �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan   ����
     * @param dimension ����ά�ȣ�1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
     * @return
     */
    @Override
    public List<TimeLimitReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception {
        List<TimeLimitReport> resultList = new ArrayList<>();

        /*--------��ȡ����list ���ܵ�����----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfOrganList = getTimeLimitOrganDataList(date, combType, fdorgan);
        /*---------��װ����---------------*/
        // �����Ի���Ϊ�����Ļ���list
        List<TimeLimitReport> organReportList = getReportTimeLimitOrganList(fdorgan);
        if (organReportList == null || organReportList.size() == 0) {
            return new ArrayList<>();
        }

        // ��װ��������
        buildTimeLimitOrganList(organReportList, tbRptBaseinfoLoankindOfOrganList, combType, cycel, date);
        /*--����  ��������������--*/
        rankTimeLimitOrganList(organReportList);

        // ����ά�ȣ�  1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-����
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            // ����

            //�����ͣ���λ�������������ֵ����Сֵ��
            addProductOtherList(organReportList, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);
            //ת��Ϊ�����б�
            resultList = TimeLimitReport.transToTree(organReportList, 8, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);
        }

        return resultList;
    }


    /**
     * ���ޱ�����
     *
     * @param response
     * @param request
     * @param combType  ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date      yyyymmdd
     * @param cycel     �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan   ����
     * @param dimension ����ά�ȣ�1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
     * @param unit      ����λ 1-��Ԫ 2-��Ԫ
     */
    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String dimension, String unit) throws Exception {
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
        List<TimeLimitReport> data = getReportData(combType, date, cycel, fdorgan, dimension);

        int level = 5;
        List<TimeLimitReport> reportList = TimeLimitReport.treeTransTo(data, new ArrayList<TimeLimitReport>(), level);

        /*---------д���ļ�---------*/
        //����
        String filename = "���ޱ�-" + date;

        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 24);
        POIExportUtil.setCell(sheet, 0, 0, filename, cellStyle);
        //��λ
        String amountUnit = "��Ԫ";
        if (ReportConstant.UNIT_2.equals(unit)) {
            amountUnit = "��Ԫ";
        }
        POIExportUtil.setCell(sheet, 1, 0, "��λ:" + amountUnit, cellStyle);

        //����
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

        //�����п�
        for (int i = 0; i < 25; i++) {
            POIExportUtil.setCellWidth(sheet, i);
        }

        //��������
        POIExportUtil.createFreezePane(sheet, 1, 5);

        //�ļ���
        filename = filename + ".xls";
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

        POIExportUtil.CellRangeAddress(sheet, row, row + 1, column, column);
        POIExportUtil.setCell(sheet, row, column++, "����", cellStyle);
        /*����*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 9);
        POIExportUtil.setCell(sheet, row, column, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "һ��������", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "1-3��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "3-6��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "6-12��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "���ںϼ�", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);

        /*�г���*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column + 11);
        POIExportUtil.setCell(sheet, row, column, "�г���", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "1-2��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "2-3��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "3-5��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "5-10��", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "10������", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "�г��ںϼ�", cellStyle);
        POIExportUtil.setCell(sheet, row + 1, column++, "����", cellStyle);

        /*�ܼ�*/
        POIExportUtil.CellRangeAddress(sheet, row, row + 1, column, column);
        POIExportUtil.setCell(sheet, row, column++, "�ܼ�", cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row, row + 1, column, column);
        POIExportUtil.setCell(sheet, row, column++, "����", cellStyle);

    }

    //�����ͣ���λ�������������ֵ����Сֵ��
    private void addProductOtherList(List<TimeLimitReport> organReportList, String parentId, int level) {
        /*-----------����-------------*/
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
        productReportSum.setName("�ϼ�");
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

        organReportList.add(0, productReportSum);
        // productReportList.add(productReportAvg);
        // productReportList.add(productReportMid);
        // productReportList.add(productReportMod);
        // productReportList.add(productReportMax);
        // productReportList.add(productReportMin);

    }


    //����  ��������������
    private void rankTimeLimitOrganList(List<TimeLimitReport> organReportList) {
        Collections.sort(organReportList, new Comparator<TimeLimitReport>() {
            @Override
            public int compare(TimeLimitReport o1, TimeLimitReport o2) {
                //�Ӵ�С
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });
    }

    //��װ��������
    private void buildTimeLimitOrganList(List<TimeLimitReport> organReportList, List<TbRptBaseinfoLoankind> organDataList, String combType, String cycel, String date) {
        //���ݻ�����ƥ��
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

        //����ϼ���
        for (TimeLimitReport report : organReportList) {
            //���ںϼ�
            BigDecimal monthBalanceTotal = report.getMonth1Balance()
                    .add(report.getMonth13Balance())
                    .add(report.getMonth36Balance())
                    .add(report.getMonth612Balance());
            Integer monthCountTotal = report.getMonth1Count()
                    + report.getMonth13Count()
                    + report.getMonth36Count()
                    + report.getMonth612Count();
            // �г��ںϼ�
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
            //�ܼ�
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

    //��ȡ����list ���ܵ�����
    private List<TbRptBaseinfoLoankind> getTimeLimitOrganDataList(String date, String combType, FdOrgan fdorgan) {
        HashMap<String, Object> param = new HashMap<>();
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = new ArrayList<>();
        String[] combListParam = null;
        String[] excludeCombListParam = null;
        // 1 ȫ����2�ٲ�ţ�4 ʵ��
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
        //�������к�һ�� �ֱ���
        if (Constant.ORGAN_LEVEL_0.equals(fdorgan.getOrganlevel())) {
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectTimeLimitLevelOneOrgan(param);
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectTimeLimitLevelOtherOrgan(param);
        }
        return tbRptBaseinfoLoankindList;
    }

    //�����Ի���Ϊ�����Ļ���list
    private List<TimeLimitReport> getReportTimeLimitOrganList(FdOrgan fdOrgan) throws Exception {
        ArrayList<TimeLimitReport> organReportList = new ArrayList<>();
        FdOrgan fdOrganParam = new FdOrgan();
        fdOrganParam.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(fdOrganParam);
        //��ӱ���
        FdOrgan fdOrganCopy = (FdOrgan) BocoUtils.deepCopy(fdOrgan);
        fdOrganCopy.setOrganname(fdOrganCopy.getOrganname() + "����");
        fdOrganList.add(fdOrganCopy);

        for (FdOrgan organ : fdOrganList) {
            TimeLimitReport organReport = new TimeLimitReport();
            //idΪ������
            organReport.setId(organ.getThiscode());
            //parentΪ�ϼ������ţ�������Ϊ����������
            organReport.setParentId(organ.getThiscode().equals(WebContextUtil.getSessionOrgan().getThiscode()) ? organ.getThiscode() : organ.getUporgan());
            organReport.setName(organ.getOrganname());
            organReport.setLevel(ReportConstant.TREE_LEVEL_TWO);
            organReport.setOrder(organ.getLeveloneorder() == null ? Integer.MAX_VALUE : organ.getLeveloneorder());
            organReportList.add(organReport);
        }
        return organReportList;

    }
}
