package com.efun.game.performance.mysql.druid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.performance.common.Stats;


class DruidRunnableImpl implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(DruidRunnableImpl.class);
	private final ClassPathXmlApplicationContext applicationContext;
	private final int maxCount;

	public DruidRunnableImpl(ClassPathXmlApplicationContext applicationContext, int maxCount) {
		this.applicationContext = applicationContext;
		this.maxCount = maxCount;
	}
	
//	public DruidRunnableImpl(int maxCount) {
//		this.applicationContext=null;
//		this.maxCount = maxCount;
//	}

	@Override
	public void run() {
		DruidUserService userService = (DruidUserService) applicationContext.getBean("druidUserServiceImpl");

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