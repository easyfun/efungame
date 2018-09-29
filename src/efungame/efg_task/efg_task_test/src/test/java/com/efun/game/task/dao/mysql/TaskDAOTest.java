package com.efun.game.task.dao.mysql;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.efun.game.commontest.SpringTestCase;
import com.efun.game.task.dao.mysql.TaskDAO;
import com.efun.game.task.entity.po.mysql.TaskPO;
import com.efun.game.task.enums.RetryStrategy;

public class TaskDAOTest extends SpringTestCase {

	@Resource
	private TaskDAO taskDAO;
	
	 @Test
	public void insert() {
		TaskPO taskPO = new TaskPO();
		taskPO.setTaskId(System.currentTimeMillis());
		taskPO.setTaskKey(String.valueOf(System.currentTimeMillis()));
		taskPO.setHandler("RobTicketHandler");
		taskPO.setParam("GodLuck");
		taskPO.setStatus("accepted");
		taskPO.setRetryStrategy(RetryStrategy.forever);
		taskPO.setRetryInterval(1000);
		taskPO.setMaxRetryTime(5);
		Date current = new Date();
		taskPO.setFirstTime(current);
		taskPO.setNextTime(current);
		taskPO.setLastTime(current);
		taskPO.setNextTime(current);
		taskPO.setCreateTime(current);
		taskPO.setUpdateTime(current);

		taskDAO.insert(taskPO);
	 }
}
