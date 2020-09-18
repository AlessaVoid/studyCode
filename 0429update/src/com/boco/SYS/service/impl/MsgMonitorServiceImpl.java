package com.boco.SYS.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.MsgMonitorService;
import com.boco.SYS.util.HttpRequestClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 发送短信-服务监控状态业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class MsgMonitorServiceImpl extends GenericService<MsgMonitor, HashMap<String, Object>> implements MsgMonitorService {

    //有自定义方法时使用
    @Autowired
    private MsgMonitorMapper msgMonitorMapper;
    @Autowired
    private TbMonitorMapper tbMonitorMapper;
    @Autowired
    private MsgPersonMapper msgPersonMapper;
    @Autowired
    private MsgParamMapper msgParamMapper;
    @Autowired
    private MsgTemplateMapper msgTemplateMapper;
    /*发送短信的环境地址*/
    @Value("${sendMsg.url}")
    private String sendMsgUrl;


    /**
     * 定时任务--更新短信表
     * 检测服务状态的改变，更新到短信表中
     */
    @Override
    public void updateMsgStatus() {

        List<TbMonitor> tbMonitorList = tbMonitorMapper.selectByAttr(new TbMonitor());
        List<MsgMonitor> msgMonitorList = msgMonitorMapper.selectByAttr(new MsgMonitor());
        MsgParam msgParam = msgParamMapper.selectByAttr(new MsgParam()).get(0);

        for (TbMonitor tbMonitor : tbMonitorList) {
            //两个表进行配对，如果配对，判断服务状态是否一致，不一致则更新短信服务表，如果没有配对结果，则新增短信服务表
            MsgMonitor monitor = null;
            for (MsgMonitor msgMonitor : msgMonitorList) {
                if (tbMonitor.getMoIp().equals(msgMonitor.getMoIp()) && tbMonitor.getMoSvrId().equals(msgMonitor.getMoSvrId())) {
                    monitor = msgMonitor;
                    break;
                }
            }

            if (monitor == null) {
                //没有配对结果，新增
                monitor = new MsgMonitor();
                monitor.setMoIp(tbMonitor.getMoIp());
                monitor.setMoSvrId(tbMonitor.getMoSvrId());
                monitor.setMoSvrType(tbMonitor.getMoSvrType());
                monitor.setMoSvrRunStatus(tbMonitor.getMoSvrRunStatus());
                monitor.setSendCount("0".equals(tbMonitor.getMoSvrRunStatus()) ? msgParam.getOpenCount() : msgParam.getCloseCount());
                msgMonitorMapper.insertEntity(monitor);
            } else {
                // 有配对，判断状态是否改变
                if (!tbMonitor.getMoSvrRunStatus().equals(monitor.getMoSvrRunStatus())) {
                    monitor.setMoSvrRunStatus(tbMonitor.getMoSvrRunStatus());
                    monitor.setSendCount("0".equals(tbMonitor.getMoSvrRunStatus()) ? msgParam.getOpenCount() : msgParam.getCloseCount());
                    msgMonitorMapper.updateByPK(monitor);
                }
            }
        }

    }


    /**
     * 定时任务--根据短信表状态发送短信
     */
    @Override
    public void sendMsg() {
        List<MsgMonitor> msgMonitorList = msgMonitorMapper.selectByAttr(new MsgMonitor());
        List<MsgPerson> msgPersonList = msgPersonMapper.selectByAttr(new MsgPerson());
        MsgParam msgParam = msgParamMapper.selectByAttr(new MsgParam()).get(0);
        MsgTemplate msgTemplate = msgTemplateMapper.selectByPK("1");

        //今日发短信次数
        int todayCount = msgParam.getTodayCount();
        //每日最大发短信次数
        int maxCount = msgParam.getMaxCount();

        for (MsgMonitor msgMonitor : msgMonitorList) {
            if (msgMonitor.getSendCount() > 0) {
                //当发短信剩余次数大于0的时候，就发送短信，发完短信次数-1

                // 判断发短信开关
                if (msgParam.getOpenStatus() == 1) {
                    for (MsgPerson msgPerson : msgPersonList) {
                        //启用状态才会发送短信
                        if ("1".equals(msgPerson.getStatus())) {
                            //每天发送的短信不能超过最大次数限制
                            if (todayCount >= maxCount) {
                                break;
                            }
                            todayCount++;

                            String msgText = msgTemplate.getTemplate();
                            msgText = msgText.replace("$appIp", msgMonitor.getMoIp());
                            msgText = msgText.replace("$appName", getSvrType(msgMonitor.getMoSvrType()));
                            msgText = msgText.replace("$appStatus", "0".equals(msgMonitor.getMoSvrRunStatus()) ? "启动" : "关闭");

                            JSONObject json = HttpRequestClient.sendMsg(msgPerson.getPhoneNumber(), sendMsgUrl, msgText);
                        }
                    }
                }

                msgMonitor.setSendCount(msgMonitor.getSendCount() - 1);
                msgMonitorMapper.updateByPK(msgMonitor);
            }
        }
        //更新每日发短信的次数
        msgParam.setTodayCount(todayCount);
        msgParamMapper.updateByPK(msgParam);
    }

    private String getSvrType(String type) {
        if ("ap".equals(type)) {
            return "采集";
        } else if ("ctrl".equals(type)) {
            return "管控";
        } else if ("monitor".equals(type)) {
            return "监控";
        } else if ("batch".equals(type)) {
            return "批处理";
        } else {
            return type;
        }
    }


    /**
     * 定时任务--重置今日发短信次数
     */
    @Override
    public void clearTodayMsgCount() {
        MsgParam msgParam = msgParamMapper.selectByAttr(new MsgParam()).get(0);
        // 重置今日发短信次数
        msgParam.setTodayCount(0);
        msgParamMapper.updateByPK(msgParam);
    }



}