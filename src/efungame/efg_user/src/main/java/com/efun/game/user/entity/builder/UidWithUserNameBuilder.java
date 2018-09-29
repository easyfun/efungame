package com.efun.game.user.entity.builder;

import java.util.Date;

import com.efun.game.common.enums.UserEnums.UsedStatus;
import com.efun.game.common.id.IdUtils;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.user.entity.UidWithUserName;

public class UidWithUserNameBuilder {
	public static UidWithUserName build(Date now, UserSignUpReq req) {
		UidWithUserName result = new UidWithUserName();
		result.setUid(IdUtils.getInstance().createUid());
		result.setUserName(req.getUserName());
		result.setUsedStatus(UsedStatus.Enable);
		result.setUpdateTime(now);
		result.setCreateTime(now);
		return result;
	}
	
	public static UidWithUserName buildTest() {
		UidWithUserName result = new UidWithUserName();
		result.setUid(IdUtils.getInstance().createUid());
		result.setUserName(String.valueOf(IdUtils.getInstance().createSessionId()));
		result.setUsedStatus(UsedStatus.Enable);
		Date now = new Date();
		result.setUpdateTime(now);
		result.setCreateTime(now);
		return result;
	}
	
}
