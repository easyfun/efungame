package com.iudge.ico.framework.tests;

import com.alibaba.fastjson.JSONObject;
import com.iudge.ico.framework.task.Task;
import com.iudge.ico.framework.task.TaskManager;
import com.iudge.ico.framework.task.TaskRedisKey;
import com.iudge.ico.framework.task.entity.TaskPO;
import com.iudge.ico.framework.task.enums.RetryStrategy;
import com.iudge.ico.framework.task.util.TaskRedisLock;
import com.iudge.ico.framework.test.SpringTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class TaskRedisLockTest extends SpringTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRedisLockTest.class);

    @Autowired
    private TaskRedisLock taskRedisLock;

    @Test
    public void lockTimeout() {
        LOGGER.debug("lock={}",taskRedisLock.lockTimeout("easyfun", 100));
    }

    @Test
    public void unlock() {
        taskRedisLock.unlock("easyfun");
    }
}
