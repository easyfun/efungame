package com.efun.game.user.entity.builder;

import java.util.Date;

import com.efun.game.common.enums.UserEnums.IdCardStatus;
import com.efun.game.common.enums.UserEnums.IdCardType;
import com.efun.game.common.enums.UserEnums.UserStatus;
import com.efun.game.common.id.IdUtils;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.user.entity.UidWithUserName;
import com.efun.game.user.entity.User;

public class UserBuilder {
	public static User build(Date now, UserSignUpReq req, UidWithUserName uidWithUserName) {
		User user = new User();
		user.setUid(uidWithUserName.getUid());
		user.setMobile(null);
		user.setUserName(req.getUserName());
		user.setEmail(null);
		user.setIdCardNo(null);
		user.setIdCardType(IdCardType.Unknown);
		user.setIdCardStatus(IdCardStatus.NotRealName);
		user.setPassword(req.getPassword());
		user.setUserStatus(UserStatus.Normal);
		user.setUpdateTime(now);
		user.setCreateTime(now);
		return user;
	}
	
	public static User buildTest() {
		User user = new User();
		user.setUid(IdUtils.getInstance().createUid());
		user.setMobile("");
		user.setUserName("easyfun");
		user.setEmail("");
		user.setIdCardNo("");
		user.setIdCardType(IdCardType.Unknown);
		user.setIdCardStatus(IdCardStatus.NotRealName);
		user.setPassword("easyfun");
		user.setUserStatus(UserStatus.Normal);
		user.setUpdateTime(new Date());
		user.setCreateTime(new Date());
		return user;
	}
}
