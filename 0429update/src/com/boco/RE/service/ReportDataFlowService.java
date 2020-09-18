package com.boco.RE.service;

import com.boco.RE.entity.DataFlowReport;
import com.boco.SYS.entity.FdOrgan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ������service
 */
public interface ReportDataFlowService {
    /**
     * ������������
     *
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date yyyymmdd
     * @param cycel �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan ����
     * @param dimension ����ά�ȣ�1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
     * @return
     */
    List<DataFlowReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception;

    /**
     * ����������
     * @param response
     * @param request
     * @param combType ����������� 1 ȫ����2�ٲ�ţ�4 ʵ��
     * @param date yyyymmdd
     * @param cycel �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan ����
     * @param dimension ����ά�ȣ�1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������
     * @param unit ����λ 1-��Ԫ 2-��Ԫ
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String dimension, String unit) throws Exception;
}
