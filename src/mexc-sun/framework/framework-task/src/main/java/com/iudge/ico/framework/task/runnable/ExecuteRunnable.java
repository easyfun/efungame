package com.iudge.ico.framework.task.runnable;

import com.alibaba.fastjson.JSON;
import com.iudge.ico.framework.task.util.DateUtil;
import com.iudge.ico.framework.task.TaskManager;
import com.iudge.ico.framework.task.entity.TaskPO;
import com.iudge.ico.framework.task.entity.builder.TaskPOBuilder;
import com.iudge.ico.framework.task.enums.RetryStrategy;
import com.iudge.ico.framework.task.enums.TaskStatus;
import com.iudge.ico.framework.task.handler.TaskExecuteResult;
import com.iudge.ico.framework.task.handler.TaskHandlerInterface;
import com.iudge.ico.framework.task.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class ExecuteRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteRunnable.class);

    private Map<String, TaskHandlerInterface> taskHandlerMap;
    private TaskManager taskManager;

    public ExecuteRunnable(Map<String, TaskHandlerInterface> taskHandlerMap, TaskManager taskManager) {
        this.taskHandlerMap = taskHandlerMap;
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TaskPO taskPO = taskManager.popExecutingTask(null, new Date());

                if (null == taskPO) {
                    ThreadUtil.safeSleep(5000);
                } else {
                    TaskHandlerInterface handler = taskHandlerMap.get(taskPO.getHandler());
                    if (null == handler) {
                        LOGGER.error("任务handler不存在, handlerName={}", taskPO.getHandler());
                        taskManager.deleteTask(null, taskPO.getTaskKey());
                        continue;
                    }

                    // 执行任务
                    executeTask(handler, taskPO);
                }
            } catch (Exception e) {
                LOGGER.error("任务执行出错.", e);
            }
        }
    }

    private void executeTask(TaskHandlerInterface handler, TaskPO taskPO) {
        if (null == taskPO.getFirstTime()) {
            taskPO.setFirstTime(new Date());
        }
        taskPO.setRetriedTimes(taskPO.getRetriedTimes() + 1);

        TaskPO copyTaskPO = TaskPOBuilder.copy(taskPO);
        TaskExecuteResult result = null;
        try {
            result = handler.execute(copyTaskPO, null);
        } catch (Exception e) {
            LOGGER.error("执行任务出错,executing queque process error. taskPO={}", JSON.toJSONString(taskPO), e);
            exceptionRetryTask(taskPO);
            return;
        }

        switch (result.getTaskResult()) {
            case SUCCESSFUL:
                successTask(result, taskPO);
                break;
            case FAIL:
                failTask(result, taskPO);
                break;
            case RETRY:
                retryTask(result, taskPO);
                break;
        }
    }

    private void successTask(TaskExecuteResult result, TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.SUCCESSFUL.getValue());
        taskPO.setProgress(result.getProgress());

        taskManager.successTask(taskPO);
    }

    private void failTask(TaskExecuteResult result, TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.FAILED.getValue());
        taskPO.setProgress(result.getProgress());

        taskManager.failTask(taskPO);
    }

    private void retryTask(TaskExecuteResult result, TaskPO taskPO) {
        taskPO.setProgress(result.getProgress());
        exceptionRetryTask(taskPO);
    }

    private void exceptionRetryTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateLastTime(taskPO, current);

        taskPO.setNextTime(DateUtil.addMiliSeconds(taskPO.getNextTime(), taskPO.getRetryInterval()));

        if (taskPO.getRetryStrategy() == RetryStrategy.NORMAL.getValue() && taskPO.getRetriedTimes() >= taskPO.getMaxRetryTimes()) {
            taskPO.setTaskStatus(TaskStatus.MORE_RETRY_FAILED.getValue());
            taskManager.failTask(taskPO);
        } else {
            taskPO.setTaskStatus(TaskStatus.RETRYING.getValue());
            taskManager.retryTask(taskPO);
        }
    }

}
