package com.efun.game.user.handler.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.efun.game.common.id.IdUtils;
import com.efun.game.main.Handler;
import com.efun.game.protobuf.app.BaseRespProto.BaseResp;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.protobuf.app.user.UserSignUpRespProto.UserSignUpResp;
import com.efun.game.user.dao.UidWithUserNameMapper;
import com.efun.game.user.dao.UserDetailMapper;
import com.efun.game.user.dao.UserMapper;
import com.efun.game.user.entity.UidWithUserName;
import com.efun.game.user.entity.User;
import com.efun.game.user.entity.UserDetail;
import com.efun.game.user.entity.builder.UidWithUserNameBuilder;
import com.efun.game.user.entity.builder.UserBuilder;
import com.efun.game.user.entity.builder.UserDetailBuilder;

@Service("signUpHandler")
public class SignUpHandler implements Handler {
	private static final Logger logger=LoggerFactory.getLogger(SignUpHandler.class);
	
	@Resource
	private UidWithUserNameMapper uidWithUserNameMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserDetailMapper userDetailMapper;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public byte[] execute(byte[] request) throws Exception {
		UserSignUpReq req=UserSignUpReq.parseFrom(request);
		logger.debug("[user:signUp]请求: req={}", req);
		Date now = new Date();
		
		CheckRequest(req);
		
		UidWithUserName uidWithUserName = uidWithUserNameMapper.selectByUserName(req.getUserName());
		if (null != uidWithUserName) {
			throw new Exception("user existed");
		}
		
		uidWithUserName = UidWithUserNameBuilder.build(now, req);
		uidWithUserNameMapper.insert(uidWithUserName);
		
		User user = UserBuilder.build(now, req, uidWithUserName);
		userMapper.insert(user);
		
		UserDetail userDetail = UserDetailBuilder.build(now, req, uidWithUserName, user);
		userDetailMapper.insert(userDetail);
		
		UserSignUpResp.Builder respBuilder=UserSignUpResp.newBuilder();
		BaseResp.Builder baseRespBuilder=BaseResp.newBuilder();
		baseRespBuilder.setErrorCode(0);
		baseRespBuilder.setErrorMessage("");
		respBuilder.setBaseResp(baseRespBuilder);
		UserSignUpResp resp=respBuilder.build();
		logger.debug("[user:signUp]应答: resp={}", resp);
		return resp.toByteArray();
	}

	private void CheckRequest(UserSignUpReq req) throws Exception {
		if (null == req) {
			throw new Exception("request params error");
		}

		if (StringUtils.isEmpty(req.getUserName()) || StringUtils.isEmpty(req.getPassword())) {
			throw new Exception("request params error");
		}
		
		if (null == req.getAppType() || null == req.getRegIp()) {
			throw new Exception("request params error");
		}
	}
	
}
