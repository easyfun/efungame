package com.efun.game.servicetest.user;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efun.game.common.id.IdUtils;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.protobuf.app.user.UserSignUpRespProto.UserSignUpResp;
import com.efun.game.service.dubbo.UserService;
import com.efun.game.service.dubbo.dto.DubboResultDto;
import com.efun.game.servicetest.user.base.SpringTestCase;
import com.google.protobuf.InvalidProtocolBufferException;

public class UserServiceTest extends SpringTestCase {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Autowired
	private UserService userService;
	
	@Test
	public void UserSignUp() throws InvalidProtocolBufferException {
		UserSignUpReq.Builder reqB = UserSignUpReq.newBuilder();
		reqB.setUserName(String.valueOf(IdUtils.getInstance().createSessionId()));
		reqB.setPassword("easyfun");
		reqB.setAppType(AppType.APP_ANDRIOD);
		reqB.setRegIp("127.0.0.1");
		UserSignUpReq req = reqB.build();
		logger.debug("req={}", req);
		DubboResultDto dto = userService.execute(CommandId.USER_SIGN_UP_REQUEST.getNumber(), req.toByteArray());
		if (0 == dto.getErrorCode()) {
			UserSignUpResp result = UserSignUpResp.parseFrom(dto.getData());
			logger.debug("user sign up result = {}", result);
		} else {
			logger.error("user sign up error. errorCode = {}, errorMessage={}", dto.getErrorCode(), dto.getErrorMessage());
		}
		

	}
	
}
