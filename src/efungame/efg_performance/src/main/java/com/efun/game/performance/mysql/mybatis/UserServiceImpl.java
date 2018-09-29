package com.efun.game.performance.mysql.mybatis;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.efun.game.common.id.IdUtils;
import com.efun.game.performance.mysql.mybatis.dao.UserMapper;
import com.efun.game.performance.mysql.mybatis.entity.User;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	private static final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void insert() {
		User user = buildUser(new Date());
		userMapper.insert(user);
	}
	
	private User buildUser(Date now) {
		User user = new User();
		user.setUid(IdUtils.getInstance().createUid());
		user.setMobile(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setUserName(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setEmail(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setIdCardNo(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setIdCardType((byte) 0);
		user.setIdCardStatus((byte) 0);
		user.setPassword("easyfun");
		user.setUserStatus((byte) 0);
		user.setUpdateTime(now);
		user.setCreateTime(now);
		return user;
	}

	private String buildUserName() {
		return String.valueOf(IdUtils.getInstance().createSessionId());
	}


}
