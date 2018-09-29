package com.efun.game.user.handler.impl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.EnumsProto.UserType;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.user.base.SpringTestCase;

public class SignUpHandlerTest extends SpringTestCase {
	private static final Logger logger = LoggerFactory.getLogger(SignUpHandlerTest.class);
	
	@Autowired
	private SignUpHandler signUpHandler;
	
	@Test
	public void execute() {
		UserSignUpReq.Builder reqBuilder = UserSignUpReq.newBuilder();
		reqBuilder.setUserType(UserType.USER_NAME);
		reqBuilder.setUserName("easyfun");
		reqBuilder.setPassword("easyfun");
		reqBuilder.setAppType(AppType.APP_ANDRIOD);
		reqBuilder.setRegIp("");
		try {
			signUpHandler.execute(reqBuilder.build().toByteArray());
		} catch (Exception e) {
			logger.error("test execute fail.", e);
		}
		logger.debug("test stop");
	}
}
