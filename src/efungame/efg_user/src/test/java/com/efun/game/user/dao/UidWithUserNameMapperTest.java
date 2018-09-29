package com.efun.game.user.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.user.base.SpringTestCase;
import com.efun.game.user.entity.UidWithUserName;
import com.efun.game.user.entity.builder.UidWithUserNameBuilder;

public class UidWithUserNameMapperTest extends SpringTestCase {
	private static final Logger logger = LoggerFactory.getLogger(UidWithUserNameMapperTest.class);

	@Resource
	private UidWithUserNameMapper uidWithUserNameMapper;
	
	@Test
	public void insert() {
		UidWithUserName record=UidWithUserNameBuilder.buildTest();
		uidWithUserNameMapper.insert(record);		
	}
}
