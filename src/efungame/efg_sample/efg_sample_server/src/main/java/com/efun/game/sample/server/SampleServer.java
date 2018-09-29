package com.efun.game.sample.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.netty.server.NettySampleServer;
import com.efun.game.protobuf.api.CodeTypeProto.CodeType;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.api.EncryptionTypeProto.EncryptionType;
import com.efun.game.protobuf.api.HeaderProto;
import com.efun.game.protobuf.api.MessageProto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SampleServer extends ChannelInboundHandlerAdapter {
	private static final Logger logger=LoggerFactory.getLogger(SampleServer.class);
	
	public static void main(String[] args) {
		logger.debug("start server");
		NettySampleServer server=new NettySampleServer(7708);
		server.setServerHandler(new SampleServer());
		try {
			server.start();
		} catch (Exception e) {
			logger.debug("exception: ", e);
		}
		logger.debug("server exit");
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channel active, remote addr = {}", (InetSocketAddress)ctx.channel().remoteAddress());
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channel inactive, remote addr = {}", (InetSocketAddress)ctx.channel().remoteAddress());
		super.channelInactive(ctx);
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProto.Message request = (MessageProto.Message) msg;
        logger.debug("message = {}", request.toString());
        HeaderProto.Header requestHeader=request.getHeader();
        
        MessageProto.Message.Builder response = MessageProto.Message.newBuilder();
        HeaderProto.Header.Builder responseHeader = HeaderProto.Header.newBuilder();
        responseHeader.setCodeType(CodeType.forNumber(request.getHeader().getCodeType().getNumber()+1));
        responseHeader.setCrcCode(0);
        responseHeader.setEncryptionType(EncryptionType.NONE);
        responseHeader.setId(requestHeader.getId());
        responseHeader.setSessionId(requestHeader.getSessionId());
        responseHeader.setCommandId(CommandId.HEART_BEAT_RESPONSE);
        response.setHeader(responseHeader);
        ctx.writeAndFlush(response.build());
    }

}
