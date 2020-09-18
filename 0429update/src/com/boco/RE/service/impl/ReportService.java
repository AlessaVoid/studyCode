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
 * ����ͳ��Service
 *
 * @Author zhuhongjiang
 * @Date 2020/3/13 ����9:25
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

    //����һ������map
    private static HashMap<String, String> threeToOneMap = new HashMap<>();
    //������������map
    private static HashMap<String, String> threeToTwoMap = new HashMap<>();

    /**
     * ��ѯ�¼���������
     *
     * @param thiscode
     * @return
     */
    @Override
    public List<FdOrgan> selectSubOrgan(String thiscode, String organlevel) {
        List<FdOrgan> subList = new ArrayList<>();

        if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
            //���в�һ��
            FdOrgan thisOrgan = fdOrganMapper.selectByPK(thiscode);
            thisOrgan.setOrganname("����");
            subList.add(thisOrgan);

            FdOrgan searchOrgan = new FdOrgan();
            searchOrgan.setUporgan(thiscode);
            subList.addAll(fdOrganMapper.selectByAttr2(searchOrgan));

        } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
            //һ�ֲ����
            FdOrgan thisOrgan = fdOrganMapper.selectByPK(thiscode);
            subList.add(thisOrgan);

            FdOrgan searchOrgan2 = new FdOrgan();
            searchOrgan2.setUporgan(thiscode);
            subList.addAll(fdOrganMapper.selectByAttr2(searchOrgan2));
        }

        return subList;
    }

    /**
     * ��ѯ�Ŵ��ձ���Ϣ����������������
     *
     * @param rptDate    ͳ�����ڣ�yyyyMMdd��
     * @param organlevel ��������0���С�1һ�����У�
     * @param organCode  ��������
     * @param type
     * @return
     */
    @Override
    public List<Map<String, Object>> getReportCreditDailyInfo(String rptDate, String organlevel, String organCode, int type) {
        List<Map<String, Object>> resultList = new ArrayList<>();


        if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
            /** <<<<<<<<<<<<<<<<<<<<---------------���е�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("rptDate", rptDate);
            resultList = tbRptBaseinfoMapper.selectForReportOrganOne(paramMap);
        } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
            /** <<<<<<<<<<<<<<<<<<<<---------------һ�����е�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("rptDate", rptDate);
            paramMap.put("organCode", organCode);
            resultList = tbRptBaseinfoMapper.selectForReportOrganTwo(paramMap);
        } else {
            throw new RuntimeException("��ǰ��������֧�ֱ����ѯ��");
        }

        //���⴦���Ŵ���ģ�ձ���Ĳ�ŷ���
        if (type == 1) {

            // ��ѯ��ŷ�������code
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("dicNo", "OTHER_COMB_CODE");
            List<GfDict> dictList = gfDictMapper.findByValINKeys(paramMap);
            // ���⴦��
            for (GfDict gfDict : dictList) {
                String dictKey = gfDict.getDictKeyIn();
                for (Map<String, Object> map : resultList) {
                    if (dictKey.equals(map.get("loanKind"))) {
                        map.put("balance", BigDecimalUtil.add(map.get("balance").toString(), map.get("dayLimit").toString()));
                    }
                }
            }
        }

        //�ϼ�
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
     * �Ŵ���ģ�ձ�������
     *  @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate ͳ������
     */
    @Override
    public void reportCreditScaleDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception {
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


        /*---------д���ļ�---------*/

        String filename = "�Ŵ���ģ�ձ���-"+rptDate;
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 32);
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //��λ
        POIExportUtil.setCell(sheet, 1, 0, "��λ:��Ԫ" , cellStyle);


        //����
        //���ô���
        int combColumn = 1;
        HashMap<String, Integer> combMap = new HashMap<>();
        for (Map<String, Object> map : combList) {
            POIExportUtil.setCell(sheet, 2, combColumn, map.get("combname").toString() , cellStyle);
            combMap.put(map.get("combcode").toString(), combColumn);
            combColumn++;
        }
        //���û���
        int organRow = 3;
        HashMap<String, Integer> organMap = new HashMap<>();
        for (FdOrgan fdOrgan : organList) {
            POIExportUtil.setCell(sheet, organRow, 0, fdOrgan.getOrganname() , cellStyle);
            organMap.put(fdOrgan.getThiscode(), organRow);
            organRow++;
        }
        //��������
        for (String organ : organMap.keySet()) {
            Integer row = organMap.get(organ);
            for (String comb : combMap.keySet()) {
                Integer column = combMap.get(comb);
                POIExportUtil.setCell(sheet, row, column, BigDecimalUtil.divide(getSafeCount(baseInfoMap.get(organ+"_"+comb)),new BigDecimal("10000")) , cellStyle);
            }
        }

        //�����п�
        for (int i = 0; i <combMap.size()+1; i++) {
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

    /**
     * �Ŵ��������ձ�������
     * @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate
     */
    @Override
    public void reportCreditMaturityDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception{
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


        /*---------д���ļ�---------*/

        String filename = "�Ŵ��������ձ���-"+rptDate;
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, 32);
        POIExportUtil.setCell(sheet, 0, 0, filename , cellStyle);
        //��λ
        POIExportUtil.setCell(sheet, 1, 0, "��λ:��Ԫ" , cellStyle);


        //����
        //���ô���
        int combColumn = 1;
        HashMap<String, Integer> combMap = new HashMap<>();
        for (Map<String, Object> map : combList) {
            POIExportUtil.setCell(sheet, 2, combColumn, map.get("combname").toString() , cellStyle);
            combMap.put(map.get("combcode").toString(), combColumn);
            combColumn++;
        }
        //���û���
        int organRow = 3;
        HashMap<String, Integer> organMap = new HashMap<>();
        for (FdOrgan fdOrgan : organList) {
            POIExportUtil.setCell(sheet, organRow, 0, fdOrgan.getOrganname() , cellStyle);
            organMap.put(fdOrgan.getThiscode(), organRow);
            organRow++;
        }
        //��������
        for (String organ : organMap.keySet()) {
            Integer row = organMap.get(organ);
            for (String comb : combMap.keySet()) {
                Integer column = combMap.get(comb);
                POIExportUtil.setCell(sheet, row, column, BigDecimalUtil.divide(getSafeCount(baseInfoMap.get(organ+"_"+comb)),new BigDecimal("10000")) , cellStyle);
            }
        }

        //�����п�
        for (int i = 0; i <combMap.size()+1 ; i++) {
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

    /**
     * ��ҳ - ��ȡ�������¾�������Ϣ������ͼ��
     *
     * @param organlevel
     * @param organCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexOrganBarInfo(String organlevel, String organCode, String organName, String operCode) throws Exception {
        String month = sdf.format(new Date());
        List<Map<String, Object>> resultListAll = new ArrayList<>(); //���ϼ�
        List<Map<String, Object>> resultList = new ArrayList<>(); //�����ϼ�

        try {
            //�жϵ�ǰ��Ա�Ƿ�����������
            List<Map<String, Object>> operLineList = tbRptBaseinfoMapper.selectOperLine(operCode);

            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------���е�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
                if (operLineList != null && !operLineList.isEmpty()) {
                    //���б���/����һ��
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

                //������������
                for (Map<String, Object> resultMap : resultList) {
                    if (Constant.HEAD_OFFICE_CODE.equals(resultMap.get("organcode"))) {
                        resultMap.put("organname", "����");
                        break;
                    }
                }

                //�ϼ�
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                //�ϼ�-�����ϼ� �������ֻ�ƶ������߼ƻ����ϼƾ�չʾ���ߵ�
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
                result.put("organname", "�����ϼ�");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                result.put("planamount", planamount);
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------һ�ֵ�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
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

                //������ӱ����ֶ�
                for (Map<String, Object> resultMap : resultList) {
                    if (organCode.equals(resultMap.get("organcode"))) {
                        resultMap.put("organname", resultMap.get("organname") + "����");
                        break;
                    }
                }


                //����
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                BigDecimal planamount = new BigDecimal(planamountCollect.getSum() + "");
                if (planamount.compareTo(new BigDecimal("0")) == 0) {
                    //-----����-�ƻ�������  �������δ�ƶ��ƻ�����ô����ֵ��ʾ�ϼ����������ƶ��ļƻ�������ƶ������߼ƻ����������߼ƻ�ֵ-----
                    TbPlan tbPlan = new TbPlan();
                    tbPlan.setPlanMonth(month);
                    tbPlan.setPlanOrgan(organCode);
                    tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                    List<TbPlan> tbPlanList = tbPlanMapper.selectByAttr(tbPlan);
                    if (tbPlanList == null || tbPlanList.size() == 0) {
                        //���� �� ���� ��û���ƶ� ��ѯ�ϼ������ƶ��ļƻ�
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
                            // �ƶ��˻����ƻ�����������
                        } else {
                            // �ƶ������߼ƻ� ����ֵΪ���߼ƻ�����ֵ
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
                result.put("organname", "�����ϼ�");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                result.put("planamount", planamount);
                resultListAll.add(result);
                resultListAll.addAll(resultList);
            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------���ֻ�������¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
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

                //ɾ������������ֻ���µ�ǰ��¼����
                Iterator<Map<String, Object>> iterator = resultList.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> next = iterator.next();
                    if (!organCode.equals(next.get("organcode").toString())) {
                        iterator.remove();
                    }
                }

                //������ӱ����ֶ�
                for (Map<String, Object> resultMap : resultList) {
                    if (organCode.equals(resultMap.get("organcode"))) {
                        resultMap.put("organname", resultMap.get("organname") + "����");
                        break;
                    }
                }

                resultListAll.addAll(resultList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("��ҳ - ��ȡ�������¾�������Ϣ", e);
            throw e;
        }
        return resultListAll;
    }

    /**
     * ��ҳ - �������ֱ��¾�����������ͼ��
     *
     * @param organlevel
     * @param organCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexCombBarInfo(String organlevel, String organCode, String operCode) throws Exception {
        String month = sdf.format(new Date());
        List<Map<String, Object>> resultListAll = new ArrayList<>(); //���ϼ�
        List<Map<String, Object>> resultList = new ArrayList<>(); //�����ϼ�
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

                //�ϼ�
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                Map<String, Object> result = new HashMap<>();
                result.put("combname", "���ֺϼ�");
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

                //�ϼ�
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                Map<String, Object> result = new HashMap<>();
                result.put("combname", "���ֺϼ�");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("planamount", planamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------���ֻ�������¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
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

                //�ϼ�
                DoubleSummaryStatistics realityamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("realityamount")))));
                DoubleSummaryStatistics planamountCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("planamount")))));
                DoubleSummaryStatistics peinprogressCollect = resultList.stream().collect(Collectors.summarizingDouble(d -> Double.valueOf(String.valueOf(d.get("peinprogress")))));
                Map<String, Object> result = new HashMap<>();
                result.put("combname", "���ֺϼ�");
                result.put("realityamount", realityamountCollect.getSum());
                result.put("planamount", planamountCollect.getSum());
                result.put("peinprogress", peinprogressCollect.getSum());
                resultListAll.add(result);
                resultListAll.addAll(resultList);

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("��ҳ - �������ֱ��¾�����", e);
            throw e;
        }
        return resultListAll;
    }

    /**
     * ��ҳ - �������ֱ��¼ƻ�����ʣ�����ͼ��
     *
     * @param organlevel
     * @param organCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getIndexCombLineInfo(String organlevel, String organCode, String operCode) throws Exception {
        String month = sdf.format(new Date());
        List<Map<String, Object>> resultList = new ArrayList<>(); //�����ϼ�

        try {
            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;

                //�ƻ�������
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoForPlanOne(paramMap);

                //ʵ�ʾ�����
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


                //�ƻ�������
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoForPlanTwo(paramMap);

                //ʵ�ʾ�����
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
                /** <<<<<<<<<<<<<<<<<<<<---------------���ֻ�������¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
                TbPlan tbPlan = new TbPlan();
                tbPlan.setPlanMonth(month);
                tbPlan.setPlanOrgan(organCode);
                tbPlan.setPlanType(2);
                tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
                List<TbPlan> planList = tbPlanMapper.selectByAttr(tbPlan);
                Integer planType = (planList != null && !planList.isEmpty()) ? 2 : 1;


                //�ƻ�������
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("planOrganCode", WebContextUtil.getSessionOrgan().getUporgan());
                paramMap.put("organCode", organCode);
                paramMap.put("month", month);
                paramMap.put("operCode", operCode);
                paramMap.put("planType", planType);
                resultList = tbRptBaseinfoMapper.selectIndexCombBarInfoForPlanThree(paramMap);

                //ʵ�ʾ�����
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
            log.error("��ҳ - �������ֱ��¼ƻ������", e);
            throw e;
        }
        return resultList;
    }

    /**
     * ��ҳ - Ԥ���������
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

            //�жϵ�ǰ��Ա�Ƿ�����������
            boolean isExistLine = false;
            List<Map<String, Object>> operLineList = tbRptBaseinfoMapper.selectOperLine(operCode);
            if (operLineList != null && !operLineList.isEmpty()) {
                isExistLine = true;
            }

            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------���е�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */

                // 2�����ֻ����ƻ����ȼ���ߣ����߼ƻ���1�����ֻ����ƻ����ȼ����
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
                /** <<<<<<<<<<<<<<<<<<<<---------------һ�ֵ�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
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
                /** <<<<<<<<<<<<<<<<<<<<---------------���ֻ�������¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */

                //����ֻ�鿴һ�ָ��Լ��ƶ��Ļ����ƻ�
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
            log.error("��ҳ - Ԥ���������", e);
            throw e;
        }
        return resultList;
    }

    /**
     * ��ҳ - Ԥ���߾���ֵ
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

            //�жϵ�ǰ��Ա�Ƿ�����������
            boolean isExistLine = false;
            List<Map<String, Object>> operLineList = tbRptBaseinfoMapper.selectOperLine(operCode);
            if (operLineList != null && !operLineList.isEmpty()) {
                isExistLine = true;
            }

            if (Constant.ORGAN_LEVEL_0.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------���е�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 0);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnAbsOne(paramMap);
                resultList.addAll(result);

            } else if (Constant.ORGAN_LEVEL_1.equals(organlevel)) {
                /** <<<<<<<<<<<<<<<<<<<<---------------һ�ֵ�¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("warnType", 0);
                paramMap.put("loginOrganCode", organCode);
                paramMap.put("month", month);
                paramMap.put("isExistLine", isExistLine);
                paramMap.put("operCode", operCode);

                List<Map<String, Object>> result = tbRptBaseinfoMapper.selectIndexWarnAbsTwo(paramMap);
                resultList.addAll(result);
            } else {
                /** <<<<<<<<<<<<<<<<<<<<---------------���ֻ�������¼��ѯ------------>>>>>>>>>>>>>>>>>>>>>>>> */
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
            log.error("��ҳ - Ԥ���߾���ֵ", e);
            throw e;
        }
        return resultList;
    }

    /*һ���������ζ�ѡ�б�*/
    @Override
    public String combLevelOneTreeList() {

        HashMap<String, Object> resultMap = new HashMap<>();

        //��ѯһ������
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("combLevel", 1);
        List<Map<String, Object>> combList = reportCombMapper.selectComb(paramMap);

        //��װ���ζ�ѡ
        ArrayList<HashMap<String, String>> treeMapList = new ArrayList<>();
        HashMap<String, String> faMap = new HashMap<>();
        faMap.put("id", "father");
        faMap.put("name", "ȫѡ");
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

    /*ͨ�����ּ����һ������ ���������һ�����ָü�������д���*/


    /**
     * ��ѯ��������
     *
     * @param prodectReportList list
     * @param date              ��������  yyyymmdd
     * @param cycel             ������ 1-�� 2-�� 3-�� 4-��
     * @param fdorgan           ������
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
        //��map�б���ȡֵ set�� list��
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
     * ��ѯ������������
     *
     * @param prodectReportList list
     * @param date              ��������  yyyymmdd
     * @param cycel             ������ 1-�� 2-�� 3-�� 4-��
     * @param fdorgan           ������
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
        //��map�б���ȡֵ set�� list��
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
     * ���ݾ������� ���������͵õ�  �·��б�
     *
     * @param date  ��������  yyyymmdd
     * @param cycel ������ 1-�� 2-�� 3-�� 4-��
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
     * ��ȡȥ��Ľ���
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
     * ����list
     * �����ֶ������������
     *
     * @param productReportList
     */
    public static void rankProductReportList(List<ProductReport> productReportList) {
        //�����ڻ��������ֶ�
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getCurrent_increase_req_organ().compareTo(o1.getCurrent_increase_req_organ());
            }
        });
        int i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_req_rank(i);
            i++;
        }

        //���ھ�������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getCurrent_increase_num().compareTo(o1.getCurrent_increase_num());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_rank(i);
            i++;
        }

        //��������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getCurrent_increase_num_growth_rate().compareTo(o1.getCurrent_increase_num_growth_rate());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_increase_num_growth_rate_rank(i);
            i++;
        }

        //�������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getBalance().compareTo(o1.getBalance());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setBalanceRank(i);
            i++;
        }

        // ���ڷ���������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getOcc().compareTo(o1.getOcc());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setOccRank(i);
            i++;
        }

        //���ڼƻ������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getCurrent_plan_completion_rate().compareTo(o1.getCurrent_plan_completion_rate());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_plan_completion_rate_rank(i);
            i++;
        }

        //����Ԥ�Ƶ���������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getExpireEstimate().compareTo(o1.getExpireEstimate());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setExpireEstimateRank(i);
            i++;
        }

        //�����ѵ���������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getExpire().compareTo(o1.getExpire());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setExpireRank(i);
            i++;
        }

        //����ʣ��Ԥ�Ƶ���������
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o2.getExpireEstimateLeft().compareTo(o1.getExpireEstimateLeft());
            }
        });
        i = 1;
        for (ProductReport tempPr : productReportList) {
            tempPr.setExpireEstimateLeftRank(i);
            i++;
        }


        //�����������ֶ�
        Collections.sort(productReportList, new Comparator<ProductReport>() {
            @Override
            public int compare(ProductReport o1, ProductReport o2) {
                //�Ӵ�С
                return o1.getCombOrder().compareTo(o2.getCombOrder());
            }
        });
    }


    /**
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     ��������  yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan  ��ǰ��¼����
     * @return
     */
    @Override
    public List<ProductReport> getProductReportData(String combType, String date, String cycel, FdOrgan fdorgan) {

        initComb();

        /*--------��ȡ����list ���ܵ����� ----------*/
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getCombDataList(date, combType, fdorgan);


        /*---------��װ����----------*/
        // �����Դ���Ϊ�����Ļ���list
        List<ProductReport> productReportList = getProductReportListBycomb(combType);

        if (productReportList == null || productReportList.size() == 0) {
            return new ArrayList<>();
        }

        //����һ������list
        List<ProductReport> productReportListCopy = copyProductList(productReportList);
        // ��� ���ռ��
        getProductBalance(productReportList, tbRptBaseinfoLoankindList);
        // ���ռ�Ƚ��ڳ�
        getProductbalanceCompareDateBegin(productReportList, productReportListCopy, date, cycel, combType, fdorgan, tbRptBaseinfoLoankindList);
        // ���ھ�������(����)
        getCurrentIncreaseReqLine(productReportList, date, cycel, fdorgan);
        // ���ھ�������(����)
        getCurrentIncreaseReqOrgan(productReportList, date, cycel, fdorgan);
        // ���ڼƻ�
        getProductPlanAmount(productReportList, combType, date, cycel, fdorgan);
        // ���վ���
        getTodayIncreaseNum(productReportList, tbRptBaseinfoLoankindList);
        // ���ھ���
        getCurrentIncreaseNum(productReportList, tbRptBaseinfoLoankindList, cycel);
        // ���ڳ��ƻ�
        getCurrentOverPlanNum(productReportList);
        // ���ھ���ռ��
        getCurrentIncreaseNumProportion(productReportList);
        // ���ڼƻ������
        getCurrentPlanCompletionRate(productReportList);
        // �������� ���ھ��� ���� �ڳ����
        getCurrentIncreaseNumGrowthRate(productReportList, tbRptBaseinfoLoankindList, cycel);
        // ���ڷ����� ����Ԥ�Ƶ����� �����ѵ����� ����ʣ��Ԥ�Ƶ�����
        getProductOccAndExpire(productReportList, tbRptBaseinfoLoankindList, cycel);

        //ͬ�ȼ���---- ���ڷ�����ͬ��  ����Ԥ�Ƶ�����ͬ�� �����ѵ�����ͬ�� ����ʣ��Ԥ�Ƶ�����ͬ�� ���ھ���ͬ��  ���ڼƻ������ͬ�� ��������ͬ��
        getProductYoy(productReportList, cycel, productReportListCopy, date, combType, fdorgan);

        //����
        rankProductReportList(productReportList);
        //�����ͣ���λ�������������ֵ����Сֵ��
        addProductOtherList(productReportList, "-1", 1);


        //ת��Ϊ�����б�
        List<ProductReport> resultList = ProductReport.transToTree(productReportList, 3, "-1", 1);
        return resultList;
    }


    //��ȡ����list ���ܵ�����
    private List<TbRptBaseinfoLoankind> getCombDataList(String date, String combType, FdOrgan fdorgan) {
        //�����������List
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
        } else {

        }
        param.put("rptDate", date);
        param.put("combList", combListParam);
        param.put("excludeCombList", excludeCombListParam);
        param.put("rptOrgan", fdorgan.getThiscode());
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByDateAndOrgan(param);
        return tbRptBaseinfoLoankindList;
    }

    //�����Ʒ��--ͬ��
    private void getProductYoy(List<ProductReport> productReportList, String cycel, List<ProductReport> productReportListCopy, String date, String combType, FdOrgan fdorgan) {
        String beginDate = getLastYearDay(date);
        //�����������List
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getCombDataList(beginDate, combType, fdorgan);
        // ������ ...
        getProductOccAndExpire(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // ����
        getTodayIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList);
        //�ƻ������ͬ��
        // ���ڼƻ�
        getProductPlanAmount(productReportListCopy, combType, beginDate, cycel, fdorgan);
        // ���ھ���
        getCurrentIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // ���ڼƻ������
        getCurrentPlanCompletionRate(productReportListCopy);
        //��������
        //��� ���ռ��
        getProductBalance(productReportListCopy, tbRptBaseinfoLoankindList);
        // �������� ��ǰ���� ���� �ڳ����
        getCurrentIncreaseNumGrowthRate(productReportListCopy, tbRptBaseinfoLoankindList, cycel);


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


        for (ProductReport productReport : productReportList) {
            for (ProductReport report : productReportListCopy) {
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


    /**
     * ���㱾������
     *
     * @param productReportList
     * @param tbRptBaseinfoLoankindList
     * @param cycel                     �������� 1-�� 2-�� 3-�� 4-��
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

        //����һ�����������ֵı�������
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

        //���ñ�������
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal current_increase_num_growth_rate = getSafeCount(rateMap.get(combCode));
            productReport.setCurrent_increase_num_growth_rate(getSafeCount(productReport.getCurrent_increase_num_growth_rate()).add(current_increase_num_growth_rate));
        }
    }


    /**
     * ��Ʒ��--���ڷ����� ����Ԥ�Ƶ����� �����ѵ����� ����ʣ��Ԥ�Ƶ�����
     *
     * @param productReportList
     * @param tbRptBaseinfoLoankindList
     * @param cycel                     �������� 1-�� 2-�� 3-�� 4-��
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

        //����һ�����������ֵı��ڷ����� ����Ԥ�Ƶ����� �����ѵ����� ����ʣ��Ԥ�Ƶ�����
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

        //����һ�����������ֵı��ڷ����� ����Ԥ�Ƶ����� �����ѵ����� ����ʣ��Ԥ�Ƶ�����
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
     * ���ڼƻ������
     *
     * @param productReportList
     */
    public static void getCurrentPlanCompletionRate(List<ProductReport> productReportList) {
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_plan_completion_rate(BigDecimalUtil.divide(tempPr.getCurrent_increase_num(), tempPr.getPlanAmount(), 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    /**
     * ���㱾�ھ���ռ��
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
     * ��Ʒ��--���ռ�Ƚ��ڳ�
     *
     * @param productReportList         ����list
     * @param productReportListCopy     ���Ƶ�list
     * @param date                      ��������  yyyymmdd
     * @param cycel                     �������� 1-�� 2-�� 3-�� 4-��
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
     * ���ó������ռ��
     *
     * @param productReportListCopy
     * @param tbRptBaseinfoLoankindList ��������
     * @param cycel                     �������� 1-�� 2-�� 3-�� 4-��
     */
    private void buildBalanceRatioOfBeginData(List<ProductReport> productReportListCopy, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList, String cycel) {

        //�ڳ������
        BigDecimal balanceCount = BigDecimal.ZERO;

        //��װ�����������
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

        //����һ�����������ֵ����
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

        //���������ռ��
        for (ProductReport productReport : productReportListCopy) {
            String combCode = productReport.getId();
            BigDecimal balance = getSafeCount(balanceMap.get(combCode));
            productReport.setBalance(getSafeCount(productReport.getBalance()).add(balance));
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }

    }

    //����һ��list
    private List<ProductReport> copyProductList(List<ProductReport> productReportList) {
        ArrayList<ProductReport> list = new ArrayList<>();
        for (ProductReport productReport : productReportList) {
            ProductReport productReport2 = (ProductReport) BocoUtils.deepCopy(productReport);
            list.add(productReport2);
        }
        return list;
    }

    /**
     * ���㱾�ڳ��ƻ�=���ھ���-���ڼƻ�
     *
     * @param productReportList
     */
    public static void getCurrentOverPlanNum(List<ProductReport> productReportList) {
        for (ProductReport tempPr : productReportList) {
            tempPr.setCurrent_over_plan_num(tempPr.getCurrent_increase_num().subtract(tempPr.getPlanAmount()));
        }

    }

    /**
     * ���㱾�ھ���
     *
     * @param productReportList         ��������list
     * @param tbRptBaseinfoLoankindList ���ݿ���ȫ�������б�
     * @param cycel                     �������� 1-�� 2-�� 3-�� 4-��
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


        //����һ�����������ֵı��ھ���
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

        //���ñ��ھ���
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal current_increase_num = getSafeCount(balanceMap.get(combCode));
            productReport.setCurrent_increase_num(BigDecimalUtil.add(productReport.getCurrent_increase_num(), current_increase_num));
        }

    }

    /**
     * д�뵱�վ���
     *
     * @param productReportList         ��������list
     * @param tbRptBaseinfoLoankindList ���ݿ���ȫ�������б�
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


        //����һ�����������ֵĵ��վ���
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

        //���õ��վ���
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal day_increase_num = getSafeCount(balanceMap.get(combCode));
            productReport.setDay_increase_num(BigDecimalUtil.add(productReport.getDay_increase_num(), day_increase_num));
        }


    }

    /**
     * ��� ���ռ��
     *
     * @param productReportList
     * @param tbRptBaseinfoLoankindList
     */
    private void getProductBalance(List<ProductReport> productReportList, List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList) {
        //�����ܺ�
        BigDecimal balanceCount = BigDecimal.ZERO;
        //��װ�����������
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

        //����һ�����������ֵ����
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

        //���������ռ��
        for (ProductReport productReport : productReportList) {
            String combCode = productReport.getId();
            BigDecimal balance = getSafeCount(balanceMap.get(combCode));
            productReport.setBalance(getSafeCount(productReport.getBalance()).add(balance));
            productReport.setBalanceRatio(BigDecimalUtil.divide(productReport.getBalance(), balanceCount, 4).multiply(ReportConstant.RATIO_NUM));
        }
    }

    //��Ʒ��--�����ͣ���λ�������������ֵ����Сֵ��
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

    //��Ʒ��--��װ���ڼƻ�
    private void getOrganPlanAmount(List<ProductReport> productReportList, String combType, String date, String cycel, FdOrgan fdorgan) throws Exception {
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
        //������organ
        param.put("pdorgan", fdorgan.getThiscode());
        //���α��organ
        param.put("organ", WebContextUtil.getSessionOrgan().getThiscode());
        /*------------��ѯһ�����ּƻ�(�ϼ�)---------*/
        // List<Map<String, Object>> planOfCombLevelOne = reportCombMapper.getPlanByLevelOne(param);
        List<Map<String, Object>> planOfCombLevelOne = reportCombMapper.getOrganPlanByLevelOne(param);
        /*------------��ѯ�������ּƻ�(�ϼ�)---------*/
        // List<Map<String, Object>> planOfCombLevelTwo = reportCombMapper.getPlanByLevelTwo(param);
        List<Map<String, Object>> planOfCombLevelTwo = reportCombMapper.getOrganPlanByLevelTwo(param);

        /*---------��װ����----------*/
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

    //��Ʒ��--��װ���ڼƻ�
    private void getProductPlanAmount(List<ProductReport> productReportList, String combType, String date, String cycel, FdOrgan fdorgan) {
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
        /*------------��ѯһ�����ּƻ�(�ϼ�)---------*/
        List<Map<String, Object>> planOfCombLevelOne = reportCombMapper.getPlanByLevelOne(param);
        /*------------��ѯ�������ּƻ�(�ϼ�)---------*/
        List<Map<String, Object>> planOfCombLevelTwo = reportCombMapper.getPlanByLevelTwo(param);

        /*---------��װ����----------*/
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

    //��Ʒ��--�����Դ���Ϊ�����Ļ���list
    private List<ProductReport> getProductReportListBycomb(String combType) {
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
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     ��������  yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan  ����
     * @return
     */
    @Override
    public List<ProductReport> getProductReportDataOfOrganExcel(String combType, String date, String cycel, FdOrgan fdorgan) throws Exception {

        initComb();

        //�����������List
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getOrganCombDatalist(date, combType, fdorgan);


        /*---------��װ����----------*/
        // �����Դ���Ϊ�����Ļ���list
        List<ProductReport> productReportList = getProductReportListBycomb(combType);

        if (productReportList == null || productReportList.size() == 0) {
            return new ArrayList<>();
        }

        if (tbRptBaseinfoLoankindList != null && tbRptBaseinfoLoankindList.size() > 0) {
            //����һ������list
            List<ProductReport> productReportListCopy = copyProductList(productReportList);
            // ��� ���ռ��
            getProductBalance(productReportList, tbRptBaseinfoLoankindList);
            // ���ռ�Ƚ��ڳ�
            getProductbalanceCompareDateBegin(productReportList, productReportListCopy, date, cycel, combType, fdorgan,tbRptBaseinfoLoankindList);
            // ���ھ�������(����)
            getCurrentIncreaseReqLine(productReportList, date, cycel, fdorgan);
            // ���ھ�������(����)
            getCurrentIncreaseReqOrgan(productReportList, date, cycel, fdorgan);
            // ���ڼƻ�
            // getProductPlanAmount(productReportList, combType, date, cycel, fdorgan);
            getOrganPlanAmount(productReportList, combType, date, cycel, fdorgan);
            // ���վ���
            getTodayIncreaseNum(productReportList, tbRptBaseinfoLoankindList);
            // ���ھ���
            getCurrentIncreaseNum(productReportList, tbRptBaseinfoLoankindList, cycel);
            // ���ڳ��ƻ�
            getCurrentOverPlanNum(productReportList);
            // ���ھ���ռ��
            getCurrentIncreaseNumProportion(productReportList);
            // ���ڼƻ������
            getCurrentPlanCompletionRate(productReportList);
            // �������� ���ھ��� ���� �ڳ����
            getCurrentIncreaseNumGrowthRate(productReportList, tbRptBaseinfoLoankindList, cycel);
            // ���ڷ����� ����Ԥ�Ƶ����� �����ѵ����� ����ʣ��Ԥ�Ƶ�����
            getProductOccAndExpire(productReportList, tbRptBaseinfoLoankindList, cycel);

            //ͬ�ȼ���---- ���ڷ�����ͬ��  ����Ԥ�Ƶ�����ͬ�� �����ѵ�����ͬ�� ����ʣ��Ԥ�Ƶ�����ͬ�� ���ھ���ͬ��  ���ڼƻ������ͬ�� ��������ͬ��
            // getProductYoy(productReportList, cycel, productReportListCopy,date,combType,fdorgan);
            getOrganYoy(productReportList, cycel, productReportListCopy, date, combType, fdorgan);

            //����
            rankProductReportList(productReportList);
        }

        //�����Լ����ֵ�parentidΪ���������� �㼶ͳһ��2������������,id ��parentid ���ϻ�����
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
     * ���ر���һ��Ʒ��
     * @param response
     * @param request
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     ��������  yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan  ��ǰ��¼����
     * @param unit
     */
    @Override
    public void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String unit) throws Exception {
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
        List<ProductReport> data = getProductReportData(combType, date,cycel,fdorgan);
        List<ProductReport> productReportList = ProductReport.treeTransTo(data,new ArrayList<ProductReport>(),4);

        /*---------д���ļ�---------*/

        String filename = "��Ʒ��-"+date;
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


    //����ʱ��������
    public static String getReportRank(Integer integer) {
        if (integer == null || integer == 0) {
            return "-";
        }
        return integer + "";
    }
    //����ʱ����ٷֱ�
    public static String getReportRatio(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return "-";
        } else if (bigDecimal.compareTo(BigDecimal.ZERO)==0){
            return "0";
        }
        return BigDecimalUtil.formatNum(bigDecimal) + "%";
    }
    /**
     * ����ʱ����λ���л���
     * @param amount ����λ����Ԫ��
     * @param unit ����λ��1-��Ԫ 2-��Ԫ
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


    //����ͬ��
    private void getOrganYoy(List<ProductReport> productReportList, String cycel, List<ProductReport> productReportListCopy, String date, String combType, FdOrgan fdorgan) throws Exception {
        String beginDate = getLastYearDay(date);
        //�����������List
        List<TbRptBaseinfoLoankind> tbRptBaseinfoLoankindList = getOrganCombDatalist(beginDate, combType, fdorgan);
        // ������ ...
        getProductOccAndExpire(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // ����
        getTodayIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList);
        //�ƻ������ͬ��
        // ���ڼƻ�
        getProductPlanAmount(productReportListCopy, combType, beginDate, cycel, fdorgan);
        // ���ھ���
        getCurrentIncreaseNum(productReportListCopy, tbRptBaseinfoLoankindList, cycel);
        // ���ڼƻ������
        getCurrentPlanCompletionRate(productReportListCopy);
        //��������
        //��� ���ռ��
        getProductBalance(productReportListCopy, tbRptBaseinfoLoankindList);
        // �������� ��ǰ���� ���� �ڳ����
        getCurrentIncreaseNumGrowthRate(productReportListCopy, tbRptBaseinfoLoankindList, cycel);


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


        for (ProductReport productReport : productReportList) {
            for (ProductReport report : productReportListCopy) {
                if (productReport.getId().equals(report.getId())) {
                    //ͬ��  ��ǰֵ-ȥ��ֵ
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
            //��������͵�ǰ��¼������ȣ���ô�����󱾲�����
            TbRptBaseinfoLoankind tbRptBaseinfoLoankindParam = new TbRptBaseinfoLoankind();
            tbRptBaseinfoLoankindParam.setRptDate(date);
            tbRptBaseinfoLoankindParam.setRptOrgan(fdorgan.getThiscode());
            tbRptBaseinfoLoankindList = tbRptBaseinfoLoankindMapper.selectByAttr(tbRptBaseinfoLoankindParam);

        } else {
            //�����������List
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
            } else {

            }
            param.put("rptDate", date);
            param.put("combList", combListParam);
            param.put("excludeCombList", excludeCombListParam);
            param.put("rptOrgan", fdorgan.getThiscode());

            //���л�����¼��һ�ֻ�����¼ �ֿ���ѯ
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
