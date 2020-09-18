package com.boco.SYS.service.impl;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.monitor.config.MonitorConfig;
import com.boco.SYS.monitor.model.SumRingBuffer;
import com.boco.SYS.monitor.model.TradeReport;
import com.boco.SYS.monitor.netty.initializer.MonitorClientChannelInitializer;
import com.boco.SYS.netty.NettyClient;
import com.boco.SYS.netty.NettyClientImpl;
import com.boco.SYS.service.IWebMonitorService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���Service
 * @Author zhuhongjiang
 * @Date 2020/1/6 ����1:32
 **/
@Service
public class WebMonitorService implements IWebMonitorService {

    private static final Logger log = LoggerFactory.getLogger(WebMonitorService.class);

    @Autowired
    private MonitorConfig monitorConfig;

    //������Ϣ
    String[] ipArr, portArr, typeArr, nameArr, numberWebArr;

    //��ر������ݻ���
    SumRingBuffer sumRingBuffer;
    int bufferSize;
    int totalMaster;

    //���Channel����
    public static Map<String, Channel> monitorChannelMap = new HashMap<>();


    @PostConstruct
    public void init(){
        //������Ϣ
        ipArr = monitorConfig.getIps().split(",");
        portArr = monitorConfig.getPorts().split(",");
        typeArr = monitorConfig.getTypes().split(",");
        nameArr = monitorConfig.getNames().split(",");
        numberWebArr = monitorConfig.getNumberWeb().split(",");

        //��ʼ����ر�������
        bufferSize = monitorConfig.getBufferSize();
        totalMaster = monitorConfig.getTotalMaster();
        sumRingBuffer = new SumRingBuffer(bufferSize, totalMaster);

        //��ʼ�����Channel
        checkChannelActive();
    }


    /**
     * ʵʱ��ѯ�����Ϣ
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����1:50
     **/
    @Override
    public List<TradeReport> select() {
        List<TradeReport> list = new ArrayList<>();
        long time = (new Date().getTime() - 1000) / 1000;
        int index = (int)(time & (bufferSize-1));

        TradeReport[] reportArr = sumRingBuffer.get(index);
        for(TradeReport report : reportArr){
            report.setTime(new Date());
            list.add(report);
        }
        log.info("��ҳ�淵�ؼ������:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * ��ʱ����-���Channel��Ծ״̬
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:50
     **/
    @Override
    public void checkChannelActive() {
        String channelKeyFormat = "|%s|%s|%s|%s|%s";  //keyֵ��ʽ: |name|ip|port|type|numberWeb
        for(int i = 0; i < ipArr.length; i++){
            try{
                String channelKey = String.format(channelKeyFormat, nameArr[i], ipArr[i], portArr[i], typeArr[i], numberWebArr[i]);
                Channel ch = monitorChannelMap.get(channelKey);

                if(ch == null || !ch.isActive()){
                    //ɾ��
                    monitorChannelMap.remove(channelKey);
                    //����
                    NettyClient nettyClient = new NettyClientImpl();
                    ch = nettyClient.connect(ipArr[i], Integer.parseInt(portArr[i]), new MonitorClientChannelInitializer(this));

                    if(ch != null){
                        monitorChannelMap.put(channelKey, ch);
                    }
                }
            }catch(Exception e){
                log.error( "��ʱ����-���Channel��Ծ״̬[{}]���Ӵ���{}", nameArr[i], e);
            }
        }

        //�رղ�ȥ���������÷�Χ�ڵ�����
        for(String spValue : monitorChannelMap.keySet()){
            boolean flag = false;
            for (int i = 0; i < ipArr.length; i++) {
                String channelKey = String.format(channelKeyFormat, nameArr[i], ipArr[i], portArr[i], typeArr[i], numberWebArr[i]);
                if (channelKey.equals(spValue)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Channel ch = monitorChannelMap.get(spValue);
                if (ch != null) ch.close();
                monitorChannelMap.remove(spValue);
            }
        }
    }

    /**
     * ��ʱ����-ʵʱ��ȡ�����Ϣ������
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:50
     **/
    @Override
    public void reqMonitorData() {
        long nowTime = new Date().getTime() / 1000;
        int index = (int)(nowTime & (bufferSize-1));

        //��ʼ����ǰʱ��buffer������Ϣ
        for(int i = 0; i < ipArr.length; i++) {
            sumRingBuffer.get(index)[i].clearData(typeArr[i], nameArr[i], ipArr[i], portArr[i]);
        }

        for(String key : monitorChannelMap.keySet()){
            Channel ch = monitorChannelMap.get(key);
            try{
                if(ch != null && ch.isActive()){
                    String message = "|" + nowTime + key;
                    ch.writeAndFlush(message + "\r\n");
                }
            }catch(Exception e){
                log.error("��ʱ����-ʵʱ��ȡ�����Ϣ�쳣��", e);
            }
        }
    }

    /**
     * ��ʱ����-ʵʱ��ȡ�����Ϣ����Ӧ��
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:50
     **/
    @Override
    public void resMonitorData(TradeReport tradeReport, int masterIndex) {
        int index = (int)(Long.valueOf(tradeReport.getMergedTime()) & (bufferSize-1));
        sumRingBuffer.put(index, masterIndex, tradeReport);
    }
}
