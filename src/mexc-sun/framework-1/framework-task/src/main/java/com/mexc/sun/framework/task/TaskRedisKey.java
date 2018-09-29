package com.mexc.sun.framework.task;

/**
 * Created by easyfun on 2018/4/28.
 */
public class TaskRedisKey {
    public static final String TASK_INFO = "t_task:info:hash";
    public static final String TASK_PENDING = "t_task:pending:zset";
    public static final String TASK_EXECUTING = "t_task:executing:zset";

//    public static String buildKeyTaskInfo(String key) {
//        return TASK_INFO + key;
//    }

}
