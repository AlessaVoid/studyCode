package com.boco.SYS.service;

/**
 * 日终任务状态监控
 */
public interface BatchDayMessageService {

    /**
     * 监控日终任务状态并且入短信库
     */
    void monitorBatchAndInsertMsg();

    /**
     * 发送短信库中的短信
     */
    void sendShortMsg();

}
