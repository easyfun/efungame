package com.efun.game.user.entity.builder;

import java.util.Date;

import com.efun.game.common.id.IdUtils;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.user.entity.User;
import com.efun.game.user.entity.UserSignInLog;

public class UserSignInLogBuilder {
	public static UserSignInLog build(Date now, long sessionId, UserSignInReq req, User user) {
		UserSignInLog signInLog = new UserSignInLog();
		signInLog.setId(IdUtils.getInstance().createPrimaryKeyId());
		signInLog.setUid(null!=user?user.getUid():-1L);
		signInLog.setSessionId(sessionId);
		signInLog.setSignInIp(req.getLoginIp());
		signInLog.setSignInStatus((byte)1);
		signInLog.setSignInFailCode(0L);
		signInLog.setSignInFailReason("");
		signInLog.setSignInAppType((byte)req.getAppType().getNumber());
		signInLog.setSignInTime(now);
		signInLog.setCreateTime(now);
		return signInLog;
	}
	
	public static UserSignInLog build(Date now, UserSignInReq req, User user, long failCode,String failReason) {
		UserSignInLog signInLog = new UserSignInLog();
		signInLog.setId(IdUtils.getInstance().createPrimaryKeyId());
		signInLog.setUid(null!=user?user.getUid():-1L);
		signInLog.setSessionId(-1L);
		signInLog.setSignInIp(req.getLoginIp());
		signInLog.setSignInStatus((byte)0);
		signInLog.setSignInFailCode(failCode);
		signInLog.setSignInFailReason(failReason);
		signInLog.setSignInAppType((byte)req.getAppType().getNumber());
		signInLog.setSignInTime(now);
		signInLog.setCreateTime(now);
		return signInLog;
	}
}
