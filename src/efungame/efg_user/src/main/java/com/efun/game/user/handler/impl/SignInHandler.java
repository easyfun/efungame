package com.efun.game.user.handler.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.efun.game.common.id.IdUtils;
import com.efun.game.common.test.ExceptionGenerator;
import com.efun.game.main.Handler;
import com.efun.game.protobuf.app.BaseRespProto.BaseResp;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.protobuf.app.user.UserSingInRespProto.UserSignInResp;
import com.efun.game.service.dubbo.MessageService;
import com.efun.game.user.dao.UserDetailMapper;
import com.efun.game.user.dao.UserMapper;
import com.efun.game.user.dao.UserSignInLogMapper;
import com.efun.game.user.entity.User;
import com.efun.game.user.entity.UserDetail;
import com.efun.game.user.entity.UserSignInLog;
import com.efun.game.user.entity.builder.UserSignInLogBuilder;
import com.efun.game.user.protobuf.UserProtobufBuilder;
import com.efun.game.user.transaction.DependentTransaction;
import com.google.protobuf.ByteString;

@Service("signInHandler")
public class SignInHandler implements Handler{
	private static final Logger logger=LoggerFactory.getLogger(SignInHandler.class);
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserDetailMapper userDetailMapper;
	
	@Resource
	private UserSignInLogMapper userSignInLogMapper;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private DependentTransaction dependentTransactionImpl;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public byte[] execute(byte[] request) throws Exception {
		UserSignInReq req = UserSignInReq.parseFrom(request);
		logger.debug("[user:signIn]请求: req={}", req);
		Date now = new Date();

		User user = userMapper.selectByUserName(req.getUserName());
		if (null == user) {
			UserSignInResp.Builder respBuilder = UserProtobufBuilder.buildUserSignInResp(1, "用户不存在");
			UserSignInResp resp=respBuilder.build();
			logger.debug("[user:signIn]应答: resp={}", resp);
			return resp.toByteArray();
		}
		
		if (!user.getPassword().equals(req.getPassword())) {
			dependentTransactionImpl.createSignInLogFail(now, req, user, 2, "密码错误");
//			ExceptionGenerator.createException();
			UserSignInResp.Builder respBuilder = UserProtobufBuilder.buildUserSignInResp(2, "密码错误");
			UserSignInResp resp=respBuilder.build();
			logger.debug("[user:signIn]应答: resp={}", resp);
			return resp.toByteArray();
		}
		
		long sessionId = updateUserSignInStatus(req, user);
		registerSystemMessage(sessionId, user);
		
		UserDetail userDetail = userDetailMapper.selectByPrimaryKey(user.getUid());
		dependentTransactionImpl.createSignInLogSuccess(now, sessionId, req, user);
		
		UserSignInResp.Builder respBuilder = UserProtobufBuilder.buildUserSignInResp(0, "");
		respBuilder.setUid(user.getUid());
		respBuilder.setLoginIp(req.getLoginIp());
		respBuilder.setNickName(userDetail.getNickName());
		respBuilder.setHeadPictureUrlBytes(ByteString.copyFromUtf8(userDetail.getHeadPictureUrl()));
		respBuilder.setGender(userDetail.getGender().toString());
		UserSignInResp resp=respBuilder.build();
		logger.debug("[user:signIn]应答: resp={}", resp);
		return resp.toByteArray();
	}
	
	private long updateUserSignInStatus(UserSignInReq req, User user) {
		long sessionId = IdUtils.getInstance().createSessionId();
		return sessionId;
	}

	private void registerSystemMessage(long sessionId, User user) {
		
	}
}
