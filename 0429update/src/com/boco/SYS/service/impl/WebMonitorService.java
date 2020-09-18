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
 * 监控Service
 * @Author zhuhongjiang
 * @Date 2020/1/6 下午1:32
 **/
@Service
public class WebMonitorService implements IWebMonitorService {

    private static final Logger log = LoggerFactory.getLogger(WebMonitorService.class);

    @Autowired
    private MonitorConfig monitorConfig;

    //服务信息
    String[] ipArr, portArr, typeArr, nameArr, numberWebArr;

    //监控报告数据缓存
    SumRingBuffer sumRingBuffer;
    int bufferSize;
    int totalMaster;

    //监控Channel缓存
    public static Map<String, Channel> monitorChannelMap = new HashMap<>();


    @PostConstruct
    public void init(){
        //服务信息
        ipArr = monitorConfig.getIps().split(",");
        portArr = monitorConfig.getPorts().split(",");
        typeArr = monitorConfig.getTypes().split(",");
        nameArr = monitorConfig.getNames().split(",");
        numberWebArr = monitorConfig.getNumberWeb().split(",");

        //初始化监控报告数据
        bufferSize = monitorConfig.getBufferSize();
        totalMaster = monitorConfig.getTotalMaster();
        sumRingBuffer = new SumRingBuffer(bufferSize, totalMaster);

        //初始化监控Channel
        checkChannelActive();
    }


    /**
     * 实时查询监控信息
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午1:50
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
        log.info("向页面返回监控数据:{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * 定时任务-检查Channel活跃状态
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午2:50
     **/
    @Override
    public void checkChannelActive() {
        String channelKeyFormat = "|%s|%s|%s|%s|%s";  //key值格式: |name|ip|port|type|numberWeb
        for(int i = 0; i < ipArr.length; i++){
            try{
                String channelKey = String.format(channelKeyFormat, nameArr[i], ipArr[i], portArr[i], typeArr[i], numberWebArr[i]);
                Channel ch = monitorChannelMap.get(channelKey);

                if(ch == null || !ch.isActive()){
                    //删除
                    monitorChannelMap.remove(channelKey);
                    //连接
                    NettyClient nettyClient = new NettyClientImpl();
                    ch = nettyClient.connect(ipArr[i], Integer.parseInt(portArr[i]), new MonitorClientChannelInitializer(this));

                    if(ch != null){
                        monitorChannelMap.put(channelKey, ch);
                    }
                }
            }catch(Exception e){
                log.error( "定时任务-检查Channel活跃状态[{}]连接错误：{}", nameArr[i], e);
            }
        }

        //关闭并去除不在配置范围内的连接
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
     * 定时任务-实时获取监控信息（请求）
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午2:50
     **/
    @Override
    public void reqMonitorData() {
        long nowTime = new Date().getTime() / 1000;
        int index = (int)(nowTime & (bufferSize-1));

        //初始化当前时间buffer格子信息
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
                log.error("定时任务-实时获取监控信息异常：", e);
            }
        }
    }

    /**
     * 定时任务-实时获取监控信息（响应）
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午2:50
     **/
    @Override
    public void resMonitorData(TradeReport tradeReport, int masterIndex) {
        int index = (int)(Long.valueOf(tradeReport.getMergedTime()) & (bufferSize-1));
        sumRingBuffer.put(index, masterIndex, tradeReport);
    }
}
