package com.efun.game.user.test.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.protobuf.app.BaseRespProto.BaseResp;

//import org.springframework.stereotype.Service;

import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.protobuf.app.user.UserSignUpRespProto.UserSignUpResp;
import com.efun.game.protobuf.app.user.UserSingInRespProto.UserSignInResp;
import com.efun.game.service.test.dto.UserSignInReqDto;
import com.efun.game.service.test.dto.UserSignInRespDto;
import com.efun.game.service.test.dubbo.UserService;
import com.google.protobuf.ByteString;

//@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserSignUpResp signUp(UserSignUpReq userSignUpReq) {
		logger.debug("[user:signUp]req={}", userSignUpReq);
		if (!"easyfun".equals(userSignUpReq.getUserName()) || !"easyfun".equals(userSignUpReq.getPassword()) ) {
			return null;
		}
		
		UserSignUpResp.Builder resp=UserSignUpResp.newBuilder();
		BaseResp.Builder baseResp = BaseResp.newBuilder();
		baseResp.setErrorCode(0);
		baseResp.setErrorMessage("");
		return resp.build();
	}

	@Override
	public UserSignInResp signIn(UserSignInReq userSignInReq) {
		if (!"easyfun".equals(userSignInReq.getUserName()) || !"easyfun".equals(userSignInReq.getPassword()) ) {
			return null;
		}
		
		UserSignInResp.Builder resp=UserSignInResp.newBuilder();
		resp.setUid(888888L);
		resp.setLoginIp("127.0.0.1");
		resp.setNickName("easyfun");
		resp.setHeadPictureUrlBytes(ByteString.copyFromUtf8("http://www.qq.com"));
		resp.setGender("Man");
		return resp.build();
	}

	@Override
	public String signUp(String user, String password) {
//		logger.debug("user={}, password={}", user, password);
		if (!"easyfun".equals(user) || !"easyfun".equals(password)) {
			return null;
		}
		
		return "ok";
	}

	@Override
	public byte[] execute(long commandId, byte[] request) throws Exception {
		UserSignUpReq userSignUpReq = UserSignUpReq.parseFrom(request); 
//		logger.debug("[user:signUp]req={}", userSignUpReq);
		if (!"easyfun".equals(userSignUpReq.getUserName()) || !"easyfun".equals(userSignUpReq.getPassword()) ) {
			return null;
		}
		
		UserSignUpResp.Builder resp=UserSignUpResp.newBuilder();
		BaseResp.Builder baseResp = BaseResp.newBuilder();
		baseResp.setErrorCode(0);
		baseResp.setErrorMessage("");
		resp.setBaseResp(baseResp);
		UserSignUpResp result = resp.build();
//		logger.debug("resp={}", result);
		return result.toByteArray();
	}

	@Override
	public UserSignInRespDto signInDto(UserSignInReqDto userSignInReq) {
		if (!"easyfun".equals(userSignInReq.getUserName()) || !"easyfun".equals(userSignInReq.getPassword()) ) {
			return null;
		}
		
		UserSignInRespDto resp=new UserSignInRespDto();
		resp.setErrorCode(0);
		resp.setErrorMeassge("");
		return resp;
	}

}
