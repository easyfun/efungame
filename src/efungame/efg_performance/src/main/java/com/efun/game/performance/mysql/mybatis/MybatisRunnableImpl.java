package com.efun.game.performance.mysql.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.performance.common.Stats;

class MybatisRunnableImpl implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MybatisRunnableImpl.class);
	private final ClassPathXmlApplicationContext applicationContext;

	private final int maxCount;

	public MybatisRunnableImpl(ClassPathXmlApplicationContext applicationContext, int maxCount) {
		this.applicationContext = applicationContext;
		this.maxCount = maxCount;
	}

	@Override
	public void run() {
		UserService userService = (UserService) applicationContext.getBean("userServiceImpl");

		int i = 0;
		while (i++ < maxCount) {
			try {
				userService.insert();
				Stats.addSuccess(1);
			} catch (Exception e) {
				Stats.addFail(1);
				logger.error("fail userService.insert", e);
			}
		}
	}
}