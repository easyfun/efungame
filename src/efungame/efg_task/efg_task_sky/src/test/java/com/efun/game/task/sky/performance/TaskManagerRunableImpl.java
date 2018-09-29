package com.efun.game.task.sky.performance;

import com.efun.game.commontest.PerformanceTestStats;
import com.efun.game.task.sky.Task;
import com.efun.game.task.sky.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2018/3/21 0021.
 */
public class TaskManagerRunableImpl implements Runnable {
    private static  final Logger LOGGER = LoggerFactory.getLogger(TaskManagerRunableImpl.class);
    private long threadId;
    private TaskManager taskManager;
    private int count;
    private int id = 0;

    public TaskManagerRunableImpl(int threadId, TaskManager taskManager, int count) {
        this.threadId = threadId * 100000000L;
        this.taskManager = taskManager;
        this.count = count;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Task task = TaskBuilder.buildTestTask();
            id++;
            if (true == taskManager.fastProduceTask(task)) {
                PerformanceTestStats.addSuccess(1);
            } else {
                PerformanceTestStats.addFail(1);
            }
        }

        long endTime = System.currentTimeMillis();
        LOGGER.debug("{}:useTime = {} ms/{}", count, endTime - startTime, count);
    }
}
