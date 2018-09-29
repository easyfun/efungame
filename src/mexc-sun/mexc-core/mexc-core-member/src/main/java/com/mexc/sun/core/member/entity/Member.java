package com.mexc.sun.core.member.entity;

import java.util.Date;

public class Member {
    private Long memberId;

    private String memberPwd;

    private String tradePwd;

    private Byte memberStatus;

    private Byte memberLevel;

    private Byte authLevel;

    private String email;

    private Byte emailStatus;

    private Date emailActiveTime;

    private String mobile;

    private Byte mobileStatus;

    private Date mobileActiveTime;

    private Date registerTime;

    private Date createTime;

    private Date updateTime;

    private Byte reserve11;

    private Byte reserve12;

    private Byte reserve13;

    private String reserve21;

    private String reserve22;

    private String reserve23;

    private Long reserve31;

    private Long reserve32;

    private Long reserve33;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd == null ? null : memberPwd.trim();
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd == null ? null : tradePwd.trim();
    }

    public Byte getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Byte memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Byte getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Byte memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Byte getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Byte authLevel) {
        this.authLevel = authLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Byte emailStatus) {
        this.emailStatus = emailStatus;
    }

    public Date getEmailActiveTime() {
        return emailActiveTime;
    }

    public void setEmailActiveTime(Date emailActiveTime) {
        this.emailActiveTime = emailActiveTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Byte getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Byte mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public Date getMobileActiveTime() {
        return mobileActiveTime;
    }

    public void setMobileActiveTime(Date mobileActiveTime) {
        this.mobileActiveTime = mobileActiveTime;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getReserve11() {
        return reserve11;
    }

    public void setReserve11(Byte reserve11) {
        this.reserve11 = reserve11;
    }

    public Byte getReserve12() {
        return reserve12;
    }

    public void setReserve12(Byte reserve12) {
        this.reserve12 = reserve12;
    }

    public Byte getReserve13() {
        return reserve13;
    }

    public void setReserve13(Byte reserve13) {
        this.reserve13 = reserve13;
    }

    public String getReserve21() {
        return reserve21;
    }

    public void setReserve21(String reserve21) {
        this.reserve21 = reserve21 == null ? null : reserve21.trim();
    }

    public String getReserve22() {
        return reserve22;
    }

    public void setReserve22(String reserve22) {
        this.reserve22 = reserve22 == null ? null : reserve22.trim();
    }

    public String getReserve23() {
        return reserve23;
    }

    public void setReserve23(String reserve23) {
        this.reserve23 = reserve23 == null ? null : reserve23.trim();
    }

    public Long getReserve31() {
        return reserve31;
    }

    public void setReserve31(Long reserve31) {
        this.reserve31 = reserve31;
    }

    public Long getReserve32() {
        return reserve32;
    }

    public void setReserve32(Long reserve32) {
        this.reserve32 = reserve32;
    }

    public Long getReserve33() {
        return reserve33;
    }

    public void setReserve33(Long reserve33) {
        this.reserve33 = reserve33;
    }
}