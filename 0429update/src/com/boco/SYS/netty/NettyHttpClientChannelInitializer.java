package com.boco.SYS.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class NettyHttpClientChannelInitializer extends ChannelInitializer {
	 
	public NettyHttpClientChannelInitializer(StringBuffer stringBuffer, DefaultFullHttpRequest request){
		this.stringBuffer=stringBuffer;	
		this.request=request;
	}
	
	private StringBuffer stringBuffer;
	DefaultFullHttpRequest request ;
	@Override
	protected void initChannel(Channel ch) throws Exception {
		
		// �ͻ��˽��յ�����httpResponse��Ӧ������Ҫʹ��HttpResponseDecoder���н���
        ch.pipeline().addLast(new HttpResponseDecoder());
        // �ͻ��˷��͵���httprequest������Ҫʹ��HttpRequestEncoder���б���
        ch.pipeline().addLast(new HttpRequestEncoder());
        ch.pipeline().addLast(new HttpObjectAggregator(1048576));
        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
		 	@Override
		    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		        if (msg instanceof HttpResponse)
		        {
		        }
		        if(msg instanceof HttpContent)
		        {
		            HttpContent content = (HttpContent)msg;
		            ByteBuf buf = content.content();
		            stringBuffer.append(buf.toString(io.netty.util.CharsetUtil.UTF_8));
		            buf.release();
		            ctx.close();
		        }
		    }
		 	
		    @Override
		    public void channelActive( ChannelHandlerContext ctx ) throws Exception
		    {
		    	ctx.writeAndFlush(request);
		    }

		});
		
		 
	}

}
