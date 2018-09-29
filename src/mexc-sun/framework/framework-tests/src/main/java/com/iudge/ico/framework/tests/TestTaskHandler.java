package com.iudge.ico.framework.tests;

import com.iudge.ico.framework.task.TaskHandler;
import com.iudge.ico.framework.task.entity.TaskPO;
import com.iudge.ico.framework.task.enums.TaskResult;
import com.iudge.ico.framework.task.handler.TaskExecuteResult;
import com.iudge.ico.framework.task.handler.TaskHandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TaskHandler
public class TestTaskHandler implements TaskHandlerInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTaskHandler.class);

    @Override
    public TaskExecuteResult execute(TaskPO taskPO, Object params) {
        LOGGER.info("hello test task");

        TaskExecuteResult result = new TaskExecuteResult();
        result.setTaskResult(TaskResult.SUCCESSFUL);
        result.setProgress(0);
        return result;
    }
}
