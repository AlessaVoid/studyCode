package com.boco.RE.service;

import com.boco.RE.entity.BankIndustryReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ������ service
 */
public interface ReportAreaIndustryService {

    /**
     * ��ѯ��������
     * @param date  ���� yyyyMM
     * @param cycel ����
     * @return
     */
    List<BankIndustryReport> getReportData(String date, String cycel);

    /**
     * ����������
     * @param response
     * @param request
     * @param date  ���� yyyyMM
     * @param cycel ����
     * @param unit ��λ 1-��Ԫ 2-��Ԫ
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, String unit) throws Exception;
}
