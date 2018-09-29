package com.mexc.sun.cores.test;

import com.mexc.sun.framework.redis.RedisData;
import com.mexc.sun.framework.test.SpringTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by easyfun on 2018/4/24.
 */
public class RedisDataTest extends SpringTestCase{
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataTest.class);

    @Autowired
    private RedisData redisData;

    @Test
    public void setBaseData() {
        redisData.setBaseData("test", "test");
    }
}
