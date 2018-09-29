package com.efun.game.task.sky;

import com.alibaba.fastjson.JSON;
import com.efun.game.commontest.PerformanceTestStats;
import com.efun.game.commontest.SpringTestCase;
import com.efun.game.task.sky.entity.TaskPO;
import com.efun.game.task.sky.entity.builder.TaskPOBuilder;
import com.efun.game.task.sky.enums.RetryStrategy;
import com.efun.game.task.sky.performance.TaskManagerRunableImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/27.
 */
public class TaskManagerTest extends SpringTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagerTest.class);

    @Autowired
    private TaskManager taskManager;

    @Test
    public void taskToJson() {
        Task task = new Task();
        task.setKey("201803271652");
        task.setParam("20180327");
        task.setHandler("Task");
        task.setRetryStrategy(RetryStrategy.NORMAL);
        LOGGER.debug(JSON.toJSONString(task));
    }

    @Test
    public void fastProduceTask() {
        Task task = new Task();
        task.setKey("201803271652");
        task.setParam("20180327");
        task.setHandler("Task");
        task.setRetryStrategy(RetryStrategy.NORMAL);
        taskManager.fastProduceTask(task);
        LOGGER.debug("hello world");

        // mq异步投递，需要等待
//        try {
//            Thread.sleep(2*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void produceRetryTask() {
        TaskPO taskPO = TaskPOBuilder.buildTestTaskPO();
        LOGGER.debug("taskPO={}", JSON.toJSONString(taskPO));
        taskManager.produceRetryTask(taskPO);
    }

//    @Test
    public void performance() {
        int maxCountPerThread=10000;
        int threads = 10;
        ArrayList<Thread> threadList=new ArrayList<Thread>();
        PerformanceTestStats.start();
        for (int i=0; i<threads; i++) {
            Thread t=new Thread(new TaskManagerRunableImpl(i, taskManager, maxCountPerThread));
            threadList.add(t);
            t.start();
        }

        for (int n=0; n<threads; n++) {
            try {
                threadList.get(n).join();
            } catch (InterruptedException e) {
                LOGGER.error("fail to wait thread i={}", n, e);
                System.exit(-1);
            }
        }
        PerformanceTestStats.stop();

        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
