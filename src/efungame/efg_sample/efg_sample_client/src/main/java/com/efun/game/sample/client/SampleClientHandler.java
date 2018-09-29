package com.efun.game.sample.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.protobuf.api.CodeTypeProto.CodeType;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.api.EncryptionTypeProto.EncryptionType;
import com.efun.game.protobuf.api.HeaderProto;
import com.efun.game.protobuf.api.MessageProto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SampleClientHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SampleClientHandler.class);
	private int counter;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		logger.info("channel active");
		MessageProto.Message.Builder request = MessageProto.Message.newBuilder();
		HeaderProto.Header.Builder requestHeader = HeaderProto.Header.newBuilder();
		requestHeader.setCrcCode(0);
		requestHeader.setSessionId(1);
		requestHeader.setEncryptionType(EncryptionType.NONE);
		requestHeader.setCodeType(CodeType.PROTOBUF);
		requestHeader.setId(0);
		requestHeader.setCommandId(CommandId.HEART_BEAT_REQUEST);
		request.setHeader(requestHeader);
		logger.info("request = {}", request.build());
		ctx.writeAndFlush(request.build());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MessageProto.Message response=(MessageProto.Message)msg;
		logger.info("Now is : {} ; the counter is : {}", response, ++counter);
		ctx.channel().close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warn("Unexpected exception from downstream : {}", cause.getMessage());
		ctx.close();
	}

}
