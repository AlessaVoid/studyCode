package com.boco.SYS.service;

import com.boco.SYS.monitor.model.TradeReport;

import java.util.List;

/**
 * 监控Service
 * @Author zhuhongjiang
 * @Date 2020/1/6 下午1:32
 **/
public interface IWebMonitorService {

    /**
     * 实时查询监控信息
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午1:50
     **/
    List<TradeReport> select();

    /**
     * 定时任务-检查Channel活跃状态
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午2:50
     **/
    void checkChannelActive();

    /**
     * 定时任务-实时获取监控信息（请求）
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午2:50
     **/
    void reqMonitorData();

    /**
     * 定时任务-实时获取监控信息（响应）
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午2:50
     **/
    void resMonitorData(TradeReport tradeReport, int masterIndex);
}
