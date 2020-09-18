package com.boco.RE.service;


import com.boco.RE.entity.ProductReport;
import com.boco.SYS.entity.FdOrgan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * ����ͳ��Service
 * @Author zhuhongjiang
 * @Date 2020/3/13 ����9:25
 **/
public interface IReportService {

    /**
     * ��ѯ�¼���������
     * @param thiscode
     * @return
     */
    List<FdOrgan> selectSubOrgan(String thiscode, String organlevel);

    /**
     * ��ѯ�Ŵ��ձ���Ϣ����������������
     * @param rptDate       ͳ�����ڣ�yyyyMMdd��
     * @param organlevel    ��������0���С�1һ�����У�
     * @param organCode     ��������
     * @param type  1-�Ŵ���ģ�ձ��� 2-�Ŵ��������ձ���
     * @return
     */
    List<Map<String, Object>> getReportCreditDailyInfo(String rptDate, String organlevel, String organCode, int type);

    /**
     * �Ŵ���ģ�ձ�������
     * @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate ͳ������
     */
    void reportCreditScaleDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception;


    /**
     * �Ŵ��������ձ�������
     * @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate
     */
    void reportCreditMaturityDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception;


    /**
     * ��ҳ - ��ȡ�������¾�������Ϣ������ͼ��
     * @param organlevel
     * @param organCode
     * @return
     */
    List<Map<String, Object>> getIndexOrganBarInfo(String organlevel, String organCode, String organName, String operCode) throws Exception;

    /**
     * ��ҳ - �������ֱ��¾�����������ͼ��
     * @param organlevel
     * @param organCode
     * @return
     */
    List<Map<String, Object>> getIndexCombBarInfo(String organlevel, String organCode, String operCode) throws Exception;

    /**
     * ��ҳ - �������ֱ��¼ƻ�����ʣ�����ͼ��
     * @param organlevel
     * @param organCode
     * @return
     */
    List<Map<String, Object>> getIndexCombLineInfo(String organlevel, String organCode, String operCode) throws Exception;

    /**
     * ��ҳ - Ԥ���������
     * @param organlevel
     * @param organCode
     * @param operCode
     * @return
     */
    List<Map<String, Object>> getIndexWarnCompleteInfo(String organlevel, String organCode, String operCode) throws Exception;

    /**
     * ��ҳ - Ԥ���߾���ֵ
     * @param organlevel
     * @param organCode
     * @param operCode
     * @return
     */
    List<Map<String, Object>> getIndexWarnAbsInfo(String organlevel, String organCode, String operCode);

    /**
     * һ���������ζ�ѡ�б�
     * @return
     */
    String combLevelOneTreeList();

    /**
     * ������Ʒ��������
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     ��������  yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan  ��ǰ��¼����
     * @return
     */
    List<ProductReport> getProductReportData(String combType, String date, String cycel, FdOrgan fdorgan);
    /**
     * Ϊ����������Ʒ��������
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     ��������  yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan  ��ǰ��¼����
     * @return
     */
    List<ProductReport> getProductReportDataOfOrganExcel(String combType, String date, String cycel, FdOrgan fdorgan) throws Exception;

    /**
     * ���ر���һ
     * @param response
     * @param request
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date     ��������  yyyymmdd
     * @param cycel    �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan  ��ǰ��¼����
     * @param unit ����λ 1-��Ԫ 2-��Ԫ
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String unit) throws Exception;


}
