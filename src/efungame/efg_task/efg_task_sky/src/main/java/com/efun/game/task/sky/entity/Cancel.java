package com.efun.game.task.sky.entity;

import com.efun.game.task.sky.enums.CancelStatus;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/27.
 */
public class Cancel {
    private CancelStatus status;

    /** 申请时间 */
    private Date appliedTime;

    /** 完成时间 */
    private Date doneTime;

    public CancelStatus getStatus() {
        return status;
    }

    public void setStatus(CancelStatus status) {
        this.status = status;
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
