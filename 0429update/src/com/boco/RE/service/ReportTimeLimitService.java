package com.boco.RE.service;

import com.boco.RE.entity.DataFlowReport;
import com.boco.RE.entity.TimeLimitReport;
import com.boco.SYS.entity.FdOrgan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 期限表 service
 */
public interface ReportTimeLimitService {

    /**
     * 构建报表数据
     *
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date yyyymmdd
     * @param cycel 报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan 机构
     * @param dimension 汇总维度：1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
     * @return
     */
    List<TimeLimitReport> getReportData(String combType, String date, String cycel, FdOrgan fdorgan, String dimension) throws Exception;

    /**
     * 期限表下载
     * @param response
     * @param request
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date yyyymmdd
     * @param cycel 报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan 机构
     * @param dimension 汇总维度：1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行
     * @param unit 报表单位 1-万元 2-亿元
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String dimension, String unit) throws Exception;
}
