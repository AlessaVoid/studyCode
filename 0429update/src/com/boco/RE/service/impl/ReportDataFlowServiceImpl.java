package com.boco.RE.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.entity.DataFlowReport;
import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.ReportConstant;
import com.boco.RE.service.IReportService;
import com.boco.RE.service.ReportDataFlowService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.FdReportOrgan;
import com.boco.SYS.entity.TbRptBaseinfoLoankind;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.*;
import com.boco.TONY.biz.loancomb.exception.LoanCombDetailException;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportDataFlowServiceImpl implements ReportDataFlowService {
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
    private ReportCombDetailMapper reportCombDetailMapper;


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
    public List<DataFlowReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception {

        List<DataFlowReport> resultList = new ArrayList<>();

        /*--------��ȡ����list ���ܵ�����������----------*/
        List<TbRptBaseinfoLoankind> organDataList = getDataFlowOrganDataList(date, combType, fdorgan);

        /*---------��װ����---------------*/
        // �����Ի���Ϊ�����Ļ���list
        List<DataFlowReport> organReportList = getReportDataFlowOrganList(fdorgan);
        if (organReportList == null || organReportList.size() == 0) {
            return new ArrayList<>();
        }

        // ��װ��������
        buildDataFlowOrganList(organReportList, organDataList, combType, cycel, date);
        /*--����  ��������������--*/
        rankDataFlowOrganList(organReportList);

        // ����ά�ȣ�  1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-����
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            // ����

            //�����ͣ���λ�������������ֵ����Сֵ��
            addProductOtherList(organReportList, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);
            //ת��Ϊ�����б�
            resultList = DataFlowReport.transToTree(organReportList, 8, fdorgan.getThiscode(), ReportConstant.TREE_LEVEL_TWO);

        } else {
            // ����

            /*--------��ȡ����list ���ܵ�����----------*/
            List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindOfAreaList = getDataFlowAreaDataList(date, combType, dimension);
            /*---------��װ����---------------*/
            // ����������Ϊ�����Ļ���list,���Ҹı�organReportList��parentId
            List<DataFlowReport> areaReportList = getDataFlowAreaReportList(dimension, organReportList);
            if (areaReportList == null || areaReportList.size() == 0) {
                return new ArrayList<>();
            }

            // ��װ��������
            buildDataFlowOrganList(areaReportList, tbRptBaseinfoLoankindOfAreaList, combType, cycel, date);
            //����
            rankDataFlowOrganList(areaReportList);
            //�����ͣ���λ�������������ֵ����Сֵ��
            addProductOtherList(areaReportList, ReportConstant.AREA, ReportConstant.TREE_LEVEL_ONE);
            //�ѻ���List���뵽����List
            areaReportList.addAll(organReportList);
            //ת��Ϊ�����б�
            resultList = DataFlowReport.transToTree(areaReportList, 8, ReportConstant.AREA, ReportConstant.TREE_LEVEL_ONE);
        }

        return resultList;
    }

    /**
     * ����������
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
        List<DataFlowReport> data = getReportData(combType, date, cycel, fdorgan, dimension);

        int level = 5;
        List<DataFlowReport> reportList = DataFlowReport.treeTransTo(data, new ArrayList<DataFlowReport>(), level);

        /*---------д���ļ�---------*/
        //����
        String filename = "-" + date;
        if (ReportConstant.DIMENSION_ORGAN.equals(dimension)) {
            filename = "������-����" + filename;
        } else {
            filename = "������-����" + filename;
        }
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 44);
        POIExportUtil.setCell(sheet, 0, 0, filename, cellStyle);
        //��λ
        String amountUnit = "��Ԫ";
        if (ReportConstant.UNIT_2.equals(unit)) {
            amountUnit = "��Ԫ";
        }
        POIExportUtil.setCell(sheet, 1, 0, "��λ:" + amountUnit, cellStyle);

        //����
        setExcelTop(sheet, 2, 0, cellStyle);
        int row = 5;
        int column = 0;
        for (DataFlowReport report : reportList) {
            POIExportUtil.setCell(sheet, row, column++, report.getName(), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_11(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_11(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_11(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_11(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_12(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_12(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_12(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_12(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_21(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_21(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_21(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_21(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_22(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_22(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_22(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_22(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_23(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_23(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_23(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_23(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_24(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_24(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_24(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_24(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_31(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_31(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_31(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_31(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_32(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_32(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_32(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_32(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_33(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_33(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_33(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_33(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_41(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_41(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_41(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_41(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getOcc_42(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpire_42(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireNormal_42(), unit), cellStyle);
            POIExportUtil.setCell(sheet, row, column++, ReportService.getReportAmount(report.getExpireAdv_42(), unit), cellStyle);
            row++;
            column = 0;
        }

        //�����п�
        for (int i = 0; i < 45; i++) {
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

        POIExportUtil.CellRangeAddress(sheet, row, row+2, column, column);
        POIExportUtil.setCell(sheet, row, column++, "����" , cellStyle);
        /*��С����*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column+7);
        POIExportUtil.setCell(sheet, row, column, "��С����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "���˾�Ӫ�Դ���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "С��ҵ" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);

        /*����ʵ�����*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column+15);
        POIExportUtil.setCell(sheet, row, column, "����ʵ�����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "ס�����Ҵ���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "�������Ѵ���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "��Ӧ����ó������" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "��˾����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);

        /*Ʊ�ݸ���͢*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column+11);
        POIExportUtil.setCell(sheet, row, column, "Ʊ�ݸ���͢" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "ת��" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "ֱ��" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "����͢" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);

        /*��������*/
        POIExportUtil.CellRangeAddress(sheet, row, row, column, column+7);
        POIExportUtil.setCell(sheet, row, column, "��������" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "�������ÿ�͸֧" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);
        POIExportUtil.CellRangeAddress(sheet, row+1, row+1, column, column+3);
        POIExportUtil.setCell(sheet, row+1, column, "��ŷ���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "�·���" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "����ϼ�" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ͬ����" , cellStyle);
        POIExportUtil.setCell(sheet, row+2, column++, "��ǰ����" , cellStyle);

    }

    //��ȡ����list ���ܵ�����
    private List<TbRptBaseinfoLoankind> getDataFlowAreaDataList(String date, String combType, String dimension) {
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
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectDataFlowAreaData(param);
        if(tbRptBaseinfoLoankindList.size()<1){
            param.put("startDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
            tbRptBaseinfoLoankindList=tbRptBaseinfoLoankindMapper.selectDataFlowAreaDataFuture(param);
        }
        return tbRptBaseinfoLoankindList;
    }

    // ����������Ϊ�����Ļ���list,���Ҹı�organReportList��parentId
    private List<DataFlowReport> getDataFlowAreaReportList(String dimension, List<DataFlowReport> organReportList) {

        ArrayList<DataFlowReport> areaReportList = new ArrayList<>();

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
                DataFlowReport organReport = new DataFlowReport();
                //idΪ������
                organReport.setId(key);
                //parentΪ�ϼ������ţ����е�ҲΪ����
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type1Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setOrder(type1Map.get(key) == 0 ? Integer.MAX_VALUE : type1Map.get(key));
                areaReportList.add(organReport);
            }
            //����organReportList��parentId
            for (DataFlowReport productReport : organReportList) {
                for (FdReportOrgan fdReportOrgan : fdReportOrganList) {
                    if (productReport.getId().equals(fdReportOrgan.getOrgancode())) {
                        productReport.setParentId(ReportConstant.AREA + fdReportOrgan.getType1());
                        break;
                    }
                }
            }
        } else if (ReportConstant.DIMENSION_AREA_2.equals(dimension)) {
            for (String key : type2Map.keySet()) {
                DataFlowReport organReport = new DataFlowReport();
                //idΪ������
                organReport.setId(key);
                //parentΪ�ϼ������ţ����е�ҲΪ����
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type2Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setOrder(type2Map.get(key) == 0 ? Integer.MAX_VALUE : type2Map.get(key));
                areaReportList.add(organReport);
            }
            //����organReportList��parentId
            for (DataFlowReport productReport : organReportList) {
                for (FdReportOrgan fdReportOrgan : fdReportOrganList) {
                    if (productReport.getId().equals(fdReportOrgan.getOrgancode())) {
                        productReport.setParentId(ReportConstant.AREA + fdReportOrgan.getType2());
                        break;
                    }
                }
            }
        } else if (ReportConstant.DIMENSION_AREA_3.equals(dimension)) {
            for (String key : type3Map.keySet()) {
                DataFlowReport organReport = new DataFlowReport();
                //idΪ������
                organReport.setId(key);
                //parentΪ�ϼ������ţ����е�ҲΪ����
                organReport.setParentId(ReportConstant.AREA);
                organReport.setName(getAreaName(dimension, type3Map.get(key)));
                organReport.setLevel(ReportConstant.TREE_LEVEL_ONE);
                organReport.setOrder(type3Map.get(key) == 0 ? Integer.MAX_VALUE : type3Map.get(key));
                areaReportList.add(organReport);
            }
            //����organReportList��parentId
            for (DataFlowReport productReport : organReportList) {
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

    //����
    private void rankDataFlowOrganList(List<DataFlowReport> organReportList) {
        Collections.sort(organReportList, new Comparator<DataFlowReport>() {
            @Override
            public int compare(DataFlowReport o1, DataFlowReport o2) {
                //�Ӵ�С
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });

    }

    private void addProductOtherList(List<DataFlowReport> organReportList, String parentId, int level) {

        /*-----------����-------------*/

        /*1.1���˾�Ӫ�Դ���*/
        BigDecimal occ_11 = BigDecimal.ZERO;
        BigDecimal expire_11 = BigDecimal.ZERO;
        BigDecimal expireNormal_11 = BigDecimal.ZERO;
        BigDecimal expireAdv_11 = BigDecimal.ZERO;

        /*1.2С��ҵ*/
        BigDecimal occ_12 = BigDecimal.ZERO;
        BigDecimal expire_12 = BigDecimal.ZERO;
        BigDecimal expireNormal_12 = BigDecimal.ZERO;
        BigDecimal expireAdv_12 = BigDecimal.ZERO;
        /*2.1ס�����Ҵ���*/
        BigDecimal occ_21 = BigDecimal.ZERO;
        BigDecimal expire_21 = BigDecimal.ZERO;
        BigDecimal expireNormal_21 = BigDecimal.ZERO;
        BigDecimal expireAdv_21 = BigDecimal.ZERO;
        /*2.2�������Ѵ���*/
        BigDecimal occ_22 = BigDecimal.ZERO;
        BigDecimal expire_22 = BigDecimal.ZERO;
        BigDecimal expireNormal_22 = BigDecimal.ZERO;
        BigDecimal expireAdv_22 = BigDecimal.ZERO;
        /*2.3��Ӧ����ó������*/
        BigDecimal occ_23 = BigDecimal.ZERO;
        BigDecimal expire_23 = BigDecimal.ZERO;
        BigDecimal expireNormal_23 = BigDecimal.ZERO;
        BigDecimal expireAdv_23 = BigDecimal.ZERO;
        /*2.4��˾����*/
        BigDecimal occ_24 = BigDecimal.ZERO;
        BigDecimal expire_24 = BigDecimal.ZERO;
        BigDecimal expireNormal_24 = BigDecimal.ZERO;
        BigDecimal expireAdv_24 = BigDecimal.ZERO;
        /*3.1ת��*/
        BigDecimal occ_31 = BigDecimal.ZERO;
        BigDecimal expire_31 = BigDecimal.ZERO;
        BigDecimal expireNormal_31 = BigDecimal.ZERO;
        BigDecimal expireAdv_31 = BigDecimal.ZERO;
        /*3.2ֱ��*/
        BigDecimal occ_32 = BigDecimal.ZERO;
        BigDecimal expire_32 = BigDecimal.ZERO;
        BigDecimal expireNormal_32 = BigDecimal.ZERO;
        BigDecimal expireAdv_32 = BigDecimal.ZERO;
        /*3.3����͢*/
        BigDecimal occ_33 = BigDecimal.ZERO;
        BigDecimal expire_33 = BigDecimal.ZERO;
        BigDecimal expireNormal_33 = BigDecimal.ZERO;
        BigDecimal expireAdv_33 = BigDecimal.ZERO;
        /*4.1�������ÿ�͸֧*/
        BigDecimal occ_41 = BigDecimal.ZERO;
        BigDecimal expire_41 = BigDecimal.ZERO;
        BigDecimal expireNormal_41 = BigDecimal.ZERO;
        BigDecimal expireAdv_41 = BigDecimal.ZERO;
        /*4.2��ŷ���*/
        BigDecimal occ_42 = BigDecimal.ZERO;
        BigDecimal expire_42 = BigDecimal.ZERO;
        BigDecimal expireNormal_42 = BigDecimal.ZERO;
        BigDecimal expireAdv_42 = BigDecimal.ZERO;


        for (DataFlowReport report : organReportList) {
            occ_11 = BigDecimalUtil.add(occ_11, report.getOcc_11());
            expire_11 = BigDecimalUtil.add(expire_11, report.getExpire_11());
            expireNormal_11 = BigDecimalUtil.add(expireNormal_11, report.getExpireNormal_11());
            expireAdv_11 = BigDecimalUtil.add(expireAdv_11, report.getExpireAdv_11());

            occ_12 = BigDecimalUtil.add(occ_12, report.getOcc_12());
            expire_12 = BigDecimalUtil.add(expire_12, report.getExpire_12());
            expireNormal_12 = BigDecimalUtil.add(expireNormal_12, report.getExpireNormal_12());
            expireAdv_12 = BigDecimalUtil.add(expireAdv_12, report.getExpireAdv_12());

            occ_21 = BigDecimalUtil.add(occ_21, report.getOcc_21());
            expire_21 = BigDecimalUtil.add(expire_21, report.getExpire_21());
            expireNormal_21 = BigDecimalUtil.add(expireNormal_21, report.getExpireNormal_21());
            expireAdv_21 = BigDecimalUtil.add(expireAdv_21, report.getExpireAdv_21());

            occ_22 = BigDecimalUtil.add(occ_22, report.getOcc_22());
            expire_22 = BigDecimalUtil.add(expire_22, report.getExpire_22());
            expireNormal_22 = BigDecimalUtil.add(expireNormal_22, report.getExpireNormal_22());
            expireAdv_22 = BigDecimalUtil.add(expireAdv_22, report.getExpireAdv_22());

            occ_23 = BigDecimalUtil.add(occ_23, report.getOcc_23());
            expire_23 = BigDecimalUtil.add(expire_23, report.getExpire_23());
            expireNormal_23 = BigDecimalUtil.add(expireNormal_23, report.getExpireNormal_23());
            expireAdv_23 = BigDecimalUtil.add(expireAdv_23, report.getExpireAdv_23());

            occ_24 = BigDecimalUtil.add(occ_24, report.getOcc_24());
            expire_24 = BigDecimalUtil.add(expire_24, report.getExpire_24());
            expireNormal_24 = BigDecimalUtil.add(expireNormal_24, report.getExpireNormal_24());
            expireAdv_24 = BigDecimalUtil.add(expireAdv_24, report.getExpireAdv_24());

            occ_31 = BigDecimalUtil.add(occ_31, report.getOcc_31());
            expire_31 = BigDecimalUtil.add(expire_31, report.getExpire_31());
            expireNormal_31 = BigDecimalUtil.add(expireNormal_31, report.getExpireNormal_31());
            expireAdv_31 = BigDecimalUtil.add(expireAdv_31, report.getExpireAdv_31());

            occ_32 = BigDecimalUtil.add(occ_32, report.getOcc_32());
            expire_32 = BigDecimalUtil.add(expire_32, report.getExpire_32());
            expireNormal_32 = BigDecimalUtil.add(expireNormal_32, report.getExpireNormal_32());
            expireAdv_32 = BigDecimalUtil.add(expireAdv_32, report.getExpireAdv_32());

            occ_33 = BigDecimalUtil.add(occ_33, report.getOcc_33());
            expire_33 = BigDecimalUtil.add(expire_33, report.getExpire_33());
            expireNormal_33 = BigDecimalUtil.add(expireNormal_33, report.getExpireNormal_33());
            expireAdv_33 = BigDecimalUtil.add(expireAdv_33, report.getExpireAdv_33());

            occ_41 = BigDecimalUtil.add(occ_41, report.getOcc_41());
            expire_41 = BigDecimalUtil.add(expire_41, report.getExpire_41());
            expireNormal_41 = BigDecimalUtil.add(expireNormal_41, report.getExpireNormal_41());
            expireAdv_41 = BigDecimalUtil.add(expireAdv_41, report.getExpireAdv_41());

            occ_42 = BigDecimalUtil.add(occ_42, report.getOcc_42());
            expire_42 = BigDecimalUtil.add(expire_42, report.getExpire_42());
            expireNormal_42 = BigDecimalUtil.add(expireNormal_42, report.getExpireNormal_42());
            expireAdv_42 = BigDecimalUtil.add(expireAdv_42, report.getExpireAdv_42());

        }

        DataFlowReport productReportSum = new DataFlowReport();
        productReportSum.setId("sum");
        productReportSum.setParentId(parentId);
        productReportSum.setName("�ϼ�");
        productReportSum.setLevel(level);
        
        productReportSum.setOcc_11(occ_11);
        productReportSum.setExpire_11(expire_11);
        productReportSum.setExpireNormal_11(expireNormal_11);
        productReportSum.setExpireAdv_11(expireAdv_11);
        productReportSum.setOcc_12(occ_12);
        productReportSum.setExpire_12(expire_12);
        productReportSum.setExpireNormal_12(expireNormal_12);
        productReportSum.setExpireAdv_12(expireAdv_12);
        productReportSum.setOcc_21(occ_21);
        productReportSum.setExpire_21(expire_21);
        productReportSum.setExpireNormal_21(expireNormal_21);
        productReportSum.setExpireAdv_21(expireAdv_21);
        productReportSum.setOcc_22(occ_22);
        productReportSum.setExpire_22(expire_22);
        productReportSum.setExpireNormal_22(expireNormal_22);
        productReportSum.setExpireAdv_22(expireAdv_22);
        productReportSum.setOcc_23(occ_23);
        productReportSum.setExpire_23(expire_23);
        productReportSum.setExpireNormal_23(expireNormal_23);
        productReportSum.setExpireAdv_23(expireAdv_23);
        productReportSum.setOcc_24(occ_24);
        productReportSum.setExpire_24(expire_24);
        productReportSum.setExpireNormal_24(expireNormal_24);
        productReportSum.setExpireAdv_24(expireAdv_24);
        productReportSum.setOcc_31(occ_31);
        productReportSum.setExpire_31(expire_31);
        productReportSum.setExpireNormal_31(expireNormal_31);
        productReportSum.setExpireAdv_31(expireAdv_31);
        productReportSum.setOcc_32(occ_32);
        productReportSum.setExpire_32(expire_32);
        productReportSum.setExpireNormal_32(expireNormal_32);
        productReportSum.setExpireAdv_32(expireAdv_32);
        productReportSum.setOcc_33(occ_33);
        productReportSum.setExpire_33(expire_33);
        productReportSum.setExpireNormal_33(expireNormal_33);
        productReportSum.setExpireAdv_33(expireAdv_33);
        productReportSum.setOcc_41(occ_41);
        productReportSum.setExpire_41(expire_41);
        productReportSum.setExpireNormal_41(expireNormal_41);
        productReportSum.setExpireAdv_41(expireAdv_41);
        productReportSum.setOcc_42(occ_42);
        productReportSum.setExpire_42(expire_42);
        productReportSum.setExpireNormal_42(expireNormal_42);
        productReportSum.setExpireAdv_42(expireAdv_42);


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

    /**
     * ��װ��������
     *
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     */
    private void buildDataFlowOrganList(List<DataFlowReport> organReportList, List<TbRptBaseinfoLoankind> baseInfoList, String combType, String cycel, String date) {

        //���ݻ��� ���� �������� �ֱ�ƥ��
        for (DataFlowReport report : organReportList) {
            String organ = report.getId();
            for (TbRptBaseinfoLoankind info : baseInfoList) {

                if (organ.equals(info.getRptOrgan())) {
                    if (isTrueComb(ReportConstant.DATA_FLOW_COMB_11,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_11(BigDecimalUtil.add(report.getOcc_11(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_11(BigDecimalUtil.add(report.getExpire_11(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_11(BigDecimalUtil.add(report.getExpireNormal_11(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_11(BigDecimalUtil.add(report.getExpireAdv_11(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_11(BigDecimalUtil.add(report.getOcc_11(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_11(BigDecimalUtil.add(report.getExpire_11(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_11(BigDecimalUtil.add(report.getExpireNormal_11(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_11(BigDecimalUtil.add(report.getExpireAdv_11(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_11(BigDecimalUtil.add(report.getOcc_11(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_11(BigDecimalUtil.add(report.getExpire_11(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_11(BigDecimalUtil.add(report.getExpireNormal_11(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_11(BigDecimalUtil.add(report.getExpireAdv_11(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_11(BigDecimalUtil.add(report.getOcc_11(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_11(BigDecimalUtil.add(report.getExpire_11(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_11(BigDecimalUtil.add(report.getExpireNormal_11(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_11(BigDecimalUtil.add(report.getExpireAdv_11(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                        //��   Ѯ
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_12,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_12(BigDecimalUtil.add(report.getOcc_12(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_12(BigDecimalUtil.add(report.getExpire_12(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_12(BigDecimalUtil.add(report.getExpireNormal_12(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_12(BigDecimalUtil.add(report.getExpireAdv_12(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_12(BigDecimalUtil.add(report.getOcc_12(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_12(BigDecimalUtil.add(report.getExpire_12(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_12(BigDecimalUtil.add(report.getExpireNormal_12(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_12(BigDecimalUtil.add(report.getExpireAdv_12(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_12(BigDecimalUtil.add(report.getOcc_12(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_12(BigDecimalUtil.add(report.getExpire_12(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_12(BigDecimalUtil.add(report.getExpireNormal_12(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_12(BigDecimalUtil.add(report.getExpireAdv_12(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_12(BigDecimalUtil.add(report.getOcc_12(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_12(BigDecimalUtil.add(report.getExpire_12(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_12(BigDecimalUtil.add(report.getExpireNormal_12(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_12(BigDecimalUtil.add(report.getExpireAdv_12(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_21,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_21(BigDecimalUtil.add(report.getOcc_21(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_21(BigDecimalUtil.add(report.getExpire_21(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_21(BigDecimalUtil.add(report.getExpireNormal_21(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_21(BigDecimalUtil.add(report.getExpireAdv_21(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_21(BigDecimalUtil.add(report.getOcc_21(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_21(BigDecimalUtil.add(report.getExpire_21(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_21(BigDecimalUtil.add(report.getExpireNormal_21(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_21(BigDecimalUtil.add(report.getExpireAdv_21(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_21(BigDecimalUtil.add(report.getOcc_21(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_21(BigDecimalUtil.add(report.getExpire_21(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_21(BigDecimalUtil.add(report.getExpireNormal_21(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_21(BigDecimalUtil.add(report.getExpireAdv_21(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_21(BigDecimalUtil.add(report.getOcc_21(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_21(BigDecimalUtil.add(report.getExpire_21(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_21(BigDecimalUtil.add(report.getExpireNormal_21(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_21(BigDecimalUtil.add(report.getExpireAdv_21(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_22,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_22(BigDecimalUtil.add(report.getOcc_22(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_22(BigDecimalUtil.add(report.getExpire_22(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_22(BigDecimalUtil.add(report.getExpireNormal_22(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_22(BigDecimalUtil.add(report.getExpireAdv_22(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_22(BigDecimalUtil.add(report.getOcc_22(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_22(BigDecimalUtil.add(report.getExpire_22(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_22(BigDecimalUtil.add(report.getExpireNormal_22(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_22(BigDecimalUtil.add(report.getExpireAdv_22(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_22(BigDecimalUtil.add(report.getOcc_22(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_22(BigDecimalUtil.add(report.getExpire_22(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_22(BigDecimalUtil.add(report.getExpireNormal_22(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_22(BigDecimalUtil.add(report.getExpireAdv_22(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_22(BigDecimalUtil.add(report.getOcc_22(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_22(BigDecimalUtil.add(report.getExpire_22(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_22(BigDecimalUtil.add(report.getExpireNormal_22(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_22(BigDecimalUtil.add(report.getExpireAdv_22(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_23,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_23(BigDecimalUtil.add(report.getOcc_23(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_23(BigDecimalUtil.add(report.getExpire_23(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_23(BigDecimalUtil.add(report.getExpireNormal_23(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_23(BigDecimalUtil.add(report.getExpireAdv_23(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_23(BigDecimalUtil.add(report.getOcc_23(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_23(BigDecimalUtil.add(report.getExpire_23(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_23(BigDecimalUtil.add(report.getExpireNormal_23(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_23(BigDecimalUtil.add(report.getExpireAdv_23(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_23(BigDecimalUtil.add(report.getOcc_23(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_23(BigDecimalUtil.add(report.getExpire_23(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_23(BigDecimalUtil.add(report.getExpireNormal_23(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_23(BigDecimalUtil.add(report.getExpireAdv_23(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_23(BigDecimalUtil.add(report.getOcc_23(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_23(BigDecimalUtil.add(report.getExpire_23(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_23(BigDecimalUtil.add(report.getExpireNormal_23(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_23(BigDecimalUtil.add(report.getExpireAdv_23(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_24,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_24(BigDecimalUtil.add(report.getOcc_24(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_24(BigDecimalUtil.add(report.getExpire_24(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_24(BigDecimalUtil.add(report.getExpireNormal_24(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_24(BigDecimalUtil.add(report.getExpireAdv_24(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_24(BigDecimalUtil.add(report.getOcc_24(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_24(BigDecimalUtil.add(report.getExpire_24(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_24(BigDecimalUtil.add(report.getExpireNormal_24(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_24(BigDecimalUtil.add(report.getExpireAdv_24(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_24(BigDecimalUtil.add(report.getOcc_24(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_24(BigDecimalUtil.add(report.getExpire_24(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_24(BigDecimalUtil.add(report.getExpireNormal_24(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_24(BigDecimalUtil.add(report.getExpireAdv_24(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_24(BigDecimalUtil.add(report.getOcc_24(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_24(BigDecimalUtil.add(report.getExpire_24(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_24(BigDecimalUtil.add(report.getExpireNormal_24(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_24(BigDecimalUtil.add(report.getExpireAdv_24(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_31,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_31(BigDecimalUtil.add(report.getOcc_31(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_31(BigDecimalUtil.add(report.getExpire_31(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_31(BigDecimalUtil.add(report.getExpireNormal_31(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_31(BigDecimalUtil.add(report.getExpireAdv_31(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_31(BigDecimalUtil.add(report.getOcc_31(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_31(BigDecimalUtil.add(report.getExpire_31(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_31(BigDecimalUtil.add(report.getExpireNormal_31(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_31(BigDecimalUtil.add(report.getExpireAdv_31(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_31(BigDecimalUtil.add(report.getOcc_31(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_31(BigDecimalUtil.add(report.getExpire_31(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_31(BigDecimalUtil.add(report.getExpireNormal_31(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_31(BigDecimalUtil.add(report.getExpireAdv_31(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_31(BigDecimalUtil.add(report.getOcc_31(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_31(BigDecimalUtil.add(report.getExpire_31(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_31(BigDecimalUtil.add(report.getExpireNormal_31(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_31(BigDecimalUtil.add(report.getExpireAdv_31(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_32,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_32(BigDecimalUtil.add(report.getOcc_32(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_32(BigDecimalUtil.add(report.getExpire_32(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_32(BigDecimalUtil.add(report.getExpireNormal_32(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_32(BigDecimalUtil.add(report.getExpireAdv_32(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_32(BigDecimalUtil.add(report.getOcc_32(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_32(BigDecimalUtil.add(report.getExpire_32(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_32(BigDecimalUtil.add(report.getExpireNormal_32(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_32(BigDecimalUtil.add(report.getExpireAdv_32(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_32(BigDecimalUtil.add(report.getOcc_32(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_32(BigDecimalUtil.add(report.getExpire_32(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_32(BigDecimalUtil.add(report.getExpireNormal_32(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_32(BigDecimalUtil.add(report.getExpireAdv_32(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_32(BigDecimalUtil.add(report.getOcc_32(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_32(BigDecimalUtil.add(report.getExpire_32(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_32(BigDecimalUtil.add(report.getExpireNormal_32(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_32(BigDecimalUtil.add(report.getExpireAdv_32(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_33,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_33(BigDecimalUtil.add(report.getOcc_33(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_33(BigDecimalUtil.add(report.getExpire_33(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_33(BigDecimalUtil.add(report.getExpireNormal_33(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_33(BigDecimalUtil.add(report.getExpireAdv_33(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_33(BigDecimalUtil.add(report.getOcc_33(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_33(BigDecimalUtil.add(report.getExpire_33(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_33(BigDecimalUtil.add(report.getExpireNormal_33(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_33(BigDecimalUtil.add(report.getExpireAdv_33(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_33(BigDecimalUtil.add(report.getOcc_33(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_33(BigDecimalUtil.add(report.getExpire_33(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_33(BigDecimalUtil.add(report.getExpireNormal_33(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_33(BigDecimalUtil.add(report.getExpireAdv_33(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_33(BigDecimalUtil.add(report.getOcc_33(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_33(BigDecimalUtil.add(report.getExpire_33(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_33(BigDecimalUtil.add(report.getExpireNormal_33(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_33(BigDecimalUtil.add(report.getExpireAdv_33(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_41,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_41(BigDecimalUtil.add(report.getOcc_41(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_41(BigDecimalUtil.add(report.getExpire_41(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_41(BigDecimalUtil.add(report.getExpireNormal_41(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_41(BigDecimalUtil.add(report.getExpireAdv_41(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_41(BigDecimalUtil.add(report.getOcc_41(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_41(BigDecimalUtil.add(report.getExpire_41(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_41(BigDecimalUtil.add(report.getExpireNormal_41(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_41(BigDecimalUtil.add(report.getExpireAdv_41(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_41(BigDecimalUtil.add(report.getOcc_41(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_41(BigDecimalUtil.add(report.getExpire_41(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_41(BigDecimalUtil.add(report.getExpireNormal_41(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_41(BigDecimalUtil.add(report.getExpireAdv_41(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_41(BigDecimalUtil.add(report.getOcc_41(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_41(BigDecimalUtil.add(report.getExpire_41(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_41(BigDecimalUtil.add(report.getExpireNormal_41(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_41(BigDecimalUtil.add(report.getExpireAdv_41(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    } else if (isTrueComb(ReportConstant.DATA_FLOW_COMB_42,info.getLoanKind())) {
                        if (ReportConstant.CYCEL_YEAR.equals(cycel)) {
                            report.setOcc_42(BigDecimalUtil.add(report.getOcc_42(), BigDecimalUtil.divide(info.getYearOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_42(BigDecimalUtil.add(report.getExpire_42(), BigDecimalUtil.divide(info.getYearExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_42(BigDecimalUtil.add(report.getExpireNormal_42(), BigDecimalUtil.divide(info.getYearExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_42(BigDecimalUtil.add(report.getExpireAdv_42(), BigDecimalUtil.divide(info.getYearExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_SEASON.equals(cycel)) {
                            report.setOcc_42(BigDecimalUtil.add(report.getOcc_42(), BigDecimalUtil.divide(info.getSeasonOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_42(BigDecimalUtil.add(report.getExpire_42(), BigDecimalUtil.divide(info.getSeasonExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_42(BigDecimalUtil.add(report.getExpireNormal_42(), BigDecimalUtil.divide(info.getSeasonExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_42(BigDecimalUtil.add(report.getExpireAdv_42(), BigDecimalUtil.divide(info.getSeasonExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_MONTH.equals(cycel)) {
                            report.setOcc_42(BigDecimalUtil.add(report.getOcc_42(), BigDecimalUtil.divide(info.getMonthOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_42(BigDecimalUtil.add(report.getExpire_42(), BigDecimalUtil.divide(info.getMonthExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_42(BigDecimalUtil.add(report.getExpireNormal_42(), BigDecimalUtil.divide(info.getMonthExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_42(BigDecimalUtil.add(report.getExpireAdv_42(), BigDecimalUtil.divide(info.getMonthExpireAdv(), ReportConstant.MONEY_NUM)));
                        } else if (ReportConstant.CYCEL_DAY.equals(cycel)) {
                            report.setOcc_42(BigDecimalUtil.add(report.getOcc_42(), BigDecimalUtil.divide(info.getDayOcc(), ReportConstant.MONEY_NUM)));
                            report.setExpire_42(BigDecimalUtil.add(report.getExpire_42(), BigDecimalUtil.divide(info.getDayExpire(), ReportConstant.MONEY_NUM)));
                            report.setExpireNormal_42(BigDecimalUtil.add(report.getExpireNormal_42(), BigDecimalUtil.divide(info.getDayExpireNormal(), ReportConstant.MONEY_NUM)));
                            report.setExpireAdv_42(BigDecimalUtil.add(report.getExpireAdv_42(), BigDecimalUtil.divide(info.getDayExpireAdv(), ReportConstant.MONEY_NUM)));
                        }
                    }
                }
            }
        }

    }

    /**
     * �ж�Ŀ������Ƿ����ڵ�ǰ����
     * @param comb ��ǰ����
     * @param indexComb Ŀ�����
     * @return
     */
    private boolean isTrueComb(String comb,String indexComb) {
        String combs = DicCache.getNameByKey_(comb, "DATA_FLOW_COMB");
        String[] combList = combs.split(",");
        for (String c : combList) {
            if (c.equals(indexComb)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��ȡ����list ���ܵ�����������
     *
     * @param date     yyyymmdd
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param fdorgan  ����
     * @return
     */
    private List<TbRptBaseinfoLoankind> getDataFlowOrganDataList(String date, String combType, FdOrgan fdorgan) {
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
        if (Constant.ORGAN_LEVEL_0.equals(fdorgan.getOrganlevel())) {//���в�һ��
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectDataFlowLevelOneOrgan(param);
            if( tbRptBaseinfoLoankindList.size()<1) {
                param.put("startDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
                tbRptBaseinfoLoankindList=tbRptBaseinfoLoankindMapper.selectDataFlowLevelOneOrganFuture(param);
            }
        } else if (Constant.ORGAN_LEVEL_1.equals(fdorgan.getOrganlevel())) {//һ�ֲ����
            param.put("organ", fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectDataFlowLevelOtherOrgan(param);
            if( tbRptBaseinfoLoankindList.size()<1) {
                param.put("startDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
                tbRptBaseinfoLoankindList=tbRptBaseinfoLoankindMapper.selectDataFlowLevelOtherOrganFuture(param);
            }
        }
        //......

        return tbRptBaseinfoLoankindList;
    }

    //�����Ի���Ϊ�����Ļ���list
    private List<DataFlowReport> getReportDataFlowOrganList(FdOrgan fdOrgan) throws Exception {
        ArrayList<DataFlowReport> organReportList = new ArrayList<>();
        FdOrgan fdOrganParam = new FdOrgan();
        fdOrganParam.setUporgan(fdOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(fdOrganParam);
        //��ӱ���
        FdOrgan fdOrganCopy = (FdOrgan) BocoUtils.deepCopy(fdOrgan);
        fdOrganCopy.setOrganname(fdOrganCopy.getOrganname() + "����");
        fdOrganList.add(fdOrganCopy);

        for (FdOrgan organ : fdOrganList) {
            DataFlowReport organReport = new DataFlowReport();
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
