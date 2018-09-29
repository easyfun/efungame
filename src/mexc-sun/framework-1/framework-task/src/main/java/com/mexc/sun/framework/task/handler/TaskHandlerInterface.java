package com.mexc.sun.framework.task.handler;

import com.mexc.sun.framework.task.entity.TaskPO;

public interface TaskHandlerInterface {
    /**
     * 注意taskPO不可变
     * @param taskPO
     * @return
     */
    TaskExecuteResult execute(TaskPO taskPO);
}
