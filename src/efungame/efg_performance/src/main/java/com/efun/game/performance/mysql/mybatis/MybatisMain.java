package com.efun.game.performance.mysql.mybatis;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.performance.common.Stats;

public class MybatisMain {
	private static final Logger logger = LoggerFactory.getLogger(MybatisMain.class);
	
	public static ClassPathXmlApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext("application.xml");
		applicationContext.start();
		
		int threads=1;
		int maxCountPerThread=2;
		ArrayList<Thread> threadList=new ArrayList<>();
		Stats.start();
		for (int i=0; i<threads; i++) {
			Thread t=new Thread(new MybatisRunnableImpl(applicationContext, maxCountPerThread));
			threadList.add(t);
			t.start();
		}
		
		for (int i=0; i<threads; i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				logger.error("fail to wait thread i={}", i, e);
				System.exit(-1);
			}
		}
		
		Stats.stop();
	}
}