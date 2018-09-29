package com.efun.game.user.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.efun.game.user.base.SpringTestCase;
import com.efun.game.user.entity.UserDetail;
import com.efun.game.user.entity.builder.UserDetailBuilder;

public class UserDetailMapperTest extends SpringTestCase {
	
	@Resource
	private UserDetailMapper userDetailMapper;
	
	@Test
	public void insert() {
		UserDetail record=UserDetailBuilder.buildTest();
		userDetailMapper.insert(record);
	}
}
