package com.efun.game.user.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.main.Handler;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.EnumsProto.UserType;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.service.dubbo.dto.DubboResultDto;
import com.efun.game.user.base.SpringTestCase;
import com.efun.game.user.entity.builder.UserBuilder;

public class UserMapperTest extends SpringTestCase  {
	private Logger logger=LoggerFactory.getLogger(UserMapperTest.class);
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private Map<CommandId, Handler> handlerMap;
	
	@Test
	public void insert() {
		userMapper.insert(UserBuilder.buildTest());
		
	}
	
	@Test
	public void transaction() {
		
		DubboResultDto dto = new DubboResultDto();
		dto.setErrorCode(0);
		dto.setErrorMessage("");
		
		UserSignInReq.Builder reqB = UserSignInReq.newBuilder();
		reqB.setUserType(UserType.USER_NAME);
		reqB.setUserName("sunny");
		reqB.setPassword("easyfun");
		reqB.setAppType(AppType.APP_ANDRIOD);
		reqB.setLoginIp("127.0.0.1");
		UserSignInReq req = reqB.build();

		Handler handler = handlerMap.get(CommandId.USER_SIGN_IN_REQUEST);
		if (null == handler) {
			dto.setErrorCode(-1);
			dto.setErrorMessage("系统暂不支持该请求");
		} else {
			try {
				byte[] data = handler.execute(req.toByteArray());
				dto.setData(data);
			} catch (Exception e) {
				logger.error("[dubbo:userService]execute error.", e);
				dto.setErrorCode(-1);
				dto.setErrorMessage(e.getMessage());
			}
		}
	}
}
