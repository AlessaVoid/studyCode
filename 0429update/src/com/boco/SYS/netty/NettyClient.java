package com.boco.SYS.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public interface NettyClient {
	public Channel connect(int nThreads, String host, int port,
                           ChannelInitializer<Channel> channelInitializer) throws Exception;
	public void close();
	public Channel connect(String host, int port, ChannelInitializer<Channel> channelInitializer) throws Exception ;
}
