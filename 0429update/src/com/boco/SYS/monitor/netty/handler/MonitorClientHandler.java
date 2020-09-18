package com.boco.SYS.monitor.netty.handler;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.monitor.model.TradeReport;
import com.boco.SYS.service.IWebMonitorService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ��� Handler
 * @Author zhuhongjiang
 * @Date 2020/1/6 ����4:07
 **/
public class MonitorClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MonitorClientHandler.class);

    private IWebMonitorService webMonitorService;

    public MonitorClientHandler(IWebMonitorService webMonitorService) {
        this.webMonitorService= webMonitorService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.debug("�յ����������ؼ����Ϣ��{}", msg);

            String[] str = ((String)msg).split( "\\|" );
            //���汨��
            TradeReport tradeReport = JSON.parseObject(str[7], TradeReport.class);
            tradeReport.setMergedTime(str[1]);
            tradeReport.setName(str[2]);
            tradeReport.setIp(str[3]);
            tradeReport.setPort(str[4]);
            tradeReport.setType(str[5]);

            //������Index
            int masterIndex = Integer.valueOf(str[6]);

            webMonitorService.resMonitorData(tradeReport, masterIndex);

        } catch ( Exception e ) {
            log.error("master�����ع�������Ϣ��msg��{}���쳣��{}", msg, e.getMessage());
        }
    }

}
