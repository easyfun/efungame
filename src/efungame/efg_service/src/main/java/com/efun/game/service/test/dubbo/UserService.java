package com.efun.game.service.test.dubbo;

import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.protobuf.app.user.UserSignUpRespProto.UserSignUpResp;
import com.efun.game.protobuf.app.user.UserSingInRespProto.UserSignInResp;
import com.efun.game.service.test.dto.UserSignInReqDto;
import com.efun.game.service.test.dto.UserSignInRespDto;

public interface UserService {
	/**
	 * 注册
	 * @param userSignUpReq
	 * @return
	 */
	UserSignUpResp signUp(UserSignUpReq userSignUpReq);
	
	String signUp(String user, String password);
	
	UserSignInRespDto signInDto(UserSignInReqDto req);
	
	/**
	 * 登录
	 * @param userSignInReq
	 * @return
	 */
	UserSignInResp signIn(UserSignInReq userSignInReq);
	
	byte[] execute(long commandId, byte[] request) throws Exception;
	
}
