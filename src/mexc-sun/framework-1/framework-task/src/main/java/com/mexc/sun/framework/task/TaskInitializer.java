package com.mexc.sun.framework.task;

import com.mexc.sun.framework.task.handler.TaskHandlerInterface;
import com.mexc.sun.framework.task.runnable.ExecuteRunnable;
import com.mexc.sun.framework.task.runnable.PendRunnable;
import com.mexc.sun.framework.task.util.TaskRedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskInitializer.class);

    @Value("${framework.task.execute.thread.count:1}")
    private int taskExecutingQueueThreadCount;

    @Autowired
    private TaskRedisLock taskRedisLock;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("=======================>开始注册TaskHandler列表<=======================");
        Map<String, Object> beanMap = event.getApplicationContext().getBeansWithAnnotation(TaskHandler.class);
        Map<String, TaskHandlerInterface> taskHandlerMap = (Map<String, TaskHandlerInterface>) event.getApplicationContext().getBean("taskHandlerMap");

        for (Object beanObject : beanMap.values()) {
            TaskHandler annotation = beanObject.getClass().getAnnotation(TaskHandler.class);
            LOGGER.info("taskHandler: {}, {}", beanObject.getClass().getSimpleName(), beanObject.getClass().getName());
            taskHandlerMap.put(beanObject.getClass().getSimpleName(), (TaskHandlerInterface)beanObject);
        }

        TaskManager taskManager = event.getApplicationContext().getBean("taskManager", TaskManager.class);
        //启动executing队列处理线程
        for (int i=0; i<taskExecutingQueueThreadCount; i++) {
            new Thread(new ExecuteRunnable(taskHandlerMap, taskManager), "execute-" + i).start();
        }

        //启动pending队列处理线程
        new Thread(new PendRunnable(taskManager, taskRedisLock), "pend").start();

        LOGGER.info("=======================>完成注册TaskHandler列表<=======================");
    }
}
