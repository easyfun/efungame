//package com.efun.game.task.dao.sqlite;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.efun.game.commontest.ExceptionGenerator;
//import com.efun.game.commontest.SpringTestCase;
//import com.efun.game.task.entity.po.sqlite.TaskPO;
//import com.efun.game.task.entity.po.sqlite.builder.TaskPOBuilder;
//
//public class TaskSqliteDAOTest extends SpringTestCase {
//	private static final Logger logger = LoggerFactory.getLogger(TaskSqliteDAOTest.class);
//
//	@Resource
//	private TaskSqliteDAO dao;
//	
//	 @Test
//	public void insert() {
//		TaskPO taskPO = TaskPOBuilder.testBuildTaskPO();
//
//		dao.insert(taskPO);
//		
//		ExceptionGenerator.createException();
//		logger.debug("test stop");
//	}
//}
