package com.efun.game.user.entity;

public class UidWithEmail {
    private Long uid;

    private String email;

    private Byte usedStatus;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Byte usedStatus) {
        this.usedStatus = usedStatus;
    }
}