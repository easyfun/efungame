package com.efun.game.task.sky.entity;

import com.efun.game.task.sky.enums.ChildOperationMode;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/27.
 */
public class ChildOperation {
    private ChildOperationMode mode;
    private Date appliedTime;
    private Date doneTime;

    public ChildOperationMode getMode() {
        return mode;
    }

    public void setMode(ChildOperationMode mode) {
        this.mode = mode;
    }

    public Date getAppliedTime() {
        return appliedTime;
    }

    public void setAppliedTime(Date appliedTime) {
        this.appliedTime = appliedTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }
}
