package com.boco.SYS.service;

/**
 * ��������״̬���
 */
public interface BatchDayMessageService {

    /**
     * �����������״̬��������ſ�
     */
    void monitorBatchAndInsertMsg();

    /**
     * ���Ͷ��ſ��еĶ���
     */
    void sendShortMsg();

}
