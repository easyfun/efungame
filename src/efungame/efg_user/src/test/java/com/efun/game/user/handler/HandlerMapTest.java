package com.efun.game.user.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.main.Handler;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.EnumsProto.UserType;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.protobuf.app.user.UserSingInRespProto.UserSignInResp;

public class HandlerMapTest {
	private static final Logger logger=LoggerFactory.getLogger(HandlerMapTest.class);

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "application.xml" });
		context.start();

		Map<CommandId, Handler> handlerMap = (Map<CommandId, Handler>) context.getBean("userHandlerMap"); // 获取远程服务代理
		System.out.println(handlerMap);
		
		Handler handler = handlerMap.get(CommandId.USER_SIGN_IN_REQUEST);
		
		UserSignInReq.Builder reqB = UserSignInReq.newBuilder();
		reqB.setUserType(UserType.USER_NAME);
		reqB.setUserName("easyfun");
		reqB.setPassword("easyfun");
		reqB.setAppType(AppType.APP_ANDRIOD);
		reqB.setLoginIp("127.0.0.1");
		UserSignInReq req = reqB.build();
		logger.debug("req={}", req);
		byte[] resp = handler.execute(req.toByteArray());
		UserSignInResp result = UserSignInResp.parseFrom(resp);
		logger.debug("resp={}", result);
	}
}
