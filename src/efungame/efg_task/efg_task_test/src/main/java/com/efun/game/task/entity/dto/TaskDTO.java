package com.efun.game.task.entity.dto;

import java.util.Date;

import com.efun.game.task.enums.RetryStrategy;

public class TaskDTO {
	/**
	 * 任务id
	 */
    private String taskKey;

    /**
     * 任务处理器
     */
    private String handler;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 重试策略
     */
    private RetryStrategy retryStrategy;

    /**
     * 重试时间间隔，单位毫秒
     */
    private Integer retryInterval;

    /**
     * 最大重试次数
     */
    private Integer maxRetryTime;

    /**
     * 第一次执行时间
     */
    private Date executeTime;

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey == null ? null : taskKey.trim();
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public RetryStrategy getRetryStrategy() {
        return retryStrategy;
    }

    public void setRetryStrategy(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Integer getMaxRetryTime() {
        return maxRetryTime;
    }

    public void setMaxRetryTime(Integer maxRetryTime) {
        this.maxRetryTime = maxRetryTime;
    }

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}


}
