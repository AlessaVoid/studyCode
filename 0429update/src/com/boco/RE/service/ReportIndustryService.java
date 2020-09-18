package com.boco.RE.service;

import com.boco.RE.entity.ProductReport;
import com.boco.RE.entity.TimeLimitReport;
import com.boco.SYS.entity.FdOrgan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 行业表 service
 */
public interface ReportIndustryService {
    /**
     * 构建报表数据
     *
     * @param date      yyyymmdd
     * @param cycel     报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan   机构
     * @return
     */
    List<ProductReport> getReportData(String date, String cycel, FdOrgan fdorgan);

    /**
     *  行业表下载
     * @param response
     * @param request
     * @param date      yyyymmdd
     * @param cycel     报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan   机构
     * @param unit 报表单位 1-万元  2-亿元
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, FdOrgan fdorgan, String unit) throws Exception;
}
