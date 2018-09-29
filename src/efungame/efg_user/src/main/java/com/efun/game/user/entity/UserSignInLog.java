package com.efun.game.user.entity;

import java.util.Date;

public class UserSignInLog {
    private Long id;

    private Long uid;

    private Long sessionId;

    private String signInIp;

    private Byte signInStatus;

    private Long signInFailCode;

    private String signInFailReason;

    private Byte signInAppType;

    private Date signInTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSignInIp() {
        return signInIp;
    }

    public void setSignInIp(String signInIp) {
        this.signInIp = signInIp == null ? null : signInIp.trim();
    }

    public Byte getSignInStatus() {
        return signInStatus;
    }

    public void setSignInStatus(Byte signInStatus) {
        this.signInStatus = signInStatus;
    }

    public Long getSignInFailCode() {
        return signInFailCode;
    }

    public void setSignInFailCode(Long signInFailCode) {
        this.signInFailCode = signInFailCode;
    }

    public String getSignInFailReason() {
        return signInFailReason;
    }

    public void setSignInFailReason(String signInFailReason) {
        this.signInFailReason = signInFailReason == null ? null : signInFailReason.trim();
    }

    public Byte getSignInAppType() {
        return signInAppType;
    }

    public void setSignInAppType(Byte signInAppType) {
        this.signInAppType = signInAppType;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}