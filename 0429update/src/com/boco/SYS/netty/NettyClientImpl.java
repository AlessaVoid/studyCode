
package com.boco.SYS.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientImpl implements NettyClient {
    private static Logger log = LoggerFactory.getLogger(NettyClientImpl.class);
     EventLoopGroup eventLoopGroup = null;
    Bootstrap bootstrap = null;

    public Channel connect(int nThreads, String host, int port,
                           ChannelInitializer<Channel> channelInitializer) throws Exception {
        try {
            if (eventLoopGroup == null)
                eventLoopGroup = new NioEventLoopGroup(nThreads);
            bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.MAX_MESSAGES_PER_READ, Integer.MAX_VALUE);
            bootstrap.handler(channelInitializer);
            return bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            close();
            log.info("连接异常，需检查：" + host + ":" + port + "error=" + e);
        }
        return null;
    }

    public void close() {
        if (eventLoopGroup != null)
            eventLoopGroup.shutdownGracefully();
    }

    public Channel connect(String host, int port, ChannelInitializer<Channel> channelInitializer) throws Exception {
        return connect(1, host, port, channelInitializer);
    }
}