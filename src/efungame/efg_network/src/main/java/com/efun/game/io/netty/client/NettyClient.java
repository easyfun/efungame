package com.efun.game.io.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.protobuf.api.MessageProto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {
	private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
	private EventLoopGroup workerGroup;
	private Bootstrap bootstrap;
	private ChannelHandler clientHandler;
	
	public NettyClient() {
		
	}
	
	public NettyClient(ChannelHandler clientHandler) {
		this.clientHandler = clientHandler; 
	}
	
	public ChannelHandler getClientHandler() {
		return clientHandler;
	}

	public void setClientHandler(ChannelHandler clientHandler) {
		this.clientHandler = clientHandler;
	}

	public synchronized void connect(String host, int port) throws Exception {
		workerGroup = new NioEventLoopGroup();
		try {
			bootstrap = new Bootstrap();
			bootstrap.group(workerGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			
			if (null == clientHandler) {
				throw new Exception("clientHandler must be setted!");
			}
			
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(clientHandler);
				}
			});

			ChannelFuture f = bootstrap.connect(host, port).sync();
//			for (int i=0; i<60; i++) {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					logger.error("sleep error.", e);
//				}
//			}

			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

}
