package com.efun.game.performance.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.performance.common.Stats;



class IdRunnableImpl implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(IdRunnableImpl.class);
	private final ClassPathXmlApplicationContext applicationContext;
	private final int maxCount;

	public IdRunnableImpl(ClassPathXmlApplicationContext applicationContext, int maxCount) {
		this.applicationContext = applicationContext;
		this.maxCount = maxCount;
	}
	
//	public DruidRunnableImpl(int maxCount) {
//		this.applicationContext=null;
//		this.maxCount = maxCount;
//	}

	@Override
	public void run() {
		IdService idService = (IdService) applicationContext.getBean("idServiceImpl");

		int i = 0;
		while (i++ < maxCount) {
			try {
				idService.insert();
				Stats.addSuccess(1);
			} catch (Exception e) {
				Stats.addFail(1);
				logger.error("fail userService.insert", e);
			}
		}
	}
	
}