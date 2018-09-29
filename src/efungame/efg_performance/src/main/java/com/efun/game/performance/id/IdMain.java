package com.efun.game.performance.id;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.performance.common.Stats;

public class IdMain {
	private static final Logger logger = LoggerFactory.getLogger(IdMain.class);
	
	public static ClassPathXmlApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext("application.xml");
		applicationContext.start();
		
		int threads=10;
		threads=50;
		int maxCountPerThread=20000;
//		maxCountPerThread=1;
		ArrayList<Thread> threadList=new ArrayList<>();
		Stats.start();
		for (int i=0; i<threads; i++) {
			Thread t=new Thread(new IdRunnableImpl(applicationContext, maxCountPerThread));
//			Thread t=new Thread(new DruidRunnableImpl(maxCountPerThread));
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