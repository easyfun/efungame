package com.efun.game.task.sky.entity;

import com.efun.game.task.sky.enums.RetryStrategy;
import com.efun.game.task.sky.enums.TaskStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */
public class TaskPO {
    private String key;

    private String param;

    private String handler;

    /** 重试策略 */
    private RetryStrategy retryStrategy;

    /** 重试间隔ms */
    private int retryInterval;

    /** 最大重试次数 */
    private int maxRetryTimes;

    /** 执行状态 */
    private TaskStatus status;

    /** 已重试次数 */
    private int retriedTimes;

    /** 创建时间 */
    private Date createdTime;

    /** 首次执行时间 */
    private Date firstTime;

    /** 最近执行时间 */
    private Date lastTime;

    /** 下次执行时间 */
    private Date nextTime;

    /** 完成时间 */
    private Date doneTime;

    /** 更新时间 */
    private Date updatedTime;

    /** 子任务 */
    private List<ChildPO> childs;

    /** 取消任务信息 */
    private Cancel cancel;

    /** 暂停子任务 */
    private String currentPausePoint;

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

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getRetriedTimes() {
        return retriedTimes;
    }

    public void setRetriedTimes(int retriedTimes) {
        this.retriedTimes = retriedTimes;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<ChildPO> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildPO> childs) {
        this.childs = childs;
    }

    public Cancel getCancel() {
        return cancel;
    }

    public void setCancel(Cancel cancel) {
        this.cancel = cancel;
    }

    public String getCurrentPausePoint() {
        return currentPausePoint;
    }

    public void setCurrentPausePoint(String currentPausePoint) {
        this.currentPausePoint = currentPausePoint;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
