package com.mexc.sun.framework.task.runnable;

import com.mexc.sun.framework.task.TaskManager;
import com.mexc.sun.framework.task.TaskRedisKey;
import com.mexc.sun.framework.task.entity.TaskPO;
import com.mexc.sun.framework.task.entity.builder.TaskPOBuilder;
import com.mexc.sun.framework.task.enums.TaskStatus;
import com.mexc.sun.framework.task.util.TaskRedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class PendRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PendRunnable.class);

    private TaskManager taskManager;

    private TaskRedisLock taskRedisLock;

    public PendRunnable(TaskManager taskManager, TaskRedisLock taskRedisLock) {
        this.taskManager = taskManager;
        this.taskRedisLock = taskRedisLock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            boolean lock = false;
            try {
                lock = taskRedisLock.lockPendingTask(TaskRedisLock.LOCK_TASK_PENDING);
                if (false == lock) {
                    LOGGER.debug("获取t_task:pending:zset执行权失败");
                    continue;
                }

                TaskPO taskPO = taskManager.popPendingTask(TaskRedisKey.TASK_PENDING, new Date());

                if (null == taskPO) {
                    safeSleep(5000);
                } else {
                    // 执行任务
                    executeTask(taskPO);
                }
            } catch (Exception e) {
                LOGGER.error("任务执行出错.", e);
            } finally {
                if (lock) {
                    taskRedisLock.unlockPendingTask(TaskRedisLock.LOCK_TASK_PENDING);
                } else {
                    safeSleep(5000);
                }
            }
        }
     }

    private void executeTask(TaskPO taskPO) {
        if (taskPO.getTaskStatus() == TaskStatus.ACCEPTED.getValue() || taskPO.getTaskStatus() == TaskStatus.RETRYING.getValue()) {
            // 超时补任务
            timeoutSupplyRetryTask(taskPO);
            return;
        }

        if (taskPO.getTaskStatus() == TaskStatus.PAUSED.getValue() || taskPO.getTaskStatus() == TaskStatus.CANCELLED.getValue()) {
            // TODO
            return;
        }

        if (taskPO.getTaskStatus() == TaskStatus.SUCCESSFUL.getValue()) {
            successFinishTask(taskPO);
            return;
        }
        if (taskPO.getTaskStatus() == TaskStatus.FAILED.getValue()) {
            failFinishTask(taskPO);
            return;
        }
        if (taskPO.getTaskStatus() == TaskStatus.MORE_RETRY_FAILED.getValue()) {
            moreRetryFailFinishTask(taskPO);
            return;
        }
    }

    private void successFinishTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.SUCCESSFUL.getValue());

        taskManager.finishTask(taskPO);
    }

    private void moreRetryFailFinishTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.MORE_RETRY_FAILED.getValue());

        taskManager.finishTask(taskPO);
    }

    private void failFinishTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.FAILED.getValue());

        taskManager.finishTask(taskPO);
    }

    private void timeoutSupplyRetryTask(TaskPO taskPO) {
        Date current = new Date();
        taskPO.setNextTime(current);
        taskPO.setUpdatedTime(current);
        taskManager.timeoutSupplyRetryTask(taskPO);
    }

    private void safeSleep(long milis) {
        try {
            // 获取不到新的任务则休息5秒钟
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            LOGGER.error("任务终止.", e);
            Thread.currentThread().interrupt();
        }
    }
}
