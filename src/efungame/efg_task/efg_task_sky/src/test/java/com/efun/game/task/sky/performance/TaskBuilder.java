package com.efun.game.task.sky.performance;

import com.efun.game.common.id.IdUtils;
import com.efun.game.task.sky.Task;
import com.efun.game.task.sky.enums.RetryStrategy;

/**
 * Created by Administrator on 2018/3/27.
 */
public class TaskBuilder {
    public static Task buildTestTask() {
        Task task = new Task();
        task.setKey(String.valueOf(IdUtils.getInstance().createUid()));
        task.setParam(null);
        task.setHandler("task");
        task.setRetryStrategy(RetryStrategy.NORMAL);
        return task;
    }
}
