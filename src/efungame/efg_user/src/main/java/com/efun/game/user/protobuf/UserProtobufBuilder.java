package com.efun.game.user.protobuf;

import org.slf4j.Logger;

import com.efun.game.protobuf.app.BaseRespProto.BaseResp;
import com.efun.game.protobuf.app.user.UserSingInRespProto.UserSignInResp;

public class UserProtobufBuilder {
	public static UserSignInResp.Builder buildUserSignInResp(long errorCode, String errorMessage) {
		UserSignInResp.Builder resp = UserSignInResp.newBuilder();
		BaseResp.Builder base = BaseResp.newBuilder();
		base.setErrorCode(errorCode);
		base.setErrorMessage(errorMessage);
		resp.setBaseResp(base);
		return resp;
	}
	
	public static byte[] userSignInRespToByteArray(UserSignInResp.Builder builder, Logger logger, String str) {
		UserSignInResp resp=builder.build();
		logger.debug("[{}]应答: resp={}", str, resp);
		return resp.toByteArray();
	}
	
}
