package com.boco.RE.service;


import com.boco.RE.entity.ProductReport;
import com.boco.SYS.entity.FdOrgan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 报表统计Service
 * @Author zhuhongjiang
 * @Date 2020/3/13 上午9:25
 **/
public interface IReportService {

    /**
     * 查询下级所属机构
     * @param thiscode
     * @return
     */
    List<FdOrgan> selectSubOrgan(String thiscode, String organlevel);

    /**
     * 查询信贷日报信息（贷款余额、到期量余额）
     * @param rptDate       统计日期（yyyyMMdd）
     * @param organlevel    机构级别（0总行、1一级分行）
     * @param organCode     机构编码
     * @param type  1-信贷规模日报表 2-信贷到期量日报表
     * @return
     */
    List<Map<String, Object>> getReportCreditDailyInfo(String rptDate, String organlevel, String organCode, int type);

    /**
     * 信贷规模日报表下载
     * @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate 统计日期
     */
    void reportCreditScaleDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception;


    /**
     * 信贷到期量日报表下载
     * @param response
     * @param request
     * @param combList
     * @param organList
     * @param baseInfoMap
     * @param rptDate
     */
    void reportCreditMaturityDailyDownload(HttpServletResponse response, HttpServletRequest request, List<Map<String, Object>> combList, List<FdOrgan> organList, Map<String, Object> baseInfoMap, String rptDate) throws Exception;


    /**
     * 首页 - 获取机构本月净增量信息（柱形图）
     * @param organlevel
     * @param organCode
     * @return
     */
    List<Map<String, Object>> getIndexOrganBarInfo(String organlevel, String organCode, String organName, String operCode) throws Exception;

    /**
     * 首页 - 二级贷种本月净增量（柱形图）
     * @param organlevel
     * @param organCode
     * @return
     */
    List<Map<String, Object>> getIndexCombBarInfo(String organlevel, String organCode, String operCode) throws Exception;

    /**
     * 首页 - 二级贷种本月计划完成率（折线图）
     * @param organlevel
     * @param organCode
     * @return
     */
    List<Map<String, Object>> getIndexCombLineInfo(String organlevel, String organCode, String operCode) throws Exception;

    /**
     * 首页 - 预警线完成率
     * @param organlevel
     * @param organCode
     * @param operCode
     * @return
     */
    List<Map<String, Object>> getIndexWarnCompleteInfo(String organlevel, String organCode, String operCode) throws Exception;

    /**
     * 首页 - 预警线绝对值
     * @param organlevel
     * @param organCode
     * @param operCode
     * @return
     */
    List<Map<String, Object>> getIndexWarnAbsInfo(String organlevel, String organCode, String operCode);

    /**
     * 一级贷种树形多选列表
     * @return
     */
    String combLevelOneTreeList();

    /**
     * 构建产品报表数据
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date     报表日期  yyyymmdd
     * @param cycel    报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan  当前登录机构
     * @return
     */
    List<ProductReport> getProductReportData(String combType, String date, String cycel, FdOrgan fdorgan);
    /**
     * 为机构表构建产品报表数据
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date     报表日期  yyyymmdd
     * @param cycel    报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan  当前登录机构
     * @return
     */
    List<ProductReport> getProductReportDataOfOrganExcel(String combType, String date, String cycel, FdOrgan fdorgan) throws Exception;

    /**
     * 下载报表一
     * @param response
     * @param request
     * @param combType 贷种组合类型 1 全部；2少拆放；4 实体
     * @param date     报表日期  yyyymmdd
     * @param cycel    报表周期 1-年 2-季 3-月 4-日
     * @param fdorgan  当前登录机构
     * @param unit 报表单位 1-万元 2-亿元
     */
    void downloadProductExcel(HttpServletResponse response, HttpServletRequest request, String combType, String date, String cycel, FdOrgan fdorgan, String unit) throws Exception;


}
