package com.efun.game.task.service.impl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efun.game.commontest.SpringTestCase;
import com.efun.game.task.service.TaskService;

public class TaskServiceImplTest extends SpringTestCase {
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImplTest.class);
	
	@Autowired
	private TaskService taskService;
	
	@Test
	public void insertTaskPO() {
	}
}
