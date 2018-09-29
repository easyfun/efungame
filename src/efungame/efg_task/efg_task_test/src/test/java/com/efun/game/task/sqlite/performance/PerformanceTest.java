//package com.efun.game.task.sqlite.performance;
//
//import java.util.ArrayList;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.efun.game.commontest.PerformanceTestStats;
//
//public class PerformanceTest /*extends SpringTestCase */{
//	private static final Logger logger = LoggerFactory.getLogger(PerformanceTest.class);
//	public static ClassPathXmlApplicationContext applicationContext;
//
//	public static void main(String[] args) {
//		applicationContext = new ClassPathXmlApplicationContext("application.xml");
//		applicationContext.start();
//
//		int threads=1;
//		int maxCountPerThread=20000;
//		ArrayList<Thread> threadList=new ArrayList<>();
//		PerformanceTestStats.start();
//		for (int i=0; i<threads; i++) {
//			Thread t=new Thread(new SqliteRunnableImpl(applicationContext, maxCountPerThread));
//			threadList.add(t);
//			t.start();
//		}
//		
//		for (int i=0; i<threads; i++) {
//			try {
//				threadList.get(i).join();
//			} catch (InterruptedException e) {
//				logger.error("fail to wait thread i={}", i, e);
//				System.exit(-1);
//			}
//		}
//		
//		PerformanceTestStats.stop();
//
//	}
//}
