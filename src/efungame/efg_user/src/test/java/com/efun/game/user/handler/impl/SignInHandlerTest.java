package com.efun.game.user.handler.impl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.EnumsProto.UserType;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.user.base.SpringTestCase;

public class SignInHandlerTest extends SpringTestCase {
	private static final Logger logger = LoggerFactory.getLogger(SignInHandlerTest.class);
	
	@Autowired
	private SignInHandler signInHandler;
	
	@Test
	public void execute() {
		UserSignInReq.Builder reqBuilder = UserSignInReq.newBuilder();
		reqBuilder.setUserType(UserType.USER_NAME);
		reqBuilder.setUserName("easyfun");
		reqBuilder.setPassword("easyfun1");
		reqBuilder.setAppType(AppType.APP_ANDRIOD);
		reqBuilder.setLoginIp("");
		try {
			signInHandler.execute(reqBuilder.build().toByteArray());
		} catch (Exception e) {
			logger.error("test execute fail.", e);
		}
		logger.debug("test stop");
	}
}
