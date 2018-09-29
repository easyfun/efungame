package com.mexc.sun.framework.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mexc.sun.framework.task.Task;
import com.mexc.sun.framework.task.TaskManager;
import com.mexc.sun.framework.task.TaskRedisKey;
import com.mexc.sun.framework.task.dao.TaskDAO;
import com.mexc.sun.framework.task.entity.TaskPO;
import com.mexc.sun.framework.task.enums.RetryStrategy;
import com.mexc.sun.framework.test.SpringTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

public class TaskManagerTest extends SpringTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagerTest.class);

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private StringRedisTemplate taskRedisTemplate;

    @Test
    public void produceTask() throws InterruptedException {
        for (int i=0; i<10000; i++) {
            Task task = new Task();
            task.setTaskKey(String.valueOf(System.nanoTime()));
            task.setHandler(TestTaskHandler.class.getSimpleName());
            task.setRetryStrategy(RetryStrategy.NORMAL);
            task.setParam("testParam");
            task.setMaxRetryTimes(10);
            taskManager.pushTask(task);
//            Thread.sleep(100);
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 获取不到新的任务则休息5秒钟
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("任务终止.", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test
    public void finishTask() {
        TaskPO taskPO = JSONObject.parseObject((String) taskRedisTemplate.opsForHash().get(TaskRedisKey.TASK_INFO, "274323727172454"), TaskPO.class);
        taskManager.finishTask(taskPO);
    }
}
