package com.efun.game.task.sky.entity;

import com.efun.game.task.sky.enums.ChildStatus;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/27.
 */
public class ChildPO {
    private String name;

    private ChildStatus status;

    /** 已重试次数 */
    private int retriedTimes;

    private Date createdTime;

    /** 首次执行时间 */
    private Date firstTime;

    /** 最近执行时间 */
    private Date lastTime;

    private Date updatedTime;

    /** 暂停时间 */
    private Date pausedTime;

    /** 恢复时间 */
    private Date resumeTime;

    private ChildOperation operation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildStatus getStatus() {
        return status;
    }

    public void setStatus(ChildStatus status) {
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getPausedTime() {
        return pausedTime;
    }

    public void setPausedTime(Date pausedTime) {
        this.pausedTime = pausedTime;
    }

    public Date getResumeTime() {
        return resumeTime;
    }

    public void setResumeTime(Date resumeTime) {
        this.resumeTime = resumeTime;
    }

    public ChildOperation getOperation() {
        return operation;
    }

    public void setOperation(ChildOperation operation) {
        this.operation = operation;
    }

}
