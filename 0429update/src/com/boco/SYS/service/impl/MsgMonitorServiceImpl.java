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
 * ���Ͷ���-������״̬ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class MsgMonitorServiceImpl extends GenericService<MsgMonitor, HashMap<String, Object>> implements MsgMonitorService {

    //���Զ��巽��ʱʹ��
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
    /*���Ͷ��ŵĻ�����ַ*/
    @Value("${sendMsg.url}")
    private String sendMsgUrl;


    /**
     * ��ʱ����--���¶��ű�
     * ������״̬�ĸı䣬���µ����ű���
     */
    @Override
    public void updateMsgStatus() {

        List<TbMonitor> tbMonitorList = tbMonitorMapper.selectByAttr(new TbMonitor());
        List<MsgMonitor> msgMonitorList = msgMonitorMapper.selectByAttr(new MsgMonitor());
        MsgParam msgParam = msgParamMapper.selectByAttr(new MsgParam()).get(0);

        for (TbMonitor tbMonitor : tbMonitorList) {
            //�����������ԣ������ԣ��жϷ���״̬�Ƿ�һ�£���һ������¶��ŷ�������û����Խ�������������ŷ����
            MsgMonitor monitor = null;
            for (MsgMonitor msgMonitor : msgMonitorList) {
                if (tbMonitor.getMoIp().equals(msgMonitor.getMoIp()) && tbMonitor.getMoSvrId().equals(msgMonitor.getMoSvrId())) {
                    monitor = msgMonitor;
                    break;
                }
            }

            if (monitor == null) {
                //û����Խ��������
                monitor = new MsgMonitor();
                monitor.setMoIp(tbMonitor.getMoIp());
                monitor.setMoSvrId(tbMonitor.getMoSvrId());
                monitor.setMoSvrType(tbMonitor.getMoSvrType());
                monitor.setMoSvrRunStatus(tbMonitor.getMoSvrRunStatus());
                monitor.setSendCount("0".equals(tbMonitor.getMoSvrRunStatus()) ? msgParam.getOpenCount() : msgParam.getCloseCount());
                msgMonitorMapper.insertEntity(monitor);
            } else {
                // ����ԣ��ж�״̬�Ƿ�ı�
                if (!tbMonitor.getMoSvrRunStatus().equals(monitor.getMoSvrRunStatus())) {
                    monitor.setMoSvrRunStatus(tbMonitor.getMoSvrRunStatus());
                    monitor.setSendCount("0".equals(tbMonitor.getMoSvrRunStatus()) ? msgParam.getOpenCount() : msgParam.getCloseCount());
                    msgMonitorMapper.updateByPK(monitor);
                }
            }
        }

    }


    /**
     * ��ʱ����--���ݶ��ű�״̬���Ͷ���
     */
    @Override
    public void sendMsg() {
        List<MsgMonitor> msgMonitorList = msgMonitorMapper.selectByAttr(new MsgMonitor());
        List<MsgPerson> msgPersonList = msgPersonMapper.selectByAttr(new MsgPerson());
        MsgParam msgParam = msgParamMapper.selectByAttr(new MsgParam()).get(0);
        MsgTemplate msgTemplate = msgTemplateMapper.selectByPK("1");

        //���շ����Ŵ���
        int todayCount = msgParam.getTodayCount();
        //ÿ����󷢶��Ŵ���
        int maxCount = msgParam.getMaxCount();

        for (MsgMonitor msgMonitor : msgMonitorList) {
            if (msgMonitor.getSendCount() > 0) {
                //��������ʣ���������0��ʱ�򣬾ͷ��Ͷ��ţ�������Ŵ���-1

                // �жϷ����ſ���
                if (msgParam.getOpenStatus() == 1) {
                    for (MsgPerson msgPerson : msgPersonList) {
                        //����״̬�Żᷢ�Ͷ���
                        if ("1".equals(msgPerson.getStatus())) {
                            //ÿ�췢�͵Ķ��Ų��ܳ�������������
                            if (todayCount >= maxCount) {
                                break;
                            }
                            todayCount++;

                            String msgText = msgTemplate.getTemplate();
                            msgText = msgText.replace("$appIp", msgMonitor.getMoIp());
                            msgText = msgText.replace("$appName", getSvrType(msgMonitor.getMoSvrType()));
                            msgText = msgText.replace("$appStatus", "0".equals(msgMonitor.getMoSvrRunStatus()) ? "����" : "�ر�");

                            JSONObject json = HttpRequestClient.sendMsg(msgPerson.getPhoneNumber(), sendMsgUrl, msgText);
                        }
                    }
                }

                msgMonitor.setSendCount(msgMonitor.getSendCount() - 1);
                msgMonitorMapper.updateByPK(msgMonitor);
            }
        }
        //����ÿ�շ����ŵĴ���
        msgParam.setTodayCount(todayCount);
        msgParamMapper.updateByPK(msgParam);
    }

    private String getSvrType(String type) {
        if ("ap".equals(type)) {
            return "�ɼ�";
        } else if ("ctrl".equals(type)) {
            return "�ܿ�";
        } else if ("monitor".equals(type)) {
            return "���";
        } else if ("batch".equals(type)) {
            return "������";
        } else {
            return type;
        }
    }


    /**
     * ��ʱ����--���ý��շ����Ŵ���
     */
    @Override
    public void clearTodayMsgCount() {
        MsgParam msgParam = msgParamMapper.selectByAttr(new MsgParam()).get(0);
        // ���ý��շ����Ŵ���
        msgParam.setTodayCount(0);
        msgParamMapper.updateByPK(msgParam);
    }



}