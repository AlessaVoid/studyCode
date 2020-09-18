package com.boco.SYS.monitor.netty.initializer;

import com.boco.SYS.monitor.netty.handler.MonitorClientHandler;
import com.boco.SYS.service.IWebMonitorService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * ¼à¿Ø Initializer
 * @Author zhuhongjiang
 * @Date 2020/1/6 ÏÂÎç4:08
 **/
public class MonitorClientChannelInitializer extends ChannelInitializer<Channel> {
	
	private static final Logger log = LoggerFactory.getLogger(MonitorClientChannelInitializer.class);

	private IWebMonitorService webMonitorService;

	private String charsetString = "GB2312";

	public MonitorClientChannelInitializer(IWebMonitorService webMonitorService) {
		this.webMonitorService= webMonitorService;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new StringEncoder(Charset.forName(charsetString)));
		ch.pipeline().addLast(new StringDecoder(Charset.forName(charsetString)));
        ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
        ch.pipeline().addFirst(new DelimiterBasedFrameDecoder(8192, delimiter));
		ch.pipeline().addLast(new MonitorClientHandler(webMonitorService));
	}

}
