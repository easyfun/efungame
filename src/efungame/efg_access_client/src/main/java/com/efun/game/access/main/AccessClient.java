package com.efun.game.access.main;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.netty.client.NettyClient;
import com.efun.game.protobuf.api.CodeTypeProto.CodeType;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.api.EncryptionTypeProto.EncryptionType;
import com.efun.game.protobuf.api.HeaderProto;
import com.efun.game.protobuf.api.MessageProto;
import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.EnumsProto.UserType;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.protobuf.app.user.UserSingInRespProto.UserSignInResp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AccessClient extends ChannelInboundHandlerAdapter {
	private static final Logger logger=LoggerFactory.getLogger(AccessClient.class);
	private static final AtomicLong counter = new AtomicLong(0);
	private static final int requestNum = 2000;
	private static long startTime;
	private static long stopTime;
	private static long sendType = 0; // 0-发请求-收应答-发请求; 1-发批量-收
	private static boolean hasSendBatch = false;
	
	public static void main(String[] args) {
		logger.error("access client start");
		NettyClient client = new NettyClient(new AccessClient());
		try {
			client.connect("127.0.0.1", 7708);
		} catch (Exception e) {
			logger.error("access client main catch exception.", e);
		}
		
		logger.error("send UserAuthReq times : {}, used time(ms) : {}", counter.get(), stopTime-startTime);
		logger.error("access client stop");
	}
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channel active");
		startTime = System.currentTimeMillis();
		send(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MessageProto.Message resp=(MessageProto.Message)msg;
		logger.info("receive is : {} ; the counter is : {}", resp, counter.getAndIncrement());
		
		UserSignInResp respBody = UserSignInResp.parseFrom(resp.getBody());
		logger.info("resp body: {}", respBody);
		
		send(ctx);
		
		if (0!=sendType) {
			isFinish();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warn("Unexpected exception from downstream : {}", cause.getMessage());
		if (requestNum < counter.get()) {
			logger.info("stop access client");
		}
		ctx.close();
	}

	private void send(ChannelHandlerContext ctx) throws Exception {
		if (0 == sendType) {
			sendOneByOne(ctx);
		} else {
			if (hasSendBatch) {
				return;
			}
			hasSendBatch = true;
			sendBatch(ctx);
		}
	}
	
	private void sendOneByOne(ChannelHandlerContext ctx) throws Exception {
		isFinish();
		
		MessageProto.Message.Builder request = MessageProto.Message.newBuilder();
		HeaderProto.Header.Builder requestHeader = HeaderProto.Header.newBuilder();
		requestHeader.setCrcCode(0);
		requestHeader.setSessionId(0);
		requestHeader.setEncryptionType(EncryptionType.NONE);
		requestHeader.setCodeType(CodeType.PROTOBUF);
		requestHeader.setId(counter.get());
		requestHeader.setCommandId(CommandId.USER_SIGN_IN_REQUEST);
		request.setHeader(requestHeader);
		
		UserSignInReq.Builder bodyBuilder = UserSignInReq.newBuilder();
		bodyBuilder.setUserType(UserType.USER_NAME);
		bodyBuilder.setUserName("easyfun");
		bodyBuilder.setPassword("easyfun");
		bodyBuilder.setAppType(AppType.APP_ANDRIOD);
		bodyBuilder.setLoginIp("127.0.0.1");
		request.setBody(bodyBuilder.build().toByteString());
		
		logger.info("request = {}", request.build());
		ctx.writeAndFlush(request.build());
	}
	
	private void isFinish() throws Exception {
		if (requestNum >= counter.get()) {
			return;
		}
		stopTime = System.currentTimeMillis();
		logger.info("send over");
		
		throw new Exception("send over, counter="+counter.get());
		
	}
	
	private void sendBatch(ChannelHandlerContext ctx) {
		for (int i=0; i<requestNum; i++) {
			MessageProto.Message.Builder request = MessageProto.Message.newBuilder();
			HeaderProto.Header.Builder requestHeader = HeaderProto.Header.newBuilder();
			requestHeader.setCrcCode(0);
			requestHeader.setSessionId(0);
			requestHeader.setEncryptionType(EncryptionType.NONE);
			requestHeader.setCodeType(CodeType.PROTOBUF);
			requestHeader.setId(counter.get());
			requestHeader.setCommandId(CommandId.USER_SIGN_IN_REQUEST);
			request.setHeader(requestHeader);
			
			UserSignInReq.Builder bodyBuilder = UserSignInReq.newBuilder();
			bodyBuilder.setUserType(UserType.USER_NAME);
			bodyBuilder.setUserName("easyfun");
			bodyBuilder.setPassword("easyfun");
			bodyBuilder.setAppType(AppType.APP_ANDRIOD);
			bodyBuilder.setLoginIp("127.0.0.1");
			request.setBody(bodyBuilder.build().toByteString());
			
			logger.info("request = {}", request.build());
			ctx.writeAndFlush(request.build());
		}
	}
}
