package com.efun.game.user.test.handler.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.efun.game.user.dao.UidWithUserNameMapper;
import com.efun.game.user.entity.UidWithUserName;
import com.efun.game.user.entity.User;
import com.efun.game.user.entity.builder.UidWithUserNameBuilder;
import com.efun.game.user.test.handler.UidWithUserNameService;

@Service("uidService")
public class UidWithUserNameServiceImpl implements UidWithUserNameService {
	private static Logger logger=LoggerFactory.getLogger(UidWithUserNameServiceImpl.class);

	@Resource
	private UidWithUserNameMapper uidWithUserNameMapper;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void create(User user) {
		uidWithUserNameMapper.insert(UidWithUserNameBuilder.buildTest());
	}
}
