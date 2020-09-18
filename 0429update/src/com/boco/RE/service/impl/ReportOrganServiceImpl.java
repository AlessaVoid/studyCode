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


    //����һ������map
    private static HashMap<String, String> threeToOneMap = new HashMap<>();
    //������������map
    private static HashMap<String, String> threeToTwoMap = new HashMap<>();


    // ������������
    @Override
    public List<ProductReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception {

        List<ProductReport> resultList = new ArrayList<>();
        // ����ά�ȣ�  1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
        if (ReportConstant.DIMENSION_KEY_ORGAN.equals(dimension)) {
            // �ص���

            /*--------��ȡ����list ���ܵ��ص���� ----------*/
            List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfKeyOrganList = getKeyOrganDataList(date, combType);

            /*---------��װ����---------------*/
            // �������ص����Ϊ�����Ļ���list
            List<ProductReport> keyOrganReportList = getReportKeyOrganList();
            if (keyOrganReportList == null || keyOrganReportList.size() == 0) {
                return new ArrayList<>();
            }

            //����List����,����ͬ��
            List<ProductReport> keyOrganReportListCooy = copyOrganReportList(keyOrganReportList);
            // ��װ��������
            buildList(keyOrganReportList, tbRptBaseinfoLoankindOfKeyOrganList, combType, cycel, date);
            //���ռ��
            getOrganBalanceRatio(keyOrganReportList);
            //���ռ�Ƚ��ڳ�
            getKeyOrganBalanceRatioCompareBeginDate(keyOrganReportList, keyOrganReportListCooy, date, cycel, combType, tbRptBaseinfoLoankindOfKeyOrganList);
            //���ڼƻ�
            getKeyOrganPlanAmount(keyOrganReportList, fdorgan, combType, date, cycel);
            //������������
            getKeyOrganCurrentIncreaseReqLine(keyOrganReportList, date, cycel);
            //���ڻ�������
            getKeyOrganCurrentIncreaseReqOrgan(keyOrganReportList, date, cycel);
            //���ڳ��ƻ�
            ReportService.getCurrentOverPlanNum(keyOrganReportList);
            // ���ھ���ռ��
            getCurrentIncreaseNumProportion(keyOrganReportList);
            // ���ڼƻ������
            ReportService.getCurrentPlanCompletionRate(keyOrganReportList);
            // ͬ�� ��ǰֵ-ȥ��ֵ
            getKeyOrganYoy(keyOrganReportList, keyOrganReportListCooy, date, cycel, combType, fdorgan);

            //����
            ReportService.rankProductReportList(keyOrganReportList);

            //�����ͣ���λ�������������ֵ����Сֵ��
            addProductOtherList(keyOrganReportList, ReportConstant.KEY_ORGAN, ReportConstant.TREE_LEVEL_ONE);

            //ת��Ϊ�����б�
            resultList = ProductReport.transToTree(keyOrganReportList, 8, ReportConstant.KEY_ORGAN, ReportConstant.TREE_LEVEL_ONE);


        } else {
            //��ѯ��������

            /*--------��ȡ����list ���ܵ�һ�ֻ���----------*/
            List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfOrganList = getOrganDataList(date, combType, fdorgan);

            /*---------��װ����---------------*/
            // �����Ի���Ϊ�����Ļ���list
            List<ProductReport> organReportList = getReportOrganList(fdorgan);
            if (organReportList == null || organReportList.size() == 0) {
                return new ArrayList<>();
            }

            //����List����,����ͬ��
            List<ProductReport> organReportListCooy = copyOrganReportList(organReportList);
            // ��װ��������
            buildList(organReportList, tbRptBaseinfoLoankindOfOrganList, combType, cycel, date);
            //���ռ��
            getOrganBalanceRatio(organReportList);
            //���ռ�Ƚ��ڳ�
            getOrganBalanceRatioCompareBeginDate(organReportList, organReportListCooy, date, cycel, combType, fdorgan, tbRptBaseinfoLoankindOfOrganList);
            //���ڼƻ�
            getOrganPlanAmount(organReportList, fdorgan, combType, date, cycel);
            //������������
            getKeyOrganCurrentIncreaseReqLine(organReportList, date, cycel);
            //���ڻ�������
            getKeyOrganCurrentIncreaseReqOrgan(organReportList, date, cycel);

            //���ڳ��ƻ�
            ReportService.getCurrentOverPlanNum(organReportList);
            // ���ھ���ռ��
            getCurrentIncreaseNumProportion(organReportList);
            // ���ڼƻ������
            ReportService.getCurrentPlanCompletionRate(organReportList);
            // ͬ�� ��ǰֵ-ȥ��ֵ
            getOrganYoy(organReportList, organReportListCooy, date, cycel, combType, fdorgan);

            /*--����  ��������������--*/
            rankOrganReportList(organReportList);



            /*--------��ȡ������Ӧ�Ĵ�����Ϣ-----------*/
            ArrayList<ProductReport> productReportCombList = new ArrayList<>();
            for (ProductReport productReport : organReportList) {
                FdOrgan fdOrganParam = new FdOrgan();
                fdOrganParam.setThiscode(productReport.getId());
                FdOrgan fdOrgan = fdOrganMapper.selectByAttr(fdOrganParam).get(0);
                List<ProductReport> productReportList = reportService.getProductReportDataOfOrganExcel(combType, date, cycel, fdOrgan);

                productReportCombList.addAll(productReportList);
            }


            if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
                // ����

                //�����ͣ���λ�������������ֵ����Сֵ��
                addProductOtherList(organReportList, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);

                //�Ѵ������ݼ��뵽����List����
                organReportList.addAll(productReportCombList);

                //ת��Ϊ�����б�
                resultList = ProductReport.transToTree(organReportList, 8, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);

            } else {
                // ����


                /*--------��ȡ����list ���ܵ�����----------*/
                List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfAreaList = getAreaDataList(date, combType, dimension);

                /*---------��װ����---------------*/
                // ����������Ϊ�����Ļ���list,���Ҹı�organReportList��parentId
                List<ProductReport> areaReportList = getAreaReportList(dimension, organReportList);
                if (areaReportList == null || areaReportList.size() == 0) {
                    return new ArrayList<>();
                }

                //����List����,����ͬ��
                List<ProductReport> areaReportListCooy = copyOrganReportList(areaReportList);
                // ��װ��������
                buildList(areaReportList, tbRptBaseinfoLoankindOfAreaList, combType, cycel, date);
                //���ռ��
                getOrganBalanceRatio(areaReportList);
                //���ռ�Ƚ��ڳ�
                getAreaBalanceRatioCompareBeginDate(areaReportList, areaReportListCooy, date, cycel, combType, dimension, tbRptBaseinfoLoankindOfAreaList);

                //��ѯ������Ϣ
                List<FdReportOrgan> fdReportOrganList = fdReportOrganMapper.selectByAttr(new FdReportOrgan());
                //���ڼƻ�
                getAreaPlanAmount(areaReportList, fdorgan, combType, date, cycel, dimension, fdReportOrganList);
                //������������
                getAreaLineReq(areaReportList, organReportList, dimension, fdReportOrganList);
                //���ڻ�������
                getAreaOrganReq(areaReportList, organReportList, dimension, fdReportOrganList);
                //���ڳ��ƻ�
                ReportService.getCurrentOverPlanNum(areaReportList);
                // ���ھ���ռ��
                getCurrentIncreaseNumProportion(areaReportList);
                // ���ڼƻ������
                ReportService.getCurrentPlanCompletionRate(areaReportList);
                // ͬ�� ��ǰֵ-ȥ��ֵ
                getAreaYoy(areaReportList, areaReportListCooy, date, cycel, combType, fdorgan, dimension);
                //����
                ReportService.rankProductReportList(areaReportList);

                //�����ͣ���λ�������������ֵ����Сֵ��
                addProductOtherList(areaReportList, ReportConstant.AREA, ReportConstant.TREE_LEVEL_ONE);

                //�Ѵ������ݼ��뵽����List����
                organReportList.addAll(productReportCombList);
                //�ѻ���List���뵽����List
                areaReportList.addAll(organReportList);

                //ת��Ϊ�����б�
                resultList = ProductReport.transToTree(areaReportList, 8, ReportConstant.AREA, ReportConstant.TREE_LEVEL_ONE);
            }
        }

        return resultList;

    }

    /**
     *  ��������
     * @param response
     * @param request
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date yyyymmdd
     * @param cycel �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan ����
     * @param dimension ����ά�ȣ�1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
     * @param unit ����λ 1-��Ԫ 2-��Ԫ
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
        List<ProductReport> data = getReportData(combType, date, cycel, fdorgan, dimension);

        int level = 2;
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            level = 2;
        } else if (ReportConstant.DIMENSION_KEY_ORGAN.equals(dimension)) {
            level = 1;
        }
        List<ProductReport> productReportList = ProductReport.treeTransTo(data,new ArrayList<ProductReport>(),level);

        /*---------д���ļ�---------*/
        //����
        String filename = "-" + date;
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            filename = "������" + filename;
        } else {
            filename = "�����ص��б�" + filename;
        }
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 32);
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
            String[] arr1 = {"���б���","��������","��������","��������","���е���","���ϵ���","���ϵ���","��������"};
            if(Arrays.asList(arr1).contains(GGName)){

            }*/

        }

        //�����п�
        for (int i = 0; i <33 ; i++) {
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
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ռ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ռ�Ƚ��ڳ�" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ�������(����)" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ�������(����)" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ڼƻ�" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���վ���" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ���" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ڳ��ƻ�" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ���ͬ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ھ���ռ��" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ڼƻ������" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        POIExportUtil.setCell(sheet, row, column++, "���ڼƻ������ͬ��" , cellStyle);
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

    //����
    private void rankOrganReportList(List<ProductReport> organReportList) throws Exception {
        Iterator<ProductReport> it = organReportList.iterator();
        //ȡ������
        ProductReport organ = new ProductReport();
        while (it.hasNext()) {
            ProductReport next = it.next();
            if (WebContextUtil.getSessionOrgan().getThiscode().equals(next.getId())) {
                organ = next;
                it.remove();
            }
        }

        ReportService.rankProductReportList(organReportList);
        //���뱾��
        organReportList.add(organ);
        // ���Ի�������
        Collections.sort(organReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o1.getCombOrder().compareTo(o2.getCombOrder());
            }
        });
    }

    /**
     * ��ȡ���ڙC������ �������
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
     * ��ȡ������������ �������
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
     * ��ȡ ���ڻ�������-�ص���
     *
     * @param keyOrganReportList
     * @param date
     * @param cycel
     */
    private void getKeyOrganCurrentIncreaseReqOrgan(List<ProductReport> keyOrganReportList, String date, String cycel) {
        String[] timePeriodArr = getTimePeriods(date, cycel);
        ArrayList<String> levelOtherOrganList = new ArrayList<>();
        //�ص��� id �ǻ����ţ���Ϊһ�ֺͶ��ֵ���������
        for (ProductReport tempPr : keyOrganReportList) {
            String id = tempPr.getId();//һ�ֻ���ֻ�����
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
     * ��ȡ ������������-�ص���
     *
     * @param keyOrganReportList
     * @param date
     * @param cycel
     */
    private void getKeyOrganCurrentIncreaseReqLine(List<ProductReport> keyOrganReportList, String date, String cycel) {

        String[] timePeriodArr = getTimePeriods(date, cycel);
        ArrayList<String> levelOtherOrganList = new ArrayList<>();
        //�ص��� id �ǻ����ţ���Ϊһ�ֺͶ��ֵ���������
        for (ProductReport tempPr : keyOrganReportList) {
            String id = tempPr.getId();//һ�ֻ���ֻ�����
            levelOtherOrganList.add(id);
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("months", timePeriodArr);
        param.put("organList", levelOtherOrganList);

        //��ѯ һ��ʱ�� ���ֻ����� ��������
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
        /*--------��ȡ����list ���ܵ�����----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfKeyOrganList = getKeyOrganDataList(beginDate, combType);

        /*----------��������List-------*/
        // ��װ��������
        buildList(keyOrganReportListCooy, tbRptBaseinfoLoankindOfKeyOrganList, combType, cycel, beginDate);
        //���ڼƻ�
        getKeyOrganPlanAmount(keyOrganReportListCooy, fdorgan, combType, date, cycel);
        //���ڳ��ƻ�
        ReportService.getCurrentOverPlanNum(keyOrganReportListCooy);
        // ���ھ���ռ��
        getCurrentIncreaseNumProportion(keyOrganReportListCooy);
        // ���ڼƻ������
        ReportService.getCurrentPlanCompletionRate(keyOrganReportListCooy);

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

        for (ProductReport productReport : keyOrganReportList) {
            for (ProductReport report : keyOrganReportListCooy) {
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

    //�ص��б��ڼƻ�
    private void getKeyOrganPlanAmount(List<ProductReport> keyOrganReportList, FdOrgan fdorgan, String combType, String date, String cycel) {

        /*-----����ص��в��ҶԲ�ͬ����Ľ������ִ���---*/
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

        /*----��װ����-------*/
        String[] months = null;

        /*---------��ѯ����-------*/
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
        }

        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        param.put("organ", fdorgan.getThiscode());
        param.put("organList", levelOneOrganList);
        //��ѯ��һ�����еļƻ�
        List<Map<String, Object>> levelOnePlanAmounList = reportCombMapper.getOrganPlan(param);

        param.put("organ", null);
        param.put("organList", levelOtherOrganList);
        //��ѯ�������мƻ�
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

    //�ص������ռ�Ƚϳ���
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
     * ���ó������ռ��
     *
     * @param reportListCopy
     * @param baseInfoList   ��������
     * @param cycel          �������� 1-�� 2-�� 3-�� 4-��
     */
    private void buildBalanceRatioOfBeginData(List<ProductReport> reportListCopy, List<TbRptBaseinfoLoankind> baseInfoList, String cycel) {
        //�ڳ������
        BigDecimal balanceCount = BigDecimal.ZERO;

        //���
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
        //�������ռ��
        for (ProductReport productReport : reportListCopy) {
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    // ��ȡ����list ���ܵ��ص����
    private List<TbRptBaseinfoLoankind> getKeyOrganDataList(String date, String combType) {
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = new ArrayList<>();
        HashMap<String, Object> param = new HashMap<>();
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
        }

        param.put("rptDate", date);
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        //���ܵ�һ�ֵ�����
        List<TbRptBaseinfoLoankind> OneOrganList = tbRptBaseinfoLoankindMapper.selectByDateAndComb(param);

        ArrayList<String> organList = new ArrayList<>();
        //���û�ж����ص��У��Ͳ������ݣ�������ӿ��ַ���
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

        //��ѯ��һ����������ص���
        param.put("organList", organList);
        List<TbRptBaseinfoLoankind> otherKeyOrganList = tbRptBaseinfoLoankindMapper.selectOtherKeyOrgan(param);
        tbRptBaseinfoLoankindList.addAll(otherKeyOrganList);

        return tbRptBaseinfoLoankindList;
    }

    //�������ص����Ϊ�����Ļ���list
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

    //���������ڼƻ�
    private void getAreaPlanAmount(List<ProductReport> areaReportList, FdOrgan fdorgan, String combType, String date, String cycel, String dimension, List<FdReportOrgan> fdReportOrganList) {
        String[] months = null;

        /*---------��ѯ����-------*/
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
        }

        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        param.put("organ", fdorgan.getThiscode());
        List<Map<String, Object>> planAmlounList = reportCombMapper.getOrganPlan(param);


        /*---------��װ����----------*/
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
     * ���㱾�ھ���ռ��
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

    //����ͬ��
    private void getAreaYoy(List<ProductReport> areaReportList, List<ProductReport> areaReportListCooy, String date, String cycel, String combType, FdOrgan fdorgan, String dimension) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------��ȡ����list ���ܵ�����----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfAreaList = getAreaDataList(beginDate, combType, dimension);

        /*----------��������List-------*/
        // ��װ��������
        buildList(areaReportListCooy, tbRptBaseinfoLoankindOfAreaList, combType, cycel, beginDate);

        //��ѯ������Ϣ
        List<FdReportOrgan> fdReportOrganList = fdReportOrganMapper.selectByAttr(new FdReportOrgan());
        //���ڼƻ�
        getAreaPlanAmount(areaReportListCooy, fdorgan, combType, beginDate, cycel, dimension, fdReportOrganList);
        //���ڳ��ƻ�
        ReportService.getCurrentOverPlanNum(areaReportListCooy);
        // ���ھ���ռ��
        getCurrentIncreaseNumProportion(areaReportListCooy);
        // ���ڼƻ������
        ReportService.getCurrentPlanCompletionRate(areaReportListCooy);

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

        for (ProductReport productReport : areaReportList) {
            for (ProductReport report : areaReportListCooy) {
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

    // ���ռ�Ƚ��ڳ�
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

    // ����������Ϊ�����Ļ���list,���Ҹı�organReportList��parentId
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
                //idΪ������
                organReport.setId(key);
                //parentΪ�ϼ������ţ����е�ҲΪ����
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type1Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setCombOrder(type1Map.get(key) == 0 ? Integer.MAX_VALUE : type1Map.get(key));
                areaReportList.add(organReport);
            }
            //����organReportList��parentId
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
                //idΪ������
                organReport.setId(key);
                //parentΪ�ϼ������ţ����е�ҲΪ����
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type2Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setCombOrder(type2Map.get(key) == 0 ? Integer.MAX_VALUE : type2Map.get(key));
                areaReportList.add(organReport);
            }
            //����organReportList��parentId
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
                //idΪ������
                organReport.setId(key);
                //parentΪ�ϼ������ţ����е�ҲΪ����
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type3Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setCombOrder(type3Map.get(key) == 0 ? Integer.MAX_VALUE : type3Map.get(key));
                areaReportList.add(organReport);
            }
            //����organReportList��parentId
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

    //��ȡ��������
    private String getAreaName(String dimension, int type) {
        if (ReportConstant.DIMENSION_AREA_1.equals(dimension)) {
            if (0 == type) {
                return "����";
            } else if (1 == type) {
                return "���б���";
            } else if (2 == type) {
                return "��������";
            } else if (3 == type) {
                return "��������";
            } else if (4 == type) {
                return "��������";
            } else if (5 == type) {
                return "���е���";
            } else if (6 == type) {
                return "���ϵ���";
            } else if (7 == type) {
                return "���ϵ���";
            } else if (8 == type) {
                return "��������";
            }

        } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
            if (0 == type) {
                return "����";
            } else if (1 == type) {
                return "���б���";
            } else if (2 == type) {
                return "������";
            } else if (3 == type) {
                return "������";
            } else if (4 == type) {
                return "������";
            } else if (5 == type) {
                return "�в�����";
            } else if (6 == type) {
                return "��������";
            } else if (7 == type) {
                return "��������";
            }

        } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
            if (0 == type) {
                return "����";
            } else if (1 == type) {
                return "���б���";
            } else if (2 == type) {
                return "��һ��";
            } else if (3 == type) {
                return "�ڶ���";
            } else if (4 == type) {
                return "������";
            } else if (5 == type) {
                return "������";
            }
        }
        return "";
    }

    /**
     * ��ѯ�����������List
     *
     * @param date      yyyymmdd
     * @param combType  ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param dimension ����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
     * @return
     */
    private List<TbRptBaseinfoLoankind> getAreaDataList(String date, String combType, String dimension) {
        HashMap<String, Object> param = new HashMap<>();
        //��Ҫ��ѯ�Ĵ��� 1������
        String[] combListParam = null;
        //��Ҫ�ų��Ĵ��� 1��2,3������
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

        //���û���ά��
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
        productReportSum.setName("�ϼ�");
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

        productReportList.add(0, productReportSum);
        // productReportList.add(productReportAvg);
        // productReportList.add(productReportMid);
        // productReportList.add(productReportMod);
        // productReportList.add(productReportMax);
        // productReportList.add(productReportMin);
    }

    //����ͬ����Ϣ
    private void getOrganYoy(List<ProductReport> reportList, List<ProductReport> reportListCooy, String date, String cycel, String combType, FdOrgan fdorgan) {
        String beginDate = ReportService.getLastYearDay(date);
        /*--------��ȡ����list ���ܵ�һ�ֻ���----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getOrganDataList(beginDate, combType, fdorgan);

        /*----------��������List-------*/
        // ��װ��������
        buildList(reportListCooy, tbRptBaseinfoLoankindList, combType, cycel, beginDate);
        //���ڼƻ�
        getOrganPlanAmount(reportListCooy, fdorgan, combType, beginDate, cycel);
        //���ڳ��ƻ�
        ReportService.getCurrentOverPlanNum(reportListCooy);
        // ���ھ���ռ��
        getCurrentIncreaseNumProportion(reportListCooy);
        // ���ڼƻ������
        ReportService.getCurrentPlanCompletionRate(reportListCooy);

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

        for (ProductReport productReport : reportList) {
            for (ProductReport report : reportListCooy) {
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

    // ���ڼƻ�
    private void getOrganPlanAmount(List<ProductReport> reportList, FdOrgan fdorgan, String combType, String date, String cycel) {
        String[] months = null;

        /*---------��ѯ����-------*/
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
        months = getTimePeriods(date, cycel);

        HashMap<String, Object> param = new HashMap<>();
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("months", months);
        param.put("organ", fdorgan.getThiscode());
        List<Map<String, Object>> planAmlounList = reportCombMapper.getOrganPlan(param);

        /*---------��װ����----------*/
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

    // ���ռ�Ƚ��ڳ�
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
     * ��װ��������
     *
     * @param reportList                ����list
     * @param tbRptBaseinfoLoankindList ����list
     * @param combType                  ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param cycel                     �������� 1-�� 2-�� 3-�� 4-��
     * @param date                      yyyymmdd
     */
    private void buildList(List<ProductReport> reportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String combType, String cycel, String date) {
        for (ProductReport productReport : reportList) {
            for (TbRptBaseinfoLoankind info : tbRptBaseinfoLoankindList) {
                if (productReport.getId().equals(info.getRptOrgan())) {
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

    //��ȡ������������
    private List<TbRptBaseinfoLoankind> getOrganDataList(String date, String combType, FdOrgan fdorgan) {
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
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByDateAndComb(param);
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectLevelOtherOrganDataByDateAndComb(param);
        }
        return tbRptBaseinfoLoankindList;
    }

    //����list
    public static List<ProductReport> copyOrganReportList(List<ProductReport> reportList) {
        ArrayList<ProductReport> list = new ArrayList<>();
        for (ProductReport productReport : reportList) {
            ProductReport productReport2 = (ProductReport) BocoUtils.deepCopy(productReport);
            list.add(productReport2);
        }
        return list;
    }

    //�������ռ��
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

    //�����Ի���Ϊ�����Ļ���list
    private List<ProductReport> getReportOrganList(FdOrgan fdOrgan) throws Exception {
        ArrayList<ProductReport> organReportList = new ArrayList<>();
        FdOrgan fdOrganParam = new FdOrgan();
        fdOrganParam.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(fdOrganParam);
        //��ӱ���
        FdOrgan fdOrganCopy = (FdOrgan) BocoUtils.deepCopy(fdOrgan);
        fdOrganCopy.setOrganname(fdOrganCopy.getOrganname() + "����");
        fdOrganList.add(fdOrganCopy);

        for (FdOrgan organ : fdOrganList) {
            ProductReport organReport = new ProductReport();
            //idΪ������
            organReport.setId(organ.getThiscode());
            //parentΪ�ϼ������ţ�������Ϊ����������
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
