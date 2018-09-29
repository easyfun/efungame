//package com.efun.game.task.sqlite.performance;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.efun.game.commontest.PerformanceTestStats;
//import com.efun.game.task.entity.po.sqlite.TaskPO;
//import com.efun.game.task.entity.po.sqlite.builder.TaskPOBuilder;
//import com.efun.game.task.service.SqliteIndependentTransactionService;
//
//public class SqliteRunnableImpl implements Runnable {
//	private static final Logger LOGGER = LoggerFactory.getLogger(SqliteRunnableImpl.class);
//	
//	private final ClassPathXmlApplicationContext applicationContext;
//	private int maxCount;
//	
//	public SqliteRunnableImpl(ClassPathXmlApplicationContext applicationContext, int maxCount) {
//		this.applicationContext = applicationContext;
//		this.maxCount = maxCount;
//	}
//
//	@Override
//	public synchronized void run() {
//		SqliteIndependentTransactionService service = (SqliteIndependentTransactionService) applicationContext.getBean(SqliteIndependentTransactionService.class);
//
//		for (int i=0; i<maxCount; i++) {
//			try {
//				TaskPO taskPO = TaskPOBuilder.testBuildTaskPO();
//				service.insertTaskPO(taskPO);
//				PerformanceTestStats.addSuccess(1);
//			} catch (Exception e) {
//				PerformanceTestStats.addFail(1);
//				LOGGER.error("insert into fail.", e);
//			}
//			
////			try {
////				Thread.sleep(1);
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
//		}
//	}
//
//}
