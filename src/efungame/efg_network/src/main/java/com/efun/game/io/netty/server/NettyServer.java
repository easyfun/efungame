package com.efun.game.io.netty.server;

import com.efun.game.io.netty.server.handler.NettyHandler;
import com.efun.game.io.remoting.exchange.DefaultExchangeHandler;
import com.efun.game.io.remoting.exchange.ExchangeHandler;
import com.efun.game.io.remoting.transport.dispatcher.ChannelHandlers;
import com.efun.game.protobuf.api.MessageProto;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
	private int port;
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	private ServerBootstrap bootstrap;
	private ExchangeHandler requestHandler; // 业务dispatcher

	public NettyServer(int port) {
		this.port = port;
	}

	public NettyServer(int port, ExchangeHandler requestHandler) {
		this.port = port;
		this.requestHandler = requestHandler;
	}

	public ExchangeHandler getExchangeHandler() {
		return requestHandler;
	}
	
	public void setExchangeHandler(ExchangeHandler requestHandler) {
		if (null == requestHandler) {
			throw new IllegalArgumentException("handler == null");
		}
		this.requestHandler=requestHandler;
	}
	
	public void start() throws Exception {
		boss = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();
		try {
			bootstrap = new ServerBootstrap();
			bootstrap.group(boss, worker);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
			bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			if (null == requestHandler) {
				requestHandler = new DefaultExchangeHandler();
			}
			requestHandler.start();
			
			NettyHandler serverHandler = new NettyHandler(ChannelHandlers.wrap(requestHandler));

			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
//					ch.pipeline().addLast(new ReadTimeoutHandler(60));
					ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					// ch.pipeline().addLast(new WriteTimeoutHandler(60));
					ch.pipeline().addLast(serverHandler);
				}

			});

			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	public void stop() {

	}
}
