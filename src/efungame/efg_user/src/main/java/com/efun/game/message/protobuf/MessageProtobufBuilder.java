package com.efun.game.message.protobuf;

import com.efun.game.protobuf.app.BaseRespProto.BaseResp;
import com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp;

public class MessageProtobufBuilder {
	public static UpdateUserSignInStatusResp.Builder buildUpdateUserSignInStatusResp(long errorCode, String errorMessage) {
		UpdateUserSignInStatusResp.Builder resp = UpdateUserSignInStatusResp.newBuilder();
		BaseResp.Builder base = BaseResp.newBuilder();
		base.setErrorCode(errorCode);
		base.setErrorMessage(errorMessage);
		resp.setBaseResp(base);
		return resp;
	}
	
}
