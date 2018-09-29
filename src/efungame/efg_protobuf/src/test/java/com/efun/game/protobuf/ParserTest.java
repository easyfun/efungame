package com.efun.game.protobuf;

import java.util.HashMap;
import java.util.Map;

import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.EnumsProto.UserType;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;

public class ParserTest {
	
	public void parserMap() {
		Map<CommandId, Parser<?>> parsers = new HashMap<>();
		parsers.put(CommandId.USER_SIGN_IN_REQUEST, UserSignInReq.parser());
		
		UserSignInReq.Builder builder = UserSignInReq.newBuilder();
		builder.setUserType(UserType.USER_NAME);
		builder.setUserName("easyfun");
		builder.setPassword("sunny");
		builder.setAppType(AppType.APP_ANDRIOD);
		builder.setLoginIp("127.0.0.1");
		System.out.println("src="+builder.build());
		byte[] bytes = builder.build().toByteArray();
		System.out.println("bytes length="+bytes.length);
		
		try {
			UserSignInReq req = (UserSignInReq)parsers.get(CommandId.USER_SIGN_IN_REQUEST).parseFrom(bytes);
			System.out.println("req="+req);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
}
