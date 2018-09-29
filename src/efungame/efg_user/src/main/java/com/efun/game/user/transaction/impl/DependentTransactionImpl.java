package com.efun.game.user.transaction.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.user.dao.UserSignInLogMapper;
import com.efun.game.user.entity.User;
import com.efun.game.user.entity.UserSignInLog;
import com.efun.game.user.entity.builder.UserSignInLogBuilder;
import com.efun.game.user.transaction.DependentTransaction;

@Service("dependentTransactionImpl")
public class DependentTransactionImpl implements DependentTransaction {
	@Resource
	private UserSignInLogMapper userSignInLogMapper;

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createSignInLogFail(Date now, UserSignInReq req, User user, long failCode, String failReason) {
		UserSignInLog signInLog = UserSignInLogBuilder.build(now, req, user, failCode, failReason);
		userSignInLogMapper.insert(signInLog);

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void createSignInLogSuccess(Date now, long sessionId, UserSignInReq req, User user) {
		UserSignInLog signInLog = UserSignInLogBuilder.build(now, sessionId, req, user);
		userSignInLogMapper.insert(signInLog);
	}

}
