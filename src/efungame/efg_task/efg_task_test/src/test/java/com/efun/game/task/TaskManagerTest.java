package com.efun.game.task;

import com.efun.game.commontest.PerformanceTestStats;
import com.efun.game.task.performance.TaskManagerRunableImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efun.game.commontest.SpringTestCase;
import com.efun.game.task.entity.dto.TaskDTO;
import com.efun.game.task.entity.dto.builder.TaskDTOBuilder;

import org.junit.Test;
import org.slf4j.Logger;

import java.util.ArrayList;

public class TaskManagerTest extends SpringTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagerTest.class);

	@Autowired
	private TaskManager taskManager;

	@Test
	public void produceTask() {
		for (int n = 0; n < 10; n++) {
			long startTime = System.currentTimeMillis();
			int count = 10000;
			for (int i = 0; i < count; i++) {
				TaskDTO dto = TaskDTOBuilder.testBuildTaskDTO();
				taskManager.produceTask(dto);
			}

			long endTime = System.currentTimeMillis();
			LOGGER.debug("{}:useTime = {} ms/{}", n, endTime - startTime, count);
		}
	}

	@Test
	public void performance() {
		int maxCountPerThread=10000;
		int threads = 10;
		ArrayList<Thread> threadList=new ArrayList<>();
		PerformanceTestStats.start();
		for (int i=0; i<threads; i++) {
			Thread t=new Thread(new TaskManagerRunableImpl(i, taskManager, maxCountPerThread));
			threadList.add(t);
			t.start();
		}

		for (int i=0; i<threads; i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				LOGGER.error("fail to wait thread i={}", i, e);
				System.exit(-1);
			}
		}

		PerformanceTestStats.stop();

	}
}
