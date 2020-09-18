package com.boco.RE.service;

import com.boco.RE.entity.BankIndustryReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地区表 service
 */
public interface ReportAreaIndustryService {

    /**
     * 查询报表数据
     * @param date  日期 yyyyMM
     * @param cycel 周期
     * @return
     */
    List<BankIndustryReport> getReportData(String date, String cycel);

    /**
     * 地区表下载
     * @param response
     * @param request
     * @param date  日期 yyyyMM
     * @param cycel 周期
     * @param unit 单位 1-万元 2-亿元
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String date, String cycel, String unit) throws Exception;
}
