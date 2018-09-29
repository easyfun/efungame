package com.efun.game.user.transaction;

import java.util.Date;

import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.user.entity.User;

public interface DependentTransaction {
	void createSignInLogFail(Date now, UserSignInReq req, User user, long failCode,String failReason);
	void createSignInLogSuccess(Date now, long sessionId, UserSignInReq req, User user);

}
