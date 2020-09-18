package com.boco.SYS.service;

import java.util.HashMap;

import com.boco.SYS.entity.MsgMonitor;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * ���Ͷ���-������״̬ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface MsgMonitorService extends IGenericService<MsgMonitor,HashMap<String,Object>>{

    /**
     * ��ʱ����--���¶��ű�
     * ������״̬�ĸı䣬���µ����ű���
     */
    void updateMsgStatus();


    /**
     * ��ʱ����--���ݶ��ű�״̬���Ͷ���
     */
    void sendMsg();

    /**
     *  ��ʱ����--���ý��շ����Ŵ���
     */
    void clearTodayMsgCount();
}