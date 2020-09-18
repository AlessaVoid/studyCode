package com.boco.RE.service;

import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.TimeLimitReport;
import com.boco.SYS.entity.FdOrgan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ��ҵ�� service
 */
public interface ReportIndustryService {
    /**
     * ������������
     *
     * @param date      yyyymmdd
     * @param cycel     �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan   ����
     * @return
     */
    List<ProductReport> getReportData(String date, String cycel, FdOrgan fdorgan);

    /**
     *  ��ҵ������
     * @param response
     * @param request
     * @param date      yyyymmdd
     * @param cycel     �������� 1-�� 2-�� 3-�� 4-��
     * @param fdorgan   ����
     * @param unit ����λ 1-��Ԫ  2-��Ԫ
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, FdOrgan fdorgan, String unit) throws Exception;
}
