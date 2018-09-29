package com.efun.game.user.entity;

public class UidWithMobile {
    private Long uid;

    private String mobile;

    private Byte usedStatus;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Byte getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Byte usedStatus) {
        this.usedStatus = usedStatus;
    }
}