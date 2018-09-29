package com.efun.game.user.entity.builder;

import java.util.Calendar;
import java.util.Date;

import com.efun.game.common.enums.UserEnums.Age;
import com.efun.game.common.enums.UserEnums.Gender;
import com.efun.game.common.enums.UserEnums.MarriageStatus;
import com.efun.game.common.enums.UserEnums.SecurityLevel;
import com.efun.game.common.enums.UserEnums.SignUpAppType;
import com.efun.game.common.id.IdUtils;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.user.entity.UidWithUserName;
import com.efun.game.user.entity.User;
import com.efun.game.user.entity.UserDetail;

public class UserDetailBuilder {
	private static final Date UNKNOWN_TIME; 

	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 9999);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		UNKNOWN_TIME = calendar.getTime();
	}
	
	public static UserDetail build(Date now, UserSignUpReq req, UidWithUserName uid, User user) {
		UserDetail result=new UserDetail();
		result.setUid(uid.getUid());
		result.setNickName("");
		result.setHeadPictureUrl("");
		result.setGender(Gender.Unknown);
		result.setRealName("");
		result.setBirthday(UNKNOWN_TIME);
		result.setAge(Age.Unknown.getValue());
		result.setMarriageStatus(MarriageStatus.Unknown);
		result.setEducation("");
		result.setSecurityLevel(SecurityLevel.Unknown);
		result.setCityCode("");
		result.setProCode("");
		result.setSignUpIp(req.getRegIp());
		result.setSignUpChannel("");
		result.setSignUpAppType(SignUpAppType.Unknown);
		result.setSignUpDate(now);
		result.setUpdateTime(now);
		result.setCreateTime(now);
		return result;
	}
	
	public static UserDetail buildTest() {
		UserDetail result=new UserDetail();
		result.setUid(IdUtils.getInstance().createUid());
		result.setNickName("");
		result.setHeadPictureUrl("");
		result.setGender(Gender.Unknown);
		result.setRealName("");
		result.setBirthday(UNKNOWN_TIME);
		result.setAge(Age.Unknown.getValue());
		result.setMarriageStatus(MarriageStatus.Unknown);
		result.setEducation("");
		result.setSecurityLevel(SecurityLevel.Unknown);
		result.setCityCode("");
		result.setProCode("");
		result.setSignUpIp("127.0.0.1");
		result.setSignUpChannel("");
		result.setSignUpAppType(SignUpAppType.Unknown);
		Date now = new Date();
		result.setSignUpDate(now);
		result.setUpdateTime(now);
		result.setCreateTime(now);
		return result;
	}
	
}
