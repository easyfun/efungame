package com.mexc.sun.framework.tests;

import com.mexc.sun.framework.task.TaskHandler;
import com.mexc.sun.framework.task.entity.TaskPO;
import com.mexc.sun.framework.task.enums.TaskResult;
import com.mexc.sun.framework.task.handler.TaskExecuteResult;
import com.mexc.sun.framework.task.handler.TaskHandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TaskHandler
public class TestTaskHandler implements TaskHandlerInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTaskHandler.class);

    @Override
    public TaskExecuteResult execute(TaskPO taskPO) {
        LOGGER.info("hello test task");

        TaskExecuteResult result = new TaskExecuteResult();
        result.setTaskResult(TaskResult.SUCCESSFUL);
        result.setProgress(0);
        return result;
    }
}
