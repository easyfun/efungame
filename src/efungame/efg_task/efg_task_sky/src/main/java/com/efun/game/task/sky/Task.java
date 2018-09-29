package com.efun.game.task.sky;

import com.efun.game.task.sky.enums.RetryStrategy;

/**
 * Created by Administrator on 2018/3/27.
 */
public class Task {
    /** 默认最大重试次数 */
    public static final int DEFAULT_MAX_RETRY_TIMES = 15;
    /** 默认重试间隔60s */
    public static final int DEFAULT_RETRY_INTERVAL = 60 * 1000;

    private String key;
    private String param;
    private String handler;
    private RetryStrategy retryStrategy;
    /** 最大重试次数 */
    private int maxRetryTimes = DEFAULT_MAX_RETRY_TIMES;
    /** 重试时间间隔ms */
    private int retryInterval = DEFAULT_RETRY_INTERVAL;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public RetryStrategy getRetryStrategy() {
        return retryStrategy;
    }

    public void setRetryStrategy(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        if (retryInterval < 0) {
            this.retryInterval = DEFAULT_RETRY_INTERVAL;
        }
        this.retryInterval = retryInterval;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
