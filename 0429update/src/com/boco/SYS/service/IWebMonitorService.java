package com.boco.SYS.service;

import com.boco.SYS.monitor.model.TradeReport;

import java.util.List;

/**
 * ���Service
 * @Author zhuhongjiang
 * @Date 2020/1/6 ����1:32
 **/
public interface IWebMonitorService {

    /**
     * ʵʱ��ѯ�����Ϣ
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����1:50
     **/
    List<TradeReport> select();

    /**
     * ��ʱ����-���Channel��Ծ״̬
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:50
     **/
    void checkChannelActive();

    /**
     * ��ʱ����-ʵʱ��ȡ�����Ϣ������
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:50
     **/
    void reqMonitorData();

    /**
     * ��ʱ����-ʵʱ��ȡ�����Ϣ����Ӧ��
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:50
     **/
    void resMonitorData(TradeReport tradeReport, int masterIndex);
}
