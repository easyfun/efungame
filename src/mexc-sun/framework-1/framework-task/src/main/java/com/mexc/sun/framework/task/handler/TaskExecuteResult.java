package com.mexc.sun.framework.task.handler;

import com.mexc.sun.framework.task.enums.TaskResult;

public class TaskExecuteResult {
    private TaskResult taskResult;
    private int progress;

    public TaskResult getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
