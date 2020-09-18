package com.boco.SYS.service;

import java.util.HashMap;

import com.boco.SYS.entity.MsgMonitor;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 发送短信-服务监控状态业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface MsgMonitorService extends IGenericService<MsgMonitor,HashMap<String,Object>>{

    /**
     * 定时任务--更新短信表
     * 检测服务状态的改变，更新到短信表中
     */
    void updateMsgStatus();


    /**
     * 定时任务--根据短信表状态发送短信
     */
    void sendMsg();

    /**
     *  定时任务--重置今日发短信次数
     */
    void clearTodayMsgCount();
}